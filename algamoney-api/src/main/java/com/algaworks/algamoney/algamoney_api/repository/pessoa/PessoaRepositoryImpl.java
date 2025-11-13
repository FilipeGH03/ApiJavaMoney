package com.algaworks.algamoney.algamoney_api.repository.pessoa;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.algaworks.algamoney.algamoney_api.model.Pessoa;
import com.algaworks.algamoney.algamoney_api.repository.filter.PessoaFilter;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import com.algaworks.algamoney.algamoney_api.util.PaginationUtil;

public class PessoaRepositoryImpl implements PessoaRepositoryQuery {
    

    @PersistenceContext
    private EntityManager manager;


    // Implementação do método filtrar conforme necessário

    private Long total(PessoaFilter pessoaFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Pessoa> root = criteria.from(Pessoa.class);

        Predicate[] predicates = CriarRestricoes(pessoaFilter, builder, root);
        criteria.where(predicates);
        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }


    private Predicate[] CriarRestricoes(PessoaFilter pessoaFilter, CriteriaBuilder builder, Root<Pessoa> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!ObjectUtils.isEmpty(pessoaFilter.getNome())) {
            predicates.add(builder.like(
                builder.lower(root.get("nome")),
                "%" + pessoaFilter.getNome().toLowerCase() + "%"
            ));
        }

        if (pessoaFilter.getAtivo() != null) {
            predicates.add(builder.equal(root.get("ativo"), pessoaFilter.getAtivo()));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    @Override
    public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Pessoa> criteria = builder.createQuery(Pessoa.class);
        Root<Pessoa> root = criteria.from(Pessoa.class);


        Predicate[] predicates = CriarRestricoes(pessoaFilter, builder, root);
        criteria.where(predicates);

        TypedQuery<Pessoa> query = manager.createQuery(criteria);

        PaginationUtil.adicionarRestricoesDePaginacao(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, total(pessoaFilter));

    }
}
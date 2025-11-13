package com.algaworks.algamoney.algamoney_api.repository.lancamento;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.algaworks.algamoney.algamoney_api.model.Lancamento;
import com.algaworks.algamoney.algamoney_api.repository.filter.LancamentoFilter;
import com.algaworks.algamoney.algamoney_api.repository.projection.ResumoLancamentos;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
public class LancamentoRepositoryImpl implements LancamentoRespositoryQuery {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        // criar as restrições
        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);
        
        TypedQuery<Lancamento> query = manager.createQuery(criteria);
        
        adicionarRestricoesDePaginacao(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
    }
    
    
    @Override
    public Page<ResumoLancamentos> resumir(LancamentoFilter lancamentoFilter, Pageable pageable) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<ResumoLancamentos> criteria = builder.createQuery(ResumoLancamentos.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        // Converter o enum TipoLancamento para String manualmente
        jakarta.persistence.criteria.Expression<String> tipoString = 
            builder.<String>selectCase()
                .when(builder.equal(root.get("tipo"), com.algaworks.algamoney.algamoney_api.model.TipoLancamento.RECEITA), "RECEITA")
                .otherwise("DESPESA");

        // selecionar os campos para a projeção
        criteria.select(builder.construct(ResumoLancamentos.class,
            root.get("codigo"),
            root.get("descricao"),
            tipoString,
            root.get("valor"),
            root.get("categoria").get("nome"),
            root.get("pessoa").get("nome")
        ));

        // criar as restrições
        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);
        
        TypedQuery<ResumoLancamentos> query = manager.createQuery(criteria);
        
        adicionarRestricoesDePaginacao(query, pageable);
        return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
    }
    
    
    private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
    Root<Lancamento> root) {
        List<Predicate> predicates = new ArrayList<>();
        
        // where descricao like '%descricao%'
        if (!ObjectUtils.isEmpty(lancamentoFilter.getDescricao())) {
            predicates.add(builder.like(
                builder.lower(root.get("descricao")),
                "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"
            ));
        }

        // where data_vencimento >= dataVencimentoDe
        if (lancamentoFilter.getDataVencimentoDe() != null) {
            predicates.add(
                builder.greaterThanOrEqualTo(root.<java.time.LocalDate>get("data_vencimento"), 
                lancamentoFilter.getDataVencimentoDe())
            );
        }
        // where data_vencimento <= dataVencimentoAte
        if (lancamentoFilter.getDataVencimentoAte() != null) {
            predicates.add(
                builder.lessThanOrEqualTo(root.<java.time.LocalDate>get("data_vencimento"), 
                lancamentoFilter.getDataVencimentoAte())
            );
        }        
        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private <T> void adicionarRestricoesDePaginacao(TypedQuery<T> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(LancamentoFilter lancamentoFilter) {
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Lancamento> root = criteria.from(Lancamento.class);

        Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
        criteria.where(predicates);
        //select count(*) from lancamentos
        criteria.select(builder.count(root));
        return manager.createQuery(criteria).getSingleResult();
    }




    
    
}

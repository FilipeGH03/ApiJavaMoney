package com.algaworks.algamoney.algamoney_api.repository.pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algamoney.algamoney_api.model.Pessoa;
import com.algaworks.algamoney.algamoney_api.repository.filter.PessoaFilter;

public interface PessoaRepositoryQuery {
    

    
    public Page<Pessoa> filtrar(PessoaFilter pessoaFilter, Pageable pageable);
}

package com.algaworks.algamoney.algamoney_api.repository.lancamento;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.algaworks.algamoney.algamoney_api.model.Lancamento;
import com.algaworks.algamoney.algamoney_api.repository.filter.LancamentoFilter;
import com.algaworks.algamoney.algamoney_api.repository.projection.ResumoLancamentos;

public interface LancamentoRespositoryQuery {

    public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
    public Page<ResumoLancamentos> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
}

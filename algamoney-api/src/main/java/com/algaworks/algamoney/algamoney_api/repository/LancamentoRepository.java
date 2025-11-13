package com.algaworks.algamoney.algamoney_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algamoney.algamoney_api.model.Lancamento;
import com.algaworks.algamoney.algamoney_api.repository.lancamento.LancamentoRespositoryQuery;

@Repository
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>, LancamentoRespositoryQuery {
	
}

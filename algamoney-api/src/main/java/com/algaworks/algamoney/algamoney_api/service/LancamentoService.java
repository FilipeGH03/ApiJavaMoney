package com.algaworks.algamoney.algamoney_api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.algamoney_api.model.Lancamento;
import com.algaworks.algamoney.algamoney_api.model.Pessoa;
import com.algaworks.algamoney.algamoney_api.repository.LancamentoRepository;
import com.algaworks.algamoney.algamoney_api.repository.PessoaRepository;
import com.algaworks.algamoney.algamoney_api.service.exception.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;
    
    public Lancamento salvarLancamento(Lancamento lancamento) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(lancamento.getPessoa().getCodigo());
        if (pessoa.isEmpty() || !pessoa.get().getAtivo()) {
            throw new PessoaInexistenteOuInativaException();
        }
        return lancamentoRepository.save(lancamento);
    }

    public Lancamento atualizarLancamento(Long codigo, Lancamento lancamento) {
        Lancamento lancamentoExistente = getLancamentoExistente(codigo);
        lancamento.setCodigo(codigo);
        Lancamento lancamentoAtualizado = lancamentoRepository.save(lancamento);
        if (!lancamentoExistente.getPessoa().getCodigo().equals(lancamentoAtualizado.getPessoa().getCodigo())) {
            Pessoa pessoa = pessoaRepository.findById(lancamentoAtualizado.getPessoa().getCodigo()).orElse(null);
            if (pessoa == null || !pessoa.getAtivo()) {
                throw new PessoaInexistenteOuInativaException(); 
            }
        }

        return lancamentoAtualizado;
    }

    private Lancamento getLancamentoExistente(Long codigo) {
        Lancamento lancamentoExistente = lancamentoRepository.findById(codigo).orElse(null);
        if (lancamentoExistente == null) {
            throw new org.springframework.dao.EmptyResultDataAccessException(1);
        }
        return lancamentoExistente;
    }
    
}

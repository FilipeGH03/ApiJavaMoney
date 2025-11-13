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
    
}

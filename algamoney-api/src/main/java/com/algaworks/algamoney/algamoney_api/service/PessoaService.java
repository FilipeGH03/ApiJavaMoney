package com.algaworks.algamoney.algamoney_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algamoney.algamoney_api.model.Pessoa;
import com.algaworks.algamoney.algamoney_api.repository.PessoaRepository;

@Service
public class PessoaService {
    @Autowired PessoaRepository pessoaRepository;

    public Pessoa pessoaAtualizar(Long codigo, Pessoa pessoa){
        getPessoaExistente(codigo);
        pessoa.setCodigo(codigo);
        Pessoa pessoaAtualizada = pessoaRepository.save(pessoa);
        return pessoaAtualizada;
    }

    public Pessoa atualizarPropriedadeAtivo(Long codigo, Boolean ativo) {
        Pessoa pessoaExistente = getPessoaExistente(codigo);
        pessoaExistente.setAtivo(ativo);
        pessoaRepository.save(pessoaExistente);
        return pessoaExistente;
    }

    public Pessoa getPessoaExistente(Long codigo) {
        Pessoa pessoaExistente = pessoaRepository.findById(codigo).orElse(null);
        if (pessoaExistente == null) {
            throw new org.springframework.dao.EmptyResultDataAccessException(1);
        }
        return pessoaExistente;
    }}
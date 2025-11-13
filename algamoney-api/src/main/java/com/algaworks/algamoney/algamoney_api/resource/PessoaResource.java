package com.algaworks.algamoney.algamoney_api.resource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.algamoney.algamoney_api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.algamoney_api.model.Pessoa;
import com.algaworks.algamoney.algamoney_api.repository.PessoaRepository;
import com.algaworks.algamoney.algamoney_api.repository.filter.PessoaFilter;
import com.algaworks.algamoney.algamoney_api.service.PessoaService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@RestController
@RequestMapping("/pessoas")

public class PessoaResource {
    @Autowired
    private PessoaRepository pessoaRepository;


    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private PessoaService pessoaService;



    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA')")
    public Page<Pessoa> pesquisar(Pageable pageable, PessoaFilter pessoaFilter){
        return pessoaRepository.filtrar(pessoaFilter, pageable);
    }
    

    
    

    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA')")
    public ResponseEntity<Pessoa> criar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
        Pessoa pessoa_salva = pessoaRepository.save(pessoa);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoa_salva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoa_salva);
    }

    @GetMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_PESSOA')")
    public ResponseEntity<Pessoa> buscarPeloCodigo(@PathVariable Long codigo){
        Pessoa pessoa = pessoaRepository.findById(codigo).orElse(null);
        if (!pessoaRepository.existsById(codigo)) {
            throw new org.springframework.dao.EmptyResultDataAccessException(1);
        }
        return pessoa == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(pessoa);
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ROLE_REMOVER_PESSOA')")
    public void remover(@PathVariable Long codigo) {
        // Verifica se a pessoa existe antes de deletar
        if (!pessoaRepository.existsById(codigo)) {
            throw new org.springframework.dao.EmptyResultDataAccessException(1);
        }
        pessoaRepository.deleteById(codigo);
    }
    


    @PutMapping("/{codigo}")
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA')")
    public ResponseEntity<Pessoa> atualizar(@PathVariable Long codigo, @Valid @RequestBody Pessoa pessoa) {
        Pessoa pessoaAtualizada = pessoaService.pessoaAtualizar(codigo, pessoa);
        return ResponseEntity.ok(pessoaAtualizada);
    }

    @PutMapping("/{codigo}/ativo")
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_PESSOA')")
    public ResponseEntity<Pessoa> atualizarStatus(@PathVariable Long codigo, @RequestBody Boolean ativo) {
        return ResponseEntity.ok(pessoaService.atualizarPropriedadeAtivo(codigo, ativo));
    }
}
 
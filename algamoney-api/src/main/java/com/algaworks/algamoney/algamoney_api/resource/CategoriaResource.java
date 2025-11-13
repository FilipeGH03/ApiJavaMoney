package com.algaworks.algamoney.algamoney_api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.algamoney.algamoney_api.event.RecursoCriadoEvent;
import com.algaworks.algamoney.algamoney_api.model.Categoria;
import com.algaworks.algamoney.algamoney_api.repository.CategoriaRepository;

import jakarta.validation.Valid;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/categorias")

public class CategoriaResource {

    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
    public List<Categoria> listar(){
        return categoriaRepository.findAll();
    }

    
    @Autowired
    private ApplicationEventPublisher publisher;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA')")
    public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
        Categoria categoria_salva = categoriaRepository.save(categoria); 
        publisher.publishEvent(new RecursoCriadoEvent(this, response, categoria_salva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoria_salva);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable long codigo){
        Categoria categoria = categoriaRepository.findById(codigo).orElse(null);
        return categoria == null ? ResponseEntity.notFound().build() : ResponseEntity.ok(categoria);
    }
} 
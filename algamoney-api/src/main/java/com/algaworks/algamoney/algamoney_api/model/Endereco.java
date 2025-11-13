package com.algaworks.algamoney.algamoney_api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;


@Embeddable
public class Endereco {
    
    @Column(name = "endereco_logradouro")
    @Size(min = 3, max = 150)
    private String logradouro;

    @Column(name = "endereco_numero")
    @Size(min = 1, max = 20)
    private String numero;

    @Column(name = "endereco_complemento")
    @Size(max = 50)
    private String complemento;

    @Column(name = "endereco_bairro")
    @Size(min = 3, max = 50)
    private String bairro;

    @Column(name = "endereco_cidade")
    @Size(min = 3, max = 50)
    private String cidade;

    @Column(name = "endereco_estado")
    @Size(min = 2, max = 2)
    private String estado;

    @Column(name = "endereco_cep")
    @Size(min = 8, max = 10)
    private String cep;
    
    public String getLogradouro() {
        return logradouro;
    }
    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getComplemento() {
        return complemento;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
    public String getBairro() {
        return bairro;
    }
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    public String getCidade() {
        return cidade;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }
    
    public void setCep(String cep) {
        this.cep = cep;
    }
}

package com.algaworks.algamoney.algamoney_api.repository.projection;

import java.math.BigDecimal;

public class ResumoLancamentos {
    private Long codigo;
    private String descricao;
    private String tipo;
    private BigDecimal valor;
    private String categoria;
    private String pessoa;

    public ResumoLancamentos(Long codigo, String descricao, String tipo, BigDecimal valor, String categoria,
            String pessoa) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.tipo = tipo;
        this.valor = valor;
        this.categoria = categoria;
        this.pessoa = pessoa;
    }

    public Long getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getPessoa() {
        return pessoa;
    }

}

package com.algaworks.algamoney.algamoney_api.util;

import org.springframework.data.domain.Pageable;

import jakarta.persistence.TypedQuery;

/**
 * Utilitário para aplicar paginação em consultas JPA.
 */
public final class PaginationUtil {

    private PaginationUtil() {
        // classe utilitária
    }

    /**
     * Aplica restrições de paginação em um {@link TypedQuery} usando um {@link Pageable}.
     * Protege contra valores nulos e números negativos.
     *
     * @param query   TypedQuery onde serão aplicados os limites
     * @param pageable objeto Pageable (pageNumber zero-based, pageSize > 0)
     */
    public static <T> void adicionarRestricoesDePaginacao(TypedQuery<T> query, Pageable pageable) {
        if (query == null || pageable == null) {
            return;
        }

        int paginaAtual = Math.max(0, pageable.getPageNumber());
        int totalRegistrosPorPagina = Math.max(1, pageable.getPageSize());
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }
}

-- Adiciona coluna endereco_cep se não existir
ALTER TABLE pessoa
    ADD COLUMN IF NOT EXISTS endereco_cep VARCHAR(10);

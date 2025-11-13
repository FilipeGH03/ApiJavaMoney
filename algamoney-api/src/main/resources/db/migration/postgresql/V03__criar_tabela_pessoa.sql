CREATE TABLE IF NOT EXISTS pessoa (
    codigo BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    ativo BOOLEAN NOT NULL,
    endereco_logradouro VARCHAR(150),
    endereco_numero VARCHAR(20),
    endereco_complemento VARCHAR(50),
    endereco_bairro VARCHAR(50),
    endereco_cidade VARCHAR(50),
    endereco_estado VARCHAR(2)
);

INSERT INTO pessoa (nome, ativo, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, endereco_cidade, endereco_estado) VALUES
('João Silva', true, 'Rua A', '123', 'Apto 1', 'Centro', 'São Paulo', 'SP'),
('Maria Santos', true, 'Rua B', '456', 'Casa 2', 'Jardins', 'Rio de Janeiro', 'RJ'),
('Pedro Oliveira', false, 'Av C', '789', NULL, 'Copacabana', 'Rio de Janeiro', 'RJ');

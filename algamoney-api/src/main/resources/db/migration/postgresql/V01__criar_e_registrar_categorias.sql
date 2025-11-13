CREATE TABLE categoria (
    codigo BIGSERIAL PRIMARY KEY,
    nome VARCHAR(50) NOT NULL
);

INSERT INTO categoria (nome) VALUES
('Alimentação'),
('Saúde'),
('Moradia'),
('Transporte'),
('Lazer'),
('Educação'),
('Impostos');

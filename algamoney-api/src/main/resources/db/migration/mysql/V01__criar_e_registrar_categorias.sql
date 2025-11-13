CREATE TABLE categoria (
    codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO categoria (nome) VALUES
('Alimentação'),
('Saúde'),
('Moradia'),
('Transporte'),
('Lazer'),
('Educação'),
('Impostos');

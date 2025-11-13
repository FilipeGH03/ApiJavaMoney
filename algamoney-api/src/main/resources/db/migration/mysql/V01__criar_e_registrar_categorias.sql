CREATE TABLE IF NOT EXISTS categoria (
    codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insere apenas se o registro ainda não existir (evita duplicação em ambientes dev)
INSERT INTO categoria (nome)
SELECT 'Alimentação' WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'Alimentação');
INSERT INTO categoria (nome)
SELECT 'Saúde' WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'Saúde');
INSERT INTO categoria (nome)
SELECT 'Moradia' WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'Moradia');
INSERT INTO categoria (nome)
SELECT 'Transporte' WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'Transporte');
INSERT INTO categoria (nome)
SELECT 'Lazer' WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'Lazer');
INSERT INTO categoria (nome)
SELECT 'Educação' WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'Educação');
INSERT INTO categoria (nome)
SELECT 'Impostos' WHERE NOT EXISTS (SELECT 1 FROM categoria WHERE nome = 'Impostos');

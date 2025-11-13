CREATE TABLE IF NOT EXISTS lancamento (
    codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(100) NOT NULL,
    data_vencimento DATE NOT NULL,
    data_pagamento DATE,
    valor DECIMAL(15,2) NOT NULL,
    observacao VARCHAR(200),
    tipo VARCHAR(10) NOT NULL,
    codigo_pessoa BIGINT NOT NULL,
    codigo_categoria BIGINT NOT NULL,
    FOREIGN KEY (codigo_pessoa) REFERENCES pessoa(codigo),
    FOREIGN KEY (codigo_categoria) REFERENCES categoria(codigo)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO lancamento (descricao, data_vencimento, data_pagamento, valor, observacao, tipo, codigo_pessoa, codigo_categoria) VALUES
('Aluguel do apartamento', '2024-07-05', NULL, 1500.00, 'Pagamento mensal do aluguel', 'DESPESA', 1, 1),
('Salário de julho', '2024-07-31', '2024-07-31', 5000.00, 'Recebimento do salário mensal', 'RECEITA', 2, 2),
('Conta de luz', '2024-07-10', NULL, 200.00, 'Pagamento da conta de energia elétrica', 'DESPESA', 1, 3);

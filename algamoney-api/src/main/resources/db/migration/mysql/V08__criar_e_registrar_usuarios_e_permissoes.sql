CREATE TABLE usuario (
    codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE permissao (
    codigo BIGINT PRIMARY KEY AUTO_INCREMENT,
    descricao VARCHAR(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE usuario_permissao (
    usuario_codigo BIGINT NOT NULL,
    permissao_codigo BIGINT NOT NULL,
    PRIMARY KEY (usuario_codigo, permissao_codigo),
    FOREIGN KEY (usuario_codigo) REFERENCES usuario(codigo) ON DELETE CASCADE,
    FOREIGN KEY (permissao_codigo) REFERENCES permissao(codigo) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

INSERT INTO usuario (codigo, nome, email, senha) VALUES (1, 'Administrador', 'admin@algamoney.com', '$2a$10$X607ZPhQ4EgGNaYKt3n4SONjIv9zc.VMWdEuhCuba7oLAL5IvcL5.');
INSERT INTO usuario (codigo, nome, email, senha) VALUES (2, 'Maria Silva', 'maria@algamoney.com', '$2a$10$Zc3w6HyuPOPXamaMhh.PQOXvDnEsadztbfi6/RyZWJDzimE8WQjaq');

INSERT INTO permissao (codigo, descricao) VALUES (1, 'ROLE_CADASTRAR_CATEGORIA');
INSERT INTO permissao (codigo, descricao) VALUES (2, 'ROLE_PESQUISAR_CATEGORIA');
INSERT INTO permissao (codigo, descricao) VALUES (3, 'ROLE_CADASTRAR_PESSOA');
INSERT INTO permissao (codigo, descricao) VALUES (4, 'ROLE_REMOVER_PESSOA');
INSERT INTO permissao (codigo, descricao) VALUES (5, 'ROLE_PESQUISAR_PESSOA');
INSERT INTO permissao (codigo, descricao) VALUES (6, 'ROLE_CADASTRAR_LANCAMENTO');
INSERT INTO permissao (codigo, descricao) VALUES (7, 'ROLE_REMOVER_LANCAMENTO');
INSERT INTO permissao (codigo, descricao) VALUES (8, 'ROLE_PESQUISAR_LANCAMENTO');

-- admin
INSERT INTO usuario_permissao (usuario_codigo, permissao_codigo) VALUES (1, 1);
INSERT INTO usuario_permissao (usuario_codigo, permissao_codigo) VALUES (1, 2);
INSERT INTO usuario_permissao (usuario_codigo, permissao_codigo) VALUES (1, 3);
INSERT INTO usuario_permissao (usuario_codigo, permissao_codigo) VALUES (1, 4);
INSERT INTO usuario_permissao (usuario_codigo, permissao_codigo) VALUES (1, 5);
INSERT INTO usuario_permissao (usuario_codigo, permissao_codigo) VALUES (1, 6);
INSERT INTO usuario_permissao (usuario_codigo, permissao_codigo) VALUES (1, 7);
INSERT INTO usuario_permissao (usuario_codigo, permissao_codigo) VALUES (1, 8);

-- maria
INSERT INTO usuario_permissao (usuario_codigo, permissao_codigo) VALUES (2, 2);
INSERT INTO usuario_permissao (usuario_codigo, permissao_codigo) VALUES (2, 5);
INSERT INTO usuario_permissao (usuario_codigo, permissao_codigo) VALUES (2, 8);

DROP DATABASE IF EXISTS cliente;

CREATE DATABASE cliente;

\c cliente;

CREATE TABLE cliente (
    id bigint PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(50) NOT NULL,
    senha VARCHAR(1000) NOT NULL,
    cpf VARCHAR(20) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    estado INT NOT NULL,
    cidade INT NOT NULL,
    cep VARCHAR(20) NOT NULL,
    rua VARCHAR(255) NOT NULL,
    numero INT NOT NULL,
    complemento VARCHAR(255),
    ativo BOOLEAN NOT NULL,
    cargo VARCHAR(20) NOT NULL
);

INSERT INTO cliente (id,nome, email, senha, cpf, telefone, estado, cidade, cep, rua, numero, complemento, ativo, cargo) VALUES
    (1,'CLIENTE 1', 'cliente1@cliente.com', '059bd4278d57c805cd939d76f30b26ef80d420c2a0662e88e2e75f8002bd62c7cd90bf3144d4d6cae3d979358360ede1d0dca9d2b66438acf59fdb30dcb9631f', '765.141.530-20', '(41) 99788-1313', 25, 3426, '88.465-450', 'Rua do Cliente 1', 15, null, TRUE, 'Cliente'),
    (2,'CLIENTE 2', 'cliente2@cliente.com', 'fc51872ca532fac69d0ce2f73dc2f25cc2687504e1954b376a76dcde2f0429d830e6e58b49056724e8a9664b09c5f3ef7df492559d406dd123651e7a2437fd64', '246.905.310-21', '(41) 97894-2136', 25, 3426, '87.422-220', 'Rua do Cliente 2', 10, null, TRUE, 'Cliente');
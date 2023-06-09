DROP DATABASE IF EXISTS gerente;

CREATE DATABASE gerente;

\c gerente;

CREATE TABLE gerente (
	id BIGINT PRIMARY KEY,
	nome VARCHAR(255) NOT NULL,
	email VARCHAR(50) NOT NULL,
	senha VARCHAR(1000) NOT NULL,
	cpf VARCHAR(20) NOT NULL,
	telefone VARCHAR(20) NOT NULL,
	cargo VARCHAR(20) NOT NULL
);

INSERT INTO gerente (id,nome, email, senha, cpf, telefone, cargo) VALUES
	(6,'GERENTE 1', 'gerente1@gerente.com', '5b0f380c06da974c8591432bb24fc713f2a019098b161112c52eca0db5ecd8d9833319330308bab56cd98eb253b0891f4c53deddc2c4857dd958571c3d564dee', '765.141.530-20', '(41) 99788-1313', 'Gerente'),
	(7,'GERENTE 2', 'gerente2@gerente.com', 'b18f6b920da857f0e3858fc59ee084686c5175a9598f19f38f2ae3017781d1ef4447ef07bae613f13fd8b597e1e51ed9e85fe437e79beb15de037b7b02e086b2', '865.229.210-81', '(41) 99761-1414', 'Gerente');

-- Senhas
-- Gerente1.
-- Gerente2.
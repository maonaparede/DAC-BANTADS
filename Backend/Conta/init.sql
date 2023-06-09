DROP DATABASE IF EXISTS contar;

CREATE DATABASE contar;

\c contar;

CREATE TABLE transacao (
	id_transacao BIGINT PRIMARY KEY,
	id_cliente BIGINT,
	tipo_transacao INTEGER,
	data_transacao DATE,
	saldo FLOAT,
	valor_transacao FLOAT,
	destinatario INTEGER
);

-- INSERT INTO transacao(id_cliente, tipo_transacao, data_transacao, saldo, valor_transacao, destinatario) VALUES
-- 	(1, 1, '2023-01-04',  3000, 3000, 0),
-- 	(1, 1, '2023-01-05', 500, 3500, 0),
-- 	(1, 2, '2023-01-06', 100, 3400, 0);

CREATE TABLE conta (
	id_conta SERIAL PRIMARY KEY,
	id_usuario BIGINT ,
	data DATE,
	ativo BOOLEAN,
	saldo FLOAT,
	id_gerente INTEGER,
	salario FLOAT,
	rejeitado_motivo VARCHAR(60),
	rejeitado_data DATE
);

INSERT INTO conta(id_usuario, data, ativo, saldo, id_gerente, salario, rejeitado_motivo, rejeitado_data) VALUES
 	(1, '2023-01-04', true, 3000, 6, 1500, null, null),
 	(2, '2023-01-05', true, 500, 6, 3000, null, null),
 	(3, '2023-01-06', true, 100, 7, 2000, null, null);

DROP DATABASE IF EXISTS contacud;

CREATE DATABASE contacud;



\c contacud;

CREATE TABLE transacao (
	id_transacao BIGINT PRIMARY KEY,
	id_cliente BIGINT,
	tipo_transacao INTEGER,
	data_transacao DATE,
	saldo FLOAT,
	valor_transacao FLOAT,
	destinatario INTEGER
);

-- INSERT INTO transacao(id_cliente, tipo_transacao, data_transacao, saldo, valor_transacao, destinatario) VALUES
-- 	(1, 1, '2023-01-04',  3000, 3000, 0),
-- 	(1, 1, '2023-01-05', 500, 3500, 0),
-- 	(1, 2, '2023-01-06', 100, 3400, 0);

CREATE TABLE conta (
	id_conta SERIAL PRIMARY KEY,
	id_usuario BIGINT ,
	data DATE,
	ativo BOOLEAN,
	saldo FLOAT,
	id_gerente INTEGER,
	salario FLOAT,
	rejeitado_motivo VARCHAR(60),
	rejeitado_data DATE
);


INSERT INTO conta(id_usuario, data, ativo, saldo, id_gerente, salario, rejeitado_motivo, rejeitado_data) VALUES
 	(1, '2023-01-04', true, 3000, 6, 1500, null, null),
 	(2, '2023-01-05', true, 500, 6, 3000, null, null),
 	(3, '2023-01-06', true, 100, 7, 2000, null, null);
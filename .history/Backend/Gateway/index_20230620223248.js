const app = require('express')();
const httpProxy = require('express-http-proxy');
var http = require('http');
var logger = require('morgan');

const port = 5000;

const express = require('express')
const helmet = require('helmet');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
const cors = require('cors');
require('dotenv').config();

const clienteServiceProxy = httpProxy(process.env.HOST_CLIENTE);
const gerenteServiceProxy = httpProxy(process.env.HOST_GERENTE);
const authServiceProxy = httpProxy(process.env.HOST_AUTH);


const clientServiceProxy = httpProxy(process.env.CLIENTES_API);
const managerServiceProxy = httpProxy(process.env.HOST_AUTH);
const authServiceProxy = httpProxy(process.env.AUTH_API);
const sagaServiceProxy = httpProxy(process.env.ORQUESTRADOR_API);
const adminServiceProxy = httpProxy(process.env.ADMIN_API);


// CLIENTES
app.get('/clientes', (req, res, next) => clientServiceProxy(req, res, next));

app.get(`/clientes/busca/:cpf`, (req, res, next) => clientServiceProxy(req, res, next));

app.post(`/clientes`, (req, res, next) => clientServiceProxy(req, res, next));

app.put(`/conta/deposit/:cpf`, (req, res, next) => adminServiceProxy(req, res, next));
app.put(`/conta/transfer/:cpfOrigem/:cpfDestino`, (req, res, next) => adminServiceProxy(req, res, next));

app.get(`/conta/cliente/situacao/:cpf`, (req, res, next) => adminServiceProxy(req, res, next));
app.get(`/conta/cliente/inicial/:cpf`, (req, res, next) => adminServiceProxy(req, res, next));

// MANAGER
app.get('/conta/cliente/esperando', (req, res, next) => adminServiceProxy(req, res, next));
app.get('/conta/cliente/top', (req, res, next) => adminServiceProxy(req, res, next));
app.put(`/conta/aprovar/:cpf`, (req, res, next) => adminServiceProxy(req, res, next));

// ADMIN
app.get('/conta/admin', (req, res, next) => adminServiceProxy(req, res, next));
app.get('/conta/cliente', (req, res, next) => adminServiceProxy(req, res, next));
app.delete(`/delete/:cpf`, (req, res, next) => orqServiceProxy(req, res, next))


app.get('/gerentes', (req, res, next) => managerServiceProxy(req, res, next));
app.get('/gerentes/busca/:cpf', (req, res, next) => managerServiceProxy(req, res, next));



// LOGIN 
app.post(`/auth/auth/login`, (req, res, next) => authServiceProxy(req, res, next));


// ORQUESTRADOR
app.post(`/criar-gerente`, (req, res, next) => orqServiceProxy(req, res, next));
app.post(`/criar-cliente`, (req, res, next) => orqServiceProxy(req, res, next));
app.put(`/manager/:cpf`, (req, res, next) => orqServiceProxy(req, res, next));


// PUBLICACAO
app.use(logger('dev'));
app.use(cors());
app.use(helmet());
app.use(express.json());
app.use(express.urlencoded({ extended: false}));
app.use(cookieParser());

var server = http.createServer(app);
server.listen(port);
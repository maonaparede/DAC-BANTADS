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

const clienteServiceProxy = httpProxy("http://localhost:5001");
const gerenteServiceProxy = httpProxy(process.env.HOST_GERENTE);
const authServiceProxy = httpProxy(process.env.HOST_AUTH);
const sagaServiceProxy = httpProxy("http://localhost:5005");

app.post(`/api/auth/login`, (req, res, next) => authServiceProxy(req, res, next));

app.post(`/api/saga/cli/auto`, (req, res, next) => sagaServiceProxy(req, res, next));


// PUBLICACAO
app.use(logger('dev'));
app.use(cors());
app.use(helmet());
app.use(express.json());
app.use(express.urlencoded({ extended: false}));
app.use(cookieParser());

var server = http.createServer(app);
server.listen(port);
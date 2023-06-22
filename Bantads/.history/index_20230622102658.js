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

const authServiceProxy = httpProxy("http://localhost:5001");
const clienteServiceProxy = httpProxy("http://localhost:5002");
const contaServiceProxy = httpProxy("http://localhost:5003");
const gerenteServiceProxy = httpProxy("http://localhost:5004");
const sagaServiceProxy = httpProxy("http://localhost:5005");

app.get('/', (req, res) => { 
    res.statusCode = 200;
    res.setHeader('Content-Type', 'text/plain');
    res.end('Hello, World!\n');
});

//R2
//app.post(`/api/auth/login`, (req, res, next) => authServiceProxy(req, res, next));

app.post(`/api/auth/login`, (req, res, next) =>{
    res.body = req.body;
    res.statusCode = 200
});

//R1
//app.post(`/api/saga/cli/auto`, (req, res, next) => sagaServiceProxy(req, res, next));
app.post(`/api/saga/cli/auto`, (req, res, next) =>{
    res.body = req.body;
    res.statusCode = 200
} );

//R10
app.put(`/api/saga/cli/apro/:idCliente`, (req, res, next) => sagaServiceProxy(req, res, next));


app.put(`/api/cli/cli/email/:email`, (req, res, next) => 
/*clienteServiceProxy(req, res, next) =>*/ {
    const { id } = res;
    res.idCliente = id;
    next();
});

// PUBLICACAO
app.use(logger('dev'));
app.use(cors());
app.use(helmet());
app.use(express.json());
app.use(express.urlencoded({ extended: false}));
app.use(cookieParser());

var server = http.createServer(app);
server.listen(port);
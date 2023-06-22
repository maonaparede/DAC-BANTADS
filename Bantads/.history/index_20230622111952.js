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

app.post(`/api/auth/login`, (req, res, next) => {
    res.body = req.body;
    res.statusCode = 200
 
});


app.post(`/api/saga/cli/auto`, (req, res, next) => {
    res.body = req.body;
    res.statusCode = 200
});

//R1
//app.post(`/api/saga/cli/auto`, (req, res, next) => sagaServiceProxy(req, res, next));
app.post(`/api/auth/login2`, async (req, res, next) => {
    res.body = req.body;
    res.statusCode = 200;
  
    let email = '';
    let tipoUser = '';
    let idCliente = null;
    let idConta = null;
    let idGerente = null;
  
    req.originalUrl = `/api/auth/login`;
  
    const authResponse = await new Promise((resolve) => {
      if (res.statusCode !== 200) {
        resolve(res);
      }
  
      res.body.email = res.body.email;
      res.body.tipo = 'C';
  
      const { email, tipo } = res.body;
      this.email = email;
      this.tipoUser = tipo;
  
      resolve();
    });
  
    if (tipoUser === 'G') {
      req.originalUrl = `/api/adm/ger/email/:email`;
  
      const gerenteResponse = await new Promise((resolve) => {
        res.body.id = 1;
        const { id } = res.body;
        this.idGerente = id;
        resolve();
      });
    } else if (tipoUser === 'C') {
      req.originalUrl = `/api/cli/cli/email/:email`;
  
      const clienteResponse = await new Promise((resolve) => {
        res.body.id = 1;
        const { id } = res.body;
        this.idCliente = id;
        resolve();
      });
  
      req.originalUrl = `/api/user/idCliente/:id`;
  
      const contaResponse = await new Promise((resolve) => {
        res.body.id = 1;
        const { id } = res.body;
        this.idCliente = id;
        resolve();
      });
    }
  
    const data = {
      email: this.email,
      tipoUser: this.tipoUser,
      idCliente: this.idCliente,
      idConta: this.idConta,
      idGerente: this.idGerente,
    };
  
    res.body = data;
  });
  

//R10
app.put(`/api/saga/cli/apro/:idCliente`, (req, res, next) => sagaServiceProxy(req, res, next));


app.put(`/api/cli/cli/email/:email`, (req, res, next) =>
/*clienteServiceProxy(req, res, next) =>*/ {
    //const { id } = res;
    const id = 1;
    res.idCliente = id;
    next();
});

// PUBLICACAO
app.use(logger('dev'));
app.use(cors());
app.use(helmet());
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());

var server = http.createServer(app);
server.listen(port);
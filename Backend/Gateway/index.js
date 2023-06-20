require('dotenv-safe').config();
const jwt = require('jsonwebtoken');
const express = require('express');
const { createProxyMiddleware } = require('http-proxy-middleware');
const cookieParser = require('cookie-parser');
const bodyParser = require('body-parser');
const logger = require('morgan');
const helmet = require('helmet');

const app = express();
const authServiceProxy = createProxyMiddleware({ target: 'http://localhost:5001', changeOrigin: true });
const clientServiceProxy = createProxyMiddleware({ target: 'http://localhost:5002', changeOrigin: true });
const accountServiceProxy = createProxyMiddleware({ target: 'http://localhost:5003', changeOrigin: true });
const managerServiceProxy = createProxyMiddleware({ target: 'http://localhost:5004', changeOrigin: true });
const sagaServiceProxy = createProxyMiddleware({ target: 'http://localhost:5005', changeOrigin: true });

const authUrl = '/api/auth';
const clientUrl = '/api/cli';
const contaSysUrl = '/api/sys/cli';
const contaUrl = '/api/user';
const gerenteUrl = '/api/adm';
const sagaUrl = '/api/saga';

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());
app.use(logger('dev'));
app.use(helmet());
app.use(cookieParser());

function verifyJWT(req, res, next) {
    const token = req.headers['x-access-token'];
    if (!token)
        return res.status(401).json({ auth: false, message: 'Token não fornecido.' });

    jwt.verify(token, process.env.SECRET, function (err, decoded) {
        if (err)
            return res
                .status(500)
                .json({ auth: false, message: 'Falha ao autenticar o token' });

        req.userId = decoded.id;
        next();
    });
}

// AUTH
//R2
app.get('/login', (req, res, next) => {
    req.method = 'GET';
    email = req.email;
    pass = req.pass;
    req.url = `${authUrl}/${email}/${pass}`;
    next();
}, authServiceProxy);

app.get('/EmailExists', (req, res, next) => {
    req.method = 'GET';
    email = req.email;
    req.url = `${authUrl}/${email}`;
    next();
}, authServiceProxy);

// CLIENT
    //Usar com apiCompose - caso precise
    //R4 - use pra pegar os dados do cliente
app.get('/getClientById', (req, res, next) => {
    req.method = 'GET';
    idCliente = req.id;
    req.url = `${clientUrl}/cli/${idCliente}`;
    next();
}, clientServiceProxy);

// ACCOUNT

//R13 - Com Api compose
app.get('/search', (req, res, next) => {
    req.method = 'GET';
    userIdConta = req.userId;
    req.url = `${contaSysUrl}/ger/${userIdConta}`;
    next();
}, accountServiceProxy);

//R9 - Clientes que precisam ser apr ou rep pelo gerente
app.get('/waiting', (req, res, next) => {
    req.method = 'GET';
    gerenteId = req.gerenteId;
    req.url = `${contaSysUrl}/ger/esp/${gerenteId}`;
    next();
}, accountServiceProxy);

//R12
app.get('/clientsManager', (req, res, next) => {
    req.method = 'GET';
    gerenteId = req.gerenteId;
    req.url = `${contaSysUrl}/ger/myCli/${gerenteId}`;
    next();
}, accountServiceProxy);

//R14
app.get('/BestClientsManager', (req, res, next) => {
    req.method = 'GET';
    gerenteId = req.gerenteId;
    req.url = `${contaSysUrl}/ger/bestCli/${gerenteId}`;
    next();
}, accountServiceProxy);

//R16
app.get('/RelatorioClientAdm', (req, res, next) => {
    req.method = 'GET';
    req.url = `${contaSysUrl}/adm/RelCli`;
    next();
}, accountServiceProxy);

//R12
app.get('/AllClientManager', (req, res, next) => {
    req.method = 'GET';
    gerenteId = req.gerenteId;
    req.url = `${contaSysUrl}/ger/allCli/${gerenteId}`;
    next();
}, accountServiceProxy);

//Operações do cliente

//R13 ou R3
app.get('/userInfo', (req, res, next) => {
    req.method = 'GET';
    userId = req.userId;
    req.url = `${contaUrl}/${userId}`;
    next();
}, accountServiceProxy);

//R8
app.get('/extrato', (req, res, next) => {
    req.method = 'GET';
    userIdConta = req.userId;
    start = req.start;
    end = req.end;
    req.url = `${contaUrl}/${userIdConta}/${start}/${end}`;
    next();
}, accountServiceProxy);

//R5
app.post('/deposito', (req, res, next) => {
    req.method = 'POST';
    req.url = `${contaUrl}/op`;
    next();
}, accountServiceProxy);
//R6
app.post('/saque', (req, res, next) => {
    req.method = 'POST';
    req.url = `${contaUrl}/op`;
    next();
}, accountServiceProxy);
//R7
app.post('/transferencia', (req, res, next) => {
    req.method = 'POST';
    req.url = `${contaUrl}/op`;
    next();
}, accountServiceProxy);


// MANAGER - modulo gerente
//R15
app.get('/findAllMainScreenGerente', (req, res, next) => {
    req.method = 'GET';
    req.url = `${gerenteUrl}/adm`;
    next();
}, managerServiceProxy);
//R20 para consulta
app.get('/findManagerById', (req, res, next) => {
    req.method = 'GET';
    userId = req.userId;
    req.url = `${gerenteUrl}/ger/${userId}`;
    next();
}, managerServiceProxy);

//R19
app.get('/findAllGerente', (req, res, next) => {
    req.method = 'GET';
    req.url = `${gerenteUrl}/ger/all`;
    next();
}, managerServiceProxy);


// SAGA

//Cliente
//R11
app.put('/rejeitaCliente', (req, res, next) => {
    req.method = 'PUT';
    req.url = `${sagaUrl}/cli/rej`;
    next();
}, sagaServiceProxy);

//R18 - Precisa ser o idCliente do Modulo cliente
app.put('/aprovarCliente', (req, res, next) => {
    req.method = 'PUT';
    userIdCliente = req.userId;
    req.url = `${sagaUrl}/cli/apro/${userIdCliente}`;
    next();
}, sagaServiceProxy);

//R4
app.put('/updateProfile', (req, res, next) => {
    req.method = 'PUT';
    req.url = `${sagaUrl}/cli/update`;
    next();
}, sagaServiceProxy);

//R1
app.post('/signup', (req, res, next) => {
    req.method = 'POST';
    req.url = `${sagaUrl}/cli/auto`;
    next();
}, sagaServiceProxy);

//Gerente
//R20
app.put('/alteraGerente', (req, res, next) => {
    req.method = 'POST';
    req.url = `${sagaUrl}/ger/alt`;
    next();
}, sagaServiceProxy);
//R17
app.post('/insertManager', (req, res, next) => {
    req.method = 'POST';
    req.url = `${sagaUrl}/ger/insert`;
    next();
}, sagaServiceProxy);
//R18
app.post('/removeManager', (req, res, next) => {
    req.method = 'POST';
    idGerente = req.idGerente;
    req.url = `${sagaUrl}/ger/rem/${idGerente}`;
    next();
}, sagaServiceProxy);



app.listen(5000, () => {
    console.log('Server running on port 5000');
});


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

app.post('/newUser', (req, res, next) => {
    req.method = 'POST';
    req.url = `/api/auth`;
    next();
}, authServiceProxy);

app.put('/updateEmail', (req, res, next) => {
    req.method = 'PUT';
    oldEmail = req.oldEmail;
    email = req.email;
    req.url = `/api/auth/${oldEmail}/${email}`;
    next();
}, authServiceProxy);

app.delete('/delete', (req, res, next) => {
    req.method = 'DELETE';
    name = req.name;
    req.url = `/api/auth/${name}`;
    next();
}, authServiceProxy);

app.get('/login', (req, res, next) => {
    req.method = 'GET';
    email = req.email;
    pass = req.pass;
    req.url = `/api/auth/${email}/${pass}`;
    next();
}, authServiceProxy);

// CLIENT

app.post('/new', (req, res, next) => {
    req.method = 'POST';
    req.url = `/api/cli/`;
    next();
}, clientServiceProxy);

app.put('/update', (req, res, next) => {
    req.method = 'PUT';
    userId = req.userId;
    req.url = `/api/cli/${userId}`;
    next();
}, clientServiceProxy);

app.post('/seila', (req, res, next) => {
    req.method = 'POST';
    userId = req.userId;
    name = req.name;
    id = req.id;
    req.url = `/api/cli/${userId}/${name}/${id}`;
    next();
}, clientServiceProxy);

// ACCOUNT

app.post('/new', (req, res, next) => {
    req.method = 'POST';
    req.url = `/api/sys`;
    next();
}, accountServiceProxy);

app.put('/situacao', (req, res, next) => {
    req.method = 'PUT';
    req.url = `/api/sys`;
    next();
}, accountServiceProxy);


app.put('/limit', (req, res, next) => {
    req.method = 'PUT';
    userId = req.userId;
    limit = req.limit;
    req.url = `/api/sys/${userId}/${limit}`;
    next();
}, accountServiceProxy);

app.get('/search', (req, res, next) => {
    req.method = 'GET';
    userId = req.userId;
    req.url = `/api/sys/${userId}`;
    next();
}, accountServiceProxy);


app.get('/waiting', (req, res, next) => {
    req.method = 'GET';
    req.url = `/api/sys`;
    next();
}, accountServiceProxy);



app.post('/extrato', (req, res, next) => {
    req.method = 'POST';
    userId = req.userId;
    start = req.start;
    end = req.end;
    req.url = `/api/user/${userId}/${start}/${end}`;
    next();
}, accountServiceProxy);

app.post('/deposito', (req, res, next) => {
    req.method = 'POST';
    req.url = `/api/user`;
    next();
}, accountServiceProxy);

app.post('/saque', (req, res, next) => {
    req.method = 'POST';
    req.url = `/api/user`;
    next();
}, accountServiceProxy);

app.post('/transferencia', (req, res, next) => {
    req.method = 'POST';
    req.url = `/api/user`;
    next();
}, accountServiceProxy);

// MANAGER

app.get('/findAll', (req, res, next) => {
    req.method = 'POST';
    req.url = `/api/adm`;
    next();
}, managerServiceProxy);

app.get('/findById', (req, res, next) => {
    req.method = 'POST';
    userId = req.userId;
    req.url = `/api/adm/${userId}`;
    next();
}, managerServiceProxy);

app.post('/insert', (req, res, next) => {
    req.method = 'POST';
    req.url = `/api/adm`;
    next();
}, managerServiceProxy);

app.put('/insert', (req, res, next) => {
    req.method = 'PUT';
    userId = req.userId;
    req.url = `/api/adm/${userId}`;
    next();
}, managerServiceProxy);


app.delete('/insert', (req, res, next) => {
    req.method = 'DELETE';
    userId = req.userId;
    req.url = `/api/adm/${userId}`;
    next();
}, managerServiceProxy);


// SAGA

app.post('/alteraGerente', (req, res, next) => {
    req.method = 'POST';
    req.url = `/api/saga/ger/alt`;
    next();
}, sagaServiceProxy);

app.post('/rejeitaCliente', (req, res, next) => {
    req.method = 'POST';
    req.url = `/api/saga/ger/rej`;
    next();
}, sagaServiceProxy);

app.post('/aprovarCliente', (req, res, next) => {
    req.method = 'POST';
    userId = req.userId;
    req.url = `/api/saga/cli/apro/${userId}`;
    next();
}, sagaServiceProxy);

app.post('/insertManager', (req, res, next) => {
    req.method = 'POST';
    req.url = '/api/saga/ger/insert';
    next();
}, sagaServiceProxy);

app.post('/removeManager', (req, res, next) => {
    req.method = 'POST';
    req.url = '/api/saga/:id';
    next();
}, sagaServiceProxy);

app.post('/updateProfile', (req, res, next) => {
    req.method = 'POST';
    req.url = '/api/saga';
    next();
}, sagaServiceProxy);

app.post('/signup', (req, res, next) => {
    req.method = 'POST';
    req.url = '/api/saga/cli/auto';
    next();
}, sagaServiceProxy);

app.get('/login', (req, res, next) => {
    req.method = 'GET';
    name = req.name;
    pass = req.pass;
    req.url = `/api/auth/${name}/${pass}`;
    next();
}, sagaServiceProxy);

app.listen(3000, () => {
    console.log('Server running on port 3000');
});


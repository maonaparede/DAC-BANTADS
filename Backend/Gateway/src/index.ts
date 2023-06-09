import * as express from 'express';
import * as logger from 'morgan';
import * as helmet from 'helmet';
import * as httpProxy from 'express-http-proxy';
import { resolve } from 'path';
import { readFileSync } from 'fs';

const app = express();

const pathFile = resolve(process.cwd(), 'config.yml');
const readFile = readFileSync(pathFile, { encoding: 'utf8' })
app.use(logger ('dev'));
app.use(helmet());
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.get('/', (req, res) => {
    return res.json({ message: "Running Aplication" });
})

app.use('/users', httpProxy('http://localhost:3001', { timeout: 3000 }));
app.use('/products', httpProxy('http://localhost:3002', { timeout: 3000 }));
app.listen(3000, () => console.log('Aplicação Rodando'))
FROM node:18.16.0

WORKDIR /app

# Copiar arquivos de configuração do projeto
COPY package.json .

# Instalar dependências do projeto
RUN npm install

# Instalar o pacote @angular/cli globalmente
RUN npm install -g @angular/cli@15

# Executar o servidor de desenvolvimento do Angular
CMD ["ng", "serve", "--host", "0.0.0.0"]

FROM node:18.16.0

WORKDIR /app

RUN npm install -g @angular/cli@15

COPY ./package.json .
RUN npm install
COPY . .
RUN ng serve
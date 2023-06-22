version: "3"

services:
  #front:
  #  container_name: front
  #  image: front:latest
  #  build:
  #    context: ./Bantads
  #    dockerfile: .Dockerfile
  #  ports:
  #    - "4200:4200"
  ##

  bantads-db:
    networks:
      - bantads
    container_name: bantads-db
    image: postgres:15.1-alpine
    restart: always
    ports:
      - "5432:5432"
    environment:
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
    volumes:
      - ./init.sql:/docker-entrypoint-initdb.d/init.sql


  auth-db:
    networks:
      - bantads
    container_name: auth-db
    image: mongo
    restart: always
    ports:
      - "27017:27017"
    environment:
      - MONGO_INITDB_DATABASE=auth-db
      
  ##
  pgadmin:
    networks:
      - bantads
    container_name: pgadmin
    image: dpage/pgadmin4
    environment:
      - PGADMIN_DEFAULT_EMAIL=bantads@bantads.br
      - PGADMIN_DEFAULT_PASSWORD=admin
    ports:
      - 5050:80
    restart: unless-stopped
  ##
  mongo-express:
    networks:
      - bantads
    container_name: mongo-express
    image: mongo-express:latest
    environment:
      - ME_CONFIG_MONGODB_SERVER=auth-db
    ports:
      - 8081:8081
    restart: always
  ##
  rabbitmq:
    container_name: rabbitmq
    networks:
      - bantads
    image: rabbitmq:3.11.13-management
    ports:
      - "5672:5672"
      - "15672:15672"
      
  ##
  auth:
    container_name: auth
    networks:
      - bantads
    build:
      context: ./Backend/Auth
      dockerfile: .Dockerfile
    volumes:
      - ./Backend/Auth:/app
    depends_on:
      - auth-db
      - saga
      - rabbitmq
    ports:
      - "5001:8080"
    environment:
      - SPRING_DATA_MONGODB_URI=mongodb://auth-db/auth-db
      - SPRING_RABBITMQ_HOST=rabbitmq
      - SPRING_RABBITMQ_PORT=5672
      - SPRING_RABBITMQ_USERNAME=guest
      - SPRING_RABBITMQ_PASSWORD=guest
      - SPRING_MAIL_HOST=smtp.gmail.com
      - SPRING_MAIL_PORT=587
      - SPRING_MAIL_USERNAME=bancodotadsbantads@gmail.com
      - SPRING_MAIL_PASSWORD=dyfgeuptezxbjxhu
      - SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=true
      - SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=true

  ##
  saga:
    networks:
      - bantads
    build:
      context: ./Backend/Saga
      dockerfile: .Dockerfile
    container_name: saga
    depends_on:
      - bantads-db
      - rabbitmq
    ports:
      - "5005:8080"
    volumes:
      - ./Backend/Saga:/saga
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://bantads-db:5432/postgres
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=postgres
      - SPRING_JPA_HIBERNATE_DDL_AUTO=create
      - SPRING_RABBITMQ_HOST=rabbitmq
      - SPRING_RABBITMQ_PORT=5672
      - SPRING_RABBITMQ_USERNAME=guest
      - SPRING_RABBITMQ_PASSWORD=guest
      - SPRING_MAIL_HOST=smtp.gmail.com
      - SPRING_MAIL_PORT=587
      - SPRING_MAIL_USERNAME=bancodotadsbantads@gmail.com
      - SPRING_MAIL_PASSWORD=dyfgeuptezxbjxhu
      - SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH=true
      - SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE=true
  ##
  gerente:
    container_name: gerente
    networks:
      - bantads
    image: "gerente:latest"
    build:
      context: ./Backend/Gerente
      dockerfile: .Dockerfile
    volumes:
      - ./Backend/Gerente:/gerente
    depends_on:
      - bantads-db
      - saga
      - rabbitmq
    ports:
      - "5004:8080"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://bantads-db:5432/postgres
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=postgres
      - SPRING_JPA_HIBERNATE_DDL_AUTO=create
      - SPRING_RABBITMQ_HOST=rabbitmq
      - SPRING_RABBITMQ_PORT=5672
      - SPRING_RABBITMQ_USERNAME=guest
      - SPRING_RABBITMQ_PASSWORD=guest

  ##
  cliente:
    container_name: cliente
    networks:
      - bantads
    image: "cliente:latest"
    build:
      context: ./Backend/Cliente
      dockerfile: .Dockerfile
    volumes:
      - ./Backend/Cliente:/cliente
    depends_on:
      - bantads-db
      - saga
      - rabbitmq
    ports:
      - "5002:8080"
    environment:
      - SPRING_DATASOURCE_URL=jdbc:postgresql://bantads-db:5432/postgres
      - SPRING_DATASOURCE_USERNAME=postgres
      - SPRING_DATASOURCE_PASSWORD=postgres
      - SPRING_JPA_HIBERNATE_DDL_AUTO=create
      - SPRING_RABBITMQ_HOST=rabbitmq
      - SPRING_RABBITMQ_PORT=5672
      - SPRING_RABBITMQ_USERNAME=guest
      - SPRING_RABBITMQ_PASSWORD=guest
  ##
  conta:
    container_name: conta
    networks:
      - bantads
    image: "conta:latest"
    build:
      context: ./Backend/Conta
      dockerfile: .Dockerfile
    volumes:
      - ./Backend/Conta:/conta
    depends_on:
      - bantads-db
      - saga
      - rabbitmq
    ports:
      - "5003:8080"
    environment:
      SPRING_APPLICATION_JSON: '{
        "spring.datasource.jdbc-url" : "jdbc:postgresql://conta-db:5432/postgres",
        "spring.datasource.username" : "postgres",
        "spring.datasource.password" : "postgres",
        "spring.jpa.hibernate.ddl-auto" : "create",
        "spring.read-datasource.jdbc-url" : "jdbc:postgresql://conta-db:5432/postgres",
        "spring.read-datasource.username" : "postgres",
        "spring.read-datasource.password" : "postgres",
        "spring.rabbitmq.host" : "rabbitmq",
        "spring.rabbitmq.port" : "5672",
        "spring.rabbitmq.username" : "guest",
	"spring.rabbitmq.password" : "guest"}'
  ##
  #gateway:
  #  container_name: gateway
  #  networks:
  #    - bantads
  #  image: "gateway:latest"
  #  build:
  #    context: ./Backend/Gateway
  #    dockerfile: .Dockerfile
  #  volumes:
  #    - ./Backend/Gateway:/gateway
  #    - /app/node_modules
  #  ports:
  #    - "5000:5000"
networks:
  bantads:
    driver: bridge
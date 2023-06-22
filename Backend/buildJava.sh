cd Auth

sudo mvn spring-boot:build-image -DskipTests

cd ..

cd Cliente

sudo mvn spring-boot:build-image -DskipTests

cd ..

cd Conta

sudo mvn spring-boot:build-image -DskipTests

cd ..

cd Gerente

sudo mvn spring-boot:build-image -DskipTests

cd ..

cd Saga

sudo mvn spring-boot:build-image -DskipTests

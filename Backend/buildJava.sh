cd Auth

mvn spring-boot:build-image -DskipTests

cd ..

cd Cliente

mvn spring-boot:build-image -DskipTests

cd ..

cd Conta

mvn spring-boot:build-image -DskipTests

cd ..

cd Gerente

mvn spring-boot:build-image -DskipTests

cd ..

cd Saga

mvn spring-boot:build-image -DskipTests

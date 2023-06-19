cd Auth

sudo docker build -t auth -f .Dockerfile .

cd ..

cd Cliente

sudo docker build -t cliente -f .Dockerfile .

cd ..

cd Conta

sudo docker build -t conta -f .Dockerfile .

cd ..

cd Gerente

sudo docker build -t gerente -f .Dockerfile .

cd ..

cd Saga

sudo docker build -t saga -f .Dockerfile .

cd Gateway

sudo docker build -t gateway -f .Dockerfile .
# Clothes-Store

This is the start of a clothing store, it uses another project called keycloak for the authentication and user manaagement. 


Steps for docker image building and deployment
docker build --tag=resource-server:latest . 

docker run -p8084:8084 resource-server:latest

Use docker compose file

docker-compose config


AWS ECR push

aws --profile scc  ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/r5r0j5k2

docker build -t resource-server .

docker tag resource-server:latest public.ecr.aws/r5r0j5k2/resource-server:latest

docker push public.ecr.aws/r5r0j5k2/resource-server:latest



docker push public.ecr.aws/r5r0j5k2/resource-server:latest
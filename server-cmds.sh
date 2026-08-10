#!/usr/bin/env bash

set -e

export IMAGE_NAME=arman04/java-maven-app:$1

echo "Deploying $IMAGE_NAME"

docker compose -f docker-compose.yaml pull

docker compose -f docker-compose.yaml up -d

echo "Docker image $IMAGE_NAME is deployed on EC2 instance"
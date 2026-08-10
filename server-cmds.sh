#! /usr/bin/env bash

export IMAGE_NAME=arman04/java-maven-app:$1

docker-compose -f docker-compose.yaml up --detach

echo "Docker image $IMAGE_NAME is deployed on EC2 instance"

export TEST=testvalue
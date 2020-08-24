#!/bin/bash

./gradlew bootBuildImage --imageName=net.savantly/aloha-importer
docker push net.savantly/aloha-importer
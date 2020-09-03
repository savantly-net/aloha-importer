#!/bin/bash

./gradlew bootBuildImage --imageName=savantly/aloha-importer
docker push savantly/aloha-importer
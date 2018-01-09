#!/usr/bin/env bash

gcloud config set project ${DATASTORE_PROJECT_ID}
nohup gcloud beta emulators datastore start --host-port=${DATASTORE_LISTEN_ADDRESS} &
$(gcloud beta emulators datastore env-init)

# export DATASTORE_USE_PROJECT_ID_AS_APP_ID=true
# export DATASTORE_DATASET=${DATASTORE_PROJECT_ID}
# export DATASTORE_EMULATOR_HOST=http://${DATASTORE_LISTEN_ADDRESS}
# export DATASTORE_EMULATOR_HOST_PATH=http://${DATASTORE_LISTEN_ADDRESS}/datastore
# export DATASTORE_HOST=http://${DATASTORE_LISTEN_ADDRESS}

cd mutant-detector
mvn clean appengine:run
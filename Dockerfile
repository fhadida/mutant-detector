FROM fhadida/gcloud-sdk:0.1.1

ENV DATASTORE_LISTEN_ADDRESS 0.0.0.0:8081
ENV DATASTORE_PROJECT_ID mutants-detector

RUN gcloud components install cloud-datastore-emulator beta --quiet

COPY start.sh .
RUN sed -i $'s/\r$//' start.sh

EXPOSE 8080

ENTRYPOINT ./start.sh
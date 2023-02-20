FROM docker.io/minio/minio:latest

#COPY --from=docker.io/minio/mc:latest /usr/bin/mc /usr/bin/mc

ENV MINIO_ROOT_USER=minio99
ENV MINIO_ROOT_PASSWORD=minio123

RUN mkdir -p /data

CMD ["minio", "server", "/data", "--address", ":9000", "--console-address", ":9001"]

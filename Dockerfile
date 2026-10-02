FROM europe-north1-docker.pkg.dev/cgr-nav/pull-through/nav.no/jre:openjdk-21@sha256:6899f99dfe87a9a0bd3f9fc5ed59d9c3628307cb9fe418ea4e236dc341ecf307
ENV TZ="Europe/Oslo"
COPY build/libs/app.jar app.jar
CMD ["-jar","app.jar"]
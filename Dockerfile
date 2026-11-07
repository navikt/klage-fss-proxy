FROM europe-north1-docker.pkg.dev/cgr-nav/pull-through/nav.no/jre:openjdk-25@sha256:fac3b7b14c36abad6aa4be856f00dc5fd3be176ce66cd45b3cfa45ab23b76d1b
ENV TZ="Europe/Oslo"
COPY build/libs/app.jar app.jar
CMD ["-jar","app.jar"]
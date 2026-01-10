FROM europe-north1-docker.pkg.dev/cgr-nav/pull-through/nav.no/jre:openjdk-21@sha256:3f5c4ae4fd1acc7aeab00d879a131af2816bd6b4f98fde8c1d01dc3e6f8d65ea
ENV TZ="Europe/Oslo"
COPY build/libs/app.jar app.jar
CMD ["-jar","app.jar"]
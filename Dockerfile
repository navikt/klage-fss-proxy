FROM europe-north1-docker.pkg.dev/cgr-nav/pull-through/nav.no/jre:openjdk-21@sha256:62cbd08eb3e57da61f1d6f39d8358035b9eaf90cb65d93d522ed0248a4d8e6a7
ENV TZ="Europe/Oslo"
COPY build/libs/app.jar app.jar
CMD ["-jar","app.jar"]
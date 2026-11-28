FROM europe-north1-docker.pkg.dev/cgr-nav/pull-through/nav.no/jre:openjdk-25@sha256:6e220de7a62b0174bb7547f7f6a7f6c53f4f60dd04bf6bdbebdf88560994951a
ENV TZ="Europe/Oslo"
COPY build/libs/app.jar app.jar
CMD ["-jar","app.jar"]
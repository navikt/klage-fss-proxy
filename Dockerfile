FROM europe-north1-docker.pkg.dev/cgr-nav/pull-through/nav.no/jre:openjdk-25@sha256:bd5ef446c6093a778451c60bb07f3f70aa4da884f66b1bebc6c5aa1d610467bd
ENV TZ="Europe/Oslo"
COPY build/libs/app.jar app.jar
CMD ["-jar","app.jar"]
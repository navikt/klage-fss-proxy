FROM europe-north1-docker.pkg.dev/cgr-nav/pull-through/nav.no/jre:openjdk-21@sha256:76eaa7c30e094611d5bdc187bf4db871cc2489d7af4611dfb72c39ad3a2be33f
ENV TZ="Europe/Oslo"
COPY build/libs/app.jar app.jar
CMD ["-jar","app.jar"]
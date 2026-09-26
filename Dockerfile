FROM europe-north1-docker.pkg.dev/cgr-nav/pull-through/nav.no/jre:openjdk-21@sha256:160fbdc4d35fbf8cf2ea1ff1a4b061ca2e57a2786188732018b88f357d76da09
ENV TZ="Europe/Oslo"
COPY build/libs/app.jar app.jar
CMD ["-jar","app.jar"]
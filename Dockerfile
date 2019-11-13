FROM openjdk:10.0.2-jre-slim
COPY target/microservice-kubernetes-demo-customer-2.0.6.RELEASE.jar .
RUN wget -O apm-agent.jar https://search.maven.org/remotecontent?filepath=co/elastic/apm/elastic-apm-agent/1.8.0/elastic-apm-agent-1.8.0.jar
CMD /usr/bin/java -Xmx400m -Xms400m -javaagent:apm-agent.jar -jar microservice-kubernetes-demo-customer-2.0.6.RELEASE.jar
EXPOSE 8080


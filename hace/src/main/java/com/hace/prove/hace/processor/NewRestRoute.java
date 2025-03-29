package com.hace.prove.hace.processor;

import com.hace.prove.hace.beans.NameAddress;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class NewRestRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
//configuration prof non funziona route() inline
//        restConfiguration()
//                .component("jetty")
//                .host("0.0.0.0")
//                .port(8080)
//                .bindingMode(RestBindingMode.json)
//                .enableCORS(true);
//
//        rest("masterclass")
//                .produces("application/json")
//                .post("nameAddress").type(NameAddress.class).route()
//                .routeId("newRestRouteId")
//                .log(LoggingLevel.INFO, "${body}")
//                .process(new InboundMessageProcessor())
//                .log(LoggingLevel.INFO, "Trasformed Body: ${body}")
//                .convertBodyTo(String.class)
//                .to("file:src/main/resources/data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n");

//configuration geppa:

            restConfiguration()
                    .component("jetty") // o "platform-http"
                    .host("0.0.0.0")
                    .port(8081)
                    .bindingMode(RestBindingMode.json)
                    .enableCORS(true);

                rest("/masterclass")
                    .produces("application/json")
                    .post("/nameAddress")
                    .type(NameAddress.class)
                    .to("direct:processNameAddress");

            from("direct:processNameAddress")
                    .routeId("newRestRouteId")
                    .log(LoggingLevel.INFO, "Received Body: ${body}")
//                    .process(new InboundMessageProcessor())
//                    .log(LoggingLevel.INFO, "Transformed Body: ${body}")
//                    .convertBodyTo(String.class)
//                    .to("file:src/main/resources/data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n");
                    .to("jpa:"+NameAddress.class.getName());
    }
}

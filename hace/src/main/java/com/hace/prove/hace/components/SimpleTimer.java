package com.hace.prove.hace.components;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SimpleTimer extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer:simpletimer?period=2000")
                .routeId("pippoFrancoRoute")
                .setBody(constant("Hello World!!")) //best practices quando ho valori costanti li inserisco come constant
                .log(LoggingLevel.INFO,"${body}");
    }
}

package com.hace.prove.hace.components;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class QuequeReceiverRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        from("activemq:queue:nameAddressQueque")
                .routeId("queueReceiverId")
                .log(LoggingLevel.INFO,">>>>>> Message Received From Queue: ${body}");
    }
}

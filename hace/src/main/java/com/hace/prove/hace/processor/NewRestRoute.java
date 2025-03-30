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
                    .to("direct:toDB")//NOTA1
                    .setBody(simple("${body}")) // imposta la risposta prima di "spezzare" il flow
                    .to("direct:toActiveMQ");

            from("direct:toDB")
                    .routeId("toDBId")
                    .to("jpa:"+NameAddress.class.getName());

            from("direct:toActiveMQ")
                    .routeId("toActiveMQId")
                    .to("activemq:queue:nameAddressQueque?exchangePattern=InOnly");
    }
}
/*
    NOTA1: Si spezzeta il 2 to db e MQ cosi da poter dare al Mq anche id_name
    che altrimenti attivierebbe la rotta di toActivateMQ con id_name =null
    invece spezzettando la rota come sopra si aspetta che dopo aver salvato a db
    ritorna il json con i valori salvati (tra cui anche id_name che e' auto incrementale)
    e poi si da in pasto alla coda mq.
    DA NOTARE l'utilizzo di setBody cosi da salvare lo stato del body da ritornare.
 */
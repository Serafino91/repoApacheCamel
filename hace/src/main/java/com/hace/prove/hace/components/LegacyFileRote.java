package com.hace.prove.hace.components;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class LegacyFileRote extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("file:src/main/resources/data/input?fileName=inputFile.txt&noop=true&readLock=changed") //NOTA1
                .routeId("legacyFileMoveRouteId")
                .to("file:src/main/resources/data/output?fileName=outputFile.txt");

    }

}
/*
    NOTA: senza noop=true camel inserisce il file detro una cartella .camel in input e lo segna come letto e non lo
           da piu' in pasto alla rotta. Altra cosa se voglio leggere solo quando il file e' cambiato devo aggiungere a
           noop=true con &readLock=changed
 */

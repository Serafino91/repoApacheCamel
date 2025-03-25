package com.hace.prove.hace.components;

import com.hace.prove.hace.beans.NameAddress;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.beanio.BeanIODataFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class LegacyFileRote extends RouteBuilder {

    Logger logger = LoggerFactory.getLogger(getClass());
    BeanIODataFormat inboundDataFormat = new BeanIODataFormat("InboundMessageBeenIOMapping.xml","inputMessageStream");
    @Override
    public void configure() throws Exception {

        from("file:src/main/resources/data/input?fileName=inputCsvFile.csv&noop=true&readLock=changed") //NOTA1
                .routeId("legacyFileMoveRouteId")
                .split(body().tokenize("\n",1,true))
                .unmarshal(inboundDataFormat)
                    .process( exchange -> { // NOTA2
                        NameAddress filedata = exchange.getIn().getBody(NameAddress.class);
                        logger.info("this is the read file data \n"+ filedata.toString());
                        exchange.getIn().setBody(filedata.toString());
                    })
                    .to("file:src/main/resources/data/output?fileName=outputFile.csv&fileExist=append&appendChars=\\n")
                .end(); //NOTA3

    }

}
/*
    NOTA: senza noop=true camel inserisce il file detro una cartella .camel in input e lo segna come letto e non lo
           da piu' in pasto alla rotta. Altra cosa se voglio leggere solo quando il file e' cambiato devo aggiungere a
           noop=true con &readLock=changed


   NOTA2: la lambda function "exchange" sto a fare praticamente questo:
            ...
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                    }
                })
                .to("file:src/main/resources/data/output?fileName=outputFile.txt");

    }
    NOTA3: ".end()" mi serve a chiudere il blocco dello split altrimenti se io vollessi aggiungere
           altre funzionalita' queste verrebbero splittate e quindi causerebbe un comportamento inatteso

 */

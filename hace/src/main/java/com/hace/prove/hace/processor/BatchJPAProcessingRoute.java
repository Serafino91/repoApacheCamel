package com.hace.prove.hace.processor;

import com.hace.prove.hace.beans.NameAddress;
import com.hace.prove.hace.repository.NomeAddressRepository;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatchJPAProcessingRoute extends RouteBuilder {
    @Autowired
    NomeAddressRepository nomeAddressRepository;
    @Override
    public void configure() throws Exception {
        from("timer:check?period=10000")
                .process(exchange -> {
                    List<NameAddress> lista = nomeAddressRepository.findAll();
                    exchange.getIn().setBody(lista);
                })
                .split(body())
                .to("log:letturaDB?level=INFO")
                .end();

    }
}

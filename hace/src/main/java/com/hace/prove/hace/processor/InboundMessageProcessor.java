package com.hace.prove.hace.processor;

import com.hace.prove.hace.beans.NameAddress;
import com.hace.prove.hace.beans.OutboundNameAddress;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Name;

public class InboundMessageProcessor implements Processor {
Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void process(Exchange exchange) throws Exception {
            NameAddress nameAddress = exchange.getIn().getBody(NameAddress.class);
            exchange.getIn().setBody(new OutboundNameAddress(nameAddress.getName(), returnOutboundAddress(nameAddress)));
    }
    private String returnOutboundAddress(NameAddress nameAddress){
        StringBuilder concatenatedAddres = new StringBuilder(200);
        concatenatedAddres.append(nameAddress.getHouseNumber()+", ");
        concatenatedAddres.append(nameAddress.getCity()+", ");
        concatenatedAddres.append(nameAddress.getProvince()+", ");
        concatenatedAddres.append(nameAddress.getPostalCode());
        return concatenatedAddres.toString();
    }
}

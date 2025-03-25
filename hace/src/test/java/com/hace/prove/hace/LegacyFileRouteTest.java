package com.hace.prove.hace;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.UseAdviceWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@CamelSpringBootTest
@SpringBootTest
@UseAdviceWith
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LegacyFileRouteTest {
    @Autowired
    CamelContext context;
    @EndpointInject("mock:result")
    protected MockEndpoint mockEndpoint;
    @Autowired
    ProducerTemplate producerTemplate;

    @Test
    public void fileMoveTest() throws Exception {
        String expectedBody = "1234";
        mockEndpoint.expectedBodiesReceivedInAnyOrder(expectedBody);
        mockEndpoint.expectedMinimumMessageCount(1);

        AdviceWith.adviceWith(context,"legacyFileMoveRouteId", routeBuilder -> {
           routeBuilder.weaveByToUri("file:*").replace().to(mockEndpoint); // Nota1
        });
        context.start();
        mockEndpoint.assertIsSatisfied();
        context.stop();
    }

    @Test
        public void fileMoveByMockingFromEndpointTest() throws Exception {
            if(context.isStarted()){
            context.stop();
            }
            String expectedBody = "OutboundNameAddress(name=Mike, address= 111,  Toronto,  ON,  M5F3D2)";
            mockEndpoint.expectedBodiesReceivedInAnyOrder(expectedBody);
            mockEndpoint.expectedMinimumMessageCount(1);

            AdviceWith.adviceWith(context,"legacyFileMoveRouteId", routeBuilder -> {
               routeBuilder.replaceFromWith("direct:mockStart"); // Nota2
               routeBuilder.weaveByToUri("file:*").replace().to(mockEndpoint);
            });

            context.start();
            producerTemplate.sendBody("direct:mockStart","name, house_number, city, province, postal_code\n" +
                    "Mike, 111, Toronto, ON, M5F3D2");
            mockEndpoint.assertIsSatisfied();
        }
}

//NOTA1 sto facsendo il replace del "to" della rotta con legacyFileMoveRouteId con il mockEndopoint

/*
   NOTA2: in questo secondo te sto provando non solo a fare un replace del to ma anche del from
          che in questo caso gli passo expecetdBody e poi mi aspetto expetedBody
          nota che per fare cio' mi e' servito un producerTemplate...
 */
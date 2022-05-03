package ru.archertech.finance.accounting.endpoints;

import org.apache.camel.builder.RouteBuilder;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Listens card operation message from broker and passes it to Camunda runtime
 */
@Component
public class OperationMessageCamelRoute extends RouteBuilder {

    private final RuntimeService runtimeService;

    public OperationMessageCamelRoute(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Override
    public void configure() {
        from("jms:queue:bank.operations")
                .routeId("bank.operations")
                .log("Received a message - ${body}")
                .process(exchange -> {
                    String cardOperationJson = (String) exchange.getIn().getBody();
                    runtimeService.startProcessInstanceByMessage("cardOperationNotificationReceived",
                            Collections.singletonMap("cardOperation", cardOperationJson));
                });
    }
}

package ru.archertech.finance.accounting.ledger;

import org.apache.camel.CamelContext;
import org.camunda.bpm.client.spring.annotation.ExternalTaskSubscription;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskHandler;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@ExternalTaskSubscription("ledger.add-tx")
public class WriterTaskHandler implements ExternalTaskHandler {
    private static final Logger log = LoggerFactory.getLogger(WriterTaskHandler.class);

    private final CamelContext camelContext;

    public WriterTaskHandler(CamelContext camelContext) {
        this.camelContext = camelContext;
    }

    @Override
    public void execute(ExternalTask externalTask, ExternalTaskService externalTaskService) {
        log.info("Got task {} with variables: {}", externalTask.getId(), externalTask.getAllVariables());
        Object cardOperationJson = externalTask.getVariable("cardOperation");
        camelContext.createProducerTemplate().sendBody("direct:add-ledger-tx", cardOperationJson);

        externalTaskService.complete(externalTask);
    }
}

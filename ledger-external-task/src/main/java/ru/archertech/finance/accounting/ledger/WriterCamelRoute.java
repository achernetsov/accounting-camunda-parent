package ru.archertech.finance.accounting.ledger;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
public class WriterCamelRoute extends RouteBuilder {

    private final FileProperties fileProperties;

    public WriterCamelRoute(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }

    @Override
    public void configure() {
        from("direct:add-ledger-tx")
                .id("add-ledger-tx")
                .log("Received a message - ${body}")
                .unmarshal().json(JsonLibrary.Gson, Operation.class)
                .process(exchange -> {
                    Operation operation = (Operation) exchange.getIn().getBody();

                    String ledgerTxString = format("\n" +
                            "%s %s\n" +
                            "   Expenses:%s         $%d\n" +
                            "   Assets:%s:%s\n",
                            operation.date, operation.operation,
                            operation.category, operation.amount,
                            operation.bank, operation.card);

                    exchange.getIn().setBody(ledgerTxString);
                })
                .to(format("file://%s?fileName=%s&fileExist=Append", fileProperties.getFolder(), fileProperties.getName()));
    }

    private static final class Operation {
        String date;
        String bank;
        String card;
        String category;
        String operation;
        Long amount;
    }
}

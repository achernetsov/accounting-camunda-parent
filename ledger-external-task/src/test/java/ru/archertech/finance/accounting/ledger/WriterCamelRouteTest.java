package ru.archertech.finance.accounting.ledger;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WriterCamelRouteTest extends CamelTestSupport {

    @Test
    public void testAddLedgerTransaction() throws Exception {
        // emptying file
        PrintWriter pw = new PrintWriter("tmp/test.ledger");
        pw.close();

        String cardOperationJson = "{\"date\":\"2022.05.01\",\"bank\": \"ArcherBank\",\"card\": \"DebitCard1\",\"operation\": \"Amazon book purchase\", \"category\":\"education\",\"amount\": 30}";

        template.sendBody("direct:add-ledger-tx", cardOperationJson);
        template.sendBody("direct:add-ledger-tx", cardOperationJson);

        String expected = Files.readString(Paths.get(Objects.requireNonNull(getClass().getResource("/expected.ledger")).toURI()));
        String actual = Files.readString(Paths.get("tmp/test.ledger"));

        assertEquals(expected, actual);
    }

    @Override
    protected RoutesBuilder createRouteBuilder() {
        FileProperties properties = new FileProperties("tmp", "test.ledger");
        return new WriterCamelRoute(properties);
    }
}
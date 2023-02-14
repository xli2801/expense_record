package persistence;

import model.Transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkTransfer(String description, int amount, Transfer transfer) {
        assertEquals(description, transfer.getDescription());
        assertEquals(amount, transfer.getAmount());
    }


}

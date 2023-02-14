package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransferTest {
    private Transfer testTransfer;

    @BeforeEach
    void runBefore() {
        testTransfer = new Transfer("Chocolate", 10);
    }

    @Test
    void testConstructor() {
        assertEquals("Chocolate", testTransfer.getDescription());
        assertEquals(10, testTransfer.getAmount());
    }

    @Test
    void testToJson() {
        Transfer item1 = new Transfer("Chocolate", 10);
        JSONObject jsonObject = item1.toJson();
        assertEquals("Chocolate",jsonObject.getString("description"));
        assertEquals(10,jsonObject.getDouble("amount"));
    }
}
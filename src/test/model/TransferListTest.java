package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class TransferListTest {
    private TransferList testTransferList;

    @BeforeEach
    void runBefore() {
        testTransferList = new TransferList("Income List");
    }

    @Test
    void testConstructor() {
        assertEquals("Income List", testTransferList.getName());
        assertTrue(testTransferList.getList().isEmpty());
    }

    @Test
    void testAddTransfer() {
        Transfer item1 = new Transfer("Chocolate", 10);
        Transfer item2 = new Transfer("Milk", 5);
        testTransferList.addTransfer(item1);
        assertEquals(1, testTransferList.getList().size());
        assertEquals(item1, testTransferList.getList().get(testTransferList.getList().size() - 1));
        testTransferList.addTransfer(item2);
        assertEquals(2, testTransferList.getList().size());
        assertEquals(item2, testTransferList.getList().get(testTransferList.getList().size() - 1));
    }

    @Test
    void testDeleteTransfer() {
        Transfer item1 = new Transfer("Chocolate", 10);
        Transfer item2 = new Transfer("Milk", 5);
        testTransferList.addTransfer(item1);
        testTransferList.addTransfer(item2);
        testTransferList.deleteTransfer(1);
        assertEquals(1, testTransferList.getList().size());
        assertEquals(Collections.singletonList(item1), testTransferList.getList());
        testTransferList.deleteTransfer(0);
        assertTrue(testTransferList.getList().isEmpty());
    }

    @Test
    void testGetSumAmount() {
        Transfer item1 = new Transfer("Chocolate", 10);
        Transfer item2 = new Transfer("Milk", 5);
        testTransferList.addTransfer(item1);
        assertEquals(10, testTransferList.getSumAmount());
        testTransferList.addTransfer(item2);
        assertEquals(15, testTransferList.getSumAmount());
    }

    @Test
    void testToJson() {
        Transfer item1 = new Transfer("Chocolate", 10);
        JSONObject jsonObject = item1.toJson();
        assertEquals("Chocolate",jsonObject.getString("description"));
        assertEquals(10,jsonObject.getDouble("amount"));
    }

    @Test
    void testTransferToJsonNoTransferInList() {
        JSONArray jsonArray = testTransferList.transferToJson();
        assertTrue(jsonArray.isEmpty());
    }

    @Test
    void testTransferToJsonGeneralTransferList() {
        Transfer item1 = new Transfer("Chocolate", 10);
        Transfer item2 = new Transfer("Milk", 5);
        testTransferList.addTransfer(item1);
        testTransferList.addTransfer(item2);
        JSONArray jsonArray = testTransferList.transferToJson();
        assertEquals(2,jsonArray.length());
        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
        assertEquals("Chocolate",jsonObject1.getString("description"));
        assertEquals(10,jsonObject1.getDouble("amount"));
        JSONObject jsonObject2 = jsonArray.getJSONObject(1);
        assertEquals("Milk",jsonObject2.getString("description"));
        assertEquals(5,jsonObject2.getDouble("amount"));
    }
}

package persistence;

import model.TransferList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            TransferList incomeList = reader.readIncomeList();
            TransferList outcomeList = reader.readOutcomeList();
            double budget = reader.readBudget();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyTransferLists() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyTransferLists.json");
        try {
            TransferList itl = reader.readIncomeList();
            TransferList otl = reader.readOutcomeList();
            double budget = reader.readBudget();
            assertEquals("Income List", itl.getName());
            assertTrue(itl.getList().isEmpty());
            assertEquals("Outcome List", otl.getName());
            assertTrue(itl.getList().isEmpty());
            assertEquals(2000, budget);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyIncomeList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyIncomeList.json");
        try {
            TransferList itl = reader.readIncomeList();
            TransferList otl = reader.readOutcomeList();
            double budget = reader.readBudget();
            assertEquals("Income List", itl.getName());
            assertTrue(itl.getList().isEmpty());
            assertEquals("Outcome List", otl.getName());
            assertEquals(2, otl.getList().size());
            checkTransfer("chocolate", 10, otl.getList().get(0));
            checkTransfer("lunch", 20, otl.getList().get(1));
            assertEquals(2000, budget);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderEmptyOutcomeList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyOutcomeList.json");
        try {
            TransferList itl = reader.readIncomeList();
            TransferList otl = reader.readOutcomeList();
            double budget = reader.readBudget();
            assertEquals("Income List", itl.getName());
            assertEquals(2, itl.getList().size());
            checkTransfer("Math tutorial", 1000, itl.getList().get(0));
            checkTransfer("CPSC tutorial", 2000, itl.getList().get(1));
            assertEquals("Outcome List", otl.getName());
            assertTrue(otl.getList().isEmpty());
            assertEquals(2000, budget);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralTransferListAndBudget() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTransferListAndBudget.json");
        try {
            TransferList itl = reader.readIncomeList();
            TransferList otl = reader.readOutcomeList();
            double budget = reader.readBudget();
            assertEquals("Income List", itl.getName());
            assertEquals(2, itl.getList().size());
            checkTransfer("Math tutorial", 1000, itl.getList().get(0));
            checkTransfer("CPSC tutorial", 2000, itl.getList().get(1));
            assertEquals("Outcome List", otl.getName());
            assertEquals(2, otl.getList().size());
            checkTransfer("chocolate", 10, otl.getList().get(0));
            checkTransfer("lunch", 20, otl.getList().get(1));
            assertEquals(2000, budget);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

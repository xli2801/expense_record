package persistence;

import model.Transfer;
import model.TransferList;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            double budget = 10000;
            TransferList itl = new TransferList("Income List");
            TransferList otl = new TransferList("Outcome List");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyTransferLists() {
        try {
            double budget = 10000;
            TransferList itl = new TransferList("Income List");
            TransferList otl = new TransferList("Outcome List");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyTransferLists.json");
            writer.open();
            writer.write(budget, itl, otl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyTransferLists.json");
            budget = reader.readBudget();
            itl = reader.readIncomeList();
            otl = reader.readOutcomeList();
            assertEquals("Income List", itl.getName());
            assertTrue(itl.getList().isEmpty());
            assertEquals("Outcome List", otl.getName());
            assertTrue(otl.getList().isEmpty());
            assertEquals(10000, budget);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    @SuppressWarnings("methodlength")
    void testWriterEmptyIncomeList() {
        try {
            double budget = 10000;
            TransferList itl = new TransferList("Income List");
            TransferList otl = new TransferList("Outcome List");
            otl.addTransfer(new Transfer("chocolate", 10));
            otl.addTransfer(new Transfer("lunch", 20));
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyIncomeList.json");
            writer.open();
            writer.write(budget, itl, otl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyIncomeList.json");
            budget = reader.readBudget();
            itl = reader.readIncomeList();
            otl = reader.readOutcomeList();
            assertEquals("Income List", itl.getName());
            assertTrue(itl.getList().isEmpty());
            assertEquals("Outcome List", otl.getName());
            assertEquals(2, otl.getList().size());
            checkTransfer("chocolate", 10, otl.getList().get(0));
            checkTransfer("lunch", 20, otl.getList().get(1));
            assertEquals(10000, budget);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    @SuppressWarnings("methodlength")
    void testWriterEmptyOutcomeList() {
        try {
            double budget = 10000;
            TransferList itl = new TransferList("Income List");
            TransferList otl = new TransferList("Outcome List");
            itl.addTransfer(new Transfer("Math tutorial", 1000));
            itl.addTransfer(new Transfer("CPSC tutorial", 2000));
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyOutcomeList.json");
            writer.open();
            writer.write(budget, itl, otl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyOutcomeList.json");
            budget = reader.readBudget();
            itl = reader.readIncomeList();
            otl = reader.readOutcomeList();
            assertEquals("Income List", itl.getName());
            assertEquals(2, itl.getList().size());
            checkTransfer("Math tutorial", 1000, itl.getList().get(0));
            checkTransfer("CPSC tutorial", 2000, itl.getList().get(1));
            assertEquals("Outcome List", otl.getName());
            assertTrue(otl.getList().isEmpty());
            assertEquals(10000, budget);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    @SuppressWarnings("methodlength")
    void testWriterGeneralTransferListAndBudget() {
        try {
            double budget = 10000;
            TransferList itl = new TransferList("Income List");
            TransferList otl = new TransferList("Outcome List");
            itl.addTransfer(new Transfer("Math tutorial", 1000));
            itl.addTransfer(new Transfer("CPSC tutorial", 2000));
            otl.addTransfer(new Transfer("chocolate", 10));
            otl.addTransfer(new Transfer("lunch", 20));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTransferListAndBudget.json");
            writer.open();
            writer.write(budget, itl, otl);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTransferListAndBudget.json");
            budget = reader.readBudget();
            itl = reader.readIncomeList();
            otl = reader.readOutcomeList();
            assertEquals("Income List", itl.getName());
            assertEquals(2, itl.getList().size());
            checkTransfer("Math tutorial", 1000, itl.getList().get(0));
            checkTransfer("CPSC tutorial", 2000, itl.getList().get(1));
            assertEquals("Outcome List", otl.getName());
            assertEquals(2, otl.getList().size());
            checkTransfer("chocolate", 10, otl.getList().get(0));
            checkTransfer("lunch", 20, otl.getList().get(1));
            assertEquals(10000, budget);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}

package persistence;

import model.Transfer;
import model.TransferList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// reference: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
// Represents a reader that reads transfer lists and budget from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads income transfer list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TransferList readIncomeList() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseIncomeTransferList(jsonObject);
    }

    // EFFECTS: reads outcome transfer list from file and returns it;
    // throws IOException if an error occurs reading data from file
    public TransferList readOutcomeList() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseOutcomeTransferList(jsonObject);
    }

    // EFFECTS: reads budget from file and returns it;
    // throws IOException if an error occurs reading data from file
    public double readBudget() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseBudget(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses income transfer list from JSON object and returns it
    private TransferList parseIncomeTransferList(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("transfer lists");
        JSONObject incomeListJsonObject = jsonArray.getJSONObject(0);
        String name = incomeListJsonObject.getString("name");
        TransferList itl = new TransferList(name);
        addTransfers(itl, incomeListJsonObject);
        return itl;
    }

    // EFFECTS: parses outcome transfer list from JSON object and returns it
    private TransferList parseOutcomeTransferList(JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("transfer lists");
        JSONObject outcomeListJsonObject = jsonArray.getJSONObject(1);
        String name = outcomeListJsonObject.getString("name");
        TransferList otl = new TransferList(name);
        addTransfers(otl, outcomeListJsonObject);
        return otl;
    }

    // EFFECTS: parses budget from JSON object and returns it
    private double parseBudget(JSONObject jsonObject) {
        double budget = jsonObject.getDouble("budget");
        return budget;
    }

    // MODIFIES: wr
    // EFFECTS: parses transfers from JSON object and adds them to transfer list
    private void addTransfers(TransferList tl, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("transfers");
        for (Object json : jsonArray) {
            JSONObject nextTransfer = (JSONObject) json;
            addTransfer(tl, nextTransfer);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses transfer from JSON object and adds it to transfer list
    private void addTransfer(TransferList tl, JSONObject jsonObject) {
        double amount = jsonObject.getDouble("amount");
        String description = jsonObject.getString("description");
        Transfer transfer = new Transfer(description, amount);
        tl.addTransfer(transfer);
    }
}

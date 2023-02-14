package model;

import org.json.JSONObject;
import persistence.Writable;

// Represent an income or outcome transfer with its description and amount of transfer.
public class Transfer implements Writable {
    private String description;   // Description of the transfer
    private double amount;       // the amount of the transfer

    /*
     * REQUIRES: description is not an empty string
     *           amount > 0
     * EFFECTS: constructs transfer with given description and amount of transfer.
     */
    public Transfer(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    // EFFECTS: returns this transfer as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("description", description);
        json.put("amount", amount);
        return json;
    }
}



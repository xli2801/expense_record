package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Objects;

// Represents a list of transfers
public class TransferList implements Writable {
    private String name; // name of the transfer list
    private ArrayList<Transfer> transfers; //list of transfers

    /*
     * REQUIRES: name is not empty string
     * EFFECTS: constructs transfer list with given name and create an empty list for transfers.
     */
    public TransferList(String name) {
        this.name = name;
        this.transfers = new ArrayList<>();
    }

    /*
     * MODIFIES: this
     * EFFECTS: add a transfer to the transfer list.
     */
    public void addTransfer(Transfer transfer) {
        transfers.add(transfer);
        if (Objects.equals(name, "Income List")) {
            EventLog.getInstance().logEvent(new Event("Income Transfer added."));
        } else {
            EventLog.getInstance().logEvent(new Event("Outcome Transfer added."));
        }
    }

    /*
     * REQUIRES: transfer with transferIndex as its index exists in the transfer list
     * MODIFIES: this
     * EFFECTS: delete a transfer with transferIndex as its index from the transfer list.
     */
    public void deleteTransfer(int transferIndex) {
        transfers.remove(transferIndex);
        if (Objects.equals(name, "Income List")) {
            EventLog.getInstance().logEvent(new Event("Income Transfer removed."));
        } else {
            EventLog.getInstance().logEvent(new Event("Outcome Transfer removed."));
        }
    }

    // EFFECTS: return the sum of the amount of every transfer in the list.
    public double getSumAmount() {
        double accumulator = 0;
        for (Transfer next : transfers) {
            accumulator += next.getAmount();
        }
        return accumulator;
    }

    public ArrayList<Transfer> getList() {
        return transfers;
    }

    public String getName() {
        return name;
    }

    //EFFECTS: returns this transfer list as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("transfers", transferToJson());
        return json;
    }

    // EFFECTS: returns transfers in this transfer list as a JSON array
    public JSONArray transferToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Transfer t : transfers) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }
}

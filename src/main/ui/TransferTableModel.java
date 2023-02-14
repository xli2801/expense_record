package ui;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

// Transfer record table model
public class TransferTableModel extends DefaultTableModel {
    static Vector<String> columns = new Vector<>();
    private static TransferTableModel transferTableModel = new TransferTableModel();

    static {
        columns.addElement("No.");
        columns.addElement("Description");
        columns.addElement("Amount");
    }

    /*
    * EFFECTS: construct a transfer table with no data and three column names;
    */
    private TransferTableModel() {
        super(null, columns);
    }

    /*
     * MODIFIES: this
     * EFFECTS: return a transfer table with given data
     */
    public static TransferTableModel addData(Vector<Vector<Object>> data) {
        transferTableModel.setDataVector(data,columns);
        return transferTableModel;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
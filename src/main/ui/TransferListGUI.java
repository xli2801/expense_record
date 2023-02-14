package ui;

import model.Transfer;
import model.TransferList;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Vector;

// Graphic user interface for the transfer list page of Expense recording application
public class TransferListGUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    JLabel nameLabel;
    JTable jtable;
    JPanel panel;
    JButton addButton = new JButton(new AddTransferAction(this));
    JButton removeButton = new JButton(new RemoveTransferAction(this));
    TransferList transferList;

    /*
     * EFFECTS: constructs a window with given title, size, icon and components to show transfer list.
     */
    public TransferListGUI(TransferList transferList) {
        super(transferList.getName());

        nameLabel = new JLabel(transferList.getName(), JLabel.CENTER);
        this.transferList = transferList;
        // Image Reference: https://www.mcicon.com/product/money-icon-41/
        setIconImage(new ImageIcon("./src/main/resource/icon.jpg").getImage());
        setSize(WIDTH, HEIGHT);
        addConponents();
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        centreOnScreen();
        setVisible(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS: Helper to create components on the window
     */
    private void addConponents() {
        Container contentPane = getContentPane();

        nameLabel.setFont(new Font("Monaco", Font.PLAIN, 40));
        nameLabel.setPreferredSize(new Dimension(0, 80));

        jtable = makeTable(transferList);
        JScrollPane jscrollPane = new JScrollPane(jtable);

        panel = new JPanel();
        panel.add(addButton);
        panel.add(removeButton);

        contentPane.add(nameLabel, BorderLayout.NORTH);
        contentPane.add(panel, BorderLayout.CENTER);
        contentPane.add(jscrollPane, BorderLayout.SOUTH);
    }

    /*
     * MODIFIES: this
     * EFFECTS: Helper to create a table of transfer records on the window
     */
    public JTable makeTable(TransferList transferList) {
        Vector<Vector<Object>> data = new Vector<>();
        int counter = 0;
        for (Transfer transfer: transferList.getList()) {
            counter += 1;
            Vector<Object> rowVector = new Vector<>();
            rowVector.addElement(counter);
            rowVector.addElement(transfer.getDescription());
            rowVector.addElement(transfer.getAmount());
            data.addElement(rowVector);
        }

        TransferTableModel transferTableModel = TransferTableModel.addData(data);
        jtable = new JTable(transferTableModel);
        JTableHeader tableHeader = jtable.getTableHeader();
        tableHeader.setFont(new Font(null, Font.BOLD, 15));
        tableHeader.setForeground(Color.BLUE);
        return jtable;
    }


    /*
     * MODIFIES: this
     * EFFECTS: Helper to centre main application window on desktop
     */
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
    }

    public TransferList getTransferList() {
        return transferList;
    }

    public void setTransferList(TransferList transferList) {
        this.transferList = transferList;
    }

    /**
     * Represents the action to be taken when the user wants to
     * add a transfer to the transfer list.
     */
    private class AddTransferAction extends AbstractAction {
        TransferListGUI transferListGUI;

        /*
         * EFFECTS: constructs add transfer action with name and set the transferListGUI to given transferListGUI
         */
        AddTransferAction(TransferListGUI transferListGUI) {
            super("Add Transfer");
            this.transferListGUI = transferListGUI;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            new AddTransferGUI(transferListGUI);
        }
    }

    /**
     * Represents the action to be taken when the user wants to
     * remove a transfer to the transfer list.
     */
    private class RemoveTransferAction extends AbstractAction {
        TransferListGUI transferListGUI;

        /*
         * EFFECTS: constructs remove transfer action with name and set the transferListGUI to given transferListGUI
         */
        RemoveTransferAction(TransferListGUI transferListGUI) {
            super("Remove Transfer");
            this.transferListGUI = transferListGUI;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int option = Integer.parseInt(JOptionPane.showInputDialog(transferListGUI,
                    "Which transfor record do you want to remove? (No.)"));
            if (transferList.getList().size() >= option) {
                transferList.deleteTransfer(option - 1);
                transferListGUI.makeTable(transferList);
            } else {
                JOptionPane.showMessageDialog(transferListGUI,
                        "Transfer record not found.");
            }
        }
    }
}

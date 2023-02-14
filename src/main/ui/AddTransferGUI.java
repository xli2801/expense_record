package ui;

import model.Transfer;
import model.TransferList;

import javax.swing.*;
import java.awt.*;

// Add Transfer Dialog of Expense recording application
public class AddTransferGUI extends JDialog {
    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;
    TransferListGUI transferListGUI;
    TransferList transferList;
    JLabel descriptionLabel;
    JTextField descriptionText;
    JLabel amountLabel;
    JTextField amountText;
    JButton addButton;

    /*
    * EFFECTS: construct a dialog with given title, size, transfer list and components to add new transfer to the list
    */
    public AddTransferGUI(TransferListGUI transferListGUI) {
        super(transferListGUI, "Add Transfer");
        setSize(WIDTH, HEIGHT);
        this.transferListGUI = transferListGUI;
        transferList = transferListGUI.getTransferList();
        init();
        setResizable(false);
        centreOnScreen();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
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

    /*
     * MODIFIES: this
     * EFFECTS: initializes the jframe components
     */
    private void init() {
        descriptionLabel = new JLabel("Description: ");
        descriptionText = new JTextField();
        amountLabel = new JLabel("Amount: ");
        amountText = new JTextField();
        addButton = new JButton("Add Transfer");
        addComponents();
    }

    /*
     * MODIFIES: this
     * EFFECTS: Helper to add components to the window
     */
    private void addComponents() {
        Container contentPane = getContentPane();
        Dimension dimension = new Dimension(200, 30);

        descriptionText.setPreferredSize(dimension);
        amountText.setPreferredSize(dimension);

        JPanel jpanel = new JPanel();
        jpanel.add(descriptionLabel);
        jpanel.add(descriptionText);
        jpanel.add(amountLabel);
        jpanel.add(amountText);

        contentPane.add(jpanel, BorderLayout.CENTER);

        addButton.addActionListener(e -> {
            Transfer transfer = new Transfer(descriptionText.getText(), Double.parseDouble(amountText.getText()));
            transferList.addTransfer(transfer);
            transferListGUI.makeTable(transferList);
            transferListGUI.setTransferList(transferList);
            dispose();
        });
        contentPane.add(addButton, BorderLayout.SOUTH);
    }
}

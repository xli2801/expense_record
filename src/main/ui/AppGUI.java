package ui;

import model.Event;
import model.EventLog;
import model.TransferList;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Graphic user interface for the main page of Expense recording application
public class AppGUI extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String JSON_STORE = "./data/transferLists.json";
    //image reference: https://www.iconfinder.com/icons/7584728/balance_income_business_money_outcome_icon
    private ImageIcon incomeIcon = new ImageIcon("./src/main/resource/incomeIcon.JPG");
    private ImageIcon outcomeIcon = new ImageIcon("./src/main/resource/outcomeIcon.JPG");

    private static TransferList incomeList;
    private static TransferList outcomeList;
    private double budget;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JLabel nameLabel;
    private JButton loadButton;
    private JButton saveButton;
    private JButton incomeButton;
    private JButton outcomeButton;
    private JButton setBudgetButton;
    private JButton viewBudgetButton;

    /*
     * EFFECTS: constructs a window with given title, size, icon and components
     */
    public AppGUI() {
        super("Expense Recording App");
        // Image Reference: https://www.mcicon.com/product/money-icon-41/
        setIconImage(new ImageIcon("./src/main/resource/icon.jpg").getImage());
        setSize(WIDTH, HEIGHT);
        init();
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                printLog();
                System.exit(0);
            }
        });
        centreOnScreen();
        setVisible(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS:  Helper to initializes the transfer lists, budget, writer, reader and JFrame components
     */
    private void init() {
        incomeList = new TransferList("Income List");
        outcomeList = new TransferList("Outcome List");
        budget = 0;
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        nameLabel = new JLabel("Expense Recording App", JLabel.CENTER);
        loadButton = new JButton(new LoadAction());
        saveButton = new JButton(new SaveAction());
        setTransferButton();
        setBudgetButton = new JButton(new SetBudgetAction());
        viewBudgetButton = new JButton(new ViewRemainingBudgetAction());
        addComponents();
    }

    /**
     * MODIFIES: this
     * EFFECTS: Helper to add components to the window
     */
    private void addComponents() {
        Container contentPane = getContentPane();
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER,500,10));
        centerPanel.add(loadButton);
        centerPanel.add(saveButton);
        centerPanel.add(setBudgetButton);
        centerPanel.add(viewBudgetButton);
        centerPanel.add(incomeButton);
        centerPanel.add(outcomeButton);

        nameLabel.setFont(new Font("Monaco", Font.PLAIN, 40));
        nameLabel.setPreferredSize(new Dimension(0, 80));
        contentPane.add(nameLabel, BorderLayout.NORTH);
        contentPane.add(centerPanel, BorderLayout.CENTER);
    }

    /**
     * MODIFIES: this
     * EFFECTS: Helper to create buttons that lead to income list and outcome list
     */
    private void setTransferButton() {
        setIncomeButtonSize();
        incomeButton.addActionListener(e -> new TransferListGUI(incomeList));

        setOutcomeButtonSize();
        outcomeButton.addActionListener(e -> new TransferListGUI(outcomeList));
    }

    /**
     * MODIFIES: this
     * EFFECTS: Helper to set the size of the income button and the size of the image on it.
     */
    private void setIncomeButtonSize() {
        Image image = incomeIcon.getImage();
        Image newImage = image.getScaledInstance(80, 50,  java.awt.Image.SCALE_SMOOTH);
        incomeIcon = new ImageIcon(newImage);
        incomeButton = new JButton(incomeIcon);
        incomeButton.setPreferredSize(new Dimension(80, 50));
    }

    /**
     * MODIFIES: this
     * EFFECTS: Helper to set the size of the outcome button and the size of the image on it.
     */
    private void setOutcomeButtonSize() {
        Image image = outcomeIcon.getImage();
        Image newImage = image.getScaledInstance(80, 50,  java.awt.Image.SCALE_SMOOTH);
        outcomeIcon = new ImageIcon(newImage);
        outcomeButton = new JButton(outcomeIcon);
        outcomeButton.setPreferredSize(new Dimension(80, 50));
    }

    /**
     * EFFECTS: Print the event log onto the console.
     */
    public void printLog() {
        EventLog events = EventLog.getInstance();
        for (Event e: events) {
            System.out.println(e + "\n");
        }
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

    /**
     * Represents the action to be taken when the user wants to
     * load the income list, outcome list and budget.
     */
    private class LoadAction extends AbstractAction {
        LoadAction() {
            super("Load");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                incomeList = jsonReader.readIncomeList();
                outcomeList = jsonReader.readOutcomeList();
                budget = jsonReader.readBudget();
                JOptionPane.showMessageDialog(null, "Saved data loaded successfully.");
            } catch (IOException exception) {
                System.out.println("Unable to read from file: " + JSON_STORE);
            }
        }
    }

    /**
     * Represents the action to be taken when the user wants to
     * save the income list, outcome list and budget.
     */
    private class SaveAction extends AbstractAction {
        SaveAction() {
            super("Save");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(budget,incomeList,outcomeList);
                jsonWriter.close();
                JOptionPane.showMessageDialog(null, "Data saved successfully.");
            } catch (FileNotFoundException exception) {
                System.out.println("Unable to write to file: " + JSON_STORE);
            }
        }
    }

    /**
     * Represents the action to be taken when the user wants to
     * set budget.
     */
    private class SetBudgetAction extends AbstractAction {
        SetBudgetAction() {
            super("Set Budget");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            budget = Double.parseDouble(JOptionPane.showInputDialog(null, "Set budget to ($):"));

        }
    }

    /**
     * Represents the action to be taken when the user wants to
     * view budget and remaining budget.
     */
    private class ViewRemainingBudgetAction extends AbstractAction {
        ViewRemainingBudgetAction() {
            super("View Remaining Budget");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if ((budget - outcomeList.getSumAmount()) <= 0) {
                JOptionPane.showMessageDialog(null, "Your budget is $" + budget
                        + ". Remaining budget is: $"
                        + (budget - outcomeList.getSumAmount()) + ", You have run out of Budget!");
            } else {
                JOptionPane.showMessageDialog(null, "Your budget is $" + budget
                        + ". Remaining budget is: $" + (budget - outcomeList.getSumAmount()));
            }
        }
    }

    // starts the application
    public static void main(String[] args) {
        new AppGUI();
    }
}

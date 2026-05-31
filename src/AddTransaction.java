import javax.swing.*;
import java.awt.*;

class AddTransaction extends JDialog {
    private JTextField amountField, dateField;
    private JComboBox<String> typeCombo, categoryCombo;
    private FinanceManager manager;
    private String[] incomeCategories = {"Salary", "Bonus", "Investment", "Other"};
    private String[] expenseCategories = {"Food", "Transportation", "Housing", "Entertainment", "Shopping", "Other"};

    /**
     * Function to add a transaction
     *
     * @param parent Main application window this function is attached to
     * @param manager Instance of FinanceManager to manage existing transactions
     */
    public AddTransaction(JFrame parent, FinanceManager manager) {
        super(parent, "Add Transaction", true);
        this.manager = manager;
        setSize(300, 200);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Amount:"));
        amountField = new JTextField();
        add(amountField);

        add(new JLabel("Type:"));
        typeCombo = new JComboBox<>(new String[]{"Income", "Expense"});
        typeCombo.addActionListener(e -> updateCategoryCombo());
        add(typeCombo);

        add(new JLabel("Category:"));
        categoryCombo = new JComboBox<>();
        updateCategoryCombo();
        add(categoryCombo);

        add(new JLabel("Date (DD-MM-YYYY):"));
        dateField = new JTextField();
        add(dateField);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                String type = (String) typeCombo.getSelectedItem();
                String category = (String) categoryCombo.getSelectedItem();
                String date = dateField.getText();
                manager.addTransaction(new Transaction(amount, type, category, date));
                manager.saveToFile();
                setVisible(false);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
            }
        });
        add(okButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> setVisible(false));
        add(cancelButton);
    }

    /**
     * Updates categories based on the selected type
     * Income = income categories
     * Expense = expense categories
     */

    private void updateCategoryCombo() {
        String selectedType = (String) typeCombo.getSelectedItem();
        categoryCombo.removeAllItems();
        String[] categories;
        if ("Income".equals(selectedType)) {
            categories = incomeCategories;
        } else {
            categories = expenseCategories;
        }
        for (String cat : categories) {
            categoryCombo.addItem(cat);
        }
    }
}
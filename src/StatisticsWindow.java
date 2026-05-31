import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

class StatisticsWindow extends JFrame {
    public StatisticsWindow(List<Transaction> transactions) {
        setTitle("Statistiky podle druhu a typu");
        setSize(500, 350);
        setLayout(new BorderLayout());

        String[] incomeCategories = {"Plat", "Bonus", "Investice", "Jiné"};
        String[] expenseCategories = {"Jídlo", "Doprava", "Bydlení", "Zábava", "Nákupy", "Jiné"};

        double[] incomeSums = new double[incomeCategories.length];
        double[] expenseSums = new double[expenseCategories.length];
        double incomeSum = 0.0;
        double expenseSum = 0.0;

        for (Transaction t : transactions) {
            String type = t.getType();
            String category = t.getCategory();
            double amount = t.getAmount();
            if ("Příjem".equals(type)) {
                for (int i = 0; i < incomeCategories.length; i++) {
                    if (incomeCategories[i].equals(category)) {
                        incomeSums[i] += amount;
                        break;
                    }
                }
                incomeSum += amount;
            } else if ("Výdaj".equals(type)) {
                for (int i = 0; i < expenseCategories.length; i++) {
                    if (expenseCategories[i].equals(category)) {
                        expenseSums[i] += amount;
                        break;
                    }
                }
                expenseSum += amount;
            }
        }

        String[] columns = {"Typ", "Druh", "Celková suma"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        for (int i = 0; i < incomeCategories.length; i++) {
            model.addRow(new Object[]{"Příjem", incomeCategories[i], incomeSums[i]});
        }

        model.addRow(new Object[]{"---", "---", "---"});

        for (int i = 0; i < expenseCategories.length; i++) {
            model.addRow(new Object[]{"Výdaj", expenseCategories[i], expenseSums[i]});
        }

        model.addRow(new Object[]{"---", "Součet příjmů:", incomeSum});
        model.addRow(new Object[]{"---", "Součet výdajů:", expenseSum});
        model.addRow(new Object[]{"---", "Čistý zůstatek:", incomeSum - expenseSum});

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton closeButton = new JButton("Zavřít");
        closeButton.addActionListener(e -> setVisible(false));
        add(closeButton, BorderLayout.SOUTH);
    }
}
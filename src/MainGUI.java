import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class MainGUI extends JFrame {
    private List<Soldier> soldiers = new ArrayList<>();
    private DefaultTableModel tableModel;
    private JTable table;
    private SoldierDatabase soldierDb = new SoldierDatabase();

    public MainGUI() {
        setTitle("Missing Soldier Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Table setup
        String[] columns = { "Name", "ID", "Rank", "Unit", "Latitude", "Longitude", "Status" };
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton registerBtn = new JButton("Register Soldier");
        JButton markMissingBtn = new JButton("Mark as Missing");
        JButton removeMissingBtn = new JButton("Remove from Missing");
        JButton viewDetailsBtn = new JButton("View Details");
        buttonPanel.add(registerBtn);
        buttonPanel.add(markMissingBtn);
        buttonPanel.add(removeMissingBtn);
        buttonPanel.add(viewDetailsBtn);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load soldiers from DB
        List<Soldier> dbSoldiers = soldierDb.getAllSoldiers();
        soldiers.addAll(dbSoldiers);
        for (Soldier s : dbSoldiers) {
            tableModel.addRow(new Object[] { s.getName(), s.getId(), s.getRank(), s.getUnit(), s.getLatitude(),
                    s.getLongitude(), s.getStatus() });
        }

        // Register Soldier
        registerBtn.addActionListener(e -> showRegisterDialog());
        // Mark as Missing
        markMissingBtn.addActionListener(e -> markSelectedSoldierMissing());
        // Remove from Missing
        removeMissingBtn.addActionListener(e -> removeSelectedSoldierFromMissing());
        // View Details
        viewDetailsBtn.addActionListener(e -> showSelectedSoldierDetails());
    }

    private void showRegisterDialog() {
        // Unit selection first
        String[] units = { "Army", "Navy", "Airforce" };
        JComboBox<String> unitCombo = new JComboBox<>(units);

        // Rank dropdown, will update based on unit
        JComboBox<String> rankCombo = new JComboBox<>();
        updateRankCombo(rankCombo, units[0]); // Default to Army
        unitCombo.addActionListener(e -> {
            String selectedUnit = (String) unitCombo.getSelectedItem();
            updateRankCombo(rankCombo, selectedUnit);
        });

        JTextField nameField = new JTextField();
        JTextField idField = new JTextField();
        JTextField latField = new JTextField();
        JTextField lonField = new JTextField();
        JPanel panel = new JPanel(new GridLayout(0, 2));
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Unit:"));
        panel.add(unitCombo);
        panel.add(new JLabel("Rank:"));
        panel.add(rankCombo);
        panel.add(new JLabel("Latitude:"));
        panel.add(latField);
        panel.add(new JLabel("Longitude:"));
        panel.add(lonField);
        int result = JOptionPane.showConfirmDialog(this, panel, "Register Soldier", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String id = idField.getText();
                String unit = (String) unitCombo.getSelectedItem();
                String rank = (String) rankCombo.getSelectedItem();
                double lat = Double.parseDouble(latField.getText());
                double lon = Double.parseDouble(lonField.getText());
                Soldier s = new Soldier(name, id, rank, unit, lat, lon);
                soldiers.add(s);
                tableModel.addRow(new Object[] { name, id, rank, unit, lat, lon, s.getStatus() });
                soldierDb.addSoldier(s);
                JOptionPane.showMessageDialog(this, "Soldier registered successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please try again.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateRankCombo(JComboBox<String> rankCombo, String unit) {
        rankCombo.removeAllItems();
        String[] armyRanks = { "Lieutenant", "Captain", "Major", "Lieutenant Colonel", "Colonel", "Brigadier",
                "Major General", "Lieutenant General", "General" };
        String[] navyRanks = { "Sub Lieutenant", "Lieutenant", "Lieutenant Commander", "Commander", "Captain",
                "Commodore", "Rear Admiral", "Vice Admiral", "Admiral" };
        String[] airforceRanks = { "Flying Officer", "Flight Lieutenant", "Squadron Leader", "Wing Commander",
                "Group Captain", "Air Commodore", "Air Vice Marshal", "Air Marshal", "Air Chief Marshal" };
        String[] ranks;
        switch (unit) {
            case "Navy":
                ranks = navyRanks;
                break;
            case "Airforce":
                ranks = airforceRanks;
                break;
            default:
                ranks = armyRanks;
        }
        for (String r : ranks)
            rankCombo.addItem(r);
    }

    private void markSelectedSoldierMissing() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a soldier to mark as missing.");
            return;
        }
        String id = (String) tableModel.getValueAt(row, 1);
        for (Soldier s : soldiers) {
            if (s.getId().equals(id)) {
                s.setStatus("missing");
                tableModel.setValueAt("missing", row, 6);
                soldierDb.updateSoldierStatus(id, "missing");
                JOptionPane.showMessageDialog(this, "Soldier marked as missing.");
                return;
            }
        }
    }

    private void removeSelectedSoldierFromMissing() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a soldier to remove from missing.");
            return;
        }
        String id = (String) tableModel.getValueAt(row, 1);
        for (Soldier s : soldiers) {
            if (s.getId().equals(id)) {
                if (s.getStatus().equals("missing")) {
                    s.setStatus("active");
                    tableModel.setValueAt("active", row, 6);
                    soldierDb.updateSoldierStatus(id, "active");
                    JOptionPane.showMessageDialog(this, "Soldier removed from missing status.");
                } else {
                    JOptionPane.showMessageDialog(this, "Soldier is not marked as missing.");
                }
                return;
            }
        }
    }

    private void showSelectedSoldierDetails() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a soldier to view details.");
            return;
        }
        String id = (String) tableModel.getValueAt(row, 1);
        for (Soldier s : soldiers) {
            if (s.getId().equals(id)) {
                StringBuilder sb = new StringBuilder();
                sb.append("Name: ").append(s.getName()).append("\n");
                sb.append("ID: ").append(s.getId()).append("\n");
                sb.append("Rank: ").append(s.getRank()).append("\n");
                sb.append("Unit: ").append(s.getUnit()).append("\n");
                sb.append("Latitude: ").append(s.getLatitude()).append("\n");
                sb.append("Longitude: ").append(s.getLongitude()).append("\n");
                sb.append("Status: ").append(s.getStatus());
                JOptionPane.showMessageDialog(this, sb.toString(), "Soldier Details", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainGUI().setVisible(true);
        });
    }
}

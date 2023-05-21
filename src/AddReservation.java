import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddReservation extends JFrame {
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton saveButton;
    private JButton backButton;
    private JTable table1;
    private JButton deleteButton;
    private Connection connection;

    public AddReservation() {
        setTitle("Add Reservation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setContentPane(panel1);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        connectToDatabase();
        loadStockDataFromDatabase();

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int tablenumber = Integer.parseInt(textField1.getText());
                String namesurname = textField2.getText();
                String date = (textField3.getText());
                String time =(textField4.getText());
                int partysize = Integer.parseInt(textField5.getText());

                try {
                    String query = "INSERT INTO addreserv (tablenumber, namesurname, date, time, partysize) VALUES (?, ?, ?, ?, ?)";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, tablenumber);
                    statement.setString(2, namesurname);
                    statement.setString(3, date);
                    statement.setString(4, time);
                    statement.setInt(5, partysize);
                    statement.executeUpdate();
                    loadStockDataFromDatabase();
                    textField1.setText("");
                    textField2.setText("");
                    textField3.setText("");
                    textField4.setText("");
                    textField5.setText("");

                    JOptionPane.showMessageDialog(null, "Data saved successfully.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred while saving data.");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                HomePage homePage = new HomePage();
                homePage.setVisible(true);
                setVisible(false);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a row to delete.");
                    return;
                }
                int id = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());
                try {
                    String query = "DELETE FROM addreserv WHERE tablenumber = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setInt(1, id);

                    statement.executeUpdate();
                    loadStockDataFromDatabase();
                    textField1.setText("");
                    textField2.setText("");
                    textField3.setText("");
                    textField4.setText("");
                    textField5.setText("");

                    JOptionPane.showMessageDialog(null, "Data deleted successfully.");
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

        private void connectToDatabase() {
            try {

                final String DB_URL = "jdbc:mysql://localhost/20200305025";
                final String USERNAME = "root";
                final String PASSWORD = "karakartal123";
                connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



    private void loadStockDataFromDatabase() {
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM addreserv";
            PreparedStatement statement1 = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery(query);

            String[] columnNames = {"tablenumber", "namesurname", "date", "time", "partysize"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            while (resultSet.next()) {
                int tablenumber = resultSet.getInt("tablenumber");
                String namesurname = resultSet.getString("namesurname");
                String date = resultSet.getString("date");
                String time = resultSet.getString("time");
                int partysize = resultSet.getInt("partysize");

                Object[] rowData = {tablenumber, namesurname, date, time, partysize};
                model.addRow(rowData);
            }

            table1.setModel(model);

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
    public static void main (String[]args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AddReservation();
            }
        });
    }
}



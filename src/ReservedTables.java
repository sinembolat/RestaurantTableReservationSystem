import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ReservedTables extends JFrame {
    private JTable table1;
    private JButton back;
    private JPanel panel;
    private Connection connection;



       public ReservedTables() {

        setTitle("Reserved Tables");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setContentPane(panel);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        connectToDatabase();
        loadStockDataFromDatabase();

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                HomePage homePage = new HomePage();
                homePage.setVisible(true);
                setVisible(false);
            }
        });
    }

    private void connectToDatabase() {
        try {

            String url = "jdbc:mysql://localhost/20200305025";
            String username = "root";
            String password = "karakartal123";

            connection = DriverManager.getConnection(url, username, password);
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

            String[] columnNames = {"tablenumber", "namesurname", "date","time","partysize"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);

            while (resultSet.next()) {
                int tablenumber = resultSet.getInt("tablenumber");
                String namesurname = resultSet.getString("namesurname");
                String date = resultSet.getString("date");
                String time = resultSet.getString("time");
                int partysize = resultSet.getInt("partysize");

                Object[] rowData = {tablenumber, namesurname, date,time,partysize};
                model.addRow(rowData);
            }
            table1.setModel(model);

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ReservedTables();
            }
        });
    }
}








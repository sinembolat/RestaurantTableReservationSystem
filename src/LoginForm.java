import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class  LoginForm extends JFrame {
    private Connection connection;
    private JButton loginButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel loginscreen;
    private JButton EXITButton;
    private PreparedStatement preparedStatement;


    public LoginForm() {
        setTitle("Login Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setContentPane(loginscreen);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);


                if (authenticate(username, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    setVisible(false);
                    HomePage homePage = new HomePage();
                    homePage.setVisible(true);
                    setVisible(false);

                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);

                }
            }

            private boolean authenticate(String username, String password) {
                try {
                    String query = "SELECT * FROM users WHERE ID = ? AND password = ?";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, password);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    return resultSet.next();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
            }

        });


        setVisible(true);

        connectToDatabase();

        EXITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                {
                    System.exit(0);
                }
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



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm();
            }
        });
    }
}



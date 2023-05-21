import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    private JButton GOButton;
    private JButton GOButton1;
    private JButton EXITButton;
    private JPanel panel;

    public HomePage() {

    setTitle("Home Page");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(500,600);
    setLocationRelativeTo(null);
    setContentPane(panel);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        GOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                ReservedTables reservedTables = new ReservedTables();
                reservedTables.setVisible(true);
                setVisible(false);
            }
        });
        GOButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                AddReservation addReservation = new AddReservation();
                addReservation.setVisible(true);
                setVisible(false);

            }
        });
        EXITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {new HomePage();}


        });
    }
    }
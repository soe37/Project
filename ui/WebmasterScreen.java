import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WebmasterScreen extends JFrame implements ActionListener {
    private JButton hotelsButton;
    private JButton usersButton;

    public WebmasterScreen() {
        setTitle("Webmaster Screen");
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel label = new JLabel("Welcome, "+Main.getLoggedInUser().getUsername());
        panel.add(label);

        hotelsButton = new JButton("Hotels");
        hotelsButton.addActionListener(this);
        panel.add(hotelsButton);

        usersButton = new JButton("Users");
        usersButton.addActionListener(this);
        panel.add(usersButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == hotelsButton) {
            // Open HotelSearchScreen when "Find room" button is clicked
            new HotelsScreen();
        } else if (e.getSource() == usersButton) {
            // Open BookingHistoryScreen when "Booking history" button is clicked
            new UsersScreen();
        }
    }
}

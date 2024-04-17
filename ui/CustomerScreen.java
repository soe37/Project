import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomerScreen extends JFrame implements ActionListener {
    private JButton findRoomButton;
    private JButton bookingHistoryButton;

    public CustomerScreen() {
        setTitle("Customer Screen");
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel label = new JLabel("Welcome, "+Main.getLoggedInUser().getUsername());
        panel.add(label);

        findRoomButton = new JButton("Find room");
        findRoomButton.addActionListener(this);
        panel.add(findRoomButton);

        bookingHistoryButton = new JButton("Booking history");
        bookingHistoryButton.addActionListener(this);
        panel.add(bookingHistoryButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == findRoomButton) {
            new HotelSearchScreen();
        } else if (e.getSource() == bookingHistoryButton) {
            User user = Main.getLoggedInUser();
            int userId = user.getId();

            new BookingHistoryScreen(userId);
        }
    }
}

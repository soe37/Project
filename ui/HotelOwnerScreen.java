import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HotelOwnerScreen extends JFrame implements ActionListener {
    private JButton roomsButton;
    private JButton changeHotelInformationButton;
    private int hotelId;

    public HotelOwnerScreen(int hotelId) {
        this.hotelId = hotelId;

        setTitle("HotelOwner Screen");
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JLabel label = new JLabel("Welcome, "+Main.getLoggedInUser().getUsername());
        panel.add(label);

        roomsButton = new JButton("Rooms");
        roomsButton.addActionListener(this);
        panel.add(roomsButton);

        changeHotelInformationButton = new JButton("Change hotel information");
        changeHotelInformationButton.addActionListener(this);
        panel.add(changeHotelInformationButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == roomsButton) {
            // Open HotelSearchScreen when "Find room" button is clicked
            new RoomsScreen(hotelId);
        } else if (e.getSource() == changeHotelInformationButton) {
            // Open BookingHistoryScreen when "Booking history" button is clicked
            new HotelInformationScreen(hotelId);
        }
    }
}

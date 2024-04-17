import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BookingScreen extends JFrame {

    public BookingScreen(RoomType roomType, LocalDate checkInDate, LocalDate checkOutDate) {
        setTitle("Booking Details");
        setSize(300, 300); // Increased height to accommodate the buttons
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1)); // Increased rows for the buttons

        int numberOfNights = (int) ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        int totalPrice = numberOfNights * roomType.getPricePerNight();

        JLabel checkInLabel = new JLabel("Check in: " + checkInDate);
        panel.add(checkInLabel);

        JLabel checkOutLabel = new JLabel("Check out: " + checkOutDate);
        panel.add(checkOutLabel);

        JLabel nightsLabel = new JLabel("Number of nights: " + numberOfNights);
        panel.add(nightsLabel);

        JLabel totalPriceLabel = new JLabel("Total price: $" + totalPrice);
        panel.add(totalPriceLabel);

        JButton bookButton = new JButton("Book room");
        bookButton.addActionListener(e -> {
            int option = JOptionPane.showConfirmDialog(this, "Do you want to confirm booking?", "Booking Confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                User user = Main.getLoggedInUser();

                int status = Main.bookingController.makeBooking(user, roomType, checkInDate, checkOutDate, totalPrice);

                if(status == 1) {
                    JOptionPane.showMessageDialog(this, "Booking confirmed!", "Booking Confirmation", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(this, "Booking failed: Room is not available", "Booking Failed", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        panel.add(bookButton);

        add(panel);
        setVisible(true);
    }
}
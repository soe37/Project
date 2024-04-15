// CustomerScreen.java
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BookingHistoryScreen extends JFrame {
    public BookingHistoryScreen(int userId) {
        setTitle("BookingHistory Screen");
        setSize(600, 400); // Adjusted size for better visibility
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Booking History");
        panel.add(titleLabel, BorderLayout.NORTH);

        List<Booking> bookings = Main.bookingController.getBookingsByUserId(userId);
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        if (bookings.isEmpty()) {
            textArea.setText("No bookings found for this user.");
        } else {
            StringBuilder sb = new StringBuilder();
            for (Booking booking : bookings) {
                Room room = Main.roomController.getRoomById(booking.getRoomId());
                int roomTypeId = room.getRoomTypeId();
                RoomType roomType = Main.roomTypeController.getRoomTypeById(roomTypeId);

                sb.append("Booking ID: ").append(booking.getId()).append("\n");
                sb.append("Room Type: ").append(roomType.getName()).append("\n");
                sb.append("Check In Date: ").append(booking.getCheckInDate()).append("\n");
                sb.append("Check Out Date: ").append(booking.getCheckOutDate()).append("\n");
                sb.append("Total Price: ").append(booking.getTotalPrice()).append("\n\n");
            }
            textArea.setText(sb.toString());
        }

        add(panel);
        setVisible(true);
    }
}

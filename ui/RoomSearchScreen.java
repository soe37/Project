import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AbstractDocument;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;

public class RoomSearchScreen extends JFrame {


    private JDateChooser checkInDateChooser;
    private JDateChooser checkOutDateChooser;
    private JSpinner numberOfPersonsSpinner;
    private JTextField maxPriceField;

    private JPanel resultsPanel;
    private GridBagConstraints gbc;

    public RoomSearchScreen(int hotelId) {
        setTitle("Room Search Screen");
        setSize(600, 400); // Increased size for better visibility
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        int highestCapacity = Main.roomTypeController.highestCapacityByHotelId(hotelId);
        int highestPrice = Main.roomTypeController.highestPriceByHotelId(hotelId);

        JLabel checkInLabel = new JLabel("Check In Date:");
        JLabel checkOutLabel = new JLabel("Check Out Date:");
        JLabel numberOfPersonsLabel = new JLabel("Number of Persons (Max " + highestCapacity + "):");
        JLabel maxPriceLabel = new JLabel("Maximum Price per Night (Max " + highestPrice + "):" );
        checkInDateChooser = new JDateChooser();
        checkOutDateChooser = new JDateChooser();

        checkInDateChooser.getDateEditor().setEnabled(false);

        numberOfPersonsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, highestCapacity, 1));
        maxPriceField = new JTextField();

        JButton searchButton = new JButton("Search");


        searchPanel.add(checkInLabel, gbc);
        gbc.gridx++;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Set the fill constraint to make components expand horizontally
        gbc.weightx = 1.0; // Make components fill the available horizontal space
        searchPanel.add(checkInDateChooser, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(checkOutLabel, gbc);
        gbc.gridx++;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Set the fill constraint to make components expand horizontally
        gbc.weightx = 1.0; // Make components fill the available horizontal space
        searchPanel.add(checkOutDateChooser, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(numberOfPersonsLabel, gbc);
        gbc.gridx++;
        searchPanel.add(numberOfPersonsSpinner, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        searchPanel.add(maxPriceLabel, gbc);
        gbc.gridx++;
        searchPanel.add(maxPriceField, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        searchPanel.add(searchButton, gbc);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new GridBagLayout());
        JScrollPane resultsScrollPane = new JScrollPane(resultsPanel);
        resultsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(resultsScrollPane, BorderLayout.CENTER);


        List<Room> allRooms = Main.roomController.getRoomsByHotelId(hotelId);

        if (allRooms.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "There are no rooms for this hotel", "No Rooms Found", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Apply document filter to restrict input to integers and enforce maximum value
        ((AbstractDocument) maxPriceField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder(doc.getText(0, doc.getLength()));
                sb.insert(offset, string);
                if (test(sb.toString())) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder(doc.getText(0, doc.getLength()));
                sb.replace(offset, offset + length, text);
                if (test(sb.toString())) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean test(String text) {
                try {
                    int value = Integer.parseInt(text);
                    return value <= highestPrice;
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });
        
        // Set the minimum selectable date to today's date
        checkInDateChooser.setMinSelectableDate(new Date());
        // Disable the check-out date chooser initially
        checkOutDateChooser.setEnabled(false);

        // Add a property change listener to the check-in date chooser
        checkInDateChooser.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                // When the check-in date changes, set the minimum selectable date for the check-out date chooser to the selected check-in date
                Date checkInDate = checkInDateChooser.getDate();
                if (checkInDate != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(checkInDate);
                    calendar.add(Calendar.DATE, 1);
                    Date minSelectableDate = calendar.getTime();
                    checkOutDateChooser.setMinSelectableDate(minSelectableDate);
                    // Enable the check-out date chooser
                    checkOutDateChooser.setEnabled(true);

                    checkOutDateChooser.getDateEditor().setEnabled(false);
                } else {
                    // If check-in date is cleared, disable the check-out date chooser again
                    checkOutDateChooser.setEnabled(false);
                }
            }
        });


        // Add a property change listener to the check-out date chooser
        checkOutDateChooser.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                // When the check-out date changes, set the maximum selectable date for the check-in date chooser to the selected check-out date
                Date checkOutDate = checkOutDateChooser.getDate();
                if (checkOutDate != null) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(checkOutDate);
                    calendar.add(Calendar.DATE, -1);
                    Date maxSelectableDate = calendar.getTime();
                    checkInDateChooser.setMaxSelectableDate(maxSelectableDate);
                }
            }
        });



        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Retrieve selected dates
                Date checkInDate = checkInDateChooser.getDate();
                Date checkOutDate = checkOutDateChooser.getDate();
                int numberOfPersons = (int) numberOfPersonsSpinner.getValue();
                
                int maxPrice;

                String maxPriceText = maxPriceField.getText();
                
                if (checkInDate == null || checkOutDate == null || maxPriceText.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "Please complete all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Exit the method
                }

                maxPrice = Integer.parseInt(maxPriceText);

                LocalDate checkInLocalDate = checkInDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate checkOutLocalDate = checkOutDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            

                List<RoomType> roomTypeSearchResult = Main.roomTypeController.roomTypeSearch(checkInLocalDate, checkOutLocalDate, numberOfPersons, maxPrice, hotelId);
            

                resultsPanel.removeAll();

                // Add the results to the resultsPanel
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.insets = new Insets(5, 5, 5, 5);
                for (RoomType roomType : roomTypeSearchResult) {
                    String roomTypeInfo = String.format("%s, Capacity: %d, Price per night: %d", roomType.getName(), roomType.getCapacity(), roomType.getPricePerNight());
                    JLabel label = new JLabel(roomTypeInfo);

                    JButton viewPhotosButton = new JButton("View photos");
                    viewPhotosButton.addActionListener(event -> {
                        // Handle selection logic here
                        ImageGalleryScreen galleryScreen = new ImageGalleryScreen();
                        galleryScreen.setVisible(true);

                        int roomTypeId = roomType.getId();
                        File directory = new File("photos\\roomType\\" + roomTypeId);
                        galleryScreen.showImagesInFolder(directory);
                    });

                    JButton selectButton = new JButton("Select");
                    selectButton.addActionListener(event -> {
                        // Handle selection logic here

                        new BookingScreen(roomType,checkInLocalDate,checkOutLocalDate);
                        System.out.println("Room type selected: " + roomType.getName());
                    });
                    
                    // Create a new panel for each hotel
                    JPanel roomTypePanel = new JPanel(new GridBagLayout());
                    GridBagConstraints roomTypePanelGbc = new GridBagConstraints();
                    roomTypePanelGbc.gridx = 0;
                    roomTypePanelGbc.gridy = 0;
                    roomTypePanel.add(label, roomTypePanelGbc);
                    roomTypePanelGbc.gridx++;
                    roomTypePanel.add(Box.createHorizontalStrut(10), roomTypePanelGbc); // Add some horizontal space
                    roomTypePanelGbc.gridx++;
                    roomTypePanel.add(viewPhotosButton, roomTypePanelGbc);
                    roomTypePanelGbc.gridx++;
                    roomTypePanel.add(selectButton, roomTypePanelGbc);
                    
                    // Add the hotel panel to the results panel
                    resultsPanel.add(roomTypePanel, gbc);
                    gbc.gridy++;
                }

                // Refresh the resultsPanel
                resultsPanel.revalidate();
                resultsPanel.repaint();
            }
        });

        add(panel);
        setVisible(true);
    }
}

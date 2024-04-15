import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;

public class PhotoUploaderScreen extends JFrame implements ActionListener {
    private JButton addPhotoButton;
    private JPanel photoPanel;
    private File directory;

    public PhotoUploaderScreen(File directory) {
        super("Photo Uploader");
        this.directory = directory;

        addPhotoButton = new JButton("Add Photo");
        addPhotoButton.addActionListener(this);

        photoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JScrollPane scrollPane = new JScrollPane(photoPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(addPhotoButton, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);

        displayPhotos();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPhotoButton) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    // Destination folder to save the photo
                    Path destinationPath = directory.toPath().resolve(selectedFile.getName());

                    // Copy the selected file to the destination folder
                    Files.copy(selectedFile.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                    JOptionPane.showMessageDialog(this, "Photo saved successfully!");
                    displayPhotos(); // Refresh the displayed photos
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            // Handle delete button click
            JButton deleteButton = (JButton) e.getSource();
            String photoPath = (String) deleteButton.getClientProperty("photoPath");
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this photo?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                deletePhoto(photoPath);
                displayPhotos(); // Refresh the displayed photos
            }
        }
    }

    private void displayPhotos() {
        // Clear existing photos
        photoPanel.removeAll();
    
        // Read photos from folder and display them
        File[] photoFiles = directory.listFiles();
        if (photoFiles != null) {
            for (File photoFile : photoFiles) {
                if (photoFile.isFile()) {
                    ImageIcon originalIcon = new ImageIcon(photoFile.getAbsolutePath());
                    int maxWidth = 600; // Set the maximum width for the displayed photos
                    int scaledWidth = originalIcon.getIconWidth() > maxWidth ? maxWidth : originalIcon.getIconWidth();
                    int scaledHeight = originalIcon.getIconHeight() * scaledWidth / originalIcon.getIconWidth();
                    Image scaledImage = originalIcon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
                    ImageIcon scaledIcon = new ImageIcon(scaledImage);
                    JLabel photoLabel = new JLabel(scaledIcon);
    
                    // Create a layered pane to overlay delete button on photo
                    JLayeredPane layeredPane = new JLayeredPane();
                    layeredPane.setPreferredSize(new Dimension(scaledWidth, scaledHeight)); // Set preferred size to accommodate scaled image and delete button
    
                    // Add photo label
                    photoLabel.setBounds(0, 0, scaledWidth, scaledHeight);
                    layeredPane.add(photoLabel, JLayeredPane.DEFAULT_LAYER);
    
                    // Add delete button
                    JButton deleteButton = new JButton("X");
                    deleteButton.addActionListener(this);
                    deleteButton.putClientProperty("photoPath", photoFile.getAbsolutePath());
                    deleteButton.setBounds(scaledWidth - 45, 0, 45, 45); // Position delete button in upper right corner
                    layeredPane.add(deleteButton, JLayeredPane.POPUP_LAYER);
    
                    // Add layered pane to photoPanel
                    photoPanel.add(layeredPane);
                }
            }
        }
    
        // Update the panel to reflect the changes
        photoPanel.revalidate();
        photoPanel.repaint();
    }
    

    private void deletePhoto(String photoPath) {
        // Delete the photo file
        File photoFile = new File(photoPath);
        if (photoFile.exists()) {
            photoFile.delete();
        }
    }
}
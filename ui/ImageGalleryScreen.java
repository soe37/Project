import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageGalleryScreen extends JFrame {
    private JLabel imageLabel;
    private File[] imageFiles;
    private int currentIndex;

    public ImageGalleryScreen() {
        setTitle("Image Gallery");
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel controlPanel = new JPanel();
        JButton prevButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);

        controlPanel.add(prevButton);
        controlPanel.add(nextButton);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(controlPanel, BorderLayout.SOUTH);

        add(panel);

        prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentIndex > 0) {
                    currentIndex--;
                    displayImage(imageFiles[currentIndex]);
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentIndex < imageFiles.length - 1) {
                    currentIndex++;
                    displayImage(imageFiles[currentIndex]);
                }
            }
        });
    }

    private void displayImage(File file) {
        if (imageLabel.getWidth() == 0 || imageLabel.getHeight() == 0) {
            return;
        }
    
        ImageIcon icon = new ImageIcon(file.getAbsolutePath());
        int labelWidth = imageLabel.getWidth();
        int labelHeight = imageLabel.getHeight();
        int imageWidth = icon.getIconWidth();
        int imageHeight = icon.getIconHeight();
    
        double widthRatio = (double) labelWidth / imageWidth;
        double heightRatio = (double) labelHeight / imageHeight;
        double scaleFactor = Math.min(widthRatio, heightRatio);
    
        int scaledWidth = (int) (scaleFactor * imageWidth);
        int scaledHeight = (int) (scaleFactor * imageHeight);
        Image image = icon.getImage().getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(image));
    }
    

    public void showImagesInFolder(File directory) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");
        imageFiles = directory.listFiles((dir, name) -> filter.accept(new File(dir, name)));
        if (imageFiles != null && imageFiles.length > 0) {
            displayImage(imageFiles[0]);
            currentIndex = 0;
        } else {
            JOptionPane.showMessageDialog(this, "No images found in the specified folder.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }
}
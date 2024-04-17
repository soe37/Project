import javax.swing.*;

public class HotelsScreen extends JFrame {
    public HotelsScreen() {
        setTitle("Hotels Screen");
        setSize(300, 200);
        setLocationRelativeTo(null);

        JLabel label = new JLabel("Hotels");
        add(label);

        setVisible(true);
    }
}

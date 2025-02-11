package UIC.Form;

import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Infra.Style;
import UIC.CustomerControl.PatButton;

public class AppMenuPanel extends JPanel {
    public  PatButton   
            btnHome     = new PatButton(" Home"),
            btnHomiga   = new PatButton(" Hormiga ");

    public AppMenuPanel() {
        customizeComponent();
    }

    private void customizeComponent()  {
        Image logo;
        try {
            logo = ImageIO.read(Style.URL_LOGO);
            logo = logo.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setPreferredSize(new Dimension(200, getHeight())); 
            
            // add-controls
            add(new JLabel(new ImageIcon(logo)));
            add(btnHome);
            add(btnHomiga);
        } catch (IOException e) {
            Style.showMsgError("No se encuentra la imagen");
        }
    }
}

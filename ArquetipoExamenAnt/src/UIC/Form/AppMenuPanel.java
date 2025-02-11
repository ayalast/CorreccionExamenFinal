package UIC.Form;

import Infra.Style;
import UIC.CustomerControl.PatButton;
import java.awt.Dimension;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AppMenuPanel extends JPanel {
    public  PatButton   
            btnHome     = new PatButton(" Home"),
            btnHomiga   = new PatButton(" Hormiga "),
            btnEcuaAnt  = new PatButton(" EcuaAnt 2K24A ");

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
            add(btnEcuaAnt);
        } catch (IOException e) {
            Style.showMsgError("No se encuentra la imagen");
        }
    }
}

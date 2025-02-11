package UIC.Form;

import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Infra.Style;

public class AppMainPanel extends JPanel{
    public AppMainPanel(){
        customizeComponent();
    }

    private void customizeComponent() {
        try {
            ImageIcon imageIcon = new ImageIcon(Style.URL_MAIN);
            JLabel label = new JLabel(imageIcon);

            setLayout(new GridBagLayout());  
            add(label);
        } catch (Exception e) {
            Style.showMsgError("No se encuentra la imagen");
        }
    }
}

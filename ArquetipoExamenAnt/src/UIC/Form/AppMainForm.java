package UIC.Form;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class AppMainForm extends JFrame{
    AppMenuPanel pnlMenu = new AppMenuPanel();
    JPanel       pnlMain = new AppMainPanel();
 
    public AppMainForm(String tilteApp) throws IOException {
        customizeComponent(tilteApp);
        pnlMenu.btnHome.addActionListener(      e -> setPanel(new AppMainPanel())); 
        pnlMenu.btnHomiga.addActionListener ( e -> setPanel(new HormigaPanel())); 
        pnlMenu.btnEcuaAnt.addActionListener( e -> setPanel(new EcuaAntPanel()));
    }

    private void setPanel(JPanel formularioPanel) {
        Container container = getContentPane();
        container.remove(pnlMain);
        pnlMain = formularioPanel;
        container.add(pnlMain, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
     
    private void customizeComponent(String tilteApp) {
        setTitle(tilteApp);
        setSize(900, 700);
        setResizable(false);
        setLocationRelativeTo(null); // Centrar en la pantalla
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Crear un contenedor para los dos paneles usando BorderLayout
        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        // Agregar los paneles al contenedor
        container.add(pnlMenu, BorderLayout.WEST);
        container.add(pnlMain, BorderLayout.CENTER);
        setVisible(true);
    }   
}
package UIC.Form;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class EcuaAntPanel extends JPanel {
    private JComboBox<String> cmbGenoAlimento;
    private JComboBox<String> cmbIngestaNativa;
    private JLabel lblStatus;
    private JPanel pnlGenoHormiguero;
    private JTable tblHormigas;
    private DefaultTableModel modeloTabla;
    private JButton btnCrearLarva, btnAlimentar, btnEliminar, btnGuardar;
    private static final String RESOURCE_PATH = "/UIC/Resource/";
    private static final Color LIGHT_BLUE = new Color(230, 240, 255);
    private static final Color SELECTED_BLUE = new Color(200, 220, 255);

    public EcuaAntPanel() {
        customizeComponent();
    }

    private void customizeComponent() {
        setLayout(new BorderLayout(0, 0));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Panel para el logo
        JPanel logoPanel = new JPanel(new BorderLayout());
        logoPanel.setPreferredSize(new Dimension(getWidth(), 300)); // Altura fija para el logo
        
        // Logo
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(RESOURCE_PATH + "Logo.png"));
            Image img = icon.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            JLabel lblLogo = new JLabel(new ImageIcon(img));
            lblLogo.setHorizontalAlignment(JLabel.CENTER);
            lblLogo.setVerticalAlignment(JLabel.CENTER);
            logoPanel.add(lblLogo, BorderLayout.CENTER);
            mainPanel.add(logoPanel);
            mainPanel.add(Box.createVerticalStrut(20)); // Espacio después del logo
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar el logo", "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Panel Geno Hormiguero
        pnlGenoHormiguero = new JPanel();
        pnlGenoHormiguero.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            "GENO HORMIGUERO EXPERIMENTAL",
            TitledBorder.CENTER,
            TitledBorder.TOP));
        pnlGenoHormiguero.setLayout(new BorderLayout(5, 5));

        // Tabla
        String[] columnas = {"IdHormiga", "TipoHormiga", "Sexo", "Alimentación", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblHormigas = new JTable(modeloTabla);
        
        // Configurar el aspecto de la tabla
        tblHormigas.setRowHeight(25);
        tblHormigas.setGridColor(Color.LIGHT_GRAY);
        tblHormigas.setShowGrid(true);
        tblHormigas.setIntercellSpacing(new Dimension(1, 1));
        tblHormigas.getTableHeader().setReorderingAllowed(false);
        tblHormigas.getTableHeader().setBackground(LIGHT_BLUE);
        tblHormigas.setSelectionBackground(SELECTED_BLUE);
        
        // Centrar el contenido de las celdas y ajustar el ancho de las columnas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblHormigas.getColumnCount(); i++) {
            TableColumn column = tblHormigas.getColumnModel().getColumn(i);
            column.setCellRenderer(centerRenderer);
            if (i == 3) { // Columna Alimentación más ancha
                column.setPreferredWidth(200);
            } else {
                column.setPreferredWidth(100);
            }
        }

        JScrollPane scrollPane = new JScrollPane(tblHormigas);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        pnlGenoHormiguero.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior con combos y botones
        JPanel pnlInferior = new JPanel();
        pnlInferior.setLayout(new BoxLayout(pnlInferior, BoxLayout.Y_AXIS));
        
        // Panel de combos
        JPanel pnlCombos = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        cmbGenoAlimento = new JComboBox<>(new String[]{"X", "XX", "XY"});
        cmbIngestaNativa = new JComboBox<>(new String[]{"Nectarívoros", "Carnívoro", "Omnívoro", "Herbívoro", "Insectívoro"});
        
        // Ajustar tamaño de los combos
        Dimension comboSize = new Dimension(120, 25);
        cmbGenoAlimento.setPreferredSize(comboSize);
        cmbIngestaNativa.setPreferredSize(new Dimension(150, 25));  // Aumentado el ancho para las opciones más largas
        
        // Agregar los combos con espacio entre ellos
        pnlCombos.add(cmbGenoAlimento);
        pnlCombos.add(Box.createHorizontalStrut(10));  // Aumentado el espacio entre combos
        pnlCombos.add(cmbIngestaNativa);
        pnlInferior.add(pnlCombos);

        // Panel de botones
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        btnCrearLarva = createStyledButton("Crear Larva");
        btnAlimentar = createStyledButton("Alimentar");
        btnEliminar = createStyledButton("Eliminar");
        btnGuardar = createStyledButton("Guardar");

        pnlBotones.add(btnCrearLarva);
        pnlBotones.add(btnAlimentar);
        pnlBotones.add(btnEliminar);
        pnlBotones.add(btnGuardar);
        pnlInferior.add(pnlBotones);

        pnlGenoHormiguero.add(pnlInferior, BorderLayout.SOUTH);
        mainPanel.add(pnlGenoHormiguero);

        add(mainPanel, BorderLayout.CENTER);

        // Status Bar
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(BorderFactory.createEtchedBorder());
        lblStatus = new JLabel("Programación II | Cédula: 0402059521 | Nombres: Bryan Steeven Ayala Pabón");
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 12));
        statusPanel.add(lblStatus);
        add(statusPanel, BorderLayout.SOUTH);

        // Agregar datos iniciales
        modeloTabla.addRow(new Object[]{"1", "Larva", "Asexual", "", "Viva"});
        modeloTabla.addRow(new Object[]{"2", "Larva", "Asexual", "", "Viva"});
        modeloTabla.addRow(new Object[]{"3", "Larva", "Asexual", "", "Viva"});
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 25));
        button.setBackground(LIGHT_BLUE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        return button;
    }

    // ... rest of the code (event handlers) stays the same ...
} 
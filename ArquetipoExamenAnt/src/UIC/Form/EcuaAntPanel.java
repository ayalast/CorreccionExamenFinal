package UIC.Form;

import BLC.Interface.IEntomologo;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;

public class EcuaAntPanel extends JPanel implements IEntomologo {
    private JComboBox<String> cmbGenoAlimento;
    private JComboBox<String> cmbIngestaNativa;
    private JLabel lblStatus;
    private JPanel pnlGenoHormiguero;
    private JTable tblHormigas;
    private DefaultTableModel modeloTabla;
    private JButton btnCrearLarva, btnAlimentar, btnEliminar, btnGuardar, btnEntrenar;
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
        String[] columnas = {"IdHormiga", "TipoHormiga", "Sexo", "Alimentación", "Estado", "Entrenada"};
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
        btnEntrenar = createStyledButton("Entrenar");

        // Agregar action listeners
        btnCrearLarva.addActionListener(e -> crearLarva());
        btnAlimentar.addActionListener(e -> alimentarHormiga());
        btnEliminar.addActionListener(e -> eliminarHormiga());
        btnGuardar.addActionListener(e -> guardarHormiguero());
        btnEntrenar.addActionListener(e -> entrenarHormiga());

        pnlBotones.add(btnCrearLarva);
        pnlBotones.add(btnAlimentar);
        pnlBotones.add(btnEliminar);
        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnEntrenar);
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
        modeloTabla.addRow(new Object[]{"1", "Larva", "Asexual", "", "Viva", "NO"});
        modeloTabla.addRow(new Object[]{"2", "Larva", "Asexual", "", "Viva", "NO"});
        modeloTabla.addRow(new Object[]{"3", "Larva", "Asexual", "", "Viva", "NO"});
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

    private void crearLarva() {
        int opcion = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de crear una Hormiga larva?",
            "Confirmar Creación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            // Obtener el siguiente ID secuencial
            int nextId = modeloTabla.getRowCount() + 1;
            
            // Crear nuevo registro
            Object[] newRow = {
                nextId,          // IdHormiga (secuencial)
                "Larva",         // TipoHormiga
                "Asexual",       // Sexo
                "",             // Alimentación (vacío inicialmente)
                "VIVA",          // Estado
                "NO"            // Entrenada
            };
            
            // Agregar el registro a la tabla
            modeloTabla.addRow(newRow);
            
            // Mostrar mensaje de confirmación
            JOptionPane.showMessageDialog(
                this,
                "HORMIGA LARVA, agregada al hormiguero",
                "Creación Exitosa",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void alimentarHormiga() {
        int filaSeleccionada = tblHormigas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione una hormiga para alimentar", 
                "Selección requerida", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verificar si la hormiga ya está muerta
        String estadoActual = (String) modeloTabla.getValueAt(filaSeleccionada, 4);
        if (estadoActual.equals("MUERTA")) {
            JOptionPane.showMessageDialog(this,
                "No se puede alimentar a una hormiga muerta",
                "Acción no permitida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tipoHormiga = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        String ingestaNativa = (String) cmbIngestaNativa.getSelectedItem();
        String genoAlimento = (String) cmbGenoAlimento.getSelectedItem();
        
        // Preparar la cadena de alimentación
        String alimentacion = String.format("%s inyectada con %s", ingestaNativa, genoAlimento);
        
        // Verificar si la hormiga vive según las reglas del caso B
        boolean vive = false;
        boolean seTransforma = false;
        
        // Reglas de supervivencia y transformación
        if (tipoHormiga.equals("Larva")) {
            if (ingestaNativa.equals("Nectarívoros")) {
                // La larva sobrevive con Nectarívoros y cualquier genoalimento
                vive = true;
            } else if (ingestaNativa.equals("Omnívoro") && genoAlimento.equals("XY")) {
                // La larva se transforma en Zángano
                vive = true;
                seTransforma = true;
            }
        } else if (tipoHormiga.equals("Zángano") && ingestaNativa.equals("Omnívoro") && genoAlimento.equals("XY")) {
            vive = true; // El Zángano sobrevive si come Omnívoro con XY
        }

        // Actualizar el estado y la alimentación en la tabla
        String estado = vive ? "VIVA" : "MUERTA";
        modeloTabla.setValueAt(alimentacion, filaSeleccionada, 3);
        modeloTabla.setValueAt(estado, filaSeleccionada, 4);

        // Si la hormiga se transforma, actualizar tipo y sexo
        if (seTransforma) {
            modeloTabla.setValueAt("Zángano", filaSeleccionada, 1); // Cambiar tipo a Zángano
            modeloTabla.setValueAt("MACHO", filaSeleccionada, 2);   // Cambiar sexo a MACHO
        }

        // Mostrar mensaje del resultado
        String mensaje;
        if (seTransforma) {
            mensaje = String.format("%s, alimentada y transformada a Zángano MACHO", tipoHormiga);
        } else if (vive) {
            mensaje = String.format("%s, alimentada", tipoHormiga);
        } else {
            mensaje = String.format("Ups...! tenemos problema con la alimentación de la %s", tipoHormiga);
        }

        JOptionPane.showMessageDialog(this, 
            mensaje,
            "Resultado de Alimentación",
            vive ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }

    private void eliminarHormiga() {
        int filaSeleccionada = tblHormigas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, seleccione una hormiga para eliminar", 
                "Selección requerida", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tipoHormiga = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        
        int opcion = JOptionPane.showConfirmDialog(
            this,
            String.format("¿Está seguro de eliminar la %s?", tipoHormiga),
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (opcion == JOptionPane.YES_OPTION) {
            // Cambiar el estado a MUERTA
            modeloTabla.setValueAt("MUERTA", filaSeleccionada, 4);
        }
    }

    private void guardarHormiguero() {
        try {
            // Aquí iría la lógica para guardar en la base de datos
            // Por ahora simulamos el guardado
            boolean exitoGuardado = true; // Simular éxito del guardado
            
            if (exitoGuardado) {
                JOptionPane.showMessageDialog(
                    this,
                    "HORMIGUERO respaldado",
                    "Guardado Exitoso",
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                throw new Exception("Error al guardar en la base de datos");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                this,
                "Ups...! tenemos problema en el hormiguero",
                "Error al Guardar",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    @Override
    public void educar(String tipoHormiga) {
        // Este método será llamado por el botón Entrenar
        if (tipoHormiga.equalsIgnoreCase("Larva")) {
            JOptionPane.showMessageDialog(this,
                "Las larvas no pueden ser entrenadas",
                "Entrenamiento no permitido",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        // La hormiga ha sido entrenada exitosamente
        JOptionPane.showMessageDialog(this,
            tipoHormiga + " entrenada y sumisa",
            "Entrenamiento exitoso",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void entrenarHormiga() {
        int filaSeleccionada = tblHormigas.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this,
                "Por favor, seleccione una hormiga para entrenar",
                "Selección requerida",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        String tipoHormiga = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
        
        if (tipoHormiga.equalsIgnoreCase("Larva")) {
            JOptionPane.showMessageDialog(this,
                "Las larvas no pueden ser entrenadas",
                "Entrenamiento no permitido",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Entrenar la hormiga
        educar(tipoHormiga);
        
        // Actualizar el estado de entrenamiento en la tabla
        modeloTabla.setValueAt("SI", filaSeleccionada, 5);
    }
} 
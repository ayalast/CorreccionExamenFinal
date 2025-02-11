package UIC.Form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import BLC.Catalogo;
import BLC.Entities.Hormiga;
import DAC.DTO.HormigaDTO;
import Infra.Style;
import UIC.CustomerControl.PatButton;
import UIC.CustomerControl.PatLabel;
import UIC.CustomerControl.PatTextBox;

public class HormigaPanel extends JPanel implements ActionListener {
    private Hormiga oHormiga = new Hormiga();
    private Integer id = 1, idMax=0;
    private Map<Integer, String> mapCatalogoTipo   ;
    private Map<Integer, String> mapCatalogoSexo   ;
    private Map<Integer, String> mapCatalogoEstado ;

    public HormigaPanel() {
        customizeComponent();
        
        try {
            mapCatalogoTipo   = new Catalogo().getAllHormigaTipo();
            mapCatalogoSexo   = new Catalogo().getAllHormigaSexo();
            mapCatalogoEstado = new Catalogo().getAllHormigaEstado();
            LoadComboBox(cmbCatalogoTipo  , mapCatalogoTipo  );
            LoadComboBox(cmbCatalogoSexo  , mapCatalogoSexo  );
            LoadComboBox(cmbCatalogoEstado, mapCatalogoEstado);

            loadDataRow(id);
        } catch (Exception e) {
            Style.showMsgError("Error al traer el catalogos de hormiga");
        }

        btnRowIni.addActionListener(this);
        btnRowAnt.addActionListener(this);
        btnRowSig.addActionListener(this);
        btnRowFin.addActionListener(this);
        
        btnNuevo.addActionListener      (  e -> btnNuevoClick());
        btnGuardar.addActionListener    (  e -> btnGuardarClick());
        btnEliminar.addActionListener   (  e -> btnEliminarClick());
        btnCancelar.addActionListener   (  e -> btnCancelarClick());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnRowIni)
            id = 1;
        if (e.getSource() == btnRowAnt && (id > 1))
            id--;
        if (e.getSource() == btnRowSig && (id < idMax))
            id++;
        if (e.getSource() == btnRowFin)
            id = idMax;
        try {
            oHormiga.getBy(id);  
            showDataRow(); 
        } catch (Exception ex) {}
    }

    private void btnNuevoClick() {
        oHormiga.set(null);
        showDataRow();
    } 
    
    private void btnGuardarClick() {
        boolean dtoNull = (oHormiga.get() == null);
        try {
            if (Style.showConfirmYesNo("¿Seguro que desea " + ((dtoNull) ? "AGREGAR ?" : "ACTUALIZAR ?"))){
                if(!((dtoNull) 
                    ? oHormiga.add(  getComboBox(cmbCatalogoTipo)
                                    ,getComboBox(cmbCatalogoSexo)
                                    ,getComboBox(cmbCatalogoEstado)
                                    ,txtNombre.getText()) 
                    : oHormiga.upd(  getComboBox(cmbCatalogoTipo)
                                    ,getComboBox(cmbCatalogoSexo)
                                    ,getComboBox(cmbCatalogoEstado)
                                    ,txtNombre.getText())))
                    Style.showMsgError("Error al guardar...!");
                loadDataRow(1);
            }
        } catch (Exception e) {
            Style.showMsgError(e.getMessage());
        }
    }

    private void btnEliminarClick() {
        try {
            if (Style.showConfirmYesNo("Seguro que desea Eliminar?")) {
                if (!oHormiga.del())
                    Style.showMsgError("Error durante la eliminar...!");
                loadDataRow(1);
            }
        } catch (Exception e) {
            Style.showMsgError(e.getMessage());
        }
    }

    private void btnCancelarClick() {
        try {
            if(oHormiga.get() == null)
                loadDataRow(1);
            else
                showDataRow();
        } catch (Exception e) {}
    }

    private void LoadComboBox(JComboBox<String> cmb, Map<Integer, String> map) {
            cmb.removeAllItems(); // Limpiar el combo antes de llenarlo
            for (String dato : map.values()) 
                cmb.addItem(dato);
    }
    private void setComboBox(JComboBox<String> cmb, Integer idCatalogo) {
        String nombre="";
        switch (cmb.getName()) {
            case "cmbCatalogoTipo" :    nombre = mapCatalogoTipo  .get(idCatalogo); break;  
            case "cmbCatalogoSexo" :    nombre = mapCatalogoSexo  .get(idCatalogo); break;  
            case "cmbCatalogoEstado" :  nombre = mapCatalogoEstado.get(idCatalogo); break;
        }
        if (nombre != "") 
            cmb.setSelectedItem(nombre);  
    }
    private Integer getComboBox(JComboBox<String> cmb) {
        String selectedName = (String) cmb.getSelectedItem();
        switch (cmb.getName()) {
            case "cmbCatalogoTipo" :    return mapCatalogoTipo.entrySet().stream()
                                        .filter(entry -> entry.getValue().equals(selectedName))
                                        .map(Map.Entry::getKey)
                                        .findFirst()
                                        .orElse(null); 
            case "cmbCatalogoSexo" :    return mapCatalogoSexo.entrySet().stream()
                                        .filter(entry -> entry.getValue().equals(selectedName))
                                        .map(Map.Entry::getKey)
                                        .findFirst()
                                        .orElse(null); 
            case "cmbCatalogoEstado" :  return mapCatalogoEstado.entrySet().stream()
                                        .filter(entry -> entry.getValue().equals(selectedName))
                                        .map(Map.Entry::getKey)
                                        .findFirst()
                                        .orElse(null);
        }
        return 0;
    }
    private void loadDataRow(int id) throws Exception {
        idMax   = oHormiga.getAll().size();
        this.id = ( id <= idMax && id > 0)?id:0;
        oHormiga.getBy(id);
        showDataRow();
        showDataTable();
    }

    private void showDataRow() {
        boolean dtoNull = (oHormiga.get() == null);
        txtNombre.setText   ((dtoNull) ? "" : oHormiga.get().getNombre());
        setComboBox(cmbCatalogoTipo  , (dtoNull) ? 1 : oHormiga.get().getIdCatalogoTipo());
        setComboBox(cmbCatalogoSexo  , (dtoNull) ? 1 : oHormiga.get().getIdCatalogoSexo());
        setComboBox(cmbCatalogoEstado, (dtoNull) ? 1 : oHormiga.get().getIdCatalogoEstado());

        lblTotalReg.setText(id.toString() + " de " + idMax.toString());
    }
    private void showDataTable() throws Exception {
        String[] header = {"IdHormiga", "Tipo","Sexo", "Estado", "Nombre"};
        Object[][] data = new Object[oHormiga.getAll().size()][5];
        int index = 0;
        for (HormigaDTO o : oHormiga.getAll()) {
            data[index][0] = o.getIdHormiga();
            data[index][1] = o.getIdCatalogoTipo  ();
            data[index][2] = o.getIdCatalogoSexo  ();
            data[index][3] = o.getIdCatalogoEstado();
            data[index][4] = o.getNombre ();
            index++;
        }

        JTable table = new JTable(data, header);
        table.setShowHorizontalLines(true);
        table.setGridColor(Color.lightGray);
        table.setRowSelectionAllowed(true);
        table.setColumnSelectionAllowed(false);

        table.setPreferredScrollableViewportSize(new Dimension(480, 150));
        table.setFillsViewportHeight(true);

        pnlTabla.removeAll();
        pnlTabla.add(new JScrollPane(table));

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row >= 0 && col >= 0) {
                    String strId = table.getModel().getValueAt(row, 0).toString();
                    id = Integer.parseInt(strId);
                    try {
                        oHormiga.getBy(id);
                        showDataRow();
                    } catch (Exception ignored) {
                    }
                    System.out.println("Tabla.Selected: " + strId);
                }
            }
        });
    }
    
/************************
 * FormDesing : pat_mic/HormigaDTO
 ************************/ 
// private Integer IdHormiga        ; *
// private Integer IdCatalogoTipo   ;
// private Integer IdCatalogoSexo   ;
// private Integer IdCatalogoEstado ;
// private String  Nombre           ;
// private String  Estado           ;
// private String  FechaCreacion    ;
// private String  FechaModifica    ;

    private JLabel lblTitulo= new JLabel(" PERSONA ");
    private PatLabel 
            lblIdHormiga        = new PatLabel("  IdHormiga:      "),
            lblIdCatalogoTipo   = new PatLabel("* lblIdCatalogoTipo : "),
            lblIdCatalogoSexo   = new PatLabel("* IdCatalogoSexo : "),
            lblIdCatalogoEstado = new PatLabel("* IdCatalogoEstado : "),
            lblNombre           = new PatLabel("* Nombre        : "),

            lblTotalReg = new PatLabel(" 0 de 0 ");
    private PatTextBox 
            IdHormiga           = new PatTextBox(),
            // txtIdCatalogoTipo   = new PatTextBox(),
            // txtIdCatalogoSexo   = new PatTextBox(),
            // txtIdCatalogoEstado = new PatTextBox(),
            txtNombre           = new PatTextBox();
    private JComboBox<String> cmbCatalogoTipo  = new JComboBox<>();
    private JComboBox<String> cmbCatalogoSexo  = new JComboBox<>();
    private JComboBox<String> cmbCatalogoEstado = new JComboBox<>();
    
    private PatButton 
            btnPageIni  = new PatButton(" |< "),
            btnPageAnt  = new PatButton(" << "),
            btnPageSig  = new PatButton(" >> "),
            btnPageFin  = new PatButton(" >| "),

            btnRowIni   = new PatButton(" |< "),
            btnRowAnt   = new PatButton(" << "),
            btnRowSig   = new PatButton(" >> "),
            btnRowFin   = new PatButton(" >| "),

            btnNuevo    = new PatButton("Nuevo"),
            btnGuardar  = new PatButton("Guardar"),
            btnCancelar = new PatButton("Cancelar"),
            btnEliminar = new PatButton("Eliminar");
    private JPanel 
            pnlTabla    = new JPanel(),
            pnlBtnRow   = new JPanel(new FlowLayout()),
            pnlBtnPage  = new JPanel(new FlowLayout()),
            pnlBtnCRUD  = new JPanel(new FlowLayout());

    public void customizeComponent() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        IdHormiga.setEnabled(false);
        //cmbPersonaTipo.setBounds(10, 35, 270, 20);
        cmbCatalogoTipo  .setName("cmbCatalogoTipo");
        cmbCatalogoSexo  .setName("cmbCatalogoSexo");
        cmbCatalogoEstado.setName("cmbCatalogoEstado");
        // cmbCatalogoTipo  .addActionListener(this);
        // cmbCatalogoSexo  .addActionListener(this);
        // cmbCatalogoEstado.addActionListener(this);

        pnlBtnPage.add(btnPageIni);
        pnlBtnPage.add(btnPageAnt);
        pnlBtnPage.add(new PatLabel(" Page:( "));
        pnlBtnPage.add(lblTotalReg); //cambiar
        pnlBtnPage.add(new PatLabel(" ) "));
        pnlBtnPage.add(btnPageSig);
        pnlBtnPage.add(btnPageFin);

        pnlBtnRow.add(btnRowIni);
        pnlBtnRow.add(btnRowAnt);
        pnlBtnRow.add(lblTotalReg);
        pnlBtnRow.add(btnRowSig);
        pnlBtnRow.add(btnRowFin);

        pnlBtnCRUD.add(btnNuevo);
        pnlBtnCRUD.add(btnGuardar);
        pnlBtnCRUD.add(btnCancelar);
        pnlBtnCRUD.add(btnEliminar);
        pnlBtnCRUD.setBorder(Style.createBorderRect());

        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        add(new JLabel("■ Sección de datos: "), gbc);
        gbc.gridy = 1;
        gbc.gridx = 1;
        add(pnlBtnPage, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.ipady = 150;
        gbc.ipadx = 450;
        pnlTabla.add(new Label("Loading data..."));
        add(pnlTabla, gbc);

        gbc.ipady = 1;
        gbc.ipadx = 1;

        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(50, 0, 0, 0);  // Ajusta el valor superior a 50
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(Box.createRigidArea(new Dimension(0, 0)), gbc);

        gbc.insets = new Insets(10, 0, 0, 0);  

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        add(new JLabel("■ Sección de registro: "), gbc);
        gbc.gridy = 4;
        gbc.gridx = 1;
        add(pnlBtnRow, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        add(lblIdHormiga, gbc);
        gbc.gridy = 5;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Indica que este componente ocupa toda la fila
        add(IdHormiga, gbc);

        gbc.gridy = 6;
        gbc.gridx = 0;
        add(lblIdCatalogoTipo, gbc);
        gbc.gridy = 6;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER; 
        add(cmbCatalogoTipo, gbc); //IdHormigaTipo cmbPersonaTipo
          
        gbc.gridy = 7;
        gbc.gridx = 0;
        add(lblIdCatalogoSexo, gbc);
        gbc.gridy = 7;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER; 
        add(cmbCatalogoSexo, gbc); //txtIdCatalogoEstado
             
        gbc.gridy = 8;
        gbc.gridx = 0;
        add(lblIdCatalogoEstado, gbc);
        gbc.gridy = 8;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER; 
        add(cmbCatalogoEstado, gbc);

        gbc.gridy = 9;
        gbc.gridx = 0;
        add(lblNombre, gbc);
        gbc.gridy = 9;
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = GridBagConstraints.REMAINDER; 
        add(txtNombre, gbc);

        gbc.gridy = 10;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        add(new PatLabel("(*) Obligatorio"), gbc);
      
        gbc.gridy = 11;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.insets = new Insets(30, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(pnlBtnCRUD, gbc);
    }
}

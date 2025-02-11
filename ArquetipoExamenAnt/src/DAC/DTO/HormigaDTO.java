package DAC.DTO;

public class HormigaDTO {
    private Integer IdHormiga        ;
    private Integer IdCatalogoTipo   ;
    private Integer IdCatalogoSexo   ;
    private Integer IdCatalogoEstado ;
    private String  Nombre           ;
    private String  Estado           ;
    private String  FechaCreacion    ;
    private String  FechaModifica    ;
    public HormigaDTO(){}
    public HormigaDTO(Integer idCatalogoTipo, Integer idCatalogoSexo, Integer idCatalogoEstado, String nombre) {
        IdCatalogoTipo = idCatalogoTipo;
        IdCatalogoSexo = idCatalogoSexo;
        IdCatalogoEstado = idCatalogoEstado;
        Nombre = nombre;
    }
    public HormigaDTO(Integer idHormiga, Integer idCatalogoTipo, Integer idCatalogoSexo, Integer idCatalogoEstado,
            String nombre, String estado, String fechaCreacion, String fechaModifica) {
        IdHormiga = idHormiga;
        IdCatalogoTipo = idCatalogoTipo;
        IdCatalogoSexo = idCatalogoSexo;
        IdCatalogoEstado = idCatalogoEstado;
        Nombre = nombre;
        Estado = estado;
        FechaCreacion = fechaCreacion;
        FechaModifica = fechaModifica;
    }

    public Integer getIdHormiga() {
        return IdHormiga;
    }
    public void setIdHormiga(Integer idHormiga) {
        IdHormiga = idHormiga;
    }
    public Integer getIdCatalogoTipo() {
        return IdCatalogoTipo;
    }
    public void setIdCatalogoTipo(Integer idCatalogoTipo) {
        IdCatalogoTipo = idCatalogoTipo;
    }
    public Integer getIdCatalogoSexo() {
        return IdCatalogoSexo;
    }
    public void setIdCatalogoSexo(Integer idCatalogoSexo) {
        IdCatalogoSexo = idCatalogoSexo;
    }
    public Integer getIdCatalogoEstado() {
        return IdCatalogoEstado;
    }
    public void setIdCatalogoEstado(Integer idCatalogoEstado) {
        IdCatalogoEstado = idCatalogoEstado;
    }
    public String getNombre() {
        return Nombre;
    }
    public void setNombre(String nombre) {
        Nombre = nombre;
    }
    public String getEstado() {
        return Estado;
    }
    public void setEstado(String estado) {
        Estado = estado;
    }
    public String getFechaCreacion() {
        return FechaCreacion;
    }
    public void setFechaCreacion(String fechaCreacion) {
        FechaCreacion = fechaCreacion;
    }
    public String getFechaModifica() {
        return FechaModifica;
    }
    public void setFechaModifica(String fechaModifica) {
        FechaModifica = fechaModifica;
    }

    @Override
    public String toString(){
        return getClass().getName()
        + "\n IdHormiga       : "+ getIdHormiga       ()
        + "\n IdCatalogoTipo  : "+ getIdCatalogoTipo  ()       
        + "\n IdCatalogoSexo  : "+ getIdCatalogoSexo  ()       
        + "\n IdCatalogoEstado: "+ getIdCatalogoEstado()
        + "\n Nombre          : "+ getNombre          ()
        + "\n Estado          : "+ getEstado          ()
        + "\n FechaCreacion   : "+ getFechaCreacion   ()
        + "\n FechaModifica   : "+ getFechaModifica   ();
    }

}

package BLC.Entities;

import java.util.List;
import BLC.BLFactory;
import DAC.DAO.HormigaDAO;
import DAC.DTO.HormigaDTO;

public  class Hormiga implements IHormiga{
    private HormigaDTO hormigaDTO;
    private BLFactory<HormigaDTO> bl = new BLFactory<>(HormigaDAO::new);
 
    public Hormiga() { }

    public boolean add(Integer idCatalogoTipo, Integer idCatalogoSexo, Integer idCatalogoEstado, String nombre) throws Exception {
        hormigaDTO = new HormigaDTO( idCatalogoTipo,  idCatalogoSexo,  idCatalogoEstado,  nombre);
        return bl.add(hormigaDTO);
    }
    public boolean upd(Integer idCatalogoTipo, Integer idCatalogoSexo, Integer idCatalogoEstado, String nombre) throws Exception {
        if( hormigaDTO != null){
            hormigaDTO.setIdCatalogoTipo(idCatalogoTipo);
            hormigaDTO.setIdCatalogoSexo(idCatalogoSexo);
            hormigaDTO.setIdCatalogoEstado(idCatalogoEstado);
            hormigaDTO.setNombre(nombre);
            return bl.upd(hormigaDTO);
        }
        return false;
    }
    public List<HormigaDTO> getAll() throws Exception {
         return bl.getAll();
    }
    public HormigaDTO getBy(Integer id) throws Exception {
        hormigaDTO = bl.getBy(id);
        return hormigaDTO;
    }
    public boolean del() throws Exception {
        if( hormigaDTO != null)
            return bl.del(hormigaDTO.getIdHormiga());
        return false;
    } 
    public HormigaDTO get() {
        return hormigaDTO;
    }
    public void set(HormigaDTO hormigaDTO) {
        this.hormigaDTO = hormigaDTO;
    }
}

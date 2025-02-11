package BLC;

import java.util.HashMap;
import java.util.Map;
import DAC.DAO.CatalogoDAO;
import DAC.DTO.CatalogoDTO;

public class Catalogo {

    private CatalogoDAO oCatalogoDAO = new CatalogoDAO();

    public Map<Integer, String> getAllHormigaTipo() throws Exception {
        return getMap(1);
    }
    public Map<Integer, String> getAllHormigaSexo () throws Exception {
        return getMap(2);
    }
    public Map<Integer, String> getAllHormigaEstado() throws Exception {
        return getMap(3);
    }
    
    private Map<Integer, String> getMap(Integer IdCatalogoPadre) throws Exception {
        Map<Integer, String> map  = new HashMap<>();
        for (CatalogoDTO pt : oCatalogoDAO.readByPadre(IdCatalogoPadre))  
            map.put(pt.getIdCatalogo(), pt.getNombre()); 
        return map;
    }
}

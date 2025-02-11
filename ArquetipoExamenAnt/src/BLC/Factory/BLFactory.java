package BLC.Factory;

import DAC.DAO.HormigaDAO;
import DAC.DAO.IDAO;
import DAC.DTO.HormigaDTO;

public class BLFactory {
    private static HormigaDAO hormigaDAO;

    public BLFactory() {
        if (hormigaDAO == null) {
            hormigaDAO = new HormigaDAO();
        }
    }

    public IDAO<HormigaDTO> getHormigaDAO() {
        return hormigaDAO;
    }
} 
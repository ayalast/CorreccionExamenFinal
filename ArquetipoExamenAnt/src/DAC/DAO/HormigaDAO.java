package DAC.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import DAC.DataHelperSQLite;
import DAC.IDAO;
import DAC.DTO.HormigaDTO;
import Infra.PatException;

public class HormigaDAO extends DataHelperSQLite implements IDAO<HormigaDTO>{
    private static final String SELECT_QUERY =   " SELECT    " 
                                                +"  IdHormiga        "
                                                +" ,IdCatalogoTipo   "
                                                +" ,IdCatalogoSexo   " 
                                                +" ,IdCatalogoEstado " 
                                                +" ,Nombre           " 
                                                +" ,Estado         " 
                                                +" ,FechaCreacion  " 
                                                +" ,FechaModifica  "
                                                +" FROM Hormiga  WHERE Estado = 'A' ";

    @Override
    public HormigaDTO newDTO(ResultSet rs) {
        try {
            return new HormigaDTO(
                rs.getInt   ("IdHormiga"),
                rs.getInt   ("IdCatalogoTipo"),
                rs.getInt   ("IdCatalogoSexo"),
                rs.getInt   ("IdCatalogoEstado"),
                rs.getString("Nombre"),
                rs.getString("Estado"),
                rs.getString("FechaCreacion"),
                rs.getString("FechaModifica")    );
        } catch (SQLException e) {
            new PatException(e, getClass().getName(), "newDTO()");
        }
        return null;
    }

    @Override
    public HormigaDTO readBy(Integer id) throws Exception {
        String query = SELECT_QUERY + " AND IdHormiga = ? "; 
        return executeReadBy(query, rs -> newDTO(rs), id);
    }

    @Override
    public List<HormigaDTO> readAll() throws Exception {
        String query = SELECT_QUERY;
        return executeReadAll(query, rs -> newDTO(rs));
    }

    @Override
    public boolean create(HormigaDTO dto) throws Exception {
        String query = "INSERT INTO Hormiga ("
        +"  IdCatalogoTipo   " 
        +" ,IdCatalogoSexo   " 
        +" ,IdCatalogoEstado " 
        +" ,Nombre          " 
        +" ,Estado          "
        +" ,FechaCreacion   "
        +") VALUES (?,?,?,?,?,?)";
        return execute(query,   dto.getIdCatalogoTipo  (),
                                dto.getIdCatalogoSexo  (),
                                dto.getIdCatalogoEstado(),
                                dto.getNombre          (),
                                "A",
                                getDateTimeNow());
    }

    @Override
    public boolean update(HormigaDTO dto) throws Exception {
        String query = "UPDATE Hormiga SET "
                    +"  IdCatalogoTipo   =?" 
                    +" ,IdCatalogoSexo   =?" 
                    +" ,IdCatalogoEstado =?" 
                    +" ,Nombre           =?" 
                    +" ,FechaModifica    =?"
                    +" WHERE IdHormiga    =?";
        return execute(query,   dto.getIdCatalogoTipo   (),
                                dto.getIdCatalogoSexo   (),
                                dto.getIdCatalogoEstado (),
                                dto.getNombre           (),
                                getDateTimeNow(),
                                dto.getIdHormiga()  );
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        String query = "UPDATE Hormiga SET Estado = ?, FechaCrea = ? WHERE IdHormiga = ?";
        return execute(query, "X", getDateTimeNow() , id);
    }

    @Override
    public Integer getMaxRow() throws Exception {
        throw new UnsupportedOperationException("Unimplemented method 'getMaxRow'");
    }
}

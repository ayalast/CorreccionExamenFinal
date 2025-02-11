package DAC.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import DAC.DataHelperSQLite;
import DAC.IDAO;
import DAC.DTO.CatalogoDTO;
import Infra.PatException;

public class CatalogoDAO extends DataHelperSQLite implements IDAO<CatalogoDTO> {
    private static final String SELECT_QUERY =   " SELECT    " 
                                                +"  IdCatalogo     " 
                                                +" ,IdCatalogoPadre" 
                                                +" ,Nombre         " 
                                                +" ,Detalle        " 
                                                +" ,Estado         " 
                                                +" ,FechaCreacion  " 
                                                +" ,FechaModifica  "
                                                +" FROM Catalogo WHERE Estado = 'A' ";
    @Override
    public CatalogoDTO newDTO(ResultSet rs) {
        try {
            return new CatalogoDTO(
                rs.getInt   ("IdCatalogo"),
                rs.getInt   ("IdCatalogoPadre"),
                rs.getString("Nombre"),
                rs.getString("Detalle"),
                rs.getString("Estado"),
                rs.getString("FechaCreacion"),
                rs.getString("FechaModifica")    );
        } catch (SQLException e) {
            new PatException(e, getClass().getName(), "newDTO()");
        }
        return null;
    }
    @Override
    public CatalogoDTO readBy(Integer id) throws Exception {
        String query = SELECT_QUERY + " AND IdCatalogo = ? "; 
        return executeReadBy(query, rs -> newDTO(rs), id);
    }
    @Override
    public List<CatalogoDTO> readAll() throws Exception {
        String query = SELECT_QUERY;
        return executeReadAll(query, rs -> newDTO(rs));
    }
    
    public List<CatalogoDTO>  readByPadre(Integer idPadre) throws Exception {
        String query = "SELECT    h.IdCatalogo      " + 
                        "        ,h.IdCatalogoPadre " + 
                        "        ,h.Nombre          " + 
                        "        ,h.Detalle         " + 
                        "        ,h.Estado          " + 
                        "        ,h.FechaCreacion   " + 
                        "        ,h.FechaModifica   " + 
                        "FROM    Catalogo p         " + 
                        "JOIN    Catalogo h on h.IdCatalogoPadre = p.IdCatalogo " + //
                        "WHERE   h.Estado = 'A'  AND p.IdCatalogo = ? "; 
        return executeReadByPadre(query, rs -> newDTO(rs), idPadre);
    }

    @Override
    public boolean create(CatalogoDTO dto) throws Exception {
        String query = "INSERT INTO Catalogo ("
                        +"  IdCatalogoPadre " 
                        +" ,Nombre          " 
                        +" ,Detalle         " 
                        +" ,Estado          "
                        +" ,FechaCreacion   "
                        +") VALUES (?,?,?,?,?)";
        return execute(query,   dto.getIdCatalogoPadre (),
                                dto.getNombre          (),
                                dto.getDetalle         (),
                                "A",
                                getDateTimeNow());
    }

    @Override
    public boolean update(CatalogoDTO dto) throws Exception {
        String query = "UPDATE Catalogo SET "
                        +"  IdCatalogoPadre= ?" 
                        +" ,Nombre         = ?" 
                        +" ,Detalle        = ?" 
                        +" ,FechaCreacion  = ?"
                        +" WHERE IdCatalogo= ?";
        return execute(query,   dto.getIdCatalogoPadre(),
                                dto.getNombre        (),
                                dto.getDetalle       (),
                                getDateTimeNow(),
                                dto.getIdCatalogo    ());
    }

    @Override
    public boolean delete(Integer id) throws Exception {
        String query = "UPDATE Catalogo SET Estado = ?, FechaCrea = ? WHERE IdCatalogo = ?";
        return execute(query, "X", getDateTimeNow() , id);
    }

    @Override
    public Integer getMaxRow() throws Exception {
        throw new UnsupportedOperationException("Unimplemented method 'getMaxRow'");
    }
}

package DAC;

import java.sql.ResultSet;
import java.util.List;

public interface IDAO<T> {
    T       newDTO(ResultSet rs);
    T       readBy(Integer id)  throws Exception;
    List<T> readAll()           throws Exception;
    boolean create(T dto)       throws Exception;
    boolean update(T dto)       throws Exception;
    boolean delete(Integer id)  throws Exception;
    Integer getMaxRow()         throws Exception;
}

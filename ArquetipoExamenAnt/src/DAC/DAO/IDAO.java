package DAC.DAO;

import java.util.List;

public interface IDAO<T> {
    void create(T entity) throws Exception;
    T read(int id) throws Exception;
    void update(T entity) throws Exception;
    void delete(int id) throws Exception;
    List<T> getAll() throws Exception;
    void saveAll() throws Exception;
} 
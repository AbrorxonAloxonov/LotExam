package org.example.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BasicDao<T> {
    boolean saveDB(T t) throws SQLException;
    List<T> getAll();
    Optional<T> findById(Integer id);
    boolean deleteById(Integer id);
    boolean update(T t);
}

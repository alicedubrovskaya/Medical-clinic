package dao;

import domain.Entity;
import exception.PersistentException;
import service.exception.ServicePersistentException;

public interface Dao<Type extends Entity> {
    Integer create(Type entity) throws PersistentException;

    Type read(Integer id) throws PersistentException;

    void update(Type entity) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}

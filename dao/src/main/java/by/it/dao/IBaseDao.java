package by.it.dao;

import java.io.Serializable;

public interface IBaseDao<PK extends Serializable, T> {

    T getByKey(PK key);

    void persist(T entity);

    void delete(T t);

    //TODO: что еще могло пригодиться
//    void saveOrUpdate(T t) throws Exception;
//
//    T get(Serializable id) throws Exception;
//
//    T load(Serializable id) throws Exception;
}
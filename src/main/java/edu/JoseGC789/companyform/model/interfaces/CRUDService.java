package edu.JoseGC789.companyform.model.interfaces;

public interface CRUDService<K,T> {
    T create(T t);
    T read(K id);
    T update(T t);
    T delete(K t);
}

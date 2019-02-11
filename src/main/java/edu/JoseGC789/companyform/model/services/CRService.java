package edu.JoseGC789.companyform.model.services;

public interface CRService<K,T> {
    T create(T t);
    T read(K id);
}

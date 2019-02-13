package edu.JoseGC789.companyform.model.services;

import java.util.List;

public interface CRService<T, K> extends CreationService<T>{
    T read(final K id);
    List<T> readAll();
}

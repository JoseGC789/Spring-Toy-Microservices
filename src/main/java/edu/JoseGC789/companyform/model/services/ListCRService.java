package edu.JoseGC789.companyform.model.services;

import java.util.Set;

public interface ListCRService<T,K> extends CRService<T,K>{
    Set<T> createAll(final Set<? extends T> t);
}

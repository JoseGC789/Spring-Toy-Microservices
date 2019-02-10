package edu.JoseGC789.companyform.utils;

import org.springframework.beans.BeanUtils;
import java.util.function.Supplier;

public final class Transformer{

    private Transformer(){
    }

    public static <T,K> K  toEntity(final T dto, final Supplier<? extends K> supplier){
        final K entity = supplier.get();
        BeanUtils.copyProperties(dto, entity, "id");
        return entity;
    }
}

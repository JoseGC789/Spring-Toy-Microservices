package edu.JoseGC789.companyform.model.helpers;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import java.util.function.Supplier;

@Component
public final class DtoConverter{

    public <T,K> K  toEntity(final T dto, final Supplier<? extends K> supplier){
        final K entity = supplier.get();
        BeanUtils.copyProperties(dto, entity, "id");
        return entity;
    }
}

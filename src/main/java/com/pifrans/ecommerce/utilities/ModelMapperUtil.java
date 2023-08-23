package com.pifrans.ecommerce.utilities;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ModelMapperUtil {
    private final ModelMapper modelMapper;


    public ModelMapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <E, D> D toDto(E objectEntity, Class<D> classDto) {
        return modelMapper.map(objectEntity, classDto);
    }
}

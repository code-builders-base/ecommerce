package com.pifrans.ecommerce.domains.dtos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDto {


    @Getter
    @Setter
    public static class Save {
        private String name;
        private Double price;
    }
}

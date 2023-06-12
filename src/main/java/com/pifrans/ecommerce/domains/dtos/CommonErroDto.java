package com.pifrans.ecommerce.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommonErroDto {
    private Integer statusCode;
    private String statusName;
    private String message;
    private String path;
}

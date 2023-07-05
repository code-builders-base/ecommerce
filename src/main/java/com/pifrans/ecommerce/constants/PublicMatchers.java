package com.pifrans.ecommerce.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PublicMatchers {

    GET(new String[]{/*"/users/**"*/ "/products/**"}),
    POST(new String[]{"/auth/forgot/**", "/users/**"}),
    ALL(new String[]{"/h2-console/**"});


    private final String[] endpoints;
}

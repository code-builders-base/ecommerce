package com.pifrans.ecommerce.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ReflectMethods {
    GET_ID("getId");


    private final String description;


    public static ReflectMethods toEnum(String description) {
        if (description == null || description.isEmpty()) {
            throw new NullPointerException("A descrição do não pode ser nula ou vazia!");
        }

        for (ReflectMethods x : ReflectMethods.values()) {
            if (description.equals(x.description)) {
                return x;
            }
        }
        throw new IllegalArgumentException("Descrição inválida: " + description);
    }
}
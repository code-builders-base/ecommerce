package com.pifrans.ecommerce.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SecurityProfiles {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private final String description;


    public static SecurityProfiles toEnum(String description) {
        if (description == null || description.isEmpty()) {
            throw new NullPointerException("A descrição não pode ser nula ou vazia!");
        }

        for (SecurityProfiles x : SecurityProfiles.values()) {
            if (description.equals(x.description)) {
                return x;
            }
        }
        throw new IllegalArgumentException("Descrição inválida: " + description);
    }
}

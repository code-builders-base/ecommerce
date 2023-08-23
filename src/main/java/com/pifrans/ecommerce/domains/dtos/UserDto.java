package com.pifrans.ecommerce.domains.dtos;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDto {


    @Getter
    @Setter
    public static class Credential {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    public static class Save extends Credential {
        private String firstName;
        private String lastName;
    }

    @Getter
    @Setter
    public static class Update extends Save {
        private Long id;
    }

    @Getter
    @Setter
    public static class Resume {
        private Long id;
        private String email;
        private String firstName;
        private String lastName;
    }
}

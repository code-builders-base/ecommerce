package com.pifrans.ecommerce.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum SecurityProfiles {
    ROLE_ADMIN, ROLE_USER;
}

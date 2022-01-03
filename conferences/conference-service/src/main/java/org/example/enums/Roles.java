package org.example.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Roles {
    ROLE_ADMIN("ADMIN"),
    ROLE_LISTENER("LISTENER"),
    ROLE_PRESENTER("PRESENTER");

    private final String name;
}

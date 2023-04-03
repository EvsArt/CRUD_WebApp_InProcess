package org.sfedueye.crudwebappsfedueye.api.login;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseForm {

    private boolean access;

    private String message;

    @Nullable
    private Long id;

    @Nullable
    private Integer hashCode;

}

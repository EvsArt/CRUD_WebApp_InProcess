package org.sfedueye.crudwebappsfedueye.api.sendUserData;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
class UserData {

    @Nullable
    private String name;
    @Nullable
    private String surname;
    @Nullable
    private String patronymic;
    @Nullable
    private String role;
    @Nullable
    private String division;
    @Nullable
    private String faculty;
    @Nullable
    private String specialization;
    @Nullable
    private short course;
    @Nullable
    private short groupNum;
    @Nullable
    private String info;
}
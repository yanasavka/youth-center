package com.example.youthcenter.dto;

import com.example.youthcenter.models.Gender;
import lombok.EqualsAndHashCode;
import lombok.Value;
import java.time.LocalDate;
public class UserDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private LocalDate dateOfBirth;
    private Integer age;
    private Gender gender;
    private Long roleId;
}

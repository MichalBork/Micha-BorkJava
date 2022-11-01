package com.example.creditcalculator.model;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String password;

    private List<Long> userPermissions;

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }
}

package com.userService.userService.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindUserByEmailRequestDTO {
    @NotBlank(message = "Email cannot be null")
    private String email;
}

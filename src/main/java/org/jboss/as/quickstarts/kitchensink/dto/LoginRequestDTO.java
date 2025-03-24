package org.jboss.as.quickstarts.kitchensink.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ToString(exclude = {"password"})
public class LoginRequestDTO {

    @NotNull
    private Long memberId;

    @NotBlank
    private String password;

}

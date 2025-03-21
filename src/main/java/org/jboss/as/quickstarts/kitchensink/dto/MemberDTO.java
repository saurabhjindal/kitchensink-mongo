package org.jboss.as.quickstarts.kitchensink.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Schema
@Validated
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MemberDTO {

    private Long memberId;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email (message = "Malformed Email")
    private String email;

    @NotBlank(message = "Phone Number must not be blank")
    @Size(min = 10, max = 12)
    @Digits(fraction = 0, integer = 12)
    private String phoneNumber;

    private String password;

}


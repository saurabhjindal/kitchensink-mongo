package org.jboss.as.quickstarts.kitchensink.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

@Schema
@Validated
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@ToString(exclude = "password")
public class MemberDTO {

    private Long memberId;

    @NotBlank(message = "Name must not be blank")
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email (message = "Invalid Email , email format: abc@mail.com")
    private String email;

    @NotBlank(message = "Phone Number must not be blank")
    @Pattern(regexp = "^[6-9]\\d{9}$", message = "Phone number must be 10 digits and start from 6 to 9 numbers.")
    private String phoneNumber;

    private String password;

}


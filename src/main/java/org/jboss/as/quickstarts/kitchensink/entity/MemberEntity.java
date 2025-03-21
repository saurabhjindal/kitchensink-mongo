package org.jboss.as.quickstarts.kitchensink.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jboss.as.quickstarts.kitchensink.constants.MemberRoleEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "members")// Defines the collection name in MongoDB
@Getter
@Setter
public class MemberEntity {

    @Id
    private String id;

    @NotNull
    private Long memberId;

    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[^0-9]*", message = "Must not contain numbers")
    private String name;

    @NotNull
    @NotEmpty
    @Email
    @Indexed(unique = true)
    private String email;

    @NotBlank
    @Size(min = 10, max = 12)
    @Digits(fraction = 0, integer = 12)
    @Field(name = "phone_number")
    @Indexed(unique = true)
    private String phoneNumber;

    @NotNull
    private String passwordHash;

    @NotNull
    private List<MemberRoleEnum> role;

}


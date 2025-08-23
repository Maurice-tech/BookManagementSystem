package com.booksystem.bookManagement.payload.response;

import com.booksystem.bookManagement.entity.enums.Roles;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtAuthResponse {
    Long id;
    String firstName;
    String lastName;
    String profilePicture;
    String email;
    String phoneNumber;
    Roles role;
    String accessToken;
    String refreshToken;
    String tokenType = "Bearer";
}

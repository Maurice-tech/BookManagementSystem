package com.booksystem.bookManagement.payload.response;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignUpResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

}
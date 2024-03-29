package com.solenersync.userstore.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    private String email;
    private String password;
    private String name;

}

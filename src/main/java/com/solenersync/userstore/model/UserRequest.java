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

//    public String getPassword() {
//        return passwordEncode(this.password);
//    }
//
//    public String getRawPassword() {
//        return this.password;
//    }
//
//    private String passwordEncode(String plainTextPassword) {
//        return DigestUtils.md5DigestAsHex(plainTextPassword.getBytes());
//    }
}

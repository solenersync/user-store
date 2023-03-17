package com.solenersync.userstore.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "generator")
    @SequenceGenerator(name = "generator", sequenceName = "users_user_id_seq", allocationSize = 1)
    @Column(name = "user_id")
    public Integer userId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "registered_date")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime registeredDate;

    @Override
    public String toString() {
        return "User{" +
            "userId=" + userId +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            ", registeredDate=" + registeredDate +
            '}';
    }
}

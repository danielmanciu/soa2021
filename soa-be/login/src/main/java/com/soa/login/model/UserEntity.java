package com.soa.login.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "USERS", schema = "users")
@Data
@SequenceGenerator(name = "UserSeq", sequenceName = "SEQ_USER", allocationSize = 1)
public class UserEntity {

    public static final String USER_ID = "USER_ID";
    public static final String USER_NAME = "USER_NAME";
    public static final String PASSWORD = "PASSWORD";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "UserSeq")
    @Column(name = USER_ID)
    private Long userId;

    @Column(name=USER_NAME)
    private String userName;

    @Column(name=PASSWORD)
    private String password;
}

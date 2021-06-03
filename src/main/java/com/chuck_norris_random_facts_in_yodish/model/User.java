package com.chuck_norris_random_facts_in_yodish.model;

import com.google.i18n.phonenumbers.Phonenumber;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@NoArgsConstructor
@Document
public class User {
    @Id
    private String userId;
    private String name;
    private String email;
    private InternationalPhoneNumber internationalPhoneNumber;
    private Phonenumber.PhoneNumber phone;

    public User(String name) {
        this.name = name;
        this.email = null;
        this.phone = null;
        this.internationalPhoneNumber = null;
    }
}

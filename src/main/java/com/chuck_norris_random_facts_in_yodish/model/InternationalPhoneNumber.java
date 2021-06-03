package com.chuck_norris_random_facts_in_yodish.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InternationalPhoneNumber {
    private int countryCode;
    private String carrierCode;
    private long nationalNumber;
}

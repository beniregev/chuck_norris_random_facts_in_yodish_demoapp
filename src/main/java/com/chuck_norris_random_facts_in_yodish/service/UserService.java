package com.chuck_norris_random_facts_in_yodish.service;

import com.chuck_norris_random_facts_in_yodish.model.User;
import com.chuck_norris_random_facts_in_yodish.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.i18n.phonenumbers.Phonenumber;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private BufferedReader in;
    private HttpURLConnection conn = null;
    private StringBuffer result;
    private StringBuffer response;

    @Autowired
    private UserRepository userRepository;

    public User createUser(String requestBody) throws IOException {
        User user = new ObjectMapper().readValue(requestBody, User.class);
        Phonenumber.PhoneNumber phone = new Phonenumber.PhoneNumber()
                .setCountryCode(user.getInternationalPhoneNumber().getCountryCode())
                .setPreferredDomesticCarrierCode(user.getInternationalPhoneNumber().getCarrierCode())
                .setNationalNumber(user.getInternationalPhoneNumber().getNationalNumber());
        user.setPhone(phone);
        return userRepository.insert(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String userId) throws ClassNotFoundException {
        Optional<User> optional = userRepository.findById(userId);
        if (!optional.isPresent()) {
            throw new ClassNotFoundException("User with id '" + userId + "' was not found");
        }
        User user = optional.get();
        return user;
    }

    public User updateUser(String userId, String requestBody) throws IOException, ClassNotFoundException {
        Optional<User> optional = userRepository.findById(userId);
        if (!optional.isPresent()) {
            throw new ClassNotFoundException("User with id '" + userId + "' was not found");
        }
        User user = new ObjectMapper().readValue(requestBody, User.class);
        userRepository.save(user);
        return user;
    }

    public User deleteUserById(final String userId) throws ClassNotFoundException {
        Optional<User> optional = userRepository.findById(userId);
        if (!optional.isPresent()) {
            throw new ClassNotFoundException("User with id '" + userId + "' was not found");
        }
        User user = optional.get();
        userRepository.deleteById(userId);
        return user;
    }

    private JSONObject getJsonObject(final String jsonString) {
        return new JSONObject(jsonString);
    }

}

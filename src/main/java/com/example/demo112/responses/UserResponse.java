package com.example.demo112.responses;

import com.example.demo112.models.Role;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class UserResponse {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("address")
    private String address;

    @JsonProperty("is_active")
    private boolean active;

    @JsonProperty("date_of_birth")
    private Date dateOfBirth;

    @JsonProperty("facebook_account_id")
    private int facebookAccountId;

    @JsonProperty("google_account_id")
    private int googleAccountId;

    @JsonProperty("role")
    private com.example.demo112.models.Role role;

    public static UserResponse fromUser(com.example.demo112.models.User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFullName(user.getFullName());
        userResponse.setPhoneNumber(user.getPhoneNumber());
        userResponse.setAddress(user.getAddress());
        userResponse.setActive(user.isActive());
        userResponse.setDateOfBirth(user.getDateOfBirth());
        userResponse.setFacebookAccountId(user.getFacebookAccountId());
        userResponse.setGoogleAccountId(user.getGoogleAccountId());
        userResponse.setRole(user.getRole());
        return userResponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getFacebookAccountId() {
        return facebookAccountId;
    }

    public void setFacebookAccountId(int facebookAccountId) {
        this.facebookAccountId = facebookAccountId;
    }

    public int getGoogleAccountId() {
        return googleAccountId;
    }

    public void setGoogleAccountId(int googleAccountId) {
        this.googleAccountId = googleAccountId;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

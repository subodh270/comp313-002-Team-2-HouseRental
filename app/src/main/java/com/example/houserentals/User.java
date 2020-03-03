package com.example.houserentals;

public class User
{
    private String userId;
    private String email;
    private String name;
    private Long phone;

    public User()
    {

    }

    public String getUserId() {return userId; }
    public String getEmail()
    {
        return email;
    }
    public String getName() {
        return name;
    }
    public Long getPhone() { return phone; }

    public User(String userId, String email, String name, Long phone) {

        this.userId = userId;
        this.email = email;
        this.name = name;
        this.phone = phone;
    }
}


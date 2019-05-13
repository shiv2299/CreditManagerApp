package com.example.shivam.creditmanage;

public class user {
    private String id;
    private String name;
    private String email;
    private int credit;

    public String getId() {
        return id;
    }

    public user(String id, String name, String email, int credit) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.credit = credit;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }
}

package com.example.report;


public class Contact {
    String name,email;
    int position;

    public Contact(String name,String email,int position)
    {
        this.setName(name);
        this.setEmail(email);
        this.setPosition(position);
    }

    public int getposition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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
}

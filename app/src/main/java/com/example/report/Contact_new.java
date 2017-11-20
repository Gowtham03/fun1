package com.example.report;


public class Contact_new {
    String name,email;
    String pre,abs,half;

    public Contact_new(String name, String email, String pre,String abs,String  half)
    {

        this.setName(name);
        this.setEmail(email);
        this.setPre(pre);
        this.setAbs(abs);
        this.setHalf(half);
    }

    public String getHalf() {
        return half;
    }

    public void setHalf(String half) {
        this.half = half;
    }

    public String getPre() {
        return pre;
    }

    public void setPre(String pre) {
        this.pre = pre;
    }

    public String getAbs() {
        return abs;
    }

    public void setAbs(String abs) {
        this.abs = abs;
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

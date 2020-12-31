package com.example.myapplication;

public class Contact {
    public String name;
    public String number;

    public Contact(){};

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }
}

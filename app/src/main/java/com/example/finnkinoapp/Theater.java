package com.example.finnkinoapp;


public class Theater
{
    private String name;
    private String ID;

    public Theater()
    {

    }

    public Theater(String id, String n)
    {
        ID = id;
        name = n;
    }

    public String toString()
    {
        return name;
    }

    public String getID()
    {
        return ID;
    }
}

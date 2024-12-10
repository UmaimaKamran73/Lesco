/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.a1_scd_22l7942;

import java.io.Serializable;

/**
 *
 * @author LENOVO
 */
public class Employee implements Serializable
{
    String username;   //check for comma "," and can only enter alphabets or numbers
    String password;  //same check

    public Employee(String username, String password) 
    {
        this.username = username;
        this.password = password;
    }

    public String getUsername() 
    {
        return username;
    }

    public void setUsername(String username) 
    {
        this.username = username;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password) 
    {
        this.password = password;
    }
}

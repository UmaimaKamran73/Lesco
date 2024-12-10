/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.a1_scd_22l7942;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * @author LENOVO
 */
public class EmployeeDataAccess 
{
    private static final String EMPLOYEE_FILE_PATH="EmployeeInfo.txt";
    
    private static final ReentrantReadWriteLock rwLock=new ReentrantReadWriteLock();
    
    public Employee authenticateEmployee(String username,String Password)
    {
        BufferedReader br=null;
        rwLock.readLock().lock();
        try
        {
            br=new BufferedReader(new FileReader(EMPLOYEE_FILE_PATH));
            
            String fileUsername;
            String filePassword;
            String line;
            while((line=br.readLine())!=null)
            {
                String[] fields= line.split(",");
                if(fields.length==2)
                {
                    fileUsername=fields[0];
                    filePassword=fields[1];
                    
                    if(fileUsername.equals(username))
                    {
                        if(filePassword.equals(Password))
                        {
                            return new Employee(fileUsername,filePassword);
                            
                        }
                        else
                        {
                            //password not matching
                            System.out.println("Invalid Password");
                            return null;
                        }
                    }
                }
            }
            System.out.println("Username not found");
        }
       
        catch(IOException e)
        {
            System.out.println("Error while reading the employee file: "+e.getMessage());
        }
        finally
        {
            try
            {
                if(br!=null)
                {
                    br.close();
                }
            }
            catch(IOException e)
            {
                System.out.println("Error while closing the file reader "+e.getMessage());
            }
            finally
            {
                rwLock.readLock().unlock();
            }
        }
        return null;
    }
    
    public boolean updatePassword(String username,String newPassword)
    {
        List<String> lines=new CopyOnWriteArrayList<>();
        boolean updated=false;
        BufferedReader br=null;
        BufferedWriter bw=null;
        rwLock.writeLock().lock();
        try
        {
            br=new BufferedReader(new FileReader(EMPLOYEE_FILE_PATH));
            String line;
            while((line=br.readLine())!=null)
            {
                String[]fields=line.split(",");
                
                if(fields.length==2)
                {
                    String fileUsername=fields[0];
                    if(fileUsername.equals(username))
                    {
                        //update password in file
                        fields[1]=newPassword;
                        updated=true;
                    }
                }
                lines.add(String.join(",", fields));
            }
            
            if(updated)
            {
                bw=new BufferedWriter(new FileWriter(EMPLOYEE_FILE_PATH));
                for(String updatedLine:lines)
                {
                    bw.write(updatedLine);
                    bw.newLine();
                }
            }
        }
        catch(IOException e)
        {
            System.out.println("Error while updating the password: "+ e.getMessage());
            return false;
        }
        finally
        {
            try
            {
                if(br!=null)
                {
                    br.close();
                }
                if(bw!=null)
                {
                    bw.close();
                }
            }
            catch(IOException e)
            {
                System.out.println("Error while closing file readers: "+e.getMessage());
            }
            finally
            {
                rwLock.writeLock().unlock();
            }
       }
    return updated;
}
    
}

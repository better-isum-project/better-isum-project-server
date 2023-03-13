package com.better.isum.project.server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.Serializable;

import java.util.logging.Level;

import java.lang.reflect.Field;
import java.lang.IllegalAccessException;

public class User implements Serializable {
    private final static MetLogger LOGGER = MetLogger.getInstance();

    private String userName;
    private String userPassword;

    private Role userRole = Role.STUDENT;

    public User() {
        LOGGER.LogMessage(userRole.toString(), Level.INFO);
    }

    public User(String param) {
        LOGGER.LogMessage(userRole.toString(), Level.INFO);
        this.loadUser(param);
    }

    public String getPassword() {
        return this.userPassword;
    }

    public String getUsername() {
        return this.userName;
    }

    public void createUser() {

    }

    public void saveUser() {
        try {
            FileOutputStream fileOut = new FileOutputStream(this.userName);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();

            LOGGER.LogMessage(String.format("User saved: %s", this.userName), Level.CONFIG);
        } catch (IOException e) {
            LOGGER.LogMessage(String.format("User not saved [IOException]: %s", e), Level.WARNING);
        }
    }

    public User loadUser(String param) {
        try {
            FileInputStream fileIn = new FileInputStream(String.format("% sobject.ser", param));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            User studentObj = (User) in.readObject();
            in.close();
            fileIn.close();

            Field[] fields = User.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(this, field.get(studentObj));
            }

            LOGGER.LogMessage(String.format("User loaded: %s", this.userName), Level.CONFIG);
            return studentObj;
        } catch (IllegalAccessException e) {
            LOGGER.LogMessage(String.format("User not loaded [IllegalAccessException]: %s", e), Level.WARNING);
        } catch (IOException e) {
            LOGGER.LogMessage(String.format("User not loaded [IOException]: %s", e), Level.WARNING);
        } catch (ClassNotFoundException e) {
            LOGGER.LogMessage(String.format("User not loaded [ClassNotFoundException]: %s", e), Level.WARNING);
        }
        return null;
    }
}

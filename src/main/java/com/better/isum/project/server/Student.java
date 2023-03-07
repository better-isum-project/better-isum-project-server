package com.better.isum.project.server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.IllegalAccessException;
import java.util.logging.Level;

public class Student implements Serializable {
    private final static MetLogger LOGGER = MetLogger.getInstance();

    private String username;
    private String password;

    public String pozicija = "student";

    public Student() {
    }

    public Student(String param) {
        this.loadStudent(param);
    }

    public Student(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return this.pozicija;
    }

    private void saveStudent() {
        try {
            FileOutputStream fileOut = new FileOutputStream(this.username);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();

            LOGGER.LogMessage(String.format("User saved: %s", this.username), Level.CONFIG);
        } catch (IOException e) {
            LOGGER.LogMessage(String.format("User not saved [IOException]: %s", e), Level.WARNING);
        }
    }

    private Student loadStudent(String param) {
        try {
            FileInputStream fileIn = new FileInputStream(String.format("% sobject.ser", param));
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Student studentObj = (Student) in.readObject();
            in.close();
            fileIn.close();

            Field[] fields = Student.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                field.set(this, field.get(studentObj));
            }

            LOGGER.LogMessage(String.format("User loaded: %s", this.username), Level.CONFIG);
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

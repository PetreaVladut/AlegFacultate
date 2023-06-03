package com.saurabh.pussgrc;

import android.net.Uri;

import java.io.Serializable;
import java.util.Arrays;

public class University  implements Serializable {
    private String name;
    private String address;
    private String[] departments;
    private String description;
    private String deanName;
    private String email;
    private int rank;
    private Uri image;

    public University(String name, String address, String[] departments, String description, String deanName, String email, int rank, Uri image) {
        this.name = name;
        this.address = address;
        this.departments = departments;
        this.description = description;
        this.deanName = deanName;
        this.email = email;
        this.rank = rank;
        this.image=image;
    }

    public University()
    {

    }
    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }
    public Uri getImage()
    {
        return image;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String[] getDepartments() {
        return departments;
    }

    public void setDepartments(String[] departments) {
        this.departments = departments;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeanName() {
        return deanName;
    }

    public void setDeanName(String deanName) {
        this.deanName = deanName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
    @Override
    public String toString() {
        return "University{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", departments=" + Arrays.toString(departments) +
                ", description='" + description + '\'' +
                ", deanName='" + deanName + '\'' +
                ", email='" + email + '\'' +
                ", rank=" + rank +
                '}';
    }
}

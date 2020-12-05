package com.example.hopithopit.ui.assessment;

public class AssementSample {
    private String name;
    private String disease;
    private String level;
    private String location;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "AssementSample{" +
                "name='" + name + '\'' +
                ", disease='" + disease + '\'' +
                ", level='" + level + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}

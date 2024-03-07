package model;

public class CharacterModel {
    private int id;
    private String firstName;
    private String lastName;
    private String lightSaberColor;
    private int force;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLightSaberColor() {
        return lightSaberColor;
    }

    public void setLightSaberColor(String lightSaberColor) {
        this.lightSaberColor = lightSaberColor;
    }

    public int getForce() {
        return force;
    }

    public void setForce(int force) {
        this.force = force;
    }
}

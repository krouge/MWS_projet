package ch.heigvd.comem.gameengine.model;

/**
 *
 * @author Julien Biedermann
 */
public class Player {
    
    private String firstName;
    
    private String lasName;
    
    private String email;
    
    private int points;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLasName() {
        return lasName;
    }

    public void setLasName(String lasName) {
        this.lasName = lasName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

}

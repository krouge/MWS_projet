package ch.heigvd.comem.gameengine.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Julien Biedermann
 */
@Entity
public class Player implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playerId;
    
    private String firstName;
    
    private String lastName;
    
    private String email;
    
    @OneToMany(mappedBy="player")
    private List <Event> events = new LinkedList <Event>();
    
    @ManyToMany(mappedBy="players")
    private List <Badge> badges = new LinkedList <Badge>();
    
    @NotNull
    private int points;

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
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

    public List<Badge> getBadges() {
        return badges;
    }

    public void setBadges(List<Badge> badges) {
        this.badges = badges;
    }
    
    
    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (playerId != null ? playerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the playerId fields are not set
        if (!(object instanceof Player)) {
            return false;
        }
        Player other = (Player) object;
        if ((this.playerId == null && other.playerId != null) || (this.playerId != null && !this.playerId.equals(other.playerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.heigvd.comem.gameengine.model.Player[ id=" + playerId + " ]";
    }

}

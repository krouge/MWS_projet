package ch.heigvd.comem.gameengine.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Julien Biedermann
 */
@Entity
public class EventHistory implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventHistoryId;

    public Long getId() {
        return eventHistoryId;
    }

    public void setId(Long id) {
        this.eventHistoryId = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (eventHistoryId != null ? eventHistoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the eventHistoryId fields are not set
        if (!(object instanceof EventHistory)) {
            return false;
        }
        EventHistory other = (EventHistory) object;
        if ((this.eventHistoryId == null && other.eventHistoryId != null) || (this.eventHistoryId != null && !this.eventHistoryId.equals(other.eventHistoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.heigvd.comem.gameengine.model.EventHistory[ id=" + eventHistoryId + " ]";
    }

}

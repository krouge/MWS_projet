/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Renouille
 */
@Entity
public class Rule implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @ManyToOne
    private Long badge;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ruleId;
    
    @NotNull
    private String eventType;
    
    @NotNull 
    private int numberOfPoints;

    @ManyToOne
    private Long application;

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long id) {
        this.ruleId = id;
    }

    
    public Long getApplication() {
        return application;
    }
    
    public void setApplication(Long application) {
        this.application = application;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getNumberOfPoints() {
        return numberOfPoints;
    }

    public void setNumberOfPoints(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    public Long getBadge() {
        return badge;
    }

    public void setBadge(Long badgeId) {
        this.badge = badgeId;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ruleId != null ? ruleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the ruleId fields are not set
        if (!(object instanceof Rule)) {
            return false;
        }
        Rule other = (Rule) object;
        if ((this.ruleId == null && other.ruleId != null) || (this.ruleId != null && !this.ruleId.equals(other.ruleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.heigvd.comem.gameengine.model.Rule[ id=" + ruleId + " ]";
    }
    
}

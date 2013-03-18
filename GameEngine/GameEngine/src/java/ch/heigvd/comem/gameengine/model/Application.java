package ch.heigvd.comem.gameengine.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Julien Biedermann
 */
@Entity
@XmlRootElement
public class Application implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;
    
    @NotNull
    private String name;
    
    private String description;
    
    @NotNull
    private String apiKey;
    
    @NotNull
    private String apiSecret;
    
    @OneToMany(mappedBy="application", cascade=CascadeType.REMOVE)
    private List <Rule> rules = new LinkedList <Rule>();
    
    @OneToMany(mappedBy="application", cascade=CascadeType.REMOVE)
    private List <Event> events = new LinkedList <Event>();

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }
    
    @XmlTransient
    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }
    
    public void addRule(Rule rule) {
        this.rules.add(rule);
    }

    @XmlTransient
    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
    
    public void addEvent(Event event) {
        this.events.add(event);
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (applicationId != null ? applicationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the applicationId fields are not set
        if (!(object instanceof Application)) {
            return false;
        }
        Application other = (Application) object;
        if ((this.applicationId == null && other.applicationId != null) || (this.applicationId != null && !this.applicationId.equals(other.applicationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.heigvd.comem.gameengine.model.Application[ id=" + applicationId + " ]";
    }

}

package ch.heigvd.comem.gameengine.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Julien Biedermann
 */
@Entity
public class Application implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ApplicationId;
    
    @NotNull
    private String name;
    
    private String description;
    
    @NotNull
    private String apiKey;
    
    @NotNull
    private String apiSecret;

    public Long getApplicationId() {
        return ApplicationId;
    }

    public void setApplicationId(Long ApplicationId) {
        this.ApplicationId = ApplicationId;
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
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ApplicationId != null ? ApplicationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the ApplicationId fields are not set
        if (!(object instanceof Application)) {
            return false;
        }
        Application other = (Application) object;
        if ((this.ApplicationId == null && other.ApplicationId != null) || (this.ApplicationId != null && !this.ApplicationId.equals(other.ApplicationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.heigvd.comem.gameengine.model.Application[ id=" + ApplicationId + " ]";
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fabiencornaz
 */
@Entity
@XmlRootElement
public class Photo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String source;   
    private int points;
    private String titre;
    
    @ManyToOne
    protected Utilisateur utilisateur;
    
    @ManyToOne
    private Theme theme;

    @ManyToMany
    private List<Tag> tags = new LinkedList<Tag>();
    
    @ManyToMany
    private List<Utilisateur> utilisateurs = new LinkedList<Utilisateur>();

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
    
    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    
        
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
    
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Photo)) {
            return false;
        }
        Photo other = (Photo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.comem.heigvd.model.Photo[ id=" + id + " ]";
    }
    
    public void addUtilisateurLike(Utilisateur utilisateur){
        this.utilisateurs.add(utilisateur);
    }

    public void addTag(Tag tag){
        this.tags.add(tag);
    }  
}

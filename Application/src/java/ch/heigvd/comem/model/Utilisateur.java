/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Jonas
 */
@Entity
@XmlRootElement
public class Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name="ID", table="UTILISATEUR")
    private Long id;
    
    private Long idPlayer;
    
    private String pseudo;
    
    private String nom;
    
    private String prenom;
    
    private String email;
    
    private String mdp;
    
    @ManyToMany(mappedBy="utilisateurs", cascade=CascadeType.REMOVE)
    private List<Photo> photos_like = new LinkedList<Photo>();
    
    @OneToMany(mappedBy="utilisateur", cascade=CascadeType.REMOVE)
    private List<Photo> photos = new LinkedList<Photo>();
    
    @OneToMany(mappedBy="utilisateur", cascade=CascadeType.REMOVE)
    private List<Theme> themes = new LinkedList<Theme>();

    @XmlTransient
    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

    @XmlTransient
    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
    
    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @XmlTransient
    public List<Photo> getPhotos_like() {
        return photos_like;
    }

    public void setPhotos_like(List<Photo> photos_like) {
        this.photos_like = photos_like;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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
        if (!(object instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ch.heigvd.comem.model.Utilisateur[ id=" + id + " ]";
    }
    
    public void addPhoto(Photo photo){
        this.photos.add(photo);
    }
    
    public void addTheme(Theme theme){
        this.themes.add(theme);
    }
    
    public void addPhotoLike(Photo photo){
        this.photos_like.add(photo);
    }

    public Long getIdPlayer() {
        return idPlayer;
    }

    public void setIdPlayer(Long idPlayer) {
        this.idPlayer = idPlayer;
    }
    
    
    
    
}

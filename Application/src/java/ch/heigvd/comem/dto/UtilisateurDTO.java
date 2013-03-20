/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jonas
 */
@XmlRootElement
public class UtilisateurDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    private String pseudo;
    
    private String email;
    
    private String mdp;
    
    private List<Photo> photos_like = new LinkedList<Photo>();
    
    private List<Photo> photos = new LinkedList<Photo>();
    
    private List<Theme> themes = new LinkedList<Theme>();

    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

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
    
    public void addPhoto(Photo photo){
        this.photos.add(photo);
    }
    
    public void addTheme(Theme theme){
        this.themes.add(theme);
    }
    
    public void addPhotoLike(Photo photo){
        this.photos_like.add(photo);
    }
    
    
}

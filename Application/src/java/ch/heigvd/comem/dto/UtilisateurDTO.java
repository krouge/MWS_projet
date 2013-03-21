/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.dto;

import ch.heigvd.comem.model.Photo;
import ch.heigvd.comem.model.Theme;
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
    
    private List<PhotoDTO> photos = new LinkedList<PhotoDTO>();
    
    private List<ThemeDTO> themes = new LinkedList<ThemeDTO>();

    public List<ThemeDTO> getThemes() {
        return themes;
    }

    public void setThemes(List<ThemeDTO> themes) {
        this.themes = themes;
    }

    public List<PhotoDTO> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotoDTO> photos) {
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

    public List<Photo> getPhotos_like() {
        return photos_like;
    }

    public void setPhotos_like(List<Photo> photos_like) {
        this.photos_like = photos_like;
    }
    
    /*
    public void addPhoto(Photo photo){
        this.photos.add(photo);
    }
    
    public void addTheme(Theme theme){
        this.themes.add(theme);
    }
    
    public void addPhotoLike(Photo photo){
        this.photos_like.add(photo);
    }
    */
    
}

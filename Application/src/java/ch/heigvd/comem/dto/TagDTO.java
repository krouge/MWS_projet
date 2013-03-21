/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.dto;

import ch.heigvd.comem.model.Photo;
import ch.heigvd.comem.model.Tag;
import ch.heigvd.comem.model.Theme;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fabiencornaz
 */
@XmlRootElement
public class TagDTO {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    
    private String titre;
  
    private List<Theme> themes = new LinkedList<Theme>();
    
    private List<Photo> photos = new LinkedList<Photo>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

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
}

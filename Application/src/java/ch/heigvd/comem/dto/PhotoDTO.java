/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.dto;

import ch.heigvd.comem.model.Tag;
import ch.heigvd.comem.model.Theme;
import ch.heigvd.comem.model.Utilisateur;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author fabiencornaz
 */
@XmlRootElement
public class PhotoDTO {
    private static final long serialVersionUID = 1L;

    private Long id;
    
    private String source; 
    
    private int points;
    
    protected Utilisateur utilisateur;
    
    private Theme theme;

    private List<Tag> tags = new LinkedList<Tag>();
    
    private List<Utilisateur> utilisateurs = new LinkedList<Utilisateur>();

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

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
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
}

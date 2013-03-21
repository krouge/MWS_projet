/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services.REST;

import ch.heigvd.comem.exceptions.ExceptionIdUtilisateur;
import ch.heigvd.comem.model.Utilisateur;
import ch.heigvd.comem.dto.UtilisateurDTO;
import ch.heigvd.comem.services.UtilisateursManagerLocal;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/**
 *
 * @author Jonas
 */
@Stateless
@Path("utilisateur")
public class UtilisateurFacadeREST extends AbstractFacade<Utilisateur> {
    @PersistenceContext(unitName = "ApplicationPU")
    private EntityManager em;
    
    @EJB
    private UtilisateursManagerLocal u; 

    public UtilisateurFacadeREST() {
        super(Utilisateur.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Utilisateur entity) {
        super.create(entity);
    }

    @PUT
    @Override
    @Consumes({"application/xml", "application/json"})
    public void edit(Utilisateur entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public UtilisateurDTO find(@PathParam("id") Long id, @QueryParam("themes") Long withThemes, @QueryParam("photos") Long withPhotos) throws ExceptionIdUtilisateur {

        Utilisateur utilisateur = u.find(id);
        
        
        UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
        
        utilisateurDTO.setId(utilisateur.getId());
        utilisateurDTO.setEmail(utilisateur.getEmail());
        utilisateurDTO.setMdp(utilisateur.getMdp());
        utilisateurDTO.setPseudo(utilisateur.getPseudo());
        
       
        if(withThemes != null && withThemes == 1){
            utilisateurDTO.setThemes(utilisateur.getThemes());
        }
        
        if(withPhotos != null && withPhotos == 1){
            utilisateurDTO.setPhotos(utilisateur.getPhotos());
        }
        
        return utilisateurDTO;
    }
    
    /*@GET
    @Path("{id}/themes")
    @Produces({"application/xml", "application/json"})
    public List<Theme> findTheme(@PathParam("id") Long id) {

        return super.find(id).getThemes();
    }*/

    @GET
    @Produces({"application/xml", "application/json"})
    public List<UtilisateurDTO> findAll(@QueryParam("themes") Long withThemes, @QueryParam("photos") Long withPhotos) {
        
        List<Utilisateur> utilisateurs = super.findAll();
        List<UtilisateurDTO> utilisateursDTO = new LinkedList<UtilisateurDTO>();
        
        for(Utilisateur utilisateur : utilisateurs){
            UtilisateurDTO utilisateurDTO = new UtilisateurDTO();
            utilisateurDTO.setId(utilisateur.getId());
            utilisateurDTO.setEmail(utilisateur.getEmail());
            utilisateurDTO.setMdp(utilisateur.getMdp());
            utilisateurDTO.setPseudo(utilisateur.getPseudo());
            
            if(withThemes != null && withThemes == 1){
                utilisateurDTO.setThemes(utilisateur.getThemes());
            }
        
            if(withPhotos != null && withPhotos == 1){
                utilisateurDTO.setPhotos(utilisateur.getPhotos());
            }
            
            utilisateursDTO.add(utilisateurDTO);
        }
       
        
        
        return utilisateursDTO;
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Utilisateur> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
}

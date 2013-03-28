package ch.heigvd.comem.gameengine.rest;

import ch.heigvd.comem.gameengine.model.Player;
import ch.heigvd.comem.gameengine.services.PlayersManagerLocal;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Service REST permettant de gérer les actions @POST, @GET, @DELETE et @PUT sur un player
 * @author Renouille
 */
@Stateless
@Path("players")
public class PlayerFacadeREST {
    
    @EJB
    private PlayersManagerLocal playersManagerLocal;

    /**
     * Permet de créer une nouvelle entité player
     * @param entity l'entité player à créer
     * @return l'entité player créée
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    @Produces("application/json")
    public Player create(Player entity) {
        
        Long playerId = playersManagerLocal.create(entity.getPoints());
        return playersManagerLocal.find(playerId);
    }

    /**
     * Permet de modifier une entité player existante
     * @param entity l'entité player à modifier
     */
    @PUT
    @Consumes({"application/xml", "application/json"})
    public void edit(Player entity) {
        playersManagerLocal.update(entity.getPlayerId(), entity.getPoints());
    }

    /**
     * Permet de supprimer une entité player
     * @param id l'id de l'entité player à supprimer
     */
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Long id) {
        playersManagerLocal.remove(id);
    }

    /**
     * Permet de récupérer une entité player
     * @param id l'id de l'entité player à récupérer
     * @return l'entité player voulue
     */
    @GET
    @Path("{id}")
    @Produces("application/json")
    public Player find(@PathParam("id") Long id) {
        return playersManagerLocal.find(id);
    }

    /**
     * Permet de récupérer toutes les entités player
     * @return une liste des entités player
     */
    @GET
    @Produces({"application/xml", "application/json"})
    public List<Player> findAll() {
        return playersManagerLocal.findAll();
    }
    
    /**
     * Permet de récupérer le leaderboard (classement) des players
     * @return le leaderboard
     */
    @GET
    @Path("leaderboard")
    @Produces("application/json")
    public List<Player> getLeaderboard() {
        
        return playersManagerLocal.getLeaderboard();
    }
}

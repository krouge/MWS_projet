/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services.REST;

import ch.heigvd.comem.model.Photo;
import ch.heigvd.comem.test.TestDataManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * Service REST permettant de gérer les actions @POST, @GET, @DELETE et @PUT pour la gestion des données de test
 * @author fabiencornaz
 */
@Stateless
@Path("populate")
public class PopulateREST {
    
    @EJB
    TestDataManagerLocal testDataManager;
    
    public PopulateREST() {
    }
    
    /**
     * Permet de générer des données de test
     * @return 
     */
    @POST
    @Consumes({"application/xml", "application/json"})
    public String create() {
        
        testDataManager.generateTestData();
        
        return "BRAVO !!!! Tu as populé la Base";
    }
    
    
}

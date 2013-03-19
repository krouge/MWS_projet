/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services.REST;

import ch.heigvd.comem.model.Photo;
import ch.heigvd.comem.test.TestDataManager;
import ch.heigvd.comem.test.TestDataManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author fabiencornaz
 */

@Stateless
@Path("populate")
public class PopulateREST {
    
    @EJB
    private TestDataManagerLocal testDataManager;
    
    public PopulateREST() {
    }
    
    @POST
    public void create() {
        testDataManager.generateTestData();
    }
    
    
    
}

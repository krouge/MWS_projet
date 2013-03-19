package ch.heigvd.comem.gameengine.rest;

import ch.heigvd.comem.gameengine.tests.TestDataManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 * @author Renouille
 */
@Stateless
@Path("test")
public class TestREST {

    @EJB
    TestDataManagerLocal testDataManagerLocal;

    @POST
    @Consumes({"application/xml", "application/json"})
    public void create() {
        testDataManagerLocal.generateData();
    }
}

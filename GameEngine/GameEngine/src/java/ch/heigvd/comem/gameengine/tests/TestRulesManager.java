/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.tests;

import ch.heigvd.comem.gameengine.services.ApplicationsManagerLocal;
import ch.heigvd.comem.gameengine.services.RulesManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 *
 * @author Renouille
 */
@Stateless
@WebService
public class TestRulesManager implements TestRulesManagerLocal {

    @EJB
    private RulesManagerLocal rulesManagerLocal;
    private ApplicationsManagerLocal applicationsManagerLocal;
    
    @Override
    public void generateRules() {
        
        Long app = applicationsManagerLocal.create("App", "App descr", "100", "100");
        
        for (int i=0; i<20; i++) {
            
            rulesManagerLocal.create("EventType Test "+1, 
                                      20, 
                                      applicationsManagerLocal.find(app));
        }
    }
}

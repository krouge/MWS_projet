package ch.heigvd.comem.gameengine.tests;

import ch.heigvd.comem.gameengine.services.ApplicationsManagerLocal;
import ch.heigvd.comem.gameengine.services.RulesManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Renouille
 */
@Stateless
public class TestRulesManager implements TestRulesManagerLocal {

    @EJB
    private RulesManagerLocal rulesManagerLocal;
    @EJB
    private ApplicationsManagerLocal applicationsManagerLocal;
    
    @Override
    public void generateRules() {
        
        Long app = applicationsManagerLocal.create("App", "Appdescr", "100", "300");

        for (int i=0; i<20; i++) {
            
            rulesManagerLocal.create("EventType Test "+1, 
                                      20, 
                                      "1234", "1234");
        }
    }
}

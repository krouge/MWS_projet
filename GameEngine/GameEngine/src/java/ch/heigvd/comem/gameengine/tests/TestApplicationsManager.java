package ch.heigvd.comem.gameengine.tests;

import ch.heigvd.comem.gameengine.services.ApplicationsManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 *
 * @author julien
 */
@Stateless
@WebService
public class TestApplicationsManager implements TestApplicationsManagerLocal {
    
    @EJB
    private ApplicationsManagerLocal applicationsManagerLocal;

    @Override
    public void generateApplications() {
        
        applicationsManagerLocal.create("Kit-Kat",
                                        "Description", 
                                        "sd898sa98da8s9d8aa",
                                        "9d9Da23jhFkksls103D");
        
        applicationsManagerLocal.create("Cool pics",
                                        "Description", 
                                        "sadadasdsadsadasd8s9d8aa",
                                        "9dsdsdsadsagggdfdsc");
        
    }
    
    

    

}

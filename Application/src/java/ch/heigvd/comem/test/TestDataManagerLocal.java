/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.test;

import javax.ejb.Local;

/**
 *
 * @author Jonas
 */
@Local
public interface TestDataManagerLocal {
    
    public void generateTestData();
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.gameengine.tests;

import javax.ejb.Local;

/**
 *
 * @author Renouille
 */
@Local
public interface TestRulesManagerLocal {
    
    void generateRules();
}

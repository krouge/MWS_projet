/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import com.sun.jersey.multipart.MultiPart;
import java.io.IOException;
import javax.ejb.Local;
import javax.ws.rs.core.Response;

/**
 *
 * @author Renouille
 */
@Local
public interface FileManagerLocal {
     public String uploadFile(MultiPart mp) throws IOException;
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services.REST;

import ch.heigvd.comem.services.FileManagerLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.MultiPart;
import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fabiencornaz
 */
@Stateless
@Path("file")
public class FileREST {
    
    @EJB
    private FileManagerLocal fileManagerLocal;
    
    public FileREST() {
    }
    
    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(MultiPart mp) throws IOException{
        
        return fileManagerLocal.uploadFile(mp);

    }
    
    
}

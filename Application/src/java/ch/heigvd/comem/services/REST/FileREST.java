/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services.REST;

import ch.heigvd.comem.exceptions.ExceptionIdTheme;
import ch.heigvd.comem.exceptions.ExceptionIdUtilisateur;
import ch.heigvd.comem.services.FileManagerLocal;
import ch.heigvd.comem.services.PhotosManagerLocal;
import com.sun.jersey.multipart.BodyPart;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.sun.jersey.multipart.MultiPart;
import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.codehaus.jettison.json.JSONObject;

/**
 *
 * @author fabiencornaz
 */
@Stateless
@Path("file")
public class FileREST {
    
    @EJB
    private FileManagerLocal fileManagerLocal;
    
    @EJB private PhotosManagerLocal photosManagerLocal;
    
    public FileREST() {
    }
    
    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(MultiPart mp) throws IOException, ExceptionIdUtilisateur, ExceptionIdTheme{
        
        String uploadedFileLocation = fileManagerLocal.uploadFile(mp);
        
        BodyPart bpUser = mp.getBodyParts().get(1);
        BodyPart bpTheme = mp.getBodyParts().get(2);
        
        String userIdStr = bpUser.getEntityAs(String.class);
        String themeIdStr = bpTheme.getEntityAs(String.class);
        
        photosManagerLocal.create(0, uploadedFileLocation, Long.valueOf(userIdStr), Long.valueOf(themeIdStr));
        
        String output = "File uploaded via Jersey based RESTFul Webservice to: " + uploadedFileLocation;
        
        return Response.status(200).entity(output).build();
    }
    
    
}

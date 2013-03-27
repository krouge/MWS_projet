/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services.REST;

import ch.heigvd.comem.exceptions.ExceptionIdTheme;
import ch.heigvd.comem.exceptions.ExceptionIdUtilisateur;
import ch.heigvd.comem.services.FileManagerLocal;
import ch.heigvd.comem.services.PhotosManagerLocal;
import ch.heigvd.comem.services.TagsManagerLocal;
import com.sun.jersey.multipart.BodyPart;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.sun.jersey.multipart.MultiPart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    
    @EJB 
    private PhotosManagerLocal photosManagerLocal;
    
    @EJB
    private TagsManagerLocal tagsManagerLocal;
    
    public FileREST() {
    }
    
    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response upload(MultiPart mp) throws IOException, ExceptionIdUtilisateur, ExceptionIdTheme{
        
        String uploadedFileLocation = fileManagerLocal.uploadFile(mp);
        
        BodyPart bpUser = mp.getBodyParts().get(1);
        BodyPart bpTheme = mp.getBodyParts().get(2);
        BodyPart bpTitle = mp.getBodyParts().get(3);
        
        String userIdStr = bpUser.getEntityAs(String.class);
        String themeIdStr = bpTheme.getEntityAs(String.class);
        String titleStr = bpTitle.getEntityAs(String.class);
        
        Long photoId = photosManagerLocal.create(0, uploadedFileLocation, Long.valueOf(userIdStr), Long.valueOf(themeIdStr));
        
        Pattern pattern = Pattern.compile("(?:\\s|\\A|^)[##]+([A-Za-z0-9-_]+)");
        Matcher matcher = pattern.matcher(titleStr);
        
        Long tagId;
        while(matcher.find()) {
            tagId = tagsManagerLocal.create('#'+matcher.group(1));
            photosManagerLocal.associateTag(photoId, tagId);
        }
        
        String output = "File uploaded via Jersey based RESTFul Webservice to: " + uploadedFileLocation;
        
        return Response.status(200).entity(output).build();
    }
}

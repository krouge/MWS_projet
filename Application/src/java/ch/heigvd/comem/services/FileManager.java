/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.heigvd.comem.services;

import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.MultiPart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Renouille
 */
@Stateless
public class FileManager implements FileManagerLocal{

    
    @Override
    public Response uploadFile(MultiPart mp) throws IOException {
            
        MediaType type = mp.getBodyParts().get(0).getMediaType();
        BodyPartEntity bpe = (BodyPartEntity) mp.getBodyParts().get(0).getEntity();
        
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String fileName = "IMG_"+timestamp+".jpg";
        InputStream uploadedInputStream = bpe.getInputStream();

        String uploadedFileLocation = "C:/Users/Renouille/Desktop/" + fileName;
        saveToFile(uploadedInputStream, uploadedFileLocation);
        String output = "File uploaded via Jersey based RESTFul Webservice to: " + uploadedFileLocation;
        
        
        return Response.status(200).entity(output).build();
    }


    // save uploaded file to new location
    private void saveToFile(InputStream uploadedInputStream, String uploadedFileLocation) {

        try {
            OutputStream out = null;
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
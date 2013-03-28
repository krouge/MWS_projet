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
    public String uploadFile(MultiPart mp) throws IOException {
            
        MediaType type = mp.getBodyParts().get(0).getMediaType();
        BodyPartEntity bpe = (BodyPartEntity) mp.getBodyParts().get(0).getEntity();

        String themeIdStr = mp.getBodyParts().get(2).getEntityAs(String.class);
        
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        String fileName = "IMG_"+timestamp+".jpg";
        InputStream uploadedInputStream = bpe.getInputStream();

        String uploadedFileLocation = "/Applications/NetBeans/glassfish-3.1.2.2/glassfish/domains/domain1/docroot/images/"+fileName;
        saveToFile(uploadedInputStream, uploadedFileLocation);
        
        return fileName;
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
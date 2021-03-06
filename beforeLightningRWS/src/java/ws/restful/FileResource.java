package ws.restful;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("File")

public class FileResource {

    @Context
    private UriInfo context;
    @Context
    private ServletContext servletContext;
    private String docrootUrl;

    public FileResource() {
        docrootUrl = "C:/glassfish-5.1.0-uploadedfiles/uploadedFiles";
    }

    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response upload(@FormDataParam("file") InputStream uploadedFileInputStream,
            @FormDataParam("file") FormDataContentDisposition uploadedFileDetails) {
        try {  
            System.err.println("********** FileResource.upload()");
            if (uploadedFileInputStream == null) {
                System.out.println("Nothing has been sent.");
            }
            if (uploadedFileInputStream != null) {
                System.out.println("At least something is received.");
            }

            String outputFilePath = docrootUrl + System.getProperty("file.separator") + uploadedFileDetails.getFileName();
            String fileName = uploadedFileDetails.getFileName();
            System.out.println("Url file: " + outputFilePath);
            File file = new File(outputFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];
            while (true) {
                a = uploadedFileInputStream.read(buffer);

                if (a < 0) {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);

                fileOutputStream.flush();
            }

            fileOutputStream.close();

            uploadedFileInputStream.close();

            return Response.status(Status.OK).entity("uploadedFileDetails.getFileName()").build();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("file processing error").build();
        } catch (IOException ex) {
            ex.printStackTrace();

            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("file processing error").build();
        }
    }

    public static byte[] toByteArray(BufferedImage bi, String format)
            throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;

    }

    @Path("retrievePhoto")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrievePhoto(@QueryParam("imageUrl") String imageUrl) throws IOException {
        String filePath = docrootUrl + System.getProperty("file.separator") + imageUrl;
        System.out.println(filePath);
        File newFile = new File(filePath);
        System.out.println("File exists?" + newFile.exists());
        BufferedImage img = ImageIO.read(new File(filePath));
        if (img != null) {
            System.out.println("Image retrieved.");
        } else {
            System.out.println("Image not retrieved. ");
        }
        byte[] bytes = toByteArray(img, "png");
        try {
            Blob bFile = new javax.sql.rowset.serial.SerialBlob(bytes);
            return Response.status(Status.OK).entity(bFile).build();
        } catch (SQLException ex) {
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Error in retrieving file").build();
        }

    }

    public UriInfo getContext() {
        return context;
    }

    public void setContext(UriInfo context) {
        this.context = context;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    public String getDocrootUrl() {
        return docrootUrl;
    }

    public void setDocrootUrl(String docrootUrl) {
        this.docrootUrl = docrootUrl;
    }
}

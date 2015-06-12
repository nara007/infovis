package ontos.infovis.service;

import ontos.infovis.pojo.Response;
import ontos.infovis.util.ApplicationManager;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.*;

/**
 * This service can be used for uploading a zip file to server.
 * 
 * @author Ye Song
 * @version 1.0
 * @date 15-6-12
 * @copyright infovis@tu-dresden.de
 */
@Path("/")
public class FileUploadResource {

  /**
   * Method handling HTTP POST requests. The returned object will be sent to the client as "json"
   * media type.Method receives a remote uploaded file
   *
   * @param uri String
   * @param version String
   * @return Component if succeed otherwise null
   */
  @POST
  @Path("fileReceiver")
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  @Produces(MediaType.APPLICATION_JSON)
  public Response receiveUploadedFile(@FormDataParam("file") InputStream uploadedInputStream,
      @FormDataParam("file") FormDataContentDisposition fileDetail) {

    String uploadedFileLocation = "./src/main/resources/zip/" + fileDetail.getFileName();
    writeToFile(uploadedInputStream, uploadedFileLocation);
    Response response =
        (Response) ApplicationManager.appManager.getSpringContext().getBean("response");
    response.setBool(true);
    response.setError("upload a file no error");
    response.setException("upload a file no exception");
    return response;
  }

  // save uploaded file to new location
  private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {

    try {
      int read = 0;
      byte[] bytes = new byte[1024];

      OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
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

package apm.apm;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class favicon {

    String getfavicon(String url_servizio) {
        String nome_file_favicon = null;
        File file = null;
      try {
          url_servizio=url_servizio.replace("http://","");
          url_servizio=url_servizio.replace("https://","");
          url_servizio=url_servizio.replace("www.","");
          if(url_servizio.contains("/")){
              url_servizio=url_servizio.substring(0,url_servizio.indexOf("/"));
          }
          if(url_servizio.contains(".")) {
              nome_file_favicon = url_servizio.substring(0, url_servizio.indexOf("."));
              file = new File("favicon/" + nome_file_favicon + ".png");
              if (file.exists()) {
                  return nome_file_favicon;
              }
          }
          URL url = new URL("https://t3.gstatic.com/faviconV2?client=SOCIAL&type=FAVICON&fallback_opts=TYPE,SIZE,URL&url=http://www."+url_servizio+"&size=256");
          BufferedImage image = null;
          image = ImageIO.read(url);
          if (!ImageIO.write(image, "png", file))
              System.err.println("errore nella scrittura");
      }
      catch (MalformedURLException e) {
          e.printStackTrace();
      }
      catch (IOException | StringIndexOutOfBoundsException e) {
          nome_file_favicon = "default";
        }
      return nome_file_favicon;
    }

}
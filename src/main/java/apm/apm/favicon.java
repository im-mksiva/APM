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
          //amazon.com
          //http://
          url_servizio=url_servizio.replace("http://","");
          url_servizio=url_servizio.replace("https://","");
          url_servizio=url_servizio.replace("www.","");
          if(url_servizio.contains("/")){
              url_servizio=url_servizio.substring(0,url_servizio.indexOf("/"));
          }
          //System.out.println("dopo -> "+ url_servizio);

          if(url_servizio.contains(".")) {
              nome_file_favicon = url_servizio.substring(0, url_servizio.indexOf("."));
              //System.out.println("nome -> "+ nome_file_favicon);
              file = new File("favicon/" + nome_file_favicon + ".png");
              if (file.exists()) {
                  return nome_file_favicon;
              }
          }
          URL url = new URL("https://t3.gstatic.com/faviconV2?client=SOCIAL&type=FAVICON&fallback_opts=TYPE,SIZE,URL&url=http://www."+url_servizio+"&size=256");
          BufferedImage image = null;
          image = ImageIO.read(url); // se non si trova una icona allora ci penserà il blocco di catch a risolvere passando il nome dell'icona predefinita
          //servizio = url_diviso[0].substring(0, url_diviso[0].indexOf("."));
          if (!ImageIO.write(image, "png", file))
              System.err.println("errore nella scrittura");
      }
      catch (MalformedURLException e) {
          e.printStackTrace();
      }
      catch (IOException | StringIndexOutOfBoundsException e) { // in questo modo sia quando non mi viene data una favicon dall'url, sia quando l'url non è una stringa adatta
          nome_file_favicon = "default"; // ho una icona da mettere nella UI
        }
      return nome_file_favicon;
    }

}
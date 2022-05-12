package apm.apm;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class favicon {
    String getfavicon(String url_servizio) {
        String servizio = null;
      try {
          //amazon.com
          String[] url_diviso = url_servizio.split("www.");
          servizio = url_diviso[0].substring(0, url_diviso[0].indexOf("."));
          File file = new File("favicon/"+servizio+".png");
          if (file.exists()) {
              return servizio;
          }
          URL url = new URL("https://t3.gstatic.com/faviconV2?client=SOCIAL&type=FAVICON&fallback_opts=TYPE,SIZE,URL&url=http://www."+url_diviso[0]+"&size=256");
          BufferedImage image = null;
          image = ImageIO.read(url); // se non si trova una icona allora ci penserà il blocco di catch a risolvere passando il nome dell'icona predefinita
          servizio = url_diviso[0].substring(0, url_diviso[0].indexOf("."));
          if (!ImageIO.write(image, "png", file))
              System.err.println("errore nella scrittura");
      }
      catch (MalformedURLException e) {
          e.printStackTrace();
      }
      catch (IOException e) {
          servizio = "default";
        }
      return servizio;
    }

}
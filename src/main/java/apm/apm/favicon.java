package apm.apm;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class favicon {
    void getfavicon(String url_servizio) {
      try {
            String[] url_diviso = url_servizio.split("www.");
            URL url = new URL("https://t3.gstatic.com/faviconV2?client=SOCIAL&type=FAVICON&fallback_opts=TYPE,SIZE,URL&url=http://www."+url_diviso[1]+"&size=256");
            BufferedImage image = null;
            image = ImageIO.read(url);
            String servizio = url_diviso[1].substring(0, url_diviso[1].indexOf("."));
            //System.out.println(servizio);
            File file = new File("C:\\Users\\calog\\IdeaProjects\\APM\\favicon\\"+servizio+".png");
            ImageIO.write(image, "png", file);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
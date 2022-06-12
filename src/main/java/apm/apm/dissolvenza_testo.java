package apm.apm;

import javafx.concurrent.Task;
import javafx.scene.text.Text;

public class dissolvenza_testo {

    dissolvenza_testo(Text testo, String messaggio){
        Task task = new Task<Void>() {
            @Override public Void call() {
                try {
                    testo.setText(messaggio);
                    testo.setOpacity(1);
                    Thread.sleep(1500);
                    double num=1;
                    int sleep=500;
                    while (num>0) {
                        num-=0.10;
                        Thread.sleep(sleep);
                        testo.setOpacity(num);
                        if(sleep > 0) {
                            sleep -= 50;
                        }
                    }
                    testo.setText(" ");
                    testo.setOpacity(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            };
        };
        new Thread(task).start();
    }

}

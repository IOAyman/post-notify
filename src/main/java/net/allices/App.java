package net.allices;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class App extends TimerTask {

    private static String lastElement;
    private Map<String, String> cookise;
    private String url = "http://www.djelfa.info/vb/forumdisplay.php?f=331";
    private boolean logged;
    private String USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:26.0) Gecko/20100101 Firefox/26.0";

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new App(), 0, 1000);
    }

    public void run() {
        String elemnt = getFirst();
        if (App.lastElement != null && !elemnt.equals(App.lastElement))
            for (int i = 0; i < 10; i++) {
                try {
                    String cmd;
                    String os = System.getProperty("os.name").toLowerCase();
                    if (os.contains("linux"))
                        cmd = "notify-send --icon=info \"Djelfa.info\" ";
                    else if (os.contains("mac"))
                        cmd = "osascript -e";
                    else {
                        System.err.println("OS not supported!");
                        return;
                    }
                    Runtime.getRuntime()
                            .exec(cmd + "display notification \"NEW POST \" with title \"Djelfa info form\" subtitle \"\" sound name \"Glass\"");
                } catch (Exception ignored) {
                }

            }
        else
            System.err.println(elemnt + " NOT NEW");
    }

    public String getFirst() {
        Connection.Response res = null;
        Document document;
        try {
            document = Jsoup.connect(url).followRedirects(true).timeout(100000).get();
            Elements es = document.select("td").select("[id^=td_threadstatusicon_]");
            if (es != null) {
                return es.get(5).attr("id");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

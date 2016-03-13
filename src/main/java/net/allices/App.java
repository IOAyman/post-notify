package net.allices;

import java.io.IOException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class App extends TimerTask {

	private String url = "http://www.djelfa.info/vb/forumdisplay.php?f=331";
	Map<String, String> cookise;
	private boolean logged;
	private String USER_AGENT = "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:26.0) Gecko/20100101 Firefox/26.0";
	private static String lastElement;

	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new App(), 0, 150000);
	}

	public void run() {
		String elemnt = getFirst();
		if (App.lastElement != null && !elemnt.equals(App.lastElement))
			for (int i = 0; i < 10; i++) {
				try {
					Runtime.getRuntime().exec(new String[] { "osascript", "-e",
							"display notification \"NEW POST \" with title \"Djelfa info form\" subtitle \"\" sound name \"Glass\"" });
				} catch (Exception e) {
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

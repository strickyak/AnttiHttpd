package antti.httpd;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import yak.etc.BaseServer;
import yak.etc.BaseServer.Request;
import yak.etc.BaseServer.Response;
import yak.etc.Yak;

public class TheServer extends BaseServer {

	public static void main(String[] args) {
		try {
			System.err.println("Hello lmnop.");
			// new StoreServer(9999).run();

			String s = ReadUrl("file:///tmp/date");
			System.err.println("RESULT = `" + s + "`");
			
			TheServer serv = new TheServer(9999);
			ReadUrl("http://localhost:9998/?f=boot");
		} catch (IOException e) {
			System.err.println("CAUGHT: " + e);
			e.printStackTrace();
		}
	}

	public TheServer(int port) {
		super(port);
	}

	public Response handleRequest(Request req) {
		/*
		 * URL url; URLConnection conn = url.openConnection(); conn.connect();
		 * conn.getContent();
		 * 
		 * 
		 * HttpURLConnection foo = new HttpURLConnection(); url.getFile();
		 * HTTPClient c;
		 * 
		 * DefaultHttpClient c = 0; foo.getContent();
		 */

		return null;

	}
}

package antti.httpd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import yak.etc.BaseServer;

public class StoreServer extends BaseServer {
	public static void main(String[] args) throws IOException {
		System.err.println("Hello, World");
		new StoreServer(9999).run();
	}

	public StoreServer(int port) {
		super(port);
	}

	public Response handleRequest(Request req) {
		String z = "{REQ PATH=" + Show(req.path) + " QUERY=" + Show(req.query)
				+ "}";
		System.err.println(z);

		if (req.path[0].equals("favicon.ico")) {
			return new Response("No favicon.ico here.", 404, "text/plain");
		}

		String verb = req.query.get("f");
		String user = req.query.get("u");
		String channel = req.query.get("c");
		String tnode = req.query.get("t");
		String latest = req.query.get("latest");
		String value = req.query.get("value");

		try {
			if (verb.equals("fetch")) {
				z = doVerbFetch(channel, tnode);
			} else if (verb.equals("scan")) {
				z = doVerbList(channel, latest);
			} else if (verb.equals("create")) {
				z = doVerbCreate(channel, tnode, value, user);
			} else {
				z = doVerbDefault(verb);
			}
			if (z == null) {
				z = "(((null)))";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new Response("ERROR:\r\n" + e.getMessage(), 200,
					"text/plain");
		}

		return new Response(z, 200, "text/plain");
	}

	public String doVerbDefault(String verb) {
		throw new IllegalArgumentException("Bad verb: " + verb);
	}

	public String doVerbList(String channel, String latest) {
		System.err
				.printf("LIST << channel: %s, latest: %s \n", channel, latest);
		File chanDir = new File(String.format("data/%s/", channel));
		String[] inodes = chanDir.list();
		if (inodes == null) {
			throw new RuntimeException(String.format(
					"Channel %s does not exist.", channel));
		}
		String z = Join(inodes, "\n");
		System.err.printf("LIST >> %s\n", z);
		return z;
	}

	public String doVerbFetch(String channel, String tnode) throws IOException {
		if (channel == null) {
			throw new RuntimeException("NULL CHANNEL!");
		}
		if (tnode == null) {
			throw new RuntimeException("NULL TNODE!");
		}
		File chanDir = new File(new File("data"), channel);
		File tnodeFile = new File(chanDir, tnode);
		BufferedReader r = new BufferedReader(new InputStreamReader(
				new FileInputStream(tnodeFile)));
		String z = r.readLine();
		r.close();
		return z;
	}

	public String doVerbCreate(String channel, String tnode, String value,
			String user) throws IOException {
		if (channel == null) {
			throw new RuntimeException("NULL CHANNEL!");
		}
		if (tnode == null) {
			throw new RuntimeException("NULL TNODE!");
		}
		if (value == null) {
			throw new RuntimeException("NULL VALUE!");
		}

		File chanDir = new File(new File("data"), channel);
		File tnodeFile = new File(chanDir, tnode);
		BufferedWriter w = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(tnodeFile)));
		w.write(value);
		w.close();
		return "OK";
	}
}

package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ThreadLocalRandom;

import gui.ServerGui;

public class Server {

	private ServerGui srvGui;
	private ServerSocket srvSock;
	private Vector<ClientHandler> clhList;
	Socket clSock;

	String[] titleList = { "Title 1", "Title 2", "Title 3", "Title 4" };
	String[] newsTxtList = {
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent viverra nisi elit, ac rhoncus lectus sodales non. Praesent semper elementum sem. Cras in felis vel arcu pulvinar pellentesque. Curabitur tempor lacus nisl, et rhoncus velit elementum at. Maecenas iaculis risus ut tristique bibendum. Vivamus laoreet tempus lacus sit amet porttitor. ",
			"Sed volutpat ultricies porta. Nam quis est tincidunt, interdum diam a, dictum est. Vestibulum ut magna eu quam blandit feugiat. Nulla convallis quam eget nunc vestibulum suscipit. Duis a elementum lectus, sed volutpat ante. Donec sed augue varius, rutrum lorem lacinia, tincidunt nibh. Interdum et malesuada fames ac ante ipsum primis in faucibus.",
			"Nulla accumsan eros in est suscipit scelerisque. Nunc sed odio vitae justo rutrum interdum id in dui. Sed in leo nec nisl eleifend interdum quis a lectus. Fusce sollicitudin magna ac ante fermentum sodales. Ut a arcu nec urna tristique fermentum ut in purus. Nam pellentesque non nunc posuere gravida. Nulla facilisi. Donec blandit finibus bibendum. Aliquam erat volutpat. Maecenas laoreet neque ut ante suscipit venenatis. Sed nisi ante, congue commodo tristique non, auctor in tellus. ",
			"Curabitur in pharetra orci, in laoreet massa. Phasellus at semper urna. Phasellus varius, elit id luctus lacinia, ligula turpis iaculis arcu, non suscipit nisi leo ut ante. Vestibulum iaculis pellentesque augue id porta. Ut fringilla est vel massa pulvinar sollicitudin. Cras vitae maximus ligula." };

	String[] timeList = { "20.03.2019. 23:19", "20.03.2019. 23:16", "20.03.2019. 23:21", "20.03.2019. 23:30" };

	public Server() {
		srvGui = new ServerGui();
		clhList = new Vector<>();

	}

	public void start() throws IOException {
		setNet();
	}

	private void setNet() throws IOException {

		srvSock = new ServerSocket(28070);
		srvGui.SrvState("Server is running ...");

		while (true) {

			clSock = srvSock.accept();

			InputStreamReader isr = new InputStreamReader(clSock.getInputStream());
			new BufferedReader(isr);
			PrintWriter pwr = new PrintWriter(clSock.getOutputStream());

			ClientHandler clh = new ClientHandler(pwr);

			clhList.add(clh);

			Thread thr = new Thread(clh);

			thr.start();
		}

	}

	private class ClientHandler implements Runnable {

		PrintWriter pwriter;

		public ClientHandler(PrintWriter wrt) {
			pwriter = wrt;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			long start = System.currentTimeMillis();

			while (clSock.isConnected()) {

				if (System.currentTimeMillis() - start > 5000) {
					srvGui.write2TxtAr(timeList[ThreadLocalRandom.current().nextInt(0, timeList.length)],
							titleList[ThreadLocalRandom.current().nextInt(0, titleList.length)],
							newsTxtList[ThreadLocalRandom.current().nextInt(0, newsTxtList.length)]);
					send2All(timeList[ThreadLocalRandom.current().nextInt(0, timeList.length)],
							titleList[ThreadLocalRandom.current().nextInt(0, titleList.length)],
							newsTxtList[ThreadLocalRandom.current().nextInt(0, newsTxtList.length)]);
					start = System.currentTimeMillis();

				}
			}
		}

		private PrintWriter getOutWriter() {
			return pwriter;
		}

		public void send2All(String time, String title, String txt) {

			Iterator<ClientHandler> it = clhList.iterator();
			while (it.hasNext()) {
				ClientHandler cl = it.next();
				PrintWriter pwr = cl.getOutWriter();
				pwr.println("Vrijeme : " + time + "   " + "Naslov : " + title + "\n" + txt + "\n\n");
				pwr.flush();

			}

		}

	}

}

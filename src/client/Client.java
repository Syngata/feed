package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import gui.ClientGui;

public class Client {

	private final int ServerPort = 28070;

	private ClientGui clFrame;
	private InputStreamReader isr;
	private BufferedReader bfr;
	private PrintWriter pwr;
	private Socket connection;

	public Client() throws IOException {

		clFrame = new ClientGui();
		start();

	}

	public void start() throws IOException {

		SetNet();
	}

	private void SetNet() throws IOException {

		InetAddress ip = InetAddress.getLocalHost();

		connection = new Socket(ip, ServerPort);

		isr = new InputStreamReader(connection.getInputStream());
		bfr = new BufferedReader(isr);
		pwr = new PrintWriter(connection.getOutputStream());

		Thread rmj = new Thread(new ReceiveMsgJob());
		clFrame.setPrinterWriter(pwr);
		rmj.start();

	}

	private class ReceiveMsgJob implements Runnable {

		@Override
		public void run() {
			String msg;
			while (true) {
				try {

					while ((msg = bfr.readLine()) != null) {
						clFrame.writeReceivedMessage(msg);
					}

				}
				catch (IOException ex) {
					ex.printStackTrace();
				}

			}

		}

	}

}

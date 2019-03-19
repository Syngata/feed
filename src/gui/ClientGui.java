package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import client.Client;

public class ClientGui extends JFrame {

	private JPanel txtPanel;
	private JTextArea txtArea;
	private JPanel infoPanel;
	private JLabel ipTxt;
	private JTextField ipInf;
	private JLabel tcpTxt;
	private JTextField tcpInf;
	private JLabel dateTimeTxt;
	private JTextField dateTimeinf;
	private JLabel statusTxt;
	private JTextField statusInf;
	private JButton connect;
	private PrintWriter pwr;
	private Socket clSock;
	private final int ServerPort = 28070;
	private InputStreamReader isr;
	private BufferedReader bfr;
	private JScrollPane scp;
	private Client cl;

	public ClientGui() throws IOException {

		// super("News client");

		initComp();
		layoutComp();
		cl.setClFrame(this);
		activateBtn();
	}

	private void initComp() throws IOException {

		cl = new Client();
		txtArea = new JTextArea(20, 44);
		txtArea.setEditable(false);
		scp = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		txtPanel = new JPanel();
		txtPanel.setSize(200, 200);

		infoPanel = new JPanel();
		ipTxt = new JLabel("Ip adress");
		ipInf = new JTextField(15);
		ipInf.setEditable(false);
		tcpTxt = new JLabel("TCP socket port");
		tcpInf = new JTextField(12);
		tcpInf.setEditable(false);
		dateTimeTxt = new JLabel("Datum i vrijeme");
		dateTimeinf = new JTextField(15);
		dateTimeinf.setEditable(false);
		statusTxt = new JLabel("Status");
		statusInf = new JTextField(15);
		statusInf.setEditable(false);
		connect = new JButton("connect");

	}

	public String getCurrentTimeUsingCalendar() {

		Calendar cal = Calendar.getInstance();

		Date date = cal.getTime();

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

		String formattedDate = dateFormat.format(date);

		return formattedDate;

	}

	private void activateBtn() throws IOException {

		connect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					cl.start();
					ipInf.setText(cl.getConnection().getInetAddress().toString());
					tcpInf.setText(Integer.toString(cl.getConnection().getPort()));
					dateTimeinf.setText(getCurrentTimeUsingCalendar());
					if (cl.getConnection().isConnected()) {
						statusInf.setText("Connected....");
					} else {
						statusInf.setText("Not connected");
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
	}

	private void layoutComp() {

		setSize(580, 550);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setName("News client");
		setLocationRelativeTo(null);
		GridBagLayout gridLay = new GridBagLayout();
		setLayout(gridLay);
		txtPanel.add(scp);
		txtPanel.setLayout(gridLay);
		infoPanel.setLayout(gridLay);

		GridBagConstraints gdc = new GridBagConstraints();

		gdc.insets = new Insets(0, 0, 20, 0);
		gdc.gridx = 0;
		gdc.gridy = 0;
		add(txtPanel, gdc);

		gdc.gridx = 0;
		gdc.gridy = 1;
		add(infoPanel, gdc);
		gdc.insets = new Insets(0, 0, 2, 0);

		gdc.gridx = 0;
		gdc.gridy = 0;
		infoPanel.add(ipTxt, gdc);

		gdc.gridx = 2;
		gdc.gridy = 0;
		infoPanel.add(dateTimeTxt, gdc);

		gdc.gridx = 1;
		gdc.gridy = 0;
		infoPanel.add(tcpTxt, gdc);

		gdc.insets = new Insets(0, 0, 10, 5);

		gdc.gridx = 0;
		gdc.gridy = 1;
		infoPanel.add(ipInf, gdc);
		gdc.gridx = 1;
		gdc.gridy = 1;
		infoPanel.add(tcpInf, gdc);

		gdc.gridx = 2;
		gdc.gridy = 1;
		infoPanel.add(dateTimeinf, gdc);

		gdc.insets = new Insets(0, 0, 0, 0);

		gdc.gridx = 0;
		gdc.gridy = 2;
		infoPanel.add(statusTxt, gdc);

		gdc.gridx = 0;
		gdc.gridy = 3;
		infoPanel.add(statusInf, gdc);

		gdc.gridx = 2;
		gdc.gridy = 3;
		infoPanel.add(connect, gdc);

		Border outer = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		Border inner = BorderFactory.createTitledBorder("News");
		Border cmpBrd = BorderFactory.createCompoundBorder(outer, inner);
		infoPanel.setBorder(new TitledBorder("Connection info"));
		txtPanel.setBorder(cmpBrd);

	}

	public void setPrinterWriter(PrintWriter writer) {

		this.pwr = writer;
	}

	public void writeReceivedMessage(String msg) {
		txtArea.append(msg);
		txtArea.append("\n");
	}

}

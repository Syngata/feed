package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

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

	public ClientGui() {

		initComp();
		layoutComp();
	}

	private void initComp() {
		txtArea = new JTextArea(20, 20);
		txtPanel = new JPanel();
		txtPanel.setSize(200, 200);

		infoPanel = new JPanel();
		ipTxt = new JLabel("Ip adress");
		ipInf = new JTextField(15);
		tcpTxt = new JLabel("TCP socket port");
		tcpInf = new JTextField(12);
		dateTimeTxt = new JLabel("Datum i vrijeme");
		dateTimeinf = new JTextField(15);
		statusTxt = new JLabel("Status");
		statusInf = new JTextField(15);
		connect = new JButton("connect");

	}

	private void layoutComp() {

		setSize(700, 700);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(true);
		setLocationRelativeTo(null);
		GridBagLayout gridLay = new GridBagLayout();
		setLayout(gridLay);
		txtPanel.setLayout(gridLay);
		infoPanel.setLayout(gridLay);

		GridBagConstraints gdc = new GridBagConstraints();

		gdc.insets = new Insets(0, 0, 20, 0);
		gdc.gridx = 0;
		gdc.gridy = 0;
		add(txtPanel.add(txtArea), gdc);

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

	}

}

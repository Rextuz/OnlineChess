package com.rextuz.onlinechess.desktop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rextuz.onlinechess.OnlineChess;
import com.rextuz.onlinechess.server.Helper;

public class MainGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	Helper helper;
	private JTextField addressTextField;
	private JButton connectButton;
	private JLabel statusLabel2;
	private JTextField usernameTextField;
	private JButton usernameButton;
	private JPanel loadingPanel;
	private JLabel progressInfo;
	private JProgressBar progressBar;

	public MainGUI() {
		super("OnlineChess client");
		setVisible(true);
		setBounds(100, 100, 300, 200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(
				new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		JPanel connectPanel = new JPanel();
		connectPanel
				.setLayout(new BoxLayout(connectPanel, BoxLayout.LINE_AXIS));

		addressTextField = new JTextField("REXTUZ-PC:4242");
		addressTextField.setMaximumSize(new Dimension(4000, 20));
		connectPanel.add(addressTextField);

		connectButton = new JButton("Connect");
		connectButton.setMaximumSize(new Dimension(75, 20));
		connectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String host = addressTextField.getText();
				String[] array1 = host.split(":");
				String hostname = array1[0];
				int port = Integer.parseInt(array1[1]);
				helper = new Helper(hostname, port);
				if (helper.connect()) {
					statusLabel2.setText("connected");
					statusLabel2.setForeground(Color.green);
					addressTextField.setEnabled(false);
					connectButton.setEnabled(false);
					usernameButton.setEnabled(true);
				} else
					JOptionPane.showMessageDialog(getContentPane(),
							"Unable to connect");
			}
		});
		connectPanel.add(connectButton);

		JPanel statusPanel = new JPanel();
		statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.LINE_AXIS));

		JLabel statusLabel1 = new JLabel("Status: ");
		statusPanel.add(statusLabel1);
		statusLabel2 = new JLabel("not connected");
		statusLabel2.setForeground(Color.red);
		statusPanel.add(statusLabel2);

		JPanel usernamePanel = new JPanel();
		usernamePanel.setLayout(new BoxLayout(usernamePanel,
				BoxLayout.LINE_AXIS));

		JLabel usernameLabel = new JLabel("Pick a username");
		usernamePanel.add(usernameLabel);
		usernameTextField = new JTextField();
		usernameTextField.setMaximumSize(new Dimension(4000, 20));
		usernamePanel.add(usernameTextField);
		usernameButton = new JButton("Submit");
		usernameButton.setMaximumSize(new Dimension(75, 20));
		usernameButton.setEnabled(false);
		usernameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!usernameTextField.getText().isEmpty()) {
					try {
						if (helper.login(usernameTextField.getText())) {
							usernameButton.setEnabled(false);
							usernameTextField.setEnabled(false);
							loadingPanel.setVisible(true);
							matchMake();
						} else
							JOptionPane.showMessageDialog(getContentPane(),
									"This name is unavailable");
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(getContentPane(),
								"Connection lost");
					}
				} else {
					JOptionPane.showMessageDialog(getContentPane(),
							"Name cannot be empty");
				}
			}
		});
		usernamePanel.add(usernameButton);

		loadingPanel = new JPanel();
		loadingPanel.setVisible(false);
		loadingPanel
				.setLayout(new BoxLayout(loadingPanel, BoxLayout.PAGE_AXIS));

		progressInfo = new JLabel("Your game is being ready");
		loadingPanel.add(progressInfo);
		progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		loadingPanel.add(progressBar);

		add(connectPanel);
		add(statusPanel);
		add(usernamePanel);
		add(loadingPanel);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					helper.disconnect(usernameTextField.getText());
				} catch (Exception e1) {
				}
			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainGUI();
			}
		});
	}

	private void matchMake() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					String foeName;
					String myName = usernameTextField.getText();
					do {
						java.util.List<String> list = helper.find();
						String myColor;
						if (list.isEmpty()) {
							foeName = helper.search(myName);
							myColor = "white";
						} else {
							foeName = helper.connect(myName);
							myColor = "black";
						}
						if (foeName != null) {
							progressBar.setVisible(false);
							progressInfo.setText("Ready!");
							Thread.sleep(2000);
							setVisible(false);
							LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
							config.title = "Online Chess";
							config.height = 800;
							config.width = 480;
							new LwjglApplication(new OnlineChess(myColor,
									myName, foeName, helper), config);
							helper.remove(myName);
						}
						Thread.sleep(1000);
						return;
					} while (true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
}

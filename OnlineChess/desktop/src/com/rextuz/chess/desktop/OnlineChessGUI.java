package com.rextuz.chess.desktop;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rextuz.chess.OnlineChess;
import com.rextuz.chess.server.ServerSend;

public class OnlineChessGUI {

	private JFrame frame;
	private JTextField nameText;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenu mnWindow;
	private JComboBox<String> comboBox;
	private Vector<String> games = new Vector<String>();
	private DefaultComboBoxModel<String> model;
	private OnlineChessGUI gui = this;
	public String serverIP = "rextuz-pc";
	public int PORT = 4242;

	private String myName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OnlineChessGUI window = new OnlineChessGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public OnlineChessGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblPickAUsername = new JLabel("Pick a username");
		lblPickAUsername.setBounds(10, 11, 125, 14);
		frame.getContentPane().add(lblPickAUsername);

		nameText = new JTextField();
		nameText.setBounds(10, 36, 125, 20);
		frame.getContentPane().add(nameText);
		nameText.setColumns(10);

		JButton submitName = new JButton("Submit");
		submitName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					Registry registry = LocateRegistry.getRegistry(serverIP,
							PORT);
					ServerSend stub = (ServerSend) registry
							.lookup("OnlineChess");
					if (stub.login(nameText.getText())) {
						JOptionPane.showMessageDialog(frame, "Name accepted");
						myName = nameText.getText();
						new GameChecker(myName, gui);
					} else
						JOptionPane.showMessageDialog(frame,
								"Name is already owned", "Error",
								JOptionPane.ERROR_MESSAGE);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(frame,
							"Server is unreachable", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		submitName.setBounds(145, 11, 129, 45);
		frame.getContentPane().add(submitName);

		model = new DefaultComboBoxModel<String>(games);
		comboBox = new JComboBox<String>(model);
		comboBox.setBounds(10, 101, 125, 20);
		frame.getContentPane().add(comboBox);

		JButton btnJoin = new JButton("Join");
		btnJoin.setBounds(145, 100, 129, 23);
		frame.getContentPane().add(btnJoin);
		btnJoin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String foe = (String) comboBox.getSelectedItem();
				LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
				config.title = "Title";
				config.height = 800;
				config.width = 480;
				new LwjglApplication(new OnlineChess("white", myName, foe), config);
			}
		});

		JButton btnJoinRandom = new JButton("Join random");
		btnJoinRandom.setBounds(145, 67, 129, 23);
		frame.getContentPane().add(btnJoinRandom);

		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.dispose();
			}
		});
		mnFile.add(mntmExit);

		mnWindow = new JMenu("Window");
		menuBar.add(mnWindow);

		JMenuItem mntmProperties = new JMenuItem("Properties");
		mnWindow.add(mntmProperties);
	}

	public void addGame(List<String> names) {
		model.removeAllElements();
		for (String s : names)
			model.addElement(s);
	}

}

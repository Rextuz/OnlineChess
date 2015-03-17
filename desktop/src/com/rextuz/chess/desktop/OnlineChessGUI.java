package com.rextuz.chess.desktop;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
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
	private JLabel lblProgress;
	private JTextField nameText;
	private JMenuBar menuBar;
	private JMenu mnFile;
	private JMenu mnWindow;
	private ServerSend server;
	private String myName;
	private boolean connected = false;
	public String serverIP;
	public int PORT;

	private class MyThread implements Runnable {
		String host;
		OnlineChessGUI gui;

		public MyThread(String host, OnlineChessGUI gui) {
			this.host = host;
			this.gui = gui;
		}

		@Override
		public void run() {
			String[] array1 = host.split(":");
			String hostname = array1[0];
			int port = Integer.parseInt(array1[1]);
			try {
				Registry registry = LocateRegistry.getRegistry(hostname, port);
				ServerSend stub = (ServerSend) registry.lookup("OnlineChess");
				gui.server = stub;
				gui.serverIP = hostname;
				gui.PORT = port;
				gui.connected = true;
				System.out.println(hostname + ":" + port + " connected.");
			} catch (Exception e) {
				System.out.println(hostname + ":" + port + " is unreachable");
			}

		}
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OnlineChessGUI window = new OnlineChessGUI();
					if (!window.connected)
						window.kill();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public OnlineChessGUI() throws IOException, InterruptedException {
		findHost();
		initialize();
	}

	private void findHost() throws IOException, InterruptedException {
		BufferedReader bufRead = new BufferedReader(new FileReader("hosts.cfg"));
		String host;
		List<Thread> hosts = new ArrayList<Thread>();
		while ((host = bufRead.readLine()) != null) {
			MyThread thread = new MyThread(host, this);
			hosts.add(new Thread(thread));
		}
		for (Thread t : hosts)
			t.start();
		for (Thread t : hosts)
			t.join();
		bufRead.close();
	}

	private void initialize() {
		frame = new JFrame();
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (e.getID() == WindowEvent.WINDOW_CLOSING) {
					if (connected)
						try {
							if (server.disconnect(myName))
								System.out.println("Disconnected successfully");
						} catch (RemoteException e1) {
							e1.printStackTrace();
						}
				}
			}
		});
		frame.setBounds(100, 100, 300, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblPickAUsername = new JLabel("Pick a username");
		lblPickAUsername.setBounds(10, 12, 133, 14);
		frame.getContentPane().add(lblPickAUsername);

		nameText = new JTextField();
		nameText.setBounds(161, 10, 125, 20);
		frame.getContentPane().add(nameText);
		nameText.setColumns(10);

		JButton submitName = new JButton("Submit and connect");
		submitName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if (server.login(nameText.getText())) {
						JOptionPane.showMessageDialog(frame, "Name accepted");
						myName = nameText.getText();
						matchMake();
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
		submitName.setBounds(10, 38, 276, 20);
		frame.getContentPane().add(submitName);

		lblProgress = new JLabel("");
		lblProgress.setBounds(10, 68, 276, 74);
		frame.getContentPane().add(lblProgress);

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

	public void kill() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	private void matchMake() throws RemoteException, InterruptedException {
		String foe = null;
		boolean found = false;
		do {
			List<String> list = server.find(myName);
			if (list.isEmpty())
				foe = server.search(myName);
			else
				foe = server.connect(myName);
			if (foe != null) {
				LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
				config.title = "Online Chess";
				config.height = 800;
				config.width = 480;
				new LwjglApplication(new OnlineChess("white", myName, foe),
						config);
			}
			Thread.sleep(1000);
		} while (!found);
	}
}

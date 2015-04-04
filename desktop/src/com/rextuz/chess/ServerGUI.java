package com.rextuz.chess;

import com.rextuz.chess.server.AuthServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;

public class ServerGUI extends JFrame {

    JTextField portTextField;
    JButton startButton;
    JLabel statusLabel2;
    JLabel hostLabel;
    JLabel addressLabel;
    JLabel portLabel;
    JPanel infoPanel;

    public ServerGUI() {
        super("OnlineChess server");
        setVisible(true);
        setBounds(100, 100, 300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel startPanel = new JPanel();
        startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.LINE_AXIS));

        {
            portTextField = new JTextField("4242");
            startPanel.add(portTextField);
            portTextField.setMaximumSize(new Dimension(4000, 20));
            startButton = new JButton("Start");
            startButton.setMaximumSize(new Dimension(150, 20));
            startButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        int port = Integer.parseInt(portTextField.getText());
                        AuthServer server = new AuthServer(port);
                        if (!server.start())
                            JOptionPane.showMessageDialog(getContentPane(), "Server failed to start. Try changing port");
                        else {
                            statusLabel2.setText("running");
                            statusLabel2.setForeground(Color.green);
                            startButton.setEnabled(false);
                            infoPanel.setVisible(true);
                        }
                    } catch (Exception e1) {
                        JOptionPane.showMessageDialog(getContentPane(), "Incorrect port");
                    }
                }
            });
            startPanel.add(startButton);
        }

        JPanel globalStatusPanel = new JPanel();
        globalStatusPanel.setLayout(new BoxLayout(globalStatusPanel, BoxLayout.PAGE_AXIS));

        {
            {
                JPanel statusPanel = new JPanel();
                statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.LINE_AXIS));

                JLabel statusLabel1 = new JLabel("Server is ");
                statusPanel.add(statusLabel1);
                statusLabel2 = new JLabel("idle");
                statusLabel2.setForeground(Color.gray);
                statusPanel.add(statusLabel2);

                globalStatusPanel.add(statusPanel);
            }
            {
                infoPanel = new JPanel();
                infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));

                String hostname = "unknown";
                String address = "unknown";
                String port = "unknown";
                try {
                    hostname = InetAddress.getLocalHost().getHostName();
                    address = InetAddress.getLocalHost().getHostAddress();
                    port = portTextField.getText();
                } catch (Exception e) {
                }
                hostLabel = new JLabel("Hostname: " + hostname);
                addressLabel = new JLabel("Address: " + address);
                portLabel = new JLabel("Port: " + port);
                infoPanel.add(hostLabel);
                infoPanel.add(addressLabel);
                infoPanel.add(portLabel);
                infoPanel.setVisible(false);

                globalStatusPanel.add(infoPanel);
            }
        }

        add(startPanel);
        add(globalStatusPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ServerGUI();
            }
        });
    }
}

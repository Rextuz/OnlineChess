package com.rextuz.chess.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.rextuz.onlinechess.server.Helper;


public class MainActivity extends Activity {

    private Helper helper;
    private String myName;
    private String hostname;
    private int port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.connectButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText serverEditText = (EditText) findViewById(R.id.serverEditText);
                String host = serverEditText.getText().toString();
                String[] array1 = host.split(":");
                hostname = array1[0];
                port = Integer.parseInt(array1[1]);
                helper = new Helper(hostname, port);
                if (helper.connect()) {
                    Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Unable to connect", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
                myName = nameEditText.getText().toString();
                try {
                    if (helper.login(myName))
                        matchMake();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void matchMake() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String foeName;
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
                            /*
                            progressBar.setVisible(false);
                            progressInfo.setText("Ready!");
                            Thread.sleep(2000);
                            setVisible(false);
                            LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
                            config.title = "Online Chess";
                            config.height = 800;
                            config.width = 480;
                            new LwjglApplication(new OnlineChess(myColor, myName, foeName, helper),
                                    config);*/
                            Intent intent = new Intent(MainActivity.this, AndroidLauncher.class);
                            intent.putExtra("myColor", myColor);
                            intent.putExtra("myName", myName);
                            intent.putExtra("foeName", foeName);
                            intent.putExtra("hostname", hostname);
                            intent.putExtra("port", port);
                            startActivity(intent);
                            helper.remove(myName);
                        }
                        Thread.sleep(20);
                        return;
                    } while (true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

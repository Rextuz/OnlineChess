package com.rextuz.chess.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.rextuz.chess.server.AuthServerInterface;
import com.rextuz.chess.server.Message;

import java.net.Socket;


public class MainActivity extends Activity {

    Button connectButton;
    EditText serverEdit;

    AuthServerInterface stub;
    String hostname;
    int port;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectButton = (Button) findViewById(R.id.connectButton);
        serverEdit = (EditText) findViewById(R.id.serverEdit);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String host = serverEdit.getText().toString();
                String[] array1 = host.split(":");
                hostname = array1[0];
                port = Integer.parseInt(array1[1]);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Socket s = new Socket(hostname, port);
                            Message message = new Message("Hello World!", s);
                            message.send(s);
                        } catch (Exception e1) {
//                            Toast.makeText(getApplicationContext(), e1 + "", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).start();
            }
        });
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

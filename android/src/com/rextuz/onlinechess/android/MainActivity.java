package com.rextuz.onlinechess.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.rextuz.onlinechess.OnlineChess;
import com.rextuz.onlinechess.server.Helper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends Activity {
    private Helper helper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.connectButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connect();
            }
        });

        Button serverButton = (Button) findViewById(R.id.serverButton);
        serverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serverActivity();
            }
        });

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String myName = ((EditText) findViewById(R.id.nicknameEditText)).getText().toString();
                if (login(myName))
                    matchmaker(myName);
            }
        });
    }

    private void connect() {
        EditText addressEditText = (EditText) findViewById(R.id.addressEditText);
        String host = addressEditText.getText().toString();
        String[] array1 = host.split(":");
        String hostname = array1[0];
        int port = Integer.parseInt(array1[1]);
        helper = new Helper(hostname, port);
        if (helper.connect()) {
            Toast.makeText(getApplicationContext(), "Connected", Toast.LENGTH_SHORT).show();
            FrameLayout connectLayout = (FrameLayout) findViewById(R.id.connectLayout);
            connectLayout.setVisibility(View.GONE);
            FrameLayout loginLayout = (FrameLayout) findViewById(R.id.loginLayout);
            loginLayout.setVisibility(View.VISIBLE);
        } else
            Toast.makeText(getApplicationContext(), "Not connected", Toast.LENGTH_SHORT).show();
    }

    private void serverActivity() {
        Intent intent = new Intent(this, ServerActivity.class);
        startActivity(intent);
    }

    private boolean login(String myName) {
        try {
            return helper.login(myName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void matchmaker(final String myName) {
        try {
            ExecutorService executor = Executors.newCachedThreadPool();
            Future<String[]> future = executor.submit(new Callable<String[]>() {
                @Override
                public String[] call() throws Exception {
                    String foeName = null;
                    String myColor = null;
                    try {
                        do {
                            java.util.List<String> list = helper.find();
                            if (list.isEmpty()) {
                                foeName = helper.search(myName);
                                myColor = "white";
                            } else {
                                foeName = helper.connect(myName);
                                myColor = "black";
                            }
                            if (foeName != null) {
                                Thread.sleep(2000);

                                helper.remove(myName);
                            }
                            Thread.sleep(1000);
                            break;
                        } while (true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return new String[]{myColor, foeName};
                }
            });
            executor.shutdown();

            String[] res = future.get();
            String myColor = res[0];
            String foeName = res[1];
            Intent intent = new Intent(this, AndroidLauncher.class);
            intent.putExtra("myColor", myColor);
            intent.putExtra("myName", myName);
            intent.putExtra("foeName", foeName);
            byte[] bytes;

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutput out = null;
            try {
                out = new ObjectOutputStream(bos);
                out.writeObject(helper);
                bytes = bos.toByteArray();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException ex) {
                    // ignore close exception
                }
                try {
                    bos.close();
                } catch (IOException ex) {
                    // ignore close exception
                }
            }
            intent.putExtra("helper", bytes);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
/*

package com.rextuz.onlinechess.android;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.rextuz.onlinechess.OnlineChess_default;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new OnlineChess_default(), config);
    }
}

 */
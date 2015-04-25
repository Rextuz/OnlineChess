package com.rextuz.chess.android;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.rextuz.onlinechess.OnlineChess;
import com.rextuz.onlinechess.server.Helper;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        Bundle extras = getIntent().getExtras();
        String myColor = extras.getString("myColor");
        String myName = extras.getString("myName");
        String foeName = extras.getString("foeName");
        String hostname = extras.getString("hostname");
        int port = extras.getInt("port");

        Helper helper = new Helper(hostname, port);
        helper.connect();
        initialize(new OnlineChess(myColor, myName, foeName, helper), config);
    }
}

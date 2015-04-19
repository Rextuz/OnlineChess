package com.rextuz.onlinechess.android;

import android.content.Intent;
import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.rextuz.onlinechess.OnlineChess;
import com.rextuz.onlinechess.server.Helper;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String myColor = bundle.getString("myColor");
        String myName = bundle.getString("myName");
        String foeName = bundle.getString("foeName");
        byte[] bytes = bundle.getByteArray("helper");
        Helper helper = null;

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in;
        try {
            in = new ObjectInputStream(bis);
            helper = (Helper) in.readObject();
            bis.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initialize(new OnlineChess(myColor, myName, foeName, helper), config);
    }
}
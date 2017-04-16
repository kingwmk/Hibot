package cn.ybz21.hibotvoice;

import android.app.Application;

import java.io.File;

import cn.ybz21.hibotvoice.util.StaticValues;

/**
 * Created by smarf on 2017/2/14.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    void init() {
        File dir = new File(StaticValues.path);
        if (!dir.exists())
            dir.mkdirs();
    }
}

package com.styephenwilliambuli.testandroidp3l.Mdoels;

import android.app.Application;

public class GlobalVariable extends Application {
    private static int idOrder;

    public static int getIdOrder() {
        return idOrder;
    }

    public static void setIdOrder(int id) {
        idOrder = id;
    }
}

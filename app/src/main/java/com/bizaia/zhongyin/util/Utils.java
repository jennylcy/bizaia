package com.bizaia.zhongyin.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by jensen on 11/12/15.
 */
public class Utils {

    private Utils() {
    }

    public static boolean close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        } else {
            return false;
        }
    }
}

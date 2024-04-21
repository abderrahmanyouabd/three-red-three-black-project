package com.a1st.threeredthreeblack.util;

/**
 * @author: Abderrahman Youabd aka: A1ST
 * @version: 1.0
 */

@FunctionalInterface
public interface RestartGameListener {
    void onRestartGame(RestartGameEvent event);
}

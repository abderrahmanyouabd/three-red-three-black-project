package com.a1st.threeredthreeblack.util;

import java.util.EventObject;

/**
 * @author: Abderrahman Youabd aka: A1ST
 * @version: 1.0
 */
public class RestartGameEvent extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public RestartGameEvent(Object source) {
        super(source);
    }
}

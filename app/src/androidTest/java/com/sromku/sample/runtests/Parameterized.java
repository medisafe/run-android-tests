package com.sromku.sample.runtests;

import androidx.test.platform.app.InstrumentationRegistry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by sromku on March, 2017.
 */
public class Parameterized {

    public static final String PARAM_INDEX = "paramIndex";

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public static @interface Repeat {
        int count();
    }

    public static int getIndex() {
        try {
            return Integer.parseInt(InstrumentationRegistry.getArguments().getString(PARAM_INDEX));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

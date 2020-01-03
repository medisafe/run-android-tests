package com.sromku.sample.runtests.shard;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.sromku.sample.runtests.ClearData;
import com.sromku.sample.runtests.MainActivity;
import com.sromku.sample.runtests.Utils;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

/**
 * Created by sromku on April, 2017.
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShardD {

    private final static int SMALL_TEST = 10000;
    private final static int LARGE_TEST = 30000;

    @Rule
    public IntentsTestRule<MainActivity> mActivity = new IntentsTestRule<>(MainActivity.class);

    @Test
    @ClearData
    public void testA() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(SMALL_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    public void testB() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(SMALL_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    public void testC() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(LARGE_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    public void testD() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(SMALL_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    public void testE() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(SMALL_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    public void testF() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(SMALL_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    public void testG() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(LARGE_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    public void testH() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(SMALL_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    public void testI() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(SMALL_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    public void testJ() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(SMALL_TEST);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }
}

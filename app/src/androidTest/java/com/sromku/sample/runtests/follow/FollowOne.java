package com.sromku.sample.runtests.follow;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.sromku.sample.runtests.ClearData;
import com.sromku.sample.runtests.Following;
import com.sromku.sample.runtests.MainActivity;
import com.sromku.sample.runtests.Utils;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FollowOne {

    @Rule
    public IntentsTestRule<MainActivity> mActivity = new IntentsTestRule<>(MainActivity.class);

    @Test
    @ClearData
    public void testA() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(2000);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @Following
    public void testB() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(2000);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    public void testC() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(2000);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @ClearData
    @Following
    public void testD() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(2000);
        assertEquals("--com.sromku.sample.runtests--", appContext.getPackageName());
    }

    @Test
    @Following
    public void testE() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(2000);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

}

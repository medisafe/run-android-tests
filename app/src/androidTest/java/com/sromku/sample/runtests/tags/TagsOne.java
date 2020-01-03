package com.sromku.sample.runtests.tags;

import android.content.Context;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.sromku.sample.runtests.MainActivity;
import com.sromku.sample.runtests.Tags;
import com.sromku.sample.runtests.Utils;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagsOne {

    @Rule
    public IntentsTestRule<MainActivity> mActivity = new IntentsTestRule<>(MainActivity.class);

    @Test
    @Tags(tags = {"sanity", "small"})
    public void testA() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(1000);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @Tags(tags = {"sanity", "medium"})
    public void testB() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(4000);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @Tags(tags = {"special"})
    public void testC() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(4000);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @Tags(tags = {"extreme", "special"})
    public void testD() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(4000);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }

    @Test
    @Tags(tags = {"extreme", "small", "medium", "special"})
    public void testE() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Utils.sleep(4000);
        assertEquals("com.sromku.sample.runtests", appContext.getPackageName());
    }
}

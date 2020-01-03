package com.sromku.sample.runtests.param;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.sromku.sample.runtests.ClearData;
import com.sromku.sample.runtests.MainActivity;
import com.sromku.sample.runtests.Parameterized;
import com.sromku.sample.runtests.Utils;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ParamOne {

    @Rule
    public IntentsTestRule<MainActivity> mActivity = new IntentsTestRule<>(MainActivity.class);

    private final String[] params = new String[]{
            "a", "A", "Aa"
    };

    @Test
    @Parameterized.Repeat(count = 2)
    @ClearData
    public void testA() throws Exception {
        int index = Parameterized.getIndex();
        if (index < 0) {
            return;
        }
        String param = params[index];
        Utils.sleep(2000);
        assertEquals("a", param.toLowerCase());
    }

    @Test
    @Parameterized.Repeat(count = 3)
    public void testB() throws Exception {
        int index = Parameterized.getIndex();
        if (index < 0) {
            return;
        }
        String param = params[index];
        Utils.sleep(2000);
        assertEquals("a", param.toLowerCase());
    }

}

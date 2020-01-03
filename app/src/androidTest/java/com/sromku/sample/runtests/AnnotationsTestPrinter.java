package com.sromku.sample.runtests;

import android.os.Bundle;
import androidx.test.internal.runner.listener.InstrumentationResultPrinter;
import androidx.test.internal.runner.listener.InstrumentationRunListener;

import org.junit.runner.Description;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * Created by sromku.
 */
public class AnnotationsTestPrinter extends InstrumentationRunListener {

    @Override
    public void testStarted(Description description) throws Exception {
        super.testStarted(description);

        Collection<Annotation> annotations = description.getAnnotations();
        if (annotations == null) {
            return;
        }

        Bundle bundle = new Bundle();
        StringBuilder stringBuilder = new StringBuilder();
        boolean comm = false;
        String tagsStr = "";
        for (Annotation annotation : annotations) {
            if (comm) stringBuilder.append(",");
            stringBuilder.append(annotation.annotationType().getSimpleName());

            // special case of Repeat
            if (annotation instanceof Parameterized.Repeat) {
                Parameterized.Repeat repeat = (Parameterized.Repeat) annotation;
                stringBuilder.append(":" + repeat.count());
            }

            // special case of Tags
            if (annotation instanceof Tags) {
                Tags tags = (Tags) annotation;
                tagsStr = buildTags(tags.tags());
            }
            comm = true;
        }

        bundle.putString("annotations", stringBuilder.toString());
        bundle.putString("tags", tagsStr);
        getInstrumentation().sendStatus(InstrumentationResultPrinter.REPORT_VALUE_RESULT_START, bundle);
    }

    private String buildTags(String[] tags) {
        StringBuilder stringBuilder = new StringBuilder();
        boolean comm = false;
        for (String tag : tags) {
            if (comm) stringBuilder.append(",");
            stringBuilder.append(tag);
            comm = true;
        }
        return stringBuilder.toString();
    }
}

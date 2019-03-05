package com.example.pourya.editor;

import com.example.pourya.editor.MVP.MVP_Main;
import com.example.pourya.editor.MVP.MainPresenter;

import org.hamcrest.Description;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        Pattern pt = Pattern.compile(".1");
        Matcher m = pt.matcher("11");
        m.matches();
    }

    @Test
    public void str(){
        assertThat("11", new org.hamcrest.Matcher<String>() {
            @Override
            public boolean matches(Object item) {
                if (item.equals("11")) {
                    return true;
                }
                return false;
            }

            @Override
            public void describeMismatch(Object item, Description mismatchDescription) {

            }

            @Override
            public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

            }

            @Override
            public void describeTo(Description description) {

            }
        });
    }

    @Test
    public void list(){


    }
}
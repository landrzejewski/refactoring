package pl.training;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class Digits extends TypeSafeMatcher<String> {

    @Override
    protected boolean matchesSafely(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("Should contains only digits");
    }

}

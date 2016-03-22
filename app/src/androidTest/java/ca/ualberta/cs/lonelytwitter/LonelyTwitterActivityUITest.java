package ca.ualberta.cs.lonelytwitter;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by sajediba on 2/8/16.
 */
public class LonelyTwitterActivityUITest extends ActivityInstrumentationTestCase2 {

    Instrumentation instrumentation;
    Activity activity;
    EditText textInput;

    public LonelyTwitterActivityUITest() {
        super(LonelyTwitterActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        instrumentation = getInstrumentation();
        activity = getActivity();
        textInput = ((EditText) activity.findViewById(ca.ualberta.cs.lonelytwitter.R.id.tweetMessage));
    }

    //makeTweet(text) fills in the input text field and clicks the 'save' button for the activity under test:
    private void makeTweet(String text) {
        assertNotNull(activity.findViewById(ca.ualberta.cs.lonelytwitter.R.id.saveButton));
        textInput.setText(text);
        ((Button) activity.findViewById(ca.ualberta.cs.lonelytwitter.R.id.saveButton)).performClick();
    }

    @UiThreadTest
    public void testMakeTweet() {
        LonelyTwitterActivity lta = (LonelyTwitterActivity) getActivity();
        int oldLength = lta.getAdapter().getCount();

        makeTweet("test string");
        ArrayAdapter<Tweet> arrayAdapter = lta.getAdapter();
        assertEquals(oldLength + 1, arrayAdapter.getCount());

        assertTrue("Did you add a Tweet object?",
                arrayAdapter.getItem(arrayAdapter.getCount() - 1) instanceof Tweet);

        Tweet tweet = arrayAdapter.getItem(arrayAdapter.getCount() - 1);
        assertEquals("This is not the text we expected!", tweet.getMessage(),
                "test string");
    }
}

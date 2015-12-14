package com.toxablack.chat.test;

import com.toxablack.chat.MainActivity;
import com.robotium.solo.*;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SettingsTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    public SettingsTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testRun() {
        solo.waitForActivity(com.toxablack.chat.MainActivity.class, 2000);
        Timeout.setSmallTimeout(25285);
        assertNotNull("View is null", testView());
        assertNotNull("EditText is null", testEditText());
        assertTrue("com.toxablack.chat.SettingsActivity is not found!", testSettingsActivity());
        assertNotNull("Button is null", testSecondButton());
        solo.goBack();
    }

    private View testView() {
        View view = solo.getView(com.toxablack.chat.R.id.action_add);
        solo.clickOnView(view);
        return view;
    }

    private EditText testEditText() {
        solo.waitForDialogToOpen(5000);
        EditText text = solo.getView(android.widget.EditText.class, 0);
        solo.clickOnView(text);
        solo.clearEditText(text);
        solo.enterText(text, "test@gmail.com");
        return  text;
    }

    private boolean testSettingsActivity() {
        solo.clickOnView(solo.getView(android.R.id.button1));
        solo.clickOnView(solo.getView(com.toxablack.chat.R.id.action_settings));
        return solo.waitForActivity(com.toxablack.chat.SettingsActivity.class);
    }

    private Button testSecondButton() {
        solo.clickInList(1, 0);
        solo.waitForDialogToOpen(5000);
        solo.clearEditText((android.widget.EditText) solo.getView(android.R.id.edit));
        solo.enterText((android.widget.EditText) solo.getView(android.R.id.edit), "Anton");
        solo.clickOnView(solo.getView(android.R.id.button1));
        solo.clickInList(2, 0);
        solo.waitForDialogToOpen(5000);
        solo.goBack();
        solo.waitForDialogToClose(5000);
        solo.clickInList(4, 0);
        solo.clickInList(4, 0);
        solo.clickInList(5, 0);
        solo.clickInList(5, 0);
        solo.clickInList(7, 0);
        solo.waitForDialogToOpen(5000);
        Button button = (Button) solo.getView(android.R.id.button2);
        solo.clickOnView(button);
        solo.clickInList(8, 0);
        solo.waitForDialogToOpen(5000);
        solo.clickOnView(solo.getView(android.R.id.button2));
        return button;
    }
}

package com.toxablack.chat.test;

import com.toxablack.chat.MainActivity;
import com.robotium.solo.*;
import com.toxablack.chat.R;

import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ChatTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    private EditText mEditText;
    private Button mButton;

    public ChatTest() {
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
        assertEquals(true, testMainActivity());
        assertEquals(true, testDialog());

        assertNotNull("EditText is null", testEditText());
        assertNotNull("Button is null", testButton());
        assertTrue("com.toxablack.chat.ChatActivity is not found!", testChatActivity());
        assertNotNull("mEditText is null", testText());
        assertNotNull("mButton is null", testSendButton());

        solo.clearEditText((android.widget.EditText) solo.getView(com.toxablack.chat.R.id.msg_edit));
        solo.enterText((android.widget.EditText) solo.getView(com.toxablack.chat.R.id.msg_edit), "hi");
        solo.clickOnView(solo.getView(com.toxablack.chat.R.id.send_btn));
        Timeout.setSmallTimeout(35309);
        solo.goBack();
    }

    public boolean testMainActivity() {
        boolean bool = solo.waitForActivity(com.toxablack.chat.MainActivity.class, 2000);
        return bool;
    }

    public boolean testDialog() {
        solo.clickOnView(solo.getView(com.toxablack.chat.R.id.action_add));
        boolean bool = solo.waitForDialogToOpen(5000);
        return bool;

    }

    private EditText testEditText() {
        EditText editText = (EditText) solo.getView(android.widget.EditText.class, 0);
        solo.clickOnView(editText);
        Timeout.setSmallTimeout(19632);
        solo.clearEditText(editText);
        solo.enterText(editText, "antoncharnou@gmail.com");
        return editText;
    }

    private Button testButton() {
        Button button = (Button) solo.getView(android.R.id.button1);
        solo.clickOnView(button);
        return button;
    }

    private boolean testChatActivity() {
        solo.clickInList(1, 0);
        return solo.waitForActivity(com.toxablack.chat.ChatActivity.class);
    }

    private EditText testText() {
        mEditText = (EditText) solo.getView(R.id.msg_edit);
        return mEditText;
    }

    private Button testSendButton() {
        mButton = (Button) solo.getView(R.id.send_btn);
        return mButton;
    }
}

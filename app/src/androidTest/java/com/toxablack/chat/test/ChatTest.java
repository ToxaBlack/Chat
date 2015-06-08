package com.toxablack.chat.test;

import com.toxablack.chat.MainActivity;
import com.robotium.solo.*;
import com.toxablack.chat.R;

import android.test.ActivityInstrumentationTestCase2;
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
        //Wait for activity: 'com.toxablack.chat.MainActivity'
		solo.waitForActivity(com.toxablack.chat.MainActivity.class, 2000);
        //Click on Empty Text View
		solo.clickOnView(solo.getView(com.toxablack.chat.R.id.action_add));
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Click on Empty Text View
		solo.clickOnView(solo.getView(android.widget.EditText.class, 0));
        //Set default small timeout to 19632 milliseconds
		Timeout.setSmallTimeout(19632);
        //Enter the text: 'antoncharnou@gmail.com'
		solo.clearEditText((android.widget.EditText) solo.getView(android.widget.EditText.class, 0));
		solo.enterText((android.widget.EditText) solo.getView(android.widget.EditText.class, 0), "antoncharnou@gmail.com");
		//Click on ОК
		solo.clickOnView(solo.getView(android.R.id.button1));
		//Click on antoncharnou
		solo.clickInList(1, 0);
        //Wait for activity: 'com.toxablack.chat.ChatActivity'
		assertTrue("com.toxablack.chat.ChatActivity is not found!", solo.waitForActivity(com.toxablack.chat.ChatActivity.class));
		mEditText =
				(EditText) solo.getView(R.id.msg_edit);
		mButton =
				(Button) solo.getView(R.id.send_btn);
		assertNotNull("mEditText is null", mEditText);
		assertNotNull("mButton is null", mButton);
		//Enter the text: 'hi'
		solo.clearEditText((android.widget.EditText) solo.getView(com.toxablack.chat.R.id.msg_edit));
		solo.enterText((android.widget.EditText) solo.getView(com.toxablack.chat.R.id.msg_edit), "hi");
        //Click on Send
		solo.clickOnView(solo.getView(com.toxablack.chat.R.id.send_btn));
        //Set default small timeout to 35309 milliseconds
		Timeout.setSmallTimeout(35309);
        //Click on Empty Text View

        //Press menu back key
		solo.goBack();
	}
}

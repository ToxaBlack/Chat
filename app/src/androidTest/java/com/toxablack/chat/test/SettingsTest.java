package com.toxablack.chat.test;

import com.toxablack.chat.MainActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


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
        //Wait for activity: 'com.toxablack.chat.MainActivity'
		solo.waitForActivity(com.toxablack.chat.MainActivity.class, 2000);
        //Set default small timeout to 25285 milliseconds
		Timeout.setSmallTimeout(25285);
        //Click on Empty Text View
		solo.clickOnView(solo.getView(com.toxablack.chat.R.id.action_add));
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Click on Empty Text View
		solo.clickOnView(solo.getView(android.widget.EditText.class, 0));
        //Enter the text: 'test@gmail.com'
		solo.clearEditText((android.widget.EditText) solo.getView(android.widget.EditText.class, 0));
		solo.enterText((android.widget.EditText) solo.getView(android.widget.EditText.class, 0), "test@gmail.com");
        //Click on ОК
		solo.clickOnView(solo.getView(android.R.id.button1));
        //Click on Empty Text View
		solo.clickOnView(solo.getView(com.toxablack.chat.R.id.action_settings));
        //Wait for activity: 'com.toxablack.chat.SettingsActivity'
		assertTrue("com.toxablack.chat.SettingsActivity is not found!", solo.waitForActivity(com.toxablack.chat.SettingsActivity.class));
        //Click on LinearLayout Display name toxablack LinearLayout
		solo.clickInList(1, 0);
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Enter the text: 'Anton'
		solo.clearEditText((android.widget.EditText) solo.getView(android.R.id.edit));
		solo.enterText((android.widget.EditText) solo.getView(android.R.id.edit), "Anton");
        //Click on ОК
		solo.clickOnView(solo.getView(android.R.id.button1));
        //Click on LinearLayout Chat email id toxablack@gmail.com LinearLayout
		solo.clickInList(2, 0);
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Press menu back key
		solo.goBack();
        //Wait for dialog to close
		solo.waitForDialogToClose(5000);
        //Click on LinearLayout New message notifications  LinearLayout
		solo.clickInList(4, 0);
        //Click on LinearLayout New message notifications  LinearLayout
		solo.clickInList(4, 0);
        //Click on LinearLayout Ringtone По умолчанию (Zirconium) LinearLayout
		solo.clickInList(5, 0);
        //Click on LinearLayout Ringtone Silent LinearLayout
		solo.clickInList(5, 0);
        //Click on LinearLayout Server URL  LinearLayout
		solo.clickInList(7, 0);
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Click on Отмена
		solo.clickOnView(solo.getView(android.R.id.button2));
        //Click on LinearLayout GCM Sender ID  LinearLayout
		solo.clickInList(8, 0);
        //Wait for dialog
		solo.waitForDialogToOpen(5000);
        //Click on Отмена
		solo.clickOnView(solo.getView(android.R.id.button2));
        //Press menu back key
		solo.goBack();
	}
}

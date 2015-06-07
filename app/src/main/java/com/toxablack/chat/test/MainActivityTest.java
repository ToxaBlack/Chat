
package com.toxablack.chat.test;

import com.toxablack.chat.R;
import com.toxablack.chat.MainActivity;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Adapter;
import android.widget.Button;

/**
 * Tests LaunchActivity in isolation from the system.
 */
public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {

    private Intent mLaunchIntent;

    public MainActivityTest() {
        super(MainActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        //Create an intent to launch target Activity
        mLaunchIntent = new Intent(getInstrumentation().getTargetContext(),
                MainActivity.class);
    }

    /**
     * Tests the preconditions of this test fixture.
     */
    @MediumTest
    public void testPreconditions() {
        //Start the activity under test in isolation, without values for savedInstanceState and
        //lastNonConfigurationInstance
        startActivity(mLaunchIntent, null, null);


        assertNotNull("mLaunchActivity is null", getActivity());

    }





}
package com.toxablack.chat.test;

import android.test.ActivityInstrumentationTestCase2;
import android.test.InstrumentationTestCase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.toxablack.chat.ChatActivity;
import com.toxablack.chat.R;

/**
 * Created by ¿ÌÚÓÌ on 13.05.2015.
 */
public class ChattActivityTest
        extends ActivityInstrumentationTestCase2<ChatActivity> {

    private ChatActivity mChatActivity;
    private EditText mEditText;
    private Button mButton;

    public ChattActivityTest() {
        super(ChatActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mChatActivity = getActivity();
        mEditText =
                (EditText) mChatActivity
                        .findViewById(R.id.msg_edit);
        mButton =
                (Button) mChatActivity.findViewById(R.id.send_btn);
    }

    public void testPreconditions() {
        assertNotNull("mChatActivity is nullî", mChatActivity);
        assertNotNull("mEditText is null", mEditText);
        assertNotNull("mButton is null", mButton);
    }
}

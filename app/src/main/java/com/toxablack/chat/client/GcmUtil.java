package com.toxablack.chat.client;

import java.io.IOException;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;


import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.toxablack.chat.Common;

public class GcmUtil {
	
	private static final String TAG = "GcmUtil";
	
	public static final String PROPERTY_REG_ID = "registration_id";
	private static final String PROPERTY_APP_VERSION = "appVersion";
	private static final String PROPERTY_ON_SERVER_EXPIRATION_TIME = "onServerExpirationTimeMs";
	
    /**
     * Default lifespan (7 days) of a reservation until it is considered expired.
     */
    public static final long REGISTRATION_EXPIRY_TIME_MS = 1000 * 3600 * 24 * 7;
    
    private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();    

    private Context mContext;
	private SharedPreferences mSharedPreferences;
	private GoogleCloudMessaging mCloudMessaging;

	public GcmUtil(Context applicationContext) {
		super();
		mContext = applicationContext;
		mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		
		String regid = getRegistrationId();
		if (regid.length() == 0) {
            registerBackground();
        } else {
        	broadcastStatus(true);
        }
		mCloudMessaging = GoogleCloudMessaging.getInstance(mContext);
	}
	

	private String getRegistrationId() {
	    String registrationId = mSharedPreferences.getString(PROPERTY_REG_ID, "");
	    if (registrationId.length() == 0) {
	        //Log.v(TAG, "Registration not found.");
	        return "";
	    }

	    int registeredVersion = mSharedPreferences.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	    int currentVersion = getAppVersion();
	    if (registeredVersion != currentVersion || isRegistrationExpired()) {
	        //Log.v(TAG, "App version changed or registration expired.");
	        return "";
	    }
	    return registrationId;
	}
	

	private void setRegistrationId(String regId) {
	    int appVersion = getAppVersion();
	    //Log.v(TAG, "Saving regId on app version " + appVersion);
	    SharedPreferences.Editor editor = mSharedPreferences.edit();
	    editor.putString(PROPERTY_REG_ID, regId);
	    editor.putInt(PROPERTY_APP_VERSION, appVersion);
	    long expirationTime = System.currentTimeMillis() + REGISTRATION_EXPIRY_TIME_MS;

	    //Log.v(TAG, "Setting registration expiry time to " + new Timestamp(expirationTime));
	    editor.putLong(PROPERTY_ON_SERVER_EXPIRATION_TIME, expirationTime);
	    editor.commit();
	}	
	

	private int getAppVersion() {
	    try {
	        PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
	        return packageInfo.versionCode;
	    } catch (NameNotFoundException e) {
	     	        throw new RuntimeException("Could not get package name: " + e);
	    }
	}
	

	private boolean isRegistrationExpired() {
	    // checks if the information is not stale
	    long expirationTime = mSharedPreferences.getLong(PROPERTY_ON_SERVER_EXPIRATION_TIME, -1);
	    return System.currentTimeMillis() > expirationTime;
	}	
	

	private void registerBackground() {
	    new AsyncTask<Void, Void, Boolean>() {
	        @Override
	        protected Boolean doInBackground(Void... params) {
	            long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
	            for (int i = 1; i <= MAX_ATTEMPTS; i++) {
	            	Log.d(TAG, "Attempt #" + i + " to register");
		            try {
		                if (mCloudMessaging == null) {
		                    mCloudMessaging = GoogleCloudMessaging.getInstance(mContext);
		                }
		                String registrationID = mCloudMessaging.register(Common.getSenderId());
		                ServerUtilities.register(Common.getPreferredEmail(), registrationID);
		                setRegistrationId(registrationID);
		                return Boolean.TRUE;
		                
		            } catch (IOException ex) {
		                Log.e(TAG, "Failed to register on attempt " + i + ":" + ex);
		                if (i == MAX_ATTEMPTS) {
		                    break;
		                }
		                try {
		                    Thread.sleep(backoff);
		                } catch (InterruptedException e1) {
		                    Thread.currentThread().interrupt();
		                }
		                backoff *= 2;		                
		            }
	            }
	            return Boolean.FALSE;
	        }

	        @Override
	        protected void onPostExecute(Boolean status) {
	        	broadcastStatus(status);
	        }
	    }.execute(null, null, null);
	}
	
	private void broadcastStatus(boolean status) {
    	Intent intent = new Intent(Common.ACTION_REGISTER);
        intent.putExtra(Common.EXTRA_STATUS, status ? Common.STATUS_SUCCESS : Common.STATUS_FAILED);
        mContext.sendBroadcast(intent);
	}
	
	public void cleanup() {
		mCloudMessaging.close();
		mContext = null;
		mSharedPreferences = null;
		mCloudMessaging = null;
	}	
	
}

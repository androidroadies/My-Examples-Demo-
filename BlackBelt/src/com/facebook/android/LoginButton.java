/* created by
 * Ramesh turi 
   zaptech solution ahmedabad
*/
package com.facebook.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.SessionEvents.AuthListener;
import com.facebook.android.SessionEvents.LogoutListener;


public class LoginButton extends TextView {

    private Facebook mFb;
    private Handler mHandler;
    private SessionListener mSessionListener = new SessionListener();
    private String[] mPermissions;
    private Activity mActivity;
    private int mActivityCode;

    public LoginButton(Context context) {
        super(context);
    }

    public LoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LoginButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void init(final Activity activity, final int activityCode, final Facebook fb) {
        init(activity, activityCode, fb, new String[] {});
    }

    public void init(final Activity activity, final int activityCode, final Facebook fb,final String[] permissions) 
    {
        mActivity = activity;
        mActivityCode = activityCode;
        mFb = fb;
        mPermissions = permissions;
        mHandler = new Handler();

        //setBackgroundColor(Color.TRANSPARENT);
        
//        if(fb.isSessionValid())
//        {
//        	setText(R.string.facebook_logout);
//        }
//        else
//        {
//        	setText(R.string.facebook);
//        }
       
        //BackgroundResource(fb.isSessionValid() ? R.drawable.logout_button : R.drawable.login_button);
        drawableStateChanged();

        SessionEvents.addAuthListener(mSessionListener);
        SessionEvents.addLogoutListener(mSessionListener);
        setOnClickListener(new ButtonOnClickListener());
    }

    private final class ButtonOnClickListener implements OnClickListener {
        /*
         * Source Tag: login_tag
         */
        @Override
        public void onClick(View arg0) {
            if (mFb.isSessionValid()) {
                SessionEvents.onLogoutBegin();
                AsyncFacebookRunner asyncRunner = new AsyncFacebookRunner(mFb);
                asyncRunner.logout(getContext(), new LogoutRequestListener());
            } else {
                mFb.authorize(mActivity, mPermissions, mActivityCode, new LoginDialogListener());
            }
        }
    }

    private final class LoginDialogListener implements DialogListener {
        @Override
        public void onComplete(Bundle values) {
            SessionEvents.onLoginSuccess();
        }

        @Override
        public void onFacebookError(FacebookError error) {
            SessionEvents.onLoginError(error.getMessage());
        }

        @Override
        public void onError(DialogError error) {
            SessionEvents.onLoginError(error.getMessage());
        }

        @Override
        public void onCancel() {
            SessionEvents.onLoginError("Action Canceled");
        }
    }

    private class LogoutRequestListener extends BaseRequestListener {
        public void onComplete(String response, final Object state) {
            /*
             * callback should be run in the original thread, not the background
             * thread
             */
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    SessionEvents.onLogoutFinish();
                }
            });
        }

		@Override
		public void onComplete(String response) {
			// TODO Auto-generated method stub
			
		}
    }

    private class SessionListener implements AuthListener, LogoutListener {

        @Override
        public void onAuthSucceed() {
            //setBackgroundResource(R.drawable.logout_button);
            //setText(R.string.facebook_logout);
        	SessionStore.save(mFb, getContext());
        }

        @Override
        public void onAuthFail(String error) {
        }

        @Override
        public void onLogoutBegin() {
        }

        @Override
        public void onLogoutFinish() {
            SessionStore.clear(getContext());
            //setText(R.string.facebook);
            //setBackgroundResource(R.drawable.login_button);
        }
    }

}

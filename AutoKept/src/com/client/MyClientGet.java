package com.client;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * This is used for get data from API.
 *
 * @author Mayank
 * @since 1.4
 */
public class MyClientGet extends AsyncTask<String, String, String> {

    private ProgressDialog dialog;
    private String message;
    private Context context;
    private OnGetCallComplete ongetcallcomplete;

    public MyClientGet(Context ctx, String progressMessage, OnGetCallComplete onGetCallComplete) {
        message = progressMessage;
        this.context = ctx;
        this.ongetcallcomplete = onGetCallComplete;
        if (!(message.equals(""))) {
            this.dialog = new ProgressDialog(this.context);
            this.dialog.setMessage(progressMessage);
        }
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        if (!(message.equals(""))) {
            dialog.setCancelable(false);
            //dialog.show();
        }
    }

    @Override
    protected String doInBackground(String... params) {
        // TODO Auto-generated method stub
        String result = null;
        if (!isCancelled()) {
            String serverurl = params[0];
            result = Utils.NetworkOperation(serverurl);
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if (!(message.equals(""))) {
            if (dialog != null) {
                dialog.hide();
                dialog.dismiss();
            }
        }
        ongetcallcomplete.response(result);
    }

    protected void onProgressUpdate(String... progress) {
    }

    public interface OnGetCallComplete {
        public void response(String result);
    }
}

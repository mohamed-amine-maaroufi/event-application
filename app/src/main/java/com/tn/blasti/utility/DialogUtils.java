package com.tn.blasti.utility;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.tn.blasti.R;

/**
 * Created by amine 15/12/2018.
 */
public class DialogUtils {

    /**
     * Use this method if you want to show (YES/NO)/(OK/CANCEL) prompt dialog
     *
     * @param activity
     * @param title
     * @param message
     * @param positiveButtonText
     * @param negativeButtonText
     * @param dialogActionListener
     */
    public static void showDialogPrompt(Activity activity, String title, String message, String positiveButtonText, String negativeButtonText, boolean isCancellable, final DialogActionListener dialogActionListener) {
        showDialog(activity, title, message, positiveButtonText, negativeButtonText, isCancellable, dialogActionListener);
    }

    /**
     * Use this method if you want to show any message in dialog, message dialog is automatically cancellable
     *
     * @param activity
     * @param title
     * @param message
     * @param negativeButtonText
     */
    public static void showMessageDialog(Activity activity, String title, String message, String negativeButtonText) {
        showDialog(activity, title, message, null, negativeButtonText, true, null);
    }

    /**
     * Use this method if you want to show a message dialog with user action callback, you can do any action on user event
     *
     * @param activity
     * @param title
     * @param message
     * @param positiveButtonText
     * @param dialogActionListener
     */
    public static void showActionDialog(Activity activity, String title, String message, String positiveButtonText, DialogActionListener dialogActionListener) {
        showDialog(activity, title, message, positiveButtonText, null, false, dialogActionListener);
    }

    /**
     * Base dialog builder method, not accessable publicly
     *
     * @param activity
     * @param title
     * @param message
     * @param positiveButtonText
     * @param negativeButtonText
     * @param isCancellable
     * @param dialogActionListener
     */
    private static void showDialog(Activity activity, String title, String message, String positiveButtonText, String negativeButtonText, boolean isCancellable, final DialogActionListener dialogActionListener) {
        if (activity != null) {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity, R.style.DialogTheme);
            if (title != null) {
                alertDialogBuilder.setTitle(title);
            }
            alertDialogBuilder.setMessage(message);
            alertDialogBuilder.setCancelable(isCancellable);

            if (dialogActionListener != null) {
                alertDialogBuilder.setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        dialogActionListener.onPositiveClick();
                    }
                });
            }

            if (dialogActionListener != null) {
                alertDialogBuilder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
            }

            alertDialogBuilder.create().show();
        }
    }


    /**
     * Call ths method if you want to show progress dialog, preserve returned ProgressDialog instance to close progress dialog
     *
     * @param activity
     * @param message
     * @param isCancellable
     * @return ProgressDialog
     */
    public static ProgressDialog showProgressDialog(Activity activity, String message, boolean isCancellable) {
        ProgressDialog progressDialog = new ProgressDialog(activity, R.style.DialogTheme);
        progressDialog.setCancelable(isCancellable);
        if (message != null) {
            progressDialog.setMessage(message);
        }
        progressDialog.show();
        return progressDialog;
    }

    /**
     * Use this method to close previewed progress dialog
     *
     * @param progressDialog
     */
    public static void dismissProgressDialog(ProgressDialog progressDialog) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public interface DialogActionListener {
        public void onPositiveClick();
    }

}

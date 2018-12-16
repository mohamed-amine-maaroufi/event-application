package com.tn.blasti.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tn.blasti.R;

import com.tn.blasti.api.http.ApiUtils;
import com.tn.blasti.api.models.posts.post.AuthorAvaterUrl;
import com.tn.blasti.api.models.posts.post.CommentsAndReplies;
import com.tn.blasti.api.models.posts.post.Content;
import com.tn.blasti.data.constant.AppConstant;
import com.tn.blasti.data.preference.AppPreference;
import com.tn.blasti.data.preference.PrefKey;
import com.tn.blasti.notification.MyFirebaseMessagingService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by amine 15/12/2018.
 */
public class WriteACommentFragment extends DialogFragment {

    private int clickedPostId;
    private int commentId;
    private EditText edtAuthorName, edtAuthorEmail, edtAuthorComment;

    private Activity mActivity;
    private boolean commentSuccessful = false;
    private String authorName;
    private String authorEmail;
    private String authorComment;

    public static interface OnCompleteListener {
        public abstract void onComplete(Boolean isCommentSuccessful, CommentsAndReplies commentsAndReplies);
    }

    private OnCompleteListener mListener;

    public static WriteACommentFragment newInstance(int clickedPostId, int commentId) {
        Bundle args = new Bundle();
        args.putInt(AppConstant.ARG_CLICKED_POST_ID, clickedPostId);
        args.putInt(AppConstant.ARG_COMMENT_ID, commentId);
        WriteACommentFragment fragment = new WriteACommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = activity;

        try {
            this.mListener = (OnCompleteListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString());
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View rootView = LayoutInflater.from(mActivity).inflate(R.layout.fragment_dialog_comment, null);

        initVar();
        initView(rootView);
        initFunctionality();

        return new AlertDialog.Builder(mActivity)
                .setView(rootView)
                .setTitle(R.string.write_a_comment)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        authorName = edtAuthorName.getText().toString().trim();
                        authorEmail = edtAuthorEmail.getText().toString().trim();
                        authorComment = edtAuthorComment.getText().toString().trim();

                        AppPreference.getInstance(mActivity).setString(PrefKey.KEY_EMAIL, authorEmail);
                        AppPreference.getInstance(mActivity).setString(PrefKey.KEY_NAME, authorName);

                        if (commentId == AppConstant.THIS_IS_COMMENT) {
                            sendComment(authorName, authorEmail, authorComment);

                        } else {
                            sendReply(authorName, authorEmail, authorComment);
                        }
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (dialogInterface != null) {
                            dialogInterface.dismiss();
                        }

                    }
                })
                .create();
    }

    public void initVar() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            clickedPostId = getArguments().getInt(AppConstant.ARG_CLICKED_POST_ID);
            commentId = getArguments().getInt(AppConstant.ARG_COMMENT_ID);
        }
    }

    public void initView(View rootView) {
        edtAuthorName = (EditText) rootView.findViewById(R.id.edt_author_name);
        edtAuthorEmail = (EditText) rootView.findViewById(R.id.edt_author_email);
        edtAuthorComment = (EditText) rootView.findViewById(R.id.edt_author_comment);
    }

    public void initFunctionality() {
        edtAuthorName.setText(AppPreference.getInstance(mActivity).getString(PrefKey.KEY_NAME));
        edtAuthorEmail.setText(AppPreference.getInstance(mActivity).getString(PrefKey.KEY_EMAIL));
    }

    private void sendReply(String authName, String authEmail, String authComment) {

        ApiUtils.getApiInterface().postAReply(authName, authEmail, authComment, clickedPostId, commentId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    commentSuccessful = true;
                    showReturnMessage(mActivity.getString(R.string.successful_reply));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                showReturnMessage(mActivity.getString(R.string.error_message));
            }
        });

    }

    private void sendComment(String authName, String authEmail, String authComment) {

        ApiUtils.getApiInterface().postAComment(authName, authEmail, authComment, clickedPostId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    commentSuccessful = true;
                    showReturnMessage(mActivity.getString(R.string.successful_comment));
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        showReturnMessage(jObjError.getString(AppConstant.COMMENT_MESSAGE));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
                showReturnMessage(mActivity.getString(R.string.error_message));
            }
        });
    }

    public void showReturnMessage(String messageText) {
        Toast.makeText(mActivity, messageText, Toast.LENGTH_SHORT).show();

        if (authorName.isEmpty()) {
            authorName = mActivity.getString(R.string.author_name);
        }

        CommentsAndReplies commentsAndReplies = new CommentsAndReplies(
                AppConstant.COMMENT_ID,
                AppConstant.COMMENT_PARENT_ID,
                authorName,
                new SimpleDateFormat(AppConstant.COMMENT_DATE_FORMAT).format(Calendar.getInstance().getTime()),
                new Content(authorComment),
                new AuthorAvaterUrl(null));
        mListener.onComplete(commentSuccessful, commentsAndReplies);
    }

}
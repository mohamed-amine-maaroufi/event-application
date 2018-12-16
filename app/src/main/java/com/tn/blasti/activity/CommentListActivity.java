package com.tn.blasti.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.tn.blasti.R;
import com.tn.blasti.adapters.CommentsAdapter;
import com.tn.blasti.api.models.posts.post.CommentsAndReplies;
import com.tn.blasti.data.constant.AppConstant;
import com.tn.blasti.fragment.WriteACommentFragment;
import com.tn.blasti.listeners.ListItemClickListener;
import com.tn.blasti.utility.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amine 15/12/2018.
 */

public class CommentListActivity extends BaseActivity implements WriteACommentFragment.OnCompleteListener {

    private Activity mActivity;
    private Context mContext;
    private Toolbar mToolbar;
    private FloatingActionButton fabWriteAComment;
    int clickedPostId;

    private RecyclerView rvComments;
    private CommentsAdapter commentsAdapter = null;
    private List<CommentsAndReplies> commentList;
    private List<CommentsAndReplies> zeroParentComments;
    private List<CommentsAndReplies> zeroParentCommentsReplica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVar();
        initView();
        initFunctionality();
        initListener();
    }

    private void initVar() {
        mActivity = CommentListActivity.this;
        mContext = mActivity.getApplicationContext();

        commentList = new ArrayList<>();
        zeroParentComments = new ArrayList<>();
        zeroParentCommentsReplica = new ArrayList<>();

        Intent intent = getIntent();
        if (intent != null) {
            commentList = getIntent().getParcelableArrayListExtra(AppConstant.BUNDLE_KEY_ALL_COMMENT);
            zeroParentComments = getIntent().getParcelableArrayListExtra(AppConstant.BUNDLE_KEY_ALL_ZERO_PARENT_COMMENT);
            zeroParentCommentsReplica.addAll(zeroParentComments);
            clickedPostId = getIntent().getIntExtra(AppConstant.BUNDLE_KEY_POST_ID, 0);
        }

    }

    private void initView() {
        setContentView(R.layout.activity_comment_list);

        fabWriteAComment = (FloatingActionButton) findViewById(R.id.fab_add_comment);

        rvComments = (RecyclerView) findViewById(R.id.rvComments);
        rvComments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        initToolbar();
        setToolbarTitle(getString(R.string.comment_list));
        enableBackButton();

    }

    private void initFunctionality() {

        commentsAdapter = new CommentsAdapter(getApplicationContext(), (ArrayList) commentList, (ArrayList) zeroParentComments);
        rvComments.setAdapter(commentsAdapter);

    }

    public void initListener() {

        fabWriteAComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getSupportFragmentManager();
                WriteACommentFragment dialog = WriteACommentFragment.newInstance(clickedPostId, -1);
                dialog.show(manager, AppConstant.BUNDLE_KEY_DIALOG_FRAGMENT);
            }
        });

        commentsAdapter.setItemClickListener(new ListItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                int id = view.getId();
                CommentsAndReplies clickedComment = zeroParentComments.get(position);
                boolean isFound = false;
                for (CommentsAndReplies commentsAndReplies : zeroParentCommentsReplica) {
                    if (clickedComment.getID() == commentsAndReplies.getID()) {
                        isFound = true;
                        break;
                    }
                }
                if (isFound) {
                    switch (id) {
                        case R.id.list_item:
                            ActivityUtils.getInstance().invokeCommentDetails(mActivity, CommentDetailsActivity.class, (ArrayList) commentList, clickedPostId, clickedComment, false, false);
                            break;
                        case R.id.reply_text:
                            ActivityUtils.getInstance().invokeCommentDetails(mActivity, CommentDetailsActivity.class, (ArrayList) commentList, clickedPostId, clickedComment, true, false);

                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onComplete(Boolean isCommentSuccessful, CommentsAndReplies commentsAndReplies) {
        if (isCommentSuccessful) {
            zeroParentComments.add(commentsAndReplies);
            commentsAdapter.notifyDataSetChanged();
        }
        setCommentSuccessResult(isCommentSuccessful);
    }

    private void setCommentSuccessResult(boolean isCommentSuccessful) {
        Intent data = new Intent();
        data.putExtra(AppConstant.BUNDLE_KEY_COMMENT_STATUS, isCommentSuccessful);
        setResult(RESULT_OK, data);
    }

    public static boolean wasCommentSuccessful(Intent result) {
        return result.getBooleanExtra(AppConstant.BUNDLE_KEY_COMMENT_STATUS, false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == AppConstant.REQUEST_CODE_COMMENT) {
            if (data == null) {
                return;
            }
            boolean isCommentSuccessful = CommentDetailsActivity.wasCommentSuccessful(data);
            if (isCommentSuccessful) {
                setCommentSuccessResult(isCommentSuccessful);
            }
        }
    }
}

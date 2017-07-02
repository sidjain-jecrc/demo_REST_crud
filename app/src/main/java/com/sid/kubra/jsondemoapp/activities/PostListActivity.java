package com.sid.kubra.jsondemoapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sid.kubra.jsondemoapp.R;
import com.sid.kubra.jsondemoapp.adapter.PostListAdapter;
import com.sid.kubra.jsondemoapp.model.UserPost;
import com.sid.kubra.jsondemoapp.restclient.JsonPlaceRestClient;

import java.util.Comparator;
import java.util.List;

public class PostListActivity extends AppCompatActivity {

    protected static final String TAG = PostListActivity.class.getSimpleName();
    protected ListView list_user_post;
    protected ArrayAdapter<UserPost> userPostAdapter;
    protected String userId = null;
    static final int ADD_POST_RESULT = 1;
    static final int EDIT_POST_RESULT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_post);

        Intent intent = getIntent();
        userId = intent.getStringExtra("EXTRA_USER_ID");

        list_user_post = (ListView) findViewById(R.id.list_user_post);
        if (list_user_post != null) {
            new RetrieveUserPostListTask().execute(userId);
            registerForContextMenu(list_user_post);
        } else {
            Log.d(TAG, "listview is NULL");
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId() == R.id.list_user_post) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_post_options, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        UserPost post = userPostAdapter.getItem(info.position);

        switch (item.getItemId()) {
            case R.id.action_edit_post:
                Intent editPostIntent = new Intent(PostListActivity.this, EditPostActivity.class);
                editPostIntent.putExtra("EXTRA_POST_OBJECT", post);
                startActivityForResult(editPostIntent, EDIT_POST_RESULT);
                return true;
            case R.id.action_delete_post:
                new DeletePostTask().execute(post.getId());
                return true;
            default:
                return true;
        }
    }

    private class DeletePostTask extends AsyncTask<Long, Void, Boolean> {

        ProgressDialog pd = new ProgressDialog(PostListActivity.this, ProgressDialog.STYLE_SPINNER);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("deleting post..");
            pd.show();
        }

        @Override
        protected Boolean doInBackground(Long... input) {
            long postId = input[0];
            JsonPlaceRestClient client = new JsonPlaceRestClient();
            return client.deletePost(postId);
        }

        protected void onPostExecute(Boolean isPostDeleted) {
            if (pd.isShowing()) {
                pd.dismiss();
            }
            if (isPostDeleted) {
                Toast.makeText(PostListActivity.this, "post deletion successful", Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "post could not be deleted");
                Toast.makeText(PostListActivity.this, "post deletion failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class RetrieveUserPostListTask extends AsyncTask<String, Void, List<UserPost>> {

        ProgressDialog pd = new ProgressDialog(PostListActivity.this, ProgressDialog.STYLE_SPINNER);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("loading posts..");
            pd.show();
        }

        @Override
        protected List<UserPost> doInBackground(String... input) {
            String userId = input[0];
            JsonPlaceRestClient client = new JsonPlaceRestClient();
            List<UserPost> posts = client.requestUserPosts(userId);
            return posts;
        }

        protected void onPostExecute(List<UserPost> posts) {
            if (pd.isShowing()) {
                pd.dismiss();
            }
            if (posts != null && posts.size() > 0) {
                userPostAdapter = new PostListAdapter(PostListActivity.this, posts);
                list_user_post.setAdapter(userPostAdapter);
            } else {
                Log.d(TAG, "no posts found for selected user");
                Toast.makeText(PostListActivity.this, "no posts found for selected user", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_post, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_sort_asc:
                userPostAdapter.sort(new Comparator<UserPost>() {
                    @Override
                    public int compare(UserPost post1, UserPost post2) {
                        return post1.getTitle().compareTo(post2.getTitle());
                    }
                });
                list_user_post.setAdapter(userPostAdapter);
                userPostAdapter.notifyDataSetChanged();
                return true;

            case R.id.action_sort_desc:
                userPostAdapter.sort(new Comparator<UserPost>() {
                    @Override
                    public int compare(UserPost post1, UserPost post2) {
                        return post2.getTitle().compareTo(post1.getTitle());
                    }
                });
                list_user_post.setAdapter(userPostAdapter);
                userPostAdapter.notifyDataSetChanged();
                return true;

            case R.id.action_add_post:
                Intent jumpToAddPost = new Intent(PostListActivity.this, AddPostActivity.class);
                jumpToAddPost.putExtra("EXTRA_USER_ID", userId);
                startActivityForResult(jumpToAddPost, ADD_POST_RESULT);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == ADD_POST_RESULT) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                long postId = data.getLongExtra("POST_ID", 0);
                Toast.makeText(PostListActivity.this, "post added with id:" + postId, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == AppCompatActivity.RESULT_CANCELED) {
                Toast.makeText(PostListActivity.this, "add post request failed!!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "add post request failed!!");
            }

        } else if (requestCode == EDIT_POST_RESULT) {
            if (resultCode == AppCompatActivity.RESULT_OK) {
                long postId = data.getLongExtra("POST_ID", 0);
                Toast.makeText(PostListActivity.this, "post edited with id:" + postId, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == AppCompatActivity.RESULT_CANCELED) {
                Toast.makeText(PostListActivity.this, "edit post request failed!!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "edit post request failed!!");
            }

        }
    }
}

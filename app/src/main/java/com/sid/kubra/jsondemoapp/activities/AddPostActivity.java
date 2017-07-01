package com.sid.kubra.jsondemoapp.activities;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sid.kubra.jsondemoapp.R;
import com.sid.kubra.jsondemoapp.model.UserPost;
import com.sid.kubra.jsondemoapp.restclient.JsonPlaceRestClient;

public class AddPostActivity extends AppCompatActivity {

    protected static final String TAG = AddPostActivity.class.getSimpleName();

    EditText txtTitle = null;
    EditText txtBody = null;
    Button btnSubmit = null;
    String userId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        userId = getIntent().getStringExtra("EXTRA_USER_ID");
        txtTitle = (EditText) findViewById(R.id.add_post_txt_title);
        txtBody = (EditText) findViewById(R.id.add_post_txt_body);
        btnSubmit = (Button) findViewById(R.id.add_post_btn_submit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String postTitle = txtTitle.getText().toString();
                String postBody = txtBody.getText().toString();
                if (postTitle.equals("") || postBody.equals("")) {
                    Toast.makeText(AddPostActivity.this, "All fields are compulsory", Toast.LENGTH_SHORT).show();
                } else {
                    new AddUserPostTask().execute(userId, postTitle, postBody);
                }
            }
        });
    }

    private class AddUserPostTask extends AsyncTask<String, Void, UserPost> {

        @Override
        protected UserPost doInBackground(String... input) {

            String userId = input[0];
            String title = input[1];
            String body = input[2];

            UserPost post = new UserPost();
            post.setUserId(Long.valueOf(userId));
            post.setTitle(title);
            post.setBody(body);

            JsonPlaceRestClient client = new JsonPlaceRestClient();
            UserPost newPost = client.addPost(post);
            return newPost;
        }

        protected void onPostExecute(UserPost post) {
            if (post != null) {
                long postId = post.getId();
                Toast.makeText(AddPostActivity.this, "Post successfully added with id:" + postId, Toast.LENGTH_SHORT).show();
            } else {
                Log.d(TAG, "add post request failed!!");
                Toast.makeText(AddPostActivity.this, "add post request failed!!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

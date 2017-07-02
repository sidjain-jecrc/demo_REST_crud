package com.sid.kubra.jsondemoapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class EditPostActivity extends AppCompatActivity {

    protected static final String TAG = EditPostActivity.class.getSimpleName();

    EditText titleEditText = null;
    EditText bodyEditText = null;
    Button btnSave = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        titleEditText = (EditText) findViewById(R.id.edit_post_txt_title);
        bodyEditText = (EditText) findViewById(R.id.edit_post_txt_body);
        btnSave = (Button) findViewById(R.id.edit_post_btn_save);

        final UserPost userPost = (UserPost) getIntent().getParcelableExtra("EXTRA_POST_OBJECT");
        if (userPost != null) {
            titleEditText.setText(userPost.getTitle());
            bodyEditText.setText(userPost.getBody());
        } else {
            Log.d(TAG, "could not retrieve post object from intent");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String modifiedTitle = titleEditText.getText().toString();
                String modifiedBody = bodyEditText.getText().toString();

                if (!modifiedTitle.equals(userPost.getTitle()) || !modifiedBody.equals(userPost.getBody())) {
                    UserPost modifiedPost = new UserPost();
                    modifiedPost.setTitle(modifiedTitle);
                    modifiedPost.setBody(modifiedBody);
                    modifiedPost.setId(userPost.getId());
                    modifiedPost.setUserId(userPost.getUserId());
                    new EditPostTask().execute(modifiedPost);
                } else {
                    Toast.makeText(EditPostActivity.this, "nothing modified", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class EditPostTask extends AsyncTask<UserPost, Void, UserPost> {

        ProgressDialog pd = new ProgressDialog(EditPostActivity.this, ProgressDialog.STYLE_SPINNER);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("modifying post..");
            pd.show();
        }

        @Override
        protected UserPost doInBackground(UserPost... posts) {
            UserPost post = posts[0];
            JsonPlaceRestClient client = new JsonPlaceRestClient();
            UserPost modifiedPost = client.updatePost(post);
            return modifiedPost;
        }

        protected void onPostExecute(UserPost post) {

            if (pd.isShowing()) {
                pd.dismiss();
            }

            Intent returnIntent = new Intent();
            if (post != null) {

                long postId = post.getId();
                returnIntent.putExtra("POST_ID", postId);
                setResult(AppCompatActivity.RESULT_OK, returnIntent);
                EditPostActivity.this.finish();

            } else {
                setResult(AppCompatActivity.RESULT_CANCELED, returnIntent);
                EditPostActivity.this.finish();
            }
        }
    }

}

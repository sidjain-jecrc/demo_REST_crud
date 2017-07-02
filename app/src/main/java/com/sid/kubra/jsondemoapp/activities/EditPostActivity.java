package com.sid.kubra.jsondemoapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sid.kubra.jsondemoapp.R;
import com.sid.kubra.jsondemoapp.model.UserPost;

public class EditPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        UserPost userPost = (UserPost) getIntent().getParcelableExtra("EXTRA_POST_OBJECT");
        if (userPost != null) {

        }
    }
}

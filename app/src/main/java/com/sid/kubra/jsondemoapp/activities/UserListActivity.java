package com.sid.kubra.jsondemoapp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.sid.kubra.jsondemoapp.R;
import com.sid.kubra.jsondemoapp.adapter.UserListAdapter;
import com.sid.kubra.jsondemoapp.model.User;
import com.sid.kubra.jsondemoapp.restclient.JsonPlaceRestClient;

import java.util.List;

public class UserListActivity extends AppCompatActivity {

    protected static final String TAG = UserListActivity.class.getSimpleName();
    ListView list_user_info;
    ArrayAdapter<User> userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        list_user_info = (ListView) findViewById(R.id.list_user_info);

        if (list_user_info != null) {
            new RetrieveUserListTask().execute();

            list_user_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    User selectedUser = (User) adapterView.getItemAtPosition(position);
                    Intent jumpToPostActivity = new Intent(UserListActivity.this, PostListActivity.class);
                    String userId = String.valueOf(selectedUser.getId());
                    jumpToPostActivity.putExtra("EXTRA_USER_ID", userId);
                    startActivity(jumpToPostActivity);
                }
            });
        } else {
            Log.d(TAG, "cannot find list view layout");
            Toast.makeText(this, "Cannot find the list view layout", Toast.LENGTH_SHORT).show();
        }

    }

    private class RetrieveUserListTask extends AsyncTask<Void, Void, List<User>> {

        ProgressDialog pd = new ProgressDialog(UserListActivity.this, ProgressDialog.STYLE_SPINNER);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("loading users..");
            pd.show();
        }

        @Override
        protected List<User> doInBackground(Void... voids) {
            JsonPlaceRestClient client = new JsonPlaceRestClient();
            List<User> users = client.requestUsersList();
            return users;
        }

        protected void onPostExecute(List<User> users) {
            if (pd.isShowing()) {
                pd.dismiss();
            }
            if (users != null && users.size() > 0) {
                userAdapter = new UserListAdapter(UserListActivity.this, users);
                list_user_info.setAdapter(userAdapter);
            } else {
                Log.d(TAG, "something went wrong in retrieving users!");
                Toast.makeText(UserListActivity.this, "no users found!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

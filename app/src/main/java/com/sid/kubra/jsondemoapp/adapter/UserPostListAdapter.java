package com.sid.kubra.jsondemoapp.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sid.kubra.jsondemoapp.R;
import com.sid.kubra.jsondemoapp.model.User;
import com.sid.kubra.jsondemoapp.model.UserAddress;
import com.sid.kubra.jsondemoapp.model.UserPost;

import java.util.Comparator;
import java.util.List;

/**
 * Created by Siddharth on 6/24/2017.
 */

public class UserPostListAdapter extends ArrayAdapter<UserPost> {

    private final Context context;
    private final List<UserPost> posts;

    public UserPostListAdapter(@NonNull Context context, @NonNull List<UserPost> posts) {
        super(context, R.layout.list_post, posts);
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_post, parent, false);

        TextView postTitle = (TextView) rowView.findViewById(R.id.list_post_title);
        TextView postBody = (TextView) rowView.findViewById(R.id.list_post_body);

        UserPost post = getItem(position);
        if (post != null) {
            postTitle.setText(post.getTitle());
            postBody.setText(post.getBody());
        }
        return rowView;
    }

}

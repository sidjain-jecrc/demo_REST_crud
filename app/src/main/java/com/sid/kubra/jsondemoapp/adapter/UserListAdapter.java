package com.sid.kubra.jsondemoapp.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sid.kubra.jsondemoapp.R;
import com.sid.kubra.jsondemoapp.model.User;
import com.sid.kubra.jsondemoapp.model.UserAddress;

import java.util.List;

/**
 * Created by Siddharth on 6/24/2017.
 */

public class UserListAdapter extends ArrayAdapter<User> {

    private final Context context;
    private final List<User> users;

    public UserListAdapter(@NonNull Context context, @NonNull List<User> users) {
        super(context, R.layout.list_user, users);
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_user, parent, false);

        TextView username = (TextView) rowView.findViewById(R.id.list_user_username);
        TextView address = (TextView) rowView.findViewById(R.id.list_user_address);

        User user = getItem(position);
        if (user != null) {
            username.setText(user.getUsername());
            UserAddress userAddress = user.getAddress();
            if (userAddress != null) {
                String concatenatedAddress = userAddress.getStreet() + ", " + userAddress.getSuite() + ", " + userAddress.getCity() + ", " + userAddress.getZipCode();
                address.setText(concatenatedAddress);
            } else {
                address.setText("No address found for this user");
            }
        }
        return rowView;
    }

}

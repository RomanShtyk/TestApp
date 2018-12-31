package com.example.rdsh.testapp.Activities.Main.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rdsh.testapp.Activities.Main.MainActivity;
import com.example.rdsh.testapp.Data.User;
import com.example.rdsh.testapp.R;

import java.util.Date;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    private final LayoutInflater lInflater;
    private final List<User> users;

    public ListAdapter(Context context, List<User> users) {
        this.users = users;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.layout_chat_list_item, parent, false);
        }
        User user = getUser(position);

        ((TextView) view.findViewById(R.id.userName)).setText(user.getName());
        if (user.getChatHistory().get((user.getChatHistory().size() - 1)).getIsFromMe() == 1)
            ((TextView) view.findViewById(R.id.lastMessage)).setText("me:" + user.getChatHistory()
                    .get((user.getChatHistory().size() - 1)).getMessage());
        else
            ((TextView) view.findViewById(R.id.lastMessage)).setText(user.getChatHistory()
                    .get((user.getChatHistory().size() - 1)).getMessage());

        Date date = new Date(user.getChatHistory()
                .get((user.getChatHistory().size() - 1)).getTime());

        ((TextView) view.findViewById(R.id.time)).setText(MainActivity.formatForDateNow.format(date));
        ImageView imageView = view.findViewById(R.id.image);
        imageView.setImageResource(user.getImage());
        return view;
    }

    private User getUser(int position) {
        return ((User) getItem(position));
    }
}

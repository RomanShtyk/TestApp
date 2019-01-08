package com.example.rdsh.testapp.Activities.main.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rdsh.testapp.Activities.main.MainActivity;
import com.example.rdsh.testapp.data.User;
import com.example.rdsh.testapp.R;

import org.w3c.dom.Text;

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

    class MyViewHolder {
        TextView userName;
        TextView lastMessage;
        TextView time;
        ImageView image;

        MyViewHolder(View view) {
            userName = view.findViewById(R.id.userName);
            lastMessage = view.findViewById(R.id.lastMessage);
            time = view.findViewById(R.id.time);
            image = view.findViewById(R.id.image);
        }
    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        MyViewHolder holder = null;
        if (view == null) {
            view = lInflater.inflate(R.layout.layout_chat_list_item, parent, false);
            holder = new MyViewHolder(view);
            view.setTag(holder);
            Log.d("mLog", "Creating new view");
        } else {
            holder = (MyViewHolder) view.getTag();
            Log.d("mLog", "Recycling stuff");
        }
        User user = getUser(position);

        holder.userName.setText(user.getName());
        if (user.getChatHistory().get((user.getChatHistory().size() - 1)).getIsFromMe() == 1)
            holder.lastMessage.setText("me:" + user.getChatHistory()
                    .get((user.getChatHistory().size() - 1)).getMessage());
        else
            holder.lastMessage.setText(user.getChatHistory()
                    .get((user.getChatHistory().size() - 1)).getMessage());

        long dateNow = new Date().getTime();

        Date date = new Date(user.getChatHistory()
                .get((user.getChatHistory().size() - 1)).getTime());
        int dayMillis = 86400000;
        if (dateNow / dayMillis - user.getChatHistory()
                .get((user.getChatHistory().size() - 1)).getTime() / dayMillis >= 1) {
            holder.time.setText(MainActivity.formatForDateNow.format(date));
        } else if (dateNow / dayMillis - user.getChatHistory()
                .get((user.getChatHistory().size() - 1)).getTime() / dayMillis == 0) {
            holder.time.setText(MainActivity.formatForTimeNow.format(date));
        }

        holder.image.setImageResource(user.getImage());
        return view;
    }

    private User getUser(int position) {
        return ((User) getItem(position));
    }
}

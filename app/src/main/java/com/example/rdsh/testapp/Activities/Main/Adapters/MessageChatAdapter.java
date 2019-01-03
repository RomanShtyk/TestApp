package com.example.rdsh.testapp.Activities.Main.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rdsh.testapp.Activities.Main.MainActivity;
import com.example.rdsh.testapp.Data.Message;
import com.example.rdsh.testapp.R;

import java.util.Date;
import java.util.List;

public class MessageChatAdapter extends BaseAdapter {

    static final int dayMillis = 86400000;
    private final LayoutInflater lInflater;
    private List<Message> messages;

    public MessageChatAdapter(Context context, List<Message> messages) {
        this.messages = messages;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateList(List<Message> messages) {
        this.messages.clear();
        this.messages = messages;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Message getItem(int position) {
        return messages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        Date date = new Date(messages.get(position).getTime());

        if (messages.get(position).getIsFromMe() == 1) {
            view = lInflater.inflate(R.layout.layout_chat_out, parent, false);
            ((TextView) view.findViewById(R.id.message)).setText(messages.get(position).getMessage());
            ((TextView) view.findViewById(R.id.time)).setText(MainActivity.formatForTimeNow.
                    format(date));
            //date comparing for chat division by date
            if (position == 0) {
                ((TextView) view.findViewById(R.id.dayOut)).setText(MainActivity.formatForDateNow.
                        format(date));
            } else {
                long date1 = messages.get(position).getTime();
                long date2 = messages.get(position - 1).getTime();

                if (date1 / dayMillis > date2 / dayMillis) {
                    ((TextView) view.findViewById(R.id.dayOut)).setText(MainActivity.formatForDateNow.
                            format(date));
                } else {
                    view.findViewById(R.id.dayOut).setVisibility(View.GONE);
                }
            }

        } else if (messages.get(position).getIsFromMe() == 0) {
            messages.get(position).setIsReaded(1);
            view = lInflater.inflate(R.layout.layout_chat_in, parent, false);
            ((TextView) view.findViewById(R.id.message)).setText(messages.get(position).getMessage());
            ((TextView) view.findViewById(R.id.time)).setText(MainActivity.formatForTimeNow.
                    format(date));
            ImageView imageView = view.findViewById(R.id.imageChat);
            CardView cardView = view.findViewById(R.id.cardChat);
            //date comparing for chat division by date
            if (position == 0) {
                ((TextView) view.findViewById(R.id.day)).setText(MainActivity.formatForDateNow.
                        format(date));
                //set image just for first message
                imageView.setImageResource(MainActivity.myAppDatabase.daoUser()
                        .getUserById(messages.get(0).getUser_id()).getImage());

            } else {
                long date1 = messages.get(position).getTime();
                long date2 = messages.get(position - 1).getTime();
                int previousIsFromMe = messages.get(position - 1).getIsFromMe();
                if (previousIsFromMe == 0) {
                    cardView.setVisibility(View.INVISIBLE);
                } else if (previousIsFromMe == 1) {
                    imageView.setImageResource(MainActivity.myAppDatabase.daoUser().
                            getUserById(messages.get(0).getUser_id()).getImage());
                }
                if (date1 / dayMillis > date2 / dayMillis) {
                    ((TextView) view.findViewById(R.id.day)).setText(MainActivity.formatForDateNow.
                            format(date));
                } else {
                    view.findViewById(R.id.day).setVisibility(View.GONE);
                }
            }
        }
        return view;
    }

    private Message getMessage(int position) {
        return (getItem(position));
    }
}
package com.example.rdsh.testapp.activities.main.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rdsh.testapp.activities.main.MainActivity;
import com.example.rdsh.testapp.R;
import com.example.rdsh.testapp.data.Message;

import java.util.Date;
import java.util.List;

import static com.example.rdsh.testapp.Values.FALSE;
import static com.example.rdsh.testapp.Values.TRUE;
import static com.example.rdsh.testapp.Values.dayMillis;
import static com.example.rdsh.testapp.Values.formatForDateNow;
import static com.example.rdsh.testapp.Values.formatForTimeNow;

public class MessageChatAdapter extends RecyclerView.Adapter<MessageChatAdapter.MyViewHolder> {

    private static List<Message> messages;
    private static LayoutInflater lInflater;

    public MessageChatAdapter(Context context, List<Message> messages) {
        MessageChatAdapter.messages = messages;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void updateList(List<Message> messages) {
        MessageChatAdapter.messages.clear();
        MessageChatAdapter.messages = messages;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getIsFromMe();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        switch (i) {
            case 1:
                view = lInflater.inflate(R.layout.layout_chat_out, viewGroup, false);
                break;
            case 0:
                view = lInflater.inflate(R.layout.layout_chat_in, viewGroup, false);
                break;
        }
        return new MyViewHolder(view, i);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Date date = new Date(messages.get(position).getTime());

        if (messages.get(position).getIsFromMe() == TRUE) {
            holder.tvMessage.setText(messages.get(position).getMessage());
            holder.tvTime.setText(formatForTimeNow.
                    format(date));
            //date comparing for chat division by date
            if (position == 0) {
                holder.tvDay.setText(formatForDateNow.format(date));
            } else {
                long date1 = messages.get(position).getTime();
                long date2 = messages.get(position - 1).getTime();

                if (date1 / dayMillis > date2 / dayMillis) {
                    holder.tvDay.setText(formatForDateNow.format(date));
                } else {
                    holder.tvDay.setVisibility(View.GONE);
                }
            }
        } else if (messages.get(position).getIsFromMe() == FALSE) {
            messages.get(position).setIsReaded(1);
            holder.tvMessage.setText(messages.get(position).getMessage());
            holder.tvTime.setText(formatForTimeNow.
                    format(date));
            //date comparing for chat division by date
            if (position == 0) {
                holder.tvDay.setText(formatForDateNow.
                        format(date));
                //set image just for first message
                holder.image.setImageResource(MainActivity.myAppDatabase.daoUser()
                        .getUserById(messages.get(0).getUser_id()).getImage());
            } else {
                long date1 = messages.get(position).getTime();
                long date2 = messages.get(position - 1).getTime();
                if (messages.get(position - 1).getIsFromMe() == FALSE) {
                    holder.card.setVisibility(View.INVISIBLE);
                } else if (messages.get(position - 1).getIsFromMe() == TRUE) {
                    holder.image.setImageResource(MainActivity.myAppDatabase.daoUser().
                            getUserById(messages.get(0).getUser_id()).getImage());
                }
                if (date1 / dayMillis > date2 / dayMillis) {
                    holder.tvDay.setText(formatForDateNow.
                            format(date));
                } else {
                    holder.tvDay.setVisibility(View.GONE);
                }
            }

        }
        holder.setIsRecyclable(false);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public static int getCount() {
        return messages.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage;
        TextView tvTime;
        TextView tvDay;
        ImageView image;
        CardView card;

        MyViewHolder(View view, int i) {
            super(view);
            switch (i) {
                case 1:
                    tvMessage = view.findViewById(R.id.message);
                    tvTime = view.findViewById(R.id.time);
                    tvDay = view.findViewById(R.id.day);
                    break;
                case 0:
                    tvMessage = view.findViewById(R.id.message);
                    tvTime = view.findViewById(R.id.time);
                    tvDay = view.findViewById(R.id.day);
                    image = view.findViewById(R.id.imageChat);
                    card = view.findViewById(R.id.cardChat);
                    break;
            }
        }
    }

//    @SuppressLint("SimpleDateFormat")
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View v = convertView;
//        MyViewHolder holder;
//
//        if (position == messages.size() - 1) {
//            v = null;
//        } else if (messages.get(position).getIsFromMe() != messages.get(lastPosition).getIsFromMe()) {
//            v = null;
//        }
//
//        lastPosition = position;
//
//        Date date = new Date(messages.get(position).getTime());
//
//        if (messages.get(position).getIsFromMe() == MainActivity.TRUE) {
//            if (v == null) {
//                v = lInflater.inflate(R.layout.layout_chat_out, parent, false);
//                holder = new MyViewHolder(v, messages.get(position).getIsFromMe());
//                v.setTag(holder);
//            } else {
//                holder = (MyViewHolder) v.getTag();
//            }
//            holder.tvMessage.setText(messages.get(position).getMessage());
//            holder.tvTime.setText(MainActivity.formatForTimeNow.
//                    format(date));
//            //date comparing for chat division by date
//            if (position == 0) {
//                holder.tvDay.setText(MainActivity.formatForDateNow.format(date));
//            } else {
//                long date1 = messages.get(position).getTime();
//                long date2 = messages.get(position - 1).getTime();
//
//                if (date1 / dayMillis > date2 / dayMillis) {
//                    holder.tvDay.setText(MainActivity.formatForDateNow.format(date));
//                } else {
//                    holder.tvDay.setVisibility(View.GONE);
//                }
//            }
//        } else if (messages.get(position).getIsFromMe() == MainActivity.FALSE) {
//            if (v == null) {
//                v = lInflater.inflate(R.layout.layout_chat_in, parent, false);
//                holder = new MyViewHolder(v, messages.get(position).getIsFromMe());
//                v.setTag(holder);
//            } else {
//                holder = (MyViewHolder) v.getTag();
//            }
//            messages.get(position).setIsReaded(1);
//            holder.tvMessage.setText(messages.get(position).getMessage());
//            holder.tvTime.setText(MainActivity.formatForTimeNow.
//                    format(date));
//            //date comparing for chat division by date
//            if (position == 0) {
//                holder.tvDay.setText(MainActivity.formatForDateNow.
//                        format(date));
//                //set image just for first message
//                holder.image.setImageResource(MainActivity.myAppDatabase.daoUser()
//                        .getUserById(messages.get(0).getUser_id()).getImage());
//            } else {
//                long date1 = messages.get(position).getTime();
//                long date2 = messages.get(position - 1).getTime();
//                if (messages.get(position - 1).getIsFromMe() == MainActivity.FALSE) {
//                    holder.card.setVisibility(View.INVISIBLE);
//                } else if (messages.get(position - 1).getIsFromMe() == MainActivity.TRUE) {
//                    holder.image.setImageResource(MainActivity.myAppDatabase.daoUser().
//                            getUserById(messages.get(0).getUser_id()).getImage());
//                }
//                if (date1 / dayMillis > date2 / dayMillis) {
//                    holder.tvDay.setText(MainActivity.formatForDateNow.
//                            format(date));
//                } else {
//                    holder.tvDay.setVisibility(View.GONE);
//                }
//            }
//        }
//        convertView = v;
//        return convertView;
//    }

//    private Message getMessage(int position) {
//        return (getItem(position));
//    }
}
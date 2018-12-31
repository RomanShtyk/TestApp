package com.example.rdsh.testapp.Activities.Main.Fragments;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.rdsh.testapp.Activities.Main.MainActivity;
import com.example.rdsh.testapp.Activities.Main.Adapters.MessageChatAdapter;
import com.example.rdsh.testapp.Data.Message;
import com.example.rdsh.testapp.Data.User;
import com.example.rdsh.testapp.R;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.example.rdsh.testapp.Activities.Main.MainActivity.FALSE;
import static com.example.rdsh.testapp.Activities.Main.MainActivity.TRUE;
import static com.example.rdsh.testapp.Activities.Main.MainActivity.chatFragment;
import static com.example.rdsh.testapp.Activities.Main.MainActivity.fragmentChatList;

public class ChatFragment extends Fragment {

    private String title;

    private MessageChatAdapter messageChatAdapter;

    private EditText creatorEd;

    private List<User> sortedList;

    private int finalItemPosition1;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chat, container, false);

        int itemPosition = 0;
        if (getArguments() != null)
            itemPosition = getArguments().getInt("position");
        List<User> users = MainActivity.myAppDatabase.daoUser().getAll();
        for (User u : users) {
            u.setChatHistory(MainActivity.myAppDatabase.daoMessage().getChatByUserId(u.getId()));
        }
        sortedList = ListFragment.sort(users);
        title = sortedList.get(itemPosition).getName();

        messageChatAdapter = new MessageChatAdapter(view.getContext(), sortedList.get(itemPosition)
                .getChatHistory());
        final ListView lvMain = view.findViewById(R.id.listview_message_list);
        lvMain.setAdapter(messageChatAdapter);
        finalItemPosition1 = itemPosition;
        uiInit(view);
        return view;
    }

    private void uiInit(View view) {
        Button sendButton = view.findViewById(R.id.button_chatbox_send);
        creatorEd = view.findViewById(R.id.edittext_chatbox);

        //notification test
        final Runnable run = notificationSet();

        Button testButton = view.findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("position", 0);
                chatFragment.setArguments(bundle);
                run.run();
            }

        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CommitTransaction")
            @Override
            public void onClick(View v) {
                String newMessage = creatorEd.getText().toString();
                creatorEd.setText("");
                long time = new Date().getTime();
                Message message = new Message(newMessage, time, TRUE, sortedList.get(finalItemPosition1).getId());
                MainActivity.myAppDatabase.daoMessage().addMessage(message);
                messageChatAdapter.updateList(MainActivity.myAppDatabase.daoMessage()
                        .getChatByUserId(sortedList.get(finalItemPosition1).getId()));
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    Objects.requireNonNull(getFragmentManager()).beginTransaction().detach(fragmentChatList)
                            .attach(fragmentChatList).commit();
                }
                Bundle bundle = new Bundle();
                    bundle.putInt("position", 0);
                    chatFragment.setArguments(bundle);
            }

        });
    }

    @NonNull
    private Runnable notificationSet() {
        final NotificationManager notificationManager = (NotificationManager) Objects
                .requireNonNull(getActivity())
                .getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel("my_channel0",
                    "my_channel", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]
                    {100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(notificationChannel);
        }

        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getActivity(), "my_channel0");

        final Handler h = new Handler();
        return new Runnable() {
            //stops after n+1 times
            final int nTimes = 1;
            boolean stopMe = false;
            int i = 0;

            public void run() {
                if (!stopMe) {
                    long date = new Date().getTime();
                    Message message = new Message("testNotify #" + (i++ + 1),
                            date, FALSE, sortedList.get(finalItemPosition1).getId());
                    MainActivity.myAppDatabase.daoMessage().addMessage(message);
                    if (i > nTimes) {
                        stopMe = true;
                    }
                    builder.setSmallIcon(R.drawable.ic_launcher_background)
                            .setContentTitle("New bubble_out from: " + sortedList.get(finalItemPosition1).getName())
                            .setContentText(message.getMessage());

                    notificationManager.notify(1, builder.build());
                    messageChatAdapter.updateList(MainActivity.myAppDatabase.daoMessage()
                            .getChatByUserId(sortedList.get(finalItemPosition1).getId()));
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        Objects.requireNonNull(getFragmentManager()).beginTransaction().detach(fragmentChatList)
                                .attach(fragmentChatList).commit();
                    }
                    h.postDelayed(this, 1000);

                }
            }
        };
    }

    public void onResume() {
        super.onResume();

        ((MainActivity) Objects.requireNonNull(getActivity()))
                .setActionBarTitle(title);

    }

}

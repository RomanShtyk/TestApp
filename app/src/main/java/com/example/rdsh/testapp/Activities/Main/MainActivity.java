package com.example.rdsh.testapp.activities.main;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rdsh.testapp.data.MyAppDatabase;
import com.example.rdsh.testapp.activities.main.fragments.ChatFragment;
import com.example.rdsh.testapp.activities.main.fragments.ListFragment;
import com.example.rdsh.testapp.data.Message;
import com.example.rdsh.testapp.data.User;
import com.example.rdsh.testapp.MyApplication;
import com.example.rdsh.testapp.R;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.example.rdsh.testapp.Values.FALSE;
import static com.example.rdsh.testapp.Values.TRUE;

public class MainActivity extends AppCompatActivity {

    public static MyAppDatabase myAppDatabase;

    public static ListFragment fragmentChatList;
    @SuppressLint("StaticFieldLeak")
    public static ChatFragment chatFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //.fallbackToDestructiveMigration() bad practise
        myAppDatabase = Room.databaseBuilder(MyApplication.getAppContext(), MyAppDatabase.class, "chatApp")
                .fallbackToDestructiveMigration().allowMainThreadQueries().build();
        generateDB(savedInstanceState);

        if (savedInstanceState == null) {
            fragmentChatList = new ListFragment();
            chatFragment = new ChatFragment();
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, fragmentChatList).commit();
            } else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.ChatListFragment, fragmentChatList).commit();
            }
        }
        Log.d("mLog", "MainActivity onCreate");
    }


    @SuppressLint("CommitTransaction")
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main);
            getSupportFragmentManager().beginTransaction().remove(fragmentChatList).commit();
            if (chatFragment.isAdded()) {
                findViewById(R.id.tvChooseChat).setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().remove(chatFragment).commit();
                getSupportFragmentManager().executePendingTransactions();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, chatFragment).commit();
                getSupportFragmentManager().executePendingTransactions();
            }
            getSupportFragmentManager().executePendingTransactions();
            getSupportFragmentManager().beginTransaction().add(R.id.ChatListFragment, fragmentChatList).commit();
            getSupportFragmentManager().executePendingTransactions();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
            getSupportFragmentManager().beginTransaction().remove(fragmentChatList).commit();
            getSupportFragmentManager().executePendingTransactions();
            getSupportFragmentManager().beginTransaction().add(R.id.container, fragmentChatList).commit();

            if (chatFragment.isAdded()) {
                getSupportFragmentManager().beginTransaction().remove(chatFragment).commit();
                getSupportFragmentManager().executePendingTransactions();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, chatFragment).commit();
            }
        }
    }

    public void setActionBarTitle(String title) {
        Objects.requireNonNull(getSupportActionBar()).setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView textView = new TextView(this);
        textView.setText(title);
        textView.setTextSize(20);
        textView.setTypeface(null, Typeface.BOLD);
        textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(textView);
    }


    private void generateDB(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            //myAppDatabase.daoMessage().deleteAll();
            //myAppDatabase.daoUser().deleteAll();
            if (myAppDatabase.daoUser().getAll().size() == 0) {
                User user = new User("friend0");
                User user1 = new User("friend1");
                user.setImage(R.drawable.friend0);
                user1.setImage(R.drawable.friend1);
                myAppDatabase.daoUser().addUser(user);
                myAppDatabase.daoUser().addUser(user1);
                List<User> users = myAppDatabase.daoUser().getAll();
                long time = new Date().getTime();

                Message message = new Message("Hi!", time, TRUE, users.get(0).getId(), 0);
                Message message1 = new Message("Hi!", time, FALSE, users.get(0).getId(), 0);
                Message message2 = new Message("Hi!", time, TRUE, users.get(1).getId(), 0);
                Message message3 = new Message("Hi!", time, FALSE, users.get(1).getId(), 0);
                myAppDatabase.daoMessage().addMessage(message);
                myAppDatabase.daoMessage().addMessage(message1);
                myAppDatabase.daoMessage().addMessage(message2);
                myAppDatabase.daoMessage().addMessage(message3);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (chatFragment.isAdded()) {
                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragmentChatList).commit();
            }
        } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            super.onBackPressed();
        }
    }

    //logging

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("mLog", "MainActivity onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("mLog", "MainActivity onStop");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("mLog", "MainActivity onPause");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("mLog", "MainActivity onStart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("mLog", "MainActivity onDestroy");
    }
}

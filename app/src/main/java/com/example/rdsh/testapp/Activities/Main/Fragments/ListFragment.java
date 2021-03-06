package com.example.rdsh.testapp.activities.main.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rdsh.testapp.activities.main.MainActivity;
import com.example.rdsh.testapp.activities.main.adapters.ListAdapter;
import com.example.rdsh.testapp.data.User;
import com.example.rdsh.testapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.rdsh.testapp.activities.main.MainActivity.chatFragment;


public class ListFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        List<User> users = MainActivity.myAppDatabase.daoUser().getAll();
        for (User u : users) {
            u.setChatHistory(MainActivity.myAppDatabase.daoMessage().getChatByUserId(u.getId()));
        }
        //The list for descend sort by time list_View of users
        final List<User> sortedList = sort(users);
        ListAdapter listAdapter = new ListAdapter(view.getContext(), sortedList);
        ListView lvMain = view.findViewById(R.id.list_view);
        lvMain.setAdapter(listAdapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("CommitTransaction")
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    bundle.putInt("position", position);
                    chatFragment.setArguments(bundle);
                    assert getFragmentManager() != null;
                    getFragmentManager().beginTransaction().replace(R.id.container, chatFragment).commit();
                } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    bundle.putInt("position", position);
                    chatFragment.setArguments(bundle);
                    if (chatFragment.isAdded()) {
                        assert getFragmentManager() != null;
                        getFragmentManager().beginTransaction().remove(chatFragment)
                                .add(R.id.container, chatFragment).addToBackStack(null).commit();
                    } else {
                        Objects.requireNonNull(getActivity()).findViewById(R.id.tvChooseChat)
                                .setVisibility(View.GONE);
                        assert getFragmentManager() != null;
                        getFragmentManager().beginTransaction()
                                .add(R.id.container, chatFragment).addToBackStack(null).commit();
                    }
                }

            }
        });
        Log.d("mLog", "ListFragment onCreateView");
        return view;
    }

    public static List<User> sort(List<User> users) {
        List<User> sortedList = new ArrayList<>();
        int size = users.size();
        if(size == 0){
            return sortedList;//for no contacts
        }
        for (int i = 0; i < size; i++) {
            int currentMax = 0;
            long currentLong = 0;
            for (int j = 0; j < size - i; j++) {
                if(users.get(j).getChatHistory().size() == 0){
                    break;
                } else if (users.get(j).getChatHistory().get(users.get(j).getChatHistory().size() - 1).getTime() > currentLong) {
                    currentLong = users.get(j).getChatHistory().get(users.get(j).getChatHistory().size() - 1).getTime();
                    currentMax = j;
                }
            }
            sortedList.add(users.get(currentMax));
            users.remove(currentMax);
        }
        return sortedList;
    }

    public void onResume() {
        super.onResume();
        ((MainActivity) Objects.requireNonNull(getActivity()))
                .setActionBarTitle("Chats");
        Log.d("mLog", "ListFragment onResume");
    }

    //logging


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("mLog", "ListFragment onCreate");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d("mLog", "ListFragment onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("mLog", "ListFragment onDetach");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("mLog", "ListFragment onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("mLog", "ListFragment onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("mLog", "ListFragment onPause");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("mLog", "ListFragment onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("mLog", "ListFragment onDestroy");
    }

}

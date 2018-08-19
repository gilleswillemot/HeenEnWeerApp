package com.example.gilles.g_hw_sl_pv_9200.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.gilles.g_hw_sl_pv_9200.Backend.DataInterface;
import com.example.gilles.g_hw_sl_pv_9200.Backend.ServiceGenerator;
import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Utils;
import com.example.gilles.g_hw_sl_pv_9200.Models.ChatMessage;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lucas on 21-11-2017.
 */
public class ChatFragment extends Fragment {
    private static final String ARG_MESSAGES = "messages";
    private static final String ARG_GESPREKID = "gesprekId";
    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private Button btnTerug;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory = new ArrayList<>();


    public ChatFragment(){}

    /**
     * methode voor het aanmaken van een nieuwe instance van een chatfragment
     * @param messages
     * @param gesprekId
     * @return
     */
    public static ChatFragment newInstance(ArrayList<ChatMessage> messages,String gesprekId){
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_MESSAGES, messages);
        args.putString(ARG_GESPREKID,gesprekId);
        args.putString("TAG", "ChatFragment");
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            chatHistory = getArguments().getParcelableArrayList(ARG_MESSAGES);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.frangment_chat, container, false);
        initControls(view);
        return view;

    }


    /**
     * initializer voor de controls van een chatadapter
     * @param view
     */
    private void initControls(View view) {
        messagesContainer = (ListView) view.findViewById(R.id.messagesContainer);
        messageET = (EditText) view.findViewById(R.id.messageEdit);
        sendBtn = (Button) view.findViewById(R.id.chatSendButton);
        btnTerug = (Button) view.findViewById(R.id.ChatTerugButton);
        ChatFragment fragment = this;
        btnTerug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                        .remove(fragment).commit();
            }
        });

       /* TextView meLabel = (TextView) findViewById(R.id.meLbl);
        TextView companionLabel = (TextView) findViewById(R.id.friendLabel);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        companionLabel.setText("My Buddy");// Hard Coded*/
        loadDummyHistory();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }

                DataInterface dataInterface = ServiceGenerator.createService(DataInterface.class);
                Call<ChatMessage> call = dataInterface.postBericht(getArguments().getString(ARG_GESPREKID),
                        new ChatMessage(messageText, Utils.GEBRUIKER.getId(),
                        DateFormat.getDateTimeInstance().format(new Date()),Utils.GEBRUIKER.getVoorNaam()));
                call.enqueue(new Callback<ChatMessage>() {
                    @Override
                    public void onResponse(Call<ChatMessage> call, Response<ChatMessage> response) {
                        messageET.setText("");
                        displayMessage(response.body());
                    }

                    @Override
                    public void onFailure(Call<ChatMessage> call, Throwable t) {
                        Log.d("Error:",t.getMessage());
                    }
                });
               /* chatMessage.setId(122);//dummy
                chatMessage.setMessage(messageText);
                chatMessage.setDate(DateFormat.getDateTimeInstance().format(new Date()));
                chatMessage.setMe(true);*/


            }
        });
    }

    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }

    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }

    private void loadDummyHistory(){


        adapter = new ChatAdapter(getContext(), new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }


}
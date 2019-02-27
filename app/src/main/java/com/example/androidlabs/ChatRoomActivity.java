package com.example.androidlabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ChatRoomActivity extends AppCompatActivity {

    ListView listView;
    EditText editText;
    List<Message> listMessage = new ArrayList<>();
    Button sendBtn;
    Button receiveBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        listView = (ListView)findViewById(R.id.ListView);
        editText = (EditText)findViewById(R.id.ChatEditText);
        sendBtn = (Button)findViewById(R.id.SendBtn);
        receiveBtn = (Button)findViewById(R.id.ReceiveBtn);





        sendBtn.setOnClickListener(c -> {
            String message = editText.getText().toString();
            Message model = new Message(message, true);
            listMessage.add(model);
            editText.setText("");
            ChatAdapter adt = new ChatAdapter(listMessage, getApplicationContext());
            listView.setAdapter(adt);
            adt.notifyDataSetChanged();
        });

        receiveBtn.setOnClickListener(c -> {
            String message = editText.getText().toString();
            Message model = new Message(message, false);
            listMessage.add(model);
            editText.setText("");
            ChatAdapter adt = new ChatAdapter(listMessage, getApplicationContext());
            listView.setAdapter(adt);
            adt.notifyDataSetChanged();
        });





        Log.d("ChatRoomActivity","onCreate");

    }


}

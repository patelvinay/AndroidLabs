package com.example.androidlabs;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    ListView lv;
    EditText et;
    List<Message> msgList = new ArrayList<>();
    Button btnSend;
    Button btnReceive;

    // DATABASE CLASS REFERENCE OBJECT
    DatabaseClass dc = new DatabaseClass(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lv = (ListView)findViewById(R.id.lstView);
        et = (EditText)findViewById(R.id.chatEditText);
        btnSend = (Button)findViewById(R.id.SendBtn);
        btnReceive = (Button)findViewById(R.id.ReceiveBtn);



        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String sendMessage = et.getText().toString();
                if (!sendMessage.equals("")) {
                    Message model = new Message(sendMessage,true, true);
                    dc.insertMessage(sendMessage);
                    msgList.add(model);
                    ChatAdapter adt = new ChatAdapter(msgList, getApplicationContext());
                    lv.setAdapter(adt);
                    et.setText("");

                }

            }

        });

        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sendMessage = et.getText().toString();
                if ( !sendMessage.equals("")){
                    Message model = new Message(sendMessage,true,false);
                    msgList.add(model);

                    ChatAdapter adt = new ChatAdapter(msgList, getApplicationContext());
                    lv.setAdapter(adt);
                    et.setText("");
                    dc.insertMessage(sendMessage);
                }
            }
        });


    }


    public void printCursor(Cursor c) {

    }
}

class ChatAdapter extends BaseAdapter{

    private List<Message> messageModels;
    private Context context;
    private LayoutInflater inflater;

    public ChatAdapter(List<Message> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return messageModels.size();
    }

    @Override
    public Object getItem(int position) {
        return messageModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null){
            if (messageModels.get(position).getisReceived()){
                view = inflater.inflate(R.layout.activity_send, null);

            }else {
                view = inflater.inflate(R.layout.activity_receive, null);
            }
            TextView messageText = (TextView)view.findViewById(R.id.messageText);
            messageText.setText(messageModels.get(position).getMsg());
        }
        return view;
    }
}
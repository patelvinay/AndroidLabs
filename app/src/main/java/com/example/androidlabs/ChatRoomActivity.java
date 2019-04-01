package com.example.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class ChatRoomActivity extends AppCompatActivity {

    private ListView chatListView;
    private EditText msgEditText;
    private List<Message> msgList = new ArrayList<>();
    private Button btnSend;
    private Button btnReceive;

    private ChatRoomHelper chatRoomHelper;

    private ChatAdapter adapter;

    public static final String ITEM_ID = "id";
    public static final String ITEM_POSITION = "position";
    public static final String ITEM_MSG = "msg";
    public static final String ITEM_TYPE = "type";

    public static final int EMPTY_ACTIVITY = 345;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        chatListView = (ListView) findViewById(R.id.lstView);
        msgEditText = (EditText) findViewById(R.id.chatEditText);
        btnSend = (Button) findViewById(R.id.SendBtn);
        btnReceive = (Button) findViewById(R.id.ReceiveBtn);
        chatRoomHelper = new ChatRoomHelper(this);

        boolean isTablet = findViewById(R.id.fragmentLocation) != null; //check if the FrameLayout is loaded --- fragment

        Cursor cursor = chatRoomHelper.printCursor();
        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {

                Message model = new Message(cursor.getLong(0), cursor.getString(1), cursor.getInt(2) == 0);
                msgList.add(model);
                adapter = new ChatAdapter(msgList, getApplicationContext());
                chatListView.setAdapter(adapter);
            }
        } else {
            adapter = new ChatAdapter(msgList, getApplicationContext());
            chatListView.setAdapter(adapter);
        }


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sendMessage = msgEditText.getText().toString();
                updateMessageIntoList(sendMessage, true);
            }
        });

        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sendMessage = msgEditText.getText().toString();
                updateMessageIntoList(sendMessage, false);
            }
        });



        chatListView.setOnItemClickListener((parent, view, position, id) -> {


            Message message = (Message) adapter.getItem(position);
            Log.e("ClickStartPosition",Integer.toString(position));
            Log.e("message= ",message.toString());


            Bundle dataToPass = new Bundle();
            dataToPass.putLong(ITEM_ID, message.getId());
            dataToPass.putInt(ITEM_POSITION, position);

            dataToPass.putString(ITEM_MSG, message.getMsg());
            dataToPass.putBoolean(ITEM_TYPE, message.isSend());
            Log.e("Bundle data id",ITEM_ID);
            Log.e("position",ITEM_POSITION);
            Log.e("positionGet",Integer.toString(dataToPass.getInt(ITEM_POSITION)));
            Log.e("msg",ITEM_MSG);
            Log.e("item_type==",ITEM_TYPE);

            if(isTablet)
            {
                MessageFragment messageFragment = new MessageFragment();
                messageFragment.setArguments( dataToPass );
                messageFragment.setTablet(true);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fragmentLocation, messageFragment)
                        .addToBackStack("AnyName")
                        .commit();
            }
            else
            {
                Intent nextActivity = new Intent(ChatRoomActivity.this, EmptyActivity.class);
                nextActivity.putExtras(dataToPass);

                Log.e("phone intent pos",Integer.toString(dataToPass.getInt(ITEM_POSITION)));
                startActivityForResult(nextActivity, EMPTY_ACTIVITY);
            }


        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == EMPTY_ACTIVITY)
        {
            if(resultCode == RESULT_OK) //if you hit the delete button instead of back button
            {
                long id = data.getLongExtra(ITEM_ID, 0);
                int position=data.getIntExtra(ITEM_POSITION,0);
                Log.e("functionActivityResult",Long.toString(id));
                Log.e("ITEM_POSITION == ",Integer.toString(position));
                deleteMessageId(id,position);
            }
        }
    }

    public void deleteMessageId(long id,int position)
    {
        //ChatRoomHelper chatRoomHelper=new ChatRoomHelper(this);
        Log.i("Delete this message:" , " id="+id);
        int result=chatRoomHelper.deleteData(id);
        Log.e("ggg",String.valueOf(result));
        if(result==1) {
            Message m=msgList.remove(position);
            Log.e("item_Position===",Integer.toString(position));
            Log.e("item_Position===",ITEM_POSITION);
            Log.e("item===",m.getMsg());
            adapter.notifyDataSetChanged();
        }
    }

    private void updateMessageIntoList(final String msg, final boolean isSend) {
        final Message model = new Message(msg, isSend);
        long id = chatRoomHelper.insertData(msg, isSend);
        if (id > -1) {
            model.setId(id);
            msgList.add(model);
            adapter.notifyDataSetChanged();
            msgEditText.setText("");
        }

    }

    private void viewFullData() {
        Cursor cursor = chatRoomHelper.printCursor();

        if (cursor.getCount() != 0) {
            while (cursor.moveToNext()) {

                Message model = new Message(cursor.getLong(0), cursor.getString(1), cursor.getInt(2) == 0);
                msgList.add(model);
                ChatAdapter adt = new ChatAdapter(msgList, getApplicationContext());
                chatListView.setAdapter(adt);
            }
        }
    }
}

class ChatAdapter extends BaseAdapter {

    private List<Message> messageModels;
    private Context context;
    private LayoutInflater inflater;

    public ChatAdapter(List<Message> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
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
        return messageModels.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;


        if (messageModels.get(position).isSend()) {
            view = LayoutInflater.from(context).inflate(R.layout.activity_send, null);

        } else {
            view = LayoutInflater.from(context).inflate(R.layout.activity_receive, null);
        }
        TextView messageText = (TextView) view.findViewById(R.id.messageText);
        messageText.setText(messageModels.get(position).getMsg());

        return view;
    }
}

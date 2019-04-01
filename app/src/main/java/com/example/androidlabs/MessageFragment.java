package com.example.androidlabs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MessageFragment extends Fragment {


    private boolean isTablet;
    private Bundle dataFromActivity;
    private long id;
    private String msg;
    private int position;
    private boolean type;

    public void setTablet(boolean tablet) { isTablet = tablet; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        dataFromActivity = getArguments();
        id = dataFromActivity.getLong(ChatRoomActivity.ITEM_ID );
        msg=dataFromActivity.getString(ChatRoomActivity.ITEM_MSG);
        type=dataFromActivity.getBoolean(ChatRoomActivity.ITEM_TYPE);
        position=dataFromActivity.getInt(ChatRoomActivity.ITEM_POSITION);

        // Inflate the layout for this fragment
        View result =  inflater.inflate(R.layout.activity_message_fragment, container, false);

        //show the id
        TextView messageID = (TextView)result.findViewById(R.id.message_id);
        messageID.setText("ID = "+id);

        //show the message:
        TextView messageText = (TextView)result.findViewById(R.id.messageText);
        messageText.setText("text=" + msg);

        TextView  messageType= (TextView)result.findViewById(R.id.messageType);
        messageType.setText("isSend=" + type);

        TextView messagePosition = (TextView)result.findViewById(R.id.messagePosition);
        messagePosition.setText("Position=" + position);

        // get the delete button, and add a click listener:
        Button deleteButton = (Button)result.findViewById(R.id.deleteMessage);
        deleteButton.setOnClickListener( clk -> {

            if(isTablet) { //both the list and details are on the screen:
                ChatRoomActivity parent = (ChatRoomActivity) getActivity();
                //parent=new ChatRoomActivity();
                //parent.deleteMessageId((int)id); //this deletes the item and updates the list
                //Log.e("Tablet id",id);
                parent.deleteMessageId(id,position); //this deletes the item and updates the list

                //now remove the fragment since you deleted it from the database:
                // this is the object to be removed, so remove(this):
                parent.getSupportFragmentManager().beginTransaction().remove(this).commit();
            }
            //for Phone:
            else //You are only looking at the details, you need to go back to the previous list page
            {
                EmptyActivity parent = (EmptyActivity) getActivity();
                Intent backToFragmentExample = new Intent();
                backToFragmentExample.putExtra(ChatRoomActivity.ITEM_ID, dataFromActivity.getLong(ChatRoomActivity.ITEM_ID ));
                backToFragmentExample.putExtra(ChatRoomActivity.ITEM_POSITION,dataFromActivity.getInt(ChatRoomActivity.ITEM_POSITION));

                parent.setResult(Activity.RESULT_OK, backToFragmentExample); //send data back to FragmentExample in onActivityResult()
                parent.finish(); //go back
            }
        });
        return result;
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_message_fragment);
//        Intent intent = getIntent();
//       // String msg=getId.getStringExtra(ChatRoomActivity.ITEM_ID);
//        id = intent.getLongExtra(ChatRoomActivity.ITEM_ID,-1);
//        position = intent.getIntExtra(ChatRoomActivity.ITEM_POSITION, -1);
//        msg = intent.getStringExtra(ChatRoomActivity.ITEM_MSG);
//
//        m_id = findViewById(R.id.message_id);
//        m_id.setText(String.valueOf(id + "    " + position + "    " + msg));
//      // Log.e("anc",msg);
//
//        message=findViewById(R.id.messageText);
//        //chatDatabase =new ChatRoomHelper(getId);
//       // Cursor cursor= chatDatabase.printCursor(m_id.toString());
//
//        message.setText(msg);
//      //  Log.e("and",msgText);
//    }
}

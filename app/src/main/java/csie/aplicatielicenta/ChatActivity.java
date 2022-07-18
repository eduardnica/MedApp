package csie.aplicatielicenta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import csie.aplicatielicenta.Adapters.ChatAdapter;
import csie.aplicatielicenta.Models.ChatList;

public class ChatActivity extends AppCompatActivity {

    String chatKey;
    int generatedChatKey;
    RecyclerView chattingRecyclerView;
    List<ChatList> chatLists = new ArrayList<>();
    ChatAdapter chatAdapter;
    boolean loadingFirestTime= true;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        ImageView btnBack = findViewById(R.id.btnBack);
        TextView userName = findViewById(R.id.twUserName);
        EditText messageEditText = findViewById(R.id.messageEditText);
        ImageView sendBtn = findViewById(R.id.btnSend);
        ImageView imageViewProfile = findViewById(R.id.imageViewProfile);
        chattingRecyclerView = findViewById(R.id.chattingRecyclerView);
        String getName = getIntent().getStringExtra("name");
        chatKey = getIntent().getStringExtra("chat_key");
        String getId = getIntent().getStringExtra("id");
        String getUserId = "FJb80GguBDgpzN4m5tqBn1GlCxm1";

        userName.setText(getName);
        chattingRecyclerView.setHasFixedSize(true);
        chattingRecyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));

        chatAdapter = new ChatAdapter(chatLists, ChatActivity.this);
        chattingRecyclerView.setAdapter(chatAdapter);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(chatKey.isEmpty()){
                    chatKey= "1";
                    generatedChatKey = 1;

                    if(snapshot.hasChild("Chat")){
                        generatedChatKey = (int) (snapshot.child("Chat").getChildrenCount() + 1);

                        chatKey= String.valueOf(generatedChatKey);
                    }
                }

                if(snapshot.hasChild("Chat")){
                    if(snapshot.child("Chat").child(chatKey).hasChild("Messages")){

                        chatLists.clear();
                        for(DataSnapshot messageSnapshot : snapshot.child("Chat").child(chatKey).child("Messages").getChildren()){

                            if(messageSnapshot.hasChild("msg") && messageSnapshot.hasChild("id")){

                                String messageTimestamps = messageSnapshot.getKey();
                                String getId = messageSnapshot.child("id").getValue(String.class);
                                String getMsg = messageSnapshot.child("msg").getValue(String.class);

                                Timestamp timestamp = new Timestamp(Long.parseLong(messageTimestamps));
                                Date date = new Date(timestamp.getTime());
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                                SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("hh:mm aa", Locale.getDefault());
                                ChatList chatList = new ChatList(getId, getName, getMsg, simpleDateFormat.format(date).toString(), simpleTimeFormat.format(date).toString());
                                chatLists.add(chatList);

                                if(loadingFirestTime || Long.parseLong(messageTimestamps) >  Long.parseLong(MemoryData.getLastMsgTs(ChatActivity.this, chatKey))){
                                    loadingFirestTime = false;
                                    MemoryData.saveLastMsgTS(messageTimestamps, chatKey,ChatActivity.this);
                                    chatAdapter.updateChatList(chatLists);

                                    chattingRecyclerView.scrollToPosition(chatLists.size() - 1 );
                                }
                            }

                        }

                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getTxtMessage = messageEditText.getText().toString();


                String currentTimestamp = String.valueOf(System.currentTimeMillis()).substring(0,10);

                databaseReference.child("Chat").child(chatKey).child("user_1").setValue(getId);
                databaseReference.child("Chat").child(chatKey).child("user_2").setValue(getUserId);
                databaseReference.child("Chat").child(chatKey).child("Messages").child(currentTimestamp).child("msg").setValue(getTxtMessage);
                databaseReference.child("Chat").child(chatKey).child("Messages").child(currentTimestamp).child("id").setValue(getId);

                messageEditText.setText("");
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
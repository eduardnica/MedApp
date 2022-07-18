package csie.aplicatielicenta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import csie.aplicatielicenta.Adapters.MessagesAdapter;
import csie.aplicatielicenta.Models.Messages;

public class MessagesActivity extends AppCompatActivity {

    String name;
    String id;
    String email;
    int unseenMessages = 0;
    String lastMessage = "";
    String chatKey= "";
    RecyclerView messagesRecycler;
    MessagesAdapter messagesAdapter;

    boolean dataSet=false;

    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        messagesRecycler = findViewById(R.id.recyclerViewMessages);
        List<Messages> messagesList = new ArrayList<>();
        name = getIntent().getStringExtra("name");
        id = getIntent().getStringExtra("id");
        email = getIntent().getStringExtra("email");


        messagesAdapter = new MessagesAdapter(messagesList, MessagesActivity.this);
        messagesRecycler.setAdapter(messagesAdapter);

        messagesRecycler.setHasFixedSize(true);
        messagesRecycler.setLayoutManager(new LinearLayoutManager(this));

        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messagesList.clear();
                unseenMessages = 0;
                lastMessage = "";
                chatKey = "";

                for(DataSnapshot dataSnapshot: snapshot.child("Users").getChildren()){

                    String userId = dataSnapshot.getKey();

                    dataSet = false;

                    if(!userId.equals(uid)){
                        String email = dataSnapshot.child("email").getValue(String.class);
                        String firstName = dataSnapshot.child("firstName").getValue(String.class);
                        String lastName = dataSnapshot.child("lastName").getValue(String.class);
                        String name = firstName + " " + lastName;



                        FirebaseDatabase.getInstance().getReference("Chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                int getChatCounts = (int) snapshot.getChildrenCount();

                                if(getChatCounts > 0){

                                    for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                                        final String getKey = dataSnapshot1.getKey();
                                        chatKey = getKey;


                                        final String getUserOne = dataSnapshot1.child("user_1").getValue(String.class);
                                        final String getUserTwo = dataSnapshot1.child("user_2").getValue(String.class);


                                        for(DataSnapshot chatDataSnapshot : dataSnapshot1.child("Messages").getChildren()){

                                            final long getMessageKey = Long.parseLong(chatDataSnapshot.getKey());
                                            final long getLastSeenMessage = Long.parseLong(MemoryData.getLastMsgTs(MessagesActivity.this, getKey));


                                            lastMessage = chatDataSnapshot.child("msg").getValue(String.class);
                                            if(getMessageKey > getLastSeenMessage){
                                                unseenMessages++;
                                            }
                                        }

                                        if((getUserOne.equals(userId) && getUserTwo.equals(uid)) || (getUserOne.equals(uid) && getUserTwo.equals(userId))){


                                        }
                                    }


                                }



                                if(!dataSet){
                                    dataSet = true;
                                    Messages messages = new Messages(name, uid, lastMessage, unseenMessages, chatKey);
                                    messagesList.add(messages);
                                    messagesAdapter.updateData(messagesList);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
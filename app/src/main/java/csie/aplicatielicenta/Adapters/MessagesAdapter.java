package csie.aplicatielicenta.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.List;
import csie.aplicatielicenta.ChatActivity;
import csie.aplicatielicenta.Models.Messages;
import csie.aplicatielicenta.R;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {

    private List<Messages> messagesList;
    private final Context context;

    public MessagesAdapter(List<Messages> messagesList, Context context) {
        this.messagesList = messagesList;
        this.context = context;
    }


    @NonNull
    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_messages, null));
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesAdapter.ViewHolder holder, int position) {
        Messages list2= messagesList.get(position);
        holder.name.setText(list2.getName());
        holder.lastMessage.setText(list2.getLastMessage());

        if(list2.getUnseenMessage() == 0){
            holder.unseenMessage.setVisibility(View.GONE);
            holder.lastMessage.setTextColor(Color.parseColor("#959595"));
        } else {
            holder.unseenMessage.setVisibility(View.VISIBLE);
            holder.unseenMessage.setText(list2.getUnseenMessage()+"");
            holder.lastMessage.setTextColor(context.getResources().getColor(R.color.myColor));
        }

        holder.linerLayoutMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("id", list2.getId());
                intent.putExtra("name", list2.getName());
                intent.putExtra("chat_key", list2.getChatKey());
                context.startActivity(intent);
            }
        });
    }

    public void updateData(List<Messages> messagesList){
        this.messagesList= messagesList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewProfile;
        private TextView name;
        private TextView lastMessage;
        private TextView unseenMessage;
        private LinearLayout linerLayoutMessages;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewProfile = itemView.findViewById(R.id.imageViewProfile);
            name = itemView.findViewById(R.id.twName);
            lastMessage = itemView.findViewById(R.id.twLastMessage);
            unseenMessage = itemView.findViewById(R.id.twUnseenMessages);
            linerLayoutMessages = itemView.findViewById(R.id.linerLayoutMessages);



        }
    }
}

package csie.aplicatielicenta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import csie.aplicatielicenta.MemoryData;
import csie.aplicatielicenta.Models.ChatList;
import csie.aplicatielicenta.R;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    List<ChatList> chatLists;
    Context context;
    String userId;

    public ChatAdapter(List<ChatList> chatLists, Context context) {
        this.chatLists = chatLists;
        this.context = context;
        this.userId = MemoryData.getData(context);
    }

    @NonNull
    @Override
    public ChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_chat_adapter, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ChatAdapter.ViewHolder holder, int position) {

        ChatList list2 = chatLists.get(position);

        if(list2.getId().equals(userId)){
            holder.myLayout.setVisibility(View.VISIBLE);
            holder.oppoLayout.setVisibility(View.GONE);

            holder.myMessage.setText(list2.getMessage());
            holder.myTime.setText(list2.getDate() + " " + list2.getTime());
        } else {
            holder.myLayout.setVisibility(View.GONE);
            holder.oppoLayout.setVisibility(View.VISIBLE);

            holder.oppoMessage.setText(list2.getMessage());
            holder.oppoTime.setText(list2.getDate() + " " + list2.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return chatLists.size();
    }


    public void updateChatList(List<ChatList> chatLists){
        this.chatLists = chatLists;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout oppoLayout, myLayout;
        TextView oppoMessage, myMessage;
        TextView oppoTime, myTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            
            oppoLayout = itemView.findViewById(R.id.oppoLayout);
            myLayout = itemView.findViewById(R.id.myLayout);
            oppoMessage = itemView.findViewById(R.id.oppoMessage);
            myMessage = itemView.findViewById(R.id.myMessage);
            oppoTime = itemView.findViewById(R.id.oppoMsgTime);
            myTime = itemView.findViewById(R.id.myMsgTime);
        }
    }
}

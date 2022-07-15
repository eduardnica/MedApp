package csie.aplicatielicenta.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import csie.aplicatielicenta.Models.Consultation;
import csie.aplicatielicenta.R;

public class ConsultationAdapter extends RecyclerView.Adapter<ConsultationAdapter.ViewHolder> {

     Context myContext;
     public ArrayList<Consultation> arrayList;


    public ConsultationAdapter(ArrayList<Consultation> arrayList, Context myContext) {
        this.arrayList = arrayList;
        this.myContext = myContext;
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView specialization, hospital, dateAndTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            specialization = itemView.findViewById(R.id.twSpecialization);
            hospital = itemView.findViewById(R.id.twHospital);
            dateAndTime = itemView.findViewById(R.id.twDateAndTime);

        }
    }


    @NonNull
    @Override
    public ConsultationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(myContext).inflate(R.layout.layout_consultations, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ConsultationAdapter.ViewHolder holder, int position) {
        Consultation consultation = arrayList.get(position);
        holder.specialization.setText(consultation.getSpecialization());
        holder.hospital.setText(consultation.getHospital());
        holder.dateAndTime.setText(consultation.getDateAndTime());
    }

    @Override
    public int getItemCount() {
       return arrayList.size();
    }
}

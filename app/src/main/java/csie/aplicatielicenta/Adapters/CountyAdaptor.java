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
import csie.aplicatielicenta.Models.County;
import csie.aplicatielicenta.R;

public class CountyAdaptor extends RecyclerView.Adapter<CountyAdaptor.ViewHolder> {

    private ArrayList<County> arrayList;
    Context myContext;

    public CountyAdaptor(ArrayList<County> arrayList, Context myContext) {
        this.arrayList = arrayList;
        this.myContext = myContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(myContext).inflate(R.layout.layout_county, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        County county = arrayList.get(position);
        String countyName = county.getCountyName();
        String countyTotalCases = county.getTotalCases();
        holder.countyName.setText(countyName);
        holder.countyTotalCases.setText(countyTotalCases);
    }

    @Override
    public int getItemCount() {
        return arrayList==null ? 0 : arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView countyName, countyTotalCases;
        LinearLayout linearLayoutCounty;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            countyName = itemView.findViewById(R.id.twLayoutCountyName);
            countyTotalCases= itemView.findViewById(R.id.twLayoutCountyCases);
            linearLayoutCounty = itemView.findViewById(R.id.linearLayoutCounty);
        }
    }

    public void filterList(ArrayList<County> filteredList){
        arrayList = filteredList;
        notifyDataSetChanged();
    }
}

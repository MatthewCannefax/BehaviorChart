package com.matthewcannefax.behaviorchart.ArrayAdapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.matthewcannefax.behaviorchart.R;
import com.matthewcannefax.behaviorchart.model.Kid;

import java.util.List;

public class KidArrayAdapter extends ArrayAdapter {

    private final List<Kid> mKids;

    private final LayoutInflater mInfalter;

    private final Context mContext;

    public KidArrayAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, R.layout.kid_view_item, objects);

        mKids = objects;
        mInfalter = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        if(getCount() < 1){
            return 1;
        }else {
            return getCount();
        }

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = mInfalter.inflate(R.layout.kid_view_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.imageView);
        TextView tvName = convertView.findViewById(R.id.tvName);
        TextView tvScreens = convertView.findViewById(R.id.tvScreens);
        TextView tvPoints = convertView.findViewById(R.id.tvPoints);

        final Kid kid = mKids.get(position);

        tvName.setText(kid.getpName());
        tvPoints.setText(Integer.toString(kid.getPoints()));

        if(kid.getInfractions() != null && kid.getInfractions().size() > 0){
            if(kid.getCandy()) {
                imageView.setImageResource(R.drawable.yes_candy_face);
            }else{
                imageView.setImageResource(R.drawable.no_candy_face);
            }
        }else{
            imageView.setImageResource(R.drawable.yes_candy_face);
        }

        if(kid.getInfractions() != null && kid.getInfractions().size() > 0){
            if(!kid.playScreens()){
                tvScreens.setText("*NO SCREENS*");
            }
        }



        return convertView;
    }
}

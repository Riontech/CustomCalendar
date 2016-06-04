package com.riontech.calendar.dao;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.riontech.calendar.R;

/**
 * Created by Dhaval Soneji on 4/4/16.
 */
public class SectionTitleViewHolder extends RecyclerView.ViewHolder {
    private TextView txtSection;

    public TextView getTxtSection() {
        return txtSection;
    }

    public void setTxtSection(TextView txtSection) {
        this.txtSection = txtSection;
    }

    public SectionTitleViewHolder(View v) {
        super(v);
        txtSection = (TextView) v.findViewById(R.id.txtSection);
    }
}

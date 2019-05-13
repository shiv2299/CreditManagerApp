package com.example.shivam.creditmanage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class user_adapter extends ArrayAdapter<user> {
    Context context;
    List<user> user_list = new ArrayList<>();

    public user_adapter(@NonNull Context context, ArrayList<user> list) {
        super(context, 0 , list);
        this.context=context;
        this.user_list=list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem =convertView;
        if(listItem==null)
            listItem= LayoutInflater.from(this.context).inflate(R.layout.list_design,parent,false);
        user user=user_list.get(position);
        TextView id = (TextView) listItem.findViewById(R.id.id);
        id.setText(""+user.getId());
        TextView name = (TextView) listItem.findViewById(R.id.name);
        name.setText(user.getName());
        TextView email = (TextView) listItem.findViewById(R.id.email);
        email.setText(user.getEmail());
        TextView credit = (TextView) listItem.findViewById(R.id.credit);
        credit.setText(""+user.getCredit());

        return listItem;
    }
}
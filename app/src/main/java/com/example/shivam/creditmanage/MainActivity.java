package com.example.shivam.creditmanage;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DB db;
    boolean f=false;
    ArrayList<user> userList = new ArrayList<user>();
    ListView user_list;
    TextView text;
    Dialog dialog;
    Button transfer;
    EditText credit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user_list=findViewById(R.id.userList);
        db=new DB(this);
        dialog = new Dialog(this);
        SharedPreferences settings2 = getSharedPreferences("myPref", 0);
        boolean hasLoggedIn = settings2.getBoolean("hasLoggedIn", false);
        if(hasLoggedIn)
        {
            Cursor cursor = db.getAllData();
            String name,email;
            int id,credit;
            while (cursor.moveToNext()) {
                id = cursor.getInt(0);
                name = cursor.getString(1);
                email = cursor.getString(2);
                credit = cursor.getInt(3);
                userList.add(new user(""+id,name, email, credit));
            }
            user_adapter user_adapter = new user_adapter(this, userList);
            user_list.setAdapter(user_adapter);
        }
        else {
            SharedPreferences settings = getSharedPreferences("myPref", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("hasLoggedIn", true);
            editor.commit();
            db.insert();
            Cursor cursor = db.getAllData();
            String name,email;
            int id,credit;
            while (cursor.moveToNext()) {
                id = cursor.getInt(0);
                name = cursor.getString(1);
                email = cursor.getString(2);
                credit = cursor.getInt(3);
                userList.add(new user(""+id,name, email, credit));
            }
            user_adapter user_adapter = new user_adapter(this, userList);
            user_list.setAdapter(user_adapter);
        }
        user_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                user senderObject=userList.get(i);
                showPopup(senderObject);
            }
        });
    }
    public void showPopup(final user senderObject){
        dialog.setContentView(R.layout.popup_design);
        text =dialog.findViewById(R.id.text);
        transfer=dialog.findViewById(R.id.transfer);
        credit=dialog.findViewById(R.id.credit);
        final Spinner users=dialog.findViewById(R.id.users);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        users.setAdapter(spinnerAdapter);
        for(int i=0; i<userList.size();i++) {
            if(userList.get(i).getId().equals(senderObject.getId()))
                continue;
            else
            {
                spinnerAdapter.add(userList.get(i).getName());
            }
        }
        spinnerAdapter.notifyDataSetChanged();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String senderId=senderObject.getId();
                int senderCredit=senderObject.getCredit();
                String amount=credit.getText().toString();
                if(amount.isEmpty())
                    credit.setError("Enter Credit");
                else if(senderCredit<Integer.parseInt(amount))
                {
                    credit.setError("Not enough credits");
                }
                else{
                    int newSenderCredit =senderCredit-Integer.parseInt(amount);
                    user receiverObject = null;
                    for(int i=0;i<userList.size();i++)
                    {
                        if(users.getSelectedItem().equals(userList.get(i).getName()))
                            receiverObject=userList.get(i);
                    }
                    int newReceiverCredit=receiverObject.getCredit()+Integer.parseInt(amount);
                    db.update(Integer.parseInt(senderId),newSenderCredit);
                    db.update(Integer.parseInt(receiverObject.getId()),newReceiverCredit);
                    db.insertTransfer(senderObject.getName(),receiverObject.getName(),Integer.parseInt(amount));
                    String noti=amount+" transfered from "+senderObject.getName()+" to "+receiverObject.getName();
                    Toast.makeText(MainActivity.this, noti, Toast.LENGTH_LONG).show();
                    recreate();
                }

            }
        });
    }
}

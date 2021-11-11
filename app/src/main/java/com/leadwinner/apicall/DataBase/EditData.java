package com.leadwinner.apicall.DataBase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.leadwinner.apicall.ListViewJson;
import com.leadwinner.apicall.R;
import com.leadwinner.apicall.UserEntity;

import java.util.Random;

public class EditData extends AppCompatActivity {

    EditText nameEdit;
    EditText jobEdit;
    Button   submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        nameEdit=findViewById(R.id.nameEdit);
        jobEdit=findViewById(R.id.jobEdit);
        submitButton=findViewById(R.id.submitButton);
        UserDataDbHelper userDataDbHelper=new UserDataDbHelper(this);
        if(getIntent().getExtras()!=null&&getIntent().getExtras().containsKey("userUpdate"))
        {
            UserEntity userEntity= (UserEntity) getIntent().getSerializableExtra("userUpdate");
            nameEdit.setText(userEntity.getName());
            jobEdit.setText(userEntity.getJob());
        }
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEntity userEntity=new UserEntity();
                SQLiteDatabase sqLiteDatabase=userDataDbHelper.getWritableDatabase();
                ContentValues contentValues=new ContentValues();
                contentValues.put(UserDataclass.Status, "1");
                contentValues.put(UserDataclass.NAME, nameEdit.getText().toString());
                contentValues.put(UserDataclass.JOB, jobEdit.getText().toString());
                long row = sqLiteDatabase.insert(UserDataclass.TABLE_NAME, null, contentValues);
                if(getIntent().getExtras()!=null&&getIntent().getExtras().containsKey("userUpdate"))
                {
                    sqLiteDatabase.update(UserDataclass.TABLE_NAME,contentValues," ID =? ", new String []{userEntity.getId()});
                }
//                else
//                {
//                    Random random=new Random();
//                    contentValues.put(UserDataclass.ID,random.nextInt());
//                    sqLiteDatabase.insert(UserDataclass.NAME,null,contentValues);
//                }
                Intent intent=new Intent(EditData.this, ListViewJson.class);
                startActivity(intent);
                finish();

            }
        });
    }
}
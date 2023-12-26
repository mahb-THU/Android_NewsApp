package com.java.mahongbo.signup;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.java.mahongbo.R;


public class Register extends AppCompatActivity{
    private MyDatabaseHelper myDatabaseHelper;
    private Button regi;
    private EditText name,pawd,repawd;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regi=(Button) findViewById(R.id.submit);
        name=(EditText) findViewById(R.id.usename);
        pawd=(EditText) findViewById(R.id.usepwd);
        repawd=(EditText) findViewById(R.id.usepwd2);
        myDatabaseHelper =new MyDatabaseHelper(this,"UserDB.db",null,1);
        regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db= myDatabaseHelper.getWritableDatabase();
                String username=name.getText().toString();
                String userpassword=pawd.getText().toString();
                String repassword=repawd.getText().toString();
                if (userpassword.equals(repassword)) {
                    ContentValues values = new ContentValues();
                    values.put("name", username);
                    values.put("password", userpassword);
                    db.insert("User", null, values);
                    Toast.makeText(Register.this, "用户创建成功", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Register.this, LogIn.class));
                    finish();
                } else {
                    Toast.makeText(Register.this, "两次密码不一致，请重新输入", Toast.LENGTH_SHORT).show();
                }
                db.close();
            }
        });
        /*设定艺术字*/
        TextView title2=findViewById(R.id.tit2);
        TextView header=findViewById(R.id.head);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "character.ttf");
        title2.setTypeface(typeface);
        header.setTypeface(typeface);
    }
}

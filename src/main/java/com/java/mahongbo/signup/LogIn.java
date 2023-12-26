/*建立登录页面*/
package com.java.mahongbo.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Typeface;

import com.java.mahongbo.R;

public class LogIn extends AppCompatActivity {
    private EditText inf,passwd;
    private Button loginbtn,regbtn;
    private MyDatabaseHelper myDatabaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        /*建立用户名输入框*/
        inf=(EditText) findViewById(R.id.name);
        /*建立密码输入框*/
        passwd=(EditText) findViewById(R.id.pwd);
        /*建立登录按钮*/
        loginbtn=(Button) findViewById(R.id.login);
        /*建立注册按钮*/
        regbtn=(Button) findViewById(R.id.reg);
        /*启动数据库*/
        myDatabaseHelper =new MyDatabaseHelper(this,"UserDB.db",null,1);
        /*按动注册键*/
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerClicked(view);
            }
        });
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=inf.getText().toString();
                String userpassword=passwd.getText().toString();
                SQLiteDatabase db= myDatabaseHelper.getWritableDatabase();
                Cursor cursor = db.rawQuery("select * from User where name=?", new String[]{username});
                if (cursor.getCount() == 0) {
                    Toast.makeText(LogIn.this, "用户名不存在！", Toast.LENGTH_SHORT).show();
                } else {
                    if (cursor.moveToFirst()) {
                        String userpassword_db = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                        if (userpassword.equals(userpassword_db)) {
                            Toast.makeText(LogIn.this,"登录成功",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LogIn.this, Welcome.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LogIn.this, "密码错误，请重新登录", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                cursor.close();
                db.close();
            }
        });
        /*设定艺术字*/
        TextView title=findViewById(R.id.tit);
        TextView ps1=findViewById(R.id.ps);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "character.ttf");
        title.setTypeface(typeface);
        ps1.setTypeface(typeface);
    }
    public void registerClicked(View view){
        Intent intent=new Intent(LogIn.this, Register.class);
        startActivity(intent);
    }
}
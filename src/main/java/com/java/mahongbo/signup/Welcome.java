package com.java.mahongbo.signup;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;

import com.java.mahongbo.HomeActivity;
import com.java.mahongbo.R;

public class Welcome extends AppCompatActivity{
    private Button cmin;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        cmin=(Button) findViewById(R.id.wel);
        cmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Welcome.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}

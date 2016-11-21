package com.lucky.cat.helloworld;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.mIcon)
    ImageView mIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.workingdog);
        mIcon.setImageDrawable(CircleFramedDrawable.getInstance(this,bitmap,800));
        //mIcon.setImageResource(R.drawable.workingdog);
    }
}

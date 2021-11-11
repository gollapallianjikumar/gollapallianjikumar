package com.leadwinner.apicall.Fragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.tabs.TabLayout;
import com.leadwinner.apicall.R;
import com.leadwinner.apicall.UserData;

public class FargmentMainActivity extends AppCompatActivity
{
    TabLayout tabLayout;
    Button userData;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fargment_main);
        tabLayout=findViewById(R.id.tabLayout);
        userData=findViewById(R.id.userData);
        addFragment(FragmentGetData.newInstance(1));
        addFragment(FargmentUserDta.newInstance(1));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                if(tab.getPosition()==0)
                {
                    addFragment(FragmentGetData.newInstance(1));
                }
                else if(tab.getPosition()==1)
                {
                    addFragment(FargmentUserDta.newInstance(1));
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        userData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FargmentMainActivity.this, UserData.class);
                startActivity(intent);
            }
        });
    }
    public void addFragment(Fragment fragment)
    {
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }
}
package com.dmyaniuk.lb_9;

import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import constants.Constants;
import models.Contact;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<Contact> contacts;

    private RecyclerView recyclerView;
    private NavigationView navigationView;
    private FloatingActionButton floatingActionButton;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private Switch contactListType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.contactListType = findViewById(R.id.contactListType);
        this.contactListType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    MainActivity.this.initializeItems(Constants.GridType);
                } else {
                    MainActivity.this.initializeItems(Constants.LinearType);
                }
            }
        });

        this.initializeItems(Constants.LinearType);
    }

    private void initializeItems(int layoutType) {
        this.contacts = Constants.getMockedData();

        this.drawerLayout = findViewById(R.id.drawerLayout);
        this.navigationView = findViewById(R.id.contactNavigation);
        this.toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNav,
                R.string.closeNav
        );

        this.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        this.navigationView.setNavigationItemSelectedListener(this);

        this.recyclerView = findViewById(R.id.contactsRecycleView);
        this.floatingActionButton = findViewById(R.id.contactFloatButton);
        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Hello", Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView.Adapter adapter = new CustomRecyclerAdapter(this.contacts);
        this.recyclerView.setAdapter(adapter);

        if (layoutType == Constants.LinearType) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            this.recyclerView.setLayoutManager(layoutManager);
        } else if (layoutType == Constants.GridType) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            this.recyclerView.setLayoutManager(gridLayoutManager);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}

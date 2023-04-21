package ht.lutemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class TransferActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager;
    ViewPagerAdapter adapter;

    public static String[] TITLES = new String[] {"HOME", "TRAIN FIELD", "BATTLE FIELD"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);

        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tabs);

        setViewPagerAdapter();

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(TITLES[position]);
            }
        }).attach();
    }

    public void setViewPagerAdapter() {
        adapter = new ViewPagerAdapter(this);
        ArrayList<Fragment> fragmentList = new ArrayList<>();

        for (int i = 0; i < TITLES.length ; i++) {
            fragmentList.add(TransferFragment.newInstance(TITLES[i]));
        }
        adapter.setData(fragmentList);
        viewPager.setAdapter(adapter);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_transfer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        if (id == R.id.action_train) {
            startActivity(new Intent(this, TrainActivity.class));
            return true;
        }
        if (id == R.id.action_battle) {
            startActivity(new Intent(this, BattleActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
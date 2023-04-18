package ht.lutemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LutemonAdapter adapter;
    LutemonListAdapter listAdapter;
    LutemonViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        adapter = new LutemonAdapter(LutemonStorage.getInstance().getLutemons(), this);
//        recyclerView.setAdapter(adapter);

        listAdapter = new LutemonListAdapter(new LutemonListAdapter.ItemDiff());
        viewModel = new ViewModelProvider(this).get(LutemonViewModel.class);
        viewModel.getAllLutemons().observe(this, listAdapter::submitList);
        recyclerView.setAdapter(listAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_home) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        if (id == R.id.action_sort) {
            //do something
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
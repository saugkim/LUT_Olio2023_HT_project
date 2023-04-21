package ht.lutemon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class ListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    LutemonListAdapter listAdapter;
    LutemonViewModel viewModel;

    boolean isSorted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listAdapter = new LutemonListAdapter(new LutemonListAdapter.ItemDiff());
        viewModel = new ViewModelProvider(this).get(LutemonViewModel.class);

        viewModel.getAllLutemons().observe(this, listAdapter::submitList);
        recyclerView.setAdapter(listAdapter);
    }

    private void sortByColor() {
        viewModel.getSortedByColor().observe(this, listAdapter::submitList);
        recyclerView.setAdapter(listAdapter);
    }

    private void sortByXP() {
        viewModel.getSortedByXP().observe(this, listAdapter::submitList);
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_home) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        if (id == R.id.action_sort_by_COLOR) {
            sortByColor();
            return true;
        }
        if (id == R.id.action_sort_by_XP) {
            sortByXP();
            return true;
        }
        if (id == R.id.action_deleteAll){
            new LutemonRepository(getApplication()).deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
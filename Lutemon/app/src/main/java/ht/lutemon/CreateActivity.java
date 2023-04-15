package ht.lutemon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.ipsec.ike.exceptions.InvalidMajorVersionException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CreateActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    Button buttonCreate;
    EditText editText;
    FloatingActionButton fab;
    ImageView imageViewForTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        radioGroup = findViewById(R.id.radioGroup);
        buttonCreate = findViewById(R.id.btnCreate);
        editText = findViewById(R.id.etName);
        fab = findViewById(R.id.btnHome);
        imageViewForTest = findViewById(R.id.imageViewTemp);

        buttonCreate.setOnClickListener(v-> create());
        fab.setOnClickListener(v-> startActivity(new Intent(this, MainActivity.class)));
    }

    private void create() {
        Lutemon lutemon = null;
        String name = editText.getText().toString();

        ArrayList<RadioButton> buttons = new ArrayList<>();

        for(int i=0;i <radioGroup.getChildCount();i++) {
            buttons.add((RadioButton) radioGroup.getChildAt(i));
        }

        for (int i=0; i<buttons.size();i++) {
            if (buttons.get(i).isChecked()) {
                lutemon = createLutemon(i);
            }
        }

        if (lutemon == null)
            return;
        if (!name.isEmpty()) {
            lutemon.setName(name);
        }

        LutemonStorage.getInstance().addLutemon(lutemon);

        imageViewForTest.setImageResource(lutemon.getImageSource());
        imageViewForTest.setBackgroundColor(lutemon.getBackground_color());

        LutemonStorage.getInstance().save(this);
    }

    private Lutemon createLutemon(int index) {
        Lutemon lutemon;
        switch (index) {
            case 0:
                lutemon = new White();
                break;
            case 1:
                lutemon = new Green();
                break;
            case 2:
                lutemon = new Pink();
                break;
            case 3:
                lutemon = new Orange();
                break;
            case 4:
                lutemon = new Black();
                break;
            default:
                lutemon = null;
                break;
        }
        return lutemon;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create, menu);
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
        if (id == R.id.action_list){
            startActivity(new Intent(this, ListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
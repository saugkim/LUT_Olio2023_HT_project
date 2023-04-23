package ht.lutemon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
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
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class CreateActivity extends AppCompatActivity {

    private static String TAG = "ZZ Create Activity";
    RadioGroup radioGroup;
    Button buttonCreate;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        radioGroup = findViewById(R.id.radioGroup);
        buttonCreate = findViewById(R.id.btnCreate);
        editText = findViewById(R.id.etName);

        buttonCreate.setOnClickListener(v-> create());
    }

    /**
     * create new lutemon object based on the user input
     * user must select a type (color) and
     * user can give custom name if user wants at this stage
     * successfully created object will be inserted into database
     */
    private void create() {
        Lutemon lutemon = null;
        String name = editText.getText().toString();

        ArrayList<RadioButton> buttons = new ArrayList<>();

        for(int i=0;i <radioGroup.getChildCount();i++) {
            buttons.add((RadioButton) radioGroup.getChildAt(i));
        }

        for (int i=0; i < buttons.size();i++) {
            if (buttons.get(i).isChecked()) {
                lutemon = createLutemon(i);
            }
        }

        if (lutemon == null)
            return;

        if (!name.isEmpty()) {
            lutemon.setName(name);
        }

        new LutemonRepository(getApplication()).insert(lutemon);
        Snackbar.make(getWindow().getDecorView(), "New Lutemon: " + lutemon.getName() + " was created", Snackbar.LENGTH_SHORT).show();

        radioGroup.clearCheck();
        editText.getText().clear();
    }

    /**
     * @param teamId: teamId is radio-button array index
     * @return lutemon type (color)
     */
    private Lutemon createLutemon(int teamId) {
        switch (teamId) {
            case 0:
                return new White();
            case 1:
                return new Green();
            case 2:
                return new Pink();
            case 3:
                return new Orange();
            case 4:
                return new Black();
            default:
                return null;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
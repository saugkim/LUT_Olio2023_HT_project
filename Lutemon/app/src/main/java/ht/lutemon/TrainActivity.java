package ht.lutemon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.snackbar.Snackbar;

public class TrainActivity extends AppCompatActivity {

    private static final String TAG = "ZZ TrainActivity";
    Button startTrainButton;
    RadioGroup radioGroupTrain, radioGroupOpponent;
    RadioButton[] radioButtonOpponents;
    Lutemon opponent;
    Lutemon[] opponents;
    String selectedLutemonInfo;
    int selectedLutemonId;
    boolean win = false;
    LutemonViewModel viewModel;
    LutemonRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        startTrainButton = findViewById(R.id.btnStartBattle);
        radioGroupTrain = findViewById(R.id.radioGroupTrain);
        radioGroupOpponent = findViewById(R.id.opponent);

        viewModel = new LutemonViewModel(getApplication());
        repository = new LutemonRepository(getApplication());

        RadioButton rbWhite = findViewById(R.id.White);
        RadioButton rbGreen = findViewById(R.id.Green);
        RadioButton rbPink = findViewById(R.id.Pink);
        RadioButton rbOrange = findViewById(R.id.Orange);
        RadioButton rbBlack = findViewById(R.id.Black);
        radioButtonOpponents = new RadioButton[] {
                rbWhite, rbGreen, rbPink, rbOrange, rbBlack
        };
        opponents = new Lutemon[] {
                new White(), new Green(), new Pink(), new Orange(), new Black()
        };

        reset();

        viewModel.getHomeLutemons().observe(this, listOfLutemonsInTrain -> {
            for (Lutemon lutemon : listOfLutemonsInTrain) {
                createRadioButtons(lutemon.shortInfo(), lutemon.getId());
            }
        });

        startTrainButton.setOnClickListener(this::startTrain);
    }

    private void startTrain(View view){
        selectOpponent();
        selectLutemonFromList();
        Lutemon lutemonToTrain = getLutemonCopy();
        if (opponent == null || selectedLutemonId == -1 || lutemonToTrain == null) {
            Snackbar.make(this, view,"Select both parties", Snackbar.LENGTH_LONG).show();
            return;
        }

        win = true;
        //fighting algorithm here

        if (win) {
            int newXP = lutemonToTrain.getXp() + 1;
            repository.updateXP(selectedLutemonId, newXP);
        }

        reset();
    }

    private Lutemon getLutemonCopy() {
        Lutemon lutemonCopied = null;
        if (selectedLutemonInfo == null) {
            return null;
        }

        String name = selectedLutemonInfo.split("\\[")[0].trim();
        String[] stats = selectedLutemonInfo.split("\\[")[1].split(" ");
        String team = stats[0];
        int xp = Integer.parseInt(stats[8]);
        int cHealth = Integer.parseInt(stats[6].split("/")[0]);

        switch (team) {
            case "WHITE]":
                lutemonCopied = new White();
                break;
            case "GREEN]":
                lutemonCopied = new Green();
                break;
            case "PINK]":
                lutemonCopied = new Pink();
                break;
            case "ORANGE]":
                lutemonCopied = new Orange();
                break;
            case "BLACK]":
                lutemonCopied = new Black();
                break;
            default:
                lutemonCopied = new Lutemon();
                break;
        }
        lutemonCopied.setName(name);
        lutemonCopied.setXp(xp);
        lutemonCopied.setCurrentHealth(cHealth);
        return lutemonCopied;
    }

    private void createRadioButtons(String info, int id) {
        for (int i=0; i<radioGroupTrain.getChildCount();i++){
            RadioButton rb = (RadioButton) radioGroupTrain.getChildAt(i);
            if (rb.getId() == id) {
                radioGroupTrain.removeView(rb);
            }
        }
        RadioButton rb = new RadioButton(this);
        rb.setText(info);
        rb.setId(id);
        radioGroupTrain.addView(rb);
    }

    private void reset() {
        for(RadioButton rbButton:radioButtonOpponents) {
            rbButton.setChecked(false);
        }
        for (int i=0; i<radioGroupTrain.getChildCount();i++){
            RadioButton button = (RadioButton) radioGroupTrain.getChildAt(i);
            button.setChecked(false);
        }
        selectedLutemonInfo = null;
        selectedLutemonId = -1;
        opponent = null;
        win = false;
    }

    private void selectLutemonFromList() {
        for (int i=0; i<radioGroupTrain.getChildCount();i++) {
            RadioButton rbButton = (RadioButton) radioGroupTrain.getChildAt(i);
            if (rbButton.isChecked()) {
                selectedLutemonInfo = rbButton.getText().toString();
                selectedLutemonId = rbButton.getId();
            }
        }
   }

    private void selectOpponent() {
        for (int i = 0; i < radioButtonOpponents.length; i++) {
            if (radioButtonOpponents[i].isChecked()) {
                opponent = opponents[i];
            }
        }
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_train, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_home) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        }
        if (id == R.id.action_transfer) {
            startActivity(new Intent(this, TransferActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
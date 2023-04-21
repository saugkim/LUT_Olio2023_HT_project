package ht.lutemon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainActivity extends AppCompatActivity {

    private static final String TAG = "ZZ TrainActivity";
    Button startTrainButton;
    TextView textViewResult;
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

        viewModel = new LutemonViewModel(getApplication());
        repository = new LutemonRepository(getApplication());

        startTrainButton = findViewById(R.id.btnStartTrain);
        radioGroupTrain = findViewById(R.id.radioGroupTrain);
        radioGroupOpponent = findViewById(R.id.radioGroupOpponents);
        textViewResult = findViewById(R.id.tvTrainResult);

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

        viewModel.getTrainLutemons().observe(this, listOfLutemonsInTrain -> {
            for (Lutemon lutemon : listOfLutemonsInTrain) {
                createRadioButtons(lutemon.shortInfo(), lutemon.getId());
            }
        });

        startTrainButton.setOnClickListener(this::startTrain);
    }

    private void startTrain(View view){
        selectOpponent();
        selectLutemonFromRadioGroup();
        Lutemon lutemonToTrain = repository.getCloned(selectedLutemonInfo);

        if (opponent == null || selectedLutemonId == -1 || lutemonToTrain == null) {
            Snackbar.make(this, view,"Select both parties", Snackbar.LENGTH_LONG).show();
            return;
        }
        opponent.setXp(lutemonToTrain.getXp());

        fight(lutemonToTrain, opponent);
    }

    private void fight(Lutemon A, Lutemon B) {
        boolean myTurn = true;

        StringBuilder sb = new StringBuilder();
        sb.append(A.shortInfo());
        sb.append("\n***  train against opponent  ***\n ");
        sb.append(B.shortInfo());
        sb.append("\n\n");

        while(true) {
            if (myTurn) {
                A.attack(B);
                myTurn = false;
                if (B.getCurrentHealth() <= 0) {
                    win = true;
                    break;
                }
            } else {
                B.attack(A);
                myTurn = true;
                if(A.getCurrentHealth() <= 0) {
                    break;
                }
            }
        }

        String result = win ? "YOU WON AND GOT 1 XP\n" : "OPPONENT WON\n";
        sb.append(result);

        textViewResult.setText(sb.toString());
        if (win){
            int newXP = A.getXp() + 1;
            int cHealth = A.getCurrentHealth();
//            repository.updateXP(selectedLutemonId, newXP);
            repository.updateXpHp(selectedLutemonId, newXP, cHealth);
        }
        reset();
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
        radioGroupOpponent.clearCheck();
        radioGroupTrain.clearCheck();
        selectedLutemonInfo = null;
        selectedLutemonId = -1;
        opponent = null;
        win = false;
    }

    private void selectLutemonFromRadioGroup() {
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
        int id = item.getItemId();

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
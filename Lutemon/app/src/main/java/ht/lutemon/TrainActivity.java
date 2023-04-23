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
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

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

    /**
     * null check of selected lutemon and opponent
     * if everything is fine, start to fight
     * @param view: for Snackbar...
     */
    private void startTrain(View view){
        selectOpponent();
        selectLutemonFromRadioGroup();
        Lutemon lutemonToTrain = repository.getCloned(selectedLutemonInfo);

        if (opponent == null || selectedLutemonId == -1 || lutemonToTrain == null) {
            Snackbar.make(this, view,"Select both parties", Snackbar.LENGTH_LONG).show();
            return;
        }
        opponent.setXp(lutemonToTrain.getXp());
        opponent.setCurrentHealth(opponent.getMaxHealth());

        fight(lutemonToTrain, opponent);
    }

    /**
     * fight algorithm, same as battle,
     * but here no random start or no multiple attack
     * opponent lutemon has same level of xp as user lutemon
     * opponent has full health at begin
     * user lutemon attacks first
     * if user lutemon won, get 1 extra xp, no xp gain from losing
     * update lutemon with new stats (xp and currentHealth)
     * show result in text field
     * user need to transfer lutemon to home arena to heal up
     * @param A: user lutemon
     * @param B: opponent lutemon
     */
    private void fight(Lutemon A, Lutemon B) {
        boolean myTurn = true;
        boolean win = false;

        StringBuilder sb = new StringBuilder();
        sb.append(A.shortInfo());
        sb.append("\n***  train against  ***\nopponent ");
        sb.append(String.format(Locale.getDefault(), "[%s] CP %d DP %d HP %d/%d XP %d",
                B.getTeam(), B.getAttack(), B.getDefence(), B.getCurrentHealth(), B.getMaxHealth(), B.getXp()));
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

        String result = win ? "YOU WON AND GOT 1 XP" : "OPPONENT WON";
        sb.append(result);
        sb.append("\nTransfer to HOME to heal up lutemon");

        textViewResult.setText(sb.toString());

        int newXP = win ? A.getXp() + 1 : A.getXp() ;
        int cHealth = A.getCurrentHealth();
        repository.updateXpHp(selectedLutemonId, newXP, cHealth);

        reset();
    }

    /**
     * create UI element for lutemons in train arena
     * set ui is same as lutemon id and ui text is lutemon short info
     * @param info: lutemon short info
     * @param id: lutemon unique id
     */
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
    }

    /**
     * user select lutemon using radio group
     * index and info from selected ui element
     * these are used to clone lutemon
     */
    private void selectLutemonFromRadioGroup() {
        for (int i=0; i<radioGroupTrain.getChildCount();i++) {
            RadioButton rbButton = (RadioButton) radioGroupTrain.getChildAt(i);
            if (rbButton.isChecked()) {
                selectedLutemonInfo = rbButton.getText().toString();
                selectedLutemonId = rbButton.getId();
            }
        }
   }

    /**
     * user selects opponent using radio group (color)
     */
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
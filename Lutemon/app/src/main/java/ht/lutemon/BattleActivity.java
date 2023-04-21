package ht.lutemon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class BattleActivity extends AppCompatActivity {
    private final static String TAG = "ZZ BattleActivity";
    Button startBattleButton;
    LinearLayout layoutForCheckBoxes;
    TextView resultBattleView;
    LutemonRepository repository;
    LutemonViewModel viewModel;
    ArrayList<Integer> selectedIndexes;
    ArrayList<String> selectedInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        repository = new LutemonRepository(getApplication());
        viewModel = new LutemonViewModel(getApplication());

        startBattleButton = findViewById(R.id.btnStartBattle);
        layoutForCheckBoxes = findViewById(R.id.layoutForBattleLutemons);
        resultBattleView = findViewById(R.id.tvBattleResult);

        reset();
        resultBattleView.setText(R.string.default_message_for_battle);

        viewModel.getBattleLutemons().observe(this, listOfLutemonsInBattle ->{
            for(Lutemon lutemon : listOfLutemonsInBattle) {
                createCheckBox(lutemon.shortInfo(), lutemon.getId());
            }
        });

        startBattleButton.setOnClickListener(this::startBattle);
    }

    private void startBattle(View view) {
        resultBattleView.setText(R.string.default_message_for_battle);
        selectLutemonsToFightEachOther();
        if (selectedIndexes.size() != 2) {
            Snackbar.make(this, view, "SELECT TWO LUTEMONS", Snackbar.LENGTH_LONG).show();
            reset();
            return;
        }

        Lutemon A = repository.getCloned(selectedInfo.get(0));
        Lutemon B = repository.getCloned(selectedInfo.get(1));
        if (A == null || B == null) {
            Log.d(TAG, "Lutemons are not object of Lutemon class");
            return;
        }

        A.setId(selectedIndexes.get(0));
        B.setId(selectedIndexes.get(1));

        fight(A, B);
        reset();
    }
    private void fight(Lutemon A, Lutemon B) {

        final Random random = new Random();
        boolean onQueue = (random.nextInt() % 2) == 0;

        Lutemon winner = A;
        Lutemon loser = B;

        StringBuilder sb = new StringBuilder();

        while(true) {
            if (onQueue) {
                String ret = A.attack(B);
                sb.append(ret);
                onQueue = false;
                if (B.getCurrentHealth() <= 0) {
                    sb.append(String.format(Locale.getDefault(),
                            "%s(%s) is dead.\n\n", B.getTeam(), B.getName()));
                    break;
                } else {
                    sb.append(String.format(Locale.getDefault(),
                            "%s(%s) manages to escape death.\n\n", B.getTeam(), B.getName()));
                }
            } else {
                String ret = B.attack(A);
                sb.append(ret);
                onQueue = true;
                if(A.getCurrentHealth() <= 0) {
                    sb.append(String.format(Locale.getDefault(),
                            "%s(%s) is dead.\n\n", A.getTeam(), A.getName()));
                    winner = B;
                    loser = A;
                    break;
                } else {
                    sb.append(String.format(Locale.getDefault(),
                            "%s(%s) manages to escape death.\n\n", A.getTeam(), A.getName()));
                }
            }
        }

        resultBattleView.setText(sb.toString());

        int xp_ = winner.getXp() + 1;
        int cHealth_ = winner.getCurrentHealth();

        repository.updateXpHp(winner.getId(), xp_, cHealth_);

        for (int i = 0; i < layoutForCheckBoxes.getChildCount(); i++) {
            CheckBox cb = (CheckBox) layoutForCheckBoxes.getChildAt(i);
            if (loser.getId() == cb.getId()) {
                layoutForCheckBoxes.removeView(cb);
            }
        }
        repository.deleteById(loser.getId());
    }

    private void reset() {
        for (int i = 0; i < layoutForCheckBoxes.getChildCount(); i++) {
            CheckBox cb = (CheckBox) layoutForCheckBoxes.getChildAt(i);
            cb.setChecked(false);
        }
        selectedInfo = new ArrayList<>();
        selectedIndexes = new ArrayList<>();
    }
    private void createCheckBox(String info, int id){
        for (int i = 0; i < layoutForCheckBoxes.getChildCount(); i++) {
            CheckBox cb = (CheckBox) layoutForCheckBoxes.getChildAt(i);
            if (id == cb.getId()) {
                layoutForCheckBoxes.removeView(cb);
            }
        }
        CheckBox cb = new CheckBox(this);
        cb.setId(id);
        cb.setText(info);
        layoutForCheckBoxes.addView(cb);
    }

    private void selectLutemonsToFightEachOther() {
        for (int i = 0; i < layoutForCheckBoxes.getChildCount(); i++) {
            CheckBox cb = (CheckBox) layoutForCheckBoxes.getChildAt(i);
            if (cb.isChecked()) {
                selectedIndexes.add(cb.getId());
                selectedInfo.add(cb.getText().toString());
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_battle, menu);
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


    //deleting
    private Lutemon getCloned(String infoText) {
        Lutemon lutemon;
        if (infoText == null) {
            return null;
        }
        String name = infoText.split("\\[")[0].trim();
        String[] stats = infoText.split("\\[")[1].split(" ");
        String team = stats[0];
        int xp = Integer.parseInt(stats[8]);
        int cHealth = Integer.parseInt(stats[6].split("/")[0]);

        switch (team) {
            case "WHITE]":
                lutemon = new White();
                break;
            case "GREEN]":
                lutemon = new Green();
                break;
            case "PINK]":
                lutemon = new Pink();
                break;
            case "ORANGE]":
                lutemon = new Orange();
                break;
            case "BLACK]":
                lutemon = new Black();
                break;
            default:
                lutemon = new Lutemon();
                break;
        }
        lutemon.setName(name);
        lutemon.setXp(xp);
        lutemon.setCurrentHealth(cHealth);
        return lutemon;
    }
}
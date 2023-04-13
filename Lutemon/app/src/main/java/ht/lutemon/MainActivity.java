package ht.lutemon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonCreate, buttonTransfer, buttonBattle, buttonTrain, buttonList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonCreate = findViewById(R.id.buttonCreate);
        buttonTransfer = findViewById(R.id.buttonTransfer);
        buttonTrain = findViewById(R.id.buttonTrain);
        buttonBattle = findViewById(R.id.buttonBattle);
        buttonList = findViewById(R.id.buttonList);

        buttonBattle.setOnClickListener(v-> startActivity(new Intent(this, BattleActivity.class)));
        buttonCreate.setOnClickListener(v-> startActivity(new Intent(this, CreateActivity.class)));
        buttonTrain.setOnClickListener(v->startActivity(new Intent(this, TrainActivity.class)));
        buttonTransfer.setOnClickListener(v->startActivity(new Intent(this, TransferActivity.class)));
        buttonList.setOnClickListener(v-> startActivity(new Intent(this, ListActivity.class)));
    }
}
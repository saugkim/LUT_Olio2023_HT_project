package ht.lutemon;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class White extends Lutemon implements Serializable {
    public White() {
        super();
        background_color = 0xFFF6F6FB;
        imageSource = R.mipmap.white_back;
        name = "Snow White";
        team = Team.WHITE.toString();
        attack = 5;
        defence = 4;
        maxHealth = 20;
        currentHealth = maxHealth;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s [%s]\n\n%s", name, team, this.statMultilineInfo());
    }
}

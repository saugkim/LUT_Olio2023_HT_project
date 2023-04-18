package ht.lutemon;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Pink extends Lutemon implements Serializable {

    public Pink() {
        super();
        background_color = 0xFFF6D7D7;
        imageSource = R.mipmap.pink_back;
        name = "Mythic Pinky";
        team = Team.PINK.toString();
        attack = 7;
        defence = 2;
        maxHealth = 18;
        currentHealth = maxHealth;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s [%s]\n\n%s", name, team, this.statMultilineInfo());
    }
}

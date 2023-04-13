package ht.lutemon;

import androidx.annotation.NonNull;

public class Pink extends Lutemon{

    public Pink() {
        super();
        background_color = 0xFFF6D7D7;
        imageSource = R.mipmap.pink_back;
        name = "Mythic Pinky";
        team = "pink";
        attack = 7;
        defence = 2;
        maxHealth = 18;
        currentHealth = maxHealth;
        xp = 0;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s [%s]\n\n%s", name, team, this.statString());
    }
}

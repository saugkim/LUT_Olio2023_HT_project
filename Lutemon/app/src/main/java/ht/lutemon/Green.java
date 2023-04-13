package ht.lutemon;

import androidx.annotation.NonNull;

public class Green extends Lutemon{
    public Green() {
        super();
        background_color = 0xFFEDF3EA;
        imageSource = R.mipmap.green_back;
        name = "Rare Green";
        team = "green";
        attack = 6;
        defence = 3;
        maxHealth = 19;
        currentHealth = maxHealth;
        xp = 0;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s [%s]\n\n%s", name, team, this.statString());
    }
}

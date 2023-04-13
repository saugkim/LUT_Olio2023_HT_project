package ht.lutemon;

import androidx.annotation.NonNull;

public class Black extends Lutemon {

    public Black () {
        super();
        background_color = 0xFFE8E6E6;
        imageSource = R.mipmap.black_back;
        name = "Legendary Musta";
        team = "black";
        attack = 9;
        defence = 0;
        maxHealth = 16;
        xp = 0;
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("%s [%s]\n\n%s", name, team, this.statString());
    }

}

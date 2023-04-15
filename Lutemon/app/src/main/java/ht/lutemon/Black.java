package ht.lutemon;


import java.io.Serializable;

public class Black extends Lutemon implements Serializable {

    public Black () {
        super();
        background_color = 0xFFE8E6E6;
        imageSource = R.mipmap.black_back;
        name = "Legendary Musta";
        team = Team.BLACK.name();
        attack = 9;
        defence = 0;
        maxHealth = 16;
    }
}

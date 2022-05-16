package com.example.oopprojekt.malemang;


import java.util.ArrayList;
import java.util.Arrays;

public class Oda extends Nupp {

    public Oda(char varv, int[] asukoht) {
        this.varv = varv;
        this.asukoht = asukoht;
        this.un_char = (varv != 'v')? '\u2657' : '\u265D';
    }

    public ArrayList<int[]> kaigud(Malelaud malelaud) {
        ArrayList<int[]> tulemus = new ArrayList<>();

        //Üles-paremale
        for (int i = asukoht[0] + 1, j = asukoht[1] + 1;
             i < 8 && j < 8;
             i++, j++) {
            if (malelaud.getLaud()[i][j] == null) tulemus.add(new int[]{i, j});
            else if (malelaud.getLaud()[i][j].getVarv() != varv) {
                tulemus.add(new int[]{i, j});
                break;
            } else if (malelaud.getLaud()[i][j].getVarv() == varv) break;
        }

        //Üles-vasakule
        for (int i = asukoht[0] + 1, j = asukoht[1] - 1;
             i < 8 && j > -1;
             i++, j--) {
            if (malelaud.getLaud()[i][j] == null) tulemus.add(new int[]{i, j});
            else if (malelaud.getLaud()[i][j].getVarv() != varv) {
                tulemus.add(new int[]{i, j});
                break;
            } else if (malelaud.getLaud()[i][j].getVarv() == varv) break;
        }

        //Alla-vasakule
        for (int i = asukoht[0] - 1, j = asukoht[1] - 1;
             i > -1 && j > -1;
             i--, j--) {
            if (malelaud.getLaud()[i][j] == null) tulemus.add(new int[]{i, j});
            else if (malelaud.getLaud()[i][j].getVarv() != varv) {
                tulemus.add(new int[]{i, j});
                break;
            } else if (malelaud.getLaud()[i][j].getVarv() == varv) break;
        }

        //Alla-paremale
        for (int i = asukoht[0] - 1, j = asukoht[1] + 1;
             i > -1 && j < 8;
             i--, j++) {
            if (malelaud.getLaud()[i][j] == null) tulemus.add(new int[]{i, j});
            else if (malelaud.getLaud()[i][j].getVarv() != varv) {
                tulemus.add(new int[]{i, j});
                break;
            } else if (malelaud.getLaud()[i][j].getVarv() == varv) break;
        }

        return tulemus;

    }

    @Override
    public String toString() {
        return varv + "Od";
    }
}



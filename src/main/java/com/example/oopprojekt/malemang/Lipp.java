package com.example.oopprojekt.malemang;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Lipp extends Nupp{

    public Lipp(char varv,int[] asukoht) {
        this.varv = varv;
        this.asukoht=asukoht;
        this.un_char = (varv != 'v')? '\u2655' : '\u265B';
    }


    @Override
    public ArrayList<int[]> kaigud(Malelaud malelaud) {

        Oda abi_oda = new Oda(varv, asukoht);
        Vanker abi_vanker = new Vanker(varv, asukoht);

        ArrayList<int[]> tulemus = new ArrayList<>(abi_oda.kaigud(malelaud));
        tulemus.addAll(abi_vanker.kaigud(malelaud));

        return tulemus;
    }

    @Override
    public String toString() {
        return varv + "Li";
    }
    @Override
    public String KodeeriNupp(){
        String tulemus = toString();
        tulemus += "," + asukoht[0] + "," + asukoht[1];
        return tulemus;
    }
}

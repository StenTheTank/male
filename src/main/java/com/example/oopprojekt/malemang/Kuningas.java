package com.example.oopprojekt.malemang;

import java.util.ArrayList;
import java.util.Arrays;

public class Kuningas extends Nupp{
    private boolean on_liigutatud;
    @Override
    public void setAsukoht(int[] asukoht) {
        super.setAsukoht(asukoht);
        on_liigutatud=true;
    }
    public boolean isOn_liigutatud() {
        return on_liigutatud;
    }
    public Kuningas(char varv, int[] asukoht, boolean on_liigutatud) {
        this.varv = varv;
        this.asukoht = asukoht;
        this.on_liigutatud = on_liigutatud;
        this.un_char = (varv != 'v')? '\u2654' : '\u265A';
    }
    public boolean abikuningas(int rida,int veerg, Malelaud malelaud){
        if (rida<0 || veerg<0 ) return false;
        if (rida>7 || veerg > 7) return false;
        if (malelaud.getLaud()[rida][veerg]==null) return true;
        else return malelaud.getLaud()[rida][veerg].getVarv() != varv;
    }
    @Override
    public ArrayList<int[]> kaigud(Malelaud malelaud) {
        ArrayList<int[]> tulemus =vangerdus(malelaud);
        int rida=asukoht[0];int veerg=asukoht[1];
        if (abikuningas(rida+1,veerg-1,malelaud)) tulemus.add(new int[]{rida+1,veerg-1});
        if (abikuningas(rida+1,veerg,malelaud)) tulemus.add(new int[]{rida+1,veerg});
        if (abikuningas(rida+1,veerg+1,malelaud)) tulemus.add(new int[]{rida+1,veerg+1});
        if (abikuningas(rida,veerg+1,malelaud)) tulemus.add(new int[]{rida,veerg+1});
        if (abikuningas(rida,veerg-1,malelaud)) tulemus.add(new int[]{rida,veerg-1});
        if (abikuningas(rida-1,veerg-1,malelaud)) tulemus.add(new int[]{rida-1,veerg-1});
        if (abikuningas(rida-1,veerg,malelaud)) tulemus.add(new int[]{rida-1,veerg});
        if (abikuningas(rida-1,veerg+1,malelaud)) tulemus.add(new int[]{rida-1,veerg+1});
        return tulemus;
    }
    public static boolean sisaldub(ArrayList<int[]>hulk,int[]element){
        for (int[] ints : hulk) {
            if(Arrays.toString(ints).equals(Arrays.toString(element))) return true;
        }
        return false;
    }
    public ArrayList<int[]> vangerdus(Malelaud laud){
        if (on_liigutatud){
            return new ArrayList<>();
        }
        Vanker v1 = (laud.getLaud()[asukoht[0]][7] != null && laud.getLaud()[asukoht[0]][7].getClass() == Vanker.class)? (Vanker) laud.getLaud()[asukoht[0]][7] : null;
        Vanker v2 = (laud.getLaud()[asukoht[0]][0] != null && laud.getLaud()[asukoht[0]][0].getClass() == Vanker.class)? (Vanker) laud.getLaud()[asukoht[0]][0] : null;
        boolean valge_kaik;
        ArrayList<int[]>vastus = new ArrayList<>();
        valge_kaik = varv == 'v';
        ArrayList<int[]>v_kaigud;
        if (laud.getLaud()[asukoht[0]][5] == null && laud.getLaud()[asukoht[0]][6] == null) {
            if (v1 != null && !on_liigutatud && !v1.isOn_liigutatud()) {
                v_kaigud = laud.vastasekaigud(valge_kaik);
                if (!(sisaldub(v_kaigud,new int[]{asukoht[0], 5}) || sisaldub(v_kaigud,new int[]{asukoht[0], 4}))) {
                    vastus.add(new int[]{asukoht[0], 6});
                }
            }
        }
        if (laud.getLaud()[asukoht[0]][1]==null && laud.getLaud()[asukoht[0]][2]==null && laud.getLaud()[asukoht[0]][3]==null) {
            if (v2 != null && !on_liigutatud && !v2.isOn_liigutatud()) {
                v_kaigud = laud.vastasekaigud(valge_kaik);
                if (!(sisaldub(v_kaigud,new int[]{asukoht[0], 3}) || sisaldub(v_kaigud,new int[]{asukoht[0], 4}))) {
                    vastus.add(new int[]{asukoht[0], 2});
                }
            }
        }
        return vastus;
    }
    @Override
    public String toString() {
        return varv + "Ku";
    }

    @Override
    public String KodeeriNupp(){
        String tulemus = toString();
        tulemus += "," + asukoht[0] + "," + asukoht[1];
        tulemus += (on_liigutatud)? ",1" : ",0";
        return tulemus;
    }

}

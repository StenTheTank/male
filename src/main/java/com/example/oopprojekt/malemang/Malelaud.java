package com.example.oopprojekt.malemang;

import java.util.ArrayList;
import java.util.Arrays;

public class Malelaud{
    private final Nupp[][] laud;
    private static final char[] tahestik = {'a','b','c','d','e','f','g','h'};
    public Malelaud() {
        laud = new Nupp[8][8];

        //Valged etturid
            for (int i = 0; i < 8; i++) {
                laud[1][i] = new Ettur('v', new int[]{1, i},false);
            }

        //Mustad etturid
            for (int i = 0; i < 8; i++) {
                laud[6][i] = new Ettur('m', new int[]{6, i},false);
            }

        //Valged vankrid
            laud[0][0] = new Vanker('v', new int[]{0, 0});
            laud[0][7] = new Vanker('v', new int[]{0, 7});

        //Mustad vankrid
            laud[7][0] = new Vanker('m', new int[]{7, 0});
            laud[7][7] = new Vanker('m', new int[]{7, 7});

        //Valged ratsud
            laud[0][1]=new Ratsu('v', new int[]{0, 1});
            laud[0][6]=new Ratsu('v', new int[]{0, 6});

        //Mustad ratsud
            laud[7][1]=new Ratsu('m', new int[]{7, 1});
            laud[7][6]=new Ratsu('m', new int[]{7, 6});

        //Valged odad
            laud[0][2]=new Oda('v', new int[]{0, 2});
            laud[0][5]=new Oda('v', new int[]{0, 5});

        //Mustad odad
            laud[7][2]=new Oda('m', new int[]{7, 2});
            laud[7][5]=new Oda('m', new int[]{7, 5});

        //Lipud
            laud[0][3] = new Lipp('v', new int[]{0, 3});
            laud[7][3] = new Lipp('m', new int[]{7, 3});

        //Kuningad
            laud[0][4]=new Kuningas('v', new int[]{0, 4},false);
            laud[7][4]=new Kuningas('m', new int[]{7, 4},false);
    }

    public Nupp[][] getLaud() {
        return laud;
    }
    public Malelaud(Malelaud teinelaud){
        this.laud=new Nupp[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(teinelaud.getLaud()[i][j]!=null){
                    if (teinelaud.getLaud()[i][j].getClass() == Ettur.class){
                        Ettur et = (Ettur) teinelaud.getLaud()[i][j];
                        this.laud[i][j] = new Ettur(teinelaud.getLaud()[i][j].getVarv(), new int[]{i,j},et.isOn_liigutatud(), et.getK??idud());
                    }
                    if (teinelaud.getLaud()[i][j].getClass() == Ratsu.class)
                        this.laud[i][j] = new Ratsu(teinelaud.getLaud()[i][j].getVarv(), new int[]{i, j});
                    if (teinelaud.getLaud()[i][j].getClass() == Oda.class)
                        this.laud[i][j] = new Oda(teinelaud.getLaud()[i][j].getVarv(), new int[]{i, j});
                    if (teinelaud.getLaud()[i][j].getClass() == Lipp.class)
                        this.laud[i][j] = new Lipp(teinelaud.getLaud()[i][j].getVarv(), new int[]{i, j});
                    if (teinelaud.getLaud()[i][j].getClass() == Kuningas.class){
                        Kuningas kunn = (Kuningas) teinelaud.getLaud()[i][j];
                        this.laud[i][j]=new Kuningas(teinelaud.getLaud()[i][j].getVarv(),new int[]{i, j},kunn.isOn_liigutatud());
                    }
                    if (teinelaud.getLaud()[i][j].getClass() == Vanker.class){
                        this.laud[i][j] = new Vanker(teinelaud.getLaud()[i][j].getVarv(), new int[]{i, j});
                    }
                }
            }
        }
    }

    /**
     *
     * @param asukoht liigutatava nupu esialgne asukoht
     * @param sihtasukoht liigutatava nupu sihtasukoht
     */
    public void liiguta(int[] asukoht, int[] sihtasukoht){
        if (laud[asukoht[0]][asukoht[1]]==null){
            return;
        }
        if (laud[asukoht[0]][asukoht[1]].getClass() == Kuningas.class){
            if (asukoht[1]==4 && sihtasukoht[1]==6){
                liiguta(new int[]{asukoht[0],7},new int[]{asukoht[0],5});
            }
            if (asukoht[1]==4 && sihtasukoht[1]==2){
                liiguta(new int[]{asukoht[0],0},new int[]{asukoht[0],3});
            }
        }
        //en passanti if
        if (laud[asukoht[0]][asukoht[1]].getClass() == Ettur.class){
            if (laud[asukoht[0]][asukoht[1]].getVarv() == 'v'){
                if (Peaklass.sisaldub(laud[asukoht[0]][asukoht[1]].kaigud(this), (new int[]{sihtasukoht[0], sihtasukoht[1], 1})))
                    laud[sihtasukoht[0]-1][sihtasukoht[1]] = null; //en passant v??tab nupu ??ra
            }
            else if (laud[asukoht[0]][asukoht[1]].getVarv() == 'm'){
                if (Peaklass.sisaldub(laud[asukoht[0]][asukoht[1]].kaigud(this), (new int[]{sihtasukoht[0], sihtasukoht[1], 1})))
                    laud[sihtasukoht[0]+1][sihtasukoht[1]] = null; //en passant v??tab nupu ??ra
            }
        }

        laud[sihtasukoht[0]][sihtasukoht[1]] = laud[asukoht[0]][asukoht[1]];
        laud[asukoht[0]][asukoht[1]] = null;
        if (laud[sihtasukoht[0]][sihtasukoht[1]].getClass() == Ettur.class && (sihtasukoht[0]-asukoht[0] == 2 || sihtasukoht[0]-asukoht[0] == -2))
            laud[sihtasukoht[0]][sihtasukoht[1]].setAsukoht(new int[]{sihtasukoht[0], sihtasukoht[1]}, true);
        else
            laud[sihtasukoht[0]][sihtasukoht[1]].setAsukoht(new int[]{sihtasukoht[0], sihtasukoht[1]});
    }

    public void v??ljasta(){
        System.out.println("   a   b   c   d   e   f   g   h");
        System.out.println("8 " + rida_s??nena(7) + "8");
        System.out.println("7 " + rida_s??nena(6) + "7");
        System.out.println("6 " + rida_s??nena(5) + "6");
        System.out.println("5 " + rida_s??nena(4) + "5");
        System.out.println("4 " + rida_s??nena(3) + "4");
        System.out.println("3 " + rida_s??nena(2) + "3");
        System.out.println("2 " + rida_s??nena(1) + "2");
        System.out.println("1 " + rida_s??nena(0) + "1");
        System.out.println("   a   b   c   d   e   f   g   h");
    }

    public String rida_s??nena(int rea_indeks){
        StringBuilder tulemus = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            if (laud[rea_indeks][i] != null)
                tulemus.append(laud[rea_indeks][i].toString()).append(" ");
            else
                tulemus.append("*** ");
        }
        return tulemus.toString();
    }
    public void v??ljasta_char(){
        System.out.println("  " + rida_s??nena_char(-1));
        System.out.println("8 " + rida_s??nena_char(7) + " 8");
        System.out.println("7 " + rida_s??nena_char(6) + " 7");
        System.out.println("6 " + rida_s??nena_char(5) + " 6");
        System.out.println("5 " + rida_s??nena_char(4) + " 5");
        System.out.println("4 " + rida_s??nena_char(3) + " 4");
        System.out.println("3 " + rida_s??nena_char(2) + " 3");
        System.out.println("2 " + rida_s??nena_char(1) + " 2");
        System.out.println("1 " + rida_s??nena_char(0) + " 1");
        System.out.println("  " + rida_s??nena_char(-1));
    }
    public String rida_s??nena_char(int rea_indeks){
        StringBuilder tulemus = new StringBuilder();
        char lai_tyhik = '\u2003';
        if (rea_indeks == -1){
            for (char c : tahestik) {
                tulemus.append(lai_tyhik).append(c).append(" ");
            }

        }else {
            for (int i = 0; i < 8; i++) {
                if (laud[rea_indeks][i] != null)
                    tulemus.append(laud[rea_indeks][i].toCharString());
                else
                    tulemus.append("[").append(lai_tyhik).append("]");
            }
        }
        return tulemus.toString();
    }

    public void ettur_muutub(String uus, Ettur ettur){
        Nupp malend = switch (uus) {
            case "Lipp" -> new Lipp(ettur.varv, ettur.asukoht);
            case "Ratsu" -> new Ratsu(ettur.varv, ettur.asukoht);
            case "Oda" -> new Oda(ettur.varv, ettur.asukoht);
            default -> new Vanker(ettur.varv, ettur.asukoht);
        };
        laud[ettur.getAsukoht()[0]][ettur.getAsukoht()[1]] = malend;
    }

    public ArrayList<int[]> vastasekaigud( boolean valge_kaik) {
        ArrayList<int[]> vastus = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (laud[i][j]!=null) {
                    if (laud[i][j].getClass() != Kuningas.class) {
                        if (valge_kaik) {
                            if (laud[i][j].getVarv() == 'm') {
                                ArrayList<int[]> ajutine = laud[i][j].kaigud(this);
                                vastus.addAll(ajutine);
                            }
                        } else {
                            if (laud[i][j].getVarv() == 'v') {
                                ArrayList<int[]> ajutine = laud[i][j].kaigud(this);
                                vastus.addAll(ajutine);
                            }
                        }
                    }
                }
            }
        }
        return vastus;
    }

    @Override
    public String toString() {
        return "Malelaud{" +
                "laud=" + Arrays.deepToString(laud) +
                '}';
    }

    public String kodeerilaud(){
        String tulemus = "";
        for (int i = 0; i < laud.length; i++) {
            for (int j = 0; j < laud.length; j++) {
                if (laud[i][j] == null)
                    tulemus += ";";
                else
                    tulemus += ";" + laud[i][j].KodeeriNupp();
            }
        }
        return tulemus;
    }
    public Malelaud(String s){
        String[] info = s.split(";");
        if (info.length != 64)
            throw new IllegalArgumentException("Sellest s??nest ei saa malelauda moodustada");
        this.laud = new Nupp[8][8];
        for (int i = 0; i < info.length; i++) {
            if (info[i].equals("")){
                this.laud[i / 8][i % 8] = null;
                continue;
            }
            String[] nupu_info = info[i].split(",");
            String klass = nupu_info[0].substring(1);
            char v??rv = nupu_info[0].charAt(0);
            int x = Integer.parseInt(nupu_info[1]);
            int y = Integer.parseInt(nupu_info[2]);
            Nupp vaadeldav;
            if(nupu_info.length == 3){
                switch (klass) {
                    case "Ra" -> vaadeldav = new Ratsu(v??rv, new int[]{x, y});
                    case "Od" -> vaadeldav = new Oda(v??rv, new int[]{x, y});
                    default -> vaadeldav = new Lipp(v??rv, new int[]{x, y});
                }
            }else if (nupu_info.length == 4){
                if (klass.equals("Ku"))
                    vaadeldav = new Kuningas(v??rv, new int[]{x, y}, Integer.parseInt(nupu_info[3]) == 1);
                else
                    vaadeldav = new Vanker(v??rv, new int[]{x, y}, Integer.parseInt(nupu_info[3]) == 1);
            }else
                vaadeldav = new Ettur(v??rv, new int[]{x, y}, Integer.parseInt(nupu_info[3]) == 1, Integer.parseInt(nupu_info[4]));
            this.laud[i / 8][i % 8] = vaadeldav;
        }
    }
}

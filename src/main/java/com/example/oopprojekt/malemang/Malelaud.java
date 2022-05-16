package com.example.oopprojekt.malemang;

import java.util.Arrays;

public class Malelaud{
    private final Nupp[][] laud;
    private final char[] tahestik={'a','b','c','d','e','f','g','h'};


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
                        this.laud[i][j] = new Ettur(teinelaud.getLaud()[i][j].getVarv(), new int[]{i,j},et.isOn_liigutatud(), et.getKäidud());
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
                    laud[sihtasukoht[0]-1][sihtasukoht[1]] = null; //en passant võtab nupu ära
            }
            else if (laud[asukoht[0]][asukoht[1]].getVarv() == 'm'){
                if (Peaklass.sisaldub(laud[asukoht[0]][asukoht[1]].kaigud(this), (new int[]{sihtasukoht[0], sihtasukoht[1], 1})))
                    laud[sihtasukoht[0]+1][sihtasukoht[1]] = null; //en passant võtab nupu ära
            }
        }

        laud[sihtasukoht[0]][sihtasukoht[1]] = laud[asukoht[0]][asukoht[1]];
        laud[asukoht[0]][asukoht[1]] = null;
        if (laud[sihtasukoht[0]][sihtasukoht[1]].getClass() == Ettur.class && (sihtasukoht[0]-asukoht[0] == 2 || sihtasukoht[0]-asukoht[0] == -2))
            laud[sihtasukoht[0]][sihtasukoht[1]].setAsukoht(new int[]{sihtasukoht[0], sihtasukoht[1]}, true);
        else
            laud[sihtasukoht[0]][sihtasukoht[1]].setAsukoht(new int[]{sihtasukoht[0], sihtasukoht[1]});
    }

    public void väljasta(){
        System.out.println("   a   b   c   d   e   f   g   h");
        System.out.println("8 " + rida_sõnena(7) + "8");
        System.out.println("7 " + rida_sõnena(6) + "7");
        System.out.println("6 " + rida_sõnena(5) + "6");
        System.out.println("5 " + rida_sõnena(4) + "5");
        System.out.println("4 " + rida_sõnena(3) + "4");
        System.out.println("3 " + rida_sõnena(2) + "3");
        System.out.println("2 " + rida_sõnena(1) + "2");
        System.out.println("1 " + rida_sõnena(0) + "1");
        System.out.println("   a   b   c   d   e   f   g   h");
    }

    public String rida_sõnena(int rea_indeks){
        StringBuilder tulemus = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            if (laud[rea_indeks][i] != null)
                tulemus.append(laud[rea_indeks][i].toString()).append(" ");
            else
                tulemus.append("*** ");
        }
        return tulemus.toString();
    }
    public void väljasta_char(){
        System.out.println("  " + rida_sõnena_char(-1));
        System.out.println("8 " + rida_sõnena_char(7) + " 8");
        System.out.println("7 " + rida_sõnena_char(6) + " 7");
        System.out.println("6 " + rida_sõnena_char(5) + " 6");
        System.out.println("5 " + rida_sõnena_char(4) + " 5");
        System.out.println("4 " + rida_sõnena_char(3) + " 4");
        System.out.println("3 " + rida_sõnena_char(2) + " 3");
        System.out.println("2 " + rida_sõnena_char(1) + " 2");
        System.out.println("1 " + rida_sõnena_char(0) + " 1");
        System.out.println("  " + rida_sõnena_char(-1));
    }
    public String rida_sõnena_char(int rea_indeks){
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

    @Override
    public String toString() {
        return "Malelaud{" +
                "laud=" + Arrays.deepToString(laud) +
                '}';
    }
}

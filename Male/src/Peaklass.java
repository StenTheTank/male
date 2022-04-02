import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Peaklass {
    public static ArrayList<int[]> legaalsus_filter(ArrayList<int[]>kaigud,int[]asukoht,Malelaud praegune_malelaud,boolean valge_kaik) throws CloneNotSupportedException {
        ArrayList<int[]>legaalsed_kaigud=new ArrayList<>();
        Malelaud voimalik=(Malelaud)((Malelaud)praegune_malelaud).clone();
        for (int[] kaik : kaigud) {
            voimalik.liiguta(asukoht,kaik);
            if (legaalne(valge_kaik,voimalik)){
                legaalsed_kaigud.add(kaik);
            }

        }
        return legaalsed_kaigud;
    }
    public static ArrayList<int[]>vastasekaigud(Malelaud laud,boolean valge_kaik){
        ArrayList<int[]>vastus=new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (laud.getLaud()[i][j]!=null) {
                    if (valge_kaik) {
                        if (laud.getLaud()[i][j].getVarv() == 'm') {
                            ArrayList<int[]> ajutine = laud.getLaud()[i][j].kaigud(laud);
                            for (int[] kaik : ajutine) {
                                vastus.add(kaik);
                            }
                        }
                    } else {
                        if (laud.getLaud()[i][j].getVarv() == 'v') {
                            ArrayList<int[]> ajutine = laud.getLaud()[i][j].kaigud(laud);
                            for (int[] kaik : ajutine) {
                                vastus.add(kaik);
                            }
                        }
                    }
                }
            }
        }
        return vastus;
    }

    public static boolean legaalne(boolean valge_kaik,Malelaud laud){
        Nupp[][]nupud=laud.getLaud();
        ArrayList<int[]>v_kaigud = vastasekaigud(laud,valge_kaik);
        int[]asukoht;
        for (Nupp[] nupps : nupud) {
            for (Nupp nupp : nupps) {
                if (nupp!=null){
                    if (valge_kaik) {
                        if (nupp.getClass() == Kuningas.class) {
                            if (nupp.getVarv() == 'v') {
                                asukoht = nupp.getAsukoht();
                                if (v_kaigud.contains(asukoht)) {
                                    return false;
                                }
                            }
                        }
                    }
                    else{
                        if (nupp.getClass() == Kuningas.class) {
                            if (nupp.getVarv() == 'm') {
                                asukoht = nupp.getAsukoht();
                                if (v_kaigud.contains(asukoht)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    private static int käiguarv = 1;

    public static int getKäiguarv() {
        return käiguarv;
    }
    public static int[]dekodeeri_kaik(String kaik){
        String[] tahestik={"a","b","c","d","e","f","g","h"};
        int esimene=0;
        for (int i = 0; i < tahestik.length; i++) {
            if (kaik.startsWith(tahestik[i])) esimene=i;
        }
        int teine=Integer.parseInt(String.valueOf(kaik.charAt(1)))-1;
        return new int[]{teine,esimene};

    }
    public static String kodeeri_kaik(int[]kaik){
        String[] tahestik={"a","b","c","d","e","f","g","h"};
        String esimene=String.valueOf(kaik[0]+1);
        String teine=tahestik[kaik[1]];
        return teine+esimene;
    }
    public static void main(String[] args) throws CloneNotSupportedException {
        boolean valge_kaik=true;
        Malelaud laud = new Malelaud();
        laud.väljasta();
        System.out.println(Arrays.deepToString(laud.getLaud()[0][0].kaigud(laud).toArray()));
        System.out.println(laud.getLaud()[1][0].getClass() == Ettur.class);
        boolean mang_kaib=true;
        Scanner sc = new Scanner(System.in);
        String kaik_string;
        int[]kaik_int1;
        int[]kaik_int2;
        Nupp vaadeldavnupp;
        ArrayList<int[]>legaalsed_kaigud;
        while(mang_kaib){
            if (valge_kaik){
                kaik_string=sc.nextLine();
                kaik_int1=dekodeeri_kaik(kaik_string);
                vaadeldavnupp=laud.getLaud()[kaik_int1[0]][kaik_int1[1]];
                if (vaadeldavnupp!=null) {
                    if (vaadeldavnupp.getVarv() == 'v') {
                        legaalsed_kaigud=legaalsus_filter(vaadeldavnupp.kaigud(laud),kaik_int1,laud,valge_kaik);
                        System.out.println(legaalsed_kaigud);
                        for (int[] ints : legaalsed_kaigud) {
                            System.out.println(kodeeri_kaik(ints));
                        }
                        kaik_string=sc.nextLine();
                        kaik_int2=dekodeeri_kaik(kaik_string);
                        if (legaalsed_kaigud.contains(kaik_int2)){
                            System.out.println("jõudsin siia kuidagi");
                            laud.liiguta(kaik_int1,kaik_int1);
                            laud.väljasta();
                        }
                        else {
                            System.out.println("pole legaalne kaik");
                            laud.väljasta();
                        }
                    }
                }
                else System.out.println("nuppu pole");
            }
        }
    }
}
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Peaklass {
    public static ArrayList<int[]> legaalsus_filter(ArrayList<int[]>kaigud,int[]asukoht,Malelaud praegune_malelaud,boolean valge_kaik) throws CloneNotSupportedException {
        ArrayList<int[]>legaalsed_kaigud=new ArrayList<>();
        Malelaud voimalik=(Malelaud) praegune_malelaud.clone();
        for (int[] kaik : kaigud) {
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
                        if (laud.getLaud()[i][j].getVarv() == 'v') {
                            ArrayList<int[]> ajutine = laud.getLaud()[i][j].kaigud(laud);
                            for (int[] kaik : ajutine) {
                                vastus.add(kaik);
                            }
                        }
                    } else {
                        if (laud.getLaud()[i][j].getVarv() == 'm') {
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
    public static void main(String[] args) {
        boolean valge_kaik=true;
        Malelaud laud = new Malelaud();
        laud.väljasta();
        System.out.println(Arrays.deepToString(laud.getLaud()[0][0].kaigud(laud).toArray()));
        System.out.println(laud.getLaud()[1][0].getClass() == Ettur.class);
        /*while(){
            käiguarv++;
        }*/
    }
}
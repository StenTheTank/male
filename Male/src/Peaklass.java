import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Peaklass {
    static Nupp[][]laud=new Nupp[8][8];
    public static void main(String[] args) {
        String[]tahestik={"a","b","c","d","e","f","g","h"};
        for (int i = 0; i < 8; i++) {//mustad etturid
            laud[1][i]=new Ettur("v",tahestik[i]+"7");
        }
        for (int i = 0; i < 8; i++) {//valged etturid
            laud[6][i]=new Ettur("m",tahestik[i]+"2");
        }
        for (int i = 0; i < 9; i+=7) {//mustad vankrid
            laud[0][i]=new Vanker("v",tahestik[i]+"1");
        }
        for (int i = 0; i < 9; i+=7) {//valged vankrid
            laud[7][i]=new Vanker("m",tahestik[i]+"8");
        }
        for (int i = 1; i < 9; i+=5) {//mustad ratsud
            laud[0][i]=new Ratsu("v",tahestik[i]+"1");
        }
        for (int i = 1; i < 9; i+=5) {//valged ratsud
            laud[7][i]=new Ratsu("m",tahestik[i]+"8");
        }
        for (int i = 2; i < 6; i+=3) {//mustad odad
            laud[0][i]=new Oda("v",tahestik[i]+"1");
        }
        for (int i = 2; i < 6; i+=3) {//valged odad
            laud[7][i]=new Oda("m",tahestik[i]+"8");
        }
        for (int i = 0; i < 9; i+=7) {
            laud[i][3]=new Lipp("v","d"+String.valueOf(i));
        }
        for (int i = 0; i < 9; i+=7) {
            laud[i][4]=new Kuningas("v","e"+String.valueOf(i));
        }
        int number=1;
        for (Nupp[] nupud : laud) {
            System.out.print(number+"|");
            for (Nupp nupp : nupud) {
                System.out.print(nupp);
                System.out.print(" | ");
            }
            number++;
            System.out.println("");
        }
        System.out.println("      H       G         F         E          D        C        B        A");
        Scanner sc = new Scanner(System.in);
    }
}

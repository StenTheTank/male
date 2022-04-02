import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Peaklass {
    public static void main(String[] args) {
        Malelaud laud = new Malelaud();
        /*
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
        */
        laud.väljasta();
        System.out.println(Arrays.deepToString(laud.getLaud()[0][0].kaigud(laud).toArray()));
    }
}

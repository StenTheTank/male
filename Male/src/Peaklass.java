import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Peaklass {
    private static int käiguarv = 1;

    public static int getKäiguarv() {
        return käiguarv;
    }

    public static void main(String[] args) {
        Malelaud laud = new Malelaud();
        laud.väljasta();
        System.out.println(Arrays.deepToString(laud.getLaud()[0][0].kaigud(laud).toArray()));
        System.out.println(laud.getLaud()[1][0].getClass() == Ettur.class);
        /*while(){

            käiguarv++;
        }*/
    }
}

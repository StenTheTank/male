import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class Peaklass {
    public static void main(String[] args) {
        Malelaud laud = new Malelaud();
        laud.väljasta();
        System.out.println(Arrays.deepToString(laud.getLaud()[0][0].kaigud(laud).toArray()));
    }
}

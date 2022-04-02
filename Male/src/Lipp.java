import java.util.ArrayList;
import java.util.Arrays;

public class Lipp extends Nupp{

    public Lipp(char varv,int[] asukoht) {
        this.varv = varv;
        this.asukoht=asukoht;
    }


    @Override
    public ArrayList<int[]> kaigud(Malelaud malelaud) {

        return null;
    }

    @Override
    public String toString() {
        return varv + "Li";
    }
}

import java.util.ArrayList;
import java.util.Arrays;

public class Ratsu extends Nupp{

    public Ratsu(char varv,int[] asukoht) {
        this.varv = varv;
        this.asukoht=asukoht;
    }

    @Override
    public ArrayList<int[]> kaigud(Malelaud malelaud) {

        return null;
    }

    @Override
    public String toString() {
        return varv + "Ra";
    }
}

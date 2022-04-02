import java.util.ArrayList;
import java.util.Arrays;

public abstract class Nupp {
    protected char varv;
    protected int[] asukoht;

     public abstract ArrayList<int[]> kaigud(Malelaud malelaud);//vb selle meetodiga annaks ette võimalikud käigud, mida selle nupuga teha saab?

    public char getVarv() {
        return varv;
    }

    public int[] getAsukoht() {
        return asukoht;
    }

    public void setAsukoht(int[] asukoht) {
        this.asukoht = asukoht;
    }


}

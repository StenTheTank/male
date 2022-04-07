import java.util.ArrayList;
import java.util.Arrays;

public abstract class Nupp {
    protected char varv;
    protected int[] asukoht;
    protected char un_char; //unicode chraracter

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

    public void setAsukoht(int[] asukoht, boolean ettur_kaks_sammu) {
        this.asukoht = asukoht;
    }

    public String toCharString(){
        return "["+ un_char + "]";
    }
}

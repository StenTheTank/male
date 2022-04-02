import java.util.ArrayList;
import java.util.Arrays;

public class Kuningas extends Nupp{
    private boolean on_liigutatud;

    public Kuningas(char varv, int[] asukoht) {
        this.varv = varv;
        this.asukoht = asukoht;
    }

    @Override
    public void setAsukoht(int[] asukoht) {
        super.setAsukoht(asukoht);
        on_liigutatud = true;
    }

    @Override
    public ArrayList<int[]> kaigud(Malelaud malelaud) {

        return null;
    }

    @Override
    public String toString() {
        return varv + "Ku";
    }
}

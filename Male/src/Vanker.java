import java.util.ArrayList;
import java.util.Arrays;

public class Vanker extends Nupp{
    private boolean on_liigutatud;

    public Vanker(char varv, int[] asukoht) {
        this.varv = varv;
        this.asukoht=asukoht;
    }

    @Override
    public void setAsukoht(int[] asukoht) {
        super.setAsukoht(asukoht);
        on_liigutatud = true;
    }

    @Override
    public ArrayList<int[]> kaigud(Malelaud malelaud) {
        ArrayList<int[]> tulemus = new ArrayList<>();

        //Paremale
        for (int i = asukoht[1] + 1 ; i < 8; i++) {
            if (malelaud.getLaud()[asukoht[0]][i] == null) tulemus.add(new int[]{asukoht[0], i});
            else if (malelaud.getLaud()[asukoht[0]][i].getVarv() != varv) {
                tulemus.add(new int[]{asukoht[0], i});
                break;
            }
            else break;
        }

        //Vasakule
        for (int i = asukoht[1] - 1 ; i > -1; i--) {
            if (malelaud.getLaud()[asukoht[0]][i] == null) tulemus.add(new int[]{asukoht[0], i});
            else if (malelaud.getLaud()[asukoht[0]][i].getVarv() != varv) {
                tulemus.add(new int[]{asukoht[0], i});
                break;
            }
            else break;
        }

        //Üles
        for (int i = asukoht[0] + 1 ; i < 8; i++) {
            if (malelaud.getLaud()[i][asukoht[1]] == null) tulemus.add(new int[]{i, asukoht[1]});
            else if (malelaud.getLaud()[i][asukoht[1]].getVarv() != varv) {
                tulemus.add(new int[]{i, asukoht[1]});
                break;
            }
            else break;
        }

        //Üles
        for (int i = asukoht[0] - 1 ; i > -1; i--) {
            if (malelaud.getLaud()[i][asukoht[1]] == null) tulemus.add(new int[]{i, asukoht[1]});
            else if (malelaud.getLaud()[i][asukoht[1]].getVarv() != varv) {
                tulemus.add(new int[]{i, asukoht[1]});
                break;
            }
            else break;
        }
        return tulemus;
    }

    @Override
    public String toString() {
        return varv + "Va";
    }
}

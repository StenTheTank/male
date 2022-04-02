import java.util.ArrayList;
import java.util.Arrays;

public class Lipp extends Nupp{

    public Lipp(char varv,int[] asukoht) {
        this.varv = varv;
        this.asukoht=asukoht;
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
        //Üles-paremale
        for (int i = asukoht[0] + 1, j = asukoht[1] + 1;
             i < 8 && j < 8;
             i++, j++) {
            if (malelaud.getLaud()[i][j] == null) tulemus.add(new int[]{i, j});
            else if (malelaud.getLaud()[i][j].getVarv() != varv) {
                tulemus.add(new int[]{i, j});
                break;
            } else if (malelaud.getLaud()[i][j].getVarv() == varv) break;
        }

        //Üles-vasakule
        for (int i = asukoht[0] + 1, j = asukoht[1] - 1;
             i < 8 && j > -1;
             i++, j--) {
            if (malelaud.getLaud()[i][j] == null) tulemus.add(new int[]{i, j});
            else if (malelaud.getLaud()[i][j].getVarv() != varv) {
                tulemus.add(new int[]{i, j});
                break;
            } else if (malelaud.getLaud()[i][j].getVarv() == varv) break;
        }

        //Alla-vasakule
        for (int i = asukoht[0] - 1, j = asukoht[1] - 1;
             i > -1 && j > -1;
             i--, j--) {
            if (malelaud.getLaud()[i][j] == null) tulemus.add(new int[]{i, j});
            else if (malelaud.getLaud()[i][j].getVarv() != varv) {
                tulemus.add(new int[]{i, j});
                break;
            } else if (malelaud.getLaud()[i][j].getVarv() == varv) break;
        }

        //Alla-paremale
        for (int i = asukoht[0] - 1, j = asukoht[1] + 1;
             i > -1 && j < 8;
             i--, j++) {
            if (malelaud.getLaud()[i][j] == null) tulemus.add(new int[]{i, j});
            else if (malelaud.getLaud()[i][j].getVarv() != varv) {
                tulemus.add(new int[]{i, j});
                break;
            } else if (malelaud.getLaud()[i][j].getVarv() == varv) break;
        }

        return tulemus;
    }

    @Override
    public String toString() {
        return varv + "Li";
    }
}

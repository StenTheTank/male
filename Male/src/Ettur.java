import java.util.ArrayList;
import java.util.Arrays;

public class Ettur extends Nupp{
    private boolean on_liigutatud;

    public Ettur(char varv, int[] asukoht) {
        this.varv = varv;
        this.asukoht = asukoht;
        on_liigutatud = false;
    }

    @Override
    public void setAsukoht(int[] asukoht) {
        super.setAsukoht(asukoht);
        on_liigutatud = true;
    }

    @Override
    public ArrayList<int[]> kaigud(Malelaud malelaud) {
        ArrayList<int[]> tulemus = new ArrayList<>();
        if (varv == 'v'){
            if (ett_saab_käia(malelaud, asukoht[0]+1, asukoht[1])) tulemus.add(new int[]{asukoht[0]+1, asukoht[1]});
            if (!on_liigutatud && ett_saab_käia(malelaud, asukoht[0]+2, asukoht[1])) tulemus.add(new int[]{asukoht[0]+2, asukoht[1]});
            if (ett_saab_süüa(malelaud, asukoht[0]+1, asukoht[1]+1)) tulemus.add(new int[]{asukoht[0]+1, asukoht[1]+1});
            if (ett_saab_süüa(malelaud, asukoht[0]+1, asukoht[1]-1)) tulemus.add(new int[]{asukoht[0]+1, asukoht[1]-1});
        }
        else{
            if (ett_saab_käia(malelaud, asukoht[0]-1, asukoht[1])) tulemus.add(new int[]{asukoht[0]-1, asukoht[1]});
            if (!on_liigutatud && ett_saab_käia(malelaud, asukoht[0]-2, asukoht[1])) tulemus.add(new int[]{asukoht[0]-2, asukoht[1]});
            if (ett_saab_süüa(malelaud, asukoht[0]-1, asukoht[1]+1)) tulemus.add(new int[]{asukoht[0]-1, asukoht[1]+1});
            if (ett_saab_süüa(malelaud, asukoht[0]-1, asukoht[1]-1)) tulemus.add(new int[]{asukoht[0]-1, asukoht[1]-1});
        }

        return tulemus;
    }

    public boolean ett_saab_käia(Malelaud malelaud, int i, int j){
        return i > 0 && i < 8 && malelaud.getLaud()[i][j] == null;
    }
    public boolean ett_saab_süüa(Malelaud malelaud, int i, int j){
        return i > 0 && i < 8 && j > 0 && j < 8 && malelaud.getLaud()[i][j] != null && malelaud.getLaud()[i][j].getVarv() != varv;
    }

    @Override
    public String toString() {
        return varv + "Et";
    }
}

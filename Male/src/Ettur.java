import java.util.ArrayList;

public class Ettur extends Nupp{
    private boolean on_liigutatud;
    private int käidud;

    public Ettur(char varv, int[] asukoht,boolean on_liigutatud) {
        this.varv = varv;
        this.asukoht = asukoht;
        this.on_liigutatud = on_liigutatud;
        käidud = 0;
    }

    @Override
    public void setAsukoht(int[] asukoht) {
        super.setAsukoht(asukoht);
        on_liigutatud = true;
    }

    public boolean isOn_liigutatud() {
        return on_liigutatud;
    }

    @Override
    public void setAsukoht(int[] asukoht, boolean ettur_kaks_sammu) {
        super.setAsukoht(asukoht);
        on_liigutatud = true;
        käidud = Peaklass.getKäiguarv();
    }

    public int getKäidud() {
        return käidud;
    }

    @Override
    public ArrayList<int[]> kaigud(Malelaud malelaud) {
        ArrayList<int[]> tulemus = new ArrayList<>();
        if (varv == 'v'){
            if (ett_saab_käia(malelaud, asukoht[0]+1, asukoht[1])) tulemus.add(new int[]{asukoht[0]+1, asukoht[1]});
            if (!on_liigutatud && ett_saab_käia(malelaud, asukoht[0]+2, asukoht[1]) && ett_saab_käia(malelaud, asukoht[0]+1, asukoht[1])) tulemus.add(new int[]{asukoht[0]+2, asukoht[1]});
            if (ett_saab_süüa(malelaud, asukoht[0]+1, asukoht[1]+1)) tulemus.add(new int[]{asukoht[0]+1, asukoht[1]+1});
            if (ett_saab_süüa(malelaud, asukoht[0]+1, asukoht[1]-1)) tulemus.add(new int[]{asukoht[0]+1, asukoht[1]-1});
            //En passant
            if (asukoht[0] == 4 && ett_saab_süüa(malelaud, asukoht[0], asukoht[1]+1) && malelaud.getLaud()[asukoht[0]][asukoht[1]+1].getClass() == Ettur.class){
                Ettur söödav = (Ettur) malelaud.getLaud()[asukoht[0]][asukoht[1]+1];
                if (söödav.getKäidud() + 1 == Peaklass.getKäiguarv()){
                    tulemus.add(new int[]{asukoht[0]+1, asukoht[1]+1});
                    tulemus.add(new int[]{asukoht[0]+1, asukoht[1]+1, 1});
                }
            }
            if (asukoht[0] == 4 && ett_saab_süüa(malelaud, asukoht[0], asukoht[1]-1) && malelaud.getLaud()[asukoht[0]][asukoht[1]+-1].getClass() == Ettur.class){
                Ettur söödav = (Ettur) malelaud.getLaud()[asukoht[0]][asukoht[1]-1];
                if (söödav.getKäidud() + 1 == Peaklass.getKäiguarv()){
                    tulemus.add(new int[]{asukoht[0]+1, asukoht[1]-1});
                    tulemus.add(new int[]{asukoht[0]+1, asukoht[1]-1, 1});
                }
            }

        }
        else{
            if (ett_saab_käia(malelaud, asukoht[0]-1, asukoht[1])) tulemus.add(new int[]{asukoht[0]-1, asukoht[1]});
            if (!on_liigutatud && ett_saab_käia(malelaud, asukoht[0]-2, asukoht[1]) && ett_saab_käia(malelaud, asukoht[0]-1, asukoht[1])) tulemus.add(new int[]{asukoht[0]-2, asukoht[1]});
            if (ett_saab_süüa(malelaud, asukoht[0]-1, asukoht[1]+1)) tulemus.add(new int[]{asukoht[0]-1, asukoht[1]+1});
            if (ett_saab_süüa(malelaud, asukoht[0]-1, asukoht[1]-1)) tulemus.add(new int[]{asukoht[0]-1, asukoht[1]-1});
            //En passant
            if (asukoht[0] == 3 && ett_saab_süüa(malelaud, asukoht[0], asukoht[1]+1) && malelaud.getLaud()[asukoht[0]][asukoht[1]+1].getClass() == Ettur.class){
                Ettur söödav = (Ettur) malelaud.getLaud()[asukoht[0]][asukoht[1]+1];
                if (söödav.getKäidud() + 1 == Peaklass.getKäiguarv()){
                    tulemus.add(new int[]{asukoht[0]-1, asukoht[1]+1});
                    tulemus.add(new int[]{asukoht[0]-1, asukoht[1]+1, 1});
                }
            }
            if (asukoht[0] == 3 && ett_saab_süüa(malelaud, asukoht[0], asukoht[1]-1) && malelaud.getLaud()[asukoht[0]][asukoht[1]+-1].getClass() == Ettur.class){
                Ettur söödav = (Ettur) malelaud.getLaud()[asukoht[0]][asukoht[1]-1];
                if (söödav.getKäidud() + 1 == Peaklass.getKäiguarv()){
                    tulemus.add(new int[]{asukoht[0]-1, asukoht[1]-1});
                    tulemus.add(new int[]{asukoht[0]-1, asukoht[1]-1, 1});
                }
            }
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

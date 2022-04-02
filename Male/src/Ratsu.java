import java.util.ArrayList;
import java.util.Arrays;

public class Ratsu extends Nupp{

    public Ratsu(char varv,int[] asukoht) {
        this.varv = varv;
        this.asukoht=asukoht;
    }

    public boolean abiratsu(int rida,int veerg, Malelaud malelaud){
        if (rida < 0 || rida > 7 || veerg < 0 || veerg > 7) return false;
        else if (malelaud.getLaud()[rida][veerg]==null) return true;
        else if (malelaud.getLaud()[rida][veerg].getVarv() != varv) return true;
        return false;
    }
    @Override
    public ArrayList<int[]> kaigud(Malelaud malelaud) {
        ArrayList<int[]> tulemus = new ArrayList<>();
        int rida=asukoht[0];int veerg=asukoht[1];
        if (abiratsu(rida+2,veerg-1,malelaud)) tulemus.add(new int[]{rida+2,veerg-1});
        if (abiratsu(rida+2,veerg+1,malelaud)) tulemus.add(new int[]{rida+2,veerg+1});
        if (abiratsu(rida+1,veerg-2,malelaud)) tulemus.add(new int[]{rida+1,veerg-2});
        if (abiratsu(rida+1,veerg+2,malelaud)) tulemus.add(new int[]{rida+1,veerg+2});
        if (abiratsu(rida-1,veerg+2,malelaud)) tulemus.add(new int[]{rida-1,veerg+2});
        if (abiratsu(rida-1,veerg-2,malelaud)) tulemus.add(new int[]{rida-1,veerg-2});
        if (abiratsu(rida-2,veerg+1,malelaud)) tulemus.add(new int[]{rida-2,veerg+1});
        if (abiratsu(rida-2,veerg-1,malelaud)) tulemus.add(new int[]{rida-2,veerg-1});

        return tulemus;
    }

    @Override
    public String toString() {
        return varv + "Ra";
    }
}

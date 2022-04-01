import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vanker implements Nupp{
    private String varv;
    private String asukoht;
    public Vanker(String varv,String asukoht) {
        this.varv = varv;
        this.asukoht=asukoht;
    }
    public void kaigud(String asukoht){
        List<String>vastus=new ArrayList<>();

    }

    public String getVarv() {
        return varv;
    }

    public String getAsukoht() {
        return asukoht;
    }

    public void setAsukoht(String asukoht) {
        this.asukoht = asukoht;
    }

    @Override
    public String toString() {
        return varv+" Vanker";
    }
}

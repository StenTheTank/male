
import java.util.*;

public class Peaklass {
    private static final ArrayList<Character> tahestik_char = new ArrayList<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'));
    private static final ArrayList<Character> numbrid_char = new ArrayList<>(Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8'));
    private static int käiguarv = 1;
    private static boolean valge_kaik = true;
    private static char värv = 'v';
    private static final Malelaud laud = new Malelaud();
    private static boolean mang_kaib = true;
    private static final Scanner sc = new Scanner(System.in);

    public static int getKäiguarv() {
        return käiguarv;
    }


    public static ArrayList<int[]> legaalsus_filter(ArrayList<int[]> kaigud, int[] asukoht, Malelaud praegune_malelaud, boolean valge_kaik){
        ArrayList<int[]> legaalsed_kaigud = new ArrayList<>();
        Malelaud voimalik;

        for (int[] kaik : kaigud) {
            voimalik = new Malelaud(praegune_malelaud);
            voimalik.liiguta(asukoht, kaik);
            if (legaalne(valge_kaik, voimalik)){
                legaalsed_kaigud.add(kaik);
            }
        }
        return legaalsed_kaigud;
    }
    public static ArrayList<int[]> vastasekaigud(Malelaud laud, boolean valge_kaik){
        ArrayList<int[]> vastus = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (laud.getLaud()[i][j] != null) {
                    if (valge_kaik) {
                        if (laud.getLaud()[i][j].getVarv() == 'm') {
                            ArrayList<int[]> selle_nupu_käigud = laud.getLaud()[i][j].kaigud(laud);
                            vastus.addAll(selle_nupu_käigud);
                        }
                    }
                    else {
                        if (laud.getLaud()[i][j].getVarv() == 'v') {
                            ArrayList<int[]> selle_nupu_käigud = laud.getLaud()[i][j].kaigud(laud);
                            vastus.addAll(selle_nupu_käigud);
                        }
                    }
                }
            }
        }
        return vastus;
    }

    public static boolean legaalne(boolean valge_kaik, Malelaud laud){
        Nupp[][] nupud = laud.getLaud();
        ArrayList<int[]> v_kaigud = vastasekaigud(laud,valge_kaik);
        int[]asukoht;
        for (Nupp[] nupps : nupud) {
            for (Nupp nupp : nupps) {
                if (nupp != null){
                    if (valge_kaik) {
                        if (nupp.getClass() == Kuningas.class) {
                            if (nupp.getVarv() == 'v') {
                                asukoht = nupp.getAsukoht();
                                if (sisaldub(v_kaigud, asukoht)) {
                                    return false;
                                }
                            }
                        }
                    }
                    else{
                        if (nupp.getClass() == Kuningas.class) {
                            if (nupp.getVarv() == 'm') {
                                asukoht = nupp.getAsukoht();
                                if (sisaldub(v_kaigud, asukoht)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     *
     * @param kaik string
     * @return liigutava nupu koordinaadid või sihtkoordinaadid
     */
    public static int[] dekodeeri_kaik(String kaik){
        String[] tahestik = {"a","b","c","d","e","f","g","h"};
        int esimene = 0;
        for (int i = 0; i < tahestik.length; i++) {
            if (kaik.startsWith(tahestik[i])) esimene = i;
        }
        int teine = Integer.parseInt(String.valueOf(kaik.charAt(1))) - 1;
        return new int[]{teine, esimene};

    }

    /**
     *
     * @param kaik käik
     * @return käik, mängijale arusaadava stringina
     */
    public static String kodeeri_kaik(int[]kaik){
        String[] tahestik = {"a","b","c","d","e","f","g","h"};
        String esimene = String.valueOf(kaik[0]+1);
        String teine = tahestik[kaik[1]];
        return teine + esimene;
    }

    /**
     *
     * @param asukohtade_list asukohtade list
     * @param element asukoht
     * @return Võrdleb massiive nende sisu järgi, mitte viitade järgi
     */
    public static boolean sisaldub(ArrayList<int[]> asukohtade_list, int[]element){
        for (int[] ints : asukohtade_list) {
            if(Arrays.toString(ints).equals(Arrays.toString(element))) return true;
        }
        return false;
    }

    public static boolean valideeri_käigu_formaat(String käik){
        String käik_lühem = käik.trim();
        return käik_lühem.length() == 2 && tahestik_char.contains(käik_lühem.charAt(0)) && numbrid_char.contains(käik_lühem.charAt(1));
    }

    /**
     *
     * @param sc skanner, mis võtab vastu sisendit
     * @return sobiv string nt("a2", aga mitte "2a")
     */
    public static String valideeri_käik(Scanner sc){
        String kaik_string = sc.nextLine();
        while (! valideeri_käigu_formaat(kaik_string)){
            System.out.println("Käigu formaat ei ole sobilik, proovige uuesti:");
            kaik_string = sc.nextLine();
        }
        return kaik_string;
    }

    public static void ettur_jõuab_lõppu(){
        int i = -1;
        int j = -1;
        for (Nupp nupp : laud.getLaud()[0]) {
            if (nupp != null && nupp.getClass() == Ettur.class){
                i = 0;
                j = nupp.getAsukoht()[1];
            }
        }
        for (Nupp nupp : laud.getLaud()[7]) {
            if (nupp != null && nupp.getClass() == Ettur.class){
                i = 7;
                j = nupp.getAsukoht()[1];
            }
        }
        if (i == -1)
            return;

        System.out.println("Ettur jõudis lõppu! Valige milleks ettur muutub: 'Lipp', 'Oda', 'Ratsu', 'Vanker'");
        String muutub = sc.nextLine().trim();
        while (!(muutub.equals("Lipp") || muutub.equals("Oda") || muutub.equals("Ratsu") || muutub.equals("Vanker"))){
             System.out.println("Vigane sisend, proovige uuesti!");
              muutub = sc.nextLine().trim();
        }
        laud.ettur_muutub(muutub, (Ettur) laud.getLaud()[i][j]);

    }

    /*public static void AI_vastu(){ ei tea kas teha
        ArrayList<Nupp> võimalikud_nupud = new ArrayList<>();
        for (Nupp[] nupp_mass : laud.getLaud()) {
            for (Nupp nupp : nupp_mass) {
                if (nupp != null && nupp.getVarv() == 'm' && legaalsus_filter(nupp.kaigud(laud), nupp.getAsukoht(), laud, valge_kaik).size() != 0)
                    võimalikud_nupud.add(nupp);
            }
        }
        if (võimalikud_nupud.size() == 0) return;
        Collections.shuffle(võimalikud_nupud);
        Nupp suvaline = võimalikud_nupud.get((int)(Math.random() * võimalikud_nupud.size()));
        ArrayList<int[]> võimalikud_käigud = legaalsus_filter(suvaline.kaigud(laud), suvaline.getAsukoht(), laud, valge_kaik);
        Collections.shuffle(võimalikud_käigud);
        int[] suvaline_käik = võimalikud_käigud.get((int)(Math.random() * võimalikud_käigud.size()));
        laud.liiguta(suvaline.getAsukoht(), suvaline_käik);
    }*/

    public static void main(String[] args){
        //TODO mängu  lõpp
        //TODO kui keegi ei saa liikuda

        //laud.väljasta_char(); //Tõõtab kasutades unicode charactere
        laud.väljasta();
        String kaik_string;
        int[] kaik_int1;
        int[] kaik_int2;
        Nupp vaadeldavnupp;
        ArrayList<int[]> legaalsed_kaigud;

        while(mang_kaib){
            if (valge_kaik)
                System.out.println("Valge käik, alguskäik:");
            else
                System.out.println("Musta käik, alguskäik:");

            do {
            kaik_string = valideeri_käik(sc);
            kaik_int1 = dekodeeri_kaik(kaik_string);
            vaadeldavnupp = laud.getLaud()[kaik_int1[0]][kaik_int1[1]];

            if (vaadeldavnupp == null){
                System.out.print("Sellel kohal pole nuppu! ");
                legaalsed_kaigud = new ArrayList<>();
                continue;
            }
            if (vaadeldavnupp.getVarv() != värv){
                System.out.print("See nupp pole sinu nupp! ");
                legaalsed_kaigud = new ArrayList<>();
                continue;
            }
            legaalsed_kaigud = legaalsus_filter(vaadeldavnupp.kaigud(laud), kaik_int1, laud, valge_kaik);
            if (legaalsed_kaigud.size() == 0) System.out.println("Selle nupuga ei saa liikuda! Sisetage alguskäik uuesti:");
            }
            while (legaalsed_kaigud.size() == 0);

            System.out.print("Võimalikud käigud: ");
            for (int[] ints : legaalsed_kaigud) {
                if (ints.length == 3) continue; // kui on en passant abikäik, siis ära prindi en passant abikäik näeb välja {x, y, 1}
                System.out.print(kodeeri_kaik(ints) + " ");
            }
            System.out.println();
            //System.out.println("Kuhu soovid nupuga käia?");
            do {
                kaik_string = valideeri_käik(sc);
                kaik_int2 = dekodeeri_kaik(kaik_string);
                if (! sisaldub(legaalsed_kaigud, kaik_int2))
                    System.out.println("Sinna ei saa selle nupuga käia! Sisestage uus sihtkoht: ");
            }
            while (! sisaldub(legaalsed_kaigud, kaik_int2));
            laud.liiguta(kaik_int1, kaik_int2);
            ettur_jõuab_lõppu();
            laud.väljasta();
            //if matt
            valge_kaik = !valge_kaik;
            värv = (valge_kaik) ? 'v' : 'm';
            käiguarv++;
        }
    }
}

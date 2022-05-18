package com.example.oopprojekt.malemang;

import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Peaklass extends Application {
    private static final ArrayList<Character> tahestik_char = new ArrayList<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'));
    private static final ArrayList<Character> numbrid_char = new ArrayList<>(Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8'));
    private static int käiguarv = 1;
    private static boolean valge_kaik = true;
    private static char värv = 'v';
    private static Malelaud laud = new Malelaud();
    private static boolean mang_kaib = true;
    //private static final Scanner sc = new Scanner(System.in);
    private static int[] alguskoht = null;
    private static ArrayList<int[]> alguse_leg_käigud;
    private static final String logifail = "male.log";
    private static final HashMap<String, Image> images;
    private static GridPane gridPane;
    private static Group juur;
    private static ArrayList<Node> eemaldatavad;
    private static Text tekstiväli;

    static {
        try {
            images = generateImageMap();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static HashMap<String, Image> generateImageMap() throws IOException{
        HashMap<String, Image> tulemus = new HashMap<>();
        tulemus.put("mEt", generateImage("Graafika/must_ettur.png"));
        tulemus.put("vEt", generateImage("Graafika/valge_ettur.png"));
        tulemus.put("mKu", generateImage("Graafika/must_kunn.png"));
        tulemus.put("vKu", generateImage("Graafika/valge_kunn.png"));
        tulemus.put("mLi", generateImage("Graafika/must_lipp.png"));
        tulemus.put("vLi", generateImage("Graafika/valge_lipp.png"));
        tulemus.put("mRa", generateImage("Graafika/must_ratsu.png"));
        tulemus.put("vRa", generateImage("Graafika/valge_ratsu.png"));
        tulemus.put("mVa", generateImage("Graafika/must_vanker.png"));
        tulemus.put("vVa", generateImage("Graafika/valge_vanker.png"));
        tulemus.put("mOd", generateImage("Graafika/must_oda.png"));
        tulemus.put("vOd", generateImage("Graafika/valge_oda.png"));
        tulemus.put("tapp", generateImage("Graafika/tapp.png"));
        return tulemus;
    }
    private static Image generateImage(String file) throws IOException{
        InputStream sisse = new FileInputStream(file);
        return new Image(sisse);
    }

    static EventHandler<MouseEvent> buttonEventHandler() {
        return event -> {
            if (! mang_kaib)
                return;
            Node node = (Node) event.getSource();
            if (node.getClass() != StackPane.class)
                return;
            int rida = GridPane.getRowIndex(node);
            int veerg = GridPane.getColumnIndex(node);
            int[] vaadeldav_käik = new int[]{7 - (rida - 1), veerg - 1};
            if (alguskoht == null){
                Nupp vaadeldav_nupp = laud.getLaud()[vaadeldav_käik[0]][vaadeldav_käik[1]];
                if (vaadeldav_nupp == null){
                    tekstiväli.setText("Sellel kohal pole nuppu!");
                    return;
                }
                if (vaadeldav_nupp.varv != värv){
                    tekstiväli.setText("See pole sinu nupp!");
                    return;
                }
                ArrayList<int[]> legaalsed_kaigud = legaalsus_filter(vaadeldav_nupp.kaigud(laud), vaadeldav_käik, laud, valge_kaik);
                if (legaalsed_kaigud.size() == 0){
                    tekstiväli.setText("Selle nupuga ei saa käia!");
                    return;
                }
                try {
                    alguskoht = vaadeldav_käik;
                    alguse_leg_käigud = legaalsed_kaigud;
                    updateGridPane();
                    tekstiväli.setText("Valitud:" + kodeeri_kaik(vaadeldav_käik));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Nupp vaadeldav_nupp = laud.getLaud()[vaadeldav_käik[0]][vaadeldav_käik[1]];
                if (vaadeldav_nupp != null) {
                    ArrayList<int[]> legaalsed_kaigud = legaalsus_filter(vaadeldav_nupp.kaigud(laud), vaadeldav_käik, laud, valge_kaik);
                    if (vaadeldav_nupp.varv == värv) {
                        alguskoht = vaadeldav_käik;
                        alguse_leg_käigud = legaalsed_kaigud;
                        tekstiväli.setText("Valitud:" + kodeeri_kaik(vaadeldav_käik));
                        try {
                            updateGridPane();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                }
                if (! sisaldub(alguse_leg_käigud, vaadeldav_käik)){
                    tekstiväli.setText("Sinna ei saa selle nupuga käia!");
                    return;
                }
                laud.liiguta(alguskoht, vaadeldav_käik);

                tekstiväli.setText("Liigutasin: " + kodeeri_kaik(alguskoht) + " -> " + kodeeri_kaik(vaadeldav_käik));
                alguskoht = null;
                if (mangu_lopp(laud,valge_kaik)){
                    if(vastasekuningas_tule_all(laud,valge_kaik)) {
                        tekstiväli.setText("Mäng läbi, võitis: " + värv);
                        return;
                    }
                    else{
                        tekstiväli.setText("Mäng läbi, viik");
                        return;
                    }
                }
                try {
                    updateGridPane();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                valge_kaik = !valge_kaik;
                värv = (valge_kaik) ? 'v' : 'm';
                try {
                    ettur_jõuab_lõppu();
                    writeToLog();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                käiguarv++;
            }
        };
    }

    public static void updateGridPane() throws IOException {
        GridPane pane = new GridPane();
        int count = 0;
        double s = 75; // side of rectangle
        for (int i = 1; i < 9; i++) {
            count++;
            for (int j = 1; j < 9; j++) {
                Rectangle r = new Rectangle(s,s);
                //r.widthProperty().bind(pane.widthProperty().subtract(40).divide(8));r.heightProperty().bind(pane.heightProperty().subtract(40).divide(8));
                if (count % 2 != 0)
                    r.setFill(Color.WHITE);
                else
                    r.setFill(Color.SADDLEBROWN);
                StackPane stackPane = new StackPane(r);

                if (laud.getLaud()[7-(i-1)][j-1] != null){
                    ImageView imageView = new ImageView(images.get(laud.getLaud()[7-(i-1)][j-1].toString()));
                    stackPane.getChildren().add(imageView);
                }
                if (alguskoht != null && sisaldub(alguse_leg_käigud, new int[]{7-(i-1),j-1})){
                    ImageView imageView = new ImageView(images.get("tapp"));
                    imageView.setFitHeight(10);
                    imageView.setFitWidth(10);
                    GridPane.setHalignment(imageView, HPos.CENTER);
                    stackPane.getChildren().add(imageView);
                }
                stackPane.addEventHandler(MouseEvent.MOUSE_CLICKED, buttonEventHandler());
                pane.add(stackPane, j, i);
                count++;
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                Text text = new Text(tahestik_char.get(j).toString());
                GridPane.setHalignment(text, HPos.CENTER);
                pane.add(text,j+1,i*9);
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                Text text = new Text(numbrid_char.get(numbrid_char.size()-1-j).toString());
                GridPane.setHalignment(text, HPos.CENTER);
                pane.add(text,i * 9,j + 1);
            }
        }
        pane.setStyle("-fx-border-color: black;");
        if (gridPane != null)
            juur.getChildren().remove(gridPane);
        gridPane = pane;
        juur.getChildren().add(gridPane);
    }
    public void start(Stage primaryStage) throws IOException {
        initLog();
        Button button = new Button("Tagasi");
        button.setLayoutX(620);
        button.setOnMouseClicked(event -> {
            try {
                roll_Back();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        tekstiväli = new Text();
        tekstiväli.setFont(Font.font(20));
        tekstiväli.setX(700);
        tekstiväli.setY(250);
        juur = new Group(button, tekstiväli);
        updateGridPane();

        Scene scene = new Scene(juur);
        primaryStage.setTitle("Male");
        primaryStage.setScene(scene); // Place in scene in the stage
        primaryStage.show();

    }
    public static int getKäiguarv() {
        return käiguarv;
    }

    public static void roll_Back() throws IOException {
        if (käiguarv == 1)
            return;
        käiguarv--;
        laud = find_from_log(käiguarv - 1);
        alguskoht = null;
        valge_kaik = !valge_kaik;
        värv = (valge_kaik) ? 'v' : 'm';
        if (!mang_kaib){
            juur.getChildren().removeAll(eemaldatavad);
            mang_kaib = true;
        }
        updateGridPane();
    }

    public static Malelaud find_from_log(int käike)throws IOException{
        String vaadeldav_rida = null;
        try(BufferedReader sisse = new BufferedReader(new InputStreamReader(new FileInputStream(logifail), StandardCharsets.UTF_8))){
            String rida = sisse.readLine();
            while (rida != null){
                if (Integer.parseInt(rida.substring(0, rida.indexOf(";"))) == käike)
                    vaadeldav_rida = rida.substring(rida.indexOf(";") + 1);
                rida = sisse.readLine();
            }
        }
        if (vaadeldav_rida == null)
            throw new RuntimeException("Logifailist ei leitud vastavat rida!");
        return new Malelaud(vaadeldav_rida);
    }

    public static void initLog() throws IOException {
        try(BufferedWriter välja = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logifail), StandardCharsets.UTF_8))){
            välja.write(0 + laud.kodeerilaud());
        }
    }
    public static void writeToLog() throws IOException{
        try(BufferedWriter välja = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(logifail, true), StandardCharsets.UTF_8))){
            välja.write("\n" + käiguarv + laud.kodeerilaud());
        }
    }

    /**
     *
     * @param kaigud nupu käigud
     * @param asukoht nupu asukoht
     * @param praegune_malelaud praegune malelaud
     * @param valge_kaik valge käik hetkel
     * @return Nupu käigud, mille puhul on kontrollitud, et kuningas ei jääks tule alla peale käigu tegemist
     */
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

    public static boolean legaalne(boolean valge_kaik, Malelaud laud){
        Nupp[][] nupud = laud.getLaud();
        ArrayList<int[]> v_kaigud = laud.vastasekaigud(valge_kaik);
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

    public static void ettur_jõuab_lõppu() throws IOException {
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

        tekstiväli.setText("Ettur jõudis lõppu! Valige milleks ettur muutub: 'Lipp', 'Oda', 'Ratsu', 'Vanker'");
        kuva_valikud(i,j);
    }
    static EventHandler<MouseEvent> ettur_muutub(String nupuks, int i, int j) {
        return event -> {
            laud.ettur_muutub(nupuks, (Ettur) laud.getLaud()[i][j]);
            käiguarv--;
            try {
                updateGridPane();
                writeToLog();
            } catch (IOException e) {
                e.printStackTrace();
            }
            juur.getChildren().removeAll(eemaldatavad);
            mang_kaib = true;
            käiguarv++;
        };
    }
    private static void kuva_valikud(int rida,int veerg) throws IOException {
        mang_kaib = false;
        eemaldatavad = new ArrayList<>();
        Button lipp = new Button("Lipp");
        lipp.setLayoutX(620);
        lipp.setLayoutY(30);
        lipp.setOnMouseClicked(ettur_muutub("Lipp", rida, veerg));

        Button oda = new Button("Oda");
        oda.setLayoutX(620);
        oda.setLayoutY(60);
        oda.setOnMouseClicked(ettur_muutub("Oda", rida, veerg));

        Button ratsu = new Button("Ratsu");
        ratsu.setLayoutX(620);
        ratsu.setLayoutY(90);
        ratsu.setOnMouseClicked(ettur_muutub("Ratsu", rida, veerg));

        Button vanker = new Button("Vanker");
        vanker.setLayoutX(620);
        vanker.setLayoutY(120);
        vanker.setOnMouseClicked(ettur_muutub("Vanker", rida, veerg));

        eemaldatavad.add(lipp);
        eemaldatavad.add(vanker);
        eemaldatavad.add(ratsu);
        eemaldatavad.add(oda);

        juur.getChildren().add(lipp);
        juur.getChildren().add(oda);
        juur.getChildren().add(vanker);
        juur.getChildren().add(ratsu);
    }

    /**
     *
     * @param malelaud ja kes just käis
     * @return true, kui vastasel pole legaalseid käike
     *           false, kui vastasel on mõni legaalne käik
     */
    public static boolean mangu_lopp (Malelaud malelaud, boolean valge_kais){
        ArrayList<int[]>vastase_legaalsed_kaigud = new ArrayList<>();
        Nupp vaadeldavnupp;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (malelaud.getLaud()[i][j]!=null){
                    vaadeldavnupp = malelaud.getLaud()[i][j];
                    if (valge_kais) {
                        if (malelaud.getLaud()[i][j].getVarv() == 'm') {
                            vastase_legaalsed_kaigud.addAll(legaalsus_filter(vaadeldavnupp.kaigud(malelaud),new int[]{i,j},malelaud, false));
                        }
                    }
                    else{
                        if (malelaud.getLaud()[i][j].getVarv() == 'v') {
                            vastase_legaalsed_kaigud.addAll(legaalsus_filter(vaadeldavnupp.kaigud(malelaud),new int[]{i,j}, malelaud, true));
                        }
                    }
                }
            }
        }
        return vastase_legaalsed_kaigud.size() == 0;
    }
    /**
     *
     * @param malelaud ja see kes just käis (true, kui just käis valge ja false, kui just käis must)
     * @return true, kui vastase kuningas on tule all
     *           false, kui vastase kuningas ei ole tule all
     */
    public static boolean vastasekuningas_tule_all(Malelaud malelaud, boolean valge_kais){
        ArrayList<int[]>minu_kaigud = malelaud.vastasekaigud(!valge_kais);
        int[]kuninga_asukoht = new int[2];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (malelaud.getLaud()[i][j] != null){
                    if (malelaud.getLaud()[i][j].getClass() == Kuningas.class){
                        if (valge_kais){
                            if (malelaud.getLaud()[i][j].getVarv() == 'm'){
                                kuninga_asukoht[0] = i;
                                kuninga_asukoht[1] = j;
                            }
                        }
                        else{
                            if (malelaud.getLaud()[i][j].getVarv() == 'v'){
                                kuninga_asukoht[0] = i;
                                kuninga_asukoht[1] = j;
                            }
                        }
                    }
                }
            }
        }
        return sisaldub(minu_kaigud, kuninga_asukoht);
    }

    public static void main(String[] args){
        launch(args);
        /*
        System.out.println("************************************************************************************************************");
        System.out.println("Male mängimise juhend: ");
        System.out.println("Mängimiseks kasutage nupu koordinaate kujul \"b7\".");
        System.out.println("Kõigepealt sisestage liigutatava nupu koordinaat.");
        System.out.println("Seejärel, kui selle nupuga on teil võimalik käia, väljastatakse teile selle nupu võimalikud sihtkoodinaadid.");
        System.out.println("Järgmiseks palun valige koordinaaadid, kuhu tes soovite selle nupuga liikuda.");
        System.out.println("************************************************************************************************************");
        System.out.println("Sisestage mängija 1 nimi: ");
        String mängija1 = sc.nextLine().trim();
        System.out.println("Sisetage mängija 2 nimi: ");
        String mängija2 = sc.nextLine().trim();
        String valge_mängija;
        String must_mängija;
        int coin_flip = (int)(Math.random() * 2);
        if (coin_flip == 1){
            valge_mängija = mängija1;
            must_mängija = mängija2;
            System.out.println(mängija1 + " mängib valgetega, " + mängija2 + " mängib mustadega!");
        }else {
            valge_mängija = mängija2;
            must_mängija= mängija1;
            System.out.println(mängija2 + " mängib valgetega, " + mängija1 + " mängib mustadega!");
        }
        System.out.println("************************************************************************************************************");

        System.out.println("Sisestage sõne \"char\", kui soovite kasutada väljastuses malenuppude päris formaati.");
        System.out.println("Sisestage sõne \"string\", kui soovite kasutada väljastuses malenuppude sõnena esitamise formaati. Näited allpool.");
        System.out.println("Näide malenuppude päris formaadist: ");
        laud.väljasta_char();
        System.out.println("Näide malenuppude sõnena esitamise formaadist: ");
        laud.väljasta();
        String väljastus;
        do {
            väljastus = sc.nextLine().trim();
            if (!(väljastus.equals("char") || väljastus.equals("string")))
                System.out.println("Sõne ei ole ei \"char\" ega \"string\", proovige uuesti.");
        }while (!(väljastus.equals("char") || väljastus.equals("string")));

        boolean välj_char;
        välj_char = väljastus.equals("char");

        System.out.println("************************************************************************************************************");
        System.out.println("Head mängu!");
        if (välj_char)
            laud.väljasta_char(); //Tõõtab kasutades unicode charactere
        else
            laud.väljasta();

        String kaik_string;
        int[] kaik_int1;
        int[] kaik_int2;
        Nupp vaadeldavnupp;
        ArrayList<int[]> legaalsed_kaigud;

        while(mang_kaib){
            if (valge_kaik)
                System.out.println(valge_mängija + ", Valge käik, alguskäik:");
            else
                System.out.println(must_mängija + ", Musta käik, alguskäik:");

            do {
                kaik_string = valideeri_käik();
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
                kaik_string = valideeri_käik();
                kaik_int2 = dekodeeri_kaik(kaik_string);
                if (! sisaldub(legaalsed_kaigud, kaik_int2))
                    System.out.println("Sinna ei saa selle nupuga käia! Sisestage uus sihtkoht: ");
            }
            while (! sisaldub(legaalsed_kaigud, kaik_int2));
            laud.liiguta(kaik_int1, kaik_int2);
            ettur_jõuab_lõppu();
            if (välj_char)
                laud.väljasta_char(); //Tõõtab kasutades unicode charactere
            else
                laud.väljasta();
            if (mangu_lopp(laud, valge_kaik)){
                if (vastasekuningas_tule_all(laud, valge_kaik)){
                    String võitja = (värv == 'v')? "Valge, " +  valge_mängija : "Must, " + must_mängija;
                    System.out.println("Mängu lõpp, " + võitja + " võitis!");
                }else
                    System.out.println("Mängu lõpp, mäng lõppes viigiga!");
                mang_kaib = false;
            }
            valge_kaik = !valge_kaik;
            värv = (valge_kaik) ? 'v' : 'm';
            käiguarv++;
       */
    }
}

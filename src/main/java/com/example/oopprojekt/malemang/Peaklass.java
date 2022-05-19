package com.example.oopprojekt.malemang;

import javafx.application.Application;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
    private static BorderPane juur;
    private static ArrayList<Node> eemaldatavad;
    private static Text tekstiväli;
    private static VBox nupud;
    private static String mängija1;
    private static String mängija2;

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
                        if (värv=='v')tekstiväli.setText("Mäng läbi, võitis: " + mängija1);
                        else tekstiväli.setText("Mäng läbi, võitis: " + mängija2);
                        try {
                            updateGridPane();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                    else{
                        tekstiväli.setText("Mäng läbi, viik");
                        try {
                            updateGridPane();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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
        double s = 70; // side of rectangle
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
        pane.setStyle("-fx-border-color: black;-fx-padding: 5, 0, 0, 0;");
        pane.setMaxSize(630, 630);
        gridPane = pane;
        juur.setCenter(gridPane);
    }
    public void mang_algab(Stage primaryStage)throws IOException{
        initLog();
        Button tagasi = new Button("Tagasi");
        tagasi.setFont(Font.font(20));
        tagasi.setOnMouseClicked(event -> {
            try {
                roll_Back();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        Button lahku = new Button("Lahku");
        lahku.setFont(Font.font(20));
        lahku.setOnMouseClicked(event -> System.exit(0));
        lahku.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE)
                System.exit(0);
        });

        tekstiväli = new Text();
        tekstiväli.setFont(Font.font(20));
        HBox tekst_abi = new HBox(tekstiväli);
        tekst_abi.setAlignment(Pos.TOP_LEFT);
        tekst_abi.setPrefHeight(10000);
        tekst_abi.setMaxWidth(630);

        nupud = new VBox(lahku, tagasi);
        nupud.setMaxHeight(500);
        nupud.setPrefWidth(10000);
        nupud.setAlignment(Pos.TOP_LEFT);
        nupud.setTranslateX(10);
        nupud.setSpacing(5);

        juur = new BorderPane();
        juur.setBottom(tekst_abi);
        juur.setRight(nupud);
        updateGridPane();
        juur.setLayoutX(5);

        primaryStage.setMinHeight(700);
        primaryStage.setMinWidth(700);
        primaryStage.setHeight(725);
        primaryStage.setWidth(775);

        updateGridPane();

        Scene scene = new Scene(juur);
        primaryStage.setTitle("Male");
        primaryStage.setScene(scene); // Place in scene in the stage
        primaryStage.show();
    }

    public void start(Stage primaryStage) throws IOException {
        GridPane algus = new GridPane();
        algus.setPrefSize(600,600);
        algus.setAlignment(Pos.CENTER);
        Button alusta = new Button("Alusta");
        alusta.setOnMouseClicked(event -> {
            primaryStage.close();
            try {
                nime_valikud(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        GridPane.setHalignment(alusta, HPos.CENTER);
        GridPane.setValignment(alusta, VPos.CENTER);
        algus.getChildren().add(alusta);
        Scene s = new Scene(algus);
        primaryStage.setTitle("Male");
        primaryStage.setScene(s);
        primaryStage.show();
    }

    private void nime_valikud(Stage primaryStage)throws IOException {
        TextField mängija_1=new TextField("Mängija 1");
        TextField mängija_2=new TextField("Mängija 2");
        Text valge=new Text("Valge");
        Text must=new Text("Must");
        GridPane valikud=new GridPane();
        valikud.setPrefSize(600,600);
        valikud.setAlignment(Pos.CENTER);
        Button alusta=new Button("Alusta mängimist");
        alusta.setOnMouseClicked(event -> {
            mängija1=mängija_1.getCharacters().toString();
            mängija2=mängija_2.getCharacters().toString();
            primaryStage.close();
            try {
                mang_algab(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        GridPane.setHalignment(alusta, HPos.CENTER);
        GridPane.setValignment(alusta, VPos.CENTER);
        valikud.add(valge,0,0);
        valikud.add(must,2,0);
        valikud.add(alusta,1,2);
        valikud.add(mängija_1,0,1);
        valikud.add(mängija_2,2,1);
        Scene s=new Scene(valikud);
        primaryStage.setTitle("Male");
        primaryStage.setScene(s);
        primaryStage.show();

    }

    public static int getKäiguarv() {
        return käiguarv;
    }

    public static void roll_Back() throws IOException {
        if (käiguarv == 1){
            tekstiväli.setText("Rohkem ei saa käike tagasi võtta");
            return;
        }
        käiguarv--;
        laud = find_from_log(käiguarv - 1);
        alguskoht = null;
        valge_kaik = !valge_kaik;
        värv = (valge_kaik) ? 'v' : 'm';
        if (!mang_kaib){
            nupud.getChildren().removeAll(eemaldatavad);
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

        tekstiväli.setText("Ettur jõudis lõppu! Valige milleks ettur muutub: 'Lipp', 'Oda', 'Vanker', 'Ratsu'");
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
            nupud.getChildren().removeAll(eemaldatavad);
            mang_kaib = true;
            käiguarv++;
        };
    }
    private static void kuva_valikud(int rida,int veerg) {
        mang_kaib = false;
        eemaldatavad = new ArrayList<>();

        Button lipp = new Button("Lipp");
        lipp.setFont(Font.font(20));
        lipp.setOnMouseClicked(ettur_muutub("Lipp", rida, veerg));

        Button oda = new Button("Oda");
        oda.setFont(Font.font(20));
        oda.setOnMouseClicked(ettur_muutub("Oda", rida, veerg));

        Button ratsu = new Button("Ratsu");
        ratsu.setFont(Font.font(20));
        ratsu.setOnMouseClicked(ettur_muutub("Ratsu", rida, veerg));

        Button vanker = new Button("Vanker");
        vanker.setFont(Font.font(20));
        vanker.setOnMouseClicked(ettur_muutub("Vanker", rida, veerg));

        eemaldatavad.add(lipp);
        eemaldatavad.add(vanker);
        eemaldatavad.add(ratsu);
        eemaldatavad.add(oda);

        nupud.getChildren().add(lipp);
        nupud.getChildren().add(oda);
        nupud.getChildren().add(vanker);
        nupud.getChildren().add(ratsu);
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
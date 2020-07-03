/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klase;

/**
 *
 * @author Coa
 */

import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Knjiga {
    private int id;
    private String naziv;
    private String autor;
    private int godinaIzdavanja;
    private String[] zanrovi;
    private boolean iznajmljena;

    public Knjiga(int id, String naziv, String autor, int godinaIzdavanja, String[] zanrovi, boolean iznajmljena) {
        this.id = id;
        this.naziv = naziv;
        this.autor = autor;
        this.godinaIzdavanja = godinaIzdavanja;
        this.zanrovi = zanrovi;
        this.iznajmljena = iznajmljena;
    }

    public static void snimi(ArrayList<?> lista) {
        JSONParser.snimi(lista, "knjiga.json");
    }

    public static ArrayList<Knjiga> citaj() {
        return (ArrayList<Knjiga>)JSONParser.citaj("knjiga.json", new TypeToken<ArrayList<Knjiga>>(){});
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getGodinaIzdavanja() {
        return godinaIzdavanja;
    }

    public void setGodinaIzdavanja(int godinaIzdavanja) {
        this.godinaIzdavanja = godinaIzdavanja;
    }

    public String[] getZanrovi() {
        return zanrovi;
    }

    public void setZanrovi(String[] zanrovi) {
        this.zanrovi = zanrovi;
    }

    public boolean isIznajmljena() {
        return iznajmljena;
    }

    public void setIznajmljena(boolean iznajmljena) {
        this.iznajmljena = iznajmljena;
    }

    @Override
    public String toString() {
        String zanroviString = "";
        for (int i = 0; i < zanrovi.length; i++) {
            zanroviString += zanrovi[i];
            if (i != zanrovi.length - 1) {
                zanroviString += " | ";
            }
        }
        return "ID: " + id + ", Naziv: " + naziv + ", Autor: " + autor + ", Godina izdavanja: " + godinaIzdavanja + ", Zanrovi: " + zanroviString + ", Iznajmljena: " + iznajmljena;
    }
    
    public static ArrayList<Knjiga> nadjiKnjigeZanra(String[] zanrovi) {
        ArrayList<Knjiga> knjige = citaj();
        ArrayList<Knjiga> knjigeZanra = new ArrayList<Knjiga>();
        if (knjige == null) {
            return knjigeZanra;
        }
        for (Knjiga k : knjige) {
            int brojZanrova = zanrovi.length;
            for (int i = 0; i < k.getZanrovi().length; i++) {
                for (int j = 0; j < zanrovi.length; j++) {
                    if (k.getZanrovi()[i].toLowerCase().equals(zanrovi[j].toLowerCase())) {
                        brojZanrova--;
                        break;
                    }
                }
            }
            if (brojZanrova == 0) {
                knjigeZanra.add(k);
            }
        }
        return knjigeZanra;
    }

    public static Knjiga nadjiKnjiguPoIdju(int id) {
        ArrayList<Knjiga> knjige = citaj();
        for (Knjiga k : knjige) {
            if (k.getId() == id) {
                return k;
            }
        }
        return null;
    }

    public static Knjiga nadjiKnjiguPoIdju(int id, ArrayList<Knjiga> trazeneKnjige) {
        for (Knjiga k : trazeneKnjige) {
            if (k.getId() == id) {
                return k;
            }
        }
        return null;
    }

    public static ArrayList<Knjiga> nadjiKnjigePoNazivu(String naziv) {
        ArrayList<Knjiga> knjige = citaj();
        ArrayList<Knjiga> knjigeSaTrazenimNazivom = new ArrayList<Knjiga>();
        for (Knjiga k : knjige) {
            if (k.getNaziv().toLowerCase().contains(naziv.toLowerCase())) {
                knjigeSaTrazenimNazivom.add(k);
            }
        }
        return knjigeSaTrazenimNazivom;
    }

    public static ArrayList<Knjiga> nadjiKnjigePoAutoru(String autor) {
        ArrayList<Knjiga> knjige = citaj();
        ArrayList<Knjiga> knjigeSaTrazenimAutorom = new ArrayList<Knjiga>();
        if (knjige == null) {
            return knjigeSaTrazenimAutorom;
        }
        for (Knjiga k : knjige) {
            if (k.getAutor().toLowerCase().equals(autor.toLowerCase())) {
                knjigeSaTrazenimAutorom.add(k);
            }
        }
        return knjigeSaTrazenimAutorom;
    }

    public static boolean ukloniKnjigu(Knjiga knjiga) {
        try {
            ArrayList<Knjiga> knjige = citaj();
            knjige.remove(knjiga);
            snimi(knjige);
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean ukloniKnjigu(int index) {
        try {
            ArrayList<Knjiga> knjige = citaj();
            knjige.remove(index);
            snimi(knjige);
            return true;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean promeniKnjigu(Knjiga knjiga) {
        boolean promenjen = false;
        ArrayList<Knjiga> knjige = citaj();
        for (int i = 0; i < knjige.size(); i++) {
            if (knjige.get(i).getId() == knjiga.getId()) {
                knjige.add(i, knjiga);
                knjige.remove(i + 1);
                //System.out.println(clanovi.get(i).toString());
                promenjen = true;
                break;
            }
        }
        if (promenjen) {
            snimi(knjige);
            return true;
        }
        else {
            return false;
        }
    }

    public static int nadjiIndexKnjigeIzListePoIdju(int id) {
        ArrayList<Knjiga> knjige = citaj();
        if (knjige == null) {
            return -1;
        }
        for (int i = 0; i < knjige.size(); i++) {
            if (knjige.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public static String[] unesiZanrove() {
        Scanner s = new Scanner(System.in);
        String zanroviS = "";
        while (true) {
            System.out.println("Unesite zanr (0 - nastavi).");
            String zanr = s.nextLine();
            if (zanr.equals("0")) {
                break;
            }
            zanroviS += zanr + ", ";
        }
        String[] zanrovi = zanroviS.split(", ");
        return zanrovi;
    }

    public static void azuriranjeKnjiga() throws ParseException {
        String opcija;
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("AZURIRANJE KNJIGA:\n"
                    + "-0 Glavni program\n"
                    + "-1 Dodaj novu knjigu\n"
                    + "-2 Obrisi knjigu\n"
                    + "-3 Azuriraj knjigu");
            System.out.println("Unesite opciju.");
            opcija = s.next();
            switch (opcija) {
                case "0":
                    return;
                case "1":
                    dodajNovuKnjigu();
                    break;
                case "2":
                    obrisiKnjigu();
                    break;
                case "3":
                    azurirajKnjigu();
                    break;
                default:
                    System.out.println("Nepostojeca opcija!");
                    break;
            }
        }

    }

    public static void azurirajKnjigu() throws ParseException {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("AZURIRAJ KNJIGU:\n"
                    + "0 - Vrati se\n"
                    + "1 - Trazi knjigu po nazivu");
            String opcija = s.nextLine();
            if (opcija.equals("0")) {
                return;
            }
            else if (opcija.equals("1")) {
                System.out.println("Unesite naziv knjige.");
                String naziv = s.nextLine();
                ArrayList<Knjiga> knjigeSaTrazenimNazivom = Knjiga.nadjiKnjigePoNazivu(naziv);
                if (knjigeSaTrazenimNazivom.size() == 0) {
                    System.out.println("Knjiga nije pronadjena!");
                    continue;
                }
                else {
                    System.out.println("Knjige sa trazenim nazivom:");
                    for (Knjiga ksn : knjigeSaTrazenimNazivom) {
                        System.out.println(ksn.toString());
                    }
                    System.out.println("Unesite ID knjige koju zelite da izmenite.");
                    int id = s.nextInt();
                    s.nextLine();
                    try {
                        Knjiga knjiga = Knjiga.nadjiKnjiguPoIdju(id, knjigeSaTrazenimNazivom);
                        if (knjiga != null) {
                            menjanjeAtributaKnjige(knjiga);
                        }
                        else {
                            System.out.println("Pogresan ID!");
                            continue;
                        }
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        continue;
                    }
                }
            }
            else {
                System.out.println("Nepostojeca opcija!");
                continue;
            }
        }

    }

    public static void menjanjeAtributaKnjige(Knjiga knjiga) throws ParseException {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("MENJANJE KNJIGE:\n" +
                    "0 - Vrati se (Odbaci promene)\n" +
                    "1 - Promeni naziv\n" +
                    "2 - Promeni autora\n" +
                    "3 - Promeni godinu izdavanja\n" +
                    "4 - Promeni zanrove\n" +
                    "5 - Sacuvaj promene");
            String opcija = s.next();
            s.nextLine();
            switch (opcija) {
                case "0":
                    return;
                case "1":
                    System.out.println("Unesite novi naziv knjige.");
                    String naziv = s.nextLine();
                    //s.nextLine();
                    knjiga.setNaziv(naziv);
                    break;
                case "2":
                    System.out.println("Unesite novog autora knjige.");
                    String autor = s.nextLine();
                    //s.nextLine();
                    knjiga.setAutor(autor);
                    break;
                case "3":
                    System.out.println("Unesite novu godinu izdavanja.");
                    int godinaIzdavanja = s.nextInt();
                    s.nextLine();
                    knjiga.setGodinaIzdavanja(godinaIzdavanja);
                    break;
                case "4":
                    String[] zanrovi = Knjiga.unesiZanrove();
                    knjiga.setZanrovi(zanrovi);
                    break;
                case "5":
                    try {
                        Knjiga.promeniKnjigu(knjiga);
                        System.out.println("Knjiga uspesno izmenjen.");
                        return;
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        return;
                    }
                default:
                    break;
            }
        }
    }

    public static void obrisiKnjigu()  {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("OBRISI KNJIGU:\n"
                    + "0 - Vrati se\n"
                    + "1 - Trazenje knjige po nazivu");
            String opcija = s.next();
            s.nextLine();
            if (opcija.equals("0")) {
                return;
            }
            else if (opcija.equals("1")) {
                System.out.println("Unesite naziv knjige.");
                String naziv = s.nextLine();
                ArrayList<Knjiga> knjigeSaTrazenimNazivom = Knjiga.nadjiKnjigePoNazivu(naziv);
                if (knjigeSaTrazenimNazivom.size() == 0) {
                    System.out.println("Knjiga nije pronadjen!");
                    continue;
                }
                else {
                    System.out.println("Knjige sa trazenim nazivom:");
                    for (Knjiga ksn : knjigeSaTrazenimNazivom) {
                        System.out.println(ksn.toString());
                    }
                    System.out.println("Unesite ID knjige koju zelite da obrisete.");
                    int id = s.nextInt();
                    s.nextLine();
                    try {
                        Knjiga knjiga = Knjiga.nadjiKnjiguPoIdju(id, knjigeSaTrazenimNazivom);
                        if (knjiga != null) {
                            if (Knjiga.ukloniKnjigu(knjigeSaTrazenimNazivom.indexOf(knjiga)) && PozamljenaKnjiga.ukloniPozajmljeneKnjigePoIdju(knjiga.getId())) {
                                System.out.println("Knjiga uspesno uklonjena.");
                            }
                        }
                        else {
                            System.out.println("Pogresan ID!");
                            continue;
                        }
                        continue;
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        continue;
                    }
                }
            }
            else {
                System.out.println("Nepostojeca opcija!");
                continue;
            }
        }
    }



    public static void dodajNovuKnjigu() throws ParseException {
        ArrayList<Knjiga> knjige;
        Scanner s = new Scanner(System.in);
        while (true) {
            int id;
            if ((knjige = Knjiga.citaj()) == null) {
                knjige = new ArrayList<Knjiga>();
                id = 0;
            }
            else {
                id = knjige.get(knjige.size() - 1).getId() + 1;
            }
            System.out.println("DODAJ NOVU KNJIGU:\n"
                    + "0 - Vrati se\n"
                    + "1 - Dodavanje nove knjige");
            int opcija = s.nextInt();
            s.nextLine();
            if (opcija == 0) {
                return;
            }
            else {
                System.out.println("Unesite naziv.");
                String naziv = s.nextLine();
                System.out.println("Unesite autora.");
                String autor = s.nextLine();
                System.out.println("Unesite godinu izdavanja.");
                int godinaIzdavanja = s.nextInt();
                s.nextLine();
                String[] zanrovi = Knjiga.unesiZanrove();
                Knjiga knjiga = new Knjiga(id, naziv, autor, godinaIzdavanja, zanrovi, false);
                knjige.add(knjiga);
                try {
                    Knjiga.snimi(knjige);
                    System.out.println("Uspesno dodavanje nove knjige.");
                    continue;
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    continue;
                }
            }
        }

    }

    public static void iznajmljivanjeKnjige(Clan clan) {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("IZNAJMLJIVANJE KNJIGE:\n"
                    + "0 - Vrati se\n"
                    + "1 - Pronadji knjigu po nazivu\n"
                    + "2 - Pronadji knjigu po autoru\n"
                    + "3 - Preporuci knjigu po zanrovima");
            String opcija = s.next();
            s.nextLine();
            if (opcija.equals("0")) {
                return;
            }
            else if (opcija.equals("1")) {
                System.out.println("Unesite naziv knjige.");
                String naziv = s.nextLine();
                ArrayList<Knjiga> knjigeSaTrazenimNazivom = Knjiga.nadjiKnjigePoNazivu(naziv);
                if (knjigeSaTrazenimNazivom.size() == 0) {
                    System.out.println("Knjiga sa trazenim nazivom nije nadjena!");
                    continue;
                }
                else {
                    System.out.println("Pronadjene knjige sa trazenim nazivom:");
                    for (Knjiga ksn : knjigeSaTrazenimNazivom) {
                        System.out.println(ksn.toString());
                    }
                    System.out.println("Unesite ID knjige koju zelite da pozajmite.");
                    int id = s.nextInt();
                    s.nextLine();
                    Knjiga knjiga = Knjiga.nadjiKnjiguPoIdju(id, knjigeSaTrazenimNazivom);
                    if (knjiga == null) {
                        System.out.println("Pogresan ID!");
                        continue;
                    }
                    else if (knjiga.isIznajmljena()) {
                        System.out.println("Ova knjiga je vec iznajmljena!");
                        continue;
                    }
                    else {
                        if (iznajmiKnjigu(clan, knjiga)) {
                            System.out.println("Knjiga uspesno pozajmljena.");
                            continue;
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
            else if (opcija.equals("2")) {
                System.out.println("Unesite ime autora.");
                String autor = s.nextLine();
                ArrayList<Knjiga> knjigeSaTrazenimAutorom = Knjiga.nadjiKnjigePoAutoru(autor);
                if (knjigeSaTrazenimAutorom.size() == 0) {
                    System.out.println("Knjiga sa trazenim autorom nije nadjena!");
                    continue;
                }
                else {
                    System.out.println("Ponadjene knjige sa trazenim autorom:");
                    for (Knjiga ksa : knjigeSaTrazenimAutorom) {
                        System.out.println(ksa.toString());
                    }
                    System.out.println("Unesite ID knjige koju zelite da pozajmite.");
                    int id = s.nextInt();
                    s.nextLine();
                    Knjiga knjiga = Knjiga.nadjiKnjiguPoIdju(id, knjigeSaTrazenimAutorom);
                    if (knjiga == null) {
                        System.out.println("Pogresan ID!");
                        continue;
                    }
                    else if (knjiga.isIznajmljena()) {
                        System.out.println("Ova knjiga je vec iznajmljena!");
                        continue;
                    }
                    else {
                        if (iznajmiKnjigu(clan, knjiga)) {
                            System.out.println("Knjiga uspesno pozajmljena.");
                            continue;
                        }
                        else {
                            continue;
                        }
                    }
                }
            }
            else if (opcija.equals("3")) {
                String[] zanrovi = Knjiga.unesiZanrove();
                ArrayList<Knjiga> knjigeSaTrazenimZanrovima = Knjiga.nadjiKnjigeZanra(zanrovi);
                if (knjigeSaTrazenimZanrovima.size() == 0) {
                    System.out.println("Nije pronadjena nijedna knjiga sa trazenim zanrovima!");
                    continue;
                }
                else {
                    System.out.println("Pronadjene knjige:");
                    for (Knjiga ksz : knjigeSaTrazenimZanrovima) {
                        System.out.println(ksz.toString());
                    }
                    System.out.println("Unesite ID knjige.");
                    int id = s.nextInt();
                    s.nextLine();
                    Knjiga knjiga = Knjiga.nadjiKnjiguPoIdju(id, knjigeSaTrazenimZanrovima);
                    if (knjiga == null) {
                        System.out.println("Pogresan ID!");
                        continue;
                    }
                    else if (knjiga.isIznajmljena()) {
                        System.out.println("Ova knjiga je vec iznajmljena!");
                        continue;
                    }
                    else {
                        if (iznajmiKnjigu(clan, knjiga)) {
                            System.out.println("Knjiga uspesno pozajmljena.");
                            continue;
                        }
                        else {
                            System.out.println("GRESKA");
                            continue;
                        }
                    }
                }
            }
            else {
                System.out.println("Nepostojeca opcija!");
                continue;
            }
        }
    }

    public static boolean iznajmiKnjigu(Clan clan, Knjiga knjiga) {
        ArrayList<PozamljenaKnjiga> pozajmljeneKnjige = PozamljenaKnjiga.citaj();
        if (pozajmljeneKnjige == null) {
            pozajmljeneKnjige = new ArrayList<PozamljenaKnjiga>();
            System.out.println("Pozajmljene = null");
        }
        pozajmljeneKnjige.add(new PozamljenaKnjiga(clan.getId(), LocalDate.now().toString(), knjiga.getId(), false, knjiga.getNaziv()));
        ArrayList<Knjiga> knjige = Knjiga.citaj();
        for (int i = 0; i < knjige.size(); i++) {
            System.out.println(i + " " + knjige.get(i).toString());
        }
        knjige.get(Knjiga.nadjiIndexKnjigeIzListePoIdju(knjiga.getId())).setIznajmljena(true);
        try {
            Knjiga.snimi(knjige);
            PozamljenaKnjiga.snimi(pozajmljeneKnjige);
            return true;
        }
        catch (Exception ex) {
            System.out.println("GRESKA!");;
            return false;
        }
    }

    public static void pregledNevracenihKnjiga() throws ParseException {
        ArrayList<PozamljenaKnjiga> pozamljenaKnjige = PozamljenaKnjiga.citaj();
        if (pozamljenaKnjige == null) {
            System.out.println("Nema nevracenih knjiga.");
            return;
        }
        else {
            for (PozamljenaKnjiga pk : pozamljenaKnjige) {
                if (!pk.isVraceno()) {
                    Date vremePozajmice = new SimpleDateFormat("yyyy-MM-dd").parse(pk.getVremePozajmice());
                    Calendar c = Calendar.getInstance();
                    c.setTime(vremePozajmice);
                    c.add(Calendar.DATE, 20);
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date rokZaVracanje = c.getTime();
                    System.out.println(pk.toString() + ", Rok za vracanje: " + dateFormat.format(rokZaVracanje));
                }
            }
        }
    }

    public static void vracanjeKnjige() {
        Scanner s = new Scanner(System.in);
        while (true) {
            Clan clan = Clan.proveriClana();
            if (clan == null) {
                return;
            }
            ArrayList<PozamljenaKnjiga> pozajmljeneKnjigeClana = PozamljenaKnjiga.nadjiNevraceneKnjigeClana(clan);
            if (pozajmljeneKnjigeClana.size() == 0) {
                System.out.println("Clan nema nevracenih knjiga!");
                return;
            }
            else {
                System.out.println("Nevracene knjige clana:");
                for (PozamljenaKnjiga pkc : pozajmljeneKnjigeClana) {
                    System.out.println(pkc.toString());
                }
                System.out.println("Unesite ID knjige koju clan vraca.");
                int id = s.nextInt();
                s.nextLine();
                PozamljenaKnjiga pozamljenaKnjiga = PozamljenaKnjiga.nadjiPozajmljenuKnjiguPoIdjevima(id, pozajmljeneKnjigeClana);
                if (pozamljenaKnjiga == null) {
                    System.out.println("Pogresan ID knjige!");
                    continue;
                }
                else {
                    pozamljenaKnjiga.setVraceno(true);
                    if (vratiKnjigu(pozamljenaKnjiga)) {
                        System.out.println("Knjiga uspesno vracena.");
                        continue;
                    }
                    else {
                        System.out.println("GRESKA!");
                        continue;
                    }
                }
            }
        }
    }

    public static boolean vratiKnjigu(PozamljenaKnjiga pozajmljenaKnjiga) {
        int indexPk = PozamljenaKnjiga.nadjiIndexPozajmljeneKnjige(pozajmljenaKnjiga.getId_knjige(), pozajmljenaKnjiga.getId_clana());
        if (indexPk != -1) {
            ArrayList<PozamljenaKnjiga> pozajmljeneKnjige = PozamljenaKnjiga.citaj();
            pozajmljeneKnjige.get(indexPk).setVraceno(true);
            ArrayList<Knjiga> knjige = Knjiga.citaj();
            if (knjige != null) {
                int indexK = Knjiga.nadjiIndexKnjigeIzListePoIdju(pozajmljenaKnjiga.getId_knjige());
                knjige.get(indexK).setIznajmljena(false);
                try {
                    PozamljenaKnjiga.snimi(pozajmljeneKnjige);
                    Knjiga.snimi(knjige);
                    return true;
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }
}
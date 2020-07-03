/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klase;

import com.google.gson.reflect.TypeToken;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author Coa
 */
public class Clan extends Korisnik implements Godine{

    private int id;
    private String poslednjaPlacenaClanarina;


    public String getPoslednjaPlacenaClanarina() {
        return poslednjaPlacenaClanarina;
    }

    public void setPoslednjaPlacenaClanarina(String poslednjaPlacenaClanarina) {
        this.poslednjaPlacenaClanarina = poslednjaPlacenaClanarina;
    }

    public Clan(int id, String ime, String prezime, int godinaRodenja, String telefom, String adresa, String email) {
        super(ime, prezime, godinaRodenja, telefom, adresa, email);
        this.id = id;
        poslednjaPlacenaClanarina = LocalDate.now().toString();
    }

    public Clan() {
        this.id = 0;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    @Override
    public String toString() {
        return "Clan{ ID: "+ id +" "+ super.toString() + ", Godine:"+ racunajGodine() + '}';
    }

    @Override
    public int racunajGodine() {
        return LocalDateTime.now().getYear() - super.godinaRodenja;
    }

    public static void snimi(ArrayList<?> lista) {
        JSONParser.snimi(lista, "clanovi.json");
        
    }

    public static ArrayList<Clan> citaj() {
        return (ArrayList<Clan>)JSONParser.citaj("clanovi.json", new TypeToken<ArrayList<Clan>>(){});
    }

    public static Clan nadjiClanaPoIdju(int id) {
        ArrayList<Clan> clanovi = citaj();
        for (Clan c : clanovi) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public static Clan nadjiClanaPoIdju(int id, ArrayList<Clan> trazeniClanovi) {
        for (Clan c : trazeniClanovi) {
            if (c.getId() == id) {
                return c;
            }
        }
        return null;
    }

    public static ArrayList<Clan> nadjiClanovePoImenuIPrezimenu(String ime, String prezime) {
        ArrayList<Clan> clanovi = citaj();
        ArrayList<Clan> clanoviSaTrazenimImenomIPrezimenom = new ArrayList<Clan>();
        for (Clan c : clanovi) {
            if (c.getIme().toLowerCase().equals(ime.toLowerCase()) && c.getPrezime().toLowerCase().equals(prezime.toLowerCase())) {
                clanoviSaTrazenimImenomIPrezimenom.add(c);
            }
        }
        return clanoviSaTrazenimImenomIPrezimenom;
    }

    public static void ukloniClana(Clan clan) {
        ArrayList<Clan> clanovi = citaj();
        clanovi.remove(clan);
        snimi(clanovi);
    }

    public static boolean promeniClana(Clan clan) {
        boolean promenjen = false;
        ArrayList<Clan> clanovi = citaj();
        for (int i = 0; i < clanovi.size(); i++) {
            if (clanovi.get(i).getId() == clan.getId()) {
                clanovi.add(i, clan);
                clanovi.remove(i + 1);
                //System.out.println(clanovi.get(i).toString());
                promenjen = true;
                break;
            }
        }
        if (promenjen) {
            snimi(clanovi);
            return true;
        }
        else {
            return false;
        }
    }

    public static void azuriranjeClanova() {
        String opcija;
        while (true) {
            Scanner s = new Scanner(System.in);
            System.out.println("AZURIRANJE CLANOVA:\n"
                    + "-0 Glavni program\n"
                    + "-1 Dodaj novog clana\n"
                    + "-2 Obrisi clana\n"
                    + "-3 Azuriraj clana");
            System.out.println("Unesite opciju.");
            opcija = s.next();
            switch (opcija) {
                case "0":
                    return;
                case "1":
                    dodajNovogClana();
                    break;
                case "2":
                    obrisiClana();
                    break;
                case "3":
                    azurirajClana();
                    break;
                default:
                    continue;
            }
        }
    }

    public static void dodajNovogClana() {
        ArrayList<Clan> clanovi;
        Scanner s = new Scanner(System.in);
        while (true) {
            int id;
            if ((clanovi = citaj()) == null) {
                clanovi = new ArrayList<Clan>();
                id = 0;
            }
            else {
                id = clanovi.get(clanovi.size() - 1).getId() + 1;
            }
            System.out.println("DODAJ NOVOG CLANA:\n"
                    + "0 - Vrati se\n"
                    + "1 - Dodavanje novog clana");
            String opcija = s.next();
            s.nextLine();
            if (opcija.equals("0")) {
                return;
            }
            else if (opcija.equals("1")){
                System.out.println("Unesite ime.");
                String ime = s.nextLine();
                System.out.println("Unesite prezime.");
                String prezime = s.nextLine();
                System.out.println("Unesite godinu rodjenja.");
                int godinaRodjenja = s.nextInt();
                System.out.println("Unesite telefon.");
                String telefon = s.next();
                char[] telefonC = telefon.toCharArray();
                telefon = "";
                for (int i = 0; i < telefonC.length; i++) {
                    if (Character.isDigit(telefonC[i])) {
                        telefon += telefonC[i];
                    }
                }
                s.nextLine();
                System.out.println("Unesite adresu.");
                String adresa = s.nextLine();
                System.out.println("Unesite email.");
                String email = s.nextLine();
                Clan clan = new Clan(id, ime, prezime, godinaRodjenja, telefon, adresa, email);
                clanovi.add(clan);
                try {
                    Clan.snimi(clanovi);
                    System.out.println("Uspesno dodavanje novog clana.");
                    continue;
                }
                catch (Exception ex) {
                    System.out.println("GRESKA");
                    continue;
                }
            }
        }

    }

    public static void obrisiClana() {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("OBRISI CLANA:\n"
                    + "0 - Vrati se\n"
                    + "1 - Trazenje clana po ID-ju\n"
                    + "2 - Trazenje clana po imenu i prezimenu");
            String opcija = s.next();
            s.nextLine();
            if (opcija.equals("0")) {
                return;
            }
            else if (opcija.equals("1")) {
                System.out.println("Unesite ID clana.");
                int id = s.nextInt();
                Clan clan = Clan.nadjiClanaPoIdju(id);
                if (clan == null) {
                    System.out.println("ID nije pronadjen!");
                    continue;
                }
                else {
                    try {
                        Clan.ukloniClana(clan);
                        System.out.println("Clan uspesno uklonjen.");
                        continue;
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                        System.out.println("GRESKA");
                        continue;
                    }
                }
            }
            else if (opcija.equals("2")) {
                System.out.println("Unesite ime clana.");
                String ime = s.nextLine();
                System.out.println("Unesite prezime clana.");
                String prezime = s.nextLine();
                ArrayList<Clan> clanoviSaTrazenimImenomIPrezimenom = nadjiClanovePoImenuIPrezimenu(ime, prezime);
                if (clanoviSaTrazenimImenomIPrezimenom.size() == 0) {
                    System.out.println("Clan sa trazenim imenom i prezimenom nije pronadjen!");
                    obrisiClana();
                }
                else {
                    System.out.println("Pronadjeni clanovi:");
                    for (Clan csz : clanoviSaTrazenimImenomIPrezimenom) {
                        System.out.println(csz.toString());
                    }
                    System.out.println("Unesite ID clana za brisanje.");
                    int id = s.nextInt();
                    s.nextLine();
                    Clan clan = Clan.nadjiClanaPoIdju(id);
                    if (clan == null) {
                        System.out.println("Pogresan ID!");
                        continue;
                    }
                    else {
                        try {
                            ukloniClana(clan);
                            System.out.println("Clan uspesno uklonjen.");
                            continue;
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                            System.out.println("GRESKA");
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
    public static void azurirajClana() {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("AZURIRAJ CLANA:\n"
                    + "0 - Vrati se\n"
                    + "1 - Trazi clana po ID-ju\n"
                    + "2 - Trazi clana po imenu i prezimenu");
            String opcija = s.nextLine();
            if (opcija.equals("0")) {
                return;
            }
            else if (opcija.equals("1")) {
                System.out.println("Unesite ID clana.");
                int id = s.nextInt();
                s.nextLine();
                Clan clan = Clan.nadjiClanaPoIdju(id);
                if (clan == null) {
                    System.out.println("Nije pronadjen clan sa trazenim ID-jem!");
                    azurirajClana();
                }
                else {
                    menjanjeAtributaClana(clan);
                }
            }
            else if (opcija.equals("2")) {
                System.out.println("Unesite ime clana.");
                String ime = s.nextLine();
                System.out.println("Unesite prezime clana.");
                String prezime = s.nextLine();
                ArrayList<Clan> clanoviSaTrazenimImenomIPrezimenom = Clan.nadjiClanovePoImenuIPrezimenu(ime, prezime);
                if (clanoviSaTrazenimImenomIPrezimenom.size() == 0) {
                    System.out.println("Clan sa trazenim imenom i prezimenom nije nadjen!");
                    continue;
                }
                else {
                    System.out.println("Pronadjeni clanovi:");
                    for (Clan csi : clanoviSaTrazenimImenomIPrezimenom) {
                        System.out.println(csi.toString());
                    }
                    System.out.println("Unesite ID clana.");
                    int id = s.nextInt();
                    s.nextLine();
                    Clan clan = Clan.nadjiClanaPoIdju(id, clanoviSaTrazenimImenomIPrezimenom);
                    if (clan == null) {
                        System.out.println("Pogresan ID!");
                        continue;
                    }
                    else {
                        menjanjeAtributaClana(clan);
                    }
                }
            }
            else {
                System.out.println("Nepostojeca opcija!");
                continue;
            }
        }

    }

    public static void menjanjeAtributaClana(Clan clan) {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("MENJANJE CLANA:\n" +
                    "0 - Azuriraj drugog clana (Odbaci promene)\n" +
                    "1 - Promeni ime\n" +
                    "2 - Promeni prezime\n" +
                    "3 - Promeni godiste\n" +
                    "4 - Promeni telefon\n" +
                    "5 - Promeni adresu\n" +
                    "6 - Promeni email\n" +
                    "7 - Sacuvaj promene");
            String opcija = s.next();
            s.nextLine();
            switch (opcija) {
                case "0":
                    return;
                case "1":
                    System.out.println("Unesite novo ime clana.");
                    String ime = s.nextLine();
                    //s.nextLine();
                    clan.setIme(ime);
                    break;
                case "2":
                    System.out.println("Unesite novo prezime clana.");
                    String prezime = s.nextLine();
                    //s.nextLine();
                    clan.setPrezime(prezime);
                    break;
                case "3":
                    System.out.println("Unesite novo godiste clana.");
                    int godiste = s.nextInt();
                    s.nextLine();
                    clan.setGodinaRodenja(godiste);
                    break;
                case "4":
                    System.out.println("Unesite novi telefon clana.");
                    String telefon = s.next();
                    char[] telefonC = telefon.toCharArray();
                    telefon = "";
                    for (int i = 0; i < telefonC.length; i++) {
                        if (Character.isDigit(telefonC[i])) {
                            telefon += telefonC[i];
                        }
                    }
                    s.nextLine();
                    clan.setTelefom(telefon);
                    break;
                case "5":
                    System.out.println("Unesite novu adresu clana.");
                    String adresa = s.nextLine();
                    //s.nextLine();
                    clan.setAdresa(adresa);
                    break;
                case "6":
                    System.out.println("Unesite novi email clana.");
                    String email = s.next();
                    s.nextLine();
                    clan.setEmail(email);
                    break;
                case "7":
                    try {
                        Clan.promeniClana(clan);
                        System.out.println("Clan uspesno izmenjen.");
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

    public static Clan proveriClana() {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("PROVERA CLANA:\n"
                    + "0 - Vrati se\n"
                    + "1 - Pronadji clana po ID-ju\n"
                    + "2 - Pronadji clana po imenu i prezimenu");
            String opcija = s.next();
            s.nextLine();
            if (opcija.equals("0")) {
                return null;
            }
            else if (opcija.equals("1")) {
                System.out.println("Unesite ID clana.");
                int id = s.nextInt();
                s.nextLine();
                Clan clan = Clan.nadjiClanaPoIdju(id);
                if (clan == null) {
                    System.out.println("Nepostojeci ID!");
                    continue;
                }
                else {
                    System.out.println("Clan pronadjen.");
                    return clan;
                }
            }
            else if (opcija.equals("2")) {
                System.out.println("Unesite ime clana.");
                String ime = s.nextLine();
                System.out.println("Unesite prezime clana.");
                String prezime = s.nextLine();
                ArrayList<Clan> clanoviSaTrazenimImenomIPrezimenom = Clan.nadjiClanovePoImenuIPrezimenu(ime, prezime);
                if (clanoviSaTrazenimImenomIPrezimenom.size() == 0) {
                    System.out.println("Nije nadjen clan sa trazenim imenom i prezimenom!");
                    continue;
                }
                else {
                    System.out.println("Nadjeni clanovi:");
                    for (Clan csi : clanoviSaTrazenimImenomIPrezimenom) {
                        System.out.println(csi.toString());
                    }
                    System.out.println("Unesite ID clana.");
                    int id = s.nextInt();
                    s.nextLine();
                    Clan clan = Clan.nadjiClanaPoIdju(id, clanoviSaTrazenimImenomIPrezimenom);
                    if (clan == null) {
                        System.out.println("Pogresan ID!");
                        continue;
                    }
                    else {
                        System.out.println("Clan pronadjen.");
                        return clan;
                    }
                }
            }
            else {
                System.out.println("Nepostojeca opcija!");
                continue;
            }
        }
    }

    public static void placanjeClanarine() throws ParseException {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("PLACANJE CLANARINE:\n"
                    + "0 - Vrati se\n"
                    + "1 - Trazi clana po ID-ju\n"
                    + "2 - Trazi clana po imenu i prezimenu");
            String opcija = s.next();
            s.nextLine();
            if (opcija.equals("0")) {
                return;
            }
            else if (opcija.equals("1")) {
                System.out.println("Unesite ID clana.");
                int id = s.nextInt();
                Clan clan = Clan.nadjiClanaPoIdju(id);
                if (clan == null) {
                    System.out.println("Uneli ste nepostojeci ID!");
                    continue;
                }
                else {
                    Date poslednjaPlacenaClanarina = new SimpleDateFormat("yyyy-MM-dd").parse(clan.getPoslednjaPlacenaClanarina());
                    Date danasnji = new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().toString());
                    if (poslednjaPlacenaClanarina.getYear() == danasnji.getYear() && poslednjaPlacenaClanarina.getMonth() == danasnji.getMonth()) {
                        System.out.println("Clanarina za tekuci mesec je vec placena!");
                        continue;
                    }
                    else {
                        try {
                            clan.setPoslednjaPlacenaClanarina(LocalDate.now().toString());
                            Clan.promeniClana(clan);
                            System.out.println(clan + "\n Clanarina uspesno placena.");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            continue;

                        }
                    }
                }
            }
            else if (opcija.equals("2")) {
                System.out.println("Unesite ime clana.");
                String ime = s.nextLine();
                System.out.println("Unesite prezime clana.");
                String prezime = s.nextLine();
                ArrayList<Clan> clanoviSaTrazenimImenomIPrezimenom = Clan.nadjiClanovePoImenuIPrezimenu(ime, prezime);
                if (clanoviSaTrazenimImenomIPrezimenom.size() == 0) {
                    System.out.println("Ne postoji clan sa trazenim imenom i prezimenom!");
                    continue;
                }
                else {
                    System.out.println("Clanovi sa trazenim imenom i prezimenom:");
                    for (Clan csi : clanoviSaTrazenimImenomIPrezimenom) {
                        System.out.println(csi.toString());
                    }
                    System.out.println("Unesite ID clana.");
                    int id = s.nextInt();
                    s.nextLine();
                    Clan clan = Clan.nadjiClanaPoIdju(id, clanoviSaTrazenimImenomIPrezimenom);
                    if (clan == null) {
                        System.out.println("Pogresan ID!");
                        continue;
                    }
                    else {
                        Date poslednjaPlacenaClanarina = new SimpleDateFormat("yyyy-MM-dd").parse(clan.getPoslednjaPlacenaClanarina());
                        Date danasnji = new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().toString());
                        if (poslednjaPlacenaClanarina.getYear() == danasnji.getYear() && poslednjaPlacenaClanarina.getMonth() == danasnji.getMonth()) {
                            System.out.println("Clanarina za tekuci mesec je vec placena!");
                            continue;
                        }
                        else {
                            try {
                                clan.setPoslednjaPlacenaClanarina(LocalDate.now().toString());
                                Clan.promeniClana(clan);
                                System.out.println("Clanarina uspesno placena.");
                            }
                            catch (Exception ex) {
                                ex.printStackTrace();
                                placanjeClanarine();
                            }
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

    public static boolean proveraClanarine(Clan clan) throws ParseException {
        Date poslednjaPlacenaClanarina = new SimpleDateFormat("yyyy-MM-dd").parse(clan.getPoslednjaPlacenaClanarina());
        Date danasnji = new SimpleDateFormat("yyyy-MM-dd").parse(LocalDate.now().toString());
        if (poslednjaPlacenaClanarina.getMonth() < danasnji.getMonth()) {
            System.out.println("Clan nije platio clanarinu za tekuci mesec!");
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean daliJeClanVratioKnjigu(int id_clan) throws ParseException
    {
        ArrayList<PozamljenaKnjiga> lista = PozamljenaKnjiga.citaj();
        String NevraceneKnjige ="";
        boolean nevraceno = false;
        for(PozamljenaKnjiga k : lista )
        {
            if (k.getId_clana() == id_clan)
            {
                if (k.isVraceno() != true)
                {
                    Date DatumVracanja = new SimpleDateFormat("yyyy-MM-dd").parse(k.getVremePozajmice());
                    if ((LocalDateTime.now().getDayOfYear() - DatumVracanja.getDay()) > 30)
                    {
                        NevraceneKnjige += k + "\n";
                        nevraceno = true;
                    }
                }
            }
        }
        if (nevraceno)
        {
            System.out.println("Nevracene knjige : \n"+ NevraceneKnjige);
            return false;
        }
        else
        {
            return true;
        }
    }

    public static void pregledPozajmljenihKnjigaClana(Clan clan) {
        if (clan == null) {
            return;
        }
        ArrayList<PozamljenaKnjiga> pozamljenaKnjigeClana = PozamljenaKnjiga.nadjiPozajmljeneKnjigeClana(clan.getId());
        if (pozamljenaKnjigeClana == null) {
            System.out.println("Clan nema pozajmljenih knjiga.");
            return;
        }
        else {
            for (PozamljenaKnjiga pkc : pozamljenaKnjigeClana) {
                System.out.println(pkc.toString());
            }
        }
        return;
    }
}

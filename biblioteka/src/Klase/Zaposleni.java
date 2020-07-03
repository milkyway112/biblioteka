/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klase;

import com.google.gson.reflect.TypeToken;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Coa
 */
public class Zaposleni extends Korisnik implements Godine{
    private String korisnikoIme;
    private String Sifra;

    public Zaposleni(String korisnikoIme, String Sifra, String ime, String prezime, int godinaRodenja, String telefom, String adresa, String email) {
        super(ime, prezime, godinaRodenja, telefom, adresa, email);
        this.korisnikoIme = korisnikoIme;
        this.Sifra = Sifra;
    }

    public Zaposleni() {
        this.korisnikoIme = "";
        this.Sifra = "";
    }

    
    public static void snimi(ArrayList<?> lista) {
        JSONParser.snimi(lista, "zaposleni.json");
        
    }

    public static ArrayList<Zaposleni> citaj() {
        return (ArrayList<Zaposleni>)JSONParser.citaj("zaposleni.json", new TypeToken<ArrayList<Zaposleni>>(){});
    }
    public String getKorisnikoIme() {
        return korisnikoIme;
    }

    public void setKorisnikoIme(String korisnikoIme) {
        this.korisnikoIme = korisnikoIme;
    }

    public String getSifra() {
        return Sifra;
    }

    public void setSifra(String Sifra) {
        this.Sifra = Sifra;
    }



    @Override
    public String toString() {
        return "Zaposleni{" + super.toString() + " korisnikoIme=" + korisnikoIme + ", Sifra=" + Sifra + ", Godine:"+ racunajGodine() + '}';
    }

    @Override
    public int racunajGodine() {
        return LocalDateTime.now().getYear() - super.godinaRodenja;
    }
    /*public static boolean proveriUsernameIPassword(String username, String password, ArrayList<Zaposleni> zaposleni) {
        for (Zaposleni z : zaposleni) {
            if (username.equals(z.getKorisnikoIme()) && password.equals(z.getSifra())) {
                return true;
            }
        }
        return false;
    }*/

    public static Zaposleni proveriUsernameIPassword(String username, String password) {
        ArrayList<Zaposleni> zaposleni = citaj();
        if (zaposleni == null) {
            return null;
        }
        for (Zaposleni z : zaposleni) {
            if (z.korisnikoIme.equals(username) && z.Sifra.equals(password)) {
                System.out.println("Dobrodosli, " + z.ime + " " + z.prezime);
                return z;
            }
        }
        return null;
    }

    public static void azuriranjeZaposlenih(Zaposleni z) {
        String opcija;
        
        if(!z.getIme().toLowerCase().equals("Admin".toLowerCase()))
        {
            System.out.println("Nemate pravo da azurirate zaposlene");
            return;
        }
        
        while (true) {
            Scanner s = new Scanner(System.in);
            System.out.println("AZURIRANJE ZAPOSLENIH:\n"
                    + "-0 Glavni program\n"
                    + "-1 Dodaj novog zaposlenog\n"
                    + "-2 Obrisi zaposlenog\n"
                    + "-3 Azuriraj zaposlenog");
            System.out.println("Unesite opciju.");
            opcija = s.next();
            switch (opcija) {
                case "0":
                    return;
                case "1":
                    dodajNovogZaposlenog();
                    break;
                case "2":
                    obrisiZaposlenog(z);
                    break;
                case "3":
                    azurirajZaposlenog();
                    break;
                default:
                    continue;
            }
        }
    }

    public static void obrisiZaposlenog(Zaposleni z) {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("OBRISI ZAPOSLENOG:\n"
                    + "0 - Vrati se\n"
                    + "1 - Trazenje clana po username-u\n"
                    + "2 - Trazenje clana po imenu i prezimenu");
            String opcija = s.next();
            s.nextLine();
            if (opcija.equals("0")) {
                return;
            }
            else if (opcija.equals("1")) {
                System.out.println("Unesite username zaposlenog.");
                String username = s.nextLine();
                if (username.equals(z.getKorisnikoIme())) {
                    System.out.println("Ne mozete obrisati svoj korisnicki nalog.");
                    continue;
                }
                Zaposleni z1 = nadjiZaposlenogPoUsernameu(username);
                if (z1 == null) {
                    System.out.println("Username nije pronadjen!");
                    continue;
                }
                else {
                    try {
                        ukloniZaposlenog(z1);
                        System.out.println("Zaposleni uspesno uklonjen.");
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
                System.out.println("Unesite ime zaposlenog.");
                String ime = s.nextLine();
                System.out.println("Unesite prezime zaposlenog.");
                String prezime = s.nextLine();
                ArrayList<Zaposleni> zaposleniSaTrazenimImenomIPrezimenom = nadjiZaposlenePoImenuIPrezimenu(ime, prezime);
                if (zaposleniSaTrazenimImenomIPrezimenom.size() == 0) {
                    System.out.println("Zaposleni sa trazenim imenom i prezimenom nije pronadjen!");
                    continue;
                }
                else {
                    System.out.println("Pronadjeni zaposleni:");
                    for (Zaposleni zsi : zaposleniSaTrazenimImenomIPrezimenom) {
                        System.out.println(zsi.toString());
                    }
                    System.out.println("Unesite username zaposlenog za brisanje.");
                    String username = s.nextLine();
                    if (username.equals(z.getKorisnikoIme())) {
                        System.out.println("Ne mozete obrisati svoj korisnicki nalog.");
                        continue;
                    }s.nextLine();
                    Zaposleni z1 = nadjiZaposlenogPoUsernameu(username);
                    if (z1 == null) {
                        System.out.println("Pogresan username!");
                        continue;
                    }
                    else {
                        try {
                            ukloniZaposlenog(z1);
                            System.out.println("Zaposleni uspesno uklonjen.");
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

    public static void azurirajZaposlenog() {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("AZURIRAJ ZAPOSLENOG:\n"
                    + "0 - Vrati se\n"
                    + "1 - Trazi zaposlenog po username-u\n"
                    + "2 - Trazi zaposlenog po imenu i prezimenu");
            String opcija = s.nextLine();
            if (opcija.equals("0")) {
                return;
            }
            else if (opcija.equals("1")) {
                System.out.println("Unesite username zaposlenog.");
                String username = s.nextLine();
                Zaposleni z1 = nadjiZaposlenogPoUsernameu(username);
                if (z1 == null) {
                    System.out.println("Nije pronadjen zaposleni sa trazenim username-om!");
                    continue;
                }
                else {
                    menjanjeAtributaZaposlenog(z1);
                }
            }
            else if (opcija.equals("2")) {
                System.out.println("Unesite ime zaposlenog.");
                String ime = s.nextLine();
                System.out.println("Unesite prezime zaposlenog.");
                String prezime = s.nextLine();
                ArrayList<Zaposleni> zaposleniSaTrazenimImenomIPrezimenom = nadjiZaposlenePoImenuIPrezimenu(ime, prezime);
                if (zaposleniSaTrazenimImenomIPrezimenom.size() == 0) {
                    System.out.println("Zaposleni sa trazenim imenom i prezimenom nije nadjen!");
                    continue;
                }
                else {
                    System.out.println("Pronadjeni zaposleni:");
                    for (Zaposleni zsi : zaposleniSaTrazenimImenomIPrezimenom) {
                        System.out.println(zsi.toString());
                    }
                    System.out.println("Unesite uzername zaposlenog.");
                    String username = s.nextLine();
                    Zaposleni z1 = nadjiZaposlenogPoUsernameu(username);
                    if (z1 == null) {
                        System.out.println("Pogresan username!");
                        continue;
                    }
                    else {
                        menjanjeAtributaZaposlenog(z1);
                    }
                }
            }
            else {
                System.out.println("Nepostojeca opcija!");
                continue;
            }
        }
    }

    public static void menjanjeAtributaZaposlenog(Zaposleni z1) {
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("MENJANJE CLANA:\n" +
                    "0 - Azuriraj drugog zaposlenog (Odbaci promene)\n" +
                    "1 - Promeni password\n" +
                    "2 - Promeni ime\n" +
                    "3 - Promeni prezime\n" +
                    "4 - Promeni godiste\n" +
                    "5 - Promeni telefon\n" +
                    "6 - Promeni adresu\n" +
                    "7 - Promeni email\n" +
                    "8 - Sacuvaj promene");
            String opcija = s.next();
            s.nextLine();
            switch (opcija) {
                case "0":
                    return;
                case "1":
                    System.out.println("Unesite novi password zaposlenog (mora sadrzati barem 6 karaktera).");
                    String password = s.nextLine();
                    if (password.length() < 6) {
                        System.out.println("Nema dovoljno karaktera!");
                        break;
                    }
                    z1.setSifra(password);
                case "2":
                    System.out.println("Unesite novo ime zaposlenog.");
                    String ime = s.nextLine();
                    z1.setIme(ime);
                    break;
                case "3":
                    System.out.println("Unesite novo prezime zaposlenog.");
                    String prezime = s.nextLine();
                    z1.setPrezime(prezime);
                    break;
                case "4":
                    System.out.println("Unesite novo godiste zaposlenog.");
                    int godiste = s.nextInt();
                    s.nextLine();
                    z1.setGodinaRodenja(godiste);
                    break;
                case "5":
                    System.out.println("Unesite novi telefon zaposlenog.");
                    String telefon = s.next();
                    char[] telefonC = telefon.toCharArray();
                    telefon = "";
                    for (int i = 0; i < telefonC.length; i++) {
                        if (Character.isDigit(telefonC[i])) {
                            telefon += telefonC[i];
                        }
                    }
                    s.nextLine();
                    z1.setTelefom(telefon);
                    break;
                case "6":
                    System.out.println("Unesite novu adresu zaposlenog.");
                    String adresa = s.nextLine();
                    z1.setAdresa(adresa);
                    break;
                case "7":
                    System.out.println("Unesite novi email zaposlenog.");
                    String email = s.next();
                    s.nextLine();
                    z1.setEmail(email);
                    break;
                case "8":
                    try {
                        promeniZaposlenog(z1);
                        System.out.println("Zaposleni uspesno izmenjen.");
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

    public static Zaposleni nadjiZaposlenogPoUsernameu(String username) {
        ArrayList<Zaposleni> zaposleni = citaj();
        for (Zaposleni z : zaposleni) {
            if (z.getKorisnikoIme() == username) {
                return z;
            }
        }
        return null;
    }

    public static void dodajNovogZaposlenog() {
        ArrayList<Zaposleni> zaposleni;
        Scanner s = new Scanner(System.in);
        while (true) {
            System.out.println("DODAJ NOVOG ZAPOSLENOG:\n"
                    + "0 - Vrati se\n"
                    + "1 - Dodavanje novog zaposlenog");
            String opcija = s.next();
            s.nextLine();
            if (opcija.equals("0")) {
                return;
            }
            else if (opcija.equals("1")){
                zaposleni = citaj();
                String username = "";
                while (true) {
                    System.out.println("Unesite username (duzina mora da bude veca od 5.");
                    username = s.nextLine();
                    if (username.length() < 6) {
                        continue;
                    }
                    if (!proveriDaliJeUsernameZauzet(username)) {
                        break;
                    }
                }
                String password = "";
                while (password.length() < 6) {
                    System.out.println("Unesite password. (Mora imati bar 6 karaktera.");
                    password = s.nextLine();
                }
                System.out.println("Unesite ime zaposlenog.");
                String ime = s.nextLine();
                System.out.println("Unesite prezime zaposlenog.");
                String prezime = s.nextLine();
                System.out.println("Unesite godinu rodjenja.");
                int godinaRodjenja = s.nextInt();
                s.nextLine();
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
                Zaposleni z = new Zaposleni(username, password, ime, prezime, godinaRodjenja, telefon, adresa, email);
                zaposleni.add(z);
                try {
                    snimi(zaposleni);
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

    public static void ukloniZaposlenog(Zaposleni z) {
        ArrayList<Zaposleni> zaposleni = citaj();
        zaposleni.remove(z);
        snimi(zaposleni);
    }

    public static ArrayList<Zaposleni> nadjiZaposlenePoImenuIPrezimenu(String ime, String prezime) {
        ArrayList<Zaposleni> zaposleni = citaj();
        ArrayList<Zaposleni> zaposleniSaTrazenimImenomIPrezimenom = new ArrayList<Zaposleni>();
        for (Zaposleni z : zaposleni) {
            if (z.getIme().toLowerCase().equals(ime.toLowerCase()) && z.getPrezime().toLowerCase().equals(prezime.toLowerCase())) {
                zaposleniSaTrazenimImenomIPrezimenom.add(z);
            }
        }
        return zaposleniSaTrazenimImenomIPrezimenom;
    }
    public static boolean proveriDaliJeUsernameZauzet(String username) {
        ArrayList<Zaposleni> zaposleni = citaj();
        for (Zaposleni z : zaposleni) {
            if (z.korisnikoIme.equals(username)) {
                System.out.println("Ovaj username je vec zauzet!");
                return true;
            }
        }
        return false;
    }

    public static boolean promeniZaposlenog(Zaposleni z) {
        boolean promenjen = false;
        ArrayList<Zaposleni> zaposleni = citaj();
        for (int i = 0; i < zaposleni.size(); i++) {
            if (zaposleni.get(i).getKorisnikoIme().equals(z.getKorisnikoIme())) {
                zaposleni.add(i, z);
                zaposleni.remove(i + 1);
                //System.out.println(clanovi.get(i).toString());
                promenjen = true;
                break;
            }
        }
        if (promenjen) {
            snimi(zaposleni);
            return true;
        }
        else {
            return false;
        }
    }
}

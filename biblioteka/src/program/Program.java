package program;

import Klase.Clan;
import Klase.Knjiga;
import Klase.Zaposleni;

import java.text.ParseException;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) throws ParseException {
        Scanner s = new Scanner(System.in);
        Zaposleni z;
        while (true) {
            String username;
            String password;
            System.out.println("username:Admin, password:123 \nUnesite username (0 za izlazak iz programa).");
            username = s.nextLine();
            if (username.equals("0")) {
                System.exit(0);
            }
            System.out.println("Unesite password.");
            password = s.nextLine();
            z = new Zaposleni();
            if ((z = Zaposleni.proveriUsernameIPassword(username, password)) != null) {
                break;
            }
        }
        String opcija;
        while (true) {
            System.out.println("--------------------------\n"
                    + "Opcije: \n"
                    + "-0 Izlazak iz programa\n"
                    + "-1 Azuriranje clanova\n"
                    + "-2 Azuriranje zaposlenih\n"
                    + "-3 Azuriranje knjiga\n"
                    + "-4 Placanje clanarine\n"
                    + "-5 Iznajmljivanje knjige\n"
                    + "-6 Vracanje knjige\n"
                    + "-7 Pregled nevracenih knjiga\n"
                    + "-8 Pregled pozajmljenih knjiga clana\n");
                    
            opcija = s.next();
            switch (opcija) {
                case "0":
                    return;
                case "1":
                    Clan.azuriranjeClanova();
                    break;
                case "2":
                    Zaposleni.azuriranjeZaposlenih(z);
                    break;
                case "3":
                    Knjiga.azuriranjeKnjiga();
                    break;
                case "4":
                    try {
                        Clan.placanjeClanarine();
                    }
                    catch (ParseException ex) {
                        ex.printStackTrace();
                        continue;
                    }
                case "5":
                    Clan clan = Clan.proveriClana();
                    if (clan != null) {
                        System.out.println(clan.toString());
                        if (Clan.proveraClanarine(clan) && Clan.daliJeClanVratioKnjigu(clan.getId())) {
                            Knjiga.iznajmljivanjeKnjige(clan);
                            break;
                        }
                        else {
                            continue;
                        }
                    }
                    else {
                        continue;
                    }
                case "6":
                    Knjiga.vracanjeKnjige();
                    continue;
                case "7":
                    Knjiga.pregledNevracenihKnjiga();
                    continue;
                case "8":
                    Clan.pregledPozajmljenihKnjigaClana(Clan.proveriClana());
                    continue;
            }
        }
    }
}

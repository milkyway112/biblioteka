/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klase;

import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 *
 * @author Coa
 */
public class PozamljenaKnjiga {
    private int id_clana;
    private String vremePozajmice;
    private int id_knjige;
    private boolean vraceno;
    private String nazivKnjige;

    public PozamljenaKnjiga(int id_clana, String vremePozajmice, int id_knjige, boolean vraceno, String nazivKnjige) {
        this.id_clana = id_clana;
        this.vremePozajmice = vremePozajmice;
        this.id_knjige = id_knjige;
        this.vraceno = vraceno;
        this.nazivKnjige = nazivKnjige;
    }

  public PozamljenaKnjiga() {
        id_clana = -1;
        this.vremePozajmice = "";
        this.id_knjige = -1;
        this.vraceno = false;
        nazivKnjige = "";
    }

    public int getId_knjige() {
        return id_knjige;
    }

    public void setId_knjige(int id_knjige) {
        this.id_knjige = id_knjige;
    }

    public String getNazivKnjige() {
        return nazivKnjige;
    }

    public void setNazivKnjige(String nazivKnjige) {
        this.nazivKnjige = nazivKnjige;
    }

    public String getVremePozajmice() {
        
        return vremePozajmice;
    }

    public void setVremePozajmice(String vremePozajmice) {
        
        this.vremePozajmice = vremePozajmice;
    }

    public int getKnjiga() {
        
        return id_knjige;
    }

    public void setKnjiga(int id_knjige) {
        
        this.id_knjige = id_knjige;
    }

    public boolean isVraceno() {
        
        return vraceno;
    }

    public void setVraceno(boolean vraceno) {
        
        this.vraceno = vraceno;
    }

    public int getId_clana() {
        return id_clana;
    }

    public void setId_clana(int id_clana) {
        this.id_clana = id_clana;
    }

    @Override
    public String toString() {
        return "ID knjige: " + id_knjige + ", Naziv: " + nazivKnjige + ", ID clana: " + id_clana + ", Vreme pozajmice: " + vremePozajmice + ", Vraceno: " + vraceno;
    }
  
    public static void snimi(ArrayList<?> lista) {
        JSONParser.snimi(lista, "pozajmljene_knjige.json");
    }

    public static ArrayList<PozamljenaKnjiga> citaj() {
        return (ArrayList<PozamljenaKnjiga>)JSONParser.citaj("pozajmljene_knjige.json", new TypeToken<ArrayList<PozamljenaKnjiga>>(){});
    }

    public static PozamljenaKnjiga nadjiPozajmljenuKnjiguPoIdjevima(int id_knjige, ArrayList<PozamljenaKnjiga> pozajmljeneKnjigeClana) {
        for (PozamljenaKnjiga pkc : pozajmljeneKnjigeClana) {
            if (pkc.getId_knjige() == id_knjige) {
                return pkc;
            }
        }
        return null;
    }

    public static int nadjiIndexPozajmljeneKnjige(int id_knjige, int id_clana) {
        ArrayList<PozamljenaKnjiga> pozamljenaKnjige = citaj();
        if (pozamljenaKnjige == null) {
            return -1;
        }
        for (int i = 0; i < pozamljenaKnjige.size(); i++) {
            if (pozamljenaKnjige.get(i).getId_knjige() == id_knjige && pozamljenaKnjige.get(i).getId_clana() == id_clana) {
                return i;
            }
        }
        return -1;
    }

    public static boolean ukloniPozajmljeneKnjigePoIdju(int id_knjige) {
        try {
            ArrayList<PozamljenaKnjiga> pozamljenaKnjige = citaj();
            if (pozamljenaKnjige == null) {
                return true;
            }
            for (int i = 0; i < pozamljenaKnjige.size(); i++) {
                if (pozamljenaKnjige.get(i).getId_knjige() == id_knjige) {
                    pozamljenaKnjige.remove(i);
                    i--;
                }
            }
            return true;
        }
        catch (Exception ex) {
            System.out.println("GRESKA");
            return false;
        }
    }

    public static ArrayList<PozamljenaKnjiga> nadjiPozajmljeneKnjigeClana(int id_clana) {
        try {
            ArrayList<PozamljenaKnjiga> pozamljenaKnjige = citaj();
            if (pozamljenaKnjige == null) {
                return null;
            }
            ArrayList<PozamljenaKnjiga> pozamljenaKnjigeClana = new ArrayList<PozamljenaKnjiga>();
            for (int i = 0; i < pozamljenaKnjige.size(); i++) {
                if (pozamljenaKnjige.get(i).getId_clana() == id_clana) {
                    pozamljenaKnjigeClana.add(pozamljenaKnjige.get(i));
                }
            }
            if (pozamljenaKnjigeClana.size() == 0) {
                return null;
            }
            else {
                return pozamljenaKnjigeClana;
            }
        }
        catch (Exception ex) {
            System.out.println("GRESKA");
            return null;
        }
    }

    public static ArrayList<PozamljenaKnjiga> nadjiNevraceneKnjigeClana(Clan clan) {
        ArrayList<PozamljenaKnjiga> pozajmljeneKnjige = PozamljenaKnjiga.citaj();
        if (pozajmljeneKnjige == null) {
            return pozajmljeneKnjige;
        }
        ArrayList<PozamljenaKnjiga> pozamljenaKnjigeClana = new ArrayList<PozamljenaKnjiga>();
        for (PozamljenaKnjiga pk : pozajmljeneKnjige) {
            if (pk.getId_clana() == clan.getId() && !pk.isVraceno()) {
                pozamljenaKnjigeClana.add(pk);
            }
        }
        return pozamljenaKnjigeClana;
    }
}

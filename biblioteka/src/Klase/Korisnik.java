/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klase;

import java.util.ArrayList;

/**
 *
 * @author Coa
 */
public abstract class Korisnik {
    protected String ime;
    protected String prezime;
    protected int godinaRodenja;
    protected String telefom;
    protected String adresa;
    protected String email;

    public Korisnik(String ime, String prezime, int godinaRodenja, String telefom, String adresa, String email) {
        this.ime = ime;
        this.prezime = prezime;
        this.godinaRodenja = godinaRodenja;
        this.telefom = telefom;
        this.adresa = adresa;
        this.email = email;
    }
    
    public Korisnik() {
        this.ime = "";
        this.prezime = "";
        this.godinaRodenja = 0;
        this.telefom = "";
        this.adresa = "";
        this.email = "";
    }
    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public int getGodinaRodenja() {
        return godinaRodenja;
    }

    public void setGodinaRodenja(int godinaRodenja) {
        this.godinaRodenja = godinaRodenja;
    }

    public String getTelefom() {
        return telefom;
    }

    public void setTelefom(String telefom) {
        this.telefom = telefom;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "ime=" + ime + ", prezime=" + prezime + ", godinaRodenja=" + godinaRodenja + ", telefom=" + telefom + ", adresa=" + adresa + ", email=" + email;
    }
    

    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Klase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.ArrayList;

/**
 *
 * @author Coa
 */
public class JSONParser {

    public static void snimi(ArrayList<?> lista, String imeDat) {
        Gson gson = new Gson();
        try {
            FileWriter fw = new FileWriter(imeDat);
            gson.toJson(lista, fw);
            fw.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static ArrayList<?> citaj(String imeDat, TypeToken type) {
        Gson gson = new Gson();
        ArrayList<?> lista = null;
        try {
            FileReader fr = new FileReader(imeDat);
            lista = gson.fromJson(fr, type.getType());
            fr.close();
            if (lista.size() == 0)
            {
                throw new Exception("Nema podataka");
            }
            return lista;
        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();

        }catch (Exception e)
        {
            e.printStackTrace();

        }
        return null;
    }
}

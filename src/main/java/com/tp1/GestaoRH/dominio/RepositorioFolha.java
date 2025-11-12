package com.tp1.GestaoRH.dominio;

import java.io.*;
import java.util.ArrayList;

public class RepositorioFolha {
    private static final String ARQ = "folhas.dat";

    @SuppressWarnings("unchecked")
    public static ArrayList<FolhaPagamento> carregar() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQ))) {
            return (ArrayList<FolhaPagamento>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void salvar(ArrayList<FolhaPagamento> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQ))) {
            oos.writeObject(lista);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void adicionar(FolhaPagamento f) {
        ArrayList<FolhaPagamento> lista = carregar();
        lista.add(f);
        salvar(lista);
    }
}

package com.tp1.GestaoRH.dominio;

import java.io.*;
import java.time.YearMonth;
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

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RepositorioFolha.class.getName());

    public static void salvar(ArrayList<FolhaPagamento> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQ))) {
            oos.writeObject(lista);
        } catch (IOException e) { 
            logger.log(java.util.logging.Level.SEVERE, "Erro ao salvar arquivo de folhas", e);
        }
    }


    public static void adicionar(FolhaPagamento f) {
        ArrayList<FolhaPagamento> lista = carregar();
        lista.add(f);
        salvar(lista);
    }
    public static boolean existeFolhaMes(String funcionario, YearMonth mes) {
        ArrayList<FolhaPagamento> lista = carregar();

        for (FolhaPagamento f : lista) {
            YearMonth folhaMes = YearMonth.from(f.getData());
            if (f.getFuncionario().getNome().equalsIgnoreCase(funcionario) &&
                folhaMes.equals(mes)) {
                return true;
            }
        }
        return false;
    }

}

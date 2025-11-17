package com.tp1.GestaoRH.dominio;

import java.io.*;
import java.util.ArrayList;

public class RepositorioFuncionario {
    private static final String ARQ = "funcionarios.dat";

    @SuppressWarnings("unchecked")
    public static ArrayList<Funcionario> carregar() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQ))) {
            return (ArrayList<Funcionario>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void salvar(ArrayList<Funcionario> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQ))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void adicionar(Funcionario f) {
        ArrayList<Funcionario> lista = carregar();
        lista.add(f);
        salvar(lista);
    }
    public static Funcionario buscarPorNome(String nome) {
        ArrayList<Funcionario> lista = carregar();
        for (Funcionario f : lista) {
            if (f.getNome().equalsIgnoreCase(nome)) {
                return f;
            }
        }
        return null;
    }

}

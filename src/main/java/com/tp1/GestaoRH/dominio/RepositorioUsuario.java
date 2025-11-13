package com.tp1.GestaoRH.dominio;

import java.io.*;
import java.util.ArrayList;

public class RepositorioUsuario {
    public static Usuario usuarioLogado; 
    private static final String ARQ = "usuarios.dat";

    @SuppressWarnings("unchecked")
    public static ArrayList<Usuario> carregar() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQ))) {
            return (ArrayList<Usuario>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void salvar(ArrayList<Usuario> lista) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQ))) {
            oos.writeObject(lista);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void inicializarPadrao() {
        ArrayList<Usuario> lista = carregar();
        if (lista.isEmpty()) {
            lista.add(new Usuario("admin", "123", "Administrador"));
            lista.add(new Usuario("gestor", "123", "GestorRH"));
            lista.add(new Usuario("func", "123", "FuncionarioGeral"));
            lista.add(new Usuario("rec", "123", "Recrutador"));
            salvar(lista);
        }
    }

    public static Usuario autenticar(String login, String senha) {
        ArrayList<Usuario> lista = carregar();
        for (Usuario u : lista) {
            if (u.getLogin().equals(login) && u.getSenha().equals(senha)) return u;
        }
        return null;
    }
    
    public static void setUsuario(Usuario u){
        usuarioLogado = u;
    }
   
}

package com.tp1.GestaoRH.dominio;

import java.io.*;
import java.util.ArrayList;

public class RepositorioFuncionario {

    // Caminho fixo na pasta "data" (igual aos outros módulos do seu grupo)
    private static final String ARQ = "funcionarios.dat";

    @SuppressWarnings("unchecked")
    public static ArrayList<Funcionario> carregar() {
        File file = new File(ARQ);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object obj = ois.readObject();
            if (obj instanceof ArrayList<?>) {
                return (ArrayList<Funcionario>) obj;
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar funcionários: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public static void salvar(ArrayList<Funcionario> lista) {
        // Cria a pasta "data" automaticamente se não existir
        new File(ARQ).getParentFile().mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQ))) {
            oos.writeObject(lista);
            System.out.println("Funcionários salvos em: " + ARQ);
        } catch (IOException e) {
            System.err.println("Erro ao salvar funcionários: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Evita duplicar funcionário com mesmo CPF
    public static void adicionar(Funcionario f) {
        ArrayList<Funcionario> lista = carregar();
        lista.removeIf(func -> func.getCpf().equals(f.getCpf()));
        lista.add(f);
        salvar(lista);
    }

    public static Funcionario buscarPorNome(String nome) {
        ArrayList<Funcionario> lista = carregar();
        for (Funcionario func : lista) {
            if (func.getNome().equalsIgnoreCase(nome)) {
                return func;
            }
        }
        return null;
    }

    public static Funcionario buscarPorCpf(String cpf) {
        ArrayList<Funcionario> lista = carregar();
        for (Funcionario func : lista) {
            if (func.getCpf().equals(cpf)) {
                return func;
            }
        }
        return null;
    }
}
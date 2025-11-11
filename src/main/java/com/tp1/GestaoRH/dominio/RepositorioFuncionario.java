/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tp1.GestaoRH.dominio;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author matheusmerechia
 */
public class RepositorioFuncionario {
    private static final String ARQUIVO = "funcionarios.dat";

    /**
     * Salva a lista de funcionários em um arquivo serializado.
     *
     * @param funcionarios lista de funcionários a ser salva
     */
    public static void salvarFuncionarios(ArrayList<Funcionario> funcionarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
            oos.writeObject(funcionarios);
            System.out.println("Dados dos funcionários salvos com sucesso em " + ARQUIVO);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados dos funcionários: " + e.getMessage());
        }
    }

    /**
     * Carrega a lista de funcionários de um arquivo serializado.
     *
     * @return lista de funcionários carregada, ou uma lista vazia se o arquivo não existir
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Funcionario> carregarFuncionarios() {
        ArrayList<Funcionario> funcionarios = new ArrayList<>();
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            System.out.println("Arquivo de funcionários não encontrado. Criando nova lista...");
            return funcionarios;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            funcionarios = (ArrayList<Funcionario>) ois.readObject();
            System.out.println("Dados dos funcionários carregados com sucesso de " + ARQUIVO);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar dados dos funcionários: " + e.getMessage());
        }

        return funcionarios;
    }

    /**
     * Teste simples de salvamento e carregamento.
     */
    public static void main(String[] args) {
        ArrayList<Funcionario> lista = new ArrayList<>();

        lista.add(new Funcionario("Ana Silva", "ana@email.com", "Rua A", "12345678900", "Analista", 3500.0, "CLT"));
        lista.add(new Funcionario("Pedro Souza", "pedro@email.com", "Rua B", "98765432100", "Estagiário", 1200.0, "Estagio"));

        // Teste de salvamento
        salvarFuncionarios(lista);

        // Teste de carregamento
        ArrayList<Funcionario> carregados = carregarFuncionarios();
        for (Funcionario f : carregados) {
            System.out.println(f);
        }
    }
    
}

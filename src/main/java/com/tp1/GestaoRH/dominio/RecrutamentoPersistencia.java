package com.tp1.GestaoRH.dominio;

import com.tp1.GestaoRH.dominio.Vaga;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecrutamentoPersistencia {

    private static final String ARQUIVO_VAGAS = "vagas.dat";

    // 🔹 Salvar todas as vagas no arquivo
    public static void salvarVagas(List<Vaga> vagas) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQUIVO_VAGAS))) {
            out.writeObject(vagas);
            System.out.println("✅ Vagas salvas com sucesso!");
        } catch (IOException e) {
            System.out.println("❌ Erro ao salvar vagas: " + e.getMessage());
        }
    }

    // 🔹 Carregar todas as vagas do arquivo
    @SuppressWarnings("unchecked")
    public static List<Vaga> carregarVagas() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ARQUIVO_VAGAS))) {
            return (List<Vaga>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("ℹ️ Nenhum arquivo encontrado. Criando lista vazia de vagas.");
            return new ArrayList<>();
        }
    }
}

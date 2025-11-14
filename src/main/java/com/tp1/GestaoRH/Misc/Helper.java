/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tp1.GestaoRH.Misc;
import com.tp1.GestaoRH.Candidatura.Candidatura;
import com.tp1.GestaoRH.Misc.Constantes.STATUS;
import com.tp1.GestaoRH.dominio.Entrevista;
import com.tp1.GestaoRH.dominio.Recrutador;
import com.tp1.GestaoRH.dominio.RecrutamentoPersistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import com.tp1.GestaoRH.dominio.Contratacao;
import com.tp1.GestaoRH.dominio.Vaga;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author spiri
 */
public class Helper {

    private static Helper instance;
    private ArrayList<Candidatura> candidaturas = new ArrayList<Candidatura>();

    public ArrayList<Contratacao> getContratacoes() {
        ArrayList<Contratacao> contratacoes = (ArrayList<Contratacao>) this.loadObject(Constantes.PATH_CONTRATACOES);
        if (contratacoes == null) {
            return new ArrayList<>(); // Retorna lista vazia
        }
        return contratacoes;
    }

    // Novo método para carregar a lista de recrutadores
    public ArrayList<Recrutador> getRecrutadores() {
        // Assumindo que o Admin salva os recrutadores em PATH_RECRUTADORES
        ArrayList<Recrutador> recrutadores = (ArrayList<Recrutador>) this.loadObject(Constantes.PATH_RECRUTADORES);
        if (recrutadores == null) {
            return new ArrayList<>(); // Retorna lista vazia
        }
        return recrutadores;
    }

    // Novo método para carregar a lista de entrevistas (igual ao getCandidatura)
    public ArrayList<Entrevista> getEntrevistas() {
        ArrayList<Entrevista> entrevistas = (ArrayList<Entrevista>) this.loadObject(Constantes.PATH_ENTREVISTAS);
        if (entrevistas == null) {
            return new ArrayList<>(); // Retorna lista vazia
        }
        return entrevistas;
    }

    // Novo método de busca: "Me dê o objeto Recrutador com este nome"
    public Recrutador getRecrutadorPorNome(String nome) {
        ArrayList<Recrutador> recrutadores = getRecrutadores();
        for (Recrutador r : recrutadores) {
            if (r.getNome().equalsIgnoreCase(nome)) {
                return r;
            }
        }
        return null; // Não encontrado
    }

    public ArrayList<Candidatura> getCandidatura() {
        if (new File("candidatos.dat").exists() == true){
           candidaturas = (ArrayList<Candidatura>) loadObject("candidatos.dat");
        }
        return candidaturas;
        
    }

    public void saveObject(Object obj, String path){
        try {
            
            File f = new File(path); // Implementar outra forma de salvar outros arquivos
            FileOutputStream fos = new FileOutputStream(f); 
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);
            oos.close();
            fos.close();

        }
        catch (IOException e){
            System.out.println("Erro: Objeto não serializavel, " + e.getMessage());
        }
    }
    

    public Object loadObject(String filePath){

        try{
        FileInputStream fileInputStream = new FileInputStream(filePath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object obj = objectInputStream.readObject();
        objectInputStream.close(); 
        return obj;
        } catch(Exception e) {
            System.out.println("Erro ao carregar Objeto, " + e.getMessage());
            return null;
        }
    }

        public static Helper getInstance(){
            if (instance == null) {
                instance = new Helper();
            }
            return instance;
        }
        
    public void listarVagas(javax.swing.JComboBox<String>  j){
    
        List<Vaga> v = RecrutamentoPersistencia.carregarVagas();
        for (Vaga va : v){
            j.addItem(va.getCargo());
        }
    }
    
    public Vaga vagaSelecionada(String cargo) throws Exception {
        List<Vaga> v = RecrutamentoPersistencia.carregarVagas();
        for (Vaga va : v){
            if (va.getCargo().equals(cargo)){
                System.out.println(va.getCargo());
            return va;
            }
        }
        throw new Exception("Vaga não existe");
    }
        
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tp1.GestaoRH.Misc;
import com.tp1.GestaoRH.Candidatura.Candidatura;
import com.tp1.GestaoRH.Misc.Constantes.STATUS;
import com.tp1.GestaoRH.dominio.RecrutamentoPersistencia;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;

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

    public ArrayList<Candidatura> getCandidatura() {
        if (new File("candidatos.txt").exists() == true){
           candidaturas = (ArrayList<Candidatura>) loadObject("candidatos.txt");
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
            return va;
            }
        }
        throw new Exception("Vaga não existe");
    }
        
}

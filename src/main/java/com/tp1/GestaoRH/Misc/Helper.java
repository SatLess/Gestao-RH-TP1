/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tp1.GestaoRH.Misc;
import com.tp1.GestaoRH.Candidatura.Candidato;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import com.tp1.GestaoRH.dominio.Vaga;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author spiri
 */
public class Helper {

    private static Helper instance;
    private ArrayList<Candidato> candidatos = new ArrayList<Candidato>();

    public ArrayList<Candidato> getCandidatos() {
        if (new File("candidatos.txt").exists() == true){
           candidatos = (ArrayList<Candidato>) loadObject("candidatos.txt");
        }
        return candidatos;
        
    }

    public static Vaga acharVagaSelecionada(int index){
        return null;
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
    

    Object loadObject(String filePath){

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

}

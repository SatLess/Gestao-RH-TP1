/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tp1.GestaoRH.Candidatura;

import java.util.ArrayList;
import javax.swing.RowFilter;

/**
 *
 * @author spiri
 */
public class Sorter extends RowFilter {
    
    private ArrayList<String> filtros = new ArrayList<String>();
    
    @Override
    public boolean include(Entry e){
        for (int i = 0; i < e.getValueCount(); i++){
        
            for (int j = 0; j < filtros.size(); j++){
            
                if (filtros.get(j).isBlank()) continue;
                
                if (e.getStringValue(i).contains(filtros.get(j))){
                    System.out.println(e.getStringValue(i));
                return true;}
            }
        
        }
        return false;
    }

    public Sorter(ArrayList<String> a) {
        filtros = a;
    }
    
}

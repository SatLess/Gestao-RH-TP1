// dominio/Recrutador.java
package com.tp1.GestaoRH.dominio;

public class Recrutador extends Pessoa {
    private String departamento;

    public Recrutador(String nome, String email, String departamento, String endereço, String cpf) {
        super(nome,email,endereço,cpf);
        this.departamento = departamento;
    }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
}

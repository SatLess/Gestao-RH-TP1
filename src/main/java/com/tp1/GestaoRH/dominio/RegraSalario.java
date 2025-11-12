package com.tp1.GestaoRH.dominio;

import java.io.*;

public class RegraSalario implements Serializable {
    private static final long serialVersionUID = 1L;
    private double valeTransporte;
    private double valeAlimentacao;
    private double imposto; // fraction e.g. 0.12

    private static final String ARQ = "regras.dat";

    public RegraSalario(double vt, double va, double imposto) {
        this.valeTransporte = vt; this.valeAlimentacao = va; this.imposto = imposto;
    }

    public double getValeTransporte() { return valeTransporte; }
    public double getValeAlimentacao() { return valeAlimentacao; }
    public double getImposto() { return imposto; }

    public void setValeTransporte(double vt) { this.valeTransporte = vt; }
    public void setValeAlimentacao(double va) { this.valeAlimentacao = va; }
    public void setImposto(double imposto) { this.imposto = imposto; }

    public static RegraSalario carregar() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQ))) {
            return (RegraSalario) ois.readObject();
        } catch (Exception e) {
            // default
            return new RegraSalario(150.0, 400.0, 0.12);
        }
    }

    public static void salvar(RegraSalario r) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQ))) {
            oos.writeObject(r);
        }
    }
}

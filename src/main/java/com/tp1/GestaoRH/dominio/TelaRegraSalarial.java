/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.tp1.GestaoRH.dominio;
import com.tp1.GestaoRH.dominio.RegraSalario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.tp1.GestaoRH.Candidatura.*;
/**
 *
 * @author matheusmerechia
 */
public class TelaRegraSalarial extends JFrame{
    
private JTextField campoValeTransporte;
    private JTextField campoValeAlimentacao;
    private JTextField campoImposto;
    private JButton botaoSalvar;
    private JButton botaoVoltar;

    public TelaRegraSalarial() {
        super("Configurar Regras Salariais");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

        // Campos de entrada
        add(new JLabel("Vale Transporte (R$):"));
        campoValeTransporte = new JTextField();
        add(campoValeTransporte);

        add(new JLabel("Vale Alimentação (R$):"));
        campoValeAlimentacao = new JTextField();
        add(campoValeAlimentacao);

        add(new JLabel("Imposto (%):"));
        campoImposto = new JTextField();
        add(campoImposto);

        // Botões
        botaoSalvar = new JButton("Salvar");
        botaoVoltar = new JButton("Voltar");
        add(botaoSalvar);
        add(botaoVoltar);

        // Ação do botão Salvar
        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double vt = Double.parseDouble(campoValeTransporte.getText());
                    double va = Double.parseDouble(campoValeAlimentacao.getText());
                    double imposto = Double.parseDouble(campoImposto.getText()) / 100.0;

                    // Cria objeto de regra salarial
                    RegraSalario regra = new RegraSalario(vt, va, imposto);

                    JOptionPane.showMessageDialog(null,
                        String.format(
                            "Regras salariais salvas com sucesso!\n" +
                            "Vale Transporte: R$%.2f\nVale Alimentação: R$%.2f\nImposto: %.1f%%",
                            vt, va, imposto * 100
                        )
                    );

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, insira apenas números válidos.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao salvar regras: " + ex.getMessage());
                }
            }
        });

        // Ação do botão Voltar
        botaoVoltar.addActionListener(e -> dispose());

        setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaRegraSalarial());
    
        // TODO code application logic here
    }
    
}

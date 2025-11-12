package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.dominio.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaContracheques extends JFrame {
    private String tipoUsuario;
    private JComboBox<String> funcCombo;
    private JTextArea area;
    private JButton gerarBtn, voltarBtn;

    public TelaContracheques(String tipoUsuario) { super("Contracheques"); this.tipoUsuario = tipoUsuario; init(); }

    private void init() {
        setSize(600,420); setLocationRelativeTo(null); setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        funcCombo = new JComboBox<>(); area = new JTextArea(); area.setEditable(false); gerarBtn = new JButton("Gerar"); voltarBtn = new JButton("Voltar");
        JPanel top = new JPanel(); top.add(new JLabel("Funcionário:")); top.add(funcCombo);
        add(new JLabel("Contracheques", SwingConstants.CENTER), BorderLayout.NORTH); add(top, BorderLayout.PAGE_START); add(new JScrollPane(area), BorderLayout.CENTER);
        JPanel botoes = new JPanel(); botoes.add(gerarBtn); botoes.add(voltarBtn); add(botoes, BorderLayout.SOUTH);
        carregar(); gerarBtn.addActionListener(e -> gerar()); voltarBtn.addActionListener(e -> { new MenuFinanceiro(tipoUsuario).setVisible(true); dispose(); });
    }

    private void carregar() { ArrayList<Funcionario> lista = RepositorioFuncionario.carregar(); funcCombo.removeAllItems(); for (Funcionario f : lista) if (f.isAtivo()) funcCombo.addItem(f.getNome()); }

    private void gerar() {
        String nome = (String) funcCombo.getSelectedItem(); if (nome == null) { JOptionPane.showMessageDialog(this, "Selecione um funcionário."); return; }
        ArrayList<Funcionario> lista = RepositorioFuncionario.carregar(); RegraSalario regra = RegraSalario.carregar();
        for (Funcionario f : lista) {
            if (f.getNome().equals(nome)) {
                FolhaPagamento fol = new FolhaPagamento(f, regra);
                area.setText(fol.toString());
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Funcionário não encontrado."); 
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new TelaContracheques("FuncionarioGeral").setVisible(true)); }
}

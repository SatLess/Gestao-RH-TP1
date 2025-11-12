package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.dominio.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaBeneficios extends JFrame {
    private String tipoUsuario;
    private JComboBox<String> funcCombo;
    private JTextArea area;
    private JButton voltarBtn;

    public TelaBeneficios(String tipoUsuario) { super("Benefícios"); this.tipoUsuario = tipoUsuario; init(); }

    private void init() {
        setSize(560,420); setLocationRelativeTo(null); setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        funcCombo = new JComboBox<>(); area = new JTextArea(); area.setEditable(false); voltarBtn = new JButton("Voltar");
        voltarBtn.addActionListener(e -> { new MenuFinanceiro(tipoUsuario).setVisible(true); dispose(); });
        JPanel top = new JPanel(); top.add(new JLabel("Funcionário:")); top.add(funcCombo);
        add(new JLabel("Benefícios", SwingConstants.CENTER), BorderLayout.NORTH);
        add(top, BorderLayout.PAGE_START); add(new JScrollPane(area), BorderLayout.CENTER); add(voltarBtn, BorderLayout.SOUTH);
        carregar();
        funcCombo.addActionListener(e -> mostrar());
    }

    private void carregar() {
        ArrayList<Funcionario> lista = RepositorioFuncionario.carregar();
        funcCombo.removeAllItems();
        for (Funcionario f : lista) if (f.isAtivo()) funcCombo.addItem(f.getNome());
    }

    private void mostrar() {
        String nome = (String) funcCombo.getSelectedItem();
        if (nome == null) return;
        RegraSalario r = RegraSalario.carregar();
        StringBuilder sb = new StringBuilder();
        sb.append("Benefícios:\n\n"); sb.append(String.format("- Vale Alimentação: R$ %.2f\n", r.getValeAlimentacao()));
        sb.append(String.format("- Vale Transporte: R$ %.2f\n", r.getValeTransporte())); sb.append("- Plano de Saúde: (dependente do contrato)\n");
        area.setText(sb.toString());
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new TelaBeneficios("FuncionarioGeral").setVisible(true)); }
}

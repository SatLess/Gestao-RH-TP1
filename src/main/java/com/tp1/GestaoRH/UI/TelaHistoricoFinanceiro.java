package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.dominio.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaHistoricoFinanceiro extends JFrame {
    private String tipoUsuario;
    private JComboBox<String> funcCombo;
    private JTextArea area;
    private JButton voltarBtn;

    public TelaHistoricoFinanceiro(String tipoUsuario) { super("Histórico Financeiro"); this.tipoUsuario = tipoUsuario; init(); }

    private void init() {
        setSize(600,420); setLocationRelativeTo(null); setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        funcCombo = new JComboBox<>(); area = new JTextArea(); area.setEditable(false); voltarBtn = new JButton("Voltar");
        voltarBtn.addActionListener(e -> { new MenuFinanceiro(tipoUsuario).setVisible(true); dispose(); });
        JPanel top = new JPanel(); top.add(new JLabel("Funcionário:")); top.add(funcCombo);
        add(new JLabel("Histórico Financeiro", SwingConstants.CENTER), BorderLayout.NORTH);
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
        ArrayList<FolhaPagamento> folhas = RepositorioFolha.carregar();
        StringBuilder sb = new StringBuilder();
        for (FolhaPagamento fol : folhas) {
            if (fol.getFuncionario().getNome().equals(nome)) {
                sb.append(String.format("Data: %s - Bruto: R$ %.2f - Deduções: R$ %.2f - Líquido: R$ %.2f\n", fol.getData().toString(), fol.getSalarioBruto(), fol.getDeducoes(), fol.getSalarioLiquido()));
            }
        }
        if (sb.length() == 0) sb.append("Nenhum histórico encontrado.");
        area.setText(sb.toString());
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new TelaHistoricoFinanceiro("FuncionarioGeral").setVisible(true)); }
}

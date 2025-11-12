package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.dominio.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TelaRelatorioFinanceiro extends JFrame {
    private String tipoUsuario;
    private JTable tabela;
    private JButton voltarBtn;

    public TelaRelatorioFinanceiro(String tipoUsuario) { super("Relatório Financeiro"); this.tipoUsuario = tipoUsuario; init(); }

    private void init() {
        setSize(900,520); setLocationRelativeTo(null); setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        tabela = new JTable(new DefaultTableModel(new Object[]{"Nome","Cargo","Regime","Bruto","Deduções","Líquido","Data"},0));
        voltarBtn = new JButton("Voltar"); voltarBtn.addActionListener(e -> { new MenuFinanceiro(tipoUsuario).setVisible(true); dispose(); });
        add(new JLabel("Relatório Financeiro - Funcionários Ativos", SwingConstants.CENTER), BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER); add(voltarBtn, BorderLayout.SOUTH);
        carregar();
    }

    private void carregar() {
        try {
            ArrayList<Funcionario> lista = RepositorioFuncionario.carregar();
            RegraSalario regra = RegraSalario.carregar();
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();
            model.setRowCount(0);
            for (Funcionario f : lista) {
                if (f.isAtivo()) {
                    FolhaPagamento fol = new FolhaPagamento(f, regra);
                    model.addRow(new Object[]{ f.getNome(), f.getCargo(), f.getTipoContratacao(), String.format("R$ %.2f", fol.getSalarioBruto()), String.format("R$ %.2f", fol.getDeducoes()), String.format("R$ %.2f", fol.getSalarioLiquido()), fol.getData().toString() });
                }
            }
        } catch(Exception ex) { JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage()); }
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new TelaRelatorioFinanceiro("GestorRH").setVisible(true)); }
}

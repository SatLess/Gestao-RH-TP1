package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.dominio.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class TelaGerarFolhaPagamento extends JFrame {
    private String tipoUsuario;
    private JTable tabela;
    private JButton gerarBtn, salvarBtn, voltarBtn;

    public TelaGerarFolhaPagamento(String tipoUsuario) { super("Gerar Folha"); this.tipoUsuario = tipoUsuario; init(); }

    private void init() {
        setSize(900,520); setLocationRelativeTo(null); setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        tabela = new JTable(new DefaultTableModel(new Object[]{"Nome","Regime","Salário Bruto","Deduções","Salário Líquido","Data"},0));
        gerarBtn = new JButton("Gerar"); salvarBtn = new JButton("Salvar Folhas"); voltarBtn = new JButton("Voltar");
        gerarBtn.addActionListener(e -> gerar()); salvarBtn.addActionListener(e -> salvar()); voltarBtn.addActionListener(e -> { new MenuFinanceiro(tipoUsuario).setVisible(true); dispose(); });
        JPanel botoes = new JPanel(); botoes.add(gerarBtn); botoes.add(salvarBtn); botoes.add(voltarBtn);
        add(new JLabel("Folha de Pagamento - Funcionários Ativos", SwingConstants.CENTER), BorderLayout.NORTH);
        add(new JScrollPane(tabela), BorderLayout.CENTER); add(botoes, BorderLayout.SOUTH);
    }

    private void gerar() {
        try {
            ArrayList<Funcionario> lista = RepositorioFuncionario.carregar();
            RegraSalario regra = RegraSalario.carregar();
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();
            model.setRowCount(0);
            for (Funcionario f : lista) {
                if (f.isAtivo()) {
                    FolhaPagamento fol = new FolhaPagamento(f, regra);
                    model.addRow(new Object[]{ f.getNome(), f.getTipoContratacao(), String.format("R$ %.2f", fol.getSalarioBruto()), String.format("R$ %.2f", fol.getDeducoes()), String.format("R$ %.2f", fol.getSalarioLiquido()), fol.getData().toString() });
                }
            }
            JOptionPane.showMessageDialog(this, "Folhas geradas (não salvas). Use 'Salvar Folhas' para persistir.");
        } catch(Exception ex) { JOptionPane.showMessageDialog(this, "Erro ao gerar: " + ex.getMessage()); }
    }

    private void salvar() {
        try {
            ArrayList<Funcionario> lista = RepositorioFuncionario.carregar();
            RegraSalario regra = RegraSalario.carregar();
            ArrayList<FolhaPagamento> salvar = new ArrayList<>();
            for (Funcionario f : lista) if (f.isAtivo()) salvar.add(new FolhaPagamento(f, regra));
            if (salvar.isEmpty()) { JOptionPane.showMessageDialog(this, "Nenhuma folha para salvar."); return; }
            ArrayList<FolhaPagamento> existentes = RepositorioFolha.carregar(); existentes.addAll(salvar); RepositorioFolha.salvar(existentes);
            JOptionPane.showMessageDialog(this, "Folhas salvas em folhas.dat"); 
        } catch(Exception ex) { JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage()); }
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new TelaGerarFolhaPagamento("GestorRH").setVisible(true)); }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.dominio.Funcionario;
import com.tp1.GestaoRH.dominio.RepositorioFuncionario;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author matheusmerechia
 */

public class TelaConsultaFuncionarios extends JFrame {

    private JComboBox<String> cargoBox, tipoBox, statusBox, departamentoBox;
    private JButton aplicarBtn, limparBtn, voltarBtn;
    private JTable tabela;
    private DefaultTableModel modelo;
    private String tipoUsuario;

    public TelaConsultaFuncionarios(String tipoUsuario) {
        super("Consulta de Funcionários - " + tipoUsuario);
        this.tipoUsuario = tipoUsuario;
        init();
    }

    private void init() {
        setSize(850, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // Painel de filtros
        JPanel filtros = new JPanel(new GridLayout(2, 4, 10, 10));
        filtros.setBorder(BorderFactory.createTitledBorder("Filtros"));

        cargoBox = new JComboBox<>(new String[]{"Todos", "Administrador", "GestorRH", "Recrutador", "FuncionarioGeral"});
        tipoBox = new JComboBox<>(new String[]{"Todos", "CLT", "Estágio", "PJ"});
        statusBox = new JComboBox<>(new String[]{"Todos", "Ativo", "Inativo"});
        departamentoBox = new JComboBox<>(new String[]{"Todos", "RH", "Financeiro", "TI", "Administração"});

        filtros.add(new JLabel("Cargo:"));
        filtros.add(cargoBox);
        filtros.add(new JLabel("Tipo de Contratação:"));
        filtros.add(tipoBox);
        filtros.add(new JLabel("Status:"));
        filtros.add(statusBox);
        filtros.add(new JLabel("Departamento:"));
        filtros.add(departamentoBox);

        // Painel de botões
        JPanel botoes = new JPanel(new FlowLayout());
        aplicarBtn = new JButton("Aplicar Filtros");
        limparBtn = new JButton("Limpar Filtros");
        voltarBtn = new JButton("Voltar");
        botoes.add(aplicarBtn);
        botoes.add(limparBtn);
        botoes.add(voltarBtn);

        // Tabela
        modelo = new DefaultTableModel(new Object[]{"Nome", "Cargo", "Tipo", "Status", "Departamento", "Salário Base"}, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela);

        add(filtros, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);

        // Ações dos botões
        aplicarBtn.addActionListener(e -> aplicarFiltros());
        limparBtn.addActionListener(e -> carregarTodosFuncionarios());
        voltarBtn.addActionListener(e -> {
            new MenuFinanceiro(tipoUsuario).setVisible(true);
            dispose();
        });

        // Carregar dados iniciais
        carregarTodosFuncionarios();
    }

    private void carregarTodosFuncionarios() {
        modelo.setRowCount(0);
        List<Funcionario> funcionarios = RepositorioFuncionario.carregar();
        for (Funcionario f : funcionarios) {
            modelo.addRow(new Object[]{
                    f.getNome(),
                    f.getCargo(),
                    f.getTipoContratacao(),
                    f.isAtivo() ? "Ativo" : "Inativo",
                    f.getDepartamento(),
                    String.format("R$ %.2f", f.getSalarioBase())
            });
        }
    }

    private void aplicarFiltros() {
        String cargoSel = (String) cargoBox.getSelectedItem();
        String tipoSel = (String) tipoBox.getSelectedItem();
        String statusSel = (String) statusBox.getSelectedItem();
        String depSel = (String) departamentoBox.getSelectedItem();

        List<Funcionario> filtrados = RepositorioFuncionario.carregar().stream()
                .filter(f -> cargoSel.equals("Todos") || f.getCargo().equalsIgnoreCase(cargoSel))
                .filter(f -> tipoSel.equals("Todos") || f.getTipoContratacao().equalsIgnoreCase(tipoSel))
                .filter(f -> statusSel.equals("Todos") ||
                        (statusSel.equals("Ativo") && f.isAtivo()) ||
                        (statusSel.equals("Inativo") && !f.isAtivo()))
                .filter(f -> depSel.equals("Todos") || f.getDepartamento().equalsIgnoreCase(depSel))
                .collect(Collectors.toList());

        modelo.setRowCount(0);
        for (Funcionario f : filtrados) {
            modelo.addRow(new Object[]{
                    f.getNome(),
                    f.getCargo(),
                    f.getTipoContratacao(),
                    f.isAtivo() ? "Ativo" : "Inativo",
                    f.getDepartamento(),
                    String.format("R$ %.2f", f.getSalarioBase())
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaConsultaFuncionarios("Administrador").setVisible(true));
    }
}


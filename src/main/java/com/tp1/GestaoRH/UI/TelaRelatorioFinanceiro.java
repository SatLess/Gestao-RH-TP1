package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.dominio.*;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class TelaRelatorioFinanceiro extends JFrame {

    private JComboBox<String> depBox, regimeBox, mesBox, anoBox;
    private JTable tabela;
    private JLabel totalLabel;
    private String tipoUsuario;

    public TelaRelatorioFinanceiro(String tipoUsuario) {
        super("Relatório Financeiro - " + tipoUsuario);
        this.tipoUsuario = tipoUsuario;
        init();
    }

    private void init() {
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10,10));

        JPanel filtros = new JPanel(new GridLayout(2,4,10,10));
        filtros.setBorder(BorderFactory.createTitledBorder("Filtros"));

        depBox = new JComboBox<>(new String[]{"Todos", "RH", "Financeiro", "TI", "Administração"});
        regimeBox = new JComboBox<>(new String[]{"Todos", "CLT", "Estágio", "PJ"});
        mesBox = new JComboBox<>(new String[]{"Todos","01","02","03","04","05","06","07","08","09","10","11","12"});
        anoBox = new JComboBox<>(new String[]{"Todos", "2023","2024","2025","2026","2027"});

        filtros.add(new JLabel("Departamento"));
        filtros.add(depBox);
        filtros.add(new JLabel("Regime"));
        filtros.add(regimeBox);
        filtros.add(new JLabel("Mês"));
        filtros.add(mesBox);
        filtros.add(new JLabel("Ano"));
        filtros.add(anoBox);

        add(filtros, BorderLayout.NORTH);

        tabela = new JTable(new DefaultTableModel(
                new Object[]{"Nome","Regime","Bruto","Deduções","Líquido","Departamento","Data"}, 0));
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel rodape = new JPanel(new FlowLayout());
        JButton filtrarBtn = new JButton("Aplicar Filtros");
        JButton pdfBtn = new JButton("Exportar PDF");
        JButton voltarBtn = new JButton("Voltar");

        totalLabel = new JLabel("Total: R$ 0.00");

        rodape.add(filtrarBtn);
        rodape.add(pdfBtn);
        rodape.add(totalLabel);
        rodape.add(voltarBtn);
        add(rodape, BorderLayout.SOUTH);

        filtrarBtn.addActionListener(e -> aplicarFiltros());
        pdfBtn.addActionListener(e -> gerarPDF());
        voltarBtn.addActionListener(e -> {
            new MenuFinanceiro(tipoUsuario).setVisible(true);
            dispose();
        });

        aplicarFiltros();
    }

    private void aplicarFiltros() {
        String depFiltro = (String) depBox.getSelectedItem();
        String regimeFiltro = (String) regimeBox.getSelectedItem();
        String mesFiltro = (String) mesBox.getSelectedItem();
        String anoFiltro = (String) anoBox.getSelectedItem();

        ArrayList<FolhaPagamento> folhas = RepositorioFolha.carregar();
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        model.setRowCount(0);

        double total = 0;

        for (FolhaPagamento f : folhas) {

            String dep = f.getFuncionario().getDepartamento();
            String regime = f.getFuncionario().getTipoContratacao();
            LocalDate data = f.getData();

            boolean okDep = depFiltro.equals("Todos") || depFiltro.equalsIgnoreCase(dep);
            boolean okRegime = regimeFiltro.equals("Todos") || regimeFiltro.equalsIgnoreCase(regime);
            boolean okMes = mesFiltro.equals("Todos") || Integer.parseInt(mesFiltro) == data.getMonthValue();
            boolean okAno = anoFiltro.equals("Todos") || Integer.parseInt(anoFiltro) == data.getYear();

            if (okDep && okRegime && okMes && okAno) {
                model.addRow(new Object[]{
                        f.getFuncionario().getNome(),
                        regime,
                        String.format("R$ %.2f", f.getSalarioBruto()),
                        String.format("R$ %.2f", f.getDeducoes()),
                        String.format("R$ %.2f", f.getSalarioLiquido()),
                        dep,
                        data.toString()
                });
                total += f.getSalarioLiquido();
            }
        }

        totalLabel.setText(String.format("Total: R$ %.2f", total));
    }

    private void gerarPDF() {
        try {
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("relatorio_financeiro.pdf"));

            doc.open();
            doc.add(new Paragraph("RELATÓRIO FINANCEIRO\n\n"));

            for (int i = 0; i < model.getRowCount(); i++) {
                doc.add(new Paragraph(
                        "Nome: " + model.getValueAt(i, 0) + "\n" +
                        "Regime: " + model.getValueAt(i, 1) + "\n" +
                        "Bruto: " + model.getValueAt(i, 2) + "\n" +
                        "Deduções: " + model.getValueAt(i, 3) + "\n" +
                        "Líquido: " + model.getValueAt(i, 4) + "\n" +
                        "Departamento: " + model.getValueAt(i, 5) + "\n" +
                        "Data: " + model.getValueAt(i, 6) + "\n\n"
                ));
            }

            doc.add(new Paragraph("\n" + totalLabel.getText()));
            doc.close();

            JOptionPane.showMessageDialog(this, "PDF gerado: relatorio_financeiro.pdf");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar PDF: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaRelatorioFinanceiro("Administrador").setVisible(true));
    }
}

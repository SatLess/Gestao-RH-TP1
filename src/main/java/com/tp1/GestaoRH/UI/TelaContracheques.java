package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.dominio.*;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class TelaContracheques extends JFrame {

    private String tipoUsuario;
    private JComboBox<String> funcionarioBox;
    private JTable tabela;
    private JButton pdfBtn, voltarBtn;

    public TelaContracheques(String tipoUsuario) {
        super("Contracheques - " + tipoUsuario);
        this.tipoUsuario = tipoUsuario;
        init();
    }

    private void init() {
        setSize(850, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel topo = new JPanel(new GridLayout(1, 2, 10, 10));
        topo.setBorder(BorderFactory.createTitledBorder("Seleção"));

        topo.add(new JLabel("Funcionário:"));

        funcionarioBox = new JComboBox<>();
        topo.add(funcionarioBox);

        add(topo, BorderLayout.NORTH);

        tabela = new JTable(new DefaultTableModel(
                new Object[]{"Data", "Bruto", "Deduções", "Líquido"}, 0));
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel rodape = new JPanel(new FlowLayout());
        pdfBtn = new JButton("Exportar Contracheque PDF");
        voltarBtn = new JButton("Voltar");

        rodape.add(pdfBtn);
        rodape.add(voltarBtn);
        add(rodape, BorderLayout.SOUTH);

        voltarBtn.addActionListener(e -> {
            new MenuFinanceiro(tipoUsuario).setVisible(true);
            dispose();
        });

        pdfBtn.addActionListener(e -> gerarPDF());

        carregarFuncionarios();
    }

    /**
     * Carrega lista de funcionários conforme o tipo de usuário
     */
    private void carregarFuncionarios() {
        ArrayList<Funcionario> lista = RepositorioFuncionario.carregar();

        funcionarioBox.removeAllItems();

        if (tipoUsuario.equalsIgnoreCase("FuncionarioGeral")) {

            // funcionário geral vê somente ele mesmo
            for (Funcionario f : lista) {
                if (f.getCargo().equalsIgnoreCase("FuncionarioGeral")) {
                    funcionarioBox.addItem(f.getNome());
                }
            }

        } else {

            // Gestor e Admin podem ver todos
            for (Funcionario f : lista) {
                funcionarioBox.addItem(f.getNome());
            }
        }

        funcionarioBox.addActionListener(e -> carregarFolhas());
        carregarFolhas();
    }

    /**
     * Carrega folhas do funcionário selecionado
     */
    private void carregarFolhas() {
        String nome = (String) funcionarioBox.getSelectedItem();
        if (nome == null) return;

        ArrayList<FolhaPagamento> folhas = RepositorioFolha.carregar();
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        model.setRowCount(0);

        for (FolhaPagamento f : folhas) {

            if (f.getFuncionario().getNome().equalsIgnoreCase(nome)) {
                model.addRow(new Object[]{
                        f.getData().toString(),
                        String.format("R$ %.2f", f.getSalarioBruto()),
                        String.format("R$ %.2f", f.getDeducoes()),
                        String.format("R$ %.2f", f.getSalarioLiquido())
                });
            }
        }
    }

    /**
     * Exportar PDF do contracheque selecionado
     */
    private void gerarPDF() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um contracheque na tabela.");
            return;
        }

        String funcionario = (String) funcionarioBox.getSelectedItem();
        ArrayList<FolhaPagamento> folhas = RepositorioFolha.carregar();

        FolhaPagamento folha = null;

        // localizar folha correspondente
        for (FolhaPagamento f : folhas) {
            if (f.getFuncionario().getNome().equalsIgnoreCase(funcionario)) {
                if (f.getData().toString().equals(tabela.getValueAt(linha, 0))) {
                    folha = f;
                    break;
                }
            }
        }

        if (folha == null) {
            JOptionPane.showMessageDialog(this, "Folha não encontrada no repositório!");
            return;
        }

        try {
            String arquivo = "contracheque_" + funcionario + ".pdf";

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream(arquivo));

            doc.open();
            doc.add(new Paragraph("CONTRACHEQUE\n\n"));
            doc.add(new Paragraph("Funcionário: " + funcionario));
            doc.add(new Paragraph("Cargo: " + folha.getFuncionario().getCargo()));
            doc.add(new Paragraph("Departamento: " + folha.getFuncionario().getDepartamento()));
            doc.add(new Paragraph("Regime: " + folha.getFuncionario().getTipoContratacao()));
            doc.add(new Paragraph("----------------------------------------"));
            doc.add(new Paragraph("Salário Bruto: R$ " + folha.getSalarioBruto()));
            doc.add(new Paragraph("Deduções: R$ " + folha.getDeducoes()));
            doc.add(new Paragraph("Salário Líquido: R$ " + folha.getSalarioLiquido()));
            doc.add(new Paragraph("Data: " + folha.getData()));
            doc.add(new Paragraph("----------------------------------------"));

            doc.close();

            JOptionPane.showMessageDialog(this, "PDF gerado: " + arquivo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar PDF: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaContracheques("Administrador").setVisible(true));
    }
}

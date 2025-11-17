package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.dominio.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.YearMonth;
import java.util.ArrayList;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class TelaGerarFolhaPagamento extends JFrame {
    private String tipoUsuario;
    private JTable tabela;
    private JButton gerarBtn, salvarBtn, voltarBtn, gerarUmBtn;

    public TelaGerarFolhaPagamento(String tipoUsuario) { super("Gerar Folha"); this.tipoUsuario = tipoUsuario; init(); }

    private void init() {
        setSize(900,520); setLocationRelativeTo(null); setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        tabela = new JTable(new DefaultTableModel(new Object[]{"Nome","Regime","Salário Bruto","Deduções","Salário Líquido","Data"},0));
        gerarBtn = new JButton("Gerar"); salvarBtn = new JButton("Salvar Folhas"); voltarBtn = new JButton("Voltar"); gerarUmBtn = new JButton("Gerar Selecionado");
        JButton pdfMesBtn = new JButton("Exportar PDF (Mês Atual)");
        JButton pdfTotalBtn = new JButton("Exportar PDF Completo");
        pdfMesBtn.addActionListener(e -> pdfMensal());
        pdfTotalBtn.addActionListener(e -> pdfCompleto());
        gerarBtn.addActionListener(e -> gerar()); salvarBtn.addActionListener(e -> salvar());  gerarUmBtn.addActionListener(e -> gerarApenasUm()); voltarBtn.addActionListener(e -> { new MenuFinanceiro(tipoUsuario).setVisible(true); dispose(); });
        JPanel botoes = new JPanel(); botoes.add(gerarBtn); botoes.add(salvarBtn); botoes.add(gerarUmBtn); botoes.add(pdfMesBtn); botoes.add(pdfTotalBtn); botoes.add(voltarBtn);
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
    
    private void gerarApenasUm() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um funcionário na tabela.");
            return;
        }

        String nome = (String) tabela.getValueAt(linha, 0);
        Funcionario f = RepositorioFuncionario.buscarPorNome(nome);

        if (f == null) {
            JOptionPane.showMessageDialog(this, "Funcionário não encontrado.");
            return;
        }

        RegraSalario regra = RegraSalario.carregar();
        FolhaPagamento fol = new FolhaPagamento(f, regra);

        JOptionPane.showMessageDialog(this,
            "Salário Líquido de " + nome + " = R$ " + fol.getSalarioLiquido());
    }

    private void salvar() {
        try {
            ArrayList<Funcionario> lista = RepositorioFuncionario.carregar();
            RegraSalario regra = RegraSalario.carregar();
            ArrayList<FolhaPagamento> salvar = new ArrayList<>();
            YearMonth mesAtual = YearMonth.now();
            for (Funcionario f : lista){
                if (f.isAtivo()){
                    if (!RepositorioFolha.existeFolhaMes(f.getNome(), mesAtual)) { 
                        salvar.add(new FolhaPagamento(f, regra));
                    }
                }    
            }    
            if (salvar.isEmpty()) { JOptionPane.showMessageDialog(this, "Nenhuma folha para salvar."); return; }
            ArrayList<FolhaPagamento> existentes = RepositorioFolha.carregar(); existentes.addAll(salvar); RepositorioFolha.salvar(existentes);
            JOptionPane.showMessageDialog(this, "Folhas salvas em folhas.dat"); 
        } catch(Exception ex) { JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage()); }
    }
    
    private void pdfMensal() {
        try {
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("folha_mensal.pdf"));
            doc.open();

            doc.add(new Paragraph("Folha Mensal\n\n"));

            for (int i = 0; i < model.getRowCount(); i++) {
                doc.add(new Paragraph(
                        "Nome: " + model.getValueAt(i, 0) + "\n" +
                        "Regime: " + model.getValueAt(i, 1) + "\n" +
                        "Salário Líquido: " + model.getValueAt(i, 4) + "\n" +
                        "Data: " + model.getValueAt(i, 5) + "\n\n"
                ));
            }

            doc.close();
            JOptionPane.showMessageDialog(this, "PDF gerado: folha_mensal.pdf");
        } catch (DocumentException | HeadlessException | FileNotFoundException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar PDF");
        }
    }
    
    private void pdfCompleto() {
        try {
            ArrayList<FolhaPagamento> lista = RepositorioFolha.carregar();
            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("folha_historico.pdf"));
            doc.open();

            doc.add(new Paragraph("Histórico Completo de Folhas\n\n"));

            for (FolhaPagamento f : lista) {
                doc.add(new Paragraph(
                        "Nome: " + f.getFuncionario().getNome() + "\n" +
                        "Salário Líquido: " + f.getSalarioLiquido() + "\n" +
                        "Data: " + f.getData() + "\n\n"
                ));
            }

            doc.close();
            JOptionPane.showMessageDialog(this, "PDF gerado: folha_historico.pdf");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao gerar PDF");
        }
    }


    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new TelaGerarFolhaPagamento("GestorRH").setVisible(true)); }
}

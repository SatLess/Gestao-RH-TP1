package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.dominio.Contratacao;
import com.tp1.GestaoRH.Misc.Helper;
import com.tp1.GestaoRH.Misc.Constantes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RecrutamentoConsultarContratacoes extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAtualizar, btnVoltar;
    
    // ===================================================================
    // NOSSO BOTÃO DE CONTINGÊNCIA
    // ===================================================================
    private JButton btnAutorizarSimulado;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RecrutamentoConsultarContratacoes() {
        setTitle("Consultar Contratações (Recrutador/Gestor)"); // Título atualizado
        setSize(800, 400); // Aumentei o tamanho para o novo botão
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        carregarContratacoes(); 
    }

    private void initComponents() {
        String[] colunas = {"CPF Candidato", "Nome", "Vaga", "Regime", "Data Contratação", "Status (Gestor)"};
        model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        btnAtualizar = new JButton("Atualizar");
        btnVoltar = new JButton("Voltar");
        
        // ===================================================================
        // Instancia o novo botão
        // ===================================================================
        btnAutorizarSimulado = new JButton("Autorizar (Simulação Gestor)");

        btnAtualizar.addActionListener(e -> carregarContratacoes());
        btnVoltar.addActionListener(e -> dispose());
        btnAutorizarSimulado.addActionListener(e -> autorizarContratacao()); // Adiciona ação

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(btnAutorizarSimulado); // Adiciona o botão à tela
        southPanel.add(btnAtualizar);
        southPanel.add(btnVoltar);

        getContentPane().setLayout(new BorderLayout(8, 8));
        getContentPane().add(new JLabel("Contratações Registradas (Aguardando Gestor):", JLabel.LEFT), BorderLayout.NORTH);
        getContentPane().add(scroll, BorderLayout.CENTER);
        getContentPane().add(southPanel, BorderLayout.SOUTH);
    }

    private void carregarContratacoes() {
        model.setRowCount(0);

        ArrayList<Contratacao> listaContratacoes = Helper.getInstance().getContratacoes();
        
        if (listaContratacoes == null || listaContratacoes.isEmpty()) {
            model.addRow(new Object[]{"Nenhuma", "solicitação", "de", "contratação", "encontrada", "."});
            return;
        }

        for (Contratacao c : listaContratacoes) {
            try {
                String cpf = c.getCandidaturaAprovada().getCandidato().getCpf();
                String nome = c.getCandidaturaAprovada().getCandidato().getNome();
                String vaga = c.getCandidaturaAprovada().getVaga().getCargo();
                String regime = c.getRegime();
                String data = c.getDataContratacao().format(dtf);
                String status = c.getStatusAutorizacao();

                model.addRow(new Object[]{cpf, nome, vaga, regime, data, status});
                
            } catch (Exception e) {
                model.addRow(new Object[]{"Erro", "ao", "ler", "dados", "da", "contratação"});
                System.err.println("Erro ao processar objeto Contratacao: " + e.getMessage());
            }
        }
    }
    
    /**
     * ===================================================================
     * O "AGORA": O MÉTODO DE AUTORIZAÇÃO SIMULADA
     * ===================================================================
     */
    private void autorizarContratacao() {
        int selectedRow = table.getSelectedRow();
        
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma contratação na tabela para autorizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Carrega a lista original
        ArrayList<Contratacao> listaContratacoes = Helper.getInstance().getContratacoes();
        
        // Pega o objeto Contratacao correspondente à linha
        Contratacao contratacaoAlvo = listaContratacoes.get(selectedRow);

        // Verifica se já não está autorizado
        if ("AUTORIZADO".equals(contratacaoAlvo.getStatusAutorizacao())) {
            JOptionPane.showMessageDialog(this, "Esta contratação já foi autorizada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Deseja simular a autorização do Gestor para:\n" + 
            contratacaoAlvo.getCandidaturaAprovada().getCandidato().getNome() + "?",
            "Simulação de Autorização",
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Modifica o objeto
                contratacaoAlvo.setStatusAutorizacao("AUTORIZADO");
                
                // Salva a lista inteira de volta no arquivo
                Helper.getInstance().saveObject(listaContratacoes, Constantes.PATH_CONTRATACOES);
                
                // Atualiza a tabela na UI
                carregarContratacoes();
                
                JOptionPane.showMessageDialog(this, "Contratação autorizada (simulado).", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar autorização: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecrutamentoConsultarContratacoes().setVisible(true));
    }
}
package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.dominio.Contratacao;
import com.tp1.GestaoRH.dominio.Entrevista; // Importação necessária
import com.tp1.GestaoRH.Misc.Helper;
import com.tp1.GestaoRH.Misc.Constantes;
import com.tp1.GestaoRH.dominio.RepositorioUsuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap; // Importação necessária
import java.util.Map; // Importação necessária

public class RecrutamentoConsultarContratacoes extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAtualizar, btnVoltar;
    private JButton btnAutorizar;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RecrutamentoConsultarContratacoes() {
        setTitle("Consultar Contratações");
        setSize(850, 400); // Aumentei a largura para a nova coluna
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        carregarContratacoes(); 
        verificarPermissaoGestor();
    }

    private void initComponents() {
        
        String[] colunas = {"CPF Candidato", "Nome", "Vaga", "Regime", "Data Contratação", "Status (Gestor)", "Nota"};

        model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 6) { // Coluna "Nota"
                    return Double.class; // Informa que é um número para ordenação correta
                }
                return String.class;
            }
        };

        table = new JTable(model);
        
        table.setAutoCreateRowSorter(true); // HABILITA A ORDENAÇÃO (REQUISITO 2)
        
        JScrollPane scroll = new JScrollPane(table);

        btnAtualizar = new JButton("Atualizar");
        btnVoltar = new JButton("Voltar");
        btnAutorizar = new JButton("Autorizar Contratação");

        btnAtualizar.addActionListener(e -> carregarContratacoes());
        btnVoltar.addActionListener(e -> dispose());
        btnAutorizar.addActionListener(e -> autorizarContratacao()); 

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(btnAutorizar); 
        southPanel.add(btnAtualizar);
        southPanel.add(btnVoltar);

        getContentPane().setLayout(new BorderLayout(8, 8));
        getContentPane().add(new JLabel("Contratações Registradas (Aguardando Gestor):", JLabel.LEFT), BorderLayout.NORTH);
        getContentPane().add(scroll, BorderLayout.CENTER);
        getContentPane().add(southPanel, BorderLayout.SOUTH);
    }

    private void verificarPermissaoGestor() {
        try {
            String cargo = RepositorioUsuario.usuarioLogado.getCargo();
            if (!"GestorRH".equals(cargo)) {
                btnAutorizar.setVisible(false);
            }
        } catch (Exception e) {
            btnAutorizar.setVisible(false); 
            System.err.println("Erro ao verificar permissão do usuário. Botão de autorização oculto.");
        }
    }

    private void carregarContratacoes() {
        model.setRowCount(0);

        // 1. Carrega as notas das entrevistas (o "join")
        ArrayList<Entrevista> listaEntrevistas = Helper.getInstance().getEntrevistas();
        Map<String, Double> notasMap = new HashMap<>();
        if (listaEntrevistas != null) {
            for (Entrevista e : listaEntrevistas) {
                try {
                    String key = e.getCandidato().getCpf() + e.getVaga().getCargo();
                    notasMap.put(key, e.getNota());
                } catch (Exception ex) {
                    System.err.println("Erro ao processar 'key' da entrevista.");
                }
            }
        }

        // 2. Carrega as contratações
        ArrayList<Contratacao> listaContratacoes = Helper.getInstance().getContratacoes();
        
        if (listaContratacoes == null || listaContratacoes.isEmpty()) {
            model.addRow(new Object[]{"Nenhuma", "solicitação", "de", "contratação", "encontrada", ".", 0.0});
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
                
                // Busca a nota correspondente
                String key = cpf + vaga;
                Double nota = notasMap.getOrDefault(key, 0.0); // 0.0 se não encontrar

                model.addRow(new Object[]{cpf, nome, vaga, regime, data, status, nota});
                
            } catch (Exception e) {
                model.addRow(new Object[]{"Erro", "ao", "ler", "dados", "da", "contratação", 0.0});
                System.err.println("Erro ao processar objeto Contratacao: " + e.getMessage());
            }
        }
    }
    
    private void autorizarContratacao() {
        int selectedRowVisual = table.getSelectedRow();
        if (selectedRowVisual < 0) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione uma contratação na tabela para autorizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Converte o índice visual (ordenado) para o índice real do modelo
        int modelRow = table.convertRowIndexToModel(selectedRowVisual);
        
        ArrayList<Contratacao> listaContratacoes = Helper.getInstance().getContratacoes();
        
        // Pega pelo índice do modelo, que corresponde à lista
        Contratacao contratacaoAlvo = listaContratacoes.get(modelRow);

        if ("AUTORIZADO".equals(contratacaoAlvo.getStatusAutorizacao())) {
            JOptionPane.showMessageDialog(this, "Esta contratação já foi autorizada.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Deseja autorizar a contratação de:\n" + 
            contratacaoAlvo.getCandidaturaAprovada().getCandidato().getNome() + "?",
            "Autorização de Gestor",
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                contratacaoAlvo.setStatusAutorizacao("AUTORIZADO");
                Helper.getInstance().saveObject(listaContratacoes, Constantes.PATH_CONTRATACOES);
                carregarContratacoes();
                JOptionPane.showMessageDialog(this, "Contratação autorizada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao salvar autorização: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecrutamentoConsultarContratacoes().setVisible(true));
    }
}
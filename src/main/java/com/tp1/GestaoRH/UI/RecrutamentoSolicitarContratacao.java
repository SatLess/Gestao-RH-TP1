package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.Candidatura.Candidatura;
import com.tp1.GestaoRH.dominio.Contratacao; // Importa a nova classe
import com.tp1.GestaoRH.Misc.Helper;
import com.tp1.GestaoRH.Misc.Constantes;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecrutamentoSolicitarContratacao extends JFrame {
    
    // ===================================================================
    // MUDANÇA DE DESIGN: Removemos cmbCandidato e cmbVaga
    // ===================================================================
    private JComboBox<String> cmbCandidaturasAprovadas;
    
    private JComboBox<String> cmbRegime;
    private JFormattedTextField txtData;
    private JButton btnSalvar;

    // O Mapa armazena a chave de exibição e o objeto Candidatura APROVADO
    private Map<String, Candidatura> dadosCandidaturas;

    public RecrutamentoSolicitarContratacao() {
        setTitle("Solicitar Contratação - Recrutamento");
        setSize(500, 280); // Ajuste de tamanho
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        carregarCandidaturasAprovadas(); // Método renomeado
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1;
        int linha = 0;

        // Linha 0: Candidatura Aprovada (UMA lista)
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Candidatura Aprovada:"), gbc);
        gbc.gridx = 1;
        // O JComboBox é populado com as chaves (Strings) do mapa
        cmbCandidaturasAprovadas = new JComboBox<>(dadosCandidaturas.keySet().toArray(new String[0]));
        panel.add(cmbCandidaturasAprovadas, gbc);

        // Linha 1: Regime
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Regime:"), gbc);
        gbc.gridx = 1;
        cmbRegime = new JComboBox<>(new String[]{"CLT", "Estágio", "PJ"}); // Conforme requisito
        panel.add(cmbRegime, gbc);

        // Linha 2: Data
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Data Contratação (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1;
        try {
            MaskFormatter mascara = new MaskFormatter("##/##/####");
            txtData = new JFormattedTextField(mascara);
            txtData.setColumns(20);
        } catch (Exception e) {
            txtData = new JFormattedTextField();
        }
        panel.add(txtData, gbc);

        // Linha 3: Botão Salvar
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnSalvar = new JButton("Salvar Solicitação");
        btnSalvar.addActionListener(e -> salvarSolicitacao());
        panel.add(btnSalvar, gbc);

        // Layout
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints center = new GridBagConstraints();
        center.gridx = 0; center.gridy = 0;
        getContentPane().add(panel, center);
    }

    /**
     * ===================================================================
     * CORREÇÃO CRÍTICA: Carrega APENAS candidatos "Aprovados"
     * ===================================================================
     */
    private void carregarCandidaturasAprovadas() {
        this.dadosCandidaturas = new HashMap<>();
        
        try {
            ArrayList<Candidatura> candidaturas = Helper.getInstance().getCandidatura();

            for (Candidatura c : candidaturas) {
                // O FILTRO (Correção do Erro 1)
                if ("Aprovado".equals(c.getStatus())) {
                    
                    // A CHAVE ÚNICA (Correção do Erro 2)
                    String chaveExibicao = c.getCandidato().getNome() + " (Vaga: " + c.getVaga().getCargo() + ")";
                    
                    // Adiciona ao mapa para seleção
                    this.dadosCandidaturas.put(chaveExibicao, c);
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao carregar candidaturas: " + e.getMessage());
        }
    }

    /**
     * ===================================================================
     * CORREÇÃO CRÍTICA: Salva o objeto Contratacao (Correção do Erro 3)
     * ===================================================================
     */
    private void salvarSolicitacao() {
        try {
            // 1. Obter a Candidatura (o objeto, não o texto)
            String chaveSelecionada = (String) cmbCandidaturasAprovadas.getSelectedItem();
            if (chaveSelecionada == null) {
                JOptionPane.showMessageDialog(this, "Nenhuma candidatura aprovada selecionada.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Candidatura candidaturaAlvo = dadosCandidaturas.get(chaveSelecionada);

            // 2. Obter os dados da UI
            String regime = (String) cmbRegime.getSelectedItem();
            String dataStr = txtData.getText();
            if (dataStr.trim().equals("/  /")) {
                JOptionPane.showMessageDialog(this, "A data de contratação é obrigatória.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            LocalDate data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            // 3. Criar a nova entidade de Contratação
            Contratacao novaContratacao = new Contratacao(candidaturaAlvo, regime, data);

            // 4. Salvar no "banco de dados" de contratações
            ArrayList<Contratacao> listaContratacoes = Helper.getInstance().getContratacoes();
            listaContratacoes.add(novaContratacao);
            Helper.getInstance().saveObject(listaContratacoes, Constantes.PATH_CONTRATACOES);

            JOptionPane.showMessageDialog(this, "Solicitação de contratação registrada com sucesso!\n"
                    + "Aguardando autorização do Gestor.",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
            
            // (Opcional) Mover o status da candidatura para "EM_CONTRATACAO"
            // (Opcional) Fechar a tela
            // dispose();

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de Data inválido. Use dd/MM/yyyy.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro de Sistema", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecrutamentoSolicitarContratacao().setVisible(true));
    }
}
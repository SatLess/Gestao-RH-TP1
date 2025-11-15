package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.Candidatura.Candidatura;
import com.tp1.GestaoRH.dominio.Contratacao; 
import com.tp1.GestaoRH.dominio.Entrevista; 
import com.tp1.GestaoRH.Misc.Helper;
import com.tp1.GestaoRH.Misc.Constantes;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;

import java.awt.event.ActionListener; 
import java.awt.event.ActionEvent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.SpinnerNumberModel;

public class RecrutamentoSolicitarContratacao extends JFrame {
    
    private JComboBox<String> cmbCandidaturasEmAnalise;
    private JSpinner spinnerNota;
    private JRadioButton rbAprovar;
    private JRadioButton rbReprovar;
    private ButtonGroup statusGroup;
    private JComboBox<String> cmbRegime;
    private JFormattedTextField txtData;
    private JButton btnSalvar;

    private Map<String, Candidatura> dadosCandidaturas;

    public RecrutamentoSolicitarContratacao() {
        setTitle("Avaliar e Solicitar Contratação");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Inicializa o mapa primeiro
        this.dadosCandidaturas = new HashMap<>(); 
        
        // Cria os componentes (vazios)
        initComponents(); 
        
        // Popula os componentes
        carregarCandidaturasEmAnalise(); 
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.weightx = 1;
        int linha = 0;

        // Linha 0
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Candidatura (Em Análise):"), gbc);
        gbc.gridx = 1;
        cmbCandidaturasEmAnalise = new JComboBox<>(); // Criado vazio
        panel.add(cmbCandidaturasEmAnalise, gbc);

        // Linha 1
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Nota (0-10):"), gbc);
        gbc.gridx = 1;
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0.0, 0.0, 10.0, 0.5);
        spinnerNota = new JSpinner(spinnerModel);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinnerNota, "0.0");
        spinnerNota.setEditor(editor);
        panel.add(spinnerNota, gbc);

        // Linha 2
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Decisão:"), gbc);
        gbc.gridx = 1;
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbAprovar = new JRadioButton("Aprovar");
        rbReprovar = new JRadioButton("Rejeitar");
        statusGroup = new ButtonGroup();
        statusGroup.add(rbAprovar);
        statusGroup.add(rbReprovar);
        statusPanel.add(rbAprovar);
        statusPanel.add(rbReprovar);
        panel.add(statusPanel, gbc);

        // Linha 3
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Regime (se Aprovado):"), gbc);
        gbc.gridx = 1;
        cmbRegime = new JComboBox<>(new String[]{"CLT", "Estágio", "PJ"});
        panel.add(cmbRegime, gbc);

        // Linha 4
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Data Contratação (se Aprovado):"), gbc);
        gbc.gridx = 1;
        try {
            MaskFormatter mascara = new MaskFormatter("##/##/####");
            txtData = new JFormattedTextField(mascara);
            txtData.setColumns(20);
        } catch (Exception e) {
            txtData = new JFormattedTextField();
        }
        panel.add(txtData, gbc);
        
        ActionListener listener = e -> atualizarVisibilidadeCampos();
        rbAprovar.addActionListener(listener);
        rbReprovar.addActionListener(listener);

        // Linha 5
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnSalvar = new JButton("Salvar Decisão");
        btnSalvar.addActionListener(e -> salvarDecisaoEContratacao());
        panel.add(btnSalvar, gbc);

        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints center = new GridBagConstraints();
        center.gridx = 0; center.gridy = 0;
        getContentPane().add(panel, center);
        
        atualizarVisibilidadeCampos();
    }

    private void atualizarVisibilidadeCampos() {
        boolean aprovado = rbAprovar.isSelected();
        cmbRegime.setEnabled(aprovado);
        txtData.setEnabled(aprovado);
    }

    private void carregarCandidaturasEmAnalise() {
        this.dadosCandidaturas.clear();
        cmbCandidaturasEmAnalise.removeAllItems();
        
        try {
            ArrayList<Candidatura> candidaturas = Helper.getInstance().getCandidatura();

            for (Candidatura c : candidaturas) {

                if ("EM_ANALISE".equalsIgnoreCase(c.getStatus().trim())) {
                    String chaveExibicao = c.getCandidato().getNome() + " (Vaga: " + c.getVaga().getCargo() + ")";
                    this.dadosCandidaturas.put(chaveExibicao, c);
                    cmbCandidaturasEmAnalise.addItem(chaveExibicao);
                }
            }
            
            if (cmbCandidaturasEmAnalise.getItemCount() == 0) {
                 cmbCandidaturasEmAnalise.addItem("Nenhuma candidatura em análise");
                 cmbCandidaturasEmAnalise.setEnabled(false);
                 btnSalvar.setEnabled(false);
            }

        } catch (Exception e) {
            System.err.println("Erro ao carregar candidaturas: " + e.getMessage());
        }
    }

    private void salvarDecisaoEContratacao() {
        try {
            String chaveSelecionada = (String) cmbCandidaturasEmAnalise.getSelectedItem();
            if (chaveSelecionada == null || !dadosCandidaturas.containsKey(chaveSelecionada)) {
                JOptionPane.showMessageDialog(this, "Nenhuma candidatura válida selecionada.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Candidatura candidaturaAlvo = dadosCandidaturas.get(chaveSelecionada);

            double novaNota = (Double) spinnerNota.getValue();
            String novoStatus;
            
            if (rbAprovar.isSelected()) {
                novoStatus = "Aprovado";
            } else if (rbReprovar.isSelected()) {
                novoStatus = "Rejeitado";
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione 'Aprovar' ou 'Rejeitar'.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Atualiza Nota na Entrevista
            Entrevista entrevistaOriginal = encontrarEntrevistaOriginal(candidaturaAlvo);
            if (entrevistaOriginal != null) {
                entrevistaOriginal.setNota(novaNota);
                ArrayList<Entrevista> listaEntrevistas = Helper.getInstance().getEntrevistas();
                listaEntrevistas.removeIf(e -> e.getCandidato().getCpf().equals(entrevistaOriginal.getCandidato().getCpf()) && e.getVaga().getCargo().equals(entrevistaOriginal.getVaga().getCargo()));
                listaEntrevistas.add(entrevistaOriginal);
                Helper.getInstance().saveObject(listaEntrevistas, Constantes.PATH_ENTREVISTAS);
            } else {
                JOptionPane.showMessageDialog(this, "Erro Crítico: A entrevista original não foi encontrada. A nota não pôde ser salva.", "Erro de Sincronização", JOptionPane.ERROR_MESSAGE);
            }

            // Atualiza Status na Candidatura
            ArrayList<Candidatura> listaCandidaturas = Helper.getInstance().getCandidatura();
            Candidatura candidaturaOriginal = encontrarCandidaturaOriginal(candidaturaAlvo, listaCandidaturas);
            
            if (candidaturaOriginal != null) {
                candidaturaOriginal.setStatus(novoStatus);
                Helper.getInstance().saveObject(listaCandidaturas, Constantes.PATHCANDIDATOS);
            } else {
                JOptionPane.showMessageDialog(this, "Erro Crítico: A candidatura original não foi encontrada.", "Erro de Sincronização", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Se Aprovado, cria a Contratação
            if ("Aprovado".equals(novoStatus)) {
                String regime = (String) cmbRegime.getSelectedItem();
                String dataStr = txtData.getText();
                if (dataStr.trim().equals("/  /")) {
                    JOptionPane.showMessageDialog(this, "Candidato Aprovado. A data de contratação é obrigatória.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                LocalDate data = LocalDate.parse(dataStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                Contratacao novaContratacao = new Contratacao(candidaturaOriginal, regime, data);

                ArrayList<Contratacao> listaContratacoes = Helper.getInstance().getContratacoes();
                listaContratacoes.add(novaContratacao);
                Helper.getInstance().saveObject(listaContratacoes, Constantes.PATH_CONTRATACOES);

                JOptionPane.showMessageDialog(this, "Candidato APROVADO e Solicitação de Contratação registrada!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            
            } else {
                JOptionPane.showMessageDialog(this, "Candidato REJEITADO com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
            
            // Recarrega o JComboBox
            carregarCandidaturasEmAnalise();
            
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de Data inválido. Use dd/MM/yyyy.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage(), "Erro de Sistema", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private Candidatura encontrarCandidaturaOriginal(Candidatura c, ArrayList<Candidatura> lista) {
        try {
            String cpfAlvo = c.getCandidato().getCpf();
            String vagaAlvo = c.getVaga().getCargo();
            for (Candidatura original : lista) {
                if (original.getCandidato().getCpf().equals(cpfAlvo) && 
                    original.getVaga().getCargo().equals(vagaAlvo)) {
                    return original;
                }
            }
        } catch (Exception e) { return null; }
        return null;
    }
    
    private Entrevista encontrarEntrevistaOriginal(Candidatura c) {
        ArrayList<Entrevista> lista = Helper.getInstance().getEntrevistas();
        try {
            String cpfAlvo = c.getCandidato().getCpf();
            String vagaAlvo = c.getVaga().getCargo();
            for (Entrevista e : lista) {
                if (e.getCandidato().getCpf().equals(cpfAlvo) && 
                    e.getVaga().getCargo().equals(vagaAlvo)) {
                    return e;
                }
            }
        } catch (Exception e) { return null; }
        return null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecrutamentoSolicitarContratacao().setVisible(true));
    }
}
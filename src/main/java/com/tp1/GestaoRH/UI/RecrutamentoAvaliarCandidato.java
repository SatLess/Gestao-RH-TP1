package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.Candidatura.Candidatura;
import com.tp1.GestaoRH.dominio.Entrevista;
import com.tp1.GestaoRH.Misc.Helper;
import com.tp1.GestaoRH.Misc.Constantes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecrutamentoAvaliarCandidato extends JFrame {

    private JComboBox<String> cmbEntrevistas;
    private JTextField txtNota;
    private JRadioButton rbAprovar;
    private JRadioButton rbReprovar;
    private JButton btnSalvarAvaliacao;
    private ButtonGroup statusGroup;

    private Map<String, Entrevista> dadosEntrevistas;
    private ArrayList<Entrevista> listaDeEntrevistas;

    public RecrutamentoAvaliarCandidato() {
        setTitle("Avaliar Entrevista - Recrutamento");
        setSize(550, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        carregarDadosReais();
        initComponents();
        
        // Atualiza a nota para o primeiro item
        atualizarCampos();
    }

    private void carregarDadosReais() {
        this.dadosEntrevistas = new HashMap<>();
        this.listaDeEntrevistas = Helper.getInstance().getEntrevistas(); // Assume que o Helper foi atualizado

        if (listaDeEntrevistas == null || listaDeEntrevistas.isEmpty()) {
            System.err.println("Nenhuma entrevista agendada encontrada.");
            return;
        }

        for (Entrevista entrevista : listaDeEntrevistas) {
            String chaveExibicao = entrevista.toString();
            this.dadosEntrevistas.put(chaveExibicao, entrevista);
        }
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        int linha = 0;

        // Linha 0: Seleção de Entrevista
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Entrevista Agendada:"), gbc);
        gbc.gridx = 1;
        cmbEntrevistas = new JComboBox<>(dadosEntrevistas.keySet().toArray(new String[0]));
        panel.add(cmbEntrevistas, gbc);

        // Linha 1: Campo de Nota
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Nota (0-10):"), gbc);
        gbc.gridx = 1;
        txtNota = new JTextField(20);
        panel.add(txtNota, gbc);

        // Linha 2: Decisão de Status
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Decisão:"), gbc);
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rbAprovar = new JRadioButton("Aprovar");
        rbReprovar = new JRadioButton("Reprovar");
        
        statusGroup = new ButtonGroup();
        statusGroup.add(rbAprovar);
        statusGroup.add(rbReprovar);
        
        statusPanel.add(rbAprovar);
        statusPanel.add(rbReprovar);
        
        gbc.gridx = 1;
        panel.add(statusPanel, gbc);

        // Ação ao trocar de entrevista
        cmbEntrevistas.addActionListener(e -> atualizarCampos());

        // Linha 3: Botão Salvar
        linha++;
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE; 
        btnSalvarAvaliacao = new JButton("Salvar Avaliação");
        btnSalvarAvaliacao.addActionListener(e -> salvarAvaliacao());
        panel.add(btnSalvarAvaliacao, gbc);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void atualizarCampos() {
        String chaveSelecionada = (String) cmbEntrevistas.getSelectedItem();
        if (chaveSelecionada == null) return;
        
        Entrevista entrevista = dadosEntrevistas.get(chaveSelecionada);
        
        // 1. Atualiza a nota (Isto estava correto)
        txtNota.setText(String.valueOf(entrevista.getNota()));
        
        // 2. Busca o status na fonte da verdade (candidatos.txt)
        Candidatura candidaturaOriginal = encontrarCandidaturaOriginal(entrevista);

        if (candidaturaOriginal != null) {
            String statusCandidatura = candidaturaOriginal.getStatus();
            if ("Aprovado".equals(statusCandidatura)) {
                rbAprovar.setSelected(true);
            } else if ("Rejeitado".equals(statusCandidatura)) {
                rbReprovar.setSelected(true);
            } else {
                statusGroup.clearSelection(); // Limpa se for "Em Analise"
            }
        } else {

            statusGroup.clearSelection();
            System.err.println("Aviso: Não foi possível encontrar a candidatura original para " + entrevista);
        }
    }

    private void salvarAvaliacao() {
        String chaveSelecionada = (String) cmbEntrevistas.getSelectedItem();
        if (chaveSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Nenhuma entrevista selecionada.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Entrevista entrevistaSelecionada = dadosEntrevistas.get(chaveSelecionada);

        try {
            double novaNota = Double.parseDouble(txtNota.getText().replace(",", "."));
            
            String novoStatus;
            if (rbAprovar.isSelected()) {
                novoStatus = "Aprovado";
            } else if (rbReprovar.isSelected()) {
                novoStatus = "Rejeitado";
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, selecione 'Aprovar' ou 'Reprovar'.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // --- AÇÃO DE SALVAMENTO 1: ATUALIZAR A ENTREVISTA ---
            entrevistaSelecionada.setNota(novaNota);
            Helper.getInstance().saveObject(this.listaDeEntrevistas, Constantes.PATH_ENTREVISTAS);
            
            
            // --- AÇÃO DE SALVAMENTO 2: ATUALIZAR A CANDIDATURA (A FONTE DA VERDADE) ---
            
            // Carrega a lista original de candidaturas
            ArrayList<Candidatura> listaOriginalCandidaturas = Helper.getInstance().getCandidatura();
            
            // Busca a candidatura original (usando o novo método)
            Candidatura candidaturaOriginal = encontrarCandidaturaOriginal(entrevistaSelecionada, listaOriginalCandidaturas);

            if (candidaturaOriginal != null) {
                // Encontramos! Atualiza o status
                candidaturaOriginal.setStatus(novoStatus); // Usa o setStatus(String) da sua classe
                
                // Salva a lista ORIGINAL de candidaturas de volta no arquivo
                Helper.getInstance().saveObject(listaOriginalCandidaturas, Constantes.PATHCANDIDATOS); // O SEU CAMINHO é "candidatos.txt"
                
                JOptionPane.showMessageDialog(this, "Avaliação salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                
            } else {
                JOptionPane.showMessageDialog(this, "Erro Crítico: A candidatura original não foi encontrada em 'candidatos.txt'.", "Erro de Sincronização", JOptionPane.ERROR_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Formato de Nota inválido. Use um número (ex: 8.5).", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado ao salvar: " + e.getMessage(), "Erro de Sistema", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

  
    private Candidatura encontrarCandidaturaOriginal(Entrevista entrevista) {
        ArrayList<Candidatura> listaOriginalCandidaturas = Helper.getInstance().getCandidatura();
        return encontrarCandidaturaOriginal(entrevista, listaOriginalCandidaturas);
    }
    
    /**
     * Sobrecarga do método de apoio para reutilização
     */
    private Candidatura encontrarCandidaturaOriginal(Entrevista entrevista, ArrayList<Candidatura> listaOriginalCandidaturas) {
        try {
            String cpfAlvo = entrevista.getCandidato().getCpf();
            String vagaAlvo = entrevista.getVaga().getCargo();

            for (Candidatura original : listaOriginalCandidaturas) {
                if (original.getCandidato().getCpf().equals(cpfAlvo) && 
                    original.getVaga().getCargo().equals(vagaAlvo)) {
                    
                    return original;
                }
            }
        } catch (Exception e) {
            // Proteção contra NullPointerException se getCandidato() ou getVaga() falharem
            System.err.println("Erro ao buscar candidatura original: " + e.getMessage());
        }
        return null; // Não encontrado
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecrutamentoAvaliarCandidato().setVisible(true));
    }
}
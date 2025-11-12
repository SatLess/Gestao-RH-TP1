package com.tp1.GestaoRH.UI;

// Imports necessários para conectar ao seu sistema
import com.tp1.GestaoRH.Candidatura.Candidatura;
import com.tp1.GestaoRH.Candidatura.Candidato; // Importação correta
import com.tp1.GestaoRH.Misc.Helper;
import com.tp1.GestaoRH.Misc.Constantes; 

// ===================================================================
// CORREÇÃO 1: A classe Vaga está no pacote 'dominio'
// ===================================================================
import com.tp1.GestaoRH.dominio.Vaga; 

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecrutamentoMarcarEntrevista extends JFrame {

    private JComboBox<String> cmbCandidato;
    private JTextField txtVagaAssociada;
    private JFormattedTextField txtData;
    private JTextField txtAvaliador, txtNota;
    private JButton btnSalvar;

    private Map<String, String> dadosCandidatosVaga;

    public RecrutamentoMarcarEntrevista() {
        setTitle("Marcar Entrevista - Recrutamento");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        carregarDadosReais();
        initComponents();
        atualizarVagaSelecionada();
    }

    private void carregarDadosReais() {
        this.dadosCandidatosVaga = new HashMap<>();
        
        ArrayList<Candidatura> listaDeCandidaturas = Helper.getInstance().getCandidatura();

        if (listaDeCandidaturas == null || listaDeCandidaturas.isEmpty()) {
            System.err.println("Nenhuma candidatura encontrada em " + Constantes.PATHCANDIDATOS);
            return;
        }

        for (Candidatura candidatura : listaDeCandidaturas) {
            
            // ===================================================================
            // CORREÇÃO 2: Comparamos a String "Em Analise" (conforme Candidatura.java)
            // ===================================================================
            if ("Em Analise".equals(candidatura.getStatus())) {
                
                try {
                    String nomeCandidato = candidatura.getCandidato().getNome();
                    
                    // ===================================================================
                    // CORREÇÃO 3: Usamos .getCargo() (conforme Vaga.java)
                    // ===================================================================
                    String nomeVaga = candidatura.getVaga().getCargo(); 

                    // Cria uma chave única para o JComboBox
                    String chaveUnica = nomeCandidato + " (" + nomeVaga + ")";
                    
                    this.dadosCandidatosVaga.put(chaveUnica, nomeVaga);

                } catch (NullPointerException e) {
                    // Captura erros se getCandidato() ou getVaga() forem nulos
                    System.err.println("Erro ao processar candidatura: Candidato ou Vaga nulos. " + e.getMessage());
                } catch (Exception e) {
                    // Captura outros erros inesperados
                    System.err.println("Erro inesperado ao processar candidatura: " + e.getMessage());
                }
            }
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

        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Candidato / Vaga:"), gbc);
        gbc.gridx = 1;
        cmbCandidato = new JComboBox<>(dadosCandidatosVaga.keySet().toArray(new String[0]));
        panel.add(cmbCandidato, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Vaga Associada:"), gbc);
        gbc.gridx = 1;
        txtVagaAssociada = new JTextField(20);
        txtVagaAssociada.setEditable(false);
        txtVagaAssociada.setFont(txtVagaAssociada.getFont().deriveFont(Font.ITALIC | Font.BOLD));
        txtVagaAssociada.setBackground(Color.LIGHT_GRAY);
        panel.add(txtVagaAssociada, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Data (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1;
        try {
            MaskFormatter mascaraData = new MaskFormatter("##/##/####");
            txtData = new JFormattedTextField(mascaraData);
            txtData.setColumns(20);
        } catch (Exception e) {
            txtData = new JFormattedTextField();
        }
        panel.add(txtData, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Avaliador:"), gbc);
        gbc.gridx = 1;
        txtAvaliador = new JTextField(20);
        panel.add(txtAvaliador, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Nota:"), gbc);
        gbc.gridx = 1;
        txtNota = new JTextField(20);
        panel.add(txtNota, gbc);

        cmbCandidato.addActionListener(e -> atualizarVagaSelecionada());

        linha++;
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE; 
        btnSalvar = new JButton("Salvar Agendamento");
        panel.add(btnSalvar, gbc);

        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints center = new GridBagConstraints();
        center.gridx = 0;
        center.gridy = 0;
        center.anchor = GridBagConstraints.NORTH;
        center.insets = new Insets(10, 10, 10, 10);
        getContentPane().add(panel, center);
    }

    private void atualizarVagaSelecionada() {
        String chaveSelecionada = (String) cmbCandidato.getSelectedItem();
        if (chaveSelecionada != null) {
            String vaga = dadosCandidatosVaga.get(chaveSelecionada);
            txtVagaAssociada.setText(vaga != null ? vaga : "Vaga não encontrada");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecrutamentoMarcarEntrevista().setVisible(true));
    }
}
package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.Candidatura.Candidatura;
import com.tp1.GestaoRH.Candidatura.Candidato;
import com.tp1.GestaoRH.dominio.Entrevista; 
// Não precisamos mais do 'Recrutador', pois não validamos
import com.tp1.GestaoRH.Misc.Helper;
import com.tp1.GestaoRH.Misc.Constantes; 
import com.tp1.GestaoRH.dominio.Vaga; 

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate; 
import java.time.format.DateTimeFormatter; 
import java.time.format.DateTimeParseException; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecrutamentoMarcarEntrevista extends JFrame {

    private JComboBox<String> cmbCandidato;
    private JTextField txtVagaAssociada;
    private JFormattedTextField txtData;
    private JTextField txtNota;
    private JButton btnSalvar;
    private JTextField txtAvaliador; 

    private Map<String, Candidatura> dadosCandidaturas;

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
        this.dadosCandidaturas = new HashMap<>();
        ArrayList<Candidatura> listaDeCandidaturas = Helper.getInstance().getCandidatura();

        if (listaDeCandidaturas == null) return;

        for (Candidatura candidatura : listaDeCandidaturas) {
            if ("Em Analise".equals(candidatura.getStatus())) {
                try {
                    String nomeCandidato = candidatura.getCandidato().getNome();
                    String nomeVaga = candidatura.getVaga().getCargo(); 
                    String chaveUnica = nomeCandidato + " (" + nomeVaga + ")";
                    this.dadosCandidaturas.put(chaveUnica, candidatura);
                } catch (Exception e) {
                    System.err.println("Erro ao processar candidatura: " + e.getMessage());
                }
            }
        }
    }

    private void initComponents() {
        // ... (O código de initComponents permanece o mesmo da versão anterior) ...
        // ... (Garantindo que 'txtAvaliador' é um JTextField) ...
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        int linha = 0;

        // Candidato (Linha 0)
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Candidato / Vaga:"), gbc);
        gbc.gridx = 1;
        cmbCandidato = new JComboBox<>(dadosCandidaturas.keySet().toArray(new String[0]));
        panel.add(cmbCandidato, gbc);

        // Vaga Associada (Linha 1)
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Vaga Associada:"), gbc);
        gbc.gridx = 1;
        txtVagaAssociada = new JTextField(20);
        txtVagaAssociada.setEditable(false);
        txtVagaAssociada.setFont(txtVagaAssociada.getFont().deriveFont(Font.ITALIC | Font.BOLD));
        txtVagaAssociada.setBackground(Color.LIGHT_GRAY);
        panel.add(txtVagaAssociada, gbc);

        // Data (Linha 2)
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

        // Avaliador (Linha 3) - JTextField
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Avaliador:"), gbc);
        gbc.gridx = 1;
        txtAvaliador = new JTextField(20); 
        panel.add(txtAvaliador, gbc);

        // Nota (Linha 4)
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Nota:"), gbc);
        gbc.gridx = 1;
        txtNota = new JTextField(20);
        panel.add(txtNota, gbc);

        cmbCandidato.addActionListener(e -> atualizarVagaSelecionada());

        // Botão Salvar (Linha 5)
        linha++;
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE; 
        btnSalvar = new JButton("Salvar Agendamento");
        
        btnSalvar.addActionListener(e -> salvarAgendamento()); 
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
            Candidatura cand = dadosCandidaturas.get(chaveSelecionada);
            if (cand != null) {
                txtVagaAssociada.setText(cand.getVaga().getCargo());
            } else {
                txtVagaAssociada.setText("Vaga não encontrada");
            }
        }
    }
    

    private void salvarAgendamento() {
        
        try {
          
            String nomeAvaliador = txtAvaliador.getText().trim();
            if (nomeAvaliador.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, digite o nome do Avaliador.", "Campos Obrigatórios", JOptionPane.ERROR_MESSAGE);
                return;
            }

            
            String dataStr = txtData.getText();
            if (dataStr.trim().equals("/  /")) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha a Data.", "Campos Obrigatórios", JOptionPane.ERROR_MESSAGE);
                return;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataParseada = LocalDate.parse(dataStr, formatter);
            
            String notaStr = txtNota.getText().trim();
            double notaParseada = 0.0; 
            if (!notaStr.isEmpty()) {
                notaParseada = Double.parseDouble(notaStr.replace(",", "."));
            }

            // --- Obtenção dos Objetos-Chave ---
            String chaveCandidatura = (String) cmbCandidato.getSelectedItem();
            if (chaveCandidatura == null) {
                JOptionPane.showMessageDialog(this, "Nenhum candidato selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Candidatura candidaturaAlvo = dadosCandidaturas.get(chaveCandidatura);
            
            // Criar o objeto Entrevista (Passando a String 'nomeAvaliador')
            Entrevista novaEntrevista = new Entrevista(
                candidaturaAlvo.getVaga(), 
                candidaturaAlvo.getCandidato(), 
                dataParseada, 
                nomeAvaliador, // Passamos a String diretamente
                notaParseada
            );

            // --- Persistência ---
            ArrayList<Entrevista> listaEntrevistas = Helper.getInstance().getEntrevistas();
            listaEntrevistas.add(novaEntrevista);
            Helper.getInstance().saveObject(listaEntrevistas, Constantes.PATH_ENTREVISTAS);
            
            // Notificar o usuário
            JOptionPane.showMessageDialog(this, 
                "Entrevista para " + candidaturaAlvo.getCandidato().getNome() + " agendada com sucesso!", 
                "Agendamento Efetuado", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de Data inválido. Use dd/MM/yyyy.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Formato de Nota inválido. Use um número (ex: 8.5).", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado ao salvar: " + e.getMessage(), "Erro de Sistema", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecrutamentoMarcarEntrevista().setVisible(true));
    }
}
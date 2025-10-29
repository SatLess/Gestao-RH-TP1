package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.Candidatura.Candidatura;
import com.tp1.GestaoRH.dominio.RecrutamentoPersistencia;
import com.tp1.GestaoRH.dominio.Vaga;
import com.tp1.GestaoRH.Misc.Helper;
import com.tp1.GestaoRH.Misc.Constantes;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RecrutamentoSolicitarContratacao extends JFrame {
    private JComboBox<String> cmbCandidato, cmbVaga, cmbRegime;
    private JFormattedTextField txtData;
    private JButton btnSalvar;

    public RecrutamentoSolicitarContratacao() {
        setTitle("Solicitar Contratação - Recrutamento");
        setSize(420, 320);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
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

        // Candidato
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Candidato:"), gbc);
        gbc.gridx = 1;
        cmbCandidato = new JComboBox<>();
        carregarCandidatos();
        panel.add(cmbCandidato, gbc);

        // Vaga
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Vaga:"), gbc);
        gbc.gridx = 1;
        cmbVaga = new JComboBox<>();
        carregarVagas();
        panel.add(cmbVaga, gbc);

        // Regime
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Regime:"), gbc);
        gbc.gridx = 1;
        cmbRegime = new JComboBox<>(new String[]{"CLT", "Estágio", "PJ"});
        panel.add(cmbRegime, gbc);

        // Data
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Data (dd/MM/yyyy):"), gbc);
        gbc.gridx = 1;
        try {
            MaskFormatter mascara = new MaskFormatter("##/##/####");
            txtData = new JFormattedTextField(mascara);
            txtData.setColumns(20);
        } catch (Exception e) {
            txtData = new JFormattedTextField();
        }
        panel.add(txtData, gbc);

        // Botão Salvar
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarSolicitacao());
        panel.add(btnSalvar, gbc);

        // Layout
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints center = new GridBagConstraints();
        center.gridx = 0; center.gridy = 0;
        getContentPane().add(panel, center);
    }

    private void carregarCandidatos() {
        try {
            ArrayList<Candidatura> candidaturas = new ArrayList<>();
                if (new File("candidatos.txt").exists()) {
                    candidaturas = (ArrayList<Candidatura>) Helper.getInstance().loadObject(Constantes.PATHCANDIDATOS);
                }


            for (Candidatura c : candidaturas) {
                if (c.getCandidato() != null)
                    cmbCandidato.addItem(c.getCandidato().getNome());
            }

            if (cmbCandidato.getItemCount() == 0)
                cmbCandidato.addItem("Nenhum candidato encontrado");
        } catch (Exception e) {
            cmbCandidato.addItem("Erro ao carregar");
        }
    }

    private void carregarVagas() {
        try {
            List<Vaga> vagas = RecrutamentoPersistencia.carregarVagas();
            for (Vaga v : vagas) {
                cmbVaga.addItem(v.getCargo() + " (" + v.getId() + ")");
            }
            if (cmbVaga.getItemCount() == 0)
                cmbVaga.addItem("Nenhuma vaga encontrada");
        } catch (Exception e) {
            cmbVaga.addItem("Erro ao carregar");
        }
    }

    private void salvarSolicitacao() {
        try {
            String candidato = (String) cmbCandidato.getSelectedItem();
            String vaga = (String) cmbVaga.getSelectedItem();
            String regime = (String) cmbRegime.getSelectedItem();
            LocalDate data = LocalDate.parse(txtData.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            JOptionPane.showMessageDialog(this, "Solicitação registrada:\n"
                    + "Candidato: " + candidato + "\n"
                    + "Vaga: " + vaga + "\n"
                    + "Regime: " + regime + "\n"
                    + "Data: " + data);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecrutamentoSolicitarContratacao().setVisible(true));
    }
}

package com.tp1.GestaoRH.UI;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;

public class RecrutamentoMarcarEntrevista extends JFrame {
    private JTextField txtCandidato, txtVaga, txtAvaliador, txtNota;
    private JFormattedTextField txtData;
    private JButton btnSalvar;

    public RecrutamentoMarcarEntrevista() {
        setTitle("Marcar Entrevista - Recrutamento");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        int linha = 0;

        // Candidato
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Candidato:"), gbc);
        gbc.gridx = 1;
        txtCandidato = new JTextField(20);
        panel.add(txtCandidato, gbc);

        // Vaga
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Vaga:"), gbc);
        gbc.gridx = 1;
        txtVaga = new JTextField(20);
        panel.add(txtVaga, gbc);

        // Data
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

        // Avaliador
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Avaliador:"), gbc);
        gbc.gridx = 1;
        txtAvaliador = new JTextField(20);
        panel.add(txtAvaliador, gbc);

        // Nota
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Nota:"), gbc);
        gbc.gridx = 1;
        txtNota = new JTextField(20);
        panel.add(txtNota, gbc);

        // Botão Salvar
        linha++;
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        btnSalvar = new JButton("Salvar");
        panel.add(btnSalvar, gbc);

        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints center = new GridBagConstraints();
        center.gridx = 0;
        center.gridy = 0;
        center.anchor = GridBagConstraints.CENTER;
        getContentPane().add(panel, center);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecrutamentoMarcarEntrevista().setVisible(true));
    }
}

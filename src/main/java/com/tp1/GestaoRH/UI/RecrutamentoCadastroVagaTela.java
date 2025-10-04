package com.tp1.GestaoRH.UI;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;

public class RecrutamentoCadastroVagaTela extends JFrame {
    private JTextField txtId, txtCargo, txtSalario, txtDepartamento;
    private JComboBox<String> cmbStatus;
    private JFormattedTextField txtData;

    public RecrutamentoCadastroVagaTela() {
        setTitle("Cadastro de Vaga - Recrutamento");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); 
        gbc.anchor = GridBagConstraints.WEST; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        int linha = 0;

        // ID
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        txtId = new JTextField(20);
        panel.add(txtId, gbc);

        // Cargo
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Cargo:"), gbc);
        gbc.gridx = 1;
        txtCargo = new JTextField(20);
        panel.add(txtCargo, gbc);

        // Salário
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Salário:"), gbc);
        gbc.gridx = 1;
        txtSalario = new JTextField(20);
        panel.add(txtSalario, gbc);

        // Departamento
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Departamento:"), gbc);
        gbc.gridx = 1;
        txtDepartamento = new JTextField(20);
        panel.add(txtDepartamento, gbc);

        // Status
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Status:"), gbc);
        gbc.gridx = 1;
        cmbStatus = new JComboBox<>(new String[]{"Aberta", "Fechada"});
        panel.add(cmbStatus, gbc);

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

        // Botão Salvar
        linha++;
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER; // centraliza o botão
        JButton btnSalvar = new JButton("Salvar");
        panel.add(btnSalvar, gbc);

        // Centralizar o painel na vertical
        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints center = new GridBagConstraints();
        center.gridx = 0;
        center.gridy = 0;
        center.anchor = GridBagConstraints.CENTER;
        getContentPane().add(panel, center);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecrutamentoCadastroVagaTela().setVisible(true));
    }
}

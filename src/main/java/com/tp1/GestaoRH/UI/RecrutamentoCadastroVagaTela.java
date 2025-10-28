package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.dominio.RecrutamentoPersistencia;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import com.tp1.GestaoRH.Misc.Constantes;
import com.tp1.GestaoRH.dominio.Vaga;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RecrutamentoCadastroVagaTela extends JFrame {

    private JTextField txtId, txtCargo, txtDepartamento, txtSalario;
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
        txtSalario.setHorizontalAlignment(JTextField.RIGHT);
        txtSalario.setText("0,00");

        // Comportamento monetário ao digitar
        txtSalario.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String texto = txtSalario.getText().replaceAll("[^0-9]", "");
                if (texto.isEmpty()) texto = "0";
                double valor = Double.parseDouble(texto) / 100.0;
                java.text.DecimalFormat df = new java.text.DecimalFormat("#,##0.00");
                txtSalario.setText(df.format(valor));
            }
        });

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
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btnSalvar = new JButton("Salvar");

        // Ação do botão
        btnSalvar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                String cargo = txtCargo.getText();

                // Lê o valor formatado corretamente
                double salario = Double.parseDouble(
                        txtSalario.getText().replace(".", "").replace(",", ".")
                );

                String departamento = txtDepartamento.getText();
                String statusStr = (String) cmbStatus.getSelectedItem();
                Constantes.STATUS status = statusStr.equals("Aberta")
                        ? Constantes.STATUS.ABERTA
                        : Constantes.STATUS.FECHADA;
                LocalDate data = LocalDate.parse(txtData.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                // Cria a vaga
                Vaga novaVaga = new Vaga(id, cargo, salario, departamento, status, data);

                // Carrega lista e adiciona nova vaga
                List<Vaga> vagas = RecrutamentoPersistencia.carregarVagas();
                vagas.add(novaVaga);
                RecrutamentoPersistencia.salvarVagas(vagas);

                JOptionPane.showMessageDialog(this, "✅ Vaga cadastrada com sucesso!");

                // Limpa os campos
                txtId.setText("");
                txtCargo.setText("");
                txtSalario.setText("0,00");
                txtDepartamento.setText("");
                txtData.setText("");
                cmbStatus.setSelectedIndex(0);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "❌ Erro ao salvar vaga: " + ex.getMessage());
            }
        });

        panel.add(btnSalvar, gbc);

        // Centralizar painel
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

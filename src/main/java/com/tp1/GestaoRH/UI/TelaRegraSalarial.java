package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.dominio.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class TelaRegraSalarial extends JFrame {
    private String tipoUsuario;
    private JTextField vtField, vaField, impField;
    private JButton salvarBtn, voltarBtn;

    public TelaRegraSalarial(String tipoUsuario) {
        super("Configurar Regras Salariais"); this.tipoUsuario = tipoUsuario; init();
    }

    private void init() {
        setSize(480,320); setLocationRelativeTo(null); setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(8,8));
        vtField = new JTextField(); vaField = new JTextField(); impField = new JTextField();
        RegraSalario r = RegraSalario.carregar();
        vtField.setText(String.format("%.2f", r.getValeTransporte()));
        vaField.setText(String.format("%.2f", r.getValeAlimentacao()));
        impField.setText(String.format("%.2f", r.getImposto()*100));
        JPanel panel = new JPanel(new GridLayout(3,2,8,8)); panel.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        panel.add(new JLabel("Vale Transporte (R$):")); panel.add(vtField);
        panel.add(new JLabel("Vale Alimentação (R$):")); panel.add(vaField);
        panel.add(new JLabel("Imposto (%):")); panel.add(impField);
        salvarBtn = new JButton("Salvar"); voltarBtn = new JButton("Voltar");
        salvarBtn.addActionListener(e -> salvar()); voltarBtn.addActionListener(e -> { new MenuFinanceiro(tipoUsuario).setVisible(true); dispose(); });
        JPanel botoes = new JPanel(); botoes.add(salvarBtn); botoes.add(voltarBtn);
        add(new JLabel("Configurar Regras Salariais", SwingConstants.CENTER), BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER); add(botoes, BorderLayout.SOUTH);
    }

    private void salvar() {
        if (!"Administrador".equalsIgnoreCase(tipoUsuario)) { JOptionPane.showMessageDialog(this, "Apenas administrador pode salvar."); return; }
        try {
            double vt = Double.parseDouble(vtField.getText().trim());
            double va = Double.parseDouble(vaField.getText().trim());
            double imp = Double.parseDouble(impField.getText().trim())/100.0;
            RegraSalario r = new RegraSalario(vt, va, imp);
            RegraSalario.salvar(r);
            JOptionPane.showMessageDialog(this, "Regras salvas com sucesso.");
            new MenuFinanceiro(tipoUsuario).setVisible(true); dispose();
        } catch(NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Valores inválidos."); }
        catch(IOException ex) { JOptionPane.showMessageDialog(this, "Erro ao salvar regras: " + ex.getMessage()); }
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new TelaRegraSalarial("Administrador").setVisible(true)); }
}

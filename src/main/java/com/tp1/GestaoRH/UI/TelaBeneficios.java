package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.dominio.RegraSalario;
import com.tp1.GestaoRH.dominio.RepositorioUsuario;
import javax.swing.*;
import java.awt.*;

public class TelaBeneficios extends JFrame {

    private final String tipoUsuario;  // Armazena o tipo para voltar ao menu

    public TelaBeneficios(String tipoUsuario) {
        super("Meus Benefícios");
        this.tipoUsuario = tipoUsuario;
        initComponents();
        carregarBeneficios();
    }

    private void initComponents() {
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Meus Benefícios", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);

        JPanel painel = new JPanel(new GridLayout(2, 2, 10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblVT = new JLabel("Vale Transporte: Carregando...");
        JLabel lblVA = new JLabel("Vale Alimentação: Carregando...");
        painel.add(lblVT);
        painel.add(lblVA);

        add(painel, BorderLayout.CENTER);

        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> voltarAoMenu());
        JPanel sul = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sul.add(btnVoltar);
        add(sul, BorderLayout.SOUTH);
    }

    private void carregarBeneficios() {
        try {
            RegraSalario regra = RegraSalario.carregar();
            JPanel centro = (JPanel) getContentPane().getComponent(1);
            JLabel lblVT = (JLabel) centro.getComponent(0);
            JLabel lblVA = (JLabel) centro.getComponent(1);

            lblVT.setText("Vale Transporte: R$ " + String.format("%.2f", regra.getValeTransporte()));
            lblVA.setText("Vale Alimentação: R$ " + String.format("%.2f", regra.getValeAlimentacao()));

            // Personaliza com o usuário logado
            String nome = RepositorioUsuario.usuarioLogado != null ? RepositorioUsuario.usuarioLogado.getLogin() : "Usuário";
            ((JLabel) getContentPane().getComponent(0)).setText("Benefícios de " + nome);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar benefícios: " + ex.getMessage());
        }
    }

    private void voltarAoMenu() {
        new MenuFinanceiro(tipoUsuario).setVisible(true);  // Volta ao menu com o mesmo tipo de usuário
        dispose();  // Fecha esta tela
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaBeneficios("Administrador").setVisible(true));
    }
}
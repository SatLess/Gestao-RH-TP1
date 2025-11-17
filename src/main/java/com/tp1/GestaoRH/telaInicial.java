package com.tp1.GestaoRH;

import com.tp1.GestaoRH.dominio.*;
import javax.swing.*;
import java.awt.*;

public class telaInicial extends JFrame {

    private JTextField loginField;
    private JPasswordField senhaField;
    private JButton entrarBtn;
    private JButton sairBtn;
    private JButton cadastroBtn;

    public telaInicial() {
        super("Login - Gestão RH");
        RepositorioUsuario.inicializarPadrao();
        init();
    }

    private void init() {
        setSize(420,220);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4,2,8,8));

        add(new JLabel("Login:"));
        loginField = new JTextField();
        add(loginField);

        add(new JLabel("Senha:"));
        senhaField = new JPasswordField();
        add(senhaField);
      

        entrarBtn = new JButton("Entrar");
        entrarBtn.addActionListener(e -> autenticar());
        add(entrarBtn);

        sairBtn = new JButton("Sair");
        sairBtn.addActionListener(e -> System.exit(0));
        add(sairBtn);
        
        cadastroBtn = new JButton("Cadastro");
        cadastroBtn.addActionListener(e -> new cadastro().setVisible(true));
        add(cadastroBtn);
        
    }

    private void autenticar() {
        String login = loginField.getText().trim();
        String senha = new String(senhaField.getPassword()).trim();

        com.tp1.GestaoRH.dominio.Usuario u = RepositorioUsuario.autenticar(login, senha);
        if (u == null) {
            JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
            loginField.setText("");
            senhaField.setText("");
            return;
        }

        
        String cargo = u.getCargo();
        RepositorioUsuario.setUsuario(u);
        
        switch (cargo) {
            case "Administrador" -> {
                new com.tp1.GestaoRH.ADMGUI().setVisible(true);
                new com.tp1.GestaoRH.UI.MenuFinanceiro("Administrador").setVisible(true);
                new com.tp1.GestaoRH.UI.MenuRecrutamento().setVisible(true);
            }
            case "GestorRH" -> {
                new com.tp1.GestaoRH.UI.MenuFinanceiro("GestorRH").setVisible(true);
                new com.tp1.GestaoRH.UI.MenuRecrutamento().setVisible(true);
            }
            case "Recrutador" -> new com.tp1.GestaoRH.UI.MenuRecrutamento().setVisible(true);
            case "FuncionarioGeral" -> new com.tp1.GestaoRH.UI.MenuFinanceiro("FuncionarioGeral").setVisible(true);
            default -> {
                RepositorioUsuario.setUsuario(null);
                JOptionPane.showMessageDialog(this, "Cargo sem acesso ao sistema.");
                return;
            }
        }
        dispose();


        
        
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new telaInicial().setVisible(true));
    }
}

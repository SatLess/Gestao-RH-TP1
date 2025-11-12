package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.dominio.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaCadastroFuncionario extends JFrame {
    private String tipoUsuario;
    private JTextField nomeF, emailF, enderecoF, cpfF, salarioF;
    private JComboBox<String> cargoBox, tipoBox, statusBox;
    private JButton salvarBtn, voltarBtn;

    public TelaCadastroFuncionario(String tipoUsuario) {
        super("Cadastro de Funcionário");
        this.tipoUsuario = tipoUsuario;
        init();
    }

    private void init() {
        setSize(560,420); setLocationRelativeTo(null); setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(8,8));
        JPanel form = new JPanel(new GridLayout(8,2,8,8));
        form.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        nomeF = new JTextField(); emailF = new JTextField(); enderecoF = new JTextField(); cpfF = new JTextField(); salarioF = new JTextField();
        cargoBox = new JComboBox<>(new String[]{"Administrador","GestorRH","RecrutadorRH","FuncionarioGeral"});
        tipoBox = new JComboBox<>(new String[]{"CLT","Estágio","PJ"}); statusBox = new JComboBox<>(new String[]{"Ativo","Inativo"});
        form.add(new JLabel("Nome:")); form.add(nomeF);
        form.add(new JLabel("Email:")); form.add(emailF);
        form.add(new JLabel("Endereço:")); form.add(enderecoF);
        form.add(new JLabel("CPF:")); form.add(cpfF);
        form.add(new JLabel("Salário Base:")); form.add(salarioF);
        form.add(new JLabel("Cargo:")); form.add(cargoBox);
        form.add(new JLabel("Tipo Contratação:")); form.add(tipoBox);
        form.add(new JLabel("Status:")); form.add(statusBox);

        salvarBtn = new JButton("Salvar"); voltarBtn = new JButton("Voltar"); 
        salvarBtn.addActionListener(e -> salvar()); voltarBtn.addActionListener(e -> { new MenuFinanceiro(tipoUsuario).setVisible(true); dispose(); });

        JPanel botoes = new JPanel(); botoes.add(salvarBtn); botoes.add(voltarBtn);
        add(new JLabel("Cadastro de Funcionário", SwingConstants.CENTER), BorderLayout.NORTH);
        add(form, BorderLayout.CENTER); add(botoes, BorderLayout.SOUTH);
    }

    private void salvar() {
        try {
            String nome = nomeF.getText().trim();
            String email = emailF.getText().trim();
            String endereco = enderecoF.getText().trim();
            String cpf = cpfF.getText().trim();
            double salario = Double.parseDouble(salarioF.getText().trim());
            String cargo = (String)cargoBox.getSelectedItem();
            String tipo = (String)tipoBox.getSelectedItem();
            boolean ativo = "Ativo".equals(statusBox.getSelectedItem());
            if (nome.isEmpty() || cpf.isEmpty()) { JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios."); return; }
            Funcionario f = new Funcionario(nome,email,endereco,cpf,cargo,salario,tipo,ativo);
            RepositorioFuncionario.adicionar(f);
            JOptionPane.showMessageDialog(this, "Funcionário salvo com sucesso.");
            new MenuFinanceiro(tipoUsuario).setVisible(true); dispose();
        } catch(NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Salário inválido."); 
        }
    }

    public static void main(String[] args) { SwingUtilities.invokeLater(() -> new TelaCadastroFuncionario("GestorRH").setVisible(true)); }
}

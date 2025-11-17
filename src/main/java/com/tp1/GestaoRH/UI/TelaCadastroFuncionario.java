package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.dominio.*;
import javax.swing.*;
import java.awt.*;

public class TelaCadastroFuncionario extends JFrame {
    private String tipoUsuario;

    private JTextField nomeF, emailF, enderecoF, telefoneF, cpfF, salarioF;
    private JComboBox<String> cargoBox, tipoBox, statusBox, departamentoBox;

    public TelaCadastroFuncionario(String tipoUsuario) {
        super("Cadastro de Funcionário");
        this.tipoUsuario = tipoUsuario;
        init();
    }

    private void init() {
        setSize(600, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel titulo = new JLabel("Cadastro de Funcionário", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        add(titulo, BorderLayout.NORTH);


        // Painel principal com duas seções
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));


        //--------------------------------------------------
        // SESSÃO 1 — DADOS PESSOAIS
        //--------------------------------------------------
        JPanel dadosPessoais = new JPanel(new GridLayout(5, 2, 10, 10));
        dadosPessoais.setBorder(BorderFactory.createTitledBorder("Dados Pessoais"));

        nomeF = new JTextField();
        emailF = new JTextField();
        enderecoF = new JTextField();
        telefoneF = new JTextField();
        cpfF = new JTextField();

        dadosPessoais.add(new JLabel("Nome:")); dadosPessoais.add(nomeF);
        dadosPessoais.add(new JLabel("Email:")); dadosPessoais.add(emailF);
        dadosPessoais.add(new JLabel("Endereço:")); dadosPessoais.add(enderecoF);
        dadosPessoais.add(new JLabel("Telefone:")); dadosPessoais.add(telefoneF);
        dadosPessoais.add(new JLabel("CPF:")); dadosPessoais.add(cpfF);


        //--------------------------------------------------
        // SESSÃO 2 — DADOS PROFISSIONAIS
        //--------------------------------------------------
        JPanel dadosProfissionais = new JPanel(new GridLayout(5, 2, 10, 10));
        dadosProfissionais.setBorder(BorderFactory.createTitledBorder("Dados Profissionais"));

        salarioF = new JTextField();
        cargoBox = new JComboBox<>(new String[]{"Administrador", "GestorRH", "RecrutadorRH", "FuncionarioGeral"});
        departamentoBox = new JComboBox<>(new String[]{"RH", "Financeiro", "TI", "Administração"});
        tipoBox = new JComboBox<>(new String[]{"CLT", "Estágio", "PJ"});
        statusBox = new JComboBox<>(new String[]{"Ativo", "Inativo"});

        dadosProfissionais.add(new JLabel("Salário Base:")); dadosProfissionais.add(salarioF);
        dadosProfissionais.add(new JLabel("Cargo:")); dadosProfissionais.add(cargoBox);
        dadosProfissionais.add(new JLabel("Departamento:")); dadosProfissionais.add(departamentoBox);
        dadosProfissionais.add(new JLabel("Tipo Contratação:")); dadosProfissionais.add(tipoBox);
        dadosProfissionais.add(new JLabel("Status:")); dadosProfissionais.add(statusBox);


        painel.add(dadosPessoais);
        painel.add(Box.createVerticalStrut(10)); // espaçamento
        painel.add(dadosProfissionais);

        add(painel, BorderLayout.CENTER);


        //--------------------------------------------------
        // BOTÕES
        //--------------------------------------------------
        JButton salvarBtn = new JButton("Salvar");
        JButton voltarBtn = new JButton("Voltar");

        JPanel botoes = new JPanel();
        botoes.add(salvarBtn);
        botoes.add(voltarBtn);

        salvarBtn.addActionListener(e -> salvar());
        voltarBtn.addActionListener(e -> { new MenuFinanceiro(tipoUsuario).setVisible(true); dispose(); });

        add(botoes, BorderLayout.SOUTH);
    }


    private void salvar() {
        try {

            // Lê campos
            String nome = nomeF.getText().trim();
            String email = emailF.getText().trim();
            String endereco = enderecoF.getText().trim();
            String telefone = telefoneF.getText().trim();
            String cpf = cpfF.getText().trim();
            double salario = Double.parseDouble(salarioF.getText().trim());
            String cargo = (String) cargoBox.getSelectedItem();
            String departamento = (String) departamentoBox.getSelectedItem();
            String tipo = (String) tipoBox.getSelectedItem();
            boolean ativo = "Ativo".equals(statusBox.getSelectedItem());

            if (nome.isEmpty() || cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios.");
                return;
            }

            // Cria objeto Funcionario (AJUSTE AQUI SE SEU CONSTRUTOR TIVER MAIS CAMPOS)
            Funcionario f = new Funcionario(nome, email, endereco, telefone, cpf,
                                            cargo, departamento, salario, tipo, ativo);

            RepositorioFuncionario.adicionar(f);

            JOptionPane.showMessageDialog(this, "Funcionário salvo com sucesso.");
            new MenuFinanceiro(tipoUsuario).setVisible(true);
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Salário inválido.");
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaCadastroFuncionario("Administrador").setVisible(true));
    }
}

package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.dominio.*;
import com.tp1.GestaoRH.Misc.Helper; 
import com.tp1.GestaoRH.Candidatura.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class TelaCadastroFuncionario extends JFrame {
    private String tipoUsuario;

    private JTextField nomeF, emailF, enderecoF, telefoneF, cpfF, salarioF;
    private JComboBox<String> cargoBox, tipoBox, statusBox, departamentoBox;
    private JButton salvarBtn, voltarBtn, buscarBtn;


    private Contratacao contratacaoAprovada = null;

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


        
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel dadosPessoais = new JPanel(new GridBagLayout());
        dadosPessoais.setBorder(BorderFactory.createTitledBorder("Dados Pessoais (Busca)"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Linha 0: CPF e Botão Buscar
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.1;
        dadosPessoais.add(new JLabel("CPF:"), gbc);

        cpfF = new JTextField();
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 0.8;
        dadosPessoais.add(cpfF, gbc);

        buscarBtn = new JButton("Buscar");
        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0.1;
        dadosPessoais.add(buscarBtn, gbc);

        // Linha 1: Nome
        gbc.gridx = 0; gbc.gridy = 1;
        dadosPessoais.add(new JLabel("Nome:"), gbc);
        nomeF = new JTextField();
        nomeF.setEditable(false); // Bloqueado
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2;
        dadosPessoais.add(nomeF, gbc);

        // Linha 2: Email
        gbc.gridx = 0; gbc.gridy = 2;
        dadosPessoais.add(new JLabel("Email:"), gbc);
        emailF = new JTextField();
        emailF.setEditable(false); // Bloqueado
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2;
        dadosPessoais.add(emailF, gbc);

        // Linha 3: Endereço
        gbc.gridx = 0; gbc.gridy = 3;
        dadosPessoais.add(new JLabel("Endereço:"), gbc);
        enderecoF = new JTextField();
        enderecoF.setEditable(false); // Bloqueado
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 2;
        dadosPessoais.add(enderecoF, gbc);

        // Linha 4: Telefone
        gbc.gridx = 0; gbc.gridy = 4;
        dadosPessoais.add(new JLabel("Telefone:"), gbc);
        telefoneF = new JTextField();
        telefoneF.setEditable(false); // Bloqueado
        gbc.gridx = 1; gbc.gridy = 4; gbc.gridwidth = 2;
        dadosPessoais.add(telefoneF, gbc);


        JPanel dadosProfissionais = new JPanel(new GridLayout(5, 2, 10, 10));
        dadosProfissionais.setBorder(BorderFactory.createTitledBorder("Dados Profissionais"));

        salarioF = new JTextField();
        salarioF.setEditable(false); // Bloqueado
        
        cargoBox = new JComboBox<>(new String[]{"Administrador", "GestorRH", "RecrutadorRH", "FuncionarioGeral"});
        departamentoBox = new JComboBox<>(new String[]{"RH", "Financeiro", "TI", "Administração"});
        
        tipoBox = new JComboBox<>(new String[]{"CLT", "Estágio", "PJ"});
        tipoBox.setEnabled(false); // Bloqueado
        
        statusBox = new JComboBox<>(new String[]{"Ativo", "Inativo"});
        statusBox.setSelectedItem("Ativo"); // Padrão

        dadosProfissionais.add(new JLabel("Salário Base:")); dadosProfissionais.add(salarioF);
        dadosProfissionais.add(new JLabel("Tipo Contratação:")); dadosProfissionais.add(tipoBox);
        dadosProfissionais.add(new JLabel("Cargo (Definir):")); dadosProfissionais.add(cargoBox);
        dadosProfissionais.add(new JLabel("Departamento (Definir):")); dadosProfissionais.add(departamentoBox);
        dadosProfissionais.add(new JLabel("Status (Definir):")); dadosProfissionais.add(statusBox);


        painel.add(dadosPessoais);
        painel.add(Box.createVerticalStrut(10)); // espaçamento
        painel.add(dadosProfissionais);

        add(painel, BorderLayout.CENTER);


        salvarBtn = new JButton("Salvar");
        voltarBtn = new JButton("Voltar");

        JPanel botoes = new JPanel();
        botoes.add(salvarBtn);
        botoes.add(voltarBtn);

        buscarBtn.addActionListener(this::buscarCandidatoAprovado);
        salvarBtn.addActionListener(e -> salvar());
        voltarBtn.addActionListener(e -> { new MenuFinanceiro(tipoUsuario).setVisible(true); dispose(); });

        add(botoes, BorderLayout.SOUTH);
    }

 
    private void buscarCandidatoAprovado(ActionEvent e) {
        String cpfBusca = cpfF.getText().trim();
        if (cpfBusca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, insira um CPF para buscar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        
        if (RepositorioFuncionario.buscarPorCpf(cpfBusca) != null) {
            JOptionPane.showMessageDialog(this, "Este CPF já pertence a um funcionário cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            limparCampos(false); // Limpa campos mas mantém o CPF
            return;
        }

      
        ArrayList<Contratacao> contracoes = Helper.getInstance().getContratacoes();
        if (contracoes == null) {
            JOptionPane.showMessageDialog(this, "Nenhuma contratação encontrada no sistema.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Contratacao encontrada = null;
        for (Contratacao c : contracoes) {
            if (c.getCandidaturaAprovada().getCandidato().getCpf().equals(cpfBusca)) {
                if ("AUTORIZADO".equals(c.getStatusAutorizacao())) {
                    encontrada = c; 
                    break;
                } else {
                    JOptionPane.showMessageDialog(this, "Este CPF foi encontrado, mas a contratação ainda não foi AUTORIZADA pelo Gestor.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    limparCampos(false);
                    return;
                }
            }
        }


        if (encontrada != null) {
            this.contratacaoAprovada = encontrada; 
            preencherCampos(encontrada);
        } else {
            JOptionPane.showMessageDialog(this, "Nenhuma contratação AUTORIZADA foi encontrada para este CPF.", "Não Encontrado", JOptionPane.INFORMATION_MESSAGE);
            limparCampos(false);
        }
    }

    private void preencherCampos(Contratacao c) {
        Candidato candidato = c.getCandidaturaAprovada().getCandidato();
        Vaga vaga = c.getCandidaturaAprovada().getVaga();


        nomeF.setText(candidato.getNome());
        emailF.setText(candidato.getEmail());
        enderecoF.setText(candidato.getEndereço());
        telefoneF.setText(candidato.getTelefone());


        salarioF.setText(String.valueOf(vaga.getSalarioBase())); // Puxa o salário da vaga
        tipoBox.setSelectedItem(c.getRegime());

        cpfF.setEditable(false);
        buscarBtn.setEnabled(false);
        
        JOptionPane.showMessageDialog(this, "Candidato '" + candidato.getNome() + "' encontrado e pré-aprovado.\nPor favor, defina o Cargo, Departamento e Status para finalizar o cadastro.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

  
    private void limparCampos(boolean limparTudo) {
        if (limparTudo) {
            cpfF.setText("");
        }
        nomeF.setText("");
        emailF.setText("");
        enderecoF.setText("");
        telefoneF.setText("");
        salarioF.setText("");
        tipoBox.setSelectedIndex(0);
        cargoBox.setSelectedIndex(0);
        departamentoBox.setSelectedIndex(0);
        statusBox.setSelectedItem("Ativo");

      
        this.contratacaoAprovada = null;
        cpfF.setEditable(true);
        buscarBtn.setEnabled(true);
    }


    private void salvar() {
       
        if (this.contratacaoAprovada == null) {
            JOptionPane.showMessageDialog(this, "Ação inválida. Você deve primeiro 'Buscar' e validar um CPF de um candidato aprovado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            
            String nome = nomeF.getText().trim();
            String email = emailF.getText().trim();
            String endereco = enderecoF.getText().trim();
            String telefone = telefoneF.getText().trim();
            String cpf = cpfF.getText().trim();
            double salario = Double.parseDouble(salarioF.getText().trim());
            String tipo = (String) tipoBox.getSelectedItem();

            
            String cargo = (String) cargoBox.getSelectedItem();
            String departamento = (String) departamentoBox.getSelectedItem();
            boolean ativo = "Ativo".equals(statusBox.getSelectedItem());

            
            if (nome.isEmpty() || cpf.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios. (Erro inesperado, pois deveriam ser auto-preenchidos)");
                return;
            }

            // Cria o objeto Funcionario
            Funcionario f = new Funcionario(nome, email, endereco, telefone, cpf,
                    cargo, departamento, salario, tipo, ativo);


            RepositorioFuncionario.adicionar(f);

            
            JOptionPane.showMessageDialog(this, "Funcionário salvo com sucesso.");
            new MenuFinanceiro(tipoUsuario).setVisible(true);
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Salário inválido (Erro inesperado).");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro ao salvar: " + ex.getMessage());
        }
    }


    public static void main(String[] args) {
       
        
        SwingUtilities.invokeLater(() -> new TelaCadastroFuncionario("Administrador").setVisible(true));
    }
}

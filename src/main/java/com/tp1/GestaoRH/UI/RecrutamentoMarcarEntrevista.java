package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.Candidatura.Candidatura;
import com.tp1.GestaoRH.Candidatura.Candidato;
import com.tp1.GestaoRH.dominio.Entrevista; 
import com.tp1.GestaoRH.Misc.Helper;
import com.tp1.GestaoRH.Misc.Constantes; 
import com.tp1.GestaoRH.dominio.Vaga; 
import com.tp1.GestaoRH.dominio.RepositorioUsuario; 

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate; 
import java.time.format.DateTimeFormatter; 
import java.time.format.DateTimeParseException; 
import java.util.ArrayList;

public class RecrutamentoMarcarEntrevista extends JFrame {

    private JTextField txtCpfCandidato;
    private JButton btnBuscarCandidato;
    
    private JTextField txtNomeCandidato; 
    
    private JTextField txtVagaAssociada;
    private JFormattedTextField txtData;
    private JButton btnSalvar;
    private JTextField txtAvaliador; 

    private Candidatura candidaturaEncontrada;

    public RecrutamentoMarcarEntrevista() {
        setTitle("Marcar Entrevista - Recrutamento");
        setSize(450, 400); // Aumentei a altura para o novo campo
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        
        // Desabilita campos até a busca
        btnSalvar.setEnabled(false);
        txtData.setEnabled(false);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        int linha = 0;

        // Linha 0: CPF
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("CPF Candidato:"), gbc);
        
        JPanel buscaPanel = new JPanel(new BorderLayout(5, 0));
        txtCpfCandidato = new JTextField(15);
        btnBuscarCandidato = new JButton("Buscar");
        buscaPanel.add(txtCpfCandidato, BorderLayout.CENTER);
        buscaPanel.add(btnBuscarCandidato, BorderLayout.EAST);
        
        gbc.gridx = 1;
        panel.add(buscaPanel, gbc);

        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Candidato:"), gbc);
        gbc.gridx = 1;
        txtNomeCandidato = new JTextField(20);
        txtNomeCandidato.setEditable(false);
        txtNomeCandidato.setFont(txtNomeCandidato.getFont().deriveFont(Font.ITALIC | Font.BOLD));
        txtNomeCandidato.setBackground(Color.LIGHT_GRAY);
        panel.add(txtNomeCandidato, gbc);

        // Linha 2: Vaga
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Vaga Associada:"), gbc);
        gbc.gridx = 1;
        txtVagaAssociada = new JTextField(20);
        txtVagaAssociada.setEditable(false);
        txtVagaAssociada.setFont(txtVagaAssociada.getFont().deriveFont(Font.ITALIC | Font.BOLD));
        txtVagaAssociada.setBackground(Color.LIGHT_GRAY);
        panel.add(txtVagaAssociada, gbc);

        // Linha 3: Data
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

        // Linha 4: Avaliador
        linha++;
        gbc.gridx = 0; gbc.gridy = linha;
        panel.add(new JLabel("Avaliador:"), gbc);
        gbc.gridx = 1;
        txtAvaliador = new JTextField(20); 
        
        try {
            String nomeRecrutadorLogado = RepositorioUsuario.usuarioLogado.getLogin();
            txtAvaliador.setText(nomeRecrutadorLogado);
            txtAvaliador.setEditable(false);
            txtAvaliador.setFont(txtAvaliador.getFont().deriveFont(Font.ITALIC | Font.BOLD));
            txtAvaliador.setBackground(Color.LIGHT_GRAY);
        } catch (Exception e) {
            txtAvaliador.setText("ERRO: Usuário não logado");
            txtAvaliador.setEditable(false);
            txtAvaliador.setBackground(Color.PINK);
        }
        
        panel.add(txtAvaliador, gbc);

        btnBuscarCandidato.addActionListener(e -> buscarCandidatoPorCpf());

        // Linha 5: Botão Salvar
        linha++;
        gbc.gridx = 0; gbc.gridy = linha; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE; 
        btnSalvar = new JButton("Salvar Agendamento");
        
        btnSalvar.addActionListener(e -> salvarAgendamento()); 
        panel.add(btnSalvar, gbc);

        getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints center = new GridBagConstraints();
        center.gridx = 0;
        center.gridy = 0;
        center.anchor = GridBagConstraints.NORTH;
        center.insets = new Insets(10, 10, 10, 10);
        getContentPane().add(panel, center);
    }
    
    private void buscarCandidatoPorCpf() {
        String cpf = txtCpfCandidato.getText().trim();
        if (cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite um CPF.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ArrayList<Candidatura> lista = Helper.getInstance().getCandidatura();
        if (lista == null) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar lista de candidaturas.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.candidaturaEncontrada = null;
        
        for (Candidatura c : lista) {
            try {
                if (c.getCandidato().getCpf().equals(cpf)) {
                    if ("EM_ANALISE".equalsIgnoreCase(c.getStatus().trim())) {
                        this.candidaturaEncontrada = c;
                        break; 
                    }
                }
            } catch (Exception e) {
                // Ignora candidatura inválida
            }
        }

        if (this.candidaturaEncontrada != null) {

            txtNomeCandidato.setText(this.candidaturaEncontrada.getCandidato().getNome());
            txtVagaAssociada.setText(this.candidaturaEncontrada.getVaga().getCargo());
            
            JOptionPane.showMessageDialog(this, "Candidato encontrado.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            btnSalvar.setEnabled(true);
            txtData.setEnabled(true);
        } else {
            // Limpa os campos
            txtNomeCandidato.setText("");
            txtVagaAssociada.setText("");
            JOptionPane.showMessageDialog(this, "Nenhum candidato 'EM ANÁLISE' encontrado com este CPF.", "Não Encontrado", JOptionPane.ERROR_MESSAGE);
            btnSalvar.setEnabled(false);
            txtData.setEnabled(false);
            this.candidaturaEncontrada = null;
        }
    }
    
    private void salvarAgendamento() {
        
        if (this.candidaturaEncontrada == null) {
            JOptionPane.showMessageDialog(this, "Nenhum candidato selecionado. Busque um CPF válido primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
            
        try {
            String nomeAvaliador = txtAvaliador.getText().trim();
            
            if (nomeAvaliador.isEmpty() || nomeAvaliador.equals("ERRO: Usuário não logado")) {
                JOptionPane.showMessageDialog(this, "Não é possível agendar. Usuário (Avaliador) não está logado.", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String dataStr = txtData.getText();
            if (dataStr.trim().equals("/  /")) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha a Data.", "Campos Obrigatórios", JOptionPane.ERROR_MESSAGE);
                return;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataParseada = LocalDate.parse(dataStr, formatter);
            
            double notaParseada = 0.0; 
            Candidatura candidaturaAlvo = this.candidaturaEncontrada;
            
            Entrevista novaEntrevista = new Entrevista(
                candidaturaAlvo.getVaga(), 
                candidaturaAlvo.getCandidato(), 
                dataParseada, 
                nomeAvaliador, 
                notaParseada 
            );

            ArrayList<Entrevista> listaEntrevistas = Helper.getInstance().getEntrevistas();
            listaEntrevistas.add(novaEntrevista);
            Helper.getInstance().saveObject(listaEntrevistas, Constantes.PATH_ENTREVISTAS);
            
            JOptionPane.showMessageDialog(this, 
                "Entrevista para " + candidaturaAlvo.getCandidato().getNome() + " agendada com sucesso!", 
                "Agendamento Efetuado", 
                JOptionPane.INFORMATION_MESSAGE);
            
            // Reseta o formulário
            txtCpfCandidato.setText("");
            txtNomeCandidato.setText(""); // Limpa o novo campo
            txtVagaAssociada.setText("");
            txtData.setText("");
            btnSalvar.setEnabled(false);
            txtData.setEnabled(false);
            this.candidaturaEncontrada = null;
                
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Formato de Data inválido. Use dd/MM/yyyy.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ocorreu um erro inesperado ao salvar: " + e.getMessage(), "Erro de Sistema", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecrutamentoMarcarEntrevista().setVisible(true));
    }
}
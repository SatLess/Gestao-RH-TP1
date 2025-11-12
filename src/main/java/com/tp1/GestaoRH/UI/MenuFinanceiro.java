package com.tp1.GestaoRH.UI;
import com.tp1.GestaoRH.*;
import javax.swing.*;
import java.awt.*;

public class MenuFinanceiro extends JFrame {
    
    private final String tipoUsuario;
    
    private JButton cadastro, regras, gerar, relatorio, beneficios, historico, contracheques, voltar;
    
    public MenuFinanceiro(String tipoUsuario) {
        super("Menu Financeiro - " + tipoUsuario);
        this.tipoUsuario = tipoUsuario;
        init();
    }

    private void init() {
        setSize(750,420);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("Módulo Financeiro - Usuário: " + tipoUsuario, SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(16f));

        JPanel painel = new JPanel(new GridLayout(4,2,12,12));
        painel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        cadastro = new JButton("Cadastro de Funcionário");
        regras = new JButton("Configurar Regras Salariais");
        gerar = new JButton("Gerar Folha de Pagamento");
        relatorio = new JButton("Relatório Financeiro");
        beneficios = new JButton("Benefícios");
        historico = new JButton("Histórico Financeiro");
        contracheques = new JButton("Contracheques");
        voltar = new JButton("Voltar ao Login");

        cadastro.addActionListener(e -> new TelaCadastroFuncionario(tipoUsuario).setVisible(true));
        regras.addActionListener(e -> new TelaRegraSalarial(tipoUsuario).setVisible(true));
        gerar.addActionListener(e -> new TelaGerarFolhaPagamento(tipoUsuario).setVisible(true));
        relatorio.addActionListener(e -> new TelaRelatorioFinanceiro(tipoUsuario).setVisible(true));
        beneficios.addActionListener(e -> new TelaBeneficios(tipoUsuario).setVisible(true));
        historico.addActionListener(e -> new TelaHistoricoFinanceiro(tipoUsuario).setVisible(true));
        contracheques.addActionListener(e -> new TelaContracheques(tipoUsuario).setVisible(true));
        voltar.addActionListener(e -> { new telaInicial().setVisible(true); dispose(); });

        painel.add(cadastro); painel.add(regras); painel.add(gerar); painel.add(relatorio);
        painel.add(beneficios); painel.add(historico); painel.add(contracheques); painel.add(voltar);

        add(titulo, BorderLayout.NORTH);
        add(painel, BorderLayout.CENTER);

        aplicarPermissoes();
    }

    private void aplicarPermissoes() {
        boolean isAdmin = tipoUsuario.equalsIgnoreCase("Administrador");
        boolean isGestor = tipoUsuario.equalsIgnoreCase("GestorRH");
        boolean isFuncionario = tipoUsuario.equalsIgnoreCase("FuncionarioGeral");
        boolean isRecrutador = tipoUsuario.equalsIgnoreCase("Recrutador");
        
        if (isAdmin) {
            // tudo liberado
            setAllEnabled(true);
        } 
        
        else if (isGestor) {
            // gestor pode quase tudo, mas não pode mexer em regras salariais
            setAllEnabled(true);
            regras.setEnabled(false);
        } 
        
        else if (isFuncionario) {
            // funcionário só pode ver benefícios, histórico e contracheques
            cadastro.setEnabled(false);
            regras.setEnabled(false);
            gerar.setEnabled(false);
            relatorio.setEnabled(false);
            beneficios.setEnabled(true);
            historico.setEnabled(true);
            contracheques.setEnabled(true);
        } 
        
        else if (isRecrutador) {
            // recrutador não tem acesso ao financeiro
            setAllEnabled(false);
            JOptionPane.showMessageDialog(this,
                "Recrutadores não têm acesso ao módulo financeiro.",
                "Acesso negado", JOptionPane.WARNING_MESSAGE);
        } 
        
        else {
            // qualquer outro tipo bloqueado
            setAllEnabled(false);
            JOptionPane.showMessageDialog(this,
                "Cargo sem permissões definidas no módulo financeiro.",
                "Acesso negado", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método utilitário para habilitar/desabilitar todos os botões
    
    private void setAllEnabled(boolean enabled) {
        cadastro.setEnabled(enabled);
        regras.setEnabled(enabled);
        gerar.setEnabled(enabled);
        relatorio.setEnabled(enabled);
        beneficios.setEnabled(enabled);
        historico.setEnabled(enabled);
        contracheques.setEnabled(enabled);
    }

        // enable/disable buttons based on role
        // find components by iterating or hold references - kept simple by checking when opening screens

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuFinanceiro("Administrador").setVisible(true));
    }
}

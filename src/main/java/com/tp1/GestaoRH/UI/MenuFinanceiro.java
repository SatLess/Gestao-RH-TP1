package com.tp1.GestaoRH.UI;
import com.tp1.GestaoRH.*;
import javax.swing.*;
import java.awt.*;

public class MenuFinanceiro extends JFrame {
    
    private final String tipoUsuario;
    
    private JButton consulta, regras, gerar, relatorio, beneficios, contracheques, voltar;
    
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

        consulta = new JButton("Consulta de Funcionário");
        regras = new JButton("Configurar Regras Salariais");
        gerar = new JButton("Folha de Pagamento");
        relatorio = new JButton("Relatório Financeiro");
        beneficios = new JButton("Benefícios");
        contracheques = new JButton("Contracheques");
        voltar = new JButton("Voltar ao Login");

        consulta.addActionListener(e -> {new TelaConsultaFuncionarios(tipoUsuario).setVisible(true); dispose();});
        regras.addActionListener(e -> {new TelaRegraSalarial(tipoUsuario).setVisible(true); dispose();});
        gerar.addActionListener(e -> {new TelaGerarFolhaPagamento(tipoUsuario).setVisible(true); dispose();});
        relatorio.addActionListener(e -> {new TelaRelatorioFinanceiro(tipoUsuario).setVisible(true); dispose();});
        beneficios.addActionListener(e -> {new TelaBeneficios(tipoUsuario).setVisible(true); dispose();});
        contracheques.addActionListener(e -> {new TelaContracheques(tipoUsuario).setVisible(true); dispose();});
        voltar.addActionListener(e -> { new telaInicial().setVisible(true); dispose(); });

        painel.add(consulta); painel.add(regras); painel.add(gerar); painel.add(relatorio);
        painel.add(beneficios); painel.add(contracheques); painel.add(voltar);

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
            beneficios.setEnabled(false);
        } 
        
        else if (isGestor) {
            // gestor pode quase tudo, mas não pode mexer em regras salariais
            setAllEnabled(true);
            regras.setEnabled(false);
            beneficios.setEnabled(false);
        } 
            
        else if (isFuncionario) {
            // funcionário só pode ver benefícios, histórico e contracheques
            consulta.setEnabled(false);
            regras.setEnabled(false);
            gerar.setEnabled(false);
            relatorio.setEnabled(false);
            beneficios.setEnabled(true);
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
        consulta.setEnabled(enabled);
        regras.setEnabled(enabled);
        gerar.setEnabled(enabled);
        relatorio.setEnabled(enabled);
        beneficios.setEnabled(enabled);
        contracheques.setEnabled(enabled);
    }

        // enable/disable buttons based on role
        // find components by iterating or hold references - kept simple by checking when opening screens

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuFinanceiro("Administrador").setVisible(true));
    }
}

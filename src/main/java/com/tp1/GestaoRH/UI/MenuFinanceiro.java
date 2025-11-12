package com.tp1.GestaoRH.UI;
import com.tp1.GestaoRH.*;
import javax.swing.*;
import java.awt.*;

public class MenuFinanceiro extends JFrame {
    private String tipoUsuario;
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

        JButton cadastro = new JButton("Cadastro de Funcionário");
        JButton regras = new JButton("Configurar Regras Salariais");
        JButton gerar = new JButton("Gerar Folha de Pagamento");
        JButton relatorio = new JButton("Relatório Financeiro");
        JButton beneficios = new JButton("Benefícios");
        JButton historico = new JButton("Histórico Financeiro");
        JButton contracheques = new JButton("Contracheques");
        JButton voltar = new JButton("Voltar ao Login");

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
        boolean isAdmin = "Administrador".equalsIgnoreCase(tipoUsuario);
        boolean isGestor = "GestorRH".equalsIgnoreCase(tipoUsuario);
        // enable/disable buttons based on role
        // find components by iterating or hold references - kept simple by checking when opening screens
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MenuFinanceiro("Administrador").setVisible(true));
    }
}

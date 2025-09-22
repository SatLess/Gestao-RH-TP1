package UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RecrutamentoConsultarContratacoes extends JFrame {
    private JTable table;
    private DefaultTableModel model;
    private JButton btnAtualizar, btnVoltar;

    public RecrutamentoConsultarContratacoes() {
        setTitle("Consultar Contratações - Recrutamento");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        // Definição das colunas
        String[] colunas = {"ID Candidato", "Nome", "Vaga", "Regime", "Data Contratação", "Status"};
        model = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // somente leitura
            }
        };

        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);

        // Botões
        btnAtualizar = new JButton("Atualizar");
        btnVoltar = new JButton("Voltar");

        btnAtualizar.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Ainda sem dados (apenas protótipo)."));
        btnVoltar.addActionListener(e -> dispose());

        // Painel inferior
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(btnAtualizar);
        southPanel.add(btnVoltar);

        // Layout
        getContentPane().setLayout(new BorderLayout(8, 8));
        getContentPane().add(new JLabel("Contratações Registradas:", JLabel.LEFT), BorderLayout.NORTH);
        getContentPane().add(scroll, BorderLayout.CENTER);
        getContentPane().add(southPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecrutamentoConsultarContratacoes().setVisible(true));
    }
}

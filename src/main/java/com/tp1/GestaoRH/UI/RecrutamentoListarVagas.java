package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.dominio.RecrutamentoPersistencia;
import com.tp1.GestaoRH.dominio.Vaga;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RecrutamentoListarVagas extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JButton btnAtualizar;
    private JButton btnVoltar;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RecrutamentoListarVagas() {
        setTitle("Listar Vagas - Recrutamento");
        setSize(720, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initComponents();
        SwingUtilities.invokeLater(this::atualizarTabela);
    }

    private void initComponents() {
        String[] colNames = {"ID", "Cargo", "Salário", "Departamento", "Status", "Data Abertura"};
        model = new DefaultTableModel(colNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // tabela somente leitura no protótipo
            }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(table);

        btnAtualizar = new JButton("Atualizar");
        btnVoltar = new JButton("Voltar");

        btnAtualizar.addActionListener(e -> atualizarTabela());
        btnVoltar.addActionListener(e -> dispose());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        topPanel.add(new JLabel("Vagas cadastradas:"), BorderLayout.WEST);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        southPanel.add(btnAtualizar);
        southPanel.add(btnVoltar);

        getContentPane().setLayout(new BorderLayout(8, 8));
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(scroll, BorderLayout.CENTER);
        getContentPane().add(southPanel, BorderLayout.SOUTH);
    }

    /**
     * Popula a tabela com a lista de vagas.
     * @param vagas lista de dominio.Vaga (pode ser obtida do repositório)
     */
    public void setVagas(List<Vaga> vagas) {
        clearTable();
        if (vagas == null) return;
        for (Vaga v : vagas) {
            String data = "";
            try {
                if (v.getDataAbertura() != null) data = v.getDataAbertura().format(dtf);
            } catch (Exception ignored) { }
            model.addRow(new Object[]{
                    v.getId(),
                    v.getCargo(),
                    v.getSalarioBase(),
                    v.getDepartamento(),
                    v.getStatus(),
                    data
            });
        }
    }

    /** Adiciona uma única vaga (útil ao salvar uma nova vaga) */
    public void addVaga(Vaga v) {
        if (v == null) return;
        String data = "";
        try {
            if (v.getDataAbertura() != null) data = v.getDataAbertura().format(dtf);
        } catch (Exception ignored) { }
        model.addRow(new Object[]{
                v.getId(),
                v.getCargo(),
                v.getSalarioBase(),
                v.getDepartamento(),
                v.getStatus(),
                data
        });
    }

    /** Remove todas as linhas da tabela */
    public void clearTable() {
        model.setRowCount(0);
    }

    /** Placeholder de atualização — na etapa 1 só mostra aviso.
     *  No futuro, aqui você chamará RepositorioVagas.getAll() e passará para setVagas(...)
     */
    private void atualizarTabela() {
        try {

        // 1️⃣ Carrega as vagas do arquivo
        List<Vaga> vagas = RecrutamentoPersistencia.carregarVagas();

        
        setVagas(vagas);

        
        JOptionPane.showMessageDialog(this,
                "✅ Lista de vagas atualizada com sucesso!\nTotal de vagas: " + vagas.size(),
                "Atualizar", JOptionPane.INFORMATION_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this,
                "❌ Erro ao carregar vagas: " + e.getMessage(),
                "Erro", JOptionPane.ERROR_MESSAGE);
    }
    }

    // Main para testes isolados
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RecrutamentoListarVagas janela = new RecrutamentoListarVagas();
            janela.setVisible(true);
        });
    }
}

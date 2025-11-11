package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.Misc.Constantes;
import com.tp1.GestaoRH.dominio.RecrutamentoPersistencia;
import com.tp1.GestaoRH.dominio.Vaga;

import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RecrutamentoListarVagas extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JButton btnAtualizar;
    private JButton btnVoltar;
    private JButton btnEditar;
    private JButton btnExcluir;

    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private final NumberFormat formatadorMoeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

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
                return false; // tabela somente leitura
            }
        };

        table = new JTable(model);
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scroll = new JScrollPane(table);

        btnAtualizar = new JButton("Atualizar");
        btnVoltar = new JButton("Voltar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");

        // Ações dos botões
        btnAtualizar.addActionListener(e -> atualizarTabela());
        btnVoltar.addActionListener(e -> dispose());
        btnEditar.addActionListener(e -> editarVaga());
        btnExcluir.addActionListener(e -> excluirVagas());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        topPanel.add(new JLabel("Vagas cadastradas:"), BorderLayout.WEST);

        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        southPanel.add(btnEditar);
        southPanel.add(btnExcluir);
        southPanel.add(btnAtualizar);
        southPanel.add(btnVoltar);

        getContentPane().setLayout(new BorderLayout(8, 8));
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(scroll, BorderLayout.CENTER);
        getContentPane().add(southPanel, BorderLayout.SOUTH);
    }

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
                    formatadorMoeda.format(v.getSalarioBase()),
                    v.getDepartamento(),
                    v.getStatus(),
                    data
            });
        }
    }

    public void addVaga(Vaga v) {
        if (v == null) return;
        String data = "";
        try {
            if (v.getDataAbertura() != null) data = v.getDataAbertura().format(dtf);
        } catch (Exception ignored) { }
        model.addRow(new Object[]{
                v.getId(),
                v.getCargo(),
                formatadorMoeda.format(v.getSalarioBase()),
                v.getDepartamento(),
                v.getStatus(),
                data
        });
    }

    public void clearTable() {
        model.setRowCount(0);
    }

    private void atualizarTabela() {
        try {
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

    private void editarVaga() {
        int[] selecionadas = table.getSelectedRows();

        if (selecionadas.length != 1) {
            JOptionPane.showMessageDialog(this,
                    "Selecione apenas uma vaga para editar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int linha = selecionadas[0];
        int id = (int) model.getValueAt(linha, 0);
        String cargo = model.getValueAt(linha, 1).toString();
        String salario = model.getValueAt(linha, 2).toString();
        String depto = model.getValueAt(linha, 3).toString();
        Constantes.STATUS statusAtual = (Constantes.STATUS) model.getValueAt(linha, 4);

        // Painel de edição embutido (JDialog)
        JDialog dialog = new JDialog(this, "Editar Vaga", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(6, 2, 10, 10));

        JTextField txtCargo = new JTextField(cargo);
        JTextField txtSalario = new JTextField(salario);


        txtSalario.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String texto = txtSalario.getText().replaceAll("[^0-9]", "");
                
                if (texto.isEmpty()) {
                    texto = "0";
                }

                double valor = Double.parseDouble(texto) / 100.0;
                String formatado = formatadorMoeda.format(valor);
                
                txtSalario.setText(formatado);
                txtSalario.setCaretPosition(formatado.length());
            }
        });




        JTextField txtDepto = new JTextField(depto);
        JComboBox<Constantes.STATUS> cmbStatus = new JComboBox<>(Constantes.STATUS.values());
        cmbStatus.setSelectedItem(statusAtual);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        dialog.add(new JLabel("ID:"));
        dialog.add(new JLabel(String.valueOf(id)));
        dialog.add(new JLabel("Cargo:"));
        dialog.add(txtCargo);
        dialog.add(new JLabel("Salário:"));
        dialog.add(txtSalario);
        dialog.add(new JLabel("Departamento:"));
        dialog.add(txtDepto);
        dialog.add(new JLabel("Status:"));
        dialog.add(cmbStatus);
        dialog.add(btnSalvar);
        dialog.add(btnCancelar);

        btnSalvar.addActionListener(e -> {
            try {
                String textoLimpo = txtSalario.getText().replaceAll("[^0-9]", "");
                
                if (textoLimpo.isEmpty()) {
                    textoLimpo = "0";
                }
                
                double novoSalario = Double.parseDouble(textoLimpo) / 100.0;

                List<Vaga> vagas = RecrutamentoPersistencia.carregarVagas();
                for (Vaga v : vagas) {
                    if (v.getId() == id) {
                        v.setCargo(txtCargo.getText());
                        v.setSalarioBase(novoSalario);
                        v.setDepartamento(txtDepto.getText());

                        Constantes.STATUS novoStatus = (Constantes.STATUS) cmbStatus.getSelectedItem();
                    v.setStatus(novoStatus);
                        break;
                    }
                }
                RecrutamentoPersistencia.salvarVagas(vagas);
                
                atualizarTabela();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "✅ Vaga atualizada com sucesso!");
                
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, 
                    "Erro de formato no Salário. Verifique o valor.", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Erro ao salvar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });


        btnCancelar.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    private void excluirVagas() {
        int[] selecionadas = table.getSelectedRows();

        if (selecionadas.length == 0) {
            JOptionPane.showMessageDialog(this,
                    "Selecione uma ou mais vagas para excluir.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir as vagas selecionadas?",
                "Confirmar Exclusão",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            List<Vaga> vagas = RecrutamentoPersistencia.carregarVagas();
            List<Integer> idsParaExcluir = new ArrayList<>();

            for (int i : selecionadas) {
                idsParaExcluir.add((Integer) model.getValueAt(i, 0));
            }

            vagas.removeIf(v -> idsParaExcluir.contains(v.getId()));
            RecrutamentoPersistencia.salvarVagas(vagas);

            atualizarTabela();
            JOptionPane.showMessageDialog(this, "✅ Vagas excluídas com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "❌ Erro ao excluir: " + e.getMessage(),
                    "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main de teste
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RecrutamentoListarVagas janela = new RecrutamentoListarVagas();
            janela.setVisible(true);
        });
    }
}

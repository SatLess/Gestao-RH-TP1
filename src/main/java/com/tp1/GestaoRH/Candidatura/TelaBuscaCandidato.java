/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.tp1.GestaoRH.Candidatura;

import com.tp1.GestaoRH.Misc.Constantes;
import com.tp1.GestaoRH.Misc.Helper;
import com.tp1.GestaoRH.dominio.RepositorioUsuario;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author spiri
 */

 class OptionListener implements ListSelectionListener  {

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (button.getName() != null){
            button.setEnabled(tabelinha.getSelectedRow() != -1);
        }
        else
        button.setEnabled(RepositorioUsuario.usuarioLogado.getCargo().equals("Recrutador") && tabelinha.getSelectedRow() != -1);
    }
     
        private JButton button;
        private JTable tabelinha;
     
    public OptionListener(JButton button, JTable tabelinha) {
        this.button = button;
        this.tabelinha = tabelinha;
    }
}



public class TelaBuscaCandidato extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaBuscaCandidato.class.getName());
    private ArrayList<Candidatura> candidatos;
    

    /**
     * Creates new form Candidaturas
     */
    public TelaBuscaCandidato() {
        initComponents();
        setLocationRelativeTo(null);
        setUpTable();
        Status.addItem("");
        Status.addItem(Constantes.CANDIDATOSTATUS.PENDENTE.toString());
        Status.addItem(Constantes.CANDIDATOSTATUS.EM_ANALISE.toString());
        Status.addItem(Constantes.CANDIDATOSTATUS.APROVADO.toString());
        Status.addItem(Constantes.CANDIDATOSTATUS.REJEITADO.toString());
        Helper.getInstance().listarVagas(Vaga);
        tabelinha.getColumnModel().getSelectionModel().addListSelectionListener(new OptionListener(this.Salvar1, tabelinha));
        tabelinha.getColumnModel().getSelectionModel().addListSelectionListener(new OptionListener(this.Exluir, tabelinha));
        tabelinha.getColumnModel().getSelectionModel().addListSelectionListener(new OptionListener(this.AddDoc, tabelinha));
        tabelinha.getColumnModel().getSelectionModel().addListSelectionListener(new OptionListener(this.AbrirDoc, tabelinha));
    }
    

    
    private void setUpTable(){
        candidatos = Helper.getInstance().getCandidatura();
        DefaultTableModel model = new DefaultTableModel(){
                  @Override public boolean isCellEditable(int row, int col) {
                      if (RepositorioUsuario.usuarioLogado.getCargo().equals("GestorRH")){return false;}
        return this.getColumnName(col).equals("Cpf") == false && this.getColumnName(col).equals("Vaga") == false;  
      }
        };
        model.addColumn("Nome");
        model.addColumn("Cpf");
        model.addColumn("Endereço");
        model.addColumn("Email");
        model.addColumn("Pretensão salarial");
        model.addColumn("Vaga");
        model.addColumn("Disponibilidade de Horario");
        model.addColumn("Status da candidatura");
        model.addColumn("Telefone");
        for(Candidatura c: candidatos){
            String[] row = new String[model.getColumnCount()];
            row[0] = c.getCandidato().getNome();
            row[1] = c.getCandidato().getCpf();
            row[2] = c.getCandidato().getEndereço();
            row[3] = c.getCandidato().getEmail();
            row[4] = String.valueOf(c.getCandidato().getPretensaoSalarial());
            row[5]  = c.getVaga().getCargo();
            ArrayList<String> horarios = c.getCandidato().getHorarioDisponivel().get(c.getVaga());
            System.out.println(horarios);
            row[6] = new String(horarios.getFirst()+ " - " + horarios.getLast());
            row[7] = c.getStatus();  
            row[8] = c.getCandidato().getTelefone();

            model.addRow(row);
        }
        tabelinha.setModel(model);
        
    }

    private void salvarValores() throws InvalidParameterException{
               //Podia cache-ar apenas valores que foram alterados, mas isso serve.
        candidatos = Helper.getInstance().getCandidatura();
        if (candidatos == null || candidatos.size() == 0) {
            return;
        }
        TableModel table = tabelinha.getModel();
            for (int j = 0; j < table.getRowCount(); j++){
                candidatos.get(j).getCandidato().setNome(table.getValueAt(j,0 ).toString());
                candidatos.get(j).getCandidato().setEndereço(table.getValueAt(j,2 ).toString());
                candidatos.get(j).getCandidato().setEmail(table.getValueAt(j,3 ).toString());
                candidatos.get(j).getCandidato().setPretensaoSalarial(Float.valueOf(table.getValueAt(j,4).toString()));
                try {
                    candidatos.get(j).setStatus(table.getValueAt(j, 7).toString()); 
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Ação negada", JOptionPane.ERROR_MESSAGE);
                    throw new InvalidParameterException("");
                }
                candidatos.get(j).getCandidato().setTelefone(table.getValueAt(j,8).toString());
            }
        System.out.println(candidatos.get(0).getCandidato().getNome());
        Helper.getInstance().saveObject(candidatos, Constantes.PATHCANDIDATOS);
        setUpTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabelinha = new javax.swing.JTable();
        Pesquisar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Salvar1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        Exluir = new javax.swing.JButton();
        Nome = new javax.swing.JTextField();
        cpf = new javax.swing.JTextField();
        endereco = new javax.swing.JTextField();
        Vaga = new javax.swing.JComboBox<>();
        Status = new javax.swing.JComboBox<>();
        AbrirDoc = new javax.swing.JButton();
        AddDoc = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setViewportView(null);

        tabelinha.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nome", "Cpf", "Endereço", "Salário"
            }
        ));
        jScrollPane1.setViewportView(tabelinha);

        Pesquisar.setText("Pesquisar");
        Pesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PesquisarActionPerformed(evt);
            }
        });

        jLabel1.setText("Filtros");

        Salvar1.setText("Salvar");
        Salvar1.setEnabled(false);
        Salvar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Salvar1ActionPerformed(evt);
            }
        });

        jLabel2.setText("Nome");

        jLabel3.setText("Telefone");

        jLabel4.setText("CPF");

        jLabel5.setText("Vaga");

        jLabel6.setText("Status da Canidatura");

        Exluir.setText("Excluir Candidato");
        Exluir.setEnabled(false);
        Exluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExluirActionPerformed(evt);
            }
        });

        cpf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TelaBuscaCandidato.this.keyTyped(evt);
            }
        });

        Vaga.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        Vaga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VagaActionPerformed(evt);
            }
        });

        Status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StatusActionPerformed(evt);
            }
        });

        AbrirDoc.setText("Abrir Documentação");
        AbrirDoc.setEnabled(false);
        AbrirDoc.setName("AddDoc"); // NOI18N
        AbrirDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirDocActionPerformed(evt);
            }
        });

        AddDoc.setText("Adicionar Documentação");
        AddDoc.setEnabled(false);
        AddDoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddDocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Exluir, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(2, 2, 2)
                                .addComponent(Status, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Nome)
                                    .addComponent(cpf)
                                    .addComponent(endereco)
                                    .addComponent(Vaga, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(AbrirDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(AddDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(335, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(55, 55, 55)
                    .addComponent(Salvar1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1023, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel1)
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(Nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(endereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(Vaga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(Status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AbrirDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(AddDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Exluir, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(67, 67, 67))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(409, Short.MAX_VALUE)
                    .addComponent(Salvar1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(11, 11, 11)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PesquisarActionPerformed
   
   var filtro = new ArrayList<String>();
   filtro.add(Nome.getText());
   filtro.add(cpf.getText());
   filtro.add(endereco.getText());
   filtro.add(Vaga.getItemAt(Vaga.getSelectedIndex()));
   filtro.add(Status.getItemAt(Status.getSelectedIndex()));
   
   var sorter = new TableRowSorter<TableModel>(tabelinha.getModel());
   sorter.setRowFilter(new Sorter(filtro));
   tabelinha.setRowSorter(sorter); 
   
    }//GEN-LAST:event_PesquisarActionPerformed

    private void Salvar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Salvar1ActionPerformed
        salvarValores();
    }//GEN-LAST:event_Salvar1ActionPerformed

    private void ExluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExluirActionPerformed
               int row = tabelinha.getSelectedRow();
                if (tabelinha.getSelectedRow() == -1) {
                        JOptionPane.showMessageDialog(rootPane, "Escolha um candidato para exclusão", "Exclusão Negada", JOptionPane.ERROR_MESSAGE);
                        return;
               }
                candidatos = Helper.getInstance().getCandidatura();
                if (candidatos.get(tabelinha.getSelectedRow()).getStatus().equals("Em Analise") == false){
                    JOptionPane.showMessageDialog(rootPane, "Candidato não pode ser excluido, dado que seu Status não está pendente", "Exclusão Negada", JOptionPane.ERROR_MESSAGE);
                    return;
                }
               int excluir =  JOptionPane.showConfirmDialog(null,
             "Deseja mesmo remover o candidato " + tabelinha.getValueAt(tabelinha.getSelectedRow(),0 ) + "?\n Alterações feitas antes da exlusão serão salvas" , "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
             if (excluir == JOptionPane.YES_OPTION){
                try {
                    salvarValores();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, "Erro em salvar, exclusão interrompida", "Ação negada", JOptionPane.ERROR_MESSAGE);
                    return;
                }
               
                candidatos.remove(row);
                Helper.getInstance().saveObject(candidatos, Constantes.PATHCANDIDATOS);
                setUpTable();
                
             }
    }//GEN-LAST:event_ExluirActionPerformed

    private void keyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyTyped
               String num = "0123456789";
        if (num.indexOf(evt.getKeyChar()) == -1){
        evt.consume(); }
    }//GEN-LAST:event_keyTyped

    private void VagaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VagaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_VagaActionPerformed

    private void AbrirDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirDocActionPerformed
        
                       if (tabelinha.getSelectedRow() == -1) {
                        JOptionPane.showMessageDialog(rootPane, "Escolha um candidato para abrir seus documentos", "Pesquisa Negada", JOptionPane.ERROR_MESSAGE);
                        return;
               }
        
        var candidato = Helper.getInstance().getCandidatura().get(tabelinha.getSelectedRow());
        if (candidato.getCandidato().getDocumentacao().isEmpty()){
         JOptionPane.showMessageDialog(rootPane, "Candidato nao anexou nenhum arquivo.", "Pesquisa Negada", JOptionPane.ERROR_MESSAGE);
        }
        Desktop desktop = Desktop.getDesktop();
        for (File f : candidato.getCandidato().getDocumentacao()) {
            try {desktop.open(f);}
            catch (Exception e){JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Pesquisa Negada", JOptionPane.ERROR_MESSAGE);}
        }
         
    }//GEN-LAST:event_AbrirDocActionPerformed

    private void StatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_StatusActionPerformed

    private void AddDocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddDocActionPerformed
                        try {
            JFileChooser file = new JFileChooser();
            FileNameExtensionFilter F = new FileNameExtensionFilter(null, "pdf");
            file.setFileFilter(F);
            file.setCurrentDirectory(new File("."));
            int approve = file.showOpenDialog(null);
           
            
            if (approve == JFileChooser.APPROVE_OPTION){
                
                File f = new File(file.getSelectedFile().getAbsolutePath());
                candidatos.get(tabelinha.getSelectedRow()).getCandidato().getDocumentacao().add(f);
                Helper.getInstance().saveObject(candidatos, Constantes.PATHCANDIDATOS);
                System.out.println(f);
            }
            
            
            
            
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }//GEN-LAST:event_AddDocActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new TelaBuscaCandidato().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AbrirDoc;
    private javax.swing.JButton AddDoc;
    private javax.swing.JButton Exluir;
    private javax.swing.JTextField Nome;
    private javax.swing.JButton Pesquisar;
    private javax.swing.JButton Salvar1;
    private javax.swing.JComboBox<String> Status;
    private javax.swing.JComboBox<String> Vaga;
    private javax.swing.JTextField cpf;
    private javax.swing.JTextField endereco;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelinha;
    // End of variables declaration//GEN-END:variables
}

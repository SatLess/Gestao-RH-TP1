/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.tp1.GestaoRH.UI;

import com.tp1.GestaoRH.Candidatura.TelaBuscaCandidato;
import com.tp1.GestaoRH.Candidatura.TelaCandidatura;
import com.tp1.GestaoRH.Candidatura.TelaCadastroCandidato;
import com.tp1.GestaoRH.*;
import com.tp1.GestaoRH.UI.RecrutamentoCadastroVagaTela;
import com.tp1.GestaoRH.dominio.RepositorioUsuario;
import javax.swing.*;

/**
 *
 * @author luish
 */
public class MenuRecrutamento extends javax.swing.JFrame {

    public MenuRecrutamento() {
        initComponents();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        
        if (RepositorioUsuario.usuarioLogado != null) {
            Nome.setText(RepositorioUsuario.usuarioLogado.getLogin());
            Cargo.setText(RepositorioUsuario.usuarioLogado.getCargo());
            setUpMenuAvaliability(); // Chamado DEPOIS de carregar o usuário
        } else {
            Nome.setText("Usuário N/A");
            Cargo.setText("N/A");
            jTabbedPane1.setEnabled(false); // Trava tudo se não houver login
        }
    }

    public void setUpMenuAvaliability(){
        String cargo = RepositorioUsuario.usuarioLogado.getCargo();
        if (cargo == null) {
            jTabbedPane1.setEnabled(false);
            return;
        }
        
        // Encontra os índices das abas pelos JPanels
        int indexCandidatura = jTabbedPane1.indexOfComponent(Candidatura);
        int indexVagas = jTabbedPane1.indexOfComponent(Contratação); // O JPanel de Vagas chama 'Contratação'
        int indexRecrutamento = jTabbedPane1.indexOfComponent(Vagas); // O JPanel de Recrutamento chama 'Vagas'

        if (cargo.equals("GestorRH")) {
            
            // 1. Gestor NÃO mexe em Candidatura. Desabilita a aba inteira.
            if (indexCandidatura != -1) {
                jTabbedPane1.setEnabledAt(indexCandidatura, false);
            }

            // 2. Gestor VÊ a aba "Vagas" (Painel 'Contratação')
            
            // 3. Gestor VÊ a aba "Recrutamento", mas com botões limitados
            jButton3.setEnabled(false); // Marcar Entrevista
            jButton4.setEnabled(false); // Avaliar e Solicitar

            // (jButton5 - Consultar/Autorizar fica ATIVO)

            // 4. CORREÇÃO DA FALHA DE SEGURANÇA: Mudar o foco da aba
            if (indexVagas != -1) {
                jTabbedPane1.setSelectedIndex(indexVagas);
            }

        } else if (cargo.equals("Recrutador")) {
            
            // 1. Recrutador VÊ a aba "Candidatura" (é a aba padrão 0)
            
            // 2. Recrutador NÃO mexe em Vagas. Desabilita a aba inteira.
            if (indexVagas != -1) {
                jTabbedPane1.setEnabledAt(indexVagas, false);
            }
            
            // 3. Recrutador VÊ a aba "Recrutamento"
            
        } else {
            // Se não for nenhum dos dois, desabilita tudo por segurança
            jTabbedPane1.setEnabled(false);
        }
    }
    
 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        Candidatura = new javax.swing.JPanel();
        Cadastro = new javax.swing.JButton();
        Inscricao = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        Contratação = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        Vagas = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        Nome = new javax.swing.JLabel();
        Cargo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Candidatura.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        Cadastro.setText("Cadastrar Candidato");
        Cadastro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CadastroActionPerformed(evt);
            }
        });

        Inscricao.setText("Inscrever Candidatos à Vaga");
        Inscricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InscricaoActionPerformed(evt);
            }
        });

        jButton7.setText("Buscar Candidatos");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout CandidaturaLayout = new javax.swing.GroupLayout(Candidatura);
        Candidatura.setLayout(CandidaturaLayout);
        CandidaturaLayout.setHorizontalGroup(
            CandidaturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CandidaturaLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(CandidaturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Inscricao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                    .addComponent(Cadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(61, Short.MAX_VALUE))
        );
        CandidaturaLayout.setVerticalGroup(
            CandidaturaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CandidaturaLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(Cadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(Inscricao, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(93, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Candidatura", new javax.swing.ImageIcon(getClass().getResource("/Images/user-icon.png")), Candidatura); // NOI18N

        jButton1.setText("Cadastrar Vaga");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Listar Vagas");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ContrataçãoLayout = new javax.swing.GroupLayout(Contratação);
        Contratação.setLayout(ContrataçãoLayout);
        ContrataçãoLayout.setHorizontalGroup(
            ContrataçãoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ContrataçãoLayout.createSequentialGroup()
                .addContainerGap(54, Short.MAX_VALUE)
                .addGroup(ContrataçãoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
                .addGap(61, 61, 61))
        );
        ContrataçãoLayout.setVerticalGroup(
            ContrataçãoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ContrataçãoLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(157, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Vagas", Contratação);

        jButton3.setText("Marcar Entrevista");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Avaliar e Solicitar Contratação");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Consultar Contratações");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout VagasLayout = new javax.swing.GroupLayout(Vagas);
        Vagas.setLayout(VagasLayout);
        VagasLayout.setHorizontalGroup(
            VagasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VagasLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(VagasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        VagasLayout.setVerticalGroup(
            VagasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(VagasLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Recrutamento", Vagas);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/windows-7-user-icon.png"))); // NOI18N

        Nome.setText("Nome");

        Cargo.setText("Cargo");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Nome)
                        .addGap(42, 42, 42)
                        .addComponent(Cargo)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Nome)
                            .addComponent(Cargo))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new RecrutamentoCadastroVagaTela().setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        new RecrutamentoListarVagas().setVisible(true); 
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        new RecrutamentoMarcarEntrevista().setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        new RecrutamentoSolicitarContratacao().setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        new RecrutamentoConsultarContratacoes().setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void CadastroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CadastroActionPerformed
      new TelaCadastroCandidato().setVisible(true);
    }//GEN-LAST:event_CadastroActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        new TelaBuscaCandidato().setVisible(true);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void InscricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InscricaoActionPerformed
        new TelaCandidatura().setVisible(true);
    }//GEN-LAST:event_InscricaoActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MenuRecrutamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuRecrutamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuRecrutamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuRecrutamento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuRecrutamento().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Cadastro;
    private javax.swing.JPanel Candidatura;
    private javax.swing.JLabel Cargo;
    private javax.swing.JPanel Contratação;
    private javax.swing.JButton Inscricao;
    private javax.swing.JLabel Nome;
    private javax.swing.JPanel Vagas;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
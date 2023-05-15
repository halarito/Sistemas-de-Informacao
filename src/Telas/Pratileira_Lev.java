/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Telas;

import java.sql.*;
import net.proteanit.sql.DbUtils;
import Classes.Modulo;
import java.awt.HeadlessException;
import javax.swing.JOptionPane;
/**
 *
 * @author PAULINHA
 */
public class Pratileira_Lev extends javax.swing.JInternalFrame {

    
    Connection conexao = null;
    PreparedStatement pst = null;
    PreparedStatement pst1 = null;
    PreparedStatement pst2 = null;
    ResultSet rs = null; 
    ResultSet rs1 = null;
    ResultSet rs2 = null;
    
    /**
     * Creates new form frmStock
     */
    public Pratileira_Lev() {
        initComponents();
        
        conexao = Modulo.conector();

        preencher();
    }

    public String registrar;
    public String quantidade;
    
    void diminuir(int id){
        
        String cmdSelect = "select quantidade from livros where id = ?";
        
        String cmdUpdate = "update livros set quantidade = ? where id = ?";
        
        
        try{
            pst = conexao.prepareStatement(cmdSelect);
            pst.setString(1, String.valueOf(id));
            rs = pst.executeQuery();
            
            if(rs.next()){
                quantidade = rs.getString("quantidade");
           
            }
            
            
        } catch (Exception e){
            JOptionPane.showMessageDialog(rootPane, e);
        }
        
        try{
            pst = conexao.prepareStatement(cmdUpdate);
            int quantidadeNova = Integer.parseInt(quantidade) - 1;
            pst.setString(1, String.valueOf(quantidadeNova));
            pst.setString(2, String.valueOf(id));
            
            pst.executeUpdate();
            
            
        } catch(Exception e){
            
                JOptionPane.showMessageDialog(rootPane, e);
        }
        
    }
    
    public int verQuant; 
   
    int verQuant(int id){
        
        String verificar = "select quantidade from livros where id = ?";
        
        try{
            pst1 = conexao.prepareStatement(verificar);
            
            pst1.setString(1, String.valueOf(id));
            
            rs1 = pst1.executeQuery();
            
            if(rs1.next()){
                verQuant = Integer.parseInt(rs1.getString("quantidade"));
            }
             
        } catch (Exception e){
            JOptionPane.showMessageDialog(rootPane, e);
        }
        
        
        return verQuant;
    }
    
    void levantar(){

        switch (cbPerfil.getSelectedIndex()) {
            case 0:
                registrar = "insert into levantar (id_livro, perfil, nome, curso, nrEst, nrBI, contacto) values(?, ?, ?, ?, ?, ?, ?)";
                try {
                    pst = conexao.prepareStatement(registrar);
                    
                    int tab = tblLivros.getSelectedRow();
                    int id = Integer.parseInt(tblLivros.getModel().getValueAt(tab, 0).toString());
                    
                    pst.setString(1, String.valueOf(id));
                    pst.setString(2, cbPerfil.getSelectedItem().toString());
                    pst.setString(3, txtNome.getText());
                    pst.setString(4, cbCurso2.getSelectedItem().toString());
                    pst.setString(5, txtNrEst.getText());
                    pst.setString(6, txtNrBI.getText());
                    pst.setString(7, txtContacto.getText());
                    
                    if (txtNome.getText().isEmpty() ||  txtNrBI.getText().isEmpty() || txtContacto.getText().isEmpty() || txtNrEst.getText().isEmpty())
                    {
                        JOptionPane.showMessageDialog(rootPane, "Preencha todos  os campos por favor");
                    }
                    else{
                        int registrarN = JOptionPane.showConfirmDialog(null, "Registrar novo Levantamento para o estudante " + txtNome.getText(), "Confirmaçao", JOptionPane.YES_NO_OPTION);
                        
                        if(registrarN == JOptionPane.YES_OPTION){
                            
                            if(verQuant(id) > 0){
                                int registrado = pst.executeUpdate();
                            
                                if(registrado > 0){
                                    JOptionPane.showMessageDialog(null, "Livro Levantado com sucesso!");
                                
                                    diminuir(id);
                                    txtNome.setText(null);
                                    txtNrBI.setText(null);
                                    txtContacto.setText(null);
                                    txtEmail.setText(null);
                                    txtNrEst.setText(null);
                                    preencher();
                                
                                }    
                            } else{
                                JOptionPane.showMessageDialog(null, "Livro não disponivel na pratileira!");
                                
                            }
                            
                        }
                        
                        
                    }
                    
                } catch (ArrayIndexOutOfBoundsException ex){
                    JOptionPane.showMessageDialog(rootPane, "Selecione o livro por levantar na tabela");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }   break;
            case 1:
                registrar = "insert into levantar (id_livro, perfil, nome, curso, nrBI, contacto, email) values(?, ?, ?, ?, ?, ?, ?)";
                try {
                    pst = conexao.prepareStatement(registrar);
                    
                    int tab = tblLivros.getSelectedRow();
                    int id = Integer.parseInt(tblLivros.getModel().getValueAt(tab, 0).toString());
                    
                    pst.setString(1, String.valueOf(id));
                    pst.setString(2, cbPerfil.getSelectedItem().toString());
                    pst.setString(3, txtNome.getText());
                    pst.setString(4, cbCurso2.getSelectedItem().toString());
                    pst.setString(5, txtNrBI.getText());
                    pst.setString(6, txtContacto.getText());
                    pst.setString(7, txtEmail.getText());
                    
                    if (txtNome.getText().isEmpty() ||  txtNrBI.getText().isEmpty() || txtContacto.getText().isEmpty() || txtEmail.getText().isEmpty())
                    {
                        JOptionPane.showMessageDialog(rootPane, "Preencha todos  os campos por favor");
                    }
                    else{
                        int registrarN = JOptionPane.showConfirmDialog(null, "Registrar novo Levantamento para o docente " + txtNome.getText(), "Confirmaçao", JOptionPane.YES_NO_OPTION);
                        
                        if(registrarN == JOptionPane.YES_OPTION){
                            int registrado = pst.executeUpdate();
                            
                            if(verQuant(id) > 0){
                                
                                if(registrado > 0){
                                    JOptionPane.showMessageDialog(null, "Livro Levantado com sucesso!");
                                
                                    diminuir(id);
                                    txtNome.setText(null);
                                    txtNrBI.setText(null);
                                    txtContacto.setText(null);
                                    txtEmail.setText(null);
                                    preencher();
                                }
                                
                            } else {
                            
                                JOptionPane.showMessageDialog(null, "Livro não disponivel na pratileira!");
                                
                            }
                        }
                    }
                    
                } catch (ArrayIndexOutOfBoundsException ex){
                    JOptionPane.showMessageDialog(rootPane, "Selecione o livro por levantar na tabela");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }   break;
            case 2:
                registrar = "insert into levantar (id_livro, perfil, nome, nrBI, contacto, email) values(?, ?, ?, ?, ?, ?)";
                try {
                    pst = conexao.prepareStatement(registrar);
                    
                    int tab = tblLivros.getSelectedRow();
                    int id = Integer.parseInt(tblLivros.getModel().getValueAt(tab, 0).toString());
                    
                    pst.setString(1, String.valueOf(id));
                    pst.setString(2, cbPerfil.getSelectedItem().toString());
                    pst.setString(3, txtNome.getText());
                    pst.setString(4, txtNrBI.getText());
                    pst.setString(5, txtContacto.getText());
                    pst.setString(6, txtEmail.getText());
                    
                    if (txtNome.getText().isEmpty() ||  txtNrBI.getText().isEmpty() ||txtContacto.getText().isEmpty())
                    {
                        JOptionPane.showMessageDialog(rootPane, "Preencha todos  os campos por favor");
                    }
                    else{
                        int registrarN = JOptionPane.showConfirmDialog(null, "Registrar novo Levantamento para o técnico " + txtNome.getText(), "Confirmaçao", JOptionPane.YES_NO_OPTION);
                        
                        if(registrarN == JOptionPane.YES_OPTION){
                            int registrado = pst.executeUpdate();
                            
                            if(verQuant(id) > 0){
                                
                                if(registrado > 0){
                                
                                    JOptionPane.showMessageDialog(null, "Livro Levantado com sucesso!");
                                
                                    diminuir(id);
                                    txtNome.setText(null);
                                    txtNrBI.setText(null);
                                    txtContacto.setText(null);
                                    txtEmail.setText(null);
                                    preencher();
                                }
                            
                            } else {
                            
                                JOptionPane.showMessageDialog(null, "Livro não disponivel na pratileira!");
                                
                            }
                            
                        }
                    }
                    
                } catch (ArrayIndexOutOfBoundsException ex){
                    JOptionPane.showMessageDialog(rootPane, "Selecione o livro por levantar na tabela");
                }
                catch (Exception e) {
                    JOptionPane.showMessageDialog(rootPane, e);
                }   break;
            default:
                break;
        }
        
        
    }
    
    void preencherC() {
        
        String c = cbCurso1.getSelectedItem().toString();
        
        String sql = "Select id, autor, titulo, edicao, quantidade as quant from livros where curso = ?";

        try {
            pst = conexao.prepareStatement(sql);
            
            pst.setString(1, c);
            
            rs = pst.executeQuery();

            tblLivros.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }
    
    void preencher() {
        
        String sql = "Select id, autor, titulo, edicao, quantidade as quant from livros";

        try {
            pst = conexao.prepareStatement(sql);
            
            rs = pst.executeQuery();

            tblLivros.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblLivros = new javax.swing.JTable();
        jLabel10 = new javax.swing.JLabel();
        cbCurso1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        txtNrEst = new javax.swing.JTextField();
        cbCurso2 = new javax.swing.JComboBox<>();
        txtNrBI = new javax.swing.JTextField();
        txtContacto = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        cbPerfil = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();

        setBackground(new java.awt.Color(0, 153, 102));
        setClosable(true);
        setIconifiable(true);
        setTitle("Armazem");
        setPreferredSize(new java.awt.Dimension(566, 402));

        jPanel1.setBackground(new java.awt.Color(0, 153, 102));
        jPanel1.setForeground(new java.awt.Color(255, 204, 204));

        jLabel1.setFont(new java.awt.Font("Arial Black", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("PRATILEIRA - LEVANTAMENTO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(204, 204, 204));

        tblLivros.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblLivros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblLivros);

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 153, 102));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Curso:");

        cbCurso1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cbCurso1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EPM", "EM", "EI", "CAP", "CA" }));

        jButton1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 153, 102));
        jButton1.setText("Verificar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 102));
        jLabel2.setText("Nome:");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 102));
        jLabel3.setText("Perfil:");

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 153, 102));
        jLabel4.setText("Curso:");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 153, 102));
        jLabel5.setText("Nr. Est.");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 153, 102));
        jLabel6.setText("Nr.BI:");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 153, 102));
        jLabel7.setText("Contacto:");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 153, 102));
        jLabel8.setText("e-mail:");

        txtNome.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtNrEst.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        cbCurso2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cbCurso2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EPM", "EM", "EI", "CAP", "CA" }));

        txtNrBI.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtContacto.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtEmail.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        cbPerfil.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cbPerfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Estudante", "Docente", "Técnico" }));
        cbPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbPerfilActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 153, 102));
        jButton2.setText("Levantar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbCurso1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(169, 169, 169))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNome)
                                    .addComponent(txtNrEst)
                                    .addComponent(cbCurso2, 0, 197, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cbPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtContacto)
                            .addComponent(txtEmail)
                            .addComponent(txtNrBI)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbCurso1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(txtNrBI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtContacto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cbCurso2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNrEst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbPerfilActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbPerfilActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        preencherC();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        levantar();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbCurso1;
    private javax.swing.JComboBox<String> cbCurso2;
    private javax.swing.JComboBox<String> cbPerfil;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblLivros;
    private javax.swing.JTextField txtContacto;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtNrBI;
    private javax.swing.JTextField txtNrEst;
    // End of variables declaration//GEN-END:variables
}

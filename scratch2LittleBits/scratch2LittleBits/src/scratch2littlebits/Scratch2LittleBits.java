/*
 *  electronicadivertida.com 의 scratch2 offline 스크래치 프로젝트 (http://littlebits.c/projects/scrstch-offline)에
 *  기반하여 '도구의 인간'에서 한글화 및 수정 변경한 프로그램입니다.
 *  https://github.com/lhdangerous/scratch2LittleBits_kr으로 프로젝트에 기여/수정요청 해주세요
 *  July 13, 2015
 *  
 */

package scratch2littlebits;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author eduardo
 */
public class Scratch2LittleBits extends javax.swing.JFrame {


    private ScratchArduino moArduino;
    private ScratchHttp moHTTP;
    private Thread moThrHTTP;
    private final Timer moTimer;
    private boolean mbConectados = false;

    /**
     * Creates new form Scratch2LittleBits
     */
    public Scratch2LittleBits() {
        initComponents();
        moTimer = new Timer();
        moTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    try {

                        if(moArduino!=null && moArduino.isConnected()){
                            lblLittleBits.setText("리틀비츠 연결됨");
                        }else{
                            lblLittleBits.setText("리틀비츠 연결 안됨");
                        }
                        if(moHTTP!=null && moHTTP.isConectado()){
                            lblScratch.setText("스크래치 연결됨");
                        }else{
                            lblScratch.setText("스크래치 연결 안됨");
                        }
                        if(moHTTP!=null && moHTTP.isConectado() && moArduino!=null && moArduino.isConnected()){
                            if(!mbConectados){
                                lblConexion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon_connected.png")));
                                setAlwaysOnTop(false);
                            }
                            mbConectados = true;
                        }else{
                            if(mbConectados){
                                lblConexion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon.png")));
                                setAlwaysOnTop(true);
                            }
                            mbConectados = false;
                        }
                        if(moArduino!=null){
                            jComboBox1.setSelectedItem(moArduino.getPuerto());                            
                        }
                    } catch (Throwable ex) {
                        ex.printStackTrace();
                    }
                }
            }, 50, 2000);
    }

    public void conectarHTTP() throws IOException {
        moHTTP = new ScratchHttp(this);
        moThrHTTP = new Thread(moHTTP);
        moThrHTTP.start();
    }

    public void conectarArduino(String psPuerto) throws Exception {
        if(moArduino!=null){
            moArduino.close();
        }
        moArduino = new ScratchArduino(psPuerto);
        recargarPuertos();
    }
    public ScratchArduino getArduino(){
        return moArduino;
    } 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblConexion = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        lblLittleBits = new javax.swing.JLabel();
        lblScratch = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Scratch2 offline + Littlebits");
        setAlwaysOnTop(true);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        lblConexion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icon.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        getContentPane().add(lblConexion, gridBagConstraints);

        jLabel1.setText("리틀비츠가 연결된 포트:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });
        jComboBox1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jComboBox1FocusGained(evt);
            }
        });
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 0);
        getContentPane().add(jComboBox1, gridBagConstraints);

        lblLittleBits.setBackground(new java.awt.Color(255, 204, 204));
        lblLittleBits.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLittleBits.setText(" ");
        lblLittleBits.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 0);
        getContentPane().add(lblLittleBits, gridBagConstraints);

        lblScratch.setBackground(new java.awt.Color(255, 204, 51));
        lblScratch.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblScratch.setText(" ");
        lblScratch.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 0, 0);
        getContentPane().add(lblScratch, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("굴림", 0, 9)); // NOI18N
        jLabel2.setText("make it happen, make it real!   도구의 인간");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipady = 2;
        getContentPane().add(jLabel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox1MouseClicked
        
    }//GEN-LAST:event_jComboBox1MouseClicked

    private void jComboBox1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jComboBox1FocusGained
        recargarPuertos();
    }//GEN-LAST:event_jComboBox1FocusGained

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        if(moArduino!=null && jComboBox1.getSelectedItem()!=null){
            if(!jComboBox1.getSelectedItem().toString().equals(moArduino.getPuerto())){
                try {
                    moArduino.setPuerto(jComboBox1.getSelectedItem().toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(rootPane, ex.toString());
                }
            }
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged

    private void recargarPuertos(){
        String[] lasLista = ScratchArduino.getListaPuertos();
        jComboBox1.removeAllItems();
        for(int i = 0 ; i < lasLista.length; i++){
            jComboBox1.addItem(lasLista[i]);
        }
        jComboBox1.setSelectedIndex(lasLista.length-1);
    }
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
            java.util.logging.Logger.getLogger(Scratch2LittleBits.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Scratch2LittleBits.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Scratch2LittleBits.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Scratch2LittleBits.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Scratch2LittleBits lo = new Scratch2LittleBits();
                    lo.conectarHTTP();
                    lo.conectarArduino("");
                    lo.setVisible(true);
                } catch (Throwable ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(new JLabel(), ex.toString());
                }
            }
        });
    }


    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel lblConexion;
    private javax.swing.JLabel lblLittleBits;
    private javax.swing.JLabel lblScratch;
    // End of variables declaration//GEN-END:variables
}

package org.netfpga.router;
/*
 * OutputPortLookupFrame.java
 *
 * Created on May 7, 2007, 11:13 PM
 */

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.Timer;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;

import org.netfpga.backend.NFDevice;
import org.netfpga.backend.NFDeviceConsts;
import org.netfpga.backend.RegTableModel;

/**
 *
 * @author  jnaous
 * modified by Georgina Kalogeridou, September 2014, Computer Lab, University of Cambridge
 */
@SuppressWarnings("serial")
public class OutputPortLookupFrame extends javax.swing.JInternalFrame {

    private RoutingTableModel routingTableModel;
    private ARPTableModel arpTableModel;
    private InterfacePortConfigTableModel ifaceTableModel;
    private RegTableModel regTableModel;

    private StatsCollection[] statsCollection;

    private static final int NUM_REGS_USED = 6;

    private Timer timer;
    private ActionListener timerActionListener;


    /**
     * Creates new form OutputPortLookupFrame
     */
    public OutputPortLookupFrame(NFDevice nf2, Timer timer) {
        routingTableModel = new RoutingTableModel(nf2);
        arpTableModel = new ARPTableModel(nf2);
        ifaceTableModel = new InterfacePortConfigTableModel(nf2);

        initComponents();

        /* set to single selection since the selected row doesn't
         * get updates */
        routingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        arpTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        /* on selection changes, flush modified entries */
        routingTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                routingTableModel.flushTable();
            }

        });

        arpTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                arpTableModel.flushTable();
            }

        });

        /* add the addresses to monitor through the regTableModel */
        long[] addresses = new long[NUM_REGS_USED];

        addresses[0] = NFDeviceConsts.XPAR_NF10_ROUTER_OUTPUT_PORT_LOOKUP_0_BAR0_PKT_FORWARDED;
        addresses[1] = NFDeviceConsts.XPAR_NF10_ROUTER_OUTPUT_PORT_LOOKUP_0_BAR0_PKT_SENT_FROM_CPU;
        addresses[2] = NFDeviceConsts.XPAR_NF10_ROUTER_OUTPUT_PORT_LOOKUP_0_BAR0_PKT_SENT_CPU_NON_IP;
        addresses[3] = NFDeviceConsts.XPAR_NF10_ROUTER_OUTPUT_PORT_LOOKUP_0_BAR0_PKT_DROPPED_CHECKSUM;
        addresses[4] = NFDeviceConsts.XPAR_NF10_ROUTER_OUTPUT_PORT_LOOKUP_0_BAR0_PKT_SENT_TO_CPU_BAD_TTL;
        addresses[5] = NFDeviceConsts.XPAR_NF10_ROUTER_OUTPUT_PORT_LOOKUP_0_BAR0_PKT_DROPPED_WRONG_DST_MAC;

        /* create the statistics collections */
        statsCollection = new StatsCollection[NUM_REGS_USED];
        statsCollection[0] = new StatsCollection(this.numForwardedPktsValueLabel, null);
        statsCollection[1] = new StatsCollection(this.numPktsFromCPUValueLabel, null);
        statsCollection[2] = new StatsCollection(this.numNonIPPktsValueLabel, null);
        statsCollection[3] = new StatsCollection(this.numBadIPPktsValueLabel, null);
        statsCollection[4] = new StatsCollection(this.numBadTTLPktsValueLabel, null);
        statsCollection[5] = new StatsCollection(this.numWrongMacPktsValueLabel, null);

        /* create the register table model which we want to monitor */
        regTableModel = new RegTableModel(nf2, addresses);

        /* Add listener to changes in the register values */
        regTableModel.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                int firstRow = e.getFirstRow();
                int lastRow = e.getLastRow();
                for(int i=firstRow; i<=lastRow; i++){
                    if(i!=TableModelEvent.HEADER_ROW){
                        statsCollection[i].update(regTableModel.getRegisterAt(i).getValue());
                    }
                }
            }
        });

        timerActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                regTableModel.updateTable();
                routingTableModel.updateTable();
                arpTableModel.updateTable();
                ifaceTableModel.updateTable();
            }
        };
        this.timer = timer;

        /* add action listener to the timer */
        timer.addActionListener(timerActionListener);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        routerConfigScrollPane = new javax.swing.JScrollPane();
        routerConfigPane = new javax.swing.JPanel();
        ifaceTableScrollPane = new javax.swing.JScrollPane();
        ifaceTable = new javax.swing.JTable();
        arpTableScrollPane = new javax.swing.JScrollPane();
        arpTable = new javax.swing.JTable();
        routingTableScrollPane = new javax.swing.JScrollPane();
        routingTable = new javax.swing.JTable();
        ifaceConfigLabel = new javax.swing.JLabel();
        arpTableLabel = new javax.swing.JLabel();
        routingTableLabel = new javax.swing.JLabel();
        arpRemoveEntryButton = new javax.swing.JButton();
        routingRemoveEntryButton = new javax.swing.JButton();
        pageTitleLabel = new javax.swing.JLabel();
        resetStatsButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        numPktsFromCPUHelpButton = new javax.swing.JButton();
        numPktsFromCPULabel = new javax.swing.JLabel();
        numBadIPPktsHelpButton = new javax.swing.JButton();
        numBadTTLPktsHelpButton = new javax.swing.JButton();
        numNonIPPktsHelpButton = new javax.swing.JButton();
        numForwardedPktsHelpButton = new javax.swing.JButton();
        numWrongMacPktsHelpButton = new javax.swing.JButton();
        numBadIPPktsLabel = new javax.swing.JLabel();
        numBadTTLPktsLabel = new javax.swing.JLabel();
        numNonIPPktsLabel = new javax.swing.JLabel();
        numForwardedPktsLabel = new javax.swing.JLabel();
        numWrongMacPktsLabel = new javax.swing.JLabel();
        numPktsFromCPUValueLabel = new javax.swing.JLabel();
        numBadIPPktsValueLabel = new javax.swing.JLabel();
        numBadTTLPktsValueLabel = new javax.swing.JLabel();
        numNonIPPktsValueLabel = new javax.swing.JLabel();
        numForwardedPktsValueLabel = new javax.swing.JLabel();
        numWrongMacPktsValueLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Router Output port Lookup");
        setVisible(true);
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameClosed(evt);
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        ifaceTable.setModel(ifaceTableModel);
        ifaceTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        ifaceTableScrollPane.setViewportView(ifaceTable);

        arpTable.setModel(arpTableModel);
        arpTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        arpTableScrollPane.setViewportView(arpTable);

        routingTable.setModel(routingTableModel);
        routingTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        routingTableScrollPane.setViewportView(routingTable);

        ifaceConfigLabel.setText("Interface Configuration");

        arpTableLabel.setText("ARP Table");

        routingTableLabel.setText("Routing Table");

        arpRemoveEntryButton.setText("Reset Entry");
        arpRemoveEntryButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        arpRemoveEntryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arpRemoveEntryButtonActionPerformed(evt);
            }
        });

        routingRemoveEntryButton.setText("Reset Entry");
        routingRemoveEntryButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        routingRemoveEntryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                routingRemoveEntryButtonActionPerformed(evt);
            }
        });

        pageTitleLabel.setFont(new java.awt.Font("Dialog", 1, 18));
        pageTitleLabel.setText("Router Output Port Lookup");

//        resetStatsButton.setText("Reset Statistics");
//        resetStatsButton.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                resetStatsButtonActionPerformed(evt);
//            }
//        }
//	);

        numPktsFromCPUHelpButton.setFont(new java.awt.Font("Dialog", 1, 8));
        numPktsFromCPUHelpButton.setText("?");
        numPktsFromCPUHelpButton.setToolTipText("Click for help");
        numPktsFromCPUHelpButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        numPktsFromCPUHelpButton.setPreferredSize(new java.awt.Dimension(25, 25));
        numPktsFromCPUHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numPktsFromCPUHelpButtonActionPerformed(evt);
            }
        });

        numPktsFromCPULabel.setText("Number of packets sent from the CPU");

        numBadIPPktsHelpButton.setFont(new java.awt.Font("Dialog", 1, 8));
        numBadIPPktsHelpButton.setText("?");
        numBadIPPktsHelpButton.setToolTipText("Click for help");
        numBadIPPktsHelpButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        numBadIPPktsHelpButton.setPreferredSize(new java.awt.Dimension(25, 25));
        numBadIPPktsHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numBadIPPktsHelpButtonActionPerformed(evt);
            }
        });

        numBadTTLPktsHelpButton.setFont(new java.awt.Font("Dialog", 1, 8));
        numBadTTLPktsHelpButton.setText("?");
        numBadTTLPktsHelpButton.setToolTipText("Click for help");
        numBadTTLPktsHelpButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        numBadTTLPktsHelpButton.setPreferredSize(new java.awt.Dimension(25, 25));
        numBadTTLPktsHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numBadTTLPktsHelpButtonActionPerformed(evt);
            }
        });

        numNonIPPktsHelpButton.setFont(new java.awt.Font("Dialog", 1, 8));
        numNonIPPktsHelpButton.setText("?");
        numNonIPPktsHelpButton.setToolTipText("Click for help");
        numNonIPPktsHelpButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        numNonIPPktsHelpButton.setPreferredSize(new java.awt.Dimension(25, 25));
        numNonIPPktsHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numNonIPPktsHelpButtonActionPerformed(evt);
            }
        });

        numForwardedPktsHelpButton.setFont(new java.awt.Font("Dialog", 1, 8));
        numForwardedPktsHelpButton.setText("?");
        numForwardedPktsHelpButton.setToolTipText("Click for help");
        numForwardedPktsHelpButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        numForwardedPktsHelpButton.setPreferredSize(new java.awt.Dimension(25, 25));
        numForwardedPktsHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numForwardedPktsHelpButtonActionPerformed(evt);
            }
        });

        numWrongMacPktsHelpButton.setFont(new java.awt.Font("Dialog", 1, 8));
        numWrongMacPktsHelpButton.setText("?");
        numWrongMacPktsHelpButton.setToolTipText("Click for help");
        numWrongMacPktsHelpButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        numWrongMacPktsHelpButton.setPreferredSize(new java.awt.Dimension(25, 25));
        numWrongMacPktsHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numWrongMacPktsHelpButtonActionPerformed(evt);
            }
        });

        numBadIPPktsLabel.setText("Number of packets dropped due to a bad IP checksum");

        numBadTTLPktsLabel.setText("Number of packets sent to the CPU due to a TTL=0 or 1");

        numNonIPPktsLabel.setText("Number of non-IP packets sent to the CPU");

        numForwardedPktsLabel.setText("Number of packets forwarded");

        numWrongMacPktsLabel.setText("Number of packets dropped due to a wrong destination MAC address");

        numPktsFromCPUValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        numPktsFromCPUValueLabel.setText("0");

        numBadIPPktsValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        numBadIPPktsValueLabel.setText("0");

        numBadTTLPktsValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        numBadTTLPktsValueLabel.setText("0");

        numNonIPPktsValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        numNonIPPktsValueLabel.setText("0");

        numForwardedPktsValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        numForwardedPktsValueLabel.setText("0");

        numWrongMacPktsValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        numWrongMacPktsValueLabel.setText("0");

        org.jdesktop.layout.GroupLayout routerConfigPaneLayout = new org.jdesktop.layout.GroupLayout(routerConfigPane);
        routerConfigPane.setLayout(routerConfigPaneLayout);
        routerConfigPaneLayout.setHorizontalGroup(
            routerConfigPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(routerConfigPaneLayout.createSequentialGroup()
                .add(10, 10, 10)
                .add(pageTitleLabel))
//            .add(routerConfigPaneLayout.createSequentialGroup()
//                .add(10, 10, 10)
//                .add(resetStatsButton))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, routerConfigPaneLayout.createSequentialGroup()
                .add(10, 10, 10)
                .add(numWrongMacPktsHelpButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(10, 10, 10)
                .add(numWrongMacPktsLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(numWrongMacPktsValueLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                .add(15, 15, 15))
            .add(jSeparator2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, routerConfigPaneLayout.createSequentialGroup()
                .add(routerConfigPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, routerConfigPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                        .add(org.jdesktop.layout.GroupLayout.TRAILING, routerConfigPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, routerConfigPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                                .add(org.jdesktop.layout.GroupLayout.TRAILING, routerConfigPaneLayout.createSequentialGroup()
                                    .add(10, 10, 10)
                                    .add(numForwardedPktsHelpButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(10, 10, 10)
                                    .add(numForwardedPktsLabel)
                                    .add(200, 200, 200))
                                .add(routerConfigPaneLayout.createSequentialGroup()
                                    .add(10, 10, 10)
                                    .add(numPktsFromCPUHelpButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                    .add(10, 10, 10)
                                    .add(numPktsFromCPULabel)
                                    .add(150, 150, 150)))
                            .add(routerConfigPaneLayout.createSequentialGroup()
                                .add(10, 10, 10)
                                .add(numNonIPPktsHelpButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(10, 10, 10)
                                .add(numNonIPPktsLabel)
                                .add(120, 120, 120)))
                        .add(routerConfigPaneLayout.createSequentialGroup()
                            .add(10, 10, 10)
                            .add(numBadIPPktsHelpButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(10, 10, 10)
                            .add(numBadIPPktsLabel)
                            .add(49, 49, 49)))
                    .add(routerConfigPaneLayout.createSequentialGroup()
                        .add(10, 10, 10)
                        .add(numBadTTLPktsHelpButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(10, 10, 10)
                        .add(numBadTTLPktsLabel)
                        .add(38, 38, 38)))
                .add(routerConfigPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, numBadTTLPktsValueLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, numBadIPPktsValueLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, numNonIPPktsValueLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, numPktsFromCPUValueLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, numForwardedPktsValueLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE))
                .add(15, 15, 15))
            .add(routerConfigPaneLayout.createSequentialGroup()
                .add(10, 10, 10)
                .add(routerConfigPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, ifaceTableScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, ifaceConfigLabel))
                .add(14, 14, 14))
            .add(routerConfigPaneLayout.createSequentialGroup()
                .addContainerGap()
                .add(routingTableLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 351, Short.MAX_VALUE)
                .add(routingRemoveEntryButton)
                .addContainerGap())
            .add(routerConfigPaneLayout.createSequentialGroup()
                .addContainerGap()
                .add(routingTableScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                .add(10, 10, 10))
            .add(routerConfigPaneLayout.createSequentialGroup()
                .addContainerGap()
                .add(arpTableLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 376, Short.MAX_VALUE)
                .add(arpRemoveEntryButton)
                .addContainerGap())
            .add(routerConfigPaneLayout.createSequentialGroup()
                .add(10, 10, 10)
                .add(arpTableScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 531, Short.MAX_VALUE)
                .addContainerGap())
        );
        routerConfigPaneLayout.setVerticalGroup(
            routerConfigPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(routerConfigPaneLayout.createSequentialGroup()
                .add(jSeparator1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(8, 8, 8)
                .add(pageTitleLabel)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jSeparator2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(22, 22, 22)
//                .add(resetStatsButton)
                .add(15, 15, 15)
                .add(routerConfigPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(numForwardedPktsHelpButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(numForwardedPktsLabel)
                    .add(numForwardedPktsValueLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(routerConfigPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(numPktsFromCPUHelpButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(numPktsFromCPULabel)
                    .add(numPktsFromCPUValueLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(routerConfigPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(numNonIPPktsHelpButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(numNonIPPktsLabel)
                    .add(numNonIPPktsValueLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(routerConfigPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(numBadIPPktsHelpButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(numBadIPPktsLabel)
                    .add(numBadIPPktsValueLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(routerConfigPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(numBadTTLPktsHelpButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(numBadTTLPktsLabel)
                    .add(numBadTTLPktsValueLabel))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(routerConfigPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(numWrongMacPktsHelpButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 10, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(numWrongMacPktsLabel)
                    .add(numWrongMacPktsValueLabel))
                .add(35, 35, 35)
                .add(ifaceConfigLabel)
                .add(5, 5, 5)
                .add(ifaceTableScrollPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 82, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(20, 20, 20)
                .add(routerConfigPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(routingTableLabel)
                    .add(routingRemoveEntryButton))
                .add(11, 11, 11)
                .add(routingTableScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                .add(25, 25, 25)
                .add(routerConfigPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(arpTableLabel)
                    .add(arpRemoveEntryButton))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(arpTableScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE)
                .addContainerGap())
        );
        routerConfigScrollPane.setViewportView(routerConfigPane);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(routerConfigScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 571, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(routerConfigScrollPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 859, Short.MAX_VALUE)
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void arpRemoveEntryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arpRemoveEntryButtonActionPerformed
	//this.regTableModel.clearAll();
        arpTableModel.clear(arpTable.getSelectedRow());
    }//GEN-LAST:event_arpRemoveEntryButtonActionPerformed

    private void routingRemoveEntryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_routingRemoveEntryButtonActionPerformed
        routingTableModel.clear(routingTable.getSelectedRow());
    }//GEN-LAST:event_routingRemoveEntryButtonActionPerformed

    private void formInternalFrameClosed(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameClosed
        this.timer.removeActionListener(timerActionListener);
    }//GEN-LAST:event_formInternalFrameClosed

//    private void resetStatsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetStatsButtonActionPerformed
//        for(int i=0; i<statsCollection.length; i++){
//            statsCollection[i].reset();
//            regTableModel.setValueAt(new Integer(0), i, RegTableModel.VALUE_COL);
//        }
//    }//GEN-LAST:event_resetStatsButtonActionPerformed

    private void numWrongMacPktsHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numWrongMacPktsHelpButtonActionPerformed
        JOptionPane.showMessageDialog(null, "This is the number of packets that have arrived to a MAC port" +
                "\n and the destination MAC address was neither a broadcast address nor the MAC address of the" +
                "\n port on which the packet arrived.", "Help",
                JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_numWrongMacPktsHelpButtonActionPerformed

    private void numForwardedPktsHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numForwardedPktsHelpButtonActionPerformed
        JOptionPane.showMessageDialog(null, "This is the number of packets that have went through the normal" +
                "\n IP LPM lookup and then had a hit in the ARP cache and were directly forwarded to the correct" +
                "\n output port. This includes the number of packets that are destined to the router interfaces.",
                "Help", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_numForwardedPktsHelpButtonActionPerformed

    private void numNonIPPktsHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numNonIPPktsHelpButtonActionPerformed
        JOptionPane.showMessageDialog(null, "This is the number of packets received whose Ethertype indicated " +
                "\nthat they are not IP packets. This includes ARP packets and any other unknown Ethertypes.",
                "Help", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_numNonIPPktsHelpButtonActionPerformed

    private void numBadTTLPktsHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numBadTTLPktsHelpButtonActionPerformed
        JOptionPane.showMessageDialog(null, "This is the number of packets sent to the CPU because they have a TTL" +
                "\n of 0 or 1. The CPU will usually generate an ICMP message back to the source.",
                "Help", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_numBadTTLPktsHelpButtonActionPerformed

    private void numBadIPPktsHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numBadIPPktsHelpButtonActionPerformed
        JOptionPane.showMessageDialog(null, "This is the number of packets have been dropped because their IP " +
                "\nheaders failed the IP checksum test.",
                "Help", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_numBadIPPktsHelpButtonActionPerformed

    private void numPktsFromCPUHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numPktsFromCPUHelpButtonActionPerformed
        JOptionPane.showMessageDialog(null, "This is the number of packets the CPU has sent through the router. " +
                "\nIt is a count of the number of packets arriving from any of the CPU input queues.", "Help",
                JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_numPktsFromCPUHelpButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton arpRemoveEntryButton;
    private javax.swing.JTable arpTable;
    private javax.swing.JLabel arpTableLabel;
    private javax.swing.JScrollPane arpTableScrollPane;
    private javax.swing.JLabel ifaceConfigLabel;
    private javax.swing.JTable ifaceTable;
    private javax.swing.JScrollPane ifaceTableScrollPane;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton numBadIPPktsHelpButton;
    private javax.swing.JLabel numBadIPPktsLabel;
    private javax.swing.JLabel numBadIPPktsValueLabel;
    private javax.swing.JButton numBadTTLPktsHelpButton;
    private javax.swing.JLabel numBadTTLPktsLabel;
    private javax.swing.JLabel numBadTTLPktsValueLabel;
    private javax.swing.JButton numForwardedPktsHelpButton;
    private javax.swing.JLabel numForwardedPktsLabel;
    private javax.swing.JLabel numForwardedPktsValueLabel;
    private javax.swing.JButton numNonIPPktsHelpButton;
    private javax.swing.JLabel numNonIPPktsLabel;
    private javax.swing.JLabel numNonIPPktsValueLabel;
    private javax.swing.JButton numPktsFromCPUHelpButton;
    private javax.swing.JLabel numPktsFromCPULabel;
    private javax.swing.JLabel numPktsFromCPUValueLabel;
    private javax.swing.JButton numWrongMacPktsHelpButton;
    private javax.swing.JLabel numWrongMacPktsLabel;
    private javax.swing.JLabel numWrongMacPktsValueLabel;
    private javax.swing.JLabel pageTitleLabel;
    private javax.swing.JButton resetStatsButton;
    private javax.swing.JPanel routerConfigPane;
    private javax.swing.JScrollPane routerConfigScrollPane;
    private javax.swing.JButton routingRemoveEntryButton;
    private javax.swing.JTable routingTable;
    private javax.swing.JLabel routingTableLabel;
    private javax.swing.JScrollPane routingTableScrollPane;
    // End of variables declaration//GEN-END:variables

}
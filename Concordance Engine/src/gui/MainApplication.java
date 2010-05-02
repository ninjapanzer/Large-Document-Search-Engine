package gui;

import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Event;
import java.awt.BorderLayout;
import java.awt.FileDialog;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.KeyStroke;
import java.awt.Point;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JDialog;
import java.awt.Dimension;
import javax.swing.JTextPane;

import analysis.AnalysisLoader;
import app.App;

import java.io.File;
import java.lang.String;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;

import structs.SelectionItems;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import java.awt.Rectangle;

public class MainApplication {

	private static Logger logger = Logger.getLogger(gui.MainApplication.class);  //  @jve:decl-index=0:
	private static MainApplication application = null;
	private static DefaultListModel listModel = new DefaultListModel();
	private static Object[] selected = null;
	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="10,10"
	private JPanel jContentPane = null;
	private JMenuBar jJMenuBar = null;
	private JMenu fileMenu = null;
	private JMenu editMenu = null;
	private JMenu helpMenu = null;
	private JMenuItem exitMenuItem = null;
	private JMenuItem openMenuItem = null;
	private JMenuItem aboutMenuItem = null;
	private JMenuItem cutMenuItem = null;
	private JMenuItem copyMenuItem = null;
	private JMenuItem pasteMenuItem = null;
	private JMenuItem saveMenuItem = null;
	private JDialog aboutDialog = null;  //  @jve:decl-index=0:visual-constraint="575,12"
	private JPanel aboutContentPane = null;
	private JLabel aboutVersionLabel = null;
	private JTextPane jTextPane = null;
	private JTextPane jTextPane1 = null;
	private JLabel jLabel = null;
	final static Vector<App> analyzingObjects = new Vector<App>();  //  @jve:decl-index=0:
	final static Vector<App> analyzedObjects = new Vector<App>();  //  @jve:decl-index=0:
	private static JList jList = null;
	private JButton jButton = null;
	private JButton Auto = null;
	/**
	 * This method initializes jTextPane	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JButton getAutoButton(){
		if (Auto == null) {
			Auto = new JButton();
			Auto.setSize(113, 25);
			Auto.setLocation(new Point(207, 45));
			Auto.setText("Auto");
			Auto.setEnabled(true);
			Auto.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {					// TODO Auto-generated method stub
					new Thread(new AutoProcessTestFiles(".."+File.separator+"Concordance"+File.separator+"Datafiles"+File.separator+"DeusCaritasEst3",0L)).start();
					new Thread(new AutoProcessTestFiles(".."+File.separator+"Concordance"+File.separator+"Datafiles"+File.separator+"DeusCaritasEst3_Start_4",1000L)).start();
					new Thread(new AutoProcessTestFiles(".."+File.separator+"Concordance"+File.separator+"Datafiles"+File.separator+"DeusCaritasEst3_Through_5.txt",1500L)).start();
					new Thread(new AutoProcessTestFiles(".."+File.separator+"Concordance"+File.separator+"Datafiles"+File.separator+"DeusCaritasEst3_With_Intro",2000L)).start();
					//new Thread(new AutoProcessTestFiles(".."+File.separator+"Concordance"+File.separator+"Datafiles"+File.separator+"deuscaritasestall.txt")).start();
					//new Thread(new AutoProcessTestFiles(".."+File.separator+"Concordance"+File.separator+"Datafiles"+File.separator+"caritasinveritate.txt")).start();
				}
			});
		}
		return Auto;
	}
	private JTextPane getJTextPane() {
		if (jTextPane == null) {
			jTextPane = new JTextPane();
			jTextPane.setText("The Large Document Search System\nis a theoretical mechanism to\ndetect the meaning of literary works\nand allow those works to be directly\ncompared to each other in part or in\nwhole.");
		}
		return jTextPane;
	}

	/**
	 * This method initializes jTextPane1	
	 * 	
	 * @return javax.swing.JTextPane	
	 */
	private JTextPane getJTextPane1() {
		if (jTextPane1 == null)
		{
			jTextPane1 = new JTextPane();
			jTextPane1.setText("By: Paul Scarrone");
		}
		return jTextPane1;
	}

	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */

	/**
	 * This method initializes jList	
	 * 	
	 * @return javax.swing.JList	
	 */
	private static void updateFileList() {
		listModel.clear();
		for(App item : analyzedObjects) {
			listModel.addElement("File "+item.getThreadID()+" "+item.getFileName());
			}
		jList.setModel(listModel);
		//listModel.addElement(analyzedObjects.size());
		
	}
	private JList getJList() {
		if (jList == null) {
			jList = new JList();
			jList.setBounds(new Rectangle(1, 0, 179, 300));
			jList.addListSelectionListener(new ListSelectionListener() {
			      public void valueChanged(ListSelectionEvent evt) {
			    	  if (!evt.getValueIsAdjusting())
			    	  {
			    		  JList list = (JList) evt.getSource();
			    		  selected = list.getSelectedValues();
			    		  if (selected.length >= 2){
			    			  jButton.setEnabled(true);
			    		  }
			    		  else {
			    			  jButton.setEnabled(false);
			    		  }
			    	  }
			      }
			    });
		}
		return jList;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setSize(113, 25);
			jButton.setLocation(new Point(207, 15));
			jButton.setText("Compare");
			jButton.setEnabled(false);
			jButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					new Thread(new Runnable() {
						private SelectionItems SelItems = null;
						@Override
						public void run() {
							String filename = "";
							ArrayList<SelectionItems> Selection = new ArrayList<SelectionItems>();
							for(Object item : selected) {
								String[] splititem = item.toString().split(" ");
								for(int i = 2; i<splititem.length;i++) {
									filename = filename+ " " +splititem[i];
								}
								filename = filename.substring(1);
								SelItems = new SelectionItems();
								SelItems.ID = Integer.parseInt(splititem[1]);
								SelItems.Filename = filename;
								Selection.add(SelItems);
								filename = "";
							}
							try {
								@SuppressWarnings("unused")
								AnalysisLoader Comparison = new AnalysisLoader(Selection, analyzedObjects);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//@SuppressWarnings("unused")
							//CompareDocuments Comparison = new CompareDocuments(Selection, analyzedObjects);
						}
					}).start();	
				}
			});
		}
		return jButton;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				application = new MainApplication();
				application.getJFrame().setVisible(true);
			}
		});
		new Thread(new Runnable() {
			@Override
			public void run() {
				App obj = null;
				do {
					for(Iterator<App> iter = analyzingObjects.iterator(); iter.hasNext();){
						try{
							synchronized (this) {
								obj = iter.next();
							}
						}
						catch(ConcurrentModificationException e){
							try {
								Thread.sleep(500);
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							synchronized (this) {
								obj = iter.next();
							}
						}
						if(obj.isDone()) {
							logger.trace("Moving Thread Object "+analyzedObjects.size());
							obj.setThreadID(analyzedObjects.size());
							analyzedObjects.add(obj);
							iter.remove();
							logger.trace("Doing Size("+analyzingObjects.size()+")     Done Size("+analyzedObjects.size()+")    Value("+obj.getThreadID()+")");
							logger.trace("Thread Object "+obj.getThreadID()+" Moved");
							updateFileList();
						}
					}
					try {
						Thread.sleep(150);
					} catch(InterruptedException e) {
						logger.trace("Failed to Sleep");
						e.printStackTrace();
					}		
				} while (true);
			}
		}).start();
	}

	/**
	 * This method initializes jFrame
	 * 
	 * @return javax.swing.JFrame
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			jFrame.setJMenuBar(getJJMenuBar());
			jFrame.setSize(561, 375);
			jFrame.setContentPane(getJContentPane());
			jFrame.setTitle("Application");
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabel = new JLabel();
			jLabel.setText("DROP HERE");
			jLabel.setBounds(new Rectangle(0, 300, 545, 16));
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabel, null);
			jContentPane.add(getJList(), null);
			jContentPane.add(getJButton(), null);
			jContentPane.add(getAutoButton(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jJMenuBar	
	 * 	
	 * @return javax.swing.JMenuBar	
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getFileMenu());
			jJMenuBar.add(getEditMenu());
			jJMenuBar.add(getHelpMenu());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getFileMenu() {
		if (fileMenu == null) {
			fileMenu = new JMenu();
			fileMenu.setText("File");
			fileMenu.add(getSaveMenuItem());
			fileMenu.add(getOpenMenuItem());
			fileMenu.add(getExitMenuItem());
		}
		return fileMenu;
	}

	private JMenuItem getOpenMenuItem() {
		if (openMenuItem == null) {
			openMenuItem = new JMenuItem();
			openMenuItem.setText("Open");
			openMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					FileDialog fd = new FileDialog(jFrame, null, FileDialog.LOAD);
				    fd.setLocation(50, 50);
				    fd.setVisible(true);
				    final FileDialog fd2 = fd;
				    new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								logger.trace("File Dialog: "+fd2.getDirectory() + fd2.getFile());
								App element = new App(fd2.getDirectory() + fd2.getFile());
								analyzingObjects.add(element);
								}catch (Exception e) {
									logger.trace("Cannot Create New Thread");
								}
						}
					}).start();
				}
			});
		}
		return openMenuItem;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getEditMenu() {
		if (editMenu == null) {
			editMenu = new JMenu();
			editMenu.setText("Edit");
			editMenu.add(getCutMenuItem());
			editMenu.add(getCopyMenuItem());
			editMenu.add(getPasteMenuItem());
		}
		return editMenu;
	}

	/**
	 * This method initializes jMenu	
	 * 	
	 * @return javax.swing.JMenu	
	 */
	private JMenu getHelpMenu() {
		if (helpMenu == null) {
			helpMenu = new JMenu();
			helpMenu.setText("Help");
			helpMenu.add(getAboutMenuItem());
		}
		return helpMenu;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getExitMenuItem() {
		if (exitMenuItem == null) {
			exitMenuItem = new JMenuItem();
			exitMenuItem.setText("Exit");
			exitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			});
		}
		return exitMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getAboutMenuItem() {
		if (aboutMenuItem == null) {
			aboutMenuItem = new JMenuItem();
			aboutMenuItem.setText("About");
			aboutMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					JDialog aboutDialog = getAboutDialog();
					aboutDialog.pack();
					Point loc = getJFrame().getLocation();
					loc.translate(20, 20);
					aboutDialog.setLocation(loc);
					aboutDialog.setVisible(true);
				}
			});
		}
		return aboutMenuItem;
	}

	/**
	 * This method initializes aboutDialog	
	 * 	
	 * @return javax.swing.JDialog
	 */
	private JDialog getAboutDialog() {
		if (aboutDialog == null) {
			aboutDialog = new JDialog(getJFrame(), true);
			aboutDialog.setTitle("About");
			aboutDialog.setSize(new Dimension(224, 189));
			aboutDialog.setContentPane(getAboutContentPane());
		}
		return aboutDialog;
	}

	/**
	 * This method initializes aboutContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getAboutContentPane() {
		if (aboutContentPane == null) {
			aboutContentPane = new JPanel();
			aboutContentPane.setLayout(new BorderLayout());
			aboutContentPane.add(getAboutVersionLabel(), BorderLayout.NORTH);
			aboutContentPane.add(getJTextPane(), BorderLayout.CENTER);
			aboutContentPane.add(getJTextPane1(), BorderLayout.SOUTH);
		}
		return aboutContentPane;
	}

	/**
	 * This method initializes aboutVersionLabel	
	 * 	
	 * @return javax.swing.JLabel	
	 */
	private JLabel getAboutVersionLabel() {
		if (aboutVersionLabel == null) {
			aboutVersionLabel = new JLabel();
			aboutVersionLabel.setText("Version 0.049a");
			aboutVersionLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return aboutVersionLabel;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getCutMenuItem() {
		if (cutMenuItem == null) {
			cutMenuItem = new JMenuItem();
			cutMenuItem.setText("Cut");
			cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
					Event.CTRL_MASK, true));
		}
		return cutMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getCopyMenuItem() {
		if (copyMenuItem == null) {
			copyMenuItem = new JMenuItem();
			copyMenuItem.setText("Copy");
			copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
					Event.CTRL_MASK, true));
		}
		return copyMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getPasteMenuItem() {
		if (pasteMenuItem == null) {
			pasteMenuItem = new JMenuItem();
			pasteMenuItem.setText("Paste");
			pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
					Event.CTRL_MASK, true));
		}
		return pasteMenuItem;
	}

	/**
	 * This method initializes jMenuItem	
	 * 	
	 * @return javax.swing.JMenuItem	
	 */
	private JMenuItem getSaveMenuItem() {
		if (saveMenuItem == null) {
			saveMenuItem = new JMenuItem();
			saveMenuItem.setText("Save");
			saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					Event.CTRL_MASK, true));
		}
		return saveMenuItem;
	}
	private class AutoProcessTestFiles implements Runnable{
	private String FileToProcess = null;
	private long Sleep;	
	public AutoProcessTestFiles(String File, long sleep){
		this.FileToProcess = File;
		this.Sleep = sleep;
	}
	@Override
	public void run() {		// TODO Auto-generated method stub
		try {
			//this.wait(this.Sleep);
			Thread.sleep(this.Sleep);
			App element = new App(this.FileToProcess);
			analyzingObjects.add(element);
			}catch (Exception e) {
				logger.trace("Cannot Create New Thread");
			}
	}

}
}

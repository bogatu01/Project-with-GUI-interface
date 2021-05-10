import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class MyFrame extends JFrame implements ActionListener{
private int state;
private JPanel pnl;
private Border bdr;
private Color[] clr=new Color[3];
private JButton btn;
private JMenu content,view,program;
private ImageIcon[] imgs;
private String[] engNames={"Charm","Bulbas","Squi","Pica"};
private String[] cyrNames={"Charmander","Bulbasaur","Squirtle","Picachiu"};
private String[] files;
private JLabel lbl;
private JMenuBar mb;
private JMenuItem[] animals;
private JMenuItem about,exit;
private JTextPane tp;
private JCheckBoxMenuItem color;
private JRadioButtonMenuItem light,dark,ordinary;
private JToolBar tb;
private MyButton exitBtn,nextBtn,prevBtn,startBtn;
private JPopupMenu pm;
class MyButton extends JButton{
	MyButton(String txt){
		super(new ImageIcon("C:/Users/bogat/Documents/meniu i paneli upravlenia/"+txt));
		setFocusPainted(false);
	}
}
private void setContent(){
	lbl.setIcon(imgs[state]);
	try{
		tp.setPage(files[state]);
	}catch(IOException err){
		tp.setText("Informatia nedostupna");
	}
}

public void actionPerformed(ActionEvent e){
	state=Integer.parseInt(((JMenuItem)e.getSource()).getActionCommand());
	setContent();
}
MyFrame(){
	super("Okno s panelimi");
	setBounds(250,250,500,500);
	setResizable(false);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setLayout(new BorderLayout());
	state=0;
	bdr=BorderFactory.createEtchedBorder();
	clr[0]=getBackground();
	clr[1]=Color.WHITE;
	clr[2]=Color.DARK_GRAY;
	imgs=new ImageIcon[engNames.length];
	files=new String[engNames.length];
	animals=new JMenuItem[cyrNames.length];
	for(int k=0;k<engNames.length;k++){
		imgs[k]=new ImageIcon("C:/Users/bogat/Documents/meniu i paneli upravlenia/"+engNames[k]+".jpg");
		files[k]="file:///C:/Users/bogat/Documents/meniu i paneli upravlenia/"+cyrNames[k]+".txt";
	}
	tb=new JToolBar("Paneli meniu");
	exitBtn=new MyButton("exit.png");
    exitBtn.setToolTipText("Zaversenie raboti");
	startBtn=new MyButton("home.png");
	startBtn.setToolTipText("Nacealinoe izobrajenie");
	prevBtn=new MyButton("prev.png");
	prevBtn.setToolTipText("Predidusie izobrajenie");
    nextBtn= new MyButton("next.png");
    nextBtn.setToolTipText("Sledusie izobrajenie");
    tb.add(exitBtn);
    tb.add(startBtn);
    tb.add(prevBtn);
    tb.add(nextBtn);
    add(tb,BorderLayout.NORTH);
    pnl=new JPanel();
    pnl.setBorder(bdr);
    pnl.setLayout(new GridLayout(1,2));
    lbl=new JLabel();
    lbl.setHorizontalAlignment(JLabel.CENTER);
    pnl.add(lbl);
    add(pnl,BorderLayout.CENTER);
    JScrollPane sp=new JScrollPane();
    tp=new JTextPane();
    tp.setEditable(false);
    sp.getViewport().add(tp);
    pnl.add(sp);
    btn=new JButton("Ok");
    btn.setFocusPainted(false);
    JPanel p=new JPanel();
    p.setLayout(new GridLayout(1,3));
    p.setBorder(bdr);
    p.add(new JPanel());
    p.add(btn);
    p.add(new JPanel());
    add(p,BorderLayout.SOUTH);
    mb=new JMenuBar();
    content=new JMenu("Soderjanie");
    view=new JMenu("Vid");
    program=new JMenu("Programa");
    about=new JMenuItem("O programe");
    exit=new JMenuItem("Vihod",exitBtn.getIcon());
    program.add(about);
    program.addSeparator();
    program.add(exit);
    color=new JCheckBoxMenuItem("Tvet paneli",true);
    light=new JRadioButtonMenuItem("Svetlii",false);
    dark=new JRadioButtonMenuItem("Teomnii",false);
    ordinary=new JRadioButtonMenuItem("Abicinii",true);
    ButtonGroup bg=new ButtonGroup();
    bg.add(ordinary);
    bg.add(light);
    bg.add(dark);
    view.add(color);
    view.addSeparator();
    view.add(ordinary);
    view.add(light);
    view.add(dark);
    for(int k=0;k<animals.length;k++){
    	animals[k]=new JMenuItem(cyrNames[k]);
    	animals[k].setActionCommand(""+k);
    	animals[k].addActionListener(this);
    	content.add(animals[k]);
    }
    mb.add(content);
    mb.add(view);
    mb.add(program);
    setJMenuBar(mb);
    pm=new JPopupMenu();
    for(int k=0;k<cyrNames.length;k++){
    	pm.add(new JMenuItem(cyrNames[k])).setActionCommand(""+k);
    	((JMenuItem)pm.getComponent(k)).addActionListener(this);
    }
    pm.addSeparator();
    pm.add(new JMenuItem("Vihod",exitBtn.getIcon())).addActionListener(e->exitBtn.doClick());
    lbl.setComponentPopupMenu(pm);
    btn.addActionListener(e->System.exit(0));
    exitBtn.addActionListener(btn.getActionListeners()[0]);
    nextBtn.addActionListener(e->{
    	state=(state+1)%(engNames.length);
    	setContent();
    	
    });
    prevBtn.addActionListener(e->{
    	state=state==0?engNames.length-1:(state-1);
    	setContent();
    });
    startBtn.addActionListener(e->{
    	state=0;
    	setContent();
    });
    exit.addActionListener(e->{state=0;
    setContent();
    });
    
    exit.addActionListener(exitBtn.getActionListeners()[0]);
    about.addActionListener(e->{
    	JOptionPane.showMessageDialog(this, "V programe ispolizuetsa paneli meniu\n i paneli instrumentov.",
    			"A pragrame",JOptionPane.INFORMATION_MESSAGE
    			);
    });
    
    lbl.addMouseListener(new MouseAdapter(){
    	public void mousePressed(MouseEvent e){
    		if(e.isPopupTrigger()){
    			pm.show(e.getComponent(),e.getX(),e.getY());
    		}
    	}
    });
    color.addActionListener(e->{
    	if(color.isSelected()){
    		ordinary.setEnabled(true);
    		light.setEnabled(true);
    		dark.setEnabled(true);
    	}
    	else{
    		ordinary.setEnabled(false);
    		light.setEnabled(false);
    		dark.setEnabled(false);
    	}
    });
    ordinary.addActionListener(e->pnl.setBackground(clr[0]));
    light.addActionListener(e->pnl.setBackground(clr[1]));
    dark.addActionListener(e->pnl.setBackground(clr[2]));
    
    setContent();
    setVisible(true);
    
    
}

}

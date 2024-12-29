package a06.e2;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GUI extends JFrame {
    
    private final Map<Pair<Integer,Integer>,JButton> cells = new HashMap<>();
    
    public GUI(int size) {
        Logics logic = new LogicsImpl(size);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);
        
        JPanel main = new JPanel(new BorderLayout());
        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(main);
        main.add(BorderLayout.CENTER, panel);
        JButton fire = new JButton("Fire");
        main.add(BorderLayout.SOUTH, fire);
        
        ActionListener al = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(logic.quit()){
                    System.exit(0);
                }
                Map<Pair<Integer,Integer>,Integer> map = logic.advance();
                map.entrySet().forEach(entry -> {
                    String s;
                    if(entry.getValue() < 0){
                       s = "";
                    }
                    else{
                        s = entry.getValue().toString();
                    }
                    cells.get(entry.getKey()).setText(s);
                });
        	    //var button = (JButton)e.getSource();
        	    //var position = cells.indexOf(button);
            }
        };

        fire.addActionListener(al);
                
        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(logic.init(new Pair<Integer,Integer>(i, j)));
                this.cells.put(new Pair<Integer,Integer>(i, j), jb);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }    
}

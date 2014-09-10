import heart.Grammar;
import heart.GrammarParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Created by dpjar_000 on 2014-06-13.
 */
public class GUI extends JFrame {


    JTabbedPane tabbedPane;
    JPanel panel1;
    JTextArea grammarIn;
    JTextArea grammarOut;
    Button simulate_button;

    JPanel panel2;
    JTextArea gotoOut;
    JTextArea followOut;
    JPanel panel3;
    JPanel panel4;

    JTextField wordIn;
    JTextArea simulatedOut;
    Button parse_button;

    Grammar grammar;

    public ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == parse_button) {
                System.out.println("Starting generate parsing algorithm...");
                /*
                Start algorithm of  generate parsing rules
                 */

                String text = grammarIn.getText();
                try {

                    GrammarParser gp = new GrammarParser();
                    grammar = gp.parse(text);
                    System.out.println("Getting grammar from textArea...");
                    grammarOut.append(grammar.toString());

                } catch (Exception exc) {
                    System.out.println(exc.getStackTrace());
                }

            } else if (e.getSource() == simulate_button) {
                System.out.println("Starting simulating algorithm...");
                /*
                Start Algorithm of simulate parsing a word
                 */

            }
        }
    };

    //JFrame fram1e;
    public GUI() {
        //  frame = new JFrame();1
        super("SLR(1) Parsing Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 500);
        setLocation(100, 100);

        tabbedPane = new JTabbedPane();
        panel1 = new JPanel();
        panel2 = new JPanel();
        panel3 = new JPanel();
        panel4 = new JPanel();
        tabbedPane.setTabPlacement(SwingConstants.TOP);
        add(tabbedPane);
        tabbedPane.addTab("Start", panel1);
        tabbedPane.addTab("Set of GOTO and FOLLOW(1)", panel2);
        tabbedPane.addTab("Parsing table", panel3);
        tabbedPane.addTab("Simulate parsing", panel4);

        panel1.setLayout(new BorderLayout(10,10));
        //panel1.add(new JLabel("Welcome in our SLR(1) Parsing Simulator!"), BorderLayout.NORTH);
        JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayout(2,1));
        panel11.add(new JLabel("Welcome in our SLR(1) Parsing Simulator!", SwingConstants.CENTER));
        panel11.add(new JLabel("Please enter the grammar below (left text area) and click 'Generate Parsing!' button.", SwingConstants.CENTER));

        panel1.add(panel11, BorderLayout.PAGE_START);
        JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayout(1,2,10,10));
        panel10.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        grammarIn = new JTextArea();
        grammarIn.setVisible(true);
        panel10.add(grammarIn);
        grammarOut = new JTextArea();
        grammarOut.setVisible(true);
        grammarOut.setEditable(false);
        panel10.add(grammarOut);
        parse_button = new Button("Generate Parsing!");
        parse_button.setSize(250, 150);
        parse_button.addActionListener(al);
        panel1.add(panel10, BorderLayout.CENTER);
        panel1.add(parse_button, BorderLayout.PAGE_END); // what's a stupid border loyout! Resize my button  :(


        gotoOut = new JTextArea();
        gotoOut.setEditable(false);
        gotoOut.setSize(400, 300);
        followOut = new JTextArea();
        followOut.setEditable(false);
        panel2.setLayout(new BorderLayout(10, 10));
        panel2.add(new JLabel("Generated set of GOTO and FOLLOW(1)"), BorderLayout.PAGE_START);
        panel2.add(gotoOut, BorderLayout.CENTER);
        panel2.add(followOut, BorderLayout.LINE_END);

        // parsing table - how represent it?

        wordIn = new JTextField();
        simulatedOut = new JTextArea();
        simulatedOut.setEditable(false);
        simulate_button = new Button("Simulate parsing!");
        simulate_button.addActionListener(al);

        panel4.setLayout(new BorderLayout(10, 10));
        panel4.add(new JLabel("Write word in below and click 'Simulate parsing!' button"), BorderLayout.PAGE_START);
        JPanel panel40 = new JPanel();
        panel40.setLayout(new BorderLayout(10, 10));
        JPanel panel41 = new JPanel();
        panel41.setLayout(new GridLayout(1, 2));
        panel41.add(wordIn);
        panel41.add(simulate_button);
        panel40.add(panel41, BorderLayout.PAGE_START);
        panel40.add(simulatedOut, BorderLayout.CENTER);
        panel4.add(panel40, BorderLayout.CENTER);


        setVisible(true);
    }

}
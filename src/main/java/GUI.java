import heart.Grammar;
import heart.GrammarParser;
import heart.ParserArray;
import heart.ParserGenerator;
import heart.DFAState;
import heart.symulator.ParseStep;
import heart.symulator.ParserSymulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;

/**
 * Created by dpjar_000 on 2014-06-13.
 */
public class GUI extends JFrame {


    JTabbedPane tabbedPane;
    JPanel panel1;
    JTextArea grammarIn;
    JTextArea grammarOut;


    Button parse_button;
    Button clear_button;
    Button simulate_button;

    JPanel panel2;
    JTextArea gotoOut;
    JTextArea followOut;
    JTextArea firstOut;
    JPanel panel3;
    JPanel panel4;

    JTextField wordIn;
    JTextArea simulatedOut;


    Grammar grammar;
    ParserArray parserArray;

    private void clear() {
        grammarOut.setText("");
        gotoOut.setText("");
        followOut.setText("");
        firstOut.setText("");
        gotoOut.setText("");
    }
    public ActionListener al = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == parse_button) {
                System.out.println("Clearing...");
                clear();
                System.out.println("Starting generate parsing algorithm...");
                /*
                Start algorithm of  generate parsing rules
                 */

                String text = grammarIn.getText();
                try {

                    GrammarParser gp = new GrammarParser();
                    grammar = gp.parse(text);
                    System.out.println("Getting grammar from textArea...");
                    grammarOut.setText(grammar.toString());

                } catch (Exception exc) {
                    System.out.println(exc.getStackTrace());
                }

                ParserGenerator pg = new ParserGenerator(grammar);
                System.out.println("Displaying set of FIRST...");
                firstOut.setText(pg.firstToString());
                System.out.println("Displaying set of FOLLOW(1)...");
                followOut.setText(pg.followToString());

                gotoOut.setText(pg.DFAStatesToString(grammar));

                // parsing array
                System.out.println("Displaying parsing array...");
                Set<DFAState> dfaStateSet = pg.computeDFAStates(grammar);
                parserArray = pg.generateParserArrayAnother(new ArrayList<DFAState>(dfaStateSet));

                int column_size = grammar.getTerminals().size() + grammar.getNonterminals().size() + 2;
                String []columnNames = new String[column_size]; //  one column is empty, one is $

                MyTableModel mtm = new MyTableModel(column_size, dfaStateSet.size());
                System.out.println("Column size " + column_size + "States size " + dfaStateSet.size() + grammar.getNonterminals() + grammar.getTerminals());

                columnNames[0] = " ";
                int i = 1;
                for (String terminal : grammar.getTerminals()) {
                    columnNames[i] = terminal;
                    i++;
                }
                columnNames[i] = "$";
                i++;

                for (String nonterminal : grammar.getNonterminals() ) {
                    columnNames[i] = nonterminal;
                    i++;
                }
                mtm.setColumnNames(columnNames);
                mtm.setData(parserArray);

                JTable table = new JTable(mtm);

                JScrollPane scrollPane = new JScrollPane(table);

                if (parserArray.conflictExist()) {
                    System.out.println("Conflict exist...");
                    panel3.add(new JLabel("Conflict exist. It is not SLR(1) grammar.", SwingConstants.HORIZONTAL), BorderLayout.PAGE_END);
                }

                panel3.add(scrollPane, BorderLayout.CENTER);


            } else if ( e.getSource() == clear_button) {
                grammarIn.setText("");
                clear();

            } else if (e.getSource() == simulate_button) {
                System.out.println("Starting simulating algorithm...");

                if (grammar == null) {
                    System.out.println("There's no grammar in...");
                    panel4.add(new JLabel("There's no grammar in.", SwingConstants.HORIZONTAL), BorderLayout.PAGE_END);
                } else {
                    ParserSymulator parserSymulator = new ParserSymulator(grammar,parserArray);
                    String word = wordIn.getText();
                    System.out.println(word);
                    try {
                        List<ParseStep> parseStepList = parserSymulator.parse(word);
                        System.out.println(parseStepList.size());

                        MyTableModel mtm_simulate = new MyTableModel(3, parseStepList.size());
                        String[] columnNames_sim ={ "Stack", "Input", "Production"};
                        mtm_simulate.setColumnNames(columnNames_sim);
                        mtm_simulate.setData(parseStepList);
                        JTable table_sim = new JTable(mtm_simulate);

                        JScrollPane scrollPane_sim = new JScrollPane(table_sim);

                        panel4.add(scrollPane_sim, BorderLayout.CENTER);
                    } catch (NullPointerException exc) {
                        panel4.add(new JLabel("Word cannot by parsed", SwingConstants.HORIZONTAL), BorderLayout.PAGE_END);
                    }


                }

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
        JPanel panel11 = new JPanel();
        panel11.setLayout(new GridLayout(2,1));
        panel11.add(new JLabel("Welcome in our SLR(1) Parsing Simulator!", SwingConstants.CENTER));
        panel11.add(new JLabel("Please enter the grammar below (left text area) and click 'Generate Parsing!' button.", SwingConstants.CENTER));

        panel1.add(panel11, BorderLayout.PAGE_START);
        JPanel panel10 = new JPanel();
        panel10.setLayout(new GridLayout(1, 2, 10, 10));
        panel10.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        grammarIn = new JTextArea();
        grammarIn.setVisible(true);
        panel10.add(grammarIn);
        grammarOut = new JTextArea();
        grammarOut.setVisible(true);
        grammarOut.setEditable(false);
        panel10.add(grammarOut);
        panel1.add(panel10, BorderLayout.CENTER);

        parse_button = new Button("Generate Parsing!");
        parse_button.setSize(250, 150);
        parse_button.addActionListener(al);

        clear_button = new Button("Clear");
        clear_button.addActionListener(al);
        JPanel panel12 = new JPanel();
        panel12.add(parse_button);
        panel12.add(clear_button);
        panel1.add(panel12, BorderLayout.PAGE_END); // what's a stupid border loyout! Resize my button  :(


        gotoOut = new JTextArea();
        gotoOut.setEditable(false);
        gotoOut.setSize(400, 300);
        followOut = new JTextArea();
        followOut.setEditable(false);
        firstOut = new JTextArea();
        firstOut.setEditable(false);

        panel2.setLayout(new BorderLayout(10, 10));
        panel2.add(new JLabel("Generated set of GOTO, FIRST and FOLLOW(1)", SwingConstants.CENTER), BorderLayout.PAGE_START);
        JPanel panel20 = new JPanel();
        panel20.setLayout(new GridLayout(1, 2, 10, 10));
        panel20.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JPanel panel20left = new JPanel();
        panel20left.setLayout(new BorderLayout(5,5));
        panel20left.add(new JLabel("Set of GOTO", SwingConstants.HORIZONTAL), BorderLayout.PAGE_START);
        panel20left.add(gotoOut, BorderLayout.CENTER);

        JPanel panel20right = new JPanel();
        panel20right.setLayout(new GridLayout(2,1,5,5));

        JPanel panel20right_up = new JPanel();
        panel20right_up.setLayout(new BorderLayout(5,5));
        panel20right_up.add(new JLabel("Set of FOLLOW(1)", SwingConstants.HORIZONTAL), BorderLayout.PAGE_START);
        panel20right_up.add(followOut, BorderLayout.CENTER);

        JPanel panel20right_down = new JPanel();
        panel20right_down.setLayout(new BorderLayout(5,5));
        panel20right_down.add(new JLabel("Set of FIRST", SwingConstants.HORIZONTAL), BorderLayout.PAGE_START);
        panel20right_down.add(firstOut, BorderLayout.CENTER);

        panel20right.add(panel20right_up);
        panel20right.add(panel20right_down);

        panel20.add(panel20left);
        panel20.add(panel20right);

        panel2.add(panel20,BorderLayout.CENTER);


        // parsing table - how represent it?
        panel3.setLayout(new BorderLayout(10,10));
        panel3.add(new JLabel("Parsing table", SwingConstants.CENTER), BorderLayout.PAGE_START);


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
        //panel40.add(simulatedOut, BorderLayout.CENTER);
        panel4.add(panel40, BorderLayout.CENTER);


        setVisible(true);
    }

}
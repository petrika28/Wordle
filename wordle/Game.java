import java.util.ArrayList;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.*;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Game extends JFrame implements KeyListener {
    int rreshti = 0;
    int kolona = 0;
    int piket = 0;
    boolean hint = false;
    int randkol = -1;

    JLabel hyrje = new JLabel("Gjeni fjalen");
    JLabel[][] shkronjat = new JLabel[5][5];
    JPanel mbajtesi = new JPanel();
    JPanel hyrje1 = new JPanel();
    JPanel panelikryesor = new JPanel();
    JButton konfirmo = new JButton("Konfirmo");
    JButton fshi = new JButton("Fshi");
    JButton hintButton = new JButton("Hint");
    JPanel butonat = new JPanel();
    Fjalori fjalori = new Fjalori();
    ArrayList<Fjalori> grFjalesh = new ArrayList<>();
    JButton restart = new JButton("Restart");

    public Game() {
        lexoSkedar();
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
        konfirmo.addActionListener(new KonfirmoOnClick());
        fshi.addActionListener(new FshiOnClick());
        restart.addActionListener(new RestartOnClick());
        hintButton.addActionListener(new HintOnClick());
        setFocusable(true);
        requestFocusInWindow(true);
        butonat.add(konfirmo);
        mbajtesi.setLayout(new GridLayout(5, 5));
        fjalori.setFjalaEsakte(gjenero());
        mbush();
        panelikryesor.add(mbajtesi);
        mbajtesi.setPreferredSize(new Dimension(300, 300));
        panelikryesor.setLayout(new FlowLayout());
        panelikryesor.add(konfirmo);
        panelikryesor.add(fshi);
        panelikryesor.add(restart);
        panelikryesor.add(hintButton);
        add(panelikryesor);
        setSize(500, 500);
        setTitle("Gjej fjalen");
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Popup instruksione = new Popup(
                "<html>-Ju duhet te gjeni fjalen.<br>-Nese fjala juaj ka shkronja te perbashketa ne pozicione te ndryshme ato behen te verdha<br>"
                        + "-Nese pozicionet dhe shkronjat perputhen ato behen jeshile. <br>-Ne te kundert do behen te kuqe<html>"
                        + "-Ju keni nje hint qe tregon nje shkronje ne pozicionin e duhur<br>" +
                        "-Perdoret vetem nje here");

    }

    private String gjenero() {
        int ran = (int) (Math.random() * grFjalesh.size());
        return grFjalesh.get(ran).getFjalaEsakte();
    }

    public void mbush() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                shkronjat[i][j] = new JLabel("");
                shkronjat[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                shkronjat[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                mbajtesi.add(shkronjat[i][j]);
            }
        }
    }

    class KonfirmoOnClick implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            GameController controller = new GameController();

            if (controller.checkColsFilled(kolona)) {
                piket = controller.kontrollo(piket, fjalori, shkronjat, rreshti);
                if (controller.checkWinningPoints(piket)) {
                    Popup pop = new Popup("E gjete fjalen e sakte");
                    setFocusable(false);
                } else {
                    rreshti++;
                    kolona = 0;
                }
            } else if (!controller.checkColsFilled(kolona) && rreshti < 5) {
                Popup pop = new Popup("Fjala duhet te kete 5 shkronja");

            }

            if (controller.checkLose(piket, rreshti)) {
                Popup popi = new Popup("Fjala ishte" + " " + fjalori.getFjalaEsakte());
            }
            randkol = -1;
            requestFocusInWindow();
            randkol = -1;
            requestFocusInWindow();

        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Set<Integer> excludedKeys = Set.of(
                KeyEvent.VK_CAPS_LOCK,
                KeyEvent.VK_BACK_SPACE,
                KeyEvent.VK_WINDOWS,
                KeyEvent.VK_SHIFT,
                KeyEvent.VK_ALT);

        String shkronjaEvendosur = KeyEvent.getKeyText(e.getKeyCode());
        if (Character.isLetter(shkronjaEvendosur.charAt(0))) {

            if (!excludedKeys.contains(e.getKeyCode())) {

                if (kolona != randkol) {

                    if (kolona < 5) {
                        shkronjat[rreshti][kolona].setText(shkronjaEvendosur);
                        kolona++;

                    }

                }

                else {
                    kolona++;
                }

            }

        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    class FshiOnClick implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (piket != 5 && kolona != 0) {
                kolona--;
                if (kolona == randkol) {
                    kolona--;
                }
                shkronjat[rreshti][kolona].setText("");
            }
            requestFocusInWindow();
        }

    }

    class RestartOnClick implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            GameController controller = new GameController();
            controller.setEmptyLabel(shkronjat);
            rreshti = 0;
            kolona = 0;
            hint = false;
            randkol = -1;
            fjalori.setFjalaEsakte(gjenero());
            setFocusable(true);
            requestFocusInWindow();
        }

    }

    public class HintOnClick implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            randkol = (int) (Math.random() * 5);
            GameController controller = new GameController();
            controller.shfaqHint(hint, shkronjat, rreshti, randkol, fjalori);
            hint = true;
            requestFocusInWindow();
        }
    }

    public void lexoSkedar() {
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\User\\Desktop\\fjalori.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Fjalori fjalori = new Fjalori();
                fjalori.setFjalaEsakte(line);
                grFjalesh.add(fjalori);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

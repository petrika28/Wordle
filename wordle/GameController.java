import javax.swing.JLabel;
import java.awt.Color;

public class GameController {
    public void shfaqHint(boolean hint, JLabel[][] shkronjat, int rreshti, int randkol, Fjalori fjalori) {
        if (hint == false) {
            shkronjat[rreshti][randkol]
                    .setText(Character.toString(fjalori.getFjalaEsakte().charAt(randkol)).toUpperCase());
            shkronjat[rreshti][randkol].setForeground(Color.GREEN);
        }

    }

    public int kontrollo(int piket, Fjalori fjalori, JLabel shkronjat[][], int rreshti) {
        piket = 0;
        for (int i = 0; i < 5; i++) {
            if (fjalori.getFjalaEsakte().toUpperCase().charAt(i) == shkronjat[rreshti][i].getText().charAt(0)) {
                shkronjat[rreshti][i].setForeground(Color.GREEN);
                piket++;
            } else if (fjalori.getFjalaEsakte().toUpperCase().indexOf(shkronjat[rreshti][i].getText()) != i
                    && fjalori.getFjalaEsakte().toUpperCase().contains(shkronjat[rreshti][i].getText())) {
                shkronjat[rreshti][i].setForeground(Color.YELLOW);
            } else if (fjalori.getFjalaEsakte().toUpperCase().contains(shkronjat[rreshti][i].getText()) == false) {
                shkronjat[rreshti][i].setForeground(Color.RED);
            }
        }
        return piket;
    }

    public void setEmptyLabel(JLabel shkronjat[][]) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                shkronjat[i][j].setText("");
                shkronjat[i][j].setForeground(Color.BLACK);
            }
        }
    }

    public boolean checkColsFilled(int col){
        if (col==5)
        return true;
        return false;
    }

    public boolean checkWinningPoints(int piket){
        if (piket==5)
        return true;
        return false;
    }
    public boolean checkLose(int piket,int rreshti){
        if (piket!=5&&rreshti==5)
        return true;
        return false;
    }

}

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class Popup extends JFrame {
    JLabel teksti=new JLabel();

    public Popup(String text){
    teksti.setText(text);
    setSize(300,300);
    setTitle("Instruksione");
    setVisible(true);
    add(teksti);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}

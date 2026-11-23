/*
 * Trabalho Prático 5 - LP2 (Java) - ADS 471
 * IFSP Campus Cubatão - Prof. Wellington Tuler Moraes
 *
 * Autor: Jefferson Nascimento
 */
package tp03;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Janela principal do programa. Só guarda o painel das threads.
 *
 * @author Jefferson Nascimento
 */
public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        setTitle("Trabalho Prático 5");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new PainelThreads());
        pack();
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }
}

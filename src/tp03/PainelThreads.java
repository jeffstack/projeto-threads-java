/*
 * Trabalho Prático 5 - LP2 (Java) - ADS 471
 * IFSP Campus Cubatão - Prof. Wellington Tuler Moraes
 *
 * Autor: Jefferson Nascimento
 */
package tp03;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * Painel com o formulário do trabalho. Ele implementa Runnable, então
 * as duas threads usam o método run() deste mesmo painel.
 *
 * @author Jefferson Nascimento
 */
public class PainelThreads extends JPanel implements Runnable {

    private JLabel labelMaximo;
    private JLabel labelPrioridade1;
    private JLabel labelPrioridade2;
    private JLabel labelChave;
    private JLabel labelObservacao;
    private JLabel labelFim1;
    private JLabel labelFim2;

    private JTextField campoMaximo;
    private JTextField campoPrioridade1;
    private JTextField campoPrioridade2;

    private JButton botaoIniciar;
    private JButton botaoLimpar;

    private DefaultListModel<String> modelo1;
    private DefaultListModel<String> modelo2;
    private JList<String> lista1;
    private JList<String> lista2;
    private JScrollPane scroll1;
    private JScrollPane scroll2;

    private Thread thread1;
    private Thread thread2;
    private int maximo;

    public PainelThreads() {
        setLayout(null);
        setPreferredSize(new Dimension(660, 420));
        criarComponentes();
    }

    private void criarComponentes() {
        labelMaximo = new JLabel("Máximo:");
        labelMaximo.setBounds(50, 14, 90, 20);
        add(labelMaximo);

        campoMaximo = new JTextField("1000");
        campoMaximo.setBounds(150, 12, 165, 24);
        add(campoMaximo);

        labelPrioridade1 = new JLabel("Prioridade 1:");
        labelPrioridade1.setBounds(50, 48, 90, 20);
        add(labelPrioridade1);

        campoPrioridade1 = new JTextField("1");
        campoPrioridade1.setBounds(150, 46, 165, 24);
        add(campoPrioridade1);

        labelPrioridade2 = new JLabel("Prioridade 2:");
        labelPrioridade2.setBounds(50, 82, 90, 20);
        add(labelPrioridade2);

        campoPrioridade2 = new JTextField("10");
        campoPrioridade2.setBounds(150, 80, 165, 24);
        add(campoPrioridade2);

        labelChave = new JLabel("}");
        labelChave.setFont(new Font("Serif", Font.PLAIN, 64));
        labelChave.setBounds(320, 30, 50, 90);
        add(labelChave);

        labelObservacao = new JLabel("Número entre 1 e 10");
        labelObservacao.setBounds(375, 66, 150, 20);
        add(labelObservacao);

        botaoIniciar = new JButton("Iniciar");
        botaoIniciar.setBounds(290, 115, 80, 25);
        botaoIniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciar();
            }
        });
        add(botaoIniciar);

        modelo1 = new DefaultListModel<String>();
        lista1 = new JList<String>(modelo1);
        scroll1 = new JScrollPane(lista1);
        scroll1.setBounds(135, 160, 95, 130);
        add(scroll1);

        modelo2 = new DefaultListModel<String>();
        lista2 = new JList<String>(modelo2);
        scroll2 = new JScrollPane(lista2);
        scroll2.setBounds(445, 160, 95, 130);
        add(scroll2);

        labelFim1 = new JLabel("Final");
        labelFim1.setBounds(145, 300, 80, 20);
        labelFim1.setVisible(false);
        add(labelFim1);

        labelFim2 = new JLabel("Final");
        labelFim2.setBounds(455, 300, 80, 20);
        labelFim2.setVisible(false);
        add(labelFim2);

        botaoLimpar = new JButton("Limpar");
        botaoLimpar.setBounds(290, 370, 80, 25);
        botaoLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpar();
            }
        });
        add(botaoLimpar);
    }

    // Lê os campos, confere as prioridades e dispara as duas threads
    private void iniciar() {
        int prioridade1;
        int prioridade2;
        int valorMaximo;

        try {
            valorMaximo = Integer.parseInt(campoMaximo.getText().trim());
            prioridade1 = Integer.parseInt(campoPrioridade1.getText().trim());
            prioridade2 = Integer.parseInt(campoPrioridade2.getText().trim());
        } catch (NumberFormatException erro) {
            JOptionPane.showMessageDialog(this, "Digite apenas números inteiros nos campos.");
            return;
        }

        if (prioridade1 < 1 || prioridade1 > 10 || prioridade2 < 1 || prioridade2 > 10) {
            JOptionPane.showMessageDialog(this, "As prioridades devem ser números entre 1 e 10.");
            return;
        }

        maximo = valorMaximo;

        thread1 = new Thread(this, "Thread 1");
        thread2 = new Thread(this, "Thread 2");
        thread1.setPriority(prioridade1);
        thread2.setPriority(prioridade2);
        thread1.start();
        thread2.start();
    }

    // Esvazia as listas e esconde os labels Final
    private void limpar() {
        modelo1.clear();
        modelo2.clear();
        labelFim1.setVisible(false);
        labelFim2.setVisible(false);
    }

    // Executado pelas duas threads: cada uma conta e preenche a sua lista
    @Override
    public void run() {
        final DefaultListModel<String> modelo;
        final JList<String> lista;
        final JLabel labelFim;

        if (Thread.currentThread().getName().equals("Thread 1")) {
            modelo = modelo1;
            lista = lista1;
            labelFim = labelFim1;
        } else {
            modelo = modelo2;
            lista = lista2;
            labelFim = labelFim2;
        }

        int limite = maximo;
        for (int i = 0; i < limite; i++) {
            final int valor = i;
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    modelo.addElement(String.valueOf(valor));
                }
            });
        }

        // Só depois de todos os valores: mostra o Final e vai para o fim da lista
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                labelFim.setVisible(true);
                lista.ensureIndexIsVisible(modelo.getSize() - 1);
            }
        });
    }
}

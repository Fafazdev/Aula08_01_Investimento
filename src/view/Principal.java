package view;

import javax.swing.SwingUtilities;

public class Principal {
    // Rafael Durval, Thamiris ferreira
    private static final String ALUNOS = "Nomes dos alunos";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JanelaInvestimento().setVisible(true));
    }
}

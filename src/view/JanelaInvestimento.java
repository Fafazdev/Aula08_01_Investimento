package view;

import business.Aplicacao;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.NumberFormat;
import java.util.Locale;

public class JanelaInvestimento extends JFrame {
    private final JTextField campoValor;
    private final JTextField campoPrazo;
    private final JComboBox<String> comboTaxa;
    private final JLabel labelResultado;
    private final Aplicacao aplicacao;

    public JanelaInvestimento() {
        super("Aula08_01_Investimento");
        aplicacao = new Aplicacao();
        campoValor = new JTextField(12);
        campoPrazo = new JTextField(12);
        comboTaxa = new JComboBox<>(new String[]{"Poupança", "CDI", "Tesouro Direto"});
        labelResultado = new JLabel("Montante: informe os dados e calcule");

        configurarCamposNumericos();
        configurarJanela();
    }

    private void configurarCamposNumericos() {
        campoValor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evento) {
                permitirNumeroDecimal(evento, campoValor);
            }
        });

        campoPrazo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evento) {
                if (!Character.isDigit(evento.getKeyChar())) {
                    evento.consume();
                }
            }
        });
    }

    private void permitirNumeroDecimal(KeyEvent evento, JTextField campo) {
        char caractere = evento.getKeyChar();
        String texto = campo.getText();
        boolean digito = Character.isDigit(caractere);
        boolean pontoValido = caractere == '.' && !texto.contains(".");
        boolean controle = Character.isISOControl(caractere);

        if (!digito && !pontoValido && !controle) {
            evento.consume();
        }
    }

    private void configurarJanela() {
        JButton botaoCalcular = new JButton("Calcular Rendimento");
        botaoCalcular.addActionListener(evento -> calcularRendimento());

        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
        GridBagConstraints restricoes = new GridBagConstraints();
        restricoes.insets = new Insets(6, 6, 6, 6);
        restricoes.anchor = GridBagConstraints.WEST;

        adicionarComponente(painel, new JLabel("Valor a ser aplicado:"), restricoes, 0, 0);
        adicionarComponente(painel, campoValor, restricoes, 1, 0);
        adicionarComponente(painel, new JLabel("Prazo da aplicação (meses):"), restricoes, 0, 1);
        adicionarComponente(painel, campoPrazo, restricoes, 1, 1);
        adicionarComponente(painel, new JLabel("Indexador financeiro:"), restricoes, 0, 2);
        adicionarComponente(painel, comboTaxa, restricoes, 1, 2);
        adicionarComponente(painel, botaoCalcular, restricoes, 0, 3);
        restricoes.gridwidth = 2;
        adicionarComponente(painel, labelResultado, restricoes, 0, 4);

        add(painel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setLocationRelativeTo(null);
    }

    private void adicionarComponente(JPanel painel, java.awt.Component componente,
                                     GridBagConstraints restricoes, int coluna, int linha) {
        restricoes.gridx = coluna;
        restricoes.gridy = linha;
        painel.add(componente, restricoes);
    }

    private void calcularRendimento() {
        try {
            float valor = Float.parseFloat(campoValor.getText());
            int prazo = Integer.parseInt(campoPrazo.getText());
            float taxa = obterTaxaSelecionada();

            if (valor <= 0 || prazo <= 0) {
                throw new NumberFormatException();
            }

            aplicacao.calcularRendimento(valor, prazo, taxa);
            NumberFormat moeda = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            labelResultado.setText("Montante: " + moeda.format(aplicacao.getMontante()));
        } catch (NumberFormatException erro) {
            JOptionPane.showMessageDialog(this,
                    "Informe um valor e um prazo válidos.",
                    "Dados inválidos",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    private float obterTaxaSelecionada() {
        return switch (comboTaxa.getSelectedIndex()) {
            case 0 -> 0.38f;
            case 1 -> 0.53f;
            case 2 -> 0.65f;
            default -> throw new IllegalStateException("Indexador inválido");
        };
    }
}

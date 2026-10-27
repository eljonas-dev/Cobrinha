import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    //CONFIGURAÇÕES DA TELA E GRADE
    static final int LARGURA_TELA = 600;
    static final int ALTURA_TELA = 600;
    static final int TAMANHO_BLOCO = 25;
    static final int TOTAL_BLOCOS = (LARGURA_TELA + ALTURA_TELA);
    static final int DELAY = 100; //velocidade do jogo em milissegundos

    //POSIÇÕES DA COBRA E DA MAÇÃ
    final int x[] = new int[TOTAL_BLOCOS]; //Posição x de cada pedaço da cobra
    final int y[] = new int[TOTAL_BLOCOS]; //Posição Y de cada pedaço da cobra
    int corpoCobra = 3; //Tamanho inicial da cobra
    int macasComidas = 0; 
    int macaX; //Posição X da maçã
    int macaY; //Posição Y da maçã

    //DIREÇÃO DO MOVIMENTO
    char direcao = 'D';
    boolean rodando = False;
    Timer timer;
    Random random;

    public GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(LARGURA_TELA, ALTURA_TELA));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(new LeitorDeTeclas());
        iniciarJogo();
    }

    public void iniciarJogo() {
        criarMaca();
        rodando = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        desenhar(g);
    }
    public void desenhar(Graphics g) {
        if (rodando) {
            //Desenhar a maçã
            g.setColor(Color.red);
            g.fillOval(macaX, macaY, TAMANHO_BLOCO, TAMANHO_BLOCO);

            //Desenhar a Cobra
            for (int i = 0; i < corpoCobra; i++) {
                if (i == 0) {
                    //Cabeça da cobra (verde escuro)
                    g.setColor(new Color(34, 139, 34));
                } else {
                    //Corpo da cobra (verde claro)
                    g.setColor(new Color(50, 205, 50));
                }
                g.fillRect(x[i], y[i], TAMANHO_BLOCO, TAMANHO_BLOCO);
            }

            g.setColor(Color.white);
            g.setFont(new Font("Ink Free", Font.BOLD, 25));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Pontos: " + macasComidas, (LARGURA_TELA - metrics.stringWidth("Pontos: " + macasComidas)) / 2, g.getFont().getSize());
        } else {
            FimDeJogo(g);
        }
    }
        public void criarMaca() {
            //Sorteira uma posição X e Y alinhado a grade
            macaX = random.nextInt((int) (LARGURA_TELA / TAMANHO_BLOCO)) * TAMANHO_BLOCO;
            macaY = random.nextInt((int) (ALTURA_TELA / TAMANHO_BLOCO)) * TAMANHO_BLOCO;
        }

        public void mover() {
            //Faz cada pedaço do corpo seguir o pedaço da frente
            for (int i = corpoCobra; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }

            switch (direcao) {
                //Mexe a cabeça de acordo com a direção atual
                case 'C': y[0] = y[0] - TAMANHO_BLOCO; break;
                case 'B': y[0] = y[0] + TAMANHO_BLOCO; break;
                case 'E': x[0] = x[0] - TAMANHO_BLOCO; break;
                case 'D': x[0] = x[0] + TAMANHO_BLOCO; break;
            }
        }

        public void checarMaca() {
            //Se a cabeça estiver na mesma posição da maçã, aumenta o tamanho da cobra e cria uma nova maçã
            if ((x[0] == macaX) && (y[0] == macaY)) {
                corpoCobra++;
                macasComidas++;
                criarMaca();
            }
        }

        public void checarColisoes() {
            //Checa se a cabeça colidiu com o proprio corpo
            for (int i = corpoCobra; i > 0; i--) {
                if ((x[0] == x[i]) && (y[0] == y[i])) {
                    rodando = false;
                }
            }

            if (x[0] < 0 || x[0] >= LARGURA_TELA || y[0] < 0 || y[0]  >=ALTURA_TELA) {
                //Checa se a cabeça tocou nas bordas da tela (Esquerda, direita, cima e baixo)
                rodando = false;
                //Manda o sinal pra interromper o jogo
            }

            if (!rodando) {
                timer.stop(); 
                //Cabou o jogo rapaziada!
            }
        }

        public void fimDeJogo(Graphics g) {
            //Exibe o texto de Game Over
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 75));
            FontMetrics metrics1 = getFontMetrics(g.getFont());
            g.drawString("Fim de Jogo", (LARGURA_TELA - metrics1.stringWidth("Fim de Jogo")) / 2, ALTURA_TELA / 2);
        }
}
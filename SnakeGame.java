import javax.swing.JFrame;

public class SnakeGame extends JFrame {
    public SnakeGame() {
        //Adiciona o painel do jogo na janela
        this.add(new GamePanel());

        //Define o título da janela
        this.setTitle("Jogo da Cobrinha");

        //Garante que o programa encerre ao fechar a janela
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Impede o usuário de redimensionar a janela
        this.setResizable(false);

        //Ajusta o tamanho da janela automaticamente ao tamanho do painel
        this.pack();

        //Centraliza a janela na tela
        this.setLocationRelativeTo(null); 

        //Torna a janela visivel
        this.setVisible(true);
        /*
        preciso nem dizer que todos esses métodos são herdados da classe JFrame, né?
        */
    }
    public static void main(String[] args) {
        new SnakeGame();
    }
}
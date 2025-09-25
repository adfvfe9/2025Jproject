    import java.awt.*;
    import java.awt.event.*;
    import javax.swing.*;

    public class Game extends JFrame {
        static final int SCREENWIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;  // 화면 가로크기 상수
        static final int SCREENHEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;    // 화면 세로크기 상수
        public Game(int players) {
            this.setTitle("Game");	// 제목
            this.setResizable(false);	// 창 크기 고정
            this.setLocationRelativeTo(null);	// 실행 시 창이 가운데
            this.getContentPane().setLayout(null);
            this.setUndecorated(true);  // 위쪽 닫기창 없앰??
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);   // 최대크기

            JPanel panel = new JPanel();
            panel.setLayout(null);  // 레이아웃 설정 없음? -> 좌표, 크기 내가 다 설정해야함 ㅈㄴ 귀찮노
            panel.setFocusable(true);  // 포커스 이놈한테 받을거라고 선언
            panel.setBounds(0, 0, SCREENWIDTH, SCREENHEIGHT); // 크기 설정 (시발 이거때문에 몇시간 날린거야)
            panel.setOpaque(false);
            this.add(panel);    // 프레임에 추가

            JLabel rev = new JLabel(new ImageIcon(Game.class.getResource("/images/revolver.png")));
            rev.setBounds(Game.SCREENWIDTH / 2 - 200, Game.SCREENHEIGHT / 2 - 200, 400, 400);
            panel.add(rev);

            JLabel bg = new JLabel(new ImageIcon(Game.class.getResource("/images/menu.png")));    // images 폴더에 있는 사진 사용
            bg.setBounds(0, 0, Game.SCREENWIDTH, Game.SCREENHEIGHT);    // 걍 컴마다 크기 다르니까 귀찮아서 동적할당함
            panel.add(bg);   // 패널에 추가

            panel.addKeyListener(new KeyAdapter() {
                @Override   // IDE가 걍 알아서 오버라이딩 도와주노 ㄷ
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_F4) {
                        dispose();  // 창 칵투
                    }
                }
            });




            this.setVisible(true);	// 창 보이게하는거 (이놈이 걍 제일 마지막에 있어야함)
            panel.requestFocusInWindow(); // 진짜 포커스 주기 (이놈도 마지막에)
        }
    }


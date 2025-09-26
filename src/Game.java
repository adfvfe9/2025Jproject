    import java.awt.*;
    import java.awt.event.*;
    import java.awt.geom.AffineTransform;
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
            panel.setBounds(0, 0, SCREENWIDTH, SCREENHEIGHT); // 크기 설정 (시발 이거때문에 몇시간 날린거야)  //TODO 얘 크기 동적으로 설정하기
            panel.setOpaque(false); // 투명하게
            this.add(panel);    // 프레임에 추가

            RotateLabel rev = new RotateLabel(new ImageIcon(Game.class.getResource("/images/revolver.png")));
            rev.setBounds(Game.SCREENWIDTH / 2 - 200, Game.SCREENHEIGHT / 2 - 200, 400, 400);
            panel.add(rev);

            JLabel bg = new JLabel(new ImageIcon(Game.class.getResource("/images/menu.png")));    // images 폴더에 있는 사진 사용
            bg.setBounds(0, 0, Game.SCREENWIDTH, Game.SCREENHEIGHT);    // 걍 컴마다 크기 다르니까 귀찮아서 동적할당함
            panel.add(bg);   // 패널에 추가

            panel.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_F4) {
                        dispose();  // 창 칵투
                    }
                }
            });




            this.setVisible(true);	// 창 보이게하는거 (이놈이 걍 제일 마지막에 있어야함)
            panel.requestFocusInWindow(); // 진짜 포커스 주기 (이놈도 마지막에)

            while (true) {
                Point p = MouseInfo.getPointerInfo().getLocation();
                // System.out.println((p.x - Game.SCREENWIDTH / 2) + ", " + -(p.y - Game.SCREENHEIGHT / 2));
                rev.lookPointer(p);
            }
        }
    }
    class RotateLabel extends JLabel {  // 회전시키는놈
        private double angle;   // 각도 변수
        private boolean flip;   // 뒤집힌거 여부
        public RotateLabel(ImageIcon image) {   // 각도 0도 생성
            super(image);
            this.angle = 0;
        }
        public void setAngle(double angle) {
            this.angle = angle;
        }
        public double getAngle() {
            return this.angle;
        }
        protected void paintComponent(Graphics g) { // 그래픽 객체 만들어서 그 객체로 rotate() 메소드 써서 돌리고 다시 그리기 (애초에 super 생성자에 있음)
            Graphics2D gd = (Graphics2D) g.create();    // g 객체 만들어서? 아들한테 쑤셔넣기
            int rotateCenterX = this.getWidth() / 2;    // 이 두개는 paintComponent 안에서 불러야함
            int rotateCenterY = this.getHeight() / 2;

            gd.translate(rotateCenterX, rotateCenterY);     // 회전 중심점 설정 (사진 정중앙)

            if (flip) { // 뒤집힌거 여부 보고 x축방향 뒤집기
                gd.scale(-1, 1);
            }

            gd.rotate(Math.toRadians(this.angle));    // 설정한 회전각으로 회전

            gd.translate(-rotateCenterX, -rotateCenterY);   // 회전 중심정 재설정

            super.paintComponent(gd);
            gd.dispose();   // 객체 삭제? 비스무리한거
        }
        public void lookPointer(Point p) {
            this.flip = p.x - Game.SCREENWIDTH / 2 < 0;
            this.setAngle(Math.toDegrees(-Math.atan2((p.x - Game.SCREENWIDTH / 2 < 0) ? (p.x - Game.SCREENWIDTH / 2) : -(p.x - Game.SCREENWIDTH / 2), -(p.y - Game.SCREENHEIGHT / 2))) - 75 - Math.sqrt(Math.hypot(p.x - Game.SCREENWIDTH / 2, -(p.x - Game.SCREENWIDTH / 2)) / 10));
            repaint();
        }
    }


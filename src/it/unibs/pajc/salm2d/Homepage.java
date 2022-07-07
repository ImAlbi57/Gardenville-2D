package it.unibs.pajc.salm2d;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Objects;

public class Homepage extends JFrame {

    public static final String MSG_NOME = "Inserisci qui il tuo username";
    public static final String ERRORE_NOME = "Inserisci il nome";
    public static final String ERRORE_IP = "IP Server non corretto. Riprova";

    private JTextField txtIpAddress;
    private JTextField txtUser;
    private JButton btnEsterno;
    private InetAddress ipAddress;
    private ImageIcon img;
    private JLabel lblMain;
    private String username;
    SoundManager soundManager = new SoundManager();


    public Homepage() {
        inizializeHomePage();
        playMusic(SoundManager.MAINTHEME);
        drawAddressTxt();
        drawLocalBtn();
        drawUserTxt();
        drawExternalBtn();
    }

    private void inizializeHomePage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 750);
        img = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/res/sprites/background/imgBackground_3D.jpg")));
        lblMain = new JLabel(img);
        lblMain.setSize(1000, 750);
        setResizable(false);
        this.setTitle("GARDENVILLE");
        setContentPane(lblMain);
        lblMain.setLayout(null);
    }

    private void drawExternalBtn() {
        btnEsterno = new JButton("Server Esterno");
        btnEsterno.setFont(new Font("Arial", Font.BOLD, 14));

        btnEsterno.addActionListener(e -> {
            if (isValidIp(txtIpAddress.getText()) && !txtUser.getText().equals("") && !txtUser.getText().equals(MSG_NOME) && !txtUser.getText().equals(ERRORE_NOME)) {
                try {
                    ipAddress = InetAddress.getByName(txtIpAddress.getText());
                } catch (UnknownHostException ex) {
                    throw new RuntimeException(ex);
                }
                Client client = new Client(ipAddress, 1234);
                username = txtUser.getText();
                client.start();
                setVisible(false);
                stopMusic();
            } else {
                if (!isValidIp(txtIpAddress.getText())) {
                    txtIpAddress.setText(ERRORE_IP);
                    txtIpAddress.setBackground(Color.RED);
                } else {
                    txtUser.setText(ERRORE_NOME);
                    txtUser.setBackground(Color.RED);
                }
            }
        });
        btnEsterno.setBounds(525, 360, 150, 40);
        lblMain.add(btnEsterno);
    }

    private void drawUserTxt() {
        txtUser = new JTextField();
        txtUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtUser.setText("");
                txtUser.setBackground(Color.WHITE);
            }
        });

        txtUser.setFont(new Font("Arial", Font.PLAIN, 15));
        txtUser.setText(MSG_NOME);
        txtUser.setHorizontalAlignment(SwingConstants.LEFT);
        txtUser.setBounds(325, 250, 350, 40);
        lblMain.add(txtUser);
        txtUser.setColumns(10);
    }

    private void drawAddressTxt() {
        txtIpAddress = new JTextField();
        txtIpAddress.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtIpAddress.setBackground(Color.WHITE);
                txtIpAddress.setText("");
            }
        });
        txtIpAddress.setFont(new Font("Arial", Font.PLAIN, 15));
        txtIpAddress.setText("IP Server (se esterno 127.0.0.1)");
        txtIpAddress.setBounds(325, 300, 350, 40);
        lblMain.add(txtIpAddress);
        txtIpAddress.setColumns(10);
    }

    private void drawLocalBtn() {
        JButton btnLocal = new JButton("LocalHost");
        btnLocal.setFont(new Font("Arial", Font.BOLD, 14));

        btnLocal.addActionListener(e -> {
            if (!(txtUser.getText().equals("") || txtUser.getText().equals(MSG_NOME) || txtUser.getText().equals(ERRORE_NOME))) {
                Client client = new Client(1234);
                username = txtUser.getText();
                client.start();
                setVisible(false);
                stopMusic();
            } else {
                txtUser.setText(ERRORE_NOME);
                txtUser.setBackground(Color.RED);
            }
        });

        btnLocal.setBounds(325, 360, 150, 40);
        lblMain.add(btnLocal);
    }

    public static boolean isValidIp(final String ip) {
        return ip.matches("^[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}$");
    }

    public String getUsername() {
        return username;
    }

    public void playMusic(int i){
        soundManager.setFile(i);
        soundManager.play();
        soundManager.loop();
    }

    public void stopMusic(){
        soundManager.stop();
    }


}
package it.unibs.pajc.salm2d;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.InputStream;
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
    Sound sound = new Sound();

    /**
     * Costruzione schermata principale
     */
    public Homepage() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1000, 750);
        img = new ImageIcon(Objects.requireNonNull(this.getClass().getResource("/res/sprites/background/imgBackground_3D.jpg")));
        lblMain = new JLabel(img);
        lblMain.setSize(1000, 750);
        setResizable(false);

        this.setTitle("GARDENVILLE");

        //Sound iniziale
        playMusic(Sound.MAINTHEME);

        setContentPane(lblMain);
        lblMain.setLayout(null);
        txtIpAddress = new JTextField();
        txtIpAddress.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtIpAddress.setBackground(Color.WHITE);
                txtIpAddress.setText("");
            }
        });
        txtIpAddress.setFont(new Font("Tahoma", Font.PLAIN, 17));
        txtIpAddress.setText("IP Server ");
        txtIpAddress.setBounds(325, 300, 350, 40);
        lblMain.add(txtIpAddress);
        txtIpAddress.setColumns(10);

        JButton btnLocal = new JButton("LocalHost");
        btnLocal.setFont(new Font("Arial", Font.BOLD, 14));
        btnLocal.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!(txtUser.getText().equals("") || txtUser.getText().equals(MSG_NOME) || txtUser.getText().equals(ERRORE_NOME))) {
                    Client client = new Client(1234);
                    client.start();
                    setVisible(false);
                    stopMusic();
                } else {
                    txtUser.setText(ERRORE_NOME);
                    txtUser.setBackground(Color.RED);
                }
            }
        });
        btnLocal.setBounds(325, 360, 150, 40);
        lblMain.add(btnLocal);

        txtUser = new JTextField();
        txtUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                txtUser.setText("");
                txtUser.setBackground(Color.WHITE);
            }
        });

        txtUser.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtUser.setText(MSG_NOME);
        txtUser.setHorizontalAlignment(SwingConstants.LEFT);
        txtUser.setBounds(325, 250, 350, 40);
        lblMain.add(txtUser);
        txtUser.setColumns(10);

        btnEsterno = new JButton("Server Esterno");
        btnEsterno.setFont(new Font("Arial", Font.BOLD, 14));

        btnEsterno.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isValidIp(txtIpAddress.getText()) && !txtUser.getText().equals("") && !txtUser.getText().equals(MSG_NOME) && !txtUser.getText().equals(ERRORE_NOME)) {
                    try {
                        ipAddress = InetAddress.getByName(txtIpAddress.getText());
                    } catch (UnknownHostException ex) {
                        throw new RuntimeException(ex);
                    }
                    Client client = new Client(ipAddress, 1234);
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
            }
        });
        btnEsterno.setBounds(525, 360, 150, 40);
        lblMain.add(btnEsterno);
    }

    /**
     * Metodo che controlla se l'ip inserito è scritto nella forma corretta
     *
     * @param ip
     * @return True se è valido False altrimenti
     */
    public static boolean isValidIp(final String ip) {
        return ip.matches("^[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}\\.[\\d]{1,3}$");
    }

    public String getTxtIpAddress() {
        return txtIpAddress.getText();
    }

    public void setTxtIpAddress(JTextField txtIpAddress) {
        this.txtIpAddress = txtIpAddress;
    }

    public String getTxtUser() {
        return txtUser.getText();
    }

    public void setTxtUser(JTextField txtUser) {
        this.txtUser = txtUser;
    }

    public void playMusic(int i){
        sound.setFile(i);
        sound.play();
        sound.loop();
    }
    public void stopMusic(){
        sound.stop();
    }


}
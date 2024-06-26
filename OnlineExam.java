/*ONLINE EXAMINATION TASK USING JAVA SWINGS*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Login extends JFrame implements ActionListener {
    JButton b1;
    JPanel newPanel;
    JLabel userLabel, passLabel;
    final JTextField textField1, textField2;
    Login() {
        userLabel = new JLabel();
        userLabel.setText("Username :");
        textField1 = new JTextField(20);
        passLabel = new JLabel();
        passLabel.setText("Password :");
        textField2 = new JPasswordField(8);
        b1 = new JButton("   LOGIN  ");
        newPanel = new JPanel(new GridLayout(3, 1));
        newPanel.add(userLabel);
        newPanel.add(textField1);
        newPanel.add(passLabel);
        newPanel.add(textField2);
        newPanel.add(b1);
        add(newPanel, BorderLayout.CENTER);
        b1.addActionListener(this);
        setTitle("Login Form ");
    }
    public void actionPerformed(ActionEvent ae) {
        String userValue = textField1.getText();
        String passValue = textField2.getText();
        if (!passValue.equals("")) {
            OnlineTestBegin testBegin = new OnlineTestBegin(userValue);
            testBegin.setSize(600,350);
            testBegin.setVisible(true);
            dispose(); // Close the login form
        } else {
            textField2.setText("Enter Password");
            actionPerformed(ae);
        }
    }
}

class OnlineTestBegin extends JFrame implements ActionListener {
    JLabel l;
    JLabel l1;
    JRadioButton jb[] = new JRadioButton[6];
    JButton b1, b2, log, updateButton; // Added updateButton
    ButtonGroup bg;
    int count = 0, current = 0, x = 1, y = 1, now = 0;
    int m[] = new int[10];
    Timer timer; // Added timer variable
    int secondsRemaining = 180; // 3 minutes (180 seconds)

    OnlineTestBegin(String s) {
        super(s);
        l = new JLabel();
        l1 = new JLabel();
        add(l);
        add(l1);
        bg = new ButtonGroup();
        for (int i = 0; i < 5; i++) {
            jb[i] = new JRadioButton();
            add(jb[i]);
            bg.add(jb[i]);
        }
        b1 = new JButton("Save and Next");
        b2 = new JButton("Save for later");
        updateButton = new JButton("Update Profile"); // Added updateButton
        log = new JButton("Logout"); // Added logout button
        b1.addActionListener(this);
        b2.addActionListener(this);
        updateButton.addActionListener(this); // Added action listener for updateButton
        log.addActionListener(this); // Added action listener for logout button
        add(b1);
        add(b2);
        add(updateButton); // Added updateButton to the frame
        add(log); // Added logout button to the frame
        set();
        l.setBounds(30, 40, 450, 20);
        l1.setBounds(20, 20, 450, 20);
        jb[0].setBounds(50, 80, 100, 20);
        jb[1].setBounds(50, 110, 100, 20);
        jb[2].setBounds(50, 140, 100, 20);
        jb[3].setBounds(50, 170, 100, 20);
        b1.setBounds(95, 240, 140, 30);
        b2.setBounds(270, 240, 150, 30);
        updateButton.setBounds(420, 20, 150, 30); // Positioned the updateButton
        log.setBounds(420, 60, 150, 30); // Positioned the logout button
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocation(250, 100);
        setVisible(true);
        setSize(600, 350);

        // Initialize the timer
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                secondsRemaining--;
                l1.setText("Time Remaining: " + formatTime(secondsRemaining));
                if (secondsRemaining <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(OnlineTestBegin.this, "Time's up!");
                    showResult();
                }
            }
        });
        timer.start();
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == b1) {
            if (check())
                count = count + 1;
            current++;
            set();
            if (current == 9) {
                b1.setEnabled(false);
                b2.setText("Result");
            }
        } else if (e.getActionCommand().equals("Save for later")) {
            JButton bk = new JButton("Review" + x);
            bk.setBounds(480, 50 + 30 * x, 100, 30);
            add(bk);
            bk.addActionListener(this);
            m[x] = current;
            x++;
            current++;
            set();
            if (current == 9)
                b2.setText("Result");
            setVisible(false);
            setVisible(true);
        } else if (e.getActionCommand().equals("Result")) {
            if (check())
                count = count + 1;
            showResult();
        } else if (e.getSource() == updateButton) {
            String newUsername = JOptionPane.showInputDialog(this, "Enter new username");
            if (newUsername != null && !newUsername.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username updated successfully!");
                setTitle("Welcome, " + newUsername);
            }
        } else if (e.getSource() == log) {
            int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                dispose(); // Close the exam interface
                Login loginForm = new Login();
                loginForm.setSize(400, 150);
                loginForm.setVisible(true);
            }
        } else {
            for (int i = 0, y = 1; i < x; i++, y++) {
                if (e.getActionCommand().equals("Review" + y)) {
                    if (check())
                        count = count + 1;
                    now = current;
                    current = m[y];
                    set();
                    ((JButton) e.getSource()).setEnabled(false);
                    current = now;
                }
            }
        }
    }

    void set() {
        jb[4].setSelected(true);
        if (current == 0) {
            l.setText("Que1: Identify return type of method that does not return any value..?");
            jb[0].setText("Int");
            jb[1].setText("none");
            jb[2].setText("double");
            jb[3].setText("void");
        } else if (current == 1) {
            l.setText("Que2:Thread priority in java is..?");
            jb[0].setText("Integer");
            jb[1].setText("Float");
            jb[2].setText("Double");
            jb[3].setText("Long");
        }
        else if (current == 2) {
            l.setText("Que3:____ is used to find and fix bugs in program..? ");
            jb[0].setText("JDK");
            jb[1].setText("JRE");
            jb[2].setText("JVM");
            jb[3].setText("JDB");
        }
    else if(current==3)
    {
        l.setText("Ques4:which of the folloing is not a java feature?");
        jb[0].setText("object-oriented");
        jb[1].setText("Use of pointers");
        jb[2].setText("Portable");
        jb[3].setText("HighPerformance");
}
else if(current==4)
{
        l.setText("Ques5:Exception created by try block is caught in which block..?");
        jb[0].setText("catch");
        jb[1].setText("throw");
        jb[2].setText("final");
        jb[3].setText("none");
    }
    
    

else if(current ==5)
{
l.setText("Ques 6:What is runnable..?");
jb[0].setText("Abstract class");
jb[1].setText("Interface");
jb[2].setText("class");
jb[3].setText("Method");
}
else if(current ==6)
{
l.setText("Ques7:Which of the following is not a OOPS concept?");
jb[0].setText("Polymorphism");
jb[1].setText("Inheritance");
jb[2].setText("Compilation");
jb[3].setText("Encapsulation");
}
else if(current ==7)
{
l.setText("Ques8:which of the following keyord is used for define the interfaces in java?");
jb[0].setText("Inf");
jb[1].setText("Intf");
jb[2].setText("interface");
jb[3].setText("Interface");
}
else if(current ==8)
{
l.setText("Ques9:Select valid declaration in array..?");
jb[0].setText("char ch[]=new char(5)");
jb[1].setText("char ch[]=new char[5]");
jb[2].setText("char ch[]=new char()");
jb[3].setText("char ch[]=new char[]");
}
else if (current ==9)
{
l.setText("Ques10:Arrays in java are ____");
jb[0].setText("Object Reference");
jb[1].setText("Objects");
jb[2].setText("primitive data type");
jb[3].setText("none");
}

                l.setBounds(30, 40, 450, 20);
        for (int i = 0, j = 0; i <= 90; i += 30, j++)
            jb[j].setBounds(50, 80 + i, 200, 20);
    }

    boolean check() {
        // Code for checking the selected answer
if (current == 0)
            return (jb[3].isSelected());
        if (current == 1)
            return (jb[0].isSelected());
        if (current == 2)
            return (jb[3].isSelected());
        if(current ==3)
            return(jb[1].isSelected());
        if (current == 4)
            return (jb[0].isSelected());
        if (current == 5)
            return (jb[1].isSelected());
        if (current == 6)
            return (jb[2].isSelected());
        if (current == 7)
            return (jb[2].isSelected());
        if (current == 8)
            return (jb[1].isSelected());
        if (current == 9)
            return (jb[1].isSelected());

        return false;
    
    }

    String formatTime(int seconds) {
        int minutes = seconds / 60;
        seconds %= 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    void showResult() {
        timer.stop();
        JOptionPane.showMessageDialog(this, " Total Score = " + count);
        System.exit(0);
    }
}

class OnlineExam {
    public static void main(String args[]) {
        try {
            Login form = new Login();
            form.setSize(300, 300);
            form.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}

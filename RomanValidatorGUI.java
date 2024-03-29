import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RomanValidatorGUI extends JFrame {

    private JTextField inputField;
    private JButton validateButton;
    private JLabel resultLabel;

    public RomanValidatorGUI() {
        // Set up the JFrame
        super("Roman Number Validator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 125);
        setLocationRelativeTo(null); // Center the frame

        // Create components
        inputField = new JTextField(20);
        validateButton = new JButton("Validate");
        resultLabel = new JLabel("Waiting for first input");

        // Add ActionListener to the button
        validateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = inputField.getText();
                int intValue = RomanNumbers.romanToInt(input);
                String correctWay = RomanNumbers.convertToRoman(intValue);

                if (intValue >= 0 && RomanNumbers.romanNumberValidator(input)) {
                    resultLabel.setText("Valid roman number for " + intValue + ".");
                } else {
                    resultLabel.setText("Non-valid roman number for " + intValue + ". The correct way is: " + correctWay + ".");
                }
            }
        });

        // Create layout
        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter a Roman numeral: "));
        panel.add(inputField);
        panel.add(validateButton);
        panel.add(resultLabel);

        // Set up the content pane
        getContentPane().add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RomanValidatorGUI().setVisible(true);
            }
        });
    }
}

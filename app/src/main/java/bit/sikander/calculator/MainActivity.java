package bit.sikander.calculator;

import android.annotation.TargetApi;
import android.databinding.DataBindingUtil;
import android.icu.text.DecimalFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import bit.sikander.calculator.databinding.ActivityMainBinding;

@TargetApi(24)
public class MainActivity extends AppCompatActivity {

    // Declare variable for binding object for automatically created class 'ActivityMainBinding'
    // See also activity_main.xmlxml
    private ActivityMainBinding binding;

    // Declare variables for operands
    // Start the app assuming second operand is not a valid number
    private double numberOne;
    private double numberTwo = Double.NaN;

    // Constants for operations
    private static final char ADDITION = '+';
    private static final char SUBTRACTION = '-';
    private static final char MULTIPLICATION = '*';
    private static final char DIVISION = '/';
    private static final char FACTORIAL = '!';
    private static final char DIV = '\u00F7';

    // Variable to store current operation
    private char CURRENT_OPERATION;

    // Decimal formatter
    private DecimalFormat decimalFormat = new DecimalFormat("#.##########");

    // Set request code for second activity
    private static final int REQUEST_CODE = 0x9345;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Uncommented line below to disable referring to views for data
        // setContentView(R.layout.activity_main);

        // New line added to enable data binding for this activity
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        // Listener for the DELETE button
        binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.editText.setText(null);
                binding.resultTextView.setText(null);
            }
        });



        binding.buttonEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // do the calculation
                calculate();
                if (CURRENT_OPERATION != '0') {

                    // Display results on Info text view
                    if (CURRENT_OPERATION != FACTORIAL){
                        binding.resultTextView.setText(binding.resultTextView.getText().toString() +
                                decimalFormat.format(numberOne) + " = " + decimalFormat.format(numberTwo));
                    }
                    else {
                        binding.resultTextView.setText(binding.resultTextView.getText().toString() + " = " + decimalFormat.format(numberTwo));
                    }

                    // Reset to initial value
                    numberTwo = Double.NaN;
                }

                // Update operation to non-value
                CURRENT_OPERATION = '0';
            }
        });


    }

   // Displays text on edit Text based on button clicked
    public void onValueClick(View v){
        Button currentButton = (Button)v;
        binding.editText.setText(binding.editText.getText() + currentButton.getText().toString());
    };

    // Display or perform operation based on operator clicked
    public void onOperatorClick(View v){
        Button operator = (Button)v;

        // Call method to calculate
        calculate();
        // set operation
        switch (operator.getText().charAt(0)){
            case ADDITION:
                CURRENT_OPERATION = ADDITION;
                break;
            case SUBTRACTION:
                CURRENT_OPERATION = SUBTRACTION;
                break;
            case MULTIPLICATION:
                CURRENT_OPERATION = MULTIPLICATION;
                break;
            case DIV:
                CURRENT_OPERATION = DIVISION;
                break;
            case FACTORIAL:
                CURRENT_OPERATION = FACTORIAL;
                break;
        }

        // display information in the Info View (which is above the edit text)
        binding.resultTextView.setText(decimalFormat.format(numberTwo) + " " + CURRENT_OPERATION);
        // clear edit text
        binding.editText.setText(null);

    }

    // Compute based on whether valid number or operator
    private void calculate(){

        // if value 2 is a valid number, then
        if (!Double.isNaN(numberTwo)){
            // assign edit Text to value 1
            if (CURRENT_OPERATION != FACTORIAL) {
                numberOne = Double.parseDouble(binding.editText.getText().toString());
            }

            // Clear edit Text
            binding.editText.setText(null);

            // Evaluate current operation
            if (CURRENT_OPERATION == ADDITION)
                numberTwo = this.numberTwo + numberOne;
            else if (CURRENT_OPERATION == SUBTRACTION)
                numberTwo = this.numberTwo - numberOne;
            else if (CURRENT_OPERATION == MULTIPLICATION)
                numberTwo = this.numberTwo * numberOne;
            else if (CURRENT_OPERATION == DIVISION)
                numberTwo = this.numberTwo / numberOne;
            else if (CURRENT_OPERATION == FACTORIAL)
                // to modify this
                numberTwo = factorial(this.numberTwo );
        }

        /// if invalid number, assign edit text contents to operand two
        else {
            try {
                numberTwo = Double.parseDouble(binding.editText.getText().toString());

                // clear info text
                binding.resultTextView.setText(null);
            }
            catch (Exception e){}
        }
    }

    private double factorial(Double num){
        Double res = 1.D;

        for (int i = 1; i <= num; i++){
            res *= i;
        }

        return res;
    }


}

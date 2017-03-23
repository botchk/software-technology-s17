package at.sw2017.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Calculator extends AppCompatActivity implements View.OnClickListener{

    private TextView numberView;

    private ArrayList<Button> numberButtons;

    private Button buttonAdd;
    private Button buttonSubtract;
    private Button buttonMultiply;
    private Button buttonDivide;
    private Button buttonClear;
    private Button buttonResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        numberView = (TextView) findViewById(R.id.textView);

        setUpNumberButtonListener();

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(this);

        buttonSubtract = (Button) findViewById(R.id.buttonSubtract);
        buttonSubtract.setOnClickListener(this);

        buttonMultiply = (Button) findViewById(R.id.buttonMultiply);
        buttonMultiply.setOnClickListener(this);

        buttonDivide = (Button) findViewById(R.id.buttonDivide);
        buttonDivide.setOnClickListener(this);

        buttonClear = (Button) findViewById(R.id.buttonClear);
        buttonClear.setOnClickListener(this);

        buttonResult = (Button) findViewById(R.id.buttonResult);
        buttonResult.setOnClickListener(this);
    }

    /**
     * Inits the number buttons and adds the click listener to each button.
     * The initialized buttons are accessible over the numberButtons array.
     */
    public void setUpNumberButtonListener() {
        for (int i = 0; i <= 9; ++i) {
            String buttonName = "button" + i;

            int id = getResources().getIdentifier(buttonName, "id", R.class.getPackage().getName());

            Button button = (Button) findViewById(id);
            button.setOnClickListener(this);

            numberButtons.add(button);
        }
    }

    @Override
    public void onClick(View v) {
        Button clickedButton = (Button) v;

        switch(clickedButton.getId()) {
            case R.id.buttonAdd:
                break;
            case R.id.buttonSubtract:
                break;
            case R.id.buttonMultiply:
                break;
            case R.id.buttonDivide:
                break;
            case R.id.buttonResult:
                break;
            case R.id.buttonClear:
                break;
            default:
                String recentNumber = numberView.getText().toString();
                if (recentNumber.equals("0")) {
                    recentNumber = "";
                }
                recentNumber += clickedButton.getText().toString();
                numberView.setText(recentNumber);
        }
    }
}

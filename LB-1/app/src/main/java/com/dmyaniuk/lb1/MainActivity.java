package com.dmyaniuk.lb1;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getDailyCalories(View view) {
        EditText height = findViewById(R.id.height);
        EditText weight = findViewById(R.id.weight);
        EditText age = findViewById(R.id.age);
        RadioGroup radioGroup = findViewById(R.id.sex);
        RadioButton sex = findViewById(radioGroup.getCheckedRadioButtonId());
        Spinner spinner = findViewById(R.id.lifestyle);
        int selectedLifeStyleIndex = spinner.getSelectedItemPosition();
        String lifeStyle = this.getLifeStyle(selectedLifeStyleIndex);

        Calories calories = new Calories(
                sex.getText().toString().equals("man"),
                Double.parseDouble(height.getText().toString()),
                Double.parseDouble(weight.getText().toString()),
                Double.parseDouble(age.getText().toString()),
                lifeStyle
        );
        double caloriesResult = calories.getMetabolism();

        TextView result = findViewById(R.id.result);
        result.setText("Результат: " + caloriesResult);
    }

    private String getLifeStyle(int index) {
        switch (index) {
            case 0:
                return ActiveMetabolismTypes.NoActivityPerson;
            case 1:
                return ActiveMetabolismTypes.LowActivityPerson;
            case 2:
                return ActiveMetabolismTypes.AverageActivityPerson;
            case 3:
                return ActiveMetabolismTypes.ActivePerson;
            case 4:
                return ActiveMetabolismTypes.Sportsman;
            default:
                return "";
        }
    }
}
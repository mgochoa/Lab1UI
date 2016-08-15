package co.edu.udea.compuvil.gr5.lab1ui;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String[] countries = {"Argentina ", "Bolivia", "Chile", "Colombia", "Costa Rica", "Cuba", "Ecuador", "El Salvador", "Guatemala", "Honduras", "Mexico", "Nicaragua", "Panama", "Paraguay", "Peru", "Puerto Rico", "Republica Dominicana", "Uruguay", "Venezuela"};
    // String[] countries = getResources().getStringArray(R.array.countries);
    String[] hobbies = {"Sports", "Music", "Gaming", "E-Sports", "Businesses", "Culture", "Art", "Collecting", "Gastronomy"};
    private TextView mDateDisplay;
    private AutoCompleteTextView countryAutoComplete;
    private Spinner hobbieSpinner;
    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDateDisplay = (TextView) findViewById(R.id.txtBd);
        countryAutoComplete = (AutoCompleteTextView) findViewById(R.id.autoCountry);
        hobbieSpinner = (Spinner) findViewById(R.id.hobSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        //ArrayAdapter<CharSequence> adapterHob = ArrayAdapter.createFromResource(this,R.array.hobbies_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<String> adapterHob = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hobbies);
        countryAutoComplete.setThreshold(1);
        countryAutoComplete.setAdapter(adapter);

        adapterHob.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        hobbieSpinner.setAdapter(adapterHob);

    }


    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.btnBd:
                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getFragmentManager(), "datePicker");
                break;
        }

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mYear = year;
        mMonth = monthOfYear;
        mDay = dayOfMonth;
        updateDisplay();
    }

    private void updateDisplay() {

        mDateDisplay.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(mMonth + 1).append("/").append(mDay).append("/")
                .append(mYear).append(" "));
    }


}

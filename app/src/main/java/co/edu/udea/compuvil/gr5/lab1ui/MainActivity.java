package co.edu.udea.compuvil.gr5.lab1ui;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    //Global variables
    private String[] countries;
    private String[] hobbies;
    private String data;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int validation=0;

    // XML objects
    private TextView mDateDisplay;
    private TextView showResults;
    private AutoCompleteTextView countryAutoComplete;
    private Spinner hobbieSpinner;
    private EditText etName;
    private EditText etlastName;
    private RadioButton rbMale;
    private RadioButton rbFemale;
    private EditText etTelephone;
    private EditText etAddress;
    private EditText etEmail;
    private CheckBox cbFavorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    //XML Initialization
        mDateDisplay = (TextView) findViewById(R.id.txtBd);
        countryAutoComplete = (AutoCompleteTextView) findViewById(R.id.autoCountry);
        hobbieSpinner = (Spinner) findViewById(R.id.hobSpinner);
        etName=(EditText) findViewById(R.id.name);
        etlastName=(EditText)findViewById(R.id.lastName);
        showResults=(TextView)findViewById(R.id.txtShow);
        showResults.setMovementMethod(new ScrollingMovementMethod());
        rbMale=(RadioButton)findViewById(R.id.rbMale);
        rbFemale=(RadioButton)findViewById(R.id.rbFemale);
        etTelephone = (EditText) findViewById(R.id.telephone);
        etAddress = (EditText)findViewById(R.id.address);
        etEmail= (EditText)findViewById(R.id.email);
        cbFavorite = (CheckBox) findViewById(R.id.cbFavorite);


        //Getting resourcers from xml.
        countries=getResources().getStringArray(R.array.countries);
        hobbies=getResources().getStringArray(R.array.hobbies_array);
        //Adapters for spinner and Autocomplete.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, countries);
        ArrayAdapter<String> adapterHob = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, hobbies);
        adapterHob.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        hobbieSpinner.setAdapter(adapterHob);
        countryAutoComplete.setThreshold(1);
        countryAutoComplete.setAdapter(adapter);

    }


    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnBd:
                DialogFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getFragmentManager(), "datePicker");
                break;
            case R.id.btnShow:
                data="";
                //Name and lastname
                validateEditText(etName.getText().toString());
                validateEditText(etlastName.getText().toString());
                data=etName.getText().toString()+"\n"+etlastName.getText().toString();

                //Sex
                if(rbMale.isChecked()==true){
                    data=data+"\n"+getResources().getString(R.string.sex)+": "+getResources().getString(R.string.male);
                }else if(rbFemale.isChecked()==true){
                    data=data+"\n"+getResources().getString(R.string.sex)+": "+getResources().getString(R.string.female);
                }
                else{
                    Toast.makeText(getBaseContext(),getResources().getString(R.string.message), Toast.LENGTH_SHORT).show();
                }
                //Born date
                if(mDateDisplay.getText().toString().equals(getResources().getString(R.string.dd_mm_yy))){
                    Toast.makeText(getBaseContext(),getResources().getString(R.string.message), Toast.LENGTH_SHORT).show();
                };
                data = data+ "\n " + mDateDisplay.getText().toString();

                //Country
                validateEditText(countryAutoComplete.getText().toString());
                data= data + "\n" + getResources().getString(R.string.country) + ": " + countryAutoComplete.getText().toString();
                //Telephone
                data = data + "\n"  + getResources().getString(R.string.telephone) + ": "  +etTelephone.getText().toString();
                //Address
                validateEditText(etAddress.getText().toString());
                data = data + "\n"  + getResources().getString(R.string.address) + ": "  + etAddress.getText().toString();
                //Email
                validateEmail(etEmail.getText().toString());
                data = data + "\n"  + getResources().getString(R.string.email) + ": "  + etEmail.getText().toString();
                //Hobbies
                data = data+ "\n" + getResources().getString(R.string.hobbies) + ": " +hobbieSpinner.getSelectedItem().toString();
                //Favorite
                if(cbFavorite.isChecked()){
                    data = data + "\n" +getResources().getString(R.string.favorite)+": "+getResources().getString(R.string.yes);
                }else{
                    data = data + "\n" +getResources().getString(R.string.favorite)+": "+getResources().getString(R.string.no);
                }
                if (validation==0) {
                    showResults.setText(data);
                }else{
                    showResults.setText("");
                    validation=0;
                }
                break;
        }
    }

    //Validate EditText
    public void validateEditText(String edt){
        if(edt.equals("")){
         Toast.makeText(getBaseContext(), getResources().getString(R.string.message), Toast.LENGTH_SHORT).show();
          validation++;
        }
    }
    //Validate Email
    public void validateEmail(CharSequence target){
        if(!Patterns.EMAIL_ADDRESS.matcher(target).matches()){
            Toast.makeText(getBaseContext(), getResources().getString(R.string.message), Toast.LENGTH_SHORT).show();
            showResults.setText("");
            validation++;
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
                .append(mYear));
    }


}

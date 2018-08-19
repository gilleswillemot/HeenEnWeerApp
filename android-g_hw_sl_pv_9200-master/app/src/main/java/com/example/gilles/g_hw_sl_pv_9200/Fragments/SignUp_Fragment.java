package com.example.gilles.g_hw_sl_pv_9200.Fragments;

/**
 * Created by Lucas on 20-11-2017.
 */

        import java.util.regex.Pattern;

        import com.example.gilles.g_hw_sl_pv_9200.Activities.CustomToast;
        import com.example.gilles.g_hw_sl_pv_9200.Activities.LoginLauncherActivity;
        import com.example.gilles.g_hw_sl_pv_9200.Activities.MainActivity;
        import com.example.gilles.g_hw_sl_pv_9200.Backend.DataInterface;
        import com.example.gilles.g_hw_sl_pv_9200.Backend.ServiceGenerator;
        import com.example.gilles.g_hw_sl_pv_9200.R;
        import com.example.gilles.g_hw_sl_pv_9200.Utils;
        import com.example.gilles.g_hw_sl_pv_9200.Models.Gebruiker;
        import com.example.gilles.g_hw_sl_pv_9200.Models.User;

        import android.annotation.SuppressLint;
        import android.content.Intent;
        import android.content.res.ColorStateList;
        import android.content.res.XmlResourceParser;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.CheckBox;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import retrofit2.Call;
        import retrofit2.Callback;
        import retrofit2.Response;

public class SignUp_Fragment extends Fragment implements OnClickListener {
    private static View view;
    private static EditText fullName, emailId, familienaam, accountnaam,
    //mobileNumber, location,
    password, confirmPassword;
    private static TextView login;
    private static Button signUpButton;
    private static CheckBox terms_conditions;

    public SignUp_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.signup_layout, container, false);
        initViews();
        setListeners();
        return view;
    }

    /**
     * initializer voor alle elementen van het signupfragment
     */
    // Initialize all views
    private void initViews() {
        fullName = (EditText) view.findViewById(R.id.familieNaam);
        familienaam = (EditText) view.findViewById(R.id.familieNaam);
        accountnaam = (EditText) view.findViewById(R.id.accountnaam);
        //  emailId = (EditText) view.findViewById(R.id.userEmailId);
        // mobileNumber = (EditText) view.findViewById(R.id.mobileNumber);
        //  location = (EditText) view.findViewById(R.id.location);
        password = (EditText) view.findViewById(R.id.password);
        confirmPassword = (EditText) view.findViewById(R.id.confirmPassword);
        signUpButton = (Button) view.findViewById(R.id.signUpBtn);
        login = (TextView) view.findViewById(R.id.already_user);
        terms_conditions = (CheckBox) view.findViewById(R.id.terms_conditions);

        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            login.setTextColor(csl);
            terms_conditions.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        signUpButton.setOnClickListener(this);
        login.setOnClickListener(this);
    }
    private void closeFragment(){
        this.getActivity().onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signUpBtn:

                // Call checkValidation method
                checkValidation();
                break;

            case R.id.already_user:

                // Replace login fragment
                new LoginLauncherActivity().replaceLoginFragment();
                break;
        }

    }

    /**
     * validatiemethode voor de ingave van het registreren
     */
    // Check Validation Method
    private void checkValidation() {

        // Get all edittext texts
        String getVoornaam = fullName.getText().toString();
        String getFamilienaam = familienaam.getText().toString();
        String getAccountnaam = accountnaam.getText().toString();
        //String getEmailId = emailId.getText().toString();
        // String getMobileNumber = mobileNumber.getText().toString();
        // String getLocation = location.getText().toString();
        String getPassword = password.getText().toString();
        String getConfirmPassword = confirmPassword.getText().toString();

        // Pattern match for email id
        Pattern p = Pattern.compile(Utils.regEx);
        //   Matcher m = p.matcher(getEmailId);

        // Check if all strings are null or not
        if (getVoornaam.equals("") || getVoornaam.length() == 0
                || getFamilienaam.equals("") || getFamilienaam.length() == 0
                || getAccountnaam.equals("") || getAccountnaam.length() == 0
                //  || getMobileNumber.equals("") || getMobileNumber.length() == 0
                //  || getLocation.equals("") || getLocation.length() == 0
                || getPassword.equals("") || getPassword.length() == 0
                || getConfirmPassword.equals("")
                || getConfirmPassword.length() == 0)

            new CustomToast().Show_Toast(getActivity(), view,
                    "Alle velden zijn verplicht!");

            // Check if email id valid or not
//        else if (!m.find())
//            new CustomToast().Show_Toast(getActivity(), view,
//                    "Your Email Id is Invalid.");

            // Check if both password should be equal
        else if (!getConfirmPassword.equals(getPassword))
            new CustomToast().Show_Toast(getActivity(), view,
                    "Both password doesn't match.");

            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked())
            new CustomToast().Show_Toast(getActivity(), view,
                    "Please select Terms and Conditions.");

            // Else do signup or do your stuff
        else {
            DataInterface dataInterface = ServiceGenerator.createService(DataInterface.class);
            Call<User> call = dataInterface.registreer(new User(getAccountnaam, getVoornaam, getFamilienaam, getPassword));
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    Utils.USERID = user.getId();
                    Utils.GEBRUIKER = new Gebruiker(getFamilienaam, getVoornaam, user.getId());
                    Toast.makeText(getActivity(), "Geregistreerd.", Toast.LENGTH_SHORT)
                            .show();

                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    startActivity(intent);
                    closeFragment();

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d("Error:", t.getMessage());
                }
            });

        }

    }
}
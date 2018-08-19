package com.example.gilles.g_hw_sl_pv_9200.Fragments;

/**
 * Created by Lucas on 20-11-2017.
 */

import java.io.IOException;
import java.util.Date;

import com.example.gilles.g_hw_sl_pv_9200.Activities.CustomToast;
import com.example.gilles.g_hw_sl_pv_9200.Activities.MainActivity;
import com.example.gilles.g_hw_sl_pv_9200.Backend.DataInterface;
import com.example.gilles.g_hw_sl_pv_9200.Backend.ServiceGenerator;
import com.example.gilles.g_hw_sl_pv_9200.R;
import com.example.gilles.g_hw_sl_pv_9200.Utils;
import com.example.gilles.g_hw_sl_pv_9200.Models.Gebruiker;
import com.google.gson.Gson;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Login_Fragment extends Fragment implements OnClickListener {
    private static View view;

    private static EditText emailid, password;
    private static Button loginButton;
    private static TextView signUp;//forgotPassword
    private static CheckBox show_hide_password;
    private static LinearLayout loginLayout;
    private static Animation shakeAnimation;
    private static FragmentManager fragmentManager;

    private UserLoginTask auth = null;

    public Login_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.login_layout, container, false);
        initViews();
        setListeners();

        // auto login for development reasons.
        emailid.setText("gilles");
        ;
        password.setText("testtesttest");
        checkValidation();

        return view;
    }

    /**
     * Zal de, via de response ontvangen, login-attributen opslaan in
     * de local storage (SharedPreferences), zodoende deze attributen later
     * overal in de app te kunnen gebruiken.
     * @param responseString String object die we van de backend terugkregen na het inloggen.
     */
    public void initLoginAttributes(String responseString){
        // deserialization of response message
        LoginObject loginObj = new Gson().fromJson(responseString, LoginObject.class);

        // ingelogde gebruiker in local storage toevoegen.
        SharedPreferences.Editor editor =
                getActivity().getSharedPreferences("myPref", MODE_PRIVATE).edit();

        editor.putString("token", loginObj.token);
        editor.putString("userId", loginObj.userId);
        editor.putString("huidigGezinId", loginObj.huidigGezinId);
        editor.apply();
    }

    // Initiate Views
    private void initViews() {
        fragmentManager = getActivity().getSupportFragmentManager();

        emailid = (EditText) view.findViewById(R.id.login_emailid);
        password = (EditText) view.findViewById(R.id.login_password);
        loginButton = (Button) view.findViewById(R.id.loginBtn);
        //forgotPassword = (TextView) view.findViewById(R.id.forgot_password);
        signUp = (TextView) view.findViewById(R.id.createAccount);
        show_hide_password = (CheckBox) view
                .findViewById(R.id.show_hide_password);
        loginLayout = (LinearLayout) view.findViewById(R.id.login_layout);

        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getActivity(),
                R.anim.shake);

        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            // forgotPassword.setTextColor(csl);
            show_hide_password.setTextColor(csl);
            signUp.setTextColor(csl);
        } catch (Exception e) {
        }
    }

    // Set Listeners
    private void setListeners() {
        loginButton.setOnClickListener(this);
//        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);

        // Set check listener over checkbox for showing and hiding password
        show_hide_password
                .setOnCheckedChangeListener(new OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(CompoundButton button,
                                                 boolean isChecked) {

                        // If it is checkec then show password else hide
                        // password
                        if (isChecked) {

                            show_hide_password.setText(R.string.hide_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT);
                            password.setTransformationMethod(HideReturnsTransformationMethod
                                    .getInstance());// show password
                        } else {
                            show_hide_password.setText(R.string.show_pwd);// change
                            // checkbox
                            // text

                            password.setInputType(InputType.TYPE_CLASS_TEXT
                                    | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            password.setTransformationMethod(PasswordTransformationMethod
                                    .getInstance());// hide password

                        }

                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginBtn:
                checkValidation();
                break;

            case R.id.createAccount:

                // Replace signup frgament with animation
                fragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                        .replace(R.id.frameContainer, new SignUp_Fragment(),
                                Utils.SignUp_Fragment).commit();
                break;
        }
    }


    // Check Validation before login
    private void checkValidation() {
        // Get email id and password
        String getEmailId = emailid.getText().toString();
        String getPassword = password.getText().toString();
        // Check patter for email id
//        Pattern p = Pattern.compile(Utils.regEx);
//        Matcher m = p.matcher(getEmailId);


        // Check for both field is empty or not
        if (getEmailId.equals("") || getEmailId.length() == 0
                || getPassword.equals("") || getPassword.length() == 0) {
            loginLayout.startAnimation(shakeAnimation);
            new CustomToast().Show_Toast(getActivity(), view,
                    "Vul beide velden in aub.");

        }
        // Check if email id is valid or not
//        else if (!m.find())
//            new CustomToast().Show_Toast(getActivity(), view,
//                    "Your Email Id is Invalid.");
//            // Else do login and do your stuff
        else {
            auth = new UserLoginTask(getEmailId, getPassword);
            auth.execute((Void) null);
        }

    }

    /**
     * async class voor het uitvoeren van een (async) login taak,
     * inloggen via de databank (User wordt teruggegeven door de databank en in Utils.GEBRUIKER bijgehouden,
     * zodat dit over de hele applicatie zal kunnen gebruikt worden.
     */
    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mUsername = email;
            mPassword = password;
        }

        /**
         * wat de taak in de achtergrond uitvoert (POST methode naar de databank voor login)
         *
         * @param params
         * @return
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            DataInterface dataInterface = ServiceGenerator.getRetrofit(mUsername, mPassword);
//            String base = mUsername + ":" + mPassword;
//            String authHeader = "Basic dGVzdHNkZnNkZjpwYXNzd29yZHBhc3N3b3Jk"; //+ Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            JSONObject jObjectData = new JSONObject();
            try {
                jObjectData.put("username", mUsername);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                jObjectData.put("password", mPassword);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(JSON, jObjectData.toString());
            Request request = new Request.Builder()
                    //.url("https://tranquil-meadow-65977.herokuapp.com/API/users/login")
                    .url("http://192.168.0.128:3000/API/users/login")
                    .post(body)
                    .build();
            okhttp3.Call call = client.newCall(request);

            try {
                okhttp3.Response response = call.execute();

                //Log.d("response antwoord",response.body().string().split(" ")[3]);
                //Log.d("response antwoord",response.body().string().toString());
                if (response.isSuccessful()) {
                    String responseString = response.body().string();
                    initLoginAttributes(responseString);

                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            auth = null;
            if (success) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getActivity(), "Login mislukt.", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            auth = null;
        }
    }

    public class LoginObject {
        public String token;
        public String userId;
        public String huidigGezinId;

        public LoginObject(String token, String userId, String huidigGezinId) {
            this.token = token;
            this.userId = userId;
            this.huidigGezinId = huidigGezinId;
        }
    }

    private void OudeCode(DataInterface d, String id) {
        Call<Gebruiker> callgbr = d.getGebruiker(id);
        callgbr.enqueue(new retrofit2.Callback<Gebruiker>() {
            @Override
            public void onResponse(Call<Gebruiker> call, Response<Gebruiker> response) {
                String naam = response.body().getNaam();
                String voorNaam = response.body().getVoorNaam();
                Toast.makeText(getActivity(), "Welkom " + Utils.GEBRUIKER.getVoorNaam(), Toast.LENGTH_SHORT)
                        .show();
                Log.d("id gebruiker", Utils.GEBRUIKER.getId());
            }

            @Override
            public void onFailure(Call<Gebruiker> call, Throwable t) {
                Log.d("Error:", t.getMessage());
            }
        });
    }
}






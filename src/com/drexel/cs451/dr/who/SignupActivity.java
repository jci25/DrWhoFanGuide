package com.drexel.cs451.dr.who;

import com.drexel.cs451.dr.who.load.ServerCalls;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

public class SignupActivity extends Activity {
	
	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";


	// Values for email and password at the time of the login attempt.
	private String mEmail;
	private String mUser;
	private String mPassword;
	private String mConfirm;

	// UI references.
	private EditText mEmailView;
	private EditText mPasswordView;
	private EditText mUserView;
	private EditText mConfirmView;
	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private ServerCalls sc = new ServerCalls();
	
	private String blockCharacterSet = "~#^|$%&*!?'\" ";

    private InputFilter filter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            if (source != null && blockCharacterSet.contains(("" + source))) {
                return "";
            }
            return null;
        }
    };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_signup);
		
		

		// Set up the login form.
		//mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setFilters(new InputFilter[] { filter});
		mUserView =  (EditText) findViewById(R.id.user);
		//mUserView.setFilters(new InputFilter[] { filter });

		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordView.setFilters(new InputFilter[] { filter });
		
		mConfirmView = (EditText) findViewById(R.id.confPassword);
		mConfirmView.setFilters(new InputFilter[] { filter });
		mConfirmView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true;
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_up_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	@SuppressLint("NewApi")
	public void attemptLogin() {
		

		// Reset errors.
		mEmailView.setError(null);
		mUserView.setError(null);
		mPasswordView.setError(null);
		mConfirmView.setError(null);

		// Store values at the time of the login attempt.
		mEmail = mEmailView.getText().toString();
		mUser = mUserView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mConfirm = mConfirmView.getText().toString();

		boolean cancel = false;
		View focusView = null;

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		}
		
		if (mPassword.length() < 6) {
			mPasswordView.setError("Min. of 6 characters required");
			focusView = mPasswordView;
			cancel = true;
		}
		
		// Check for a valid password.
		if (TextUtils.isEmpty(mConfirm)) {
			mConfirmView.setError(getString(R.string.error_field_required));
			focusView = mConfirmView;
			cancel = true;
		}
		
		if(cancel == false && !mConfirm.equals(mPassword)){
			mConfirmView.setError("Passwords must match");
			focusView = mConfirmView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		}else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}
		
		// Check for a valid username.
		if (TextUtils.isEmpty(mUser)) {
			mUserView.setError(getString(R.string.error_field_required));
			focusView = mUserView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			focusView.requestFocus();
		} else {
			int login = sc.doSignup(mUser, mPassword, mConfirm, mEmail);
			if(login == 1){
				//check if login state is saved
				SharedPreferences settings = this.getSharedPreferences("prefs", 0);
				final SharedPreferences.Editor editor = settings.edit();
			    editor.putString("user", mUser);
			    editor.putString("pass", mPassword);
			    editor.commit();
				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
		        startActivity(intent);
		        finishAffinity();
			}else if(login == 2){
				mEmailView.setError("Email already in use");
				focusView = mEmailView;
				focusView.requestFocus();
			}else if(login == 3){
				mPasswordView.setError("Password mismatch error");
				focusView = mPasswordView;
				focusView.requestFocus();
			}else if(login == 4){
				mUserView.setError("Username already in use");
				focusView = mUserView;
				focusView.requestFocus();
			}else if(login == 5){
				mPasswordView.setError("Min. of 6 charachters required");
				focusView = mPasswordView;
				focusView.requestFocus();
			}else if(login == 6){
				mEmailView.setError("Invalid email");
				focusView = mEmailView;
				focusView.requestFocus();
			}
		}
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();  // optional depending on your needs
	     // code here to show dialog
		overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left);
	     
	}
	
	
}

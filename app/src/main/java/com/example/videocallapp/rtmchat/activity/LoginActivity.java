package com.example.videocallapp.rtmchat.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.videocallapp.R;
import com.example.videocallapp.rtmchat.rtmtutorial.AGApplication;
import com.example.videocallapp.rtmchat.rtmtutorial.ChatManager;
import com.example.videocallapp.rtmchat.utils.MessageUtil;

import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmClient;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = LoginActivity.class.getSimpleName();

    private TextView mLoginBtn;
    private EditText mUserIdEditText;
    private RadioGroup userRadioGroup;
    private RadioButton user1;
    private RadioButton user2;
    private String mUserId;
    private String token;

    private RtmClient mRtmClient;
    private boolean mIsInChat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //mUserIdEditText = findViewById(R.id.user_id);
        mLoginBtn = findViewById(R.id.button_login);
        userRadioGroup = findViewById(R.id.user_radio_group);
        user1 = findViewById(R.id.user1);
        user2 = findViewById(R.id.user2);

        ChatManager mChatManager = AGApplication.the().getChatManager();
        mRtmClient = mChatManager.getRtmClient();

        radioButton();
    }

    private void radioButton() {

        user1.setChecked(true);
        mUserId = "user1";
        token = getString(R.string.agora_token1);

        userRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.user1: {
                        mUserId = "user1";
                        token = getString(R.string.agora_token1);
                    }
                    case R.id.user2: {
                        mUserId = "user2";
                        token = getString(R.string.agora_token2);
                    }
                }
            }
        });
    }

    public void onClickLogin(View v) {
        //mUserId = mUserIdEditText.getText().toString();
        if (mUserId.equals("")) {
            showToast(getString(R.string.account_empty));
        } else if (mUserId.length() > MessageUtil.MAX_INPUT_NAME_LENGTH) {
            showToast(getString(R.string.account_too_long));
        } else if (mUserId.startsWith(" ")) {
            showToast(getString(R.string.account_starts_with_space));
        } else if (mUserId.equals("null")) {
            showToast(getString(R.string.account_literal_null));
        } else {
            mLoginBtn.setEnabled(false);
            doLogin();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoginBtn.setEnabled(true);
        if (mIsInChat) {
            doLogout();
        }
    }

    /**
     * API CALL: login RTM server
     */
    private void doLogin() {
        mIsInChat = true;
        mRtmClient.login(token, mUserId, new ResultCallback<Void>() {
            @Override
            public void onSuccess(Void responseInfo) {
                Log.i(TAG, "login success");
                runOnUiThread(() -> {
                    Intent intent = new Intent(LoginActivity.this, SelectionActivity.class);
                    intent.putExtra(MessageUtil.INTENT_EXTRA_USER_ID, mUserId);
                    startActivity(intent);
                });
            }

            @Override
            public void onFailure(ErrorInfo errorInfo) {
                Log.i("failwww", "login failed: " + errorInfo.getErrorCode());
                runOnUiThread(() -> {
                    mLoginBtn.setEnabled(true);
                    mIsInChat = false;
                    showToast(getString(R.string.login_failed));
                });
            }
        });
    }

    /**
     * API CALL: logout from RTM server
     */
    private void doLogout() {
        mRtmClient.logout(null);
        MessageUtil.cleanMessageListBeanList();
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}
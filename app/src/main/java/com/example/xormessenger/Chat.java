package com.example.xormessenger;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

public class Chat extends AppCompatActivity implements Dialog.DialogListener {
    LinearLayout layout;
    RelativeLayout layout_2;
    ImageView sendButton;
    EditText messageArea;
    static ScrollView scrollView;
    Firebase reference1, reference2;
    ToggleButton encryptButton;
    TextView tview;
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        layout = findViewById(R.id.layout1);
        layout_2 = findViewById(R.id.layout2);
        sendButton = findViewById(R.id.sendButton);
        messageArea = findViewById(R.id.messageArea);
        scrollView = findViewById(R.id.scrollView);
        encryptButton = findViewById(R.id.encryptToggle);

        Firebase.setAndroidContext(this);
        reference1 = new Firebase("https://xor-messenger-d045b.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://xor-messenger-d045b.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);

        messageArea.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scrollDown();
                return false;
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageArea.getText().toString();

                if (!messageText.equals("")) {
                    Map<String, String> map = constructRequestBody(messageText);
                    reference1.push().setValue(map);
                    reference2.push().setValue(map);
                    messageArea.setText("");
                }
                Log.d("DEBUG", Integer.toString(layout.getChildCount()));
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String userName = map.get("user").toString();
                boolean encrypt = Boolean.parseBoolean(map.get("encrypt").toString());

                if(userName.equals(UserDetails.username)) {
                    addMessageBox(message, 1, encrypt);
                }
                else{
                    addMessageBox(message, 2, encrypt);
                }
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(FirebaseError firebaseError) {}
        });
        scrollDown();
    }

    public Map<String, String> constructRequestBody(String msg) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("message", msg);
        map.put("user", UserDetails.username);
        if (encryptButton.isChecked()) {
            map.put("encrypt", "true");
        } else {
            map.put("encrypt", "false");
        }
        return map;
    }

    public void addMessageBox(final String message, int type, final boolean encrypted) {
        final TextView textView = new TextView(Chat.this);
        if (encrypted) {
            textView.setText("<Encrypted Message>");
        } else {
            textView.setText(message);
        }

        if (encrypted) {
            textView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    tview = textView;
                    msg = message;
                    openDialog();
                }
            });
        }

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 7.0f;

        if(type == 1) {
            lp2.gravity = Gravity.RIGHT;
            if (encrypted) {
                textView.setBackgroundResource(R.drawable.bubble_in_enc);
                textView.setTextColor(Color.parseColor("#FFFFFF"));
            } else {
                textView.setBackgroundResource(R.drawable.bubble_in);
            }
        }
        else{
            lp2.gravity = Gravity.LEFT;
            if (encrypted) {
                textView.setBackgroundResource(R.drawable.bubble_out_enc);
                textView.setTextColor(Color.parseColor("#FFFFFF"));
            } else {
                textView.setBackgroundResource(R.drawable.bubble_out);
            }
        }
        textView.setLayoutParams(lp2);
        layout.addView(textView);
        scrollDown();
    }

    public void decryptMessage() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(5000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tview.setClickable(true);
                                tview.setText("<Encrypted Message>");
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
        tview.setText(msg);
        tview.setClickable(false);
        scrollDown();
    }

    public void openDialog() {
        Dialog passDialog = new Dialog();
        passDialog.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void applyTexts(String password) {
        if (password.equals(UserDetails.password)) {
            Toast.makeText(Chat.this, "Correct Password", Toast.LENGTH_SHORT).show();
            decryptMessage();
        } else {
            Toast.makeText(Chat.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
        }
    }

    public static void scrollDown()
    {
        Thread scrollThread = new Thread() {
            public void run() {
                try {
                    sleep(300);
                    scrollView.fullScroll(View.FOCUS_DOWN);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        scrollThread.start();
    }
}

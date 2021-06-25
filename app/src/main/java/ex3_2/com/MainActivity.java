package ex3_2.com;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import ex3_2.com.View.Joystick;

public class MainActivity extends AppCompatActivity {

    RelativeLayout layout_joystick;
    SeekBar rudder, aileron, throttle, elevator;
    TextView txt_rudder, txt_aileron, txt_throttle, txt_elevator;
    EditText ip, port;
    Button buttonConnect;
    Joystick js;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rudder=(SeekBar)findViewById(R.id.Rudder);
        txt_rudder = (TextView)findViewById(R.id.textRudder);
        aileron=(SeekBar)findViewById(R.id.Aileron);
        txt_aileron = (TextView)findViewById(R.id.textAileron);
        elevator=(SeekBar)findViewById(R.id.Elevator);
        txt_elevator = (TextView)findViewById(R.id.textElevator);
        throttle=(SeekBar)findViewById(R.id.Throttle);
        txt_throttle = (TextView)findViewById(R.id.textThrottle);
        ip = (EditText)findViewById(R.id.IP);
        port = (EditText)findViewById(R.id.Port);
        buttonConnect =(Button)findViewById(R.id.Connect);
        layout_joystick = (RelativeLayout)findViewById(R.id.layout_joystick);
        js = new Joystick(getApplicationContext(), layout_joystick, R.drawable.image_button);


        //Determine size of internal button
        js.setStickSize(200, 200);
        // Determine size of the joystick circle
        js.setLayoutSize(600, 600);
        js.setLayoutAlpha(150);
        js.setStickAlpha(100);
        js.setOffset(90);
        js.setMinimumDistance(50);

        // Button connect appear only when both fields are full
        ip.addTextChangedListener(textWatcher);
        port.addTextChangedListener(textWatcher);



        // Rudder between -1 and 1
        rudder.setMax(200);
        rudder.setProgress(100);
        rudder.incrementProgressBy(1);
        rudder.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Make the value decimal and negative
                double p = (double)progress / 100;
                p = p - 1;
                // Keep only two last digits
                DecimalFormat df = new DecimalFormat("#.##");
                p = Double.valueOf(df.format(p));
                // Instead of showing send to model
                String txt = "Rudder " + String.valueOf(p);
                txt_rudder.setText(txt);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        // Throttle between -1 and 1
        throttle.setMax(200);
        throttle.setProgress(100);
        throttle.incrementProgressBy(1);
        throttle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Make the value decimal and negative
                double p = (double)progress / 100;
                p = p - 1;
                // Keep only two last digits
                DecimalFormat df = new DecimalFormat("#.##");
                p = Double.valueOf(df.format(p));
                // Instead of showing send to model
                String txt = "Throttle " + String.valueOf(p);
                txt_throttle.setText(txt);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        // Aileron between -10 and 10
        aileron.setMax(2000);
        aileron.setProgress(1000);
        aileron.incrementProgressBy(1);
        aileron.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Make the value decimal and negative
                double p = (double)progress / 100;
                p = p - 10;
                // Keep only two last digits
                DecimalFormat df = new DecimalFormat("#.##");
                p = Double.valueOf(df.format(p));
                // Instead of showing send to model
                String txt = "Aileron " + String.valueOf(p);
                txt_aileron.setText(txt);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        // Elevator between -10 and 10
        elevator.setMax(2000);
        elevator.setProgress(1000);
        elevator.incrementProgressBy(1);
        elevator.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Make the value decimal and negative
                double p = (double)progress / 100;
                p = p - 10;
                // Keep only two last digits
                DecimalFormat df = new DecimalFormat("#.##");
                p = Double.valueOf(df.format(p));
                // Instead of showing send to model
                String txt = "Elevator " + String.valueOf(p);
                txt_elevator.setText(txt);
                // model.setElevator(p);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        //Joystick
        // Sensitive to when the user put is finger on the joystick
        layout_joystick.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                js.drawStick(arg1);
                // Joystick is clicked and move
                if(arg1.getAction() == MotionEvent.ACTION_DOWN
                        || arg1.getAction() == MotionEvent.ACTION_MOVE) {


                    int direction = js.get8Direction();
                    if(direction == Joystick.STICK_UP) {
                        //  textView5.setText("Direction : Up");
                    } else if(direction == Joystick.STICK_UPRIGHT) {
                        // textView5.setText("Direction : Up Right");
                    } else if(direction == Joystick.STICK_RIGHT) {
                        //  textView5.setText("Direction : Right");
                    } else if(direction == Joystick.STICK_DOWNRIGHT) {
                        // textView5.setText("Direction : Down Right");
                    } else if(direction == Joystick.STICK_DOWN) {
                        // textView5.setText("Direction : Down");
                    } else if(direction == Joystick.STICK_DOWNLEFT) {
                        //   textView5.setText("Direction : Down Left");
                    } else if(direction == Joystick.STICK_LEFT) {
                        //  textView5.setText("Direction : Left");
                    } else if(direction == Joystick.STICK_UPLEFT) {
                        //  textView5.setText("Direction : Up Left");
                    } else if(direction == Joystick.STICK_NONE) {
                        //   textView5.setText("Direction : Center");
                    }
                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                    //  textView1.setText("X :");
                    //textView2.setText("Y :");
                    //textView3.setText("Angle :");
                    //textView4.setText("Distance :");
                    //textView5.setText("Direction :");
                }
                return true;
            }
        });
    }

    public void clickConnect(View view) {
        String ipInput = ip.getText().toString().trim();
        String portInput = port.getText().toString().trim();
        // pass ip and port

        // Show user the connection he is trying to connect to
        String connection = "Connection to IP: " + ipInput + ", port: " + portInput;
        Toast.makeText(this, connection, Toast.LENGTH_SHORT).show();
       // Enable connection button after connection
        buttonConnect.setEnabled(false);
    }
    // Check if text has been enter into text field
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String ipInput = ip.getText().toString().trim();
            String portInput = port.getText().toString().trim();
            buttonConnect.setEnabled(!ipInput.isEmpty() && !portInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


}
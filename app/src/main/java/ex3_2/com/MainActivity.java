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
    TextView txt_rudder, txt_aileron, txt_throttle, txt_elevator,  x, y;
    EditText ip, port;
    Button buttonConnect;
    Joystick js;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Initialization of sliders and their name
        rudder=(SeekBar)findViewById(R.id.Rudder);
        txt_rudder = (TextView)findViewById(R.id.textRudder);
        aileron=(SeekBar)findViewById(R.id.Aileron);
        txt_aileron = (TextView)findViewById(R.id.textAileron);
        elevator=(SeekBar)findViewById(R.id.Elevator);
        txt_elevator = (TextView)findViewById(R.id.textElevator);
        throttle=(SeekBar)findViewById(R.id.Throttle);
        txt_throttle = (TextView)findViewById(R.id.textThrottle);
        // Initialization of ip and port and button connect
        ip = (EditText)findViewById(R.id.IP);
        port = (EditText)findViewById(R.id.Port);
        buttonConnect =(Button)findViewById(R.id.Connect);
        // Initialization joystick
        layout_joystick = (RelativeLayout)findViewById(R.id.layout_joystick);
        js = new Joystick(getApplicationContext(), layout_joystick, R.drawable.image_button);
        // Show value of aileron and elevator when joystick moved
        x = (TextView)findViewById(R.id.x);
        y = (TextView)findViewById(R.id.y);


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

            // Show value of the seekbar rudder
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

            // Show value of the seekbar throttle
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
            // Show value of the seekbar aileron
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

            // Show value of the seekbar elevator
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

                    // Get value of aileron and elevator as string
                    String str_aileron = js.getAileron();
                    String str_elevator = js.getElevator();
                    // Show the user the value of aileron and elevator
                    x.setText("Aileron : " + str_aileron);
                    y.setText("Elevator : " + str_elevator);
                    // Put the seekbar of aileron and elevator to the right value
                    double value_aileron = (Double.parseDouble(str_aileron) + 10) * 100;
                    aileron.setProgress((int)value_aileron);
                    double value_elevator = (Double.parseDouble(str_elevator) + 10) * 100;
                    elevator.setProgress((int)value_elevator);

                    // Send aileron and elevator to model

                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                    // Everything return to be 0 when the joystick is not touch
                    x.setText("Aileron : 0");
                    y.setText("Elevator : 0");
                    aileron.setProgress(0);
                    elevator.setProgress(0);


                }
                return true;
            }
        });
    }

    // Connect button has been click, and ip and port are send
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
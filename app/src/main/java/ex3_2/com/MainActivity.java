package ex3_2.com;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import ex3_2.com.View.Joystick;
import ex3_2.com.Model.*;

public class MainActivity extends AppCompatActivity {

    RelativeLayout layout_joystick;
    SeekBar rudder, aileron, throttle, elevator;
    TextView txt_rudder, txt_aileron, txt_throttle, txt_elevator,  x, y;
    EditText ip, port;
    Button buttonConnect;
    Joystick js;
    SimulatorCommunicator client;
    ExecutorService executor = Executors.newSingleThreadExecutor();
    boolean firstTime = true;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.print("On create entered\n");
        setContentView(R.layout.activity_main);
        // Initialization of sliders and their name
        rudder = (SeekBar) findViewById(R.id.Rudder);
        txt_rudder = (TextView) findViewById(R.id.textRudder);
        aileron = (SeekBar) findViewById(R.id.Aileron);
        txt_aileron = (TextView) findViewById(R.id.textAileron);
        elevator = (SeekBar) findViewById(R.id.Elevator);
        txt_elevator = (TextView) findViewById(R.id.textElevator);
        throttle = (SeekBar) findViewById(R.id.Throttle);
        txt_throttle = (TextView) findViewById(R.id.textThrottle);
        // Initialization of ip and port and button connect
        ip = (EditText) findViewById(R.id.IP);
        port = (EditText) findViewById(R.id.Port);
        buttonConnect = (Button) findViewById(R.id.Connect);
        // Initialization joystick
        layout_joystick = (RelativeLayout) findViewById(R.id.layout_joystick);
        js = new Joystick(getApplicationContext(), layout_joystick, R.drawable.image_button);
        // Show value of aileron and elevator when joystick moved
        x = (TextView) findViewById(R.id.x);
        y = (TextView) findViewById(R.id.y);
        // Initialization of the map info
        client = new SimulatorCommunicator();

    }

    protected void onStart() {
        super.onStart();
        System.out.print("On start entered\n");

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

        // Throttle between 0 and 1
        throttle.setMax(100);
        throttle.incrementProgressBy(1);

        // Aileron between -1 and 1
        aileron.setMax(200);
        aileron.setProgress(100);
        aileron.incrementProgressBy(1);

        // Elevator between -1 and 1
        elevator.setMax(200);
        elevator.setProgress(100);
        elevator.incrementProgressBy(1);
    }

    public void onResume() {
        super.onResume();
        if (!firstTime)
            executor.execute( ()->client.ChangePause());
        firstTime = false;
        System.out.print("On resume entered\n");

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
                double finalP = p;
                executor.execute( ()->client.setRudder((float) finalP));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        throttle.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            // Show value of the seekbar throttle
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Make the value decimal and negative
                double p = (double)progress / 100;
                // Keep only two last digits
                DecimalFormat df = new DecimalFormat("#.##");
                p = Double.valueOf(df.format(p));
                // Instead of showing send to model
                String txt = "Throttle " + String.valueOf(p);
                txt_throttle.setText(txt);
                double finalP = p;
                executor.execute( ()->client.setThrottle((float) finalP));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        aileron.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            // Show value of the seekbar aileron
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Make the value decimal and negative
                double p = (double)progress / 100;
                p = p - 1;
                // Keep only two last digits
                DecimalFormat df = new DecimalFormat("#.##");
                p = Double.valueOf(df.format(p));
                // Instead of showing send to model
                String txt = "Aileron " + String.valueOf(p);
                txt_aileron.setText(txt);
                double finalP = p;
                executor.execute( ()->client.setAileron((float) finalP));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        elevator.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){


            // Show value of the seekbar elevator
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Make the value decimal and negative
                double p = (double)progress / 100;
                p = p - 1;
                // Keep only two last digits
                DecimalFormat df = new DecimalFormat("#.##");
                p = Double.valueOf(df.format(p));
                // Instead of showing send to model
                String txt = "Elevator " + String.valueOf(p);
                txt_elevator.setText(txt);
                double finalP = p;
                executor.execute( ()->client.setElevator((float) finalP));
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
                    double value_aileron = (Double.parseDouble(str_aileron) + 1) * 100;
                    aileron.setProgress((int)value_aileron);
                    double value_elevator = (Double.parseDouble(str_elevator) + 1) * 100;
                    elevator.setProgress((int)value_elevator);

                    // Send aileron and elevator to model
                    double finalValueAileron = value_aileron;
                    executor.execute( ()->client.setAileron((float) finalValueAileron));
                    double finalValueElevator = value_elevator;
                    executor.execute( ()->client.setElevator((float) finalValueElevator));
                } else if(arg1.getAction() == MotionEvent.ACTION_UP) {
                    // Everything return to be 0 when the joystick is not touch
                    x.setText("Aileron : 0");
                    y.setText("Elevator : 0");
                    aileron.setProgress(100);
                    elevator.setProgress(100);
                    executor.execute( ()->client.setAileron(0));
                    executor.execute( ()->client.setElevator(0));
                }
                return true;
            }
        });

    }

    public void onPause() {
        super.onPause();
        System.out.print("On pause entered\n");
        executor.execute( ()->client.ChangePause());
    }

    public void onStop() {
        super.onStop();
        System.out.print("On stop entered\n");
        executor.execute( ()->client.EndFlight());
    }

    public void onDestroy() {
        super.onDestroy();
        System.out.print("On destroy entered\n");
        executor.execute( ()->client.EndFlight());
    }


    // Connect button has been click, and ip and port are send
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void clickConnect(View view) {
        String ipInput = ip.getText().toString().trim();
        String portInput = port.getText().toString().trim();

/**
        Runnable runnable =
                () -> { client.StartFlight(ipInput, portInput); };
        ExecutorService pool = Executors.newFixedThreadPool(1);
        pool.execute(runnable);
*/
        // pass ip and port
        Future<String> s = executor.submit( ()-> client.StartFlight(ipInput, portInput));
        // Show user the connection he is trying to connect to
        try { Toast.makeText(this, s.get(), Toast.LENGTH_SHORT).show(); }
        catch (ExecutionException e) { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
/**
        CompletableFuture.supplyAsync( ()->{return client.StartFlight(ipInput, portInput);})
                .thenAccept(s->Toast.makeText(this, s, Toast.LENGTH_SHORT).show());
*/
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


package com.example.accelerometer;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONObject;



public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetic;
    private Sensor gyroscope;

    private TextView accelerometerData;
    private TextView magneticData;
    private TextView gyroscopeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main); // Layout

        accelerometerData = findViewById(R.id.accelerometerData); // Textview accelerometer
        magneticData = findViewById(R.id.magneticData); // Textview magnetic field sensor
        gyroscopeData = findViewById(R.id.gyroscopeData); // Textview gyroscope


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE); // Sensor Manager

        if (sensorManager != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER); // Accelerometer
            magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD); // Magnetic field
            gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE); // Gyroscope

            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL); // Accelerometer
            sensorManager.registerListener(this,magnetic,SensorManager.SENSOR_DELAY_NORMAL); // Magnetic field
            sensorManager.registerListener(this,gyroscope,SensorManager.SENSOR_DELAY_NORMAL); // Gyroscope
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            String accelerometerText = "Accelerometer:\n" + "X: " + x + "\nY: " + y + "\nZ: " + z;
            accelerometerData.setText(accelerometerText);
        }
        else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            String magneticText = "Magnetic field:\n" + "X: " + x + "\nY: " + y + "\nZ: " + z;
            magneticData.setText(magneticText);
        }
        else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            String gyroscopeText = "Gyroscope:\n" + "X: " + x + "\nY: " + y + "\nZ: " + z;
            gyroscopeData.setText(gyroscopeText);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else if (magnetic != null) {
            sensorManager.registerListener(this, magnetic, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
        }
    }
}
package com.example.apliacacioncomederomascotas.MQTT;


import android.content.Context;
import android.widget.Toast;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MqttHandler {

    private MqttClient client;
    private Context context;

    public MqttHandler(Context context) {
        this.context = context;
    }

    public void connect(String brokerIP, String port, String clientId, String username, String password) {
        try {
            // Set up the persistence layer
            MemoryPersistence persistence = new MemoryPersistence();

            // Initialize the MQTT client
            String brokerUrl = "tcp://" + brokerIP + ":" + port;
            client = new MqttClient(brokerUrl, clientId, persistence);

            // Set up the connection options
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);

            // Set the username and password
            connectOptions.setUserName(username);
            connectOptions.setPassword(password.toCharArray());

            // Connect to the broker
            client.connect(connectOptions);

            // Show connected message
            showToast("Connected to MQTT broker");
        } catch (MqttException e) {
            e.printStackTrace();
            // Show connection error message
            showToast("Failed to connect to MQTT broker");
        }
    }

    public void disconnect() {
        try {
            client.disconnect();
            // Show disconnected message
            showToast("Disconnected from MQTT broker");
        } catch (MqttException e) {
            e.printStackTrace();
            // Show disconnection error message
            showToast("Failed to disconnect from MQTT broker");
        }
    }

    public void publish(String topic, String message) {
        try {
            MqttMessage mqttMessage = new MqttMessage(message.getBytes());
            client.publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void subscribe(String topic) {
        try {
            client.subscribe(topic);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}

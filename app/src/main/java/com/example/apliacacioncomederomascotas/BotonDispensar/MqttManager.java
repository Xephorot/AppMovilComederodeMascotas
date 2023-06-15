package com.example.apliacacioncomederomascotas.BotonDispensar;

import android.content.Context;
import android.widget.Toast;

import com.example.apliacacioncomederomascotas.MQTT.MqttHandler;

public class MqttManager {
    //MQTT Credenciales
    private static final String BROKER_URL = "35.222.113.128";
    private static final String PORT = "1883";
    private static final String CLIENT_ID = "mqttx_9e37747a";
    private static final String USERNAME = "sergio";
    private static final String PASSWORD = "2003";
    private MqttHandler mqttHandler;
    private Context context;

    public MqttManager(Context context) {
        this.context = context;
        mqttHandler = new MqttHandler(context);
    }

    public void connect() {
        mqttHandler.connect(BROKER_URL, PORT, CLIENT_ID, USERNAME, PASSWORD);
    }

    public void publishMessage(String topic, String message) {
        Toast.makeText(context, "Publishing message: " + message, Toast.LENGTH_SHORT).show();
        mqttHandler.publish(topic, message);
    }

    public void subscribeToTopic(String topic) {
        Toast.makeText(context, "Subscribing to topic " + topic, Toast.LENGTH_SHORT).show();
        mqttHandler.subscribe(topic);
    }
}

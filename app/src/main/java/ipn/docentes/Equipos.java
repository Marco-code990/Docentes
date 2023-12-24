package ipn.docentes;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Equipos extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipos);

        Button botonEncender = findViewById(R.id.botonEncender);
        botonEncender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                encenderComputadora(v);
            }
        });
    }

    public void encenderComputadora(View view) {
        String macAddress = "00:1D:92:E6:6E:13"; // Dirección MAC de la computadora
        String ipAddress = "192.168.100.169"; // Dirección IP de la computadora

        // Crear instancia de WakeOnLanTask y ejecutar la tarea asíncrona
        new WakeOnLanTask().execute(macAddress, ipAddress);
    }

    // Clase interna WakeOnLanTask que extiende AsyncTask para operaciones en segundo plano
    private static class WakeOnLanTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            try {
                String macAddress = params[0];
                String ipAddress = params[1];

                byte[] macBytes = getMacBytes(macAddress);
                byte[] magicPacket = new byte[6 + 16 * macBytes.length];

                // Llenar el paquete mágico con 0xFF
                for (int i = 0; i < 6; i++) {
                    magicPacket[i] = (byte) 0xFF;
                }

                // Repetir la dirección MAC 16 veces en el paquete
                for (int i = 6; i < magicPacket.length; i += macBytes.length) {
                    System.arraycopy(macBytes, 0, magicPacket, i, macBytes.length);
                }

                InetAddress address = InetAddress.getByName(ipAddress);
                DatagramPacket packet = new DatagramPacket(magicPacket, magicPacket.length, address, 9);
                DatagramSocket socket = new DatagramSocket();
                socket.send(packet);
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        private static byte[] getMacBytes(String macStr) throws IllegalArgumentException {
            byte[] bytes = new byte[6];
            String[] hex = macStr.split("(\\:|\\-)");
            if (hex.length != 6) {
                throw new IllegalArgumentException("Dirección MAC no válida");
            }
            try {
                for (int i = 0; i < 6; i++) {
                    bytes[i] = (byte) Integer.parseInt(hex[i], 16);
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Dígitos no hexadecimales en la dirección MAC");
            }
            return bytes;
        }
    }
}
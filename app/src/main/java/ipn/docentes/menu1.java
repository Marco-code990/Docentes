package ipn.docentes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
public class menu1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu1);

        CardView cardView1 = findViewById(R.id.card1);
        // Agrega un OnClickListener al CardView
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define aquí lo que deseas hacer cuando se haga clic en el CardView
                // Por ejemplo, puedes abrir una nueva actividad
                Intent intent = new Intent(menu1.this, Profile.class);
                startActivity(intent);
            }
        });

        CardView cardView2 = findViewById(R.id.card2);
        // Agrega un OnClickListener al CardView
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define aquí lo que deseas hacer cuando se haga clic en el CardView
                // Por ejemplo, puedes abrir una nueva actividad
                Intent intent = new Intent(menu1.this, Laboratorios.class);
                startActivity(intent);
            }
        });

        CardView cardView3 = findViewById(R.id.card3);
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String targetIpAddress = "192.168.168.169"; // Cambia esto con la dirección IP de la PC de destino
                String targetMacAddress = "00:1D:92:E6:6E:13"; // Cambia esto con la dirección MAC de la tarjeta de red de la PC de destino

                try {
                    WakeonLan.sendWakeOnLan(targetIpAddress, targetMacAddress);
                    Toast.makeText(menu1.this, "Se envió el paquete de Wake-on-LAN", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(menu1.this, "Error al enviar el paquete de Wake-on-LAN", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });


        CardView cardView4 = findViewById(R.id.card4);
        // Agrega un OnClickListener al CardView
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define aquí lo que deseas hacer cuando se haga clic en el CardView
                // Por ejemplo, puedes abrir una nueva actividad
                Intent intent = new Intent(menu1.this, reportes.class);
                startActivity(intent);
            }
        });
    }
}
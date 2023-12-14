package ipn.docentes;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Laboratorios extends AppCompatActivity implements LabAdapter.OnLabItemClickListener {

    private RecyclerView recyclerView;
    private LabAdapter labAdapter;
    private List<LabModel> labList;  // Agrega esta línea

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratorios);

        recyclerView = findViewById(R.id.lab_1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Toast.makeText(this, "Laboratorio no disponible", Toast.LENGTH_SHORT).show();
        // Ejecutar la tarea asíncrona para obtener los datos de laboratorios
        new FetchLabData().execute();
    }

    // Clase para realizar la tarea asíncrona
    private class FetchLabData extends AsyncTask<Void, Void, List<LabModel>> {

        @Override
        protected List<LabModel> doInBackground(Void... voids) {
            List<LabModel> labList = new ArrayList<>();

            try {
                URL url = new URL("http://192.168.100.148/Administrador/obtener_lab.php");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();

                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                JSONArray jsonArray = new JSONArray(stringBuilder.toString());

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("id");
                    String nombre = jsonObject.getString("nombre");
                    int capacidad = jsonObject.getInt("capacidad");
                    String estado = jsonObject.getString("estado");

                    LabModel labModel = new LabModel(id, nombre, capacidad, estado);
                    labList.add(labModel);
                }

                reader.close();
                connection.disconnect();
            } catch (IOException | JSONException e) {
                Log.e("Error", "Error al obtener datos: " + e.getMessage());
            }

            return labList;
        }

        @Override
        protected void onPostExecute(List<LabModel> labList) {
            super.onPostExecute(labList);

            // Configurar el adaptador con los datos obtenidos y establecerlo en el RecyclerView
            labAdapter = new LabAdapter(labList, Laboratorios.this);
            recyclerView.setAdapter(labAdapter);
        }
    }

    @Override
    public void onLabItemClick(int position) {
        // Obtén el laboratorio seleccionado
        LabModel selectedLab = labList.get(position);

        // Cambia el estado del laboratorio (por ejemplo, de "Activo" a "Inactivo" o viceversa)
        String newStatus = (selectedLab.getEstado().equals("Activo")) ? "Inactivo" : "Activo";
        selectedLab.setEstado(newStatus);

        // Notifica al adaptador sobre el cambio
        labAdapter.notifyItemChanged(position);

        // Muestra un Toast con el mensaje correspondiente
        Toast.makeText(this, "Laboratorio reservado con exito", Toast.LENGTH_SHORT).show();

        // Muestra un mensaje en el log para indicar que se ha actualizado el estado
        Log.d("Laboratorios", "Estado del laboratorio actualizado: " + selectedLab.getEstado());
        // Realiza alguna acción adicional según tu lógica
        if (selectedLab.getEstado().equals("Activo")) {
            Log.d("Laboratorios", "Laboratorio reservado");
            // Puedes agregar aquí cualquier lógica adicional cuando se reserve un laboratorio
        } else {
            Log.d("Laboratorios", "Laboratorio liberado");
            // Puedes agregar aquí cualquier lógica adicional cuando se libere un laboratorio
        }
    }

}



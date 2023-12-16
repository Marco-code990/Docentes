package ipn.docentes;

public class LabModel {
    private int id;
    private String nombre;
    private int capacidad;
    private String estado;

    public LabModel(int id, String nombre, int capacidad, String estado) {
        this.id = id;
        this.nombre = nombre;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

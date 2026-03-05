package casino;

public class CartasEspeciales {
	
	
	private String nombre; 
    private Habilidades habilidad;

    public CartasEspeciales() {

    }

    public CartasEspeciales(String nombre, Habilidades habilidad) {
        this.nombre = nombre;
        this.habilidad = habilidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Habilidades getHabilidad() {
        return habilidad;
    }

    public void setHabilidad(Habilidades habilidad) {
        this.habilidad = habilidad;
    }

}

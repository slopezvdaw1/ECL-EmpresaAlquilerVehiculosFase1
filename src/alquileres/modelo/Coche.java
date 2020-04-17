package alquileres.modelo;

/**
 * Un coche es un vehículo que añade un nº de plazas
 * 
 * El coste final de alquiler depende no solo del nº de días de alquiler 
 * sino también del nº de plazas. Si el nº de plazas es > 4 se añaden 5€ más por día
 * @author - Sara L�pez Vicente
 */
public class Coche extends Vehiculo {

	private int numeroPlazas;
	
	public Coche(String matricula, String marca, String modelo, double precioDia, int numeroPlazas) {
		super(matricula, marca, modelo, precioDia);
		this.numeroPlazas = numeroPlazas;
	}

	public int getNumeroPlazas() {
		return numeroPlazas;
	}

	public void setNumeroPlazas(int numeroPlazas) {
		this.numeroPlazas = numeroPlazas;
	}
	
	public double calcularPrecioAlquiler(int dias) {
		double precio = super.calcularPrecioAlquiler(dias);
		if (numeroPlazas > 4) {
			return precio + dias * 5;
		}
		return precio;
	}
	
	public String toString() {
		return super.toString() + "\t|\tPlazas: " + numeroPlazas;
	}
}

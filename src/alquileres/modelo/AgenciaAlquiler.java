package alquileres.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * La clase guarda en una colección List (un ArrayList) la flota de vehículos
 * que una agencia de alquiler posee
 * 
 * Los vehículos se modelan como un interface List que se instanciará como una
 * colección concreta ArrayList
 * @author - Sara L�pez Vicente
 */
public class AgenciaAlquiler {
	private String nombre; // el nombre de la agencia
	private List<Vehiculo> flota; // la lista de vehículos

	/**
	 * Constructor
	 * 
	 * @param nombre el nombre de la agencia
	 */
	public AgenciaAlquiler(String nombre) {
		this.nombre = nombre;
		this.flota = new ArrayList<>();
	}

	/**
	 * añade un nuevo vehículo solo si no existe
	 * 
	 */
	public void addVehiculo(Vehiculo v) {
		if (!flota.contains(v)) {
			flota.add(v);
		}
	}

	/**
	 * Extrae los datos de una línea, crea y devuelve el vehículo
	 * correspondiente
	 * 
	 * Formato de la línea:
	 * C,matricula,marca,modelo,precio,plazas para coches
	 * F,matricula,marca,modelo,precio,volumen para furgonetas
	 * 
	 * 
	 * Asumimos todos los datos correctos. Puede haber espacios antes y después
	 * de cada dato
	 */
	private Vehiculo obtenerVehiculo(String linea) {
		String[] datos = linea.split(",");
		for (String dato: datos) {
			dato.trim();
		}
		String matricula = datos[1];
		String marca = datos[2];
		String modelo = datos[3];
		double precio = Double.parseDouble(datos[4]);
		if (datos[0].equals("C")) {
			int plazas = Integer.parseInt(datos[5]);
			Coche c = new Coche(matricula, marca, modelo, precio, plazas);
			return c;
		}
		double vol = Double.parseDouble(datos[5]);
		Furgoneta furgo = new Furgoneta(matricula, marca, modelo, precio, vol);
		return furgo;
	}

	/**
	 * La clase Utilidades nos devuelve un array con las líneas de datos
	 * de la flota de vehículos  
	 */
	public void cargarFlota() {
		String[] datos = Utilidades.obtenerLineasDatos();
		String error = null;
		try {
			for (String linea : datos) {
				error = linea;
				Vehiculo vehiculo = obtenerVehiculo(linea);
				this.addVehiculo(vehiculo);

			}
		}
		catch (Exception e) {
			System.out.println(error);
		}

	}

	/**
	 * Representación textual de la agencia
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Veh�culos en alquiler de la agencia " + nombre + "\n");
		sb.append("\nTotal veh�culos: " + flota.size());
		for (Vehiculo v: flota) {
			sb.append("\n" + v.toString() 
			+ "\n\n-----------------------------------------------------");
		}
		return sb.toString();

	}

	/**
	 * Busca todos los coches de la agencia
	 * Devuelve un String con esta información y lo que
	 * costaría alquilar cada coche el nº de días indicado * 
	 *  
	 */
	public String buscarCoches(int dias) {
		StringBuilder sb = new StringBuilder();
		for (Vehiculo v: flota) {
			if (v instanceof Coche) {
				sb.append(v.toString());
				sb.append("\nCoste alquiler " + dias + "d�as: " + v.calcularPrecioAlquiler(dias) + "�"
						+ "\n---------------");
			}
		}
		return sb.toString();

	}

	/**
	 * Obtiene y devuelve una lista de coches con más de 4 plazas ordenada por
	 * matrícula - Hay que usar un iterador
	 * 
	 */
	public List<Coche> cochesOrdenadosMatricula() {
		ArrayList<Coche> ordenados = new ArrayList<>();
		Iterator<Vehiculo> it = flota.iterator();
		while (it.hasNext()) {
			Vehiculo v = it.next();
			if ((v instanceof Coche) && ((Coche) v).getNumeroPlazas() > 4) {
				ordenados.add((Coche) v);
			}
		}
		Collections.sort(ordenados);
		return ordenados;
	}

	/**
	 * Devuelve la relación de todas las furgonetas ordenadas de
	 * mayor a menor volumen de carga
	 * 
	 */
	public List<Furgoneta> furgonetasOrdenadasPorVolumen() {
		ArrayList<Furgoneta> ordenadas = new ArrayList<>();
		Iterator<Vehiculo> it = flota.iterator();
		while (it.hasNext()) {
			Vehiculo v = it.next();
			if (v instanceof Furgoneta) {
				ordenadas.add((Furgoneta) v);
			}
		}
		return ordenadas;

	}

	/**
	 * Genera y devuelve un map con las marcas (importa el orden) de todos los
	 * vehículos que hay en la agencia como claves y un conjunto (importa el orden) 
	 * de los modelos en cada marca como valor asociado
	 */
	public Map<String, Set<String>> marcasConModelos() {
		Map<String, Set<String>> mapa = new TreeMap<String, Set<String>>();
		for (Vehiculo v: flota) {
			String marca = v.getMarca();
			String modelo = v.getModelo();
			if (mapa.containsKey(marca)) {
				mapa.get(marca).add(modelo);
			}
			else {
				TreeSet<String> modelos = new TreeSet<String>();
				modelos.add(modelo);
				mapa.put(marca, modelos);
			}
		}
		return mapa;
	}

}

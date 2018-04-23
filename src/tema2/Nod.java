package tema2;

/**
 * Nod este o clasa abstracta in care declar metodele ce urmeaza sa fie aplicate
 * nodurilor in functie de versiunea lor.
 * 
 * @author Andreea
 */
public abstract class Nod {
	/**
	 * Metoda de adugare a unui nod nou.
	 * 
	 * @param new_node
	 *            - nodul nou
	 */
	public abstract void add_node(Nod new_node);

	/**
	 * Metoda de stergere a unui nod.
	 * 
	 * @param node
	 *            - nodul ce urmeaza a fi sters
	 */
	public abstract void remove_node(Nod node);

	/**
	 * Metoda de extragere a clasei nodului.
	 * 
	 * @return String - clasa nodului
	 */
	public abstract String get_class();

	/**
	 * Metoda de extragere a versiunii nodului.
	 * 
	 * @return int - versiunea nodului
	 */
	public abstract int get_version();

	/**
	 * Metoda de extragere a id-ului nodului.
	 * 
	 * @return int - id-ul nodului
	 */
	public abstract int get_id();

	/**
	 * Metoda de extragere a numelui nodului.
	 * 
	 * @return - numele nodului
	 */
	public abstract String get_name();

	/**
	 * Metoda de setare a numelui nodului.
	 * 
	 * @param name
	 */
	public abstract void set_name(String name);

	/**
	 * Metoda in care verific versiunea nodului si in functie de aceasta
	 * returnez LIST, VECTOR sau SET.
	 * 
	 * @return String - LIST/VECTOR/SET
	 */
	public abstract String get_version_text();

	/**
	 * Metoda prin care extrag un nod de la pozitia index.
	 * 
	 * @param index
	 *            - indexul nodului dorit
	 * @return Nod - nodul de la pozitia index
	 */
	public abstract Nod get_node(int index);

	/**
	 * Metoda de afisare a listei/vectorului/setului de adiacenta.
	 */
	public abstract void print();

	/**
	 * Metoda de extragere a numarului de vecini.
	 * 
	 * @return int - numarul de vecini
	 */
	public abstract int get_neighbours_nr();
}

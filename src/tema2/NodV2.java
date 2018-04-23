package tema2;

import java.util.ArrayList;

/**
 * Aceasta clasa mentine nodurile adiacente folosind un VECTOR.
 * 
 * @author Andreea
 *
 */
public class NodV2 extends Nod {
	private String node_class;
	private int version;
	private int id;
	private String name;
	int array_size = 0; // dimensiune vector vecini
	ArrayList<Nod> array = new ArrayList<Nod>(); // vector vecini

	/**
	 * Constructor fara parametri pentru un nod de versiune 2.
	 */
	public NodV2() {
		this.array_size = 0;
		this.node_class = null;
		this.version = 0;
		this.id = 0;
		this.name = null;
	}

	/**
	 * Constructor cu parametri pentru un nod de versiune 2.
	 * 
	 * @param node_class
	 *            - calsa nodului
	 * @param version
	 *            - versiunea nodului
	 * @param id
	 *            - id-ul nodului
	 * @param name
	 *            - numele nodului
	 */
	public NodV2(String node_class, int version, int id, String name) {
		this.array_size = 0;
		this.node_class = node_class;
		this.version = version;
		this.id = id;
		this.name = name;
	}

	@Override
	public void add_node(Nod new_node) {
		if (!array.contains(new_node))
			array.add(new_node);
		else
			return;
	}

	@Override
	public String get_class() {
		return this.node_class;
	}

	@Override
	public int get_version() {
		return this.version;
	}

	@Override
	public int get_id() {
		return this.id;
	}

	@Override
	public String get_name() {
		return this.name;
	}

	@Override
	public String get_version_text() {
		return "VECTOR";
	}

	@Override
	public int get_neighbours_nr() {
		return array.size();
	}

	@Override
	public Nod get_node(int index) {
		return array.get(index);
	}

	@Override
	public void print() {
		if (get_neighbours_nr() == 0)
			return;
		else {
			for (int i = 0; i < get_neighbours_nr(); i++)
				System.out.println(array.get(i).get_name());
		}
	}

	@Override
	public void remove_node(Nod node) {
		int index = array.indexOf(node);
		array.remove(index);
	}

	@Override
	public void set_name(String name) {
		this.name = name;
	}
}
package tema2;

import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 * Aceasta clasa mentine nodurile adiacente folosind un SET.
 * 
 * @author Andreea
 *
 */
public class NodV3 extends Nod {
	private String node_class;
	private int version;
	private int id;
	private String name;
	LinkedHashSet<Nod> hs = new LinkedHashSet<Nod>();

	/**
	 * Constructor fara parametri pentru un nod de versiune 3.
	 */
	public NodV3() {
		this.node_class = null;
		this.version = 0;
		this.id = 0;
		this.name = null;
	}

	/**
	 * Constructor cu parametri pentru un nod de versiune 3.
	 * 
	 * @param node_class
	 *            - calsa nodului
	 * @param version
	 *            -versiunea nodului
	 * @param id
	 *            - id-ul nodului
	 * @param name
	 *            - numele nodului
	 */
	public NodV3(String node_class, int version, int id, String name) {
		this.node_class = node_class;
		this.version = version;
		this.id = id;
		this.name = name;
	}

	@Override
	public void add_node(Nod new_node) {
		hs.add(new_node);
	}

	@Override
	public String get_class() {
		return node_class;
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
		return "SET";
	}

	@Override
	public int get_neighbours_nr() {
		return hs.size();
	}

	@Override
	public Nod get_node(int index) {
		int ind = 0;
		Iterator<Nod> it = hs.iterator();
		while (it.hasNext()) {
			Nod el = (Nod) it.next();
			if (ind == index) {
				return el;
			}
			ind++;
		}
		return null;
	}

	@Override
	public void print() {
		Iterator<Nod> it = hs.iterator();
		while (it.hasNext()) {
			Nod el = (Nod) it.next();
			System.out.println(el.get_name());
		}
	}

	@Override
	public void remove_node(Nod node) {
		String temp_name = node.get_name();
		Iterator<Nod> it = hs.iterator();
		while (it.hasNext()) {
			Nod el = (Nod) it.next();
			if (el.get_name().equals(temp_name)) {
				it.remove();
			}
		}
	}

	@Override
	public void set_name(String name) {
		this.name = name;
	}
}
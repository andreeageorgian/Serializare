package tema2;

import java.util.LinkedList;

/**
 * Aceasta clasa mentine nodurile adiacente folosind o LISTA.
 * 
 * @author Andreea
 *
 */
public class NodV1 extends Nod {
	private String node_class;
	private int version;
	private int id;
	private String name;
	LinkedList<Nod> linked_list = new LinkedList<Nod>();

	/**
	 * Constructor fara parametri pentru un nod de versiune 1.
	 */
	public NodV1() {
		this.node_class = null;
		this.version = 0;
		this.id = 0;
		this.name = null;
	}

	/**
	 * Constructor cu parametri pentru un nod de versiune 1.
	 * 
	 * @param pnode_class
	 *            - clasa nodului
	 * @param pversion
	 *            - versiunea nodului
	 * @param pid
	 *            - id-ul nodului
	 * @param pname
	 *            -numele nodului
	 */
	public NodV1(String pnode_class, int pversion, int pid, String pname) {
		this.node_class = pnode_class;
		this.version = pversion;
		this.id = pid;
		this.name = pname;
	}

	@Override
	public void add_node(Nod new_node) {
		if (!linked_list.contains(new_node))
			linked_list.add(new_node);
		else
			return;
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
	public void set_name(String name) {
		this.name = name;
	}

	@Override
	public String get_version_text() {
		return "LIST";
	}

	@Override
	public Nod get_node(int index) {
		return linked_list.get(index);
	}

	@Override
	public void print() {
		if (get_neighbours_nr() == 0)
			return;
		else {
			for (int i = 0; i < get_neighbours_nr(); i++)
				System.out.println(linked_list.get(i).get_name());
		}
	}

	@Override
	public int get_neighbours_nr() {
		return linked_list.size();
	}

	@Override
	public void remove_node(Nod node) {
		int index = linked_list.indexOf(node);
		linked_list.remove(index);
	}
}
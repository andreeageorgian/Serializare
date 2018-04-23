package tema2;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * In aceasta clasa aveam implementate toate operatiile ce se pot efectua pe
 * graf.
 * 
 * @author Andreea
 */
public class Graph {
	Nod[] nodes_array;
	int nodes_array_length = 0;
	static int nodes_nr;
	ArrayList<String> serialized = new ArrayList<String>();
	static FileOutputStream fOut;
	static PrintStream ps;
	private BufferedReader reader;

	/**
	 * Constructor pentru un graf nou.
	 */
	public Graph() {
		nodes_array = new Nod[100];
		nodes_array_length = 0;
	}

	/**
	 * Aceasta metoda introduce un nod nou in vectorul de noduri al grafului.
	 * 
	 * @param name
	 *            - numele nodului nou
	 * @param node_class
	 *            - clasa nodului nou
	 * @param version
	 *            - versiunea nodului nou
	 * @param id
	 *            - id nodului nou
	 */
	public void add_Node(String name, String node_class, int version, int id) {
		Nod new_node;
		// facem un nod nou in functie de versiune
		if (version == 1) {
			new_node = new NodV1(node_class, version, id, name);
		} else if (version == 2) {
			new_node = new NodV2(node_class, version, id, name);
		} else {
			new_node = new NodV3(node_class, version, id, name);
		}

		// adaugam noul nod la vectorul de noduri
		if (nodes_array_length < nodes_array.length) {
			nodes_array[nodes_array_length++] = new_node;
		} else {
			Nod[] array_copy = null;
			array_copy = new Nod[nodes_array.length * 2];
			System.arraycopy(nodes_array, 0, array_copy, 0, nodes_array.length);
			nodes_array = array_copy;
			nodes_array[nodes_array_length++] = new_node;
		}
	}

	/**
	 * Aceasta metoda adauga o nou muchie in graf.
	 * 
	 * @param name1
	 *            - numele unui capat al muchiei
	 * @param name2
	 *            - numele celuilalt capat al muchiei
	 */
	public void AddM(String name1, String name2) {
		String temp_name = null;
		Nod temp_node1 = null;
		Nod temp_node2 = null;

		// parcurgem vectorul de noduri pentru a
		// identifica capetele muchiei
		for (int i = 0; i < nodes_array_length; i++) {
			temp_name = nodes_array[i].get_name();
			if (temp_name.equals(name1)) {
				temp_node1 = nodes_array[i];
			}
			if (temp_name.equals(name2)) {
				temp_node2 = nodes_array[i];
			}
		}

		// adaugam muchia
		for (int i = 0; i < nodes_array_length; i++) {
			temp_name = nodes_array[i].get_name();
			if (temp_name.equals(name1)) {
				nodes_array[i].add_node(temp_node2);
			}
			if (temp_name.equals(name2)) {
				nodes_array[i].add_node(temp_node1);
			}
		}
	}

	/**
	 * Aceasta metoda sterge o muchie din graf.
	 * 
	 * @param name1
	 *            - numele unui capat la muchiei
	 * @param name2
	 *            - numele celuilalt capat al muchiei
	 */
	public void DelM(String name1, String name2) {
		String temp_name = null;

		// identificam nodurile in vectorul de noduri ale grafului
		// parcurgem vecinii si il stergem pe cel corespunzator
		for (int i = 0; i < nodes_array_length; i++) {
			temp_name = nodes_array[i].get_name();
			if (temp_name.equals(name1)) {
				for (int j = 0; j < nodes_array[i].get_neighbours_nr(); j++) {
					if (nodes_array[i].get_node(j).get_name().equals(name2)) {
						nodes_array[i].remove_node(nodes_array[i].get_node(j));
					}
				}
			}
			if (temp_name.equals(name2)) {
				for (int j = 0; j < nodes_array[i].get_neighbours_nr(); j++) {
					if (nodes_array[i].get_node(j).get_name().equals(name1)) {
						nodes_array[i].remove_node(nodes_array[i].get_node(j));
					}
				}
			}
		}
	}

	/**
	 * Aceasta metoda sterge un nod din graf.
	 * 
	 * @param node_name
	 *            - nodul ce urmeaza sa fie sters
	 */
	public void Del(String node_name) {
		// parcurgem nodurile grafului si identificam nodul ce urmeaza a fi
		// sters
		int k = 0;
		Nod[] temp_array = new Nod[Graph.nodes_nr - 1];
		for (int i = 0; i < Graph.nodes_nr; i++) {
			if (!nodes_array[i].get_name().equals(node_name)) {
				temp_array[k] = nodes_array[i];
				k++;
			} else {
				for (int j = 0; j < nodes_array[i].get_neighbours_nr(); j++)
					DelM(nodes_array[i].get_name(), nodes_array[i].get_node(j).get_name());
			}
		}
		Graph.nodes_nr = k;
		System.arraycopy(temp_array, 0, nodes_array, 0, Graph.nodes_nr);
	}

	/**
	 * Metoda pentru printarea de tab-uri, folositoare pentru serializare.
	 * 
	 * @param nivel
	 * @param ps
	 *            - fisierul in care vor fi afisate
	 */
	public void print_tabs(int nivel, PrintStream ps) {
		for (int j = 0; j <= nivel - 1; j++) {
			ps.print("\t");
		}
	}

	/**
	 * Aceasta metoda realizeaza serializarea grafului. Incepand de la nodul de
	 * start, il adaug unei liste de serializare, afisez linia corespunzatoare
	 * (cu "Object"), apoi incep sa parcurg vecinii. Pentru fiecare vecin,
	 * verific daca a mai fost serializat si in functie de rezultat apelez
	 * recursiv metoda pentru vecinul respectiv, altfel afisez linie cu
	 * "Reference". In cazul in care lista de adiacenta are un numar de noduri
	 * egal cu vecinii nodului de start + 1, parasesc functia.
	 * 
	 * @param start_node
	 *            - nodul de start
	 * @param nivel
	 * @param ps
	 *            - fisierul in care vom scrie
	 * @param count
	 *            - parametru prin intermediul caruia transmit numarul de vecini
	 *            al nodului de start + 1
	 * @param parent
	 *            - nodul parinte
	 */
	public void serialize(Nod start_node, int nivel, PrintStream ps, int count, Nod parent) {
		// daca numarul de noduri serializate este egal cu numarul de vecini ai
		// nodului de start + 1, parasim functia
		if (serialized.size() == count) {
			print_tabs(nivel, ps);
			ps.println("<Reference class=\"" + start_node.get_class() + "\" Version=\"" + start_node.get_version()
					+ "\" id=\"" + start_node.get_id() + "\" parent_name=\"" + parent.get_name() + "\">");
			return;
		} else {
			print_tabs(nivel, ps);
			ps.println("<Object class=\"" + start_node.get_class() + "\" Version=\"" + start_node.get_version()
					+ "\" id=\"" + start_node.get_id() + "\">");
			print_tabs(nivel, ps);
			ps.println("\t<Nume>" + start_node.get_name() + "</Nume>");
			print_tabs(nivel, ps);
			ps.println("\t<" + start_node.get_version_text() + ">");
			nivel++;
			serialized.add(start_node.get_name());
		}

		// parcurgem vectorul de vecini si verificam daca vecinul a mai fost
		// serializat
		for (int i = 0; i < start_node.get_neighbours_nr(); i++) {
			Nod n = start_node.get_node(i);
			if (n != null && serialized.contains(n.get_name()) == false) {
				serialize(n, nivel + 1, ps, count, start_node);
			} else if (serialized.contains(n.get_name()) == true) {
				print_tabs(nivel, ps);
				ps.println("\t<Reference class=\"" + n.get_class() + "\" Version=\"" + n.get_version() + "\" id=\""
						+ n.get_id() + "\" parent_name=\"" + start_node.get_name() + "\">");
			}
		}

		print_tabs(nivel, ps);
		ps.println("</" + start_node.get_version_text() + ">");
		print_tabs(nivel - 1, ps);
		ps.println("</Object>");
	}

	/**
	 * Aceasta metoda realizeaza deserializarea grafului. De fiecare data cand
	 * gasesc o linie care contine "Object", adaug nodul respectiv vectorului de
	 * noduri al grafului. Apoi, de fiecare data cand gasesc o linie ce contine
	 * "Reference" adaug o noua muchie intre nodul respectiv si nodul parinte,
	 * retinut cu ajutorul argumentului parent_name de la serializare. In
	 * acelasi timp cu deserializarea verific si operatiile de cast.
	 * 
	 * @param file_name
	 *            - numele fisierului din care urmeaza sa citim
	 * @param param_settings
	 *            - vectorul de versiuni ale NodA, NodB, NodC
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public void deserialize(String file_name, String[] param_settings) throws NumberFormatException, IOException {

		String cast_file = "Deserialize_";
		cast_file = cast_file.concat(file_name);
		cast_file = cast_file.concat("_CAST.log");
		FileOutputStream fOut = new FileOutputStream(cast_file);
		PrintStream ps = new PrintStream(fOut);

		String des_category = null; // NodA, NodB sau NodC
		int des_id = 0;
		String des_name = null;
		String des_type_text = null;
		int des_version = 0;
		String line = null;
		int[] pos = null;
		int space_nr;
		int[] init_param_settings = new int[3];

		reader = new BufferedReader(new FileReader(file_name));
		// parcurgem fisierul si refacem vecotul de noduri ale grafului
		while ((line = reader.readLine()) != null) {
			if (line.contains("<Object")) {
				pos = new int[3];
				space_nr = 0;
				for (int i = 0; i <= line.length() - 1; i++) {
					if (line.charAt(i) == ' ') {
						pos[space_nr] = i;
						space_nr++;
					}
				}
				des_category = line.substring(pos[0] + 8, pos[1] - 1);
				des_id = Integer.parseInt(line.substring(pos[2] + 5, line.length() - 2));
				line = reader.readLine();
				des_name = line.substring(line.indexOf('>') + 1, line.indexOf('/') - 1);
				line = reader.readLine();
				des_type_text = line.substring(line.indexOf('<') + 1, line.indexOf('>'));
				if (des_type_text.equals("LIST"))
					des_version = 1;
				else if (des_type_text.equals("VECTOR"))
					des_version = 2;
				else
					des_version = 3;
				if (des_category.equals("NodA"))
					init_param_settings[0] = des_version;
				else if (des_category.equals("NodB"))
					init_param_settings[1] = des_version;
				else
					init_param_settings[2] = des_version;
				add_Node(des_name, des_category, des_version, des_id);
				Graph.nodes_nr++;
			}
		}

		// parcurgem vectorul de noduri pentru a verifica cast
		for (int i = 0; i < Graph.nodes_nr; i++) {
			if (nodes_array[i].get_class().equals("NodA")) {
				// daca se incerca versiune noua -> versiune veche
				if (nodes_array[i].get_version() > Integer.parseInt(param_settings[0]))
					ps.println("Fail cast " + nodes_array[i].get_class() + " " + nodes_array[i].get_name()
							+ " from Version=\"" + nodes_array[i].get_version() + "\"" + " to Version=\""
							+ Integer.parseInt(param_settings[0]));
				else
					ps.println("OK cast " + nodes_array[i].get_class() + " " + nodes_array[i].get_name()
							+ " from Version=\"" + nodes_array[i].get_version() + "\"" + " to Version=\""
							+ Integer.parseInt(param_settings[0]));
			} else if (nodes_array[i].get_class().equals("NodB")) {
				if (nodes_array[i].get_version() > Integer.parseInt(param_settings[1]))
					ps.println("Fail cast " + nodes_array[i].get_class() + " " + nodes_array[i].get_name()
							+ " from Version=\"" + nodes_array[i].get_version() + "\"" + " to Version=\""
							+ Integer.parseInt(param_settings[1]));
				else
					ps.println("OK cast " + nodes_array[i].get_class() + " " + nodes_array[i].get_name()
							+ " from Version=\"" + nodes_array[i].get_version() + "\"" + " to Version=\""
							+ Integer.parseInt(param_settings[1]));
			} else if (nodes_array[i].get_class().equals("NodC")) {
				if (nodes_array[i].get_version() > Integer.parseInt(param_settings[0]))
					ps.println("Fail cast " + nodes_array[i].get_class() + " " + nodes_array[i].get_name()
							+ " from Version=\"" + nodes_array[i].get_version() + "\"" + " to Version=\""
							+ Integer.parseInt(param_settings[2]));
				else
					ps.println("OK cast " + nodes_array[i].get_class() + " " + nodes_array[i].get_name()
							+ " from Version=\"" + nodes_array[i].get_version() + "\"" + " to Version=\""
							+ Integer.parseInt(param_settings[2]));
			}
		}

		reader = new BufferedReader(new FileReader(file_name));
		// parcurgem fisierul pentru a face legaturile dintre noduri
		while ((line = reader.readLine()) != null) {
			int ref_id = 0;
			String parent_node;
			String aux_line;

			if (line.contains("<Reference")) {
				aux_line = line.substring(line.indexOf('<'), line.lastIndexOf('>') + 1);
				pos = new int[4];
				space_nr = 0;
				for (int i = 0; i <= aux_line.length() - 1; i++) {
					if (aux_line.charAt(i) == ' ') {
						pos[space_nr] = i;
						space_nr++;
					}
				}

				ref_id = Integer.parseInt(aux_line.substring(pos[2] + 5, pos[3] - 1));
				parent_node = aux_line.substring(aux_line.lastIndexOf('=') + 2, aux_line.lastIndexOf('"'));
				AddM(parent_node, nodes_array[ref_id].get_name());
			}
		}
		ps.close();
		fOut.close();
	}

}
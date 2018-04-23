package tema2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Parcurge fisierul de intarare linie nu linie, identifica comanda si lista de
 * paramteri. In functie de comanda, efectueaza operatii de: setare, adaugare de
 * nod, adaugare de muchie, stergere de nod, stergere de muchie, serializare si
 * deserializare.
 * - in cazul unei comenzi de setare, facem un vector param_settings in care sa 
 * retinem parametrii comenzii
 * - in cazul unei operatii de adaugare de nod(Add), identificam clasa nodului, 
 * numele si vecinii acestuia, adaugam nodul la vectorul de noduri al grafului 
 * si facem legaturile nodului cu vecinii, precum si invers 
 * - in cazul operatiei de adaugare de muchie(AddM) identificam capetele noii 
 * muchii si facem legatura dintre ele
 * - in cazul de stergere de nod(Del) extragem numele nodului ce urmeaza sa fie
 * sters, apoi apelam functia grafului de stergere de nod
 * - in cazul stergerii de muchie(DelM) extragem capetele muchiei ce urmeaza a 
 * fi stearsa si eliminam legatura dintre ele apeland functia grafului
 * - in cazul serializarii, nodul de start si fisierul in care vom scrie, apoi 
 * apelam functia grafului si golim graful
 * - in cazul operatiei de deserializare, apelam functia
 * graful pentru a reface graful sters la serializare.
 * 
 * @author Andreea
 */
public class Main {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		Graph graph = new Graph();
		FileOutputStream fOut;
		PrintStream ps;

		BufferedReader reader = new BufferedReader(new FileReader(args[0]));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");

		String command = null;
		int pos[];
		int space_nr = 0;
		int i, j;
		String param_settings[] = null;
		int cnt = 0;
		int version = 0;
		String name = null;
		Nod[] neighbour = null;
		int neighbour_size = 0;
		String str = null;
		String str2 = null;
		String file_name = null;
		String start_node = null;
		Nod pstart = null;
		String node_class = null;

		try {
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);

				// extragem numarul de spatii de pe linie
				space_nr = 0;
				for (i = 0; i <= line.length() - 1; i++) {
					if (line.charAt(i) == ' ') {
						space_nr++;
					}
				}

				// retinem indicii spatiilor intr-un vector
				pos = new int[space_nr];
				space_nr = 0;
				for (i = 0; i <= line.length() - 1; i++) {
					if (line.charAt(i) == ' ') {
						pos[space_nr] = i;
						space_nr++;
					}
				}

				// extragem comanda
				command = line.substring(0, pos[0]);

				if (command.equals("Settings")) {
					param_settings = new String[space_nr];
					// extragere parametrii din line
					for (i = 0; i <= space_nr - 2; i++) {
						param_settings[i] = line.substring(pos[i] + 1, pos[i + 1]);
					}
					param_settings[space_nr - 1] = line.substring(pos[space_nr - 1] + 1, line.length());
				}

				if (command.equals("Add")) {
					Graph.nodes_nr++;

					node_class = line.substring(pos[0] + 1, pos[1]);

					if (node_class.equals("NodA"))
						version = Integer.parseInt(param_settings[0]);
					else if (node_class.equals("NodB"))
						version = Integer.parseInt(param_settings[1]);
					else
						version = Integer.parseInt(param_settings[2]);

					if (space_nr == 2) {
						name = line.substring(pos[1] + 1, line.length());
						neighbour = null;
					} else {
						name = line.substring(pos[1] + 1, pos[2]);
						neighbour_size = space_nr - 2;
						neighbour = new Nod[neighbour_size];
						for (j = 0; j < neighbour_size - 1; j++) {
							str = line.substring(pos[j + 2] + 1, pos[j + 3]);
							for (i = 0; i < cnt; i++) {
								if (graph.nodes_array[i].get_name().equals(str)) {
									neighbour[j] = graph.nodes_array[i];
									break;
								}
							}
						}

						str = line.substring(pos[space_nr - 1] + 1, line.length());
						for (i = 0; i < cnt; i++)
							if (graph.nodes_array[i].get_name().equals(str)) {
								neighbour[neighbour_size - 1] = graph.nodes_array[i];
								break;
							}
					}

					graph.add_Node(name, node_class, version, cnt);
					cnt++;

					String temp_name = null;
					if (neighbour != null) {
						for (j = 0; j < neighbour.length; j++) {
							for (i = 0; i < cnt - 1; i++) {
								temp_name = graph.nodes_array[i].get_name();
								if (temp_name.equals(neighbour[j].get_name())) {
									graph.AddM(temp_name, graph.nodes_array[cnt - 1].get_name());
									break;
								}
							}
						}
					}
				}

				if (command.equals("AddM")) {
					str = line.substring(pos[0] + 1, pos[1]);
					str2 = line.substring(pos[1] + 1, line.length());
					graph.AddM(str, str2);
				}

				if (command.equals("DelM")) {
					str = line.substring(pos[0] + 1, pos[1]);
					str2 = line.substring(pos[1] + 1, line.length());
					graph.DelM(str, str2);
				}

				if (command.equals("Del")) {
					str = line.substring(pos[1] + 1, line.length());
					graph.Del(str);
				}

				if (command.equals("Serialize")) {
					start_node = line.substring(pos[0] + 1, pos[1]);

					file_name = line.substring(pos[1] + 1, line.length());

					for (i = 0; i <= Graph.nodes_nr - 1; i++) {
						if (start_node.equals(graph.nodes_array[i].get_name())) {
							pstart = graph.nodes_array[i];
							break;
						} else {
							System.out.println("Nodul de start nu exista in graf!");
						}
					}

					try {
						fOut = new FileOutputStream(file_name);
						ps = new PrintStream(fOut);

						graph.serialize(pstart, 0, ps, pstart.get_neighbours_nr() + 1, pstart);

						for (i = 0; i < Graph.nodes_nr; i++) {
							graph.nodes_array[i] = null;
						}
						Graph.nodes_nr = 0;

						ps.close();
						fOut.close();
					} catch (IOException e) {

						e.printStackTrace();
					}
					graph = new Graph();
				}

				if (command.equals("Deserialize")) {
					file_name = line.substring(line.indexOf(' ') + 1, line.length());
					graph.deserialize(file_name, param_settings);
				}
			}

		} finally {
			reader.close();
		}
	}

}
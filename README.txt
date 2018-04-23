GEORGIAN ANDREEA - 321 CB
---------------------------
TEMA  2 - POO - SERIALIZARE
---------------------------

Pentru realizarea temei, am folosit urmatoarele clase:
	1. Nod - aceasta clasa este o clasa abstracta
	2. NodV1 - aceasta clasa extinde Nod si implementeaza metode de stergere, 
	adaugare, cautare etc, intr-o LISTA
	3. NodV2 - aceasta clasa extinde Nod si implementeaza metode de stergere, 
	adaugare, cautare etc, intr-un VECTOR
	4. NodV3 - aceasta clasa extinde Nod si implementeaza metode de stergere, 
	adaugare, cautare etc, intr-un SET
	5. Graph - in aceasta clasa am implementat operatiile pe graf, anume:
		* add_Node - adauga un nod in graf
		* Del - sterge un nod din graf
		* AddM - adauga o muchie in graf
		* DelM - sterge o muchie din graf
		* print_tabs - printeaza tab-uri( ma ajuta la serializare)
		* serialize
		* deserialize
	6. Main

Rezolvarea decurge astfel:
	Primul pas este sa citesc fisierul linie cu linie si sa identific comanda:
	- Add -> citesc clasa si numele nodului, precum si numele vecinilor, apoi 
	il adaug in vectorul de noduri ale grafului si fac legaturile cu vecinii;
	- Del -> citesc numele nodului, parcurg vectorul de noduri si il sterg, 
	modificand in acelasi timp legaturile vecinilor sai;
	- AddM -> citesc cele doua noduri ce urmeaza a fi legate si apelez AddM;
	- DelM -> citesc cele doua noduri ce urmeaza a fi "dezlegate" si apelez DezM;
	- Serialize -> pentru serializare am folosit o metoda recursiva; incepand 
	de la nodul de start, il adaug unei liste de serializare, afisez linia 
	corespunzatoare (cu Object), apoi incep sa parcurg vecinii.
	Pentru fiecare vecin, verific daca a mai fost serializat si in functie de 
	rezultat apelez recursiv functia de serializare pentru vecinul respectiv, 
	altfel afisez linie cu Reference. In cazul in care lista de adiacenta are 
	un numar de noduri egal cu vecinii nodului de start + 1, parasesc functia.
	- Deserialize -> pentru aceasta operatie parcurg fisierul primit ca 
	parametru, iar de fiecare data cand gasesc o linie care contine "Object", 
	adaug nodul respectiv vectorului de noduri al grafului. Apoi, de fiecare 
	data cand gasesc o linie ce contine "Reference" adaug o noua muchie intre 
	nodul respectiv si nodul parinte, retinut cu ajutorul argumentului 
	parent_name de la serializare.

Observatii:
	- nu am 3 clase diferite NodA, NodB si NodC deoarece nu am vazut utilitatea 
	folosirii a trei clase diferite

Probleme intampinate:
	- enuntul nu este suficient de clar
	- un singur exemplu nu este de ajuns pentru a verifica corectitudinea 
implementarii
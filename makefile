build:
	javac src/tema2/*.java
run:
	java -cp src tema2/Main ${ARGS}
clean:
	rm -rf /src/tema2/*.class

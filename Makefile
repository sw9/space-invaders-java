all:
	cd src/;javac Game.java -d ../

.PHONY: run
run: all
	java Game
	
.PHONY: clean
clean:
	rm -f *.class
	rm -f src/*.class
	rm -rf bin/

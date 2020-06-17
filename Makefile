PACKAGE=com.usp.corrida
DEST=docs/
SRC=core/src/

doc:
	rm -rf docs/
	javadoc -noindex -notree -nohelp -nodeprecated -d $(DEST) -sourcepath $(SRC) $(PACKAGE) $(PACKAGE).logic $(PACKAGE).screens $(PACKAGE).utils

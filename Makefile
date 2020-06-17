PACKAGE=com.usp.corrida
DEST=docs/
SRC=core/src/
TITLE="Corrida Aritmetica - docs"

doc:
	rm -rf docs/
	javadoc -doctitle $(TITLE) -windowtitle $(TITLE) -noindex -notree -nohelp -nodeprecated -d $(DEST) -sourcepath $(SRC) $(PACKAGE) $(PACKAGE).logic $(PACKAGE).screens $(PACKAGE).utils

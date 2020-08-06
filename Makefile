PACKAGE=com.usp.corrida
DEST=docs/
SRC=core/src/
TITLE="Arithmetic Run - docs"

doc:
	rm -rf docs/
	javadoc -private -doctitle $(TITLE) -windowtitle $(TITLE) -noindex -notree -nohelp -nodeprecated -d $(DEST) -sourcepath $(SRC) $(PACKAGE) $(PACKAGE).logic $(PACKAGE).screens $(PACKAGE).utils

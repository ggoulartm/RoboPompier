# Ensimag 2A POO - TP 2018/19
# ============================
#
# Ce Makefile permet de compiler le test de l'ihm en ligne de commande.
# Alternative (recommandee?): utiliser un IDE (eclipse, netbeans, ...)
# Le but est ici d'illustrer les notions de "classpath", a vous de l'adapter
# a votre projet.
#
# Organisation:
#  1) Les sources (*.java) se trouvent dans le repertoire src
#     Les classes d'un package toto sont dans src/toto
#     Les classes du package par defaut sont dans src
#
#  2) Les bytecodes (*.class) se trouvent dans le repertoire bin
#     La hierarchie des sources (par package) est conservee.
#     L'archive lib/gui.jar contient les classes de l'interface graphique
#
# Compilation:
#  Options de javac:
#   -d : repertoire dans lequel sont places les .class compiles
#   -classpath : repertoire dans lequel sont cherches les .class deja compiles
#   -sourcepath : repertoire dans lequel sont cherches les .java (dependances)

all: testPathFinder exePath

Boss: testBoss exeBoss

testInvader:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/tests.TestInvader.java

testLecture:
	javac -d bin -sourcepath src src/tests.TestLecteurDonnees.java


testEvenement:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/tests/TestEvenement.java

testPathFinder:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/tests/TestPathFinder.java

testBoss:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/tests/TestChefPompier.java

testMover:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/tests/TestMover.java

testChef:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/tests/TestChefPompier.java

test:
	javac -d bin -classpath lib/gui.jar -sourcepath src src/tests/TestChefPompier.java

# Execution:
# on peut taper directement la ligne de commande :
#   > java -classpath bin:lib/gui.jar tests.TestInvader
# ou bien lancer l'execution en passant par ce Makefile:
#   > make exeInvader
exeInvader: 
	java -classpath bin:lib/gui.jar tests.TestInvader

exeLecture: 
	java -classpath bin tests.TestLecteurDonnees cartes/carteSujet.map

exeSim:
	java -classpath bin:lib/gui.jar TestSimulateur cartes/spiralOfMadness-50x50.map

exeEve:
	java -classpath bin:lib/gui.jar tests.TestEvenement cartes/carteSujet.map

exePath:
	java -classpath bin:lib/gui.jar tests.TestPathFinder cartes/carteSujet.map

exeBoss:
	java -classpath bin:lib/gui.jar tests.TestChefPompier cartes/carteSujet.map

exeDesert:
	java -classpath bin:lib/gui.jar tests.TestPathFinder cartes/desertOfDeath-20x20.map

exeMove:
	java -classpath bin:lib/gui.jar tests.TestMover cartes/mushroomOfHell-20x20.map

exeChef:
	java -classpath bin:lib/gui.jar tests.TestChefPompier cartes/carteSujet.map

exeMap:
	java -classpath bin:lib/gui.jar tests.TestChefPompier cartes/carteSujet.map

exeDesert:
	java -classpath bin:lib/gui.jar tests.TestChefPompier cartes/desertOfDeath-20x20.map

exeMushroom:
	java -classpath bin:lib/gui.jar tests.TestChefPompier cartes/mushroomOfHell-20x20.map

exeSpiral:
	java -classpath bin:lib/gui.jar tests.TestChefPompier cartes/spiralOfMadness-50x50.map

# JAVADOCS
# javadoc -d docs -sourcepath src -subpackages toto
javadoc:
	javadoc -d doc -classpath lib/gui.jar src/*/*

# Nettoyage:
clean:
	rm -rf bin/*

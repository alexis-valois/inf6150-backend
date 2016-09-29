# inf6150-backend
## Requirements
* [Java JDK 1.8 64bits](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [SourceTree](https://www.sourcetreeapp.com/download/)
* [Spring Tool Suite 3.8.1.RELEASE 64bits](https://spring.io/tools/sts/all)
* [MySQL Server Community 5.6.33 + Workbench](https://dev.mysql.com/downloads/mysql/5.6.html)

## Getting Started
### Portion MySQL
1. Cloner le projet avec SourceTree : Fichier / Cloner-Nouveau / URL Git / Cloner
2. Ouvrir MySQL Workbench
3. Choisir Local Instance
4. Dans l'onglet Schema, faire clic-droit/create schema
5. Nommer le schema **ezbudget** (case sensitive)
6. Appuyer sur Apply
7. Dans l'onglet Management, cliquer sur Users and priviledges
8. Cliquer sur Add Account. Pour Login name : **ezbudget**, password : **ezbudget** et faire Apply
9. Dans la même fenêtre, naviguer dans l'onglet Schema Previldges et cliquer sur Add Entry
10. Sélectionner l'option Selected Schema et choisir le schema ezbudget de la liste déroulante et faire OK
11. Toujours dans la même fenêtre, appuyer sur Select All et faire Apply.

### Portion Spring Tool Suite
1. Ouvrir Spring Tool Suite en spécifiant un emplacement de workspace arbitraire (pas le même que le répertoire de clone !)
2. Dans l'onglet vide de gauche, faire clic-droit / Import... / Existing project into workspace / Next
3. Appuyer sur Browse pour naviguer jusqu'à la racine du répertoire de clone du projet
4. Appuyer sur Finish
5. Appuyer sur le menu Help/Eclipse Market Place et faire une recherche pour Gradle.
6. Sélectionner **Buildship Gradle Integration 1.0** et cliquer sur le bouton "install". Suivre les étapes pour finaliser l'installation du plug-in.
7. Le projet Java comportera des erreurs de compilations. Il faut maintenant résoudre les dépendances Gradle en faisant clic-droit sur la racine du projet (Panneau de gauche dans Spring Tool Suite) / Gradle / Refresh Gradle Porject. Attendre que les dépendences se téléchargent.

### Build & development
* Pour lancer le projet backend, il faut faire clic-droit sur le projet / Run As... / Spring Boot App.
* Les tables de la base de données MySQL se créeront automatiquement.
* Pour confirmer l'installation, aller dans un naviguateur et tapper : http://localhost:8081/health

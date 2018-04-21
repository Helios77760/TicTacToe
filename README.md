# Reconnaissance de TicTacToe

Projet d'imagerie de reconnaissance d'un jeu de TicTacToe.

Auteurs : Dylan BRASSEUR, Jérémy MEYNADIER

##Pour le tester :

1. Téléchargez la dernière version de ce dépot
2. Importer le projet Maven dans Eclipse (ou ouvrez le projet existant dans Intellij Idea)
3. Lancez via LaunchImageJ
4. Ouvrez votre image de TicTacToe
5. Ouvrez la console (Window > Console)
6. Lancez le plugin TicTacToeFinder (Plugin > TicTacToe Finder)
7. Patientez le temps du traitement
8. Les lignes "Synthesis" donnent le résultat du traitement : 
   - La premiere donne le plateau reconnu
   - La seconde donne l'état actuel de la partie
    
##Contraintes de l'image

- Pas plus de 1000x1000 (pour des raisons de performances)
- Les traits doivent faire entre 5 et 40 pixels de large (pour s'assurer que les nettoyages et correction d'éclairage fonctionnent correctement)
- Les cercles doivent être fermés
- La grille doit être composée de 4 traits, perpendiculaires (à peu près) , séparant les différentes cases
- Une seule couleur pour tous les traits (cercles, croix et grille)
- Fond uni distingable des traits

##Fonctionnalités

- [x] Correction de l'éclairage (Dylan)
- [x] Rectification de l'angle (Dylan)
- [x] Binarisation (Jérémy et Dylan)
- [x] Image en négatif (Dylan)
- [x] Correction du bruit type "poivre et sel" (Jérémy)
- [x] Détection des lignes de la grille, même si elles ne sont pas parfaitement droites (Dylan)
- [x] Détection des cases vides par pourcentage (Dylan)
- [x] Détection des cercles fermés par inondation (Dylan)
- [ ] Détection des croix par projection
- [ ] Détection des cercles par recadrage
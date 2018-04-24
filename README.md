# Reconnaissance de TicTacToe

Projet d'imagerie de reconnaissance d'un jeu de TicTacToe.

Auteurs : Dylan BRASSEUR, Jérémy MEYNADIER

## Pour le tester :

1. Téléchargez la dernière version de ce dépot
2. Importer le projet Maven dans Eclipse (ou ouvrez le projet existant dans Intellij Idea)
3. Lancez via LaunchImageJ 
   * sur Eclipse : licence3.image/launchImageJ/src/.../LaunchImageJ.java : clic droit 'Run As' > 'Run configurations' > 'Run'
   * sur IDEA : TicTacToe/launchImageJ/src/.../LaunchImageJ : clic droit "Run 'LaunchImageJ.main()'" 
4. Ouvrez votre image de TicTacToe
5. Lancez le plugin TicTacToeFinder (Plugin > TicTacToe Finder)
6. Patientez le temps du traitement (la console devrait s'ouvrir)
7. Les lignes "Synthesis" sur la console donnent le résultat du traitement : le plateau reconnu (à 90° près) et l'état de la partie
    
## Contraintes de l'image

- Pas plus de 1000x1000 (pour des raisons de performances)
- Les traits doivent faire entre 5 et 40 pixels de large (pour s'assurer que les nettoyages et correction d'éclairage fonctionnent correctement)
- Les cercles doivent être fermés
- Les cercles ou les croix ne doivent pas toucher la grille
- La grille doit être composée de 4 traits, perpendiculaires (à peu près) , séparant les différentes cases
- Une seule couleur pour tous les traits (cercles, croix et grille)
- Fond uni clair distingable des traits sombres

## Fonctionnalités

- [x] Correction de l'éclairage (Dylan)
- [x] Rectification de l'angle (Dylan)
- [x] Binarisation (Jérémy et Dylan)
- [x] ~~Image en négatif (Dylan)~~ (Incompatible avec la correction de l'éclairage)
- [x] Correction du bruit type "poivre et sel" (Jérémy)
- [x] Détection des lignes de la grille, même si elles ne sont pas parfaitement droites (Dylan)
- [x] Détection des cases vides par pourcentage (Dylan)
- [x] Détection des cercles fermés par inondation (Dylan)
- [ ] Détection des croix par projection
- [ ] Détection des cercles par recalage

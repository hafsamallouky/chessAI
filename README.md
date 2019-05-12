# chessAI (handmade chess game)
Jeu d'echec avec IA s'appuyant sur l'algo du min max.

## Interface
![Image de l'interface](https://image.noelshack.com/fichiers/2019/19/7/1557659575-tmpchess.png)
L'IA joue avec les pions blancs, le joueur avec les noirs.
On clique sur la piece a deplacer, cela affiche les possibilités. On clique sur une des cases en surbrillance pour déplacer la pièce
et sur une autre case pour desélectionner la piece.

## Video
[Lien vers la video](https://youtu.be/PpP1ALGV_ok)
(NB : Je sais à peine jouer aux echecs, ne jugez pas :) )
## IA
L'IA choisi son coup grâce a l'algo du min max avec une profondeur de 3.


Les fonctions relatives à l'IA sont à la fin de la classe Plateau

### Heuristique
La formule de l'heuristique est :
((Valeur des pieces de l'IA)*4 + (valeur de la position des pieces de l'IA)) - ((Valeur des pieces du joueur)*4 + (valeur de la position des pièces du joueur))

Après divers test c'est l'heuristique qui semble etre le meilleur compromis entre en bon positionnement des pièces, une stratégie aggressive
pour manger les pièces de l'adversaire tout en preservant ses propres pièces.

![Tableau des valeurs des positions](https://i.stack.imgur.com/hxGdi.png)

Valeur des pieces : ([source](https://fr.wikipedia.org/wiki/Valeur_relative_des_pi%C3%A8ces_d%27%C3%A9checs))
* pion : 1.0
* cavalier : 3.2
* fou : 3.33
* tour : 5.1
* dame : 8.81

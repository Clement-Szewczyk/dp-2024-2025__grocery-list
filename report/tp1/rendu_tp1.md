# Rapport sur le Projet "Grocery List"

## Introduction

Le projet "Grocery List" est une application Java permettant de gérer une liste de courses en ligne de commande. Il supporte l'ajout, la suppression et l'affichage des éléments, tout en offrant la possibilité de stocker les données dans des fichiers CSV ou JSON.

## Objectifs

- Développer une application de gestion de liste de courses.
- Permettre la persistance des données en CSV et JSON.
- Offrir une interface CLI pour manipuler les données facilement.
- Implémenter des tests unitaires pour garantir la fiabilité du programme.

## Fonctionnalités Implémentées

### Gestion des fichiers CSV et JSON

Deux classes distinctes (`CSV` et `JSON`) permettent de stocker et manipuler les données :

- **CSVHandler** : utilise `Files.readAllLines()` et `Files.write()` pour lire/écrire les données sous forme de texte.
- **JSONHandler** : utilise `Jackson` pour sérialiser et désérialiser les objets Java en JSON.

### CLI (Command Line Interface)

Une interface en ligne de commande (`CLIHandler`) a été développée à l'aide d'`Apache Commons CLI` pour interpréter les commandes :

- `add <groceryItem> <quantity>` : ajoute un élément à la liste.
- `remove <groceryItem> [quantity]` : supprime un élément (ou réduit sa quantité).
- `list` : affiche la liste complète.

Le programme détecte automatiquement le type de fichier utilisé (.csv ou .json) et utilise la classe correspondante.

## Difficultés Rencontrées

1. **Gestion de la persistance des données**

   - Problèmes avec la lecture/écriture concurrente sur les fichiers.
   - Gestion des erreurs lors de la conversion JSON.

2. **Interface CLI**
   - Manipulation des arguments de la ligne de commande.
   - Gestion des entrées invalides et affichage de messages d'erreur compréhensibles.

## Tests et Validation

### Tests des fichiers CSV et JSON

Nous avons vérifié que l’application peut correctement charger, modifier et enregistrer des listes d’achats stockées en fichiers CSV et JSON. Cela inclut l’ajout de nouveaux éléments, la mise à jour des quantités existantes, ainsi que la suppression totale ou partielle des produits. Nous avons également testé la persistance des modifications, en nous assurant que les données restent cohérentes après rechargement du fichier.

### Tests de l’interface en ligne de commande (CLI)

Nous avons validé le bon fonctionnement de la CLI en simulant différentes utilisations possibles. Les tests garantissent que l’application réagit correctement en cas d’erreur (fichier manquant, format incorrect, argument invalide) et qu’elle exécute les commandes attendues, comme l’ajout, la suppression et l’affichage des éléments de la liste.

### Outils et Méthodologie

Les tests ont été réalisés avec JUnit 5, en utilisant AssertJ pour des assertions plus claires et robustes. Nous avons également utilisé des répertoires temporaires pour manipuler des fichiers sans affecter les données réelles. L’ensemble de la suite de tests permet de valider que l’application fonctionne comme prévu dans différents scénarios d’usage.

### Organisation du Travail  
Nous avons travaillé à trois sur ce projet, en nous partageant les tâches tout en utilisant un seul ordinateur pour la mise en place du code et des tests sur GitHub. Cette organisation nous a permis de réfléchir ensemble aux solutions, d’échanger rapidement sur les problèmes rencontrés et de garantir une meilleure qualité du code grâce aux retours instantanés de chacun.  

## Conclusion

Le projet "Grocery List" est une application fonctionnelle qui permet une gestion efficace des listes de courses via une interface en ligne de commande. L'utilisation de fichiers CSV et JSON assure la persistance des données, et la modularité du code permet de futures améliorations.

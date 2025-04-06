# Rapport n°2 sur le Projet "Grocery List"



## Objectifs de cette étape

- Améliorer la gestion des format et implémenter clairement le -f dans la CLI.
- Revoir notre CLI qui était trop longue et redondante.
- Ajouter la fonctionnalité catégorie (-c) pour catégoriser les items.
- Implémenter des tests unitaires pour garantir la fiabilité du programme.

## Fonctionnalités Implémentées

### 1. Strategy Pattern
Le pattern **Strategy** a été utilisé pour permettre le support de plusieurs formats de fichier (JSON, CSV).  
Chaque format implémente une interface `GroceryListDAO` avec les méthodes `load()` et `save()`.

- **Interface** : `GroceryListDAO`  
- **Implémentations concrètes** :
  - `JSONGroceryListDAO`
  - `CSVGroceryListDAO`

Cette structure permet d'ajouter facilement d'autres formats à l'avenir (par exemple XML) sans modifier le cœur du programme.


### 2. Gestion du format de sauvegarde (JSON/CSV)
Le format de sauvegarde détermine comment la liste de courses est enregistrée sur le disque (fichier .json ou .csv). L’utilisateur choisit ce format en ligne de commande, en écrivant json ou csv comme premier argument. Par exemple :


````bash 
java -jar target/dp-2024-2025__grocery-list-1.0-SNAPSHOT.jar -f csv add tomates 5
java -jar target/dp-2024-2025__grocery-list-1.0-SNAPSHOT.jar add pomme 2
````

### 3. Fonctionnement
Si l’utilisateur entre json ou csv, le programme crée automatiquement un fichier nommé liste.json ou liste.csv.

Ce nom est généré par défaut, l'utilisateur ne choisit pas le nom du fichier, pour simplifier l’usage.

Le format JSON est utilisé par défaut si aucun format n’est précisé ou si une entrée non reconnue est donnée.

### 4. Implémentation
Un parseur d’arguments récupère le premier argument format, et génère automatiquement un nom de fichier basé sur l’extension.

Ensuite, une fabrique de DAO choisit dynamiquement l'implémentation :

- JSONGroceryListDAO pour .json

- CSVGroceryListDAO pour .csv

Ce comportement est centralisé dans la classe GroceryListManager.

## Difficultés Rencontrées
### 2. Mauvaise gestion des arguments CLI
#### Problème :
Les options comme `-f` ou `--format` n’étaient pas reconnues.

#### Solution :
Ajout correct des options avec `Options` d'Apache CLI :
```java
cliOptions.addOption("f", "format", true, "File format (json or csv)");
```

### 3. Mauvaise initialisation des noms de fichiers
#### Problème :
Possibilité de nommer manuellement les fichiers via `-s`, ce qui permettait des noms invalides (ex : `.csv`).

#### Solution :
Suppression de cette possibilité. Le fichier est désormais automatiquement nommé `liste.json` ou `liste.csv` en fonction du format.

## Tests et Validation



### Organisation du Travail  
Nous avons travaillé à trois sur ce projet, en nous partageant les tâches tout en utilisant un seul ordinateur pour la mise en place du code et des tests sur GitHub. Cette organisation nous a permis de réfléchir ensemble aux solutions, d’échanger rapidement sur les problèmes rencontrés et de garantir une meilleure qualité du code grâce aux retours instantanés de chacun.  

## Conclusion

Le projet "Grocery List" est une application fonctionnelle qui permet une gestion efficace des listes de courses via une interface en ligne de commande. L'utilisation de fichiers CSV et JSON assure la persistance des données, et la modularité du code permet de futures améliorations.
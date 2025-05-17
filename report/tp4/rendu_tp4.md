# Rapport n°4 sur le Projet "Grocery List"

## Ce pour quoi vous n'avez pas eu le temps

Nous n'avons malheureusement pas eu suffisamment de temps pour développer une suite complète de tests pour l'interface web, ce qui aurait permis de garantir une meilleure stabilité et fiabilité de cette fonctionnalité.

La refactorisation de la classe GroceryListManager aurait nécessité une révision majeure de notre structure de code. Cette classe étant devenue trop volumineuse, son allègement aurait été bénéfique mais aurait demandé un temps considérable que nous n'avions pas à disposition.

## Ce qui était difficile

L'implémentation de notre architecture a présenté plusieurs défis techniques :

- Refactorisation importante de la logique d'exécution des commandes
- Gestion complexe des différents formats de sauvegarde et leurs dépendances
- Centralisation du mécanisme de création des objets d'accès aux données
- Maintien de la rétrocompatibilité dans la gestion des arguments CLI
- Équilibre entre la gestion globale des options de commande et la testabilité du code
- Mise en place de tests unitaires et d'intégration couvrant toutes les nouvelles fonctionnalités

## Design Patterns utilisés

### 1. Command Pattern
- **Utilisation** : Implémenté pour gérer les différentes commandes (add, remove, list, web, info)
- **Pourquoi** : Permet d'encapsuler une requête comme un objet, facilitant ainsi l'ajout de nouvelles commandes sans modifier le code existant. Chaque commande est implémentée comme une classe distincte.

### 2. Strategy Pattern
- **Utilisation** : Implémenté pour gérer les différents formats de sauvegarde (JSON, CSV)
- **Pourquoi** : Permet de définir une famille d'algorithmes (ici, différentes méthodes de persistance) et de les rendre interchangeables. Le client peut ainsi choisir dynamiquement la stratégie à utiliser.

### 3. Factory Pattern
- **Utilisation** : Implémenté pour la création des DAO selon le format spécifié
- **Pourquoi** : Centralise la création d'objets complexes et cache les détails d'implémentation au client. Facilite l'ajout de nouveaux formats de persistance.

### 4. Singleton Pattern
- **Utilisation** : Implémenté pour la classe CommandOption
- **Pourquoi** : Assure qu'une classe n'a qu'une seule instance et fournit un point d'accès global à cette instance. Utile pour partager l'état de la configuration entre les différentes parties de l'application.

### 5. DAO Pattern
- **Utilisation** : Implémenté pour l'accès aux données (GroceryListDAO)
- **Pourquoi** : Sépare la logique d'accès aux données de la logique métier, permettant ainsi de changer la source de données sans impacter le reste du code.

## Questions théoriques

### Comment ajouter une nouvelle commande ?
1. Créer une nouvelle classe implémentant l'interface `Command`
2. Implémenter la méthode `execute(GroceryListManager manager)` avec la logique spécifique
3. Ajouter la commande au registre des commandes dans `CommandRegistry` en l'associant à son nom
4. La nouvelle commande sera automatiquement disponible via la ligne de commande

### Comment ajouter une nouvelle source de données ?
1. Créer une classe helper pour gérer le format spécifique (comme JsonHelper ou CsvHelper)
2. Implémenter une nouvelle classe DAO qui implémente l'interface `GroceryListDAO`
3. Mettre à jour la factory `GroceryListDAOFactory` pour créer une instance du nouveau DAO selon le format spécifié
4. Mettre à jour la validation du format dans la méthode `parseCommandLineArguments` de `CLI`

### Que dois-je changer si je veux spécifier un magasin où acheter mes courses ?
1. Modifier la classe `GroceryItem` pour ajouter un attribut "store" (magasin)
2. Étendre l'interface utilisateur (CLI) pour accepter un nouveau paramètre de magasin (`-s` ou `--store`)
3. Mettre à jour `CommandOption` pour stocker cette nouvelle information
4. Adapter les classes `Add` et `List` pour prendre en compte le magasin lors de l'ajout et de l'affichage
5. Adapter les helpers `JsonHelper` et `CsvHelper` pour inclure le magasin dans les formats de persistance
6. Mettre à jour l'interface web pour afficher et permettre la sélection du magasin

git # Rapport n°3 sur le Projet "Grocery List"

## Design Patterns Utilisés

### 1. Strategy Pattern
Le **Strategy Pattern** a été mis en place pour gérer les différents formats de sauvegarde des données (JSON, CSV). Ce design pattern permet de séparer les algorithmes de gestion des formats dans des classes distinctes, offrant ainsi une flexibilité accrue et une extensibilité facilitée. Chaque format implémente une interface commune `GroceryListDAO`, ce qui permet d'ajouter de nouveaux formats sans modifier le code existant.

### 2. Factory Pattern
Le **Factory Pattern** a été utilisé pour centraliser la création des instances de DAO en fonction du format choisi par l'utilisateur. Cette approche permet de déléguer la logique de création à une classe dédiée, simplifiant ainsi le code client et respectant le principe de responsabilité unique.


## Avantages des Patterns Implémentés
- **Modularité** : Les patterns utilisés permettent de découpler les différentes parties du code, rendant le projet plus maintenable.
- **Extensibilité** : L'ajout de nouvelles fonctionnalités ou formats est simplifié grâce à l'utilisation des patterns.
- **Lisibilité** : Le code est plus structuré et facile à comprendre, ce qui facilite la collaboration en équipe.

## Difficultés Rencontrées
- **Intégration des Patterns** : La mise en œuvre des patterns a nécessité une refactorisation importante du code existant, ce qui a pris du temps.
- **Gestion des Dépendances** : L'utilisation de plusieurs patterns a parfois rendu la gestion des dépendances plus complexe, nécessitant une attention particulière pour éviter les cycles.

## Conclusion
L'utilisation des design patterns a permis d'améliorer significativement la qualité du code et de poser une base solide pour les futures évolutions du projet. Ces choix de conception ont renforcé la robustesse et la flexibilité de l'application.

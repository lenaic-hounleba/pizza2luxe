# 🍕 Pizza2Luxe - Application Java de création et commande de pizzas

> Application Java complète (JavaFX) avec architecture MVC, gestion des commandes en temps réel et persistance des données.

---

## 📌 Description

Pizza2Luxe est une application Java de gestion de pizzeria permettant à un **pizzaïolo** de gérer son catalogue de pizzas et d'ingrédients, et à des **clients** de passer des commandes, de les suivre et d'évaluer les pizzas reçues.

Le système s'articule autour de **deux rôles distincts** :

| Rôle | Droits |
|---|---|
| 👨‍🍳 Pizzaïolo | Créer les ingrédients et les pizzas, consulter et traiter les commandes, visualiser les statistiques |
| 👤 Client | Créer un compte, se connecter, passer des commandes, consulter son historique, évaluer les pizzas |

Le projet suit une architecture proche du modèle **MVC** avec séparation claire entre logique métier (`pizzas/`), interface graphique (`ui/`) et persistance des données (`io/`).

---

## 🧩 Architecture simplifiée

```
┌─────────────────────────────────────────┐
│           UI - JavaFX (FXML)            │  ← client.fxml / pizzaiolo.fxml
│   ClientControleur / PizzaioloControleur│
└────────────────────┬────────────────────┘
                     │ appelle
┌────────────────────▼────────────────────┐
│         Interfaces métier               │
│   InterClient  /  InterPizzaiolo        │
└────────────────────┬────────────────────┘
                     │ implémentées par
┌────────────────────▼────────────────────┐
│           Logique métier (Model)        │
│  GestionClient / GestionPizzaiolo       │
│  Commande · Pizza · Client · Evaluation │
│  EtatCommande · TypePizza · Ingredient  │
└────────────────────┬────────────────────┘
                     │ persisté par
┌────────────────────▼────────────────────┐
│         Persistance - io/               │
│   SauvegardePizzeria → pizzeria.dat     │
└─────────────────────────────────────────┘
```

---

## 📸 Aperçu de l'application

### 👨‍🍳 Interface Pizzaïolo
![Interface Pizzaïolo](docs/screenshots/interface_pizzaiolo.png)

### 👤 Interface Client
![Interface Client](docs/screenshots/interface_client.png)

---

## ⚙️ Stack technique

- **Langage** : Java
- **Interface graphique** : JavaFX (FXML)
- **Tests** : JUnit (tests unitaires et d'intégration)
- **Qualité du code** : Javadoc · Checkstyle
- **Persistance** : Sérialisation Java (`pizzeria.dat`)
- **Livrable** : JAR exécutable (`Pizzaiolo.jar`)

---

## 🧠 Fonctionnalités principales

### Côté client
- 🔐 Création de compte et connexion sécurisée
- 🍕 Consultation du catalogue de pizzas (avec filtres)
- 🛒 Création et gestion des commandes (ajout de pizzas, validation)
- 📋 Consultation de l'historique des commandes traitées
- ⭐ Évaluation des pizzas après réception

### Côté pizzaïolo
- ➕ Création et gestion des ingrédients et pizzas
- 📦 Consultation et traitement des commandes validées
- 📊 Statistiques de ventes et de bénéfices (par client, par commande, global)

### Règles métier clés
- 🔄 Une commande suit trois états stricts : **créée → validée → traitée**
- ❌ Une commande validée ne peut plus être modifiée
- 📖 La consultation d'une commande par le pizzaïolo la marque automatiquement comme traitée
- ⭐ L'évaluation d'une pizza n'est possible qu'après réception de la commande
- 🍕 Les pizzas ont un type (`TypePizza`) qui conditionne les ingrédients autorisés

---

## 🏗️ Architecture du projet

```
pizza2luxe/
├── Projet_Java/
│   └── src/
│       ├── MainPizzas.java              # point d'entrée de l'application
│       ├── pizzas/                      # logique métier
│       │   ├── Client.java
│       │   ├── Commande.java            # états, ajout pizzas, bénéfice
│       │   ├── CommandeException.java   # exception métier commandes
│       │   ├── EtatCommande.java        # enum : creee / validee / traitee
│       │   ├── Evaluation.java
│       │   ├── GestionClient.java       # interface InterClient implémentée
│       │   ├── GestionPizzaiolo.java    # interface InterPizzaiolo implémentée
│       │   ├── InformationPersonnelle.java
│       │   ├── Ingredient.java
│       │   ├── InterClient.java         # interface du client
│       │   ├── InterPizzaiolo.java      # interface du Pizzaiolo
│       │   ├── NonConnecteException.java
│       │   ├── Pizza.java
│       │   ├── PizzeriaData.java        # données globales de la pizzeria
│       │   └── TypePizza.java           # enum des types de pizzas
│       ├── io/                          # persistance
│       │   ├── InterSauvegarde.java
│       │   └── SauvegardePizzeria.java  # sérialisation / désérialisation
│       ├── tests/                       # tests JUnit
│       │   ├── TestClient.java
│       │   ├── TestCommande.java
│       │   ├── TestInformationPersonnelle.java
│       │   └── TestPizza.java
│       └── ui/                          # interface graphique JavaFX
│           ├── MainInterface.java
│           ├── AppContext.java
│           ├── client.fxml
│           ├── ClientControleur.java
│           ├── pizzaiolo.fxml
│           └── PizzaioloControleur.java
└── javadocs/
    ├── Pizzaiolo.jar
    ├── jvdoc.zip
    └── Plan_de_tests_Pizzeria_Pizza2Luxe.pdf
```

---

## 🚀 Lancement de l'application

### Prérequis
- Java 17+
- JavaFX SDK (si non installé globalement)

### Exécution

```bash
# Si JavaFX est installé globalement
java -jar Pizzaiolo.jar

# Si JavaFX n'est pas installé globalement
java --module-path <chemin_javafx>/lib --add-modules javafx.controls,javafx.fxml -jar Pizzaiolo.jar
```

---

## 🧪 Tests

- Tests unitaires : `TestClient`, `TestCommande`, `TestPizza`, `TestInformationPersonnelle`
- Framework : **JUnit**
- Plan de tests complet disponible : `Plan_de_tests_Pizzeria_Pizza2Luxe.pdf`

---

## 🔐 Qualité du code

- Documentation **Javadoc** complète (disponible dans `Projet_Java/doc/`)
- Conformité **Checkstyle** vérifiée
- Respect strict des interfaces imposées par le professeur (`InterClient`, `InterPizzaiolo`)

---

## ⚠️ Limites

> Projet développé en groupe de 3, dans un cadre académique avec des délais contraints.

- Interface utilisateur basée sur la maquette fournie par le professeur
- Persistance par sérialisation Java (pas de base de données)
- Application non déployée en ligne (exécutable local uniquement)

---

## 👥 Équipe & contributions

Projet réalisé en groupe de 3 - **chef de groupe : Lenaïc Love HOUNLEBA (responsable architecture & logique métier)**

### 💡 Ma contribution principale
- Module **commandes** complet : classe `Commande`, `EtatCommande`, `CommandeException`, logique métier et transitions d'états
- **Persistance des données** : sérialisation / désérialisation (`SauvegardePizzeria`, `PizzeriaData`)
- **Tests JUnit** : `TestCommande`, `TestClient`, `TestPizza`, `TestInformationPersonnelle`
- Application de **Checkstyle** sur l'ensemble du code
- Génération de la **Javadoc** complète
- Coordination du groupe et intégration des contributions

---

## 📚 Compétences mobilisées

- Programmation orientée objet en **Java**
- Interface graphique avec **JavaFX** (FXML, contrôleurs)
- Tests unitaires et d'intégration avec **JUnit**
- Persistance des données par **sérialisation Java**
- Documentation **Javadoc** et conformité **Checkstyle**
- Conception d'interfaces et respect d'un **contrat d'interface** imposé

---

## 👨‍💻 Auteur principal

**Lenaïc Love HOUNLEBA**   
CEO & Développeur Full Stack - [ComeUp](https://comeup.com/fr/@lenaic-1)

🔗 GitHub : [github.com/lenaic-hounleba](https://github.com/lenaic-hounleba)  
📧 lovehounleba@gmail.com

---

> *Projet réalisé dans le cadre de l'EC Conception d'Applications (CA) - Licence 3 Informatique, Université de Bretagne Occidentale, 2025-2026.*

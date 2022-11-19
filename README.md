# **Gwent Stone**
True based story about two famous games **Heartstone** and **Gwent**.

## **Table of contents**

1. [Getting Started](#start-desc)
   1. [Description](#start-desc-desc)
2. [Main Functionality](#main-desc)
   1. [Project Structure](#main-proj-desc)
   2. [File hierarchy](main-file-desc)
3. [Cards Package](#cards-desc)
   1. [Description](#cards-desc-desc)
   2. [ULM Schema](#cards-ulm-desc)
   3. [Explaining the ULM](#cards-expl-desc)
4. [Players Package](#players-desc)
   1. [Description](#players-desc-desc)
   2. [ULM Schema](#players-ulm-desc)
   3. [Explaining the ULM](#players-expl-desc)
5. [Battlefield Package](#battle-desc)
   1. [Description](#battle-desc-desc)
   2. [ULM Schema](#battle-ulm-desc)
   3. [Explaining the ULM](#battle-expl-desc)
6. [Commands Package](#commands-desc)
7. [Actions Package](#actions-desc)
8. [Main Game](#game-desc)


<a name="start-desc"></a>
## **Getting Started**


<a name="start-desc-desc"></a>
### **Description**

Basically this task is to test our knowledge about basic *OOP* concepts.
Even if I have some practice working with Java and *OOP* concepts, I found this
homework a little tricky. Overall I had a very great time working on the
project and I hope next tasks will be funnier than this one.

I hope you also will have a great time checking my assignment and reading
this **README** file. I would like to know what you liked most or less about this
readme and my project in general.

Now let's stop talking and get to good work.


<a name="main-desc"></a>
## **Main Functionality**


<a name="main-proj-desc"></a>
### **Project Structure**

The project is divided in 6 packages (not included the skeleton packages).
We will discuss every package and I will explain how every class communicates
with another class.

For a better understanding I will present you some **UML** schemas in every
package section and will discuss a lot more in the following sections.

<a name="main-file-desc"></a>
### **File hierarchy**

My implementation of the project can be found in the following packages:

* *actions* - a special class to maintain the action processing.
* *battlefield* - a class for the game table.
* *cards* - a whole package containing all the card logic.
* *commands* - special class, working as **log** functions/classes.
* *game* - a singleton class maintaining the whole game logic.
* *players* - a package designed for players functionalities.


<a name="cards-desc"></a>
## **Cards Package**


<a name="cards-desc-desc"></a>
### **Description**

The cards package are one of the most important package, because it contains the logic
of a card, the maintenance of a card, how a card works, what should it do in some
cases. This package has a very low communication with the other classes (in other
words it is encapsulated in its package, however other classes use them to instance
objects of **Minions** or other cards).

Even though the class is almost hidden from the entire word we need some functionalities
or some information about the game, in this case we will use the **Singleton** class
game to get some data, but overall no extern binding is needed.


<a name="cards-ulm-desc"></a>
### **ULM Schema**

![Cards ULM](images/cards-hierarchy.jpg)


<a name="cards-expl-desc"></a>
### **Explaining the ULM**

To keep the functionalities broken in some chunks of code, I created interfaces and
abstract classes. The main two interfaces are *Card* (the most generic interface all the
game is based on this interface) and *SpecialCard* which is an interface to signal
to the other chunks of code that the card has a special attack that can be used to attack
your opponent cards.

From these interfaces we can implement **3** abstract classes. The first class
is the **Minion** abstract class which is basically am effective card that can be placed on
the table and can perform simple actions (like attacking other minions), however
some minions can have special attack, so they also implement the **SpecialCard** interface

The Minion class implements the most of the **card** functionality and adds to it some abstract
functions left for the children classes to implement the methods. The **Minion** is very
important for the hierarchy, because it maintains the whole card structure, and we avoid code
duplication.

The next class is the **Environment** class, the main reason of environment class existence is that
an environment class cannot be placed on a table and do not follow the rules of a **Minion** card.
Even though the class does not come with some abstract methods it should be abstract to throw
the implementation to its children, and also we should not be able to create an instance
of an environment class, because it does not know what to do on a battlefield (in other
words, it can process an attack to another opponent).

The last abstract class is **Hero** class, which maintain the Hero cards. Now
why haven't I extended the class **Minion** for the Hero cards? The answer is because
the Hero is not a minion, and it cannot be placed on the table, also the functionalities
of the Hero card are different from the Minion card, the first difference is that
a Hero card cannot attack another card (it can use a special ability) and a hero
card cannot be frozen.

>**NOTE:** Overall the cards hierarchy is thought to keep the cards functionalities
> very easy and to encapsulate the same properties in one place and to use them
> anywhere from the extended classes. 


<a name="players-desc"></a>
## **Players Package**


<a name="players-desc-desc"></a>
### **Description**

Because no game exists without players, I created some functionalities for basic
players. This package as **cards** package does not communicate very much with the
extern classes (in order words it does not call or create instances of objects from
other packages).

>**NOTE:** Even though I told you that the package tries not to work with extern packages
> it would be a lie. In order to process input data we have to communicate with a third-party
> class (the classes from the **fileio** package), however the communication is very low
> and is only needed at constructors when we initialize the objects.

<a name="players-ulm-desc"></a>
### **ULM Schema**

![players ulm schema](images/players-hierarchy.jpg)


<a name="players-expl-desc"></a>
### **Explaining the ULM**

For this design I chose not to work with abstract classes or interfaces, because
it would be unuseful. In every game a player can exist by its own (even though
for this game we do not call explicit the Player constructor, we use it for the **PlayingPlayer**
class which will be the most important class from entire game).

The **Player** class is a collection of data, about the player that will want in some time
to play a game. The class stores all the decks that are available for the user and the win games.
I tried to keep it as simple I could, because we will not be using this class
very much, however and is very important for the flow of thoughts and to strengthen the
logic behind the game.

>**NOTE:** In future if we would like to add some more functionalities than
> it would be easier to change something in the Player class instead of working on
> the PlayingPlayers class or even the Deck class. The Player class is more to break
> the logic code in some steps to achive a bigger goal.

The next class, **Deck** as it name suggests it's a collection of some cards,
which makes the entire deck. The Deck Object can be created with **3** methods:
* *From input data*
* *From another deck data*
* *Create an empty deck* - this method will be useful when we will create the
* player hand, and we would like to have no card until the game is started.

Behind the scenes the **Deck** class is just a simple *wrapper* class for the *List and ArrayList*
structures. We want to keep the functionality of adding removing or changing some cards
in the Deck class and not to expose them to the world.

The last class is **PlayingPlayer** which has two relations with the classes discussed above:
* *With Deck* - Aggregation
* *With Player* - Inheritance

We want that every **playing player** will have a deck to takes cards from
and a hand to put his cards. For this functionality to work we need to have some decks.
On the other hand a player cannot play if it is not a player, for this we need to
inherit the **Player** class and to give **basic** functionalities to a player.

>**NOTE:** As I mentioned above the whole Player structure is found in the **PlayingPlayer**
> , because the playing player can have an allocated hero *which a simple player cannot have)
> it also can choose a deck to play the game session it can be his turn or not and
> finally it can gain mana for the game or can lose it. This is the strongest reson
> why I have created the **PlayingPlayer** class.


<a name="battle-desc"></a>
## **Battlefield Package**


<a name="battle-desc-desc"></a>
### **Description**

As for players there cannot be a valid game if there is not a right and
**dangerous** battlefield filled with a lot of angry and harmfully **Minions**.
As usual, the **BattleTable** Class that exists int this package does try
not to communicate with the outer world, however it has a **strong** connection
with the **Minion** class that we will discuss in the next sections. For now keep
enjoying the beauty of a good game.


<a name="battle-ulm-desc"></a>
### **ULM Schema**

![battlefield ulm schema](images/battlefield-hierarchy.jpg)


<a name="battle-expl-desc"></a>
### **Explaining the ULM**

The **BattleTable** Class is as easy as it sounds. The class is a maintainer
of a matrix full of **minions**, and all the actions are related to the main
matrix **table** field (the core of the class itself). It is very important to understand
that the class cannot exist without *minions*, because it will be just a simple field and
not a **battlefield**.

Keep for now in mind that the **BattleTable** Object (when it will be instanced) is the main place
where all the actions happen, for this reason I tried to encapsulate the functionalities as much as I could,
for this reason the only instance there the **BattleTable** can be found is in the **GwentStone** class,
however about this special class we will discuss in the next sections.

>**NOTE:** A very important nuance is that the battle table has some functions to check the table
> integrity and to clear the table after each game session played (match). More about this functionality
> will be discused on the *Main game* section 


<a name="commands-desc"></a>
## **Commands Package**


<a name="actions-desc"></a>
## **Actions Package**


<a name="game-desc"></a>
## **Main Game**
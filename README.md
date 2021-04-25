Team Name: WhyNotBot
======

Team Members:

Mohamed Eltayeb Student Number:19349633

Cian O'Reilly Student Number:19394833

Tom Higgins Student Number: 19343176

Installation:
======
// Eclipse

Step 1. Open up the project in Eclipse,
Step 2. click the Main.java 
Step 3. Click run

//Jar File
Step 1. Double click the risk.jar

Sprint 5
======
Reinforcements:
Trading in Cards: It will not trade in unless its forced to trade in (e.g CCC)

For Player:   Code reinforcements territory with the least amount of armies that shares a border with
              the enemy, else bordering a neutral
For Neutrals: it randomly selects them

Defending: Always defends with 2 if available else 1;

Attacking: Each attack is given a probability of winning
 Any attack with probability greater than 0.5 is executed

Fortification:  This is not a useful tool and thus our bot skips fortification always

Sprint 4
======

1.Include the use to territory cards, that is, the winning of territory cards after combat and the exchange of territory cards for reinforcements, 
according to the rules of Risk☑

2.Users should be allowedto enter just the first letter of the insignias that they wish to exchange, e.g. “III” for exchange of 3 infantry cards.☑

3.The user should receive appropriate error messages if their exchanges are invalid.☑

![alt text](https://github.com/UCD-COMP20050/WhyNotBot/blob/main/src/ImagesForGithub/Sprint4.PNG?raw=true)

Sprint 3 
======

1.Roll a dice each to see who takes the first turn. Highest roll does first. Re-roll if a draw. Inform the user.☑

2.Allow the players to play a series of reduced turns. The turns do not include getting or exchanging territory cards:
Calculate the number of reinforcements that a user gets based on the number of occupied countries and continents. 
Inform the user.

3.Allow users to place their reinforcements on their territories. Update the map after every update.
 Provide error messages and allow re-entry of instructions, as appropriate.
 Ensure that all reinforcements are placed.oAllow combat between armies. 
 Combat takes place according to the rules of Risk. 
 When the user does not want a further attack, the usermay enter the command “skip”.
 Allow users to fortify their positions after combat according to the rules of Risk.
 If the user does not want to fortify, the user may enter the command “skip”.☑
 
 4.If a neutral player’s armies are eliminated, the app must deal with them not having a turn.☑
 
 5.If the other human player’s armies are eliminated, the app should display the name of thewinner and a game over message.☑
 
 6.The user should receive appropriate error messages if their commands are invalid.☑
 ![alt text](https://github.com/UCD-COMP20050/WhyNotBot/blob/main/src/ImagesForGithub/Sprint3.PNG?raw=true)
 
Sprint 2
======

1.As part of asking the player’s names, allocate colours to them.☑

2.Give players 36 armies and neutrals 24 armies.☑

3.Allow players to draw territory cards from the deck. Inform the users which cards are drawn. Place the number of armies that each player has accordingly.
Note, wild cards and card insignia are not needed yet. At the end, update the map.☑

4.Roll a dice each to see who places their reinforcements first. Highest roll does first. Re-roll if a draw. Inform the user.☑

5.Players take it in turns to place 3 units at a time on a territory that they control and then one unit for each neutral.
 The user types in the name of the territories.The user should be allowed to enter a shortened version of the name, so long as it is unambiguous. 
 After each selection, update the map.☑
 
6.The user should receive appropriate error messages if their commands are invalid.☑

![alt text](https://github.com/UCD-COMP20050/WhyNotBot/blob/main/src/ImagesForGithub/Sprint2CardDrawn.PNG?raw=true)

![alt text](https://github.com/UCD-COMP20050/WhyNotBot/blob/main/src/ImagesForGithub/Sprint2.PNG?raw=true)
Sprint 1
======

Overview:
======
We have created a Java Project which completes all the requirements for the first sprint.

The requirements state the app should:
1.Display a blank map, i.e. a map with no armies on it.âœ”

2.Get the names of the two human players.âœ”

3.Allocate 9 territories to each of the two players and 6 territories to each of the 4 neutral players. âœ”
The allocation does not need to be random. Put one army on each territory.

4.Display the map showing the units.âœ”

![alt text](https://github.com/UCD-COMP20050/WhyNotBot/blob/main/src/ImagesForGithub/SplashScreenSprint1.PNG?raw=true)
![alt text](https://github.com/UCD-COMP20050/WhyNotBot/blob/main/src/ImagesForGithub/Sprint1.PNG?raw=true)



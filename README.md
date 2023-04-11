
# Survival Dot

## A simple but fun to play endless survival game!

This project is a **2D survival game**. The player will be controlling a single character to move around and dodge
projectiles, eliminate enemies, gain and upgrade weapons and try to reach a high score. As the game progresses, enemies
will increase and different enemies will appear.

This project is interest to me because I personally loved games that are endless and require beating a high score. 
I have programmed several games before, but I want to create one that is simple but fun and easy to play as a desktop 
application.

The following will be some characteristics of the game:
- Player will have hitpoints that decrease to 0 when hit by projectiles or enemies, and it is game over when it is 0
- Player's score increases as time progresses and enemies are eliminated
- Players can pick up random weapons around the map
- Saving and loading the game should be possible, along with resetting and storing highscores
- Enemies will vary, with different damage outputs, hitpoints and speed to reach player

Sources:
JsonSerializationDemo, taken from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
Inspiration for saving and loading using Json

Logging system taken from AlarmSystem https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

## User Stories
- As a user, I want to be able to move in four directions in the game, and see my current position as a player
- As a user, I want to have enemies spawn randomly to a list of enemies where I can see how many there are
- As a user, I want to be able to fire bullets from my current position (player position) many times,
- As a user, I want to be able to move bullets in their respective directions 
- As a user, I want to be able eliminate enemies and see that I did so
- As a user, I want to choose to save my game state of the most recent state. This saves all information of the game 
- As a user, I want to choose to load my most recent saved gamestate to get the saved information of the game


## Instructions for Grader
- Run the Game.
- Play the game by using the arrow keys.
- Shoot bullets to randomly spawned enemies by pressing the space bar.
- Enemies will spawn randomly in intervals.
- Note that you should also gradually speed up.
- Press escape to pause the game and open an menu.
- As prompted, save or load the game, or speed up the game by specifying a speed that is greater than current speed.
- Do not press escape again, as I saw that this causes problems sometimes
- After pressing load, you should have the most recent saved state loaded, 
- or the game saved and continue playing the current version
- When you hit an enemy, your health should decrease. When 0, it is game over. You may restart the game, or exit.
- When exiting, you should see the event log appear

## Phase 4: Task 2, Example Event Log
- Sun Apr 09 16:54:51 PDT 2023
- Started Game
- Sun Apr 09 16:54:53 PDT 2023
- Sped Up Beings
- Sun Apr 09 16:54:54 PDT 2023
- Spawned Enemy
- Sun Apr 09 16:54:56 PDT 2023
- New Bullet Fired
- Sun Apr 09 16:54:56 PDT 2023
- New Bullet Fired
- Sun Apr 09 16:54:56 PDT 2023
- Enemy Removed by Bullet
- Sun Apr 09 16:54:56 PDT 2023
- New Bullet Fired
- Sun Apr 09 16:54:57 PDT 2023
- Bullet Removed from Boundary
- Sun Apr 09 16:54:57 PDT 2023
- Bullet Removed from Boundary
- Sun Apr 09 16:54:57 PDT 2023
- Spawned Enemy
- Sun Apr 09 16:54:57 PDT 2023
- Sped Up Beings
- Sun Apr 09 16:54:58 PDT 2023
- Player Collided with Enemy
- Sun Apr 09 16:55:01 PDT 2023
- Spawned Enemy
- Sun Apr 09 16:55:02 PDT 2023
- Sped Up Beings
- Sun Apr 09 16:55:02 PDT 2023
- New Bullet Fired
- Sun Apr 09 16:55:02 PDT 2023
- Enemy Removed by Bullet
- Sun Apr 09 16:55:04 PDT 2023
- Spawned Enemy
- Sun Apr 09 16:55:05 PDT 2023
- New Bullet Fired
- Sun Apr 09 16:55:05 PDT 2023
- New Bullet Fired
- Sun Apr 09 16:55:06 PDT 2023 
- Bullet Removed from Boundary 
- Sun Apr 09 16:55:06 PDT 2023 
- Bullet Removed from Boundary 
- Sun Apr 09 16:55:06 PDT 2023 
- New Bullet Fired 
- Sun Apr 09 16:55:06 PDT 2023 
- New Bullet Fired 
- Sun Apr 09 16:55:06 PDT 2023 
- Enemy Removed by Bullet
- 
   Sun Apr 09 16:55:07 PDT 2023
- 
   Bullet Removed from Boundary
- 
   Sun Apr 09 16:55:07 PDT 2023
- 
   Sped Up Beings
- 
   Sun Apr 09 16:55:08 PDT 2023
- 
   Spawned Enemy
- 
   Sun Apr 09 16:55:09 PDT 2023
- 
   New Bullet Fired
- 
   Sun Apr 09 16:55:09 PDT 2023
- 
   Enemy Removed by Bullet
- 
   Sun Apr 09 16:55:11 PDT 2023
- 
   Spawned Enemy
- 
   Sun Apr 09 16:55:11 PDT 2023
- 
   Sped Up Beings
- 
   Sun Apr 09 16:55:14 PDT 2023
-  
   Player Collided with Enemy
-  
   Sun Apr 09 16:55:15 PDT 2023
- 
   Spawned Enemy
- 
- Sun Apr 09 16:55:16 PDT 2023 
- Sped Up Beings 
- Sun Apr 09 16:55:16 PDT 2023 
- Player Collided with Enemy 
- Sun Apr 09 16:55:18 PDT 2023 
- Spawned Enemy 
- Sun Apr 09 16:55:20 PDT 2023 
- Sped Up Beings 
- Sun Apr 09 16:55:21 PDT 2023 
- Player Collided with Enemy 
- Sun Apr 09 16:55:21 PDT 2023 
- Spawned Enemy 
- Sun Apr 09 16:55:22 PDT 2023 
- Player Collided with Enemy 
- Sun Apr 09 16:55:22 PDT 2023 
- Game Over



## Phase 4: Task 3

One refracting I would make is for the SGame class. It feels that SGame is responsible 
for too many actions of the whole program, and could be split into different classes to adhere to single responsibility 
principle. This can be done by splitting the different methods that considers the bullets, the players and enemies to be
different classes. For example, spawn enemy or update enemy, move bullets or fire bullet and check gameover are 
different methods that deal with different classes, so they may be split into different classes. 

Another final change I would make is implementing a singleton pattern for SGame. There is always only one instance of 
SGame, and in the UI, I have to pass the SGame I am working with around different classes to get information from it by 
having a parameter , which can be avoided if SGame is a singleton: there is only one instance that can be accessed from 
multiple classes. However, one issue that would arise is the loading of the game. Currently I create a new instance of a 
game and make this new game equal to the loaded game, which can not be done with a singleton. I would need a reset function 
and change each part of the one SGame instance that exists if I were to implement a singleton pattern.

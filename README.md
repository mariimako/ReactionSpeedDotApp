
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

## User Stories
- As a user, I want to be able to move in four directions in the game, and see my current position as a player
- As a user, I want to add enemies to a list of enemies where I can see how many there are
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
- Note that you should gradually speed up.
- Press escape to pause the game and open an menu.
- As prompted, save or load the game.
- After exiting the popup window, you should have the most recent saved state loaded, 
- or the game saved and continue playing the current version
- When you hit an enemy, your health should decrease. When 0, it is game over. You may restart the game, or exit


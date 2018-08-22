## About
This is a log parser for 'Quake 3 arena' to organize informaion of each game.  
And a script that prints information from the log parser and search API.

### Information organized by parser
1. Total Kills
2. Players
3. Kills of Players
4. Kills by Means
### Search
1. Function that allowes to search information of one ID
### Information organized by parserHandler
1. Ranking of a game
2. Raking of all games

### Example
The script will output Information and Ranking of Game
```
1: {
    total_kills: 45;
    players: [Dono da bola, Isgalamido, Zeh]
    kills: {
      Dono da bola: 5,
      Isgalamido: 18,
      Zeh: 20
    }
    kills_by_means: {
      MOD_SHOTGUN: 20,
      MOD_RAILGUN: 18,
        MOD_GAUNTLET: 7
    }
}
[Ranking]
1. Isagalamido (18)
2. Zeh (20)
3. Dono da bola: (5)
```

### Plus
The kills by means is from the [source code](https://github.com/id-Software/Quake-III-Arena/blob/master/code/game/bg_public.h).

### Observations
1. When `<world>` kills a player, the player loses 1 kill.
2. total_kills are the total kills of the game including the kills of `<world>`.
3. When a player kills himself (with a granade for example) total_kills and kills do not change.

## How it works
### logparser
- From a games.log file, the start of every game is defined at the log `InitGame` and the end is defined at `ShutdownGame` or `InitGame` because `ShutdownGame` was not always present.
- The script adds new information of a player (ID and kills) in a list of players when there is a log of `ClientConnect`. 
   - The ID is added to the player based on the log of `ClientUserInfoChanged`.
   - Based on the log of `Kill`, kills of player are calculated in the list of each player. 
- Based on the log of `Kill`, total kills and kills of means are calculated.
- When the game ends, information of the previous items are added to the list of the information of the game.
### search
When you write "search [ID]", the code reviews every game and excludes the games where the ID is not present.  
### logparser handler
Every game is organized in a way that shows the ranking of kills per player in every game.  
A final ranking of all games is calculated by the sum of each game.

## Instructions
1. ```git clone https://github.com/JihyeKwon06/logparser.git```
2. ```cd git/logparser/BackendTest/target/classes/```
3. ```java com.github.handler.LogParserHandler```  
 if you want to change the path of the log file, write the new path after the class name LogParserHandler, like this:
 ```java com.github.handler.LogParserHandler [new path]```
4. Enter the order.
    - Press enter to see the information of all games.  
    - Write 'search [ID]' to search for a specific ID.


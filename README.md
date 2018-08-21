## About
This is a log parser for 'Quake 3 arena' to organize informaion of each game.

### Information of Game
1. Total Kills
2. Players
3. Kills of Players
4. Kills by Means

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
      MOD_SHOTGUN: 10,
      MOD_RAILGUN: 2,
        MOD_GAUNTLET: 1
    }
}
[Ranking]
1. Isagalamido (18)
2. Zeh (20)
3. Dono da bola: (5)
```

### Plus
The kills by means is from [the source code] (https://github.com/id-Software/Quake-III-Arena/blob/master/code/game/bg_public.h)

### Observations
1. When `<world>` kills player, the player loses 1 kill.
2. total_kills are total kills of the game include the kills of `<world>`.
3. When player kill himself total_kills and kills not changes.

## How it works
### logparser
- From a games.log file, the start of every game is defined at the log `InitGame` and the end is defined at `ShutdownGame` or `InitGame` because `ShutdownGame` was not always present.
- The script adds new information of a player (id and kills) in a list of players when there is a log of `ClientConnect`. 
   - The ID is added to the player based on the log of `ClientUserInfoChanged`.
   - Based on the log of `Kill`, kills of player are calculated in the list of each player. 
- Based on the log of `Kill`, total kills and kills of means are calculated.
- When the game ends, information is added to the list of the information of the game.
### search
When you write "search ID" in the console, the code reviews every game and excludes the games where the ID is not present.  
### logparser handler
Every game is organized in a way that shows the ranking of kills per player in every game.


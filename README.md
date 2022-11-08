This project is part of ECE NTUA's Multimedia Technology Course 2021-2022 and it works as an introduction to building apps with JavaFX and SceneBuilder

In this project we were tasked to create a simple hangman game where the user could create new dictionaries from the descriptions of books found in openlibrary.org

The dictionaries are created using the openlibrary api found at "http://www.openlibrary.org/works/" and they must follow the rules below to be considered valid:
We don't insert words more than one time
The dictionary must contain at least 20 words
We insert words that have at least 6 letters
At least 20% of the words in the dictionary must have 9 letters or more

After the player creates a dictionary and picks it as the active dictionary he can start a game. A word is picked randomly from the chosen dictionary and the game starts. The player has 6 lives and in each round he picks a letter and it's position in the word. The list of letters is sorted by the probability of each letter being at the chosen position. This probability is calculated based on the total of the words from the dictionary that could be the hidden word based on the number of letters of the hidden word, the revealed characters and their positions and the rejected characters. The probabilities can also be found on the right part of the screen.

There is also a point system which rewards the player with 5 points if he found a letter with 0.6 or higher probability, 10 points for a letter with probability between 0.4 and 0.6, 15 points for a letter with probability between 0.25 and 0.4 and 30 points for a letter with a probability less that 0.25. A wrong guess subtracts 15 points with the lowest score possible being 0 points.

If the player loses all 6 lives the word is automaticly revealed.

On the top of the screen the player can see the available words in the current dictionary, his total points and the percentage of correct guesses made in the current game

Finally through the menu the player can see statistics about the length of words in the current dictionary(Details->Dictionary), information about the 5 last rounds(Details->Rounds) and can forfeit the current game and see the solution (Details->Solve), in which case the game registers as a lost game for the player.

The app is made using Java version 17.0.2, JavaFX-18, json-simple-1.1.1
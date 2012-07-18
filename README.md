# Mastermind

### About the Mastermind board game

Mastermind is based on the popular kid's board game of the same name. 

For those unfamiliar, Mastermind is a deductive logic game where the goal
is to figure out a secret combination of colored pegs. After each incorrect
guess, the guesser is told which pegs were exactly correct (i.e. the right 
color and in the right position) and which pegs were close (i.e. the right
color but the wrong position). 

With each new guess, more combinations can be ruled out, and eventually the
guesser figures out the right combination. Like many games that leverage
logic, there have been many studies on best algorithms to arrive at the correct
combination with a minimum of guesses. 

### About this Android app

The board game lends itself to an application because one player basically acts
as the computer while the other guesses, and then the players switch. Also each
play-through is fairly quick and each game slightly unique do to a new random 
code being chosen. 

The app differs from the board game in that there are several versions (and you can
make your own custom version) which adds new dimensions to the game. In the board game
there are a finite number of moves before the guesser loses, but in this app, the user
gets as many guesses as it takes. The average guesses per game version is aggregated.
Also it is roughly translated into 10 languages.

### About the code

It took me a long time to learn how to make an Android app, and I threw away probably
3 or 4 apps before releasing this one. So its all up here if you want to see how a fairly
simple app is made. 

Warning: It is my first released program after a little over a year of programming experience.
There are probably awful design decisions and messy code one way or another. 

The game is entirely user-input driven, meaning it responds to button clicks
which means it does not need a game loop. All together the app is tiny at 177kb and
it is free, so check it out!

### Google Play

Get it on google play: https://play.google.com/store/apps/details?id=com.ostrow.mastermind&feature=search_result#?t=W251bGwsMSwxLDEsImNvbS5vc3Ryb3cubWFzdGVybWluZCJd

Hopefully you like it, and if I get my hands on an Android phone I might make another app
in the future. Probably integrated with a web app. 
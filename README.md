Zordzman is a messy 2D RPG Game made in Java, started at
around October 13, 2013.

Main reason I'm putting it on GitHub is to get some help
cleaning the code up.

## Libraries ##

You'll need Slick 2D ( Doesn't really matter what version, its
 only used for texture loading anyway), and LWJGL (very latest
 version recommended).
I've also written a class for doing things with LWJGL rather
quickly, called NewGLHandler, it's supplied in the lib/jars
directory.

There's also the lwjgl_util.jar that you'll need to get.
Can't remember if that's actually needed or not, though...

# Trivial Stuff #

## Plot ##

Basically the plot is that a Nordic boy, Zorrvik, sets out
to destroy the evil Omnom, who has been terrorizing his
kind. He must save various locations from Omnom.

## Gameplay ##

The Player can select a map on singleplayer or join a server
on multiplayer (currently only localhost, though), and they 
can use WASD to move, Spacebar to attack, and Z/X to switch to
Combat weapon / Special Weapon.

Combat Weapons are what they are, mainly used for combat with
enemies in the maps.

Special Weapons are weapons or items that have a special purpose
other than combat, such as food to heal the player, or a shield
to increase the player's maximum HP. Some of these Special Weapons
also have negative effects, such as making the player burn faster.

## Graphics ##

The graphics are meant to look a bit like Legend of Zelda on the NES
or something, but I'm very bad at art. All the sprites and tiles are
stored in spritesheets. You can find UI things such as buttons in the
res/gui folder.

## Level Format ##

Levels are, right now, just folders that end in '.map' (too lazy).
Inside them are .lvl files (Level files, contain all the tiles), .ents
(Contain all the entities), and a script.lua file which can contain
functions that are called when certain events happen, such as a player
picking up a health pickup.

![alt text](https://github.com/rakib-shahid/Siege-Settings-app/blob/main/res/r6s48.png?raw=true)


# R6Siege-Settings-app

It's annoying to go into the Documents folder and manually edit lines in GameSettings.ini so I made this.

Provides a UI for easily changing many settings for Rainbow 6 Siege without having to launch the game or find the settings files

Allows you to change things like sensitivities and mouse multipliers, display options like fov, refresh rate, etc.

![siegesettings ui](https://user-images.githubusercontent.com/95511504/172519069-51b9271b-8691-49a4-922b-28ccfa36313c.png)


### Upon running, it creates a backup file of settings in case the user wants to revert

The file is called GameSettings.ini.backup in the same folder as GameSettings.ini
The program automatically applies the same settings to all Uplay accounts on the PC, so if you want to avoid that, use the backup file to replace the settings

## Usage

Download and run the .exe or .jar, or compile the .java file yourself

Use the tabs to navigate between setting categories and hit the apply button to save

## Issues

there are probably quite a few but it works for me ¯\\_ (ツ) _/¯

~~The window *really* does not like to be resized so be nice to it and leave it how it is~~ The window is no longer resizable

issues may arise when providing invalid inputs for multipliers but it is untested

again, if any issues do arrise, delete the GameSettings.ini file and rename the backup file to replace it

## To-do(?)

Fix the ~~resizing and~~ layout and maybe make it prettier

Add aspect ratios, graphics settings, and more

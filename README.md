# Click4Ever
Create, run, and save multiple auto clickers on both Mac and Windows

## Features
* Runs on Mac and Windows
* Simulate both mouse clicks and key presses
* Create, run, delete, and save multiple auto clickers, allowing you to simulate left clicks and right clicks together or any other combination of keys and mouse buttons
* Configure random delays
* Activation options:
     * Hold to activate
     * Tap once to activate and tap again to deactivate

## Download
Click on "Version 1.0.0 Release" to the right. When running on Mac, enable the application to control the computer using accessibility features and restart the application after doing so

## Screenshots
<img src="https://github.com/maseratirex/Click4Ever/assets/88254697/8ac76484-7ac8-4542-9e7a-f61de1517d21" width="400" height="400">
<img src="https://github.com/maseratirex/Click4Ever/assets/88254697/02927d24-2b3f-4681-afdf-2f7b0b5600a8" width="400" height="400">
<img src="https://github.com/maseratirex/Click4Ever/assets/88254697/43c9d8ba-05f1-4a53-828b-8c5bb1811913" width="400" height="400">

## Issues
* On Mac, Java's Robot class, which handles the simulation of mouse clicks and key presses, has a major bug. When the Robot simulates a mouse click (not a key press), the cursor is snapped back to the mouse coordinates of the first click
     * Workaround: if using the auto clicker for a video game, navigate to the game settings and bind the action you want to simulate (for example a left click) to another key. Then have the auto clicker simulate pressing that key

# FSAMS
Fire and Security Alarm Monitoring Simulation System

A fun project for CSC 481 Software Engineering at CSUDH. 

Given a vague and self-contradictory requirements written by the "user" we were given just a few short weeks to produce a "Fire and Security Alarm Monitoring Simulation System" complete with a detailed and refined software requirements specification.
This is the result...

First, construct an office building using the gridlayout system to place walls, sensitive-equipment, lockable doors, fire sensors and alarms, fire sprinklers. Press and hold the mouse button while moving the mouse to pan, and use the mouse wheel to zoom in and out. After selecting the type of item you would like to place from the pan on the left, simply click on a grid to add the item to the gird square. To place walls, click on the gridlines. To remove an object right click it.

Make sure add people to your building along with designated exits. Finally, add some fire to you building and hit the start button to see how well your building holds up.

The fire will spread to nearby squares using a stochastic algorithm. The fire will ocassionally burn itself out, but sprinklers are far more effective. The fire will trigger alarms and sprinklers when it gets too close. People in the building will begin moving towards the nearest exit while avoiding the spreading fire using the A* pathfinding algorithm.

You can mannually operate the activation and deactivation alarms, sprinklers, and sensitive-equiptment as well as the locking and unlock of doors using the buttons on the left pane.

Once the fire has been detected, a 30 second count down will begin. If the fire is not extinguished by the end of the countdown, all of the building will engage sprinklers, suchdown sensitive equipment,  emergency services will be notified.

You have the option to save the current configuration to a file so you can continue working on it a later date.

## Have Fun!

![alt tag](https://raw.githubusercontent.com/jnhankins/FSAMS/master/Documentation/screenshot1.png)
![alt tag](https://raw.githubusercontent.com/jnhankins/FSAMS/master/Documentation/screenshot2.png)


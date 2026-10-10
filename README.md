
<img width="650" height="550" alt="image" src="https://github.com/user-attachments/assets/24f78ecf-a98b-42b6-9e02-8a3bd432427b" />

# Platformer

A simple multi-player platform game made with Spring Boot and libGDX. All game logic handled by Spring Boot.<br>
Players can build their own map and play. <br>

Other services/tools:
- Database: H2
- Skin: default/uiskin, ui/iskin, lgdxs-ui
- Icon: created for the project with PixelArt.com

## Items in the game
- `static block`: solid block. Fixed height and customizable width. Can be combined together.
- `moving block`: solid block with fixed size moving with fixed speed. Can move to all different directions.
- `door-key pair`: a door is a solid block, that can be removed by going into its corresponding key. 
- `exit`: win the game by going into the exit

## Feature

### Main screen
The player can choose between the play mode and build mode.<br>

<img width="520" height="440" alt="image" src="https://github.com/user-attachments/assets/c23d94ee-6676-4fb7-a476-6161ba7812ef" />

### Select screen
For both play mode and build mode, player can select from the available maps. <br>
The player can hover on map options to preview the map layout.

<img width="520" height="440" alt="image" src="https://github.com/user-attachments/assets/4d982385-7f31-4407-83dc-f7924815c68b" />

In the build mode, the player can choose to create a new map or edit existing maps.<br>
> The default map is not available since it is not editable by players.<br>

<img width="520" height="440" alt="image" src="https://github.com/user-attachments/assets/d187a62b-83a7-433b-8a41-d84b9d77ba14" />

### Play screen
The player can control the movement of the character with arrow keys: move left/right and jump up.<br>
A timer counts the total time until the player hits the exit.

<img width="520" height="440" alt="image" src="https://github.com/user-attachments/assets/24f78ecf-a98b-42b6-9e02-8a3bd432427b" />
<br/><br/>
Multiplayer is also supported. The player can see the movement of other players in the same map.
<br/><br/>

<img width="520" height="440" alt="image" src="https://github.com/user-attachments/assets/52285682-1e72-4879-a17c-7993d7091aa3" />
<br/><br/>
The player can win the game by going into the exit. A dialog would be shown with final total time.
<br/><br/>
<img width="520" height="440" alt="image" src="https://github.com/user-attachments/assets/9930ea68-2c2a-4fe4-b9d5-6397c47f539f" />

### Build screen

The player can freely move around and use key 1-6 to perform different operations: place items, revert last step, or remove all the blocks intersecting with the character.<br>
The player can check on building instructions on the bottom and hover on the buttons to show more detailed explanation.<br>

<img width="520" height="440" alt="image" src="https://github.com/user-attachments/assets/56257f74-ebb7-48d1-bfad-17b9a676fd1b" />
<br/><br/>
The player has to give a unique name to the map. 
All movements and operations in the map, including renaming, is synchronized to all players in the same map.
<br/><br/>
<img width="1040" height="440" alt="image" src="https://github.com/user-attachments/assets/693276ff-c857-489f-b96c-2382d55eaf9b" />


<br/><br/>
The player can click on the same button to save all new changes from all players to the current map.
The saving would fail because:
- Of unfinished block
- Of duplicated map name
- Of no exit
- Numbers of doors and keys don't match
<img width="2749" height="1082" alt="image" src="https://github.com/user-attachments/assets/9eb66fb5-7539-4421-95bf-ed96586ebf4c" />



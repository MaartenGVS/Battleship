# Programming project web project group [39]

## Before you start
* Read the documentation on [Gitlab](https://git.ti.howest.be/TI/2022-2023/s2/programming-project/documentation/battleship-documentation/-/tree/main/)
* Go to file `sonar-project.properties` and replace "39" with your **group number**.
* Do the same for the "39" below in this file.

## Project parent group
https://git.ti.howest.be/TI/2022-2023/s2/programming-project/projects/group-39

## Remote urls
### Your own project
* https://project-i.ti.howest.be/battleship-39/
* https://project-i.ti.howest.be/battleship-39/api/

### Provided API
* https://project-i.ti.howest.be/2022-2023/battleship-api-spec/


## Please complete the following before committing the final version of the project
Please **add** any **instructions** required to
* Make your application work if applicable
* Be able to test the application (login data)

Also clarify
* If there are known **bugs** (use the template)

## Instructions for local CI testing
You can **run** the validator and Sonar with CSS and JS rules **locally.** There is no need to push to the server to check if you are compliant with our rules. In the interest of sparing the server, please result to local testing as often as possible.

If everyone will push to test, the remote server will not last.

Please consult the Sonar guide at [https://git.ti.howest.be/TI/2022-2023/s2/programming-project/documentation/battleship-documentation/-/blob/main/sonar-guide/Sonar%20guide.md](https://git.ti.howest.be/TI/2022-2023/s2/programming-project/documentation/battleship-documentation/-/blob/main/sonar-guide/Sonar%20guide.md)

## Client
In order to help you along with planning, we've provided a client roadmap
[https://git.ti.howest.be/TI/2022-2023/s2/programming-project/documentation/battleship-documentation/-/blob/main/roadmaps/client-roadmap.md](https://git.ti.howest.be/TI/2022-2023/s2/programming-project/documentation/battleship-documentation/-/blob/main/roadmaps/client-roadmap.md)

## File structure
All files should be places in the `src` directory.

**Do not** change the file structure of the folders outside of that directory. Within, you may do as you please.


## Default files

### CSS
The `reset.css` has aleady been supplied, but it's up to you and your team to add the rest of the styles. Please feel free to split those up in multiple files. We'll handle efficient delivery for products in production in later semesters.

### JavaScript
A demonstration for connecting with the API has already been set up. We urge you to separate your JS files as **atomically as possible**. Add folders as you please.

## Extra tips for CSS Grid
In case you get stuck or confused
https://learncssgrid.com/

And for your convenience, yet use with caution
https://grid.layoutit.com/ 

## Known Bugs
**Bug behaviour-1**: Ship wont be showed on grid.

How to reproduce: When u try to place a ship next to a border.

Why it hasn’t been fixed: No time

solution: When u click on ‘turn shits’ u need to press multiple times for ship to display.


**Bug behaviour-2**: U will see the clicked cell light up with a red border.

How to reproduce: Trying to place a ship in a place where there is no place vertically and horizontally for placement.

Why it hasn’t been fixed: No time

solution: Pressing the ‘dump ships’ button will reset the placement display’	When u try to place a ship on grid but it has no space to place is on all sides
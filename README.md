# GameOfLife - Simulation

The app is built using spring-boot to represent GameofLife simulation.

The app uses a simple in memory Map to keep the scenarios, in future we may use proper DB

The app exposes below REST endpoints:

    GET     -   /board      : List the boards present in memory
    GET     -   /board/{id} : Get the board identified by the given {id}
    POST    -   /board      : Create a new board
    PUT     -   /board/{id} : Update the board identified by the give {id} by calculating next generation
    DELETE  -   /board/{id} : Delete the board identified by the given {id}
    
The app makes use of a Angular2 UI, the files of the UI are however generated from the Angular2 CLI app, for any modification please use the https://github.com/VijayKumarTiwari/GameOfLife-UI codebase.


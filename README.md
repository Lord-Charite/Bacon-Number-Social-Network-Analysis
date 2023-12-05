# Bacon-Number-Social-Network-Analysis
Introduction

This project implements a social network analysis tool to calculate the "Bacon number" of actors, a popular concept representing the number of steps separating an actor from Kevin Bacon through shared movie appearances. The tool allows any actor to be the center of this universe, exploring the fascinating world of six degrees of separation in the film industry.

Features

- Flexible Center of Universe: Allows setting any actor as the center of the acting universe.
- Shortest Path Calculation: Finds the shortest path between two actors using breadth-first search (BFS).
- Comprehensive Data Analysis: Calculates and lists actors based on degrees of separation, number of co-stars, and average separation distance.
- Interactive Command-Line Interface: Engage with the tool through various commands for dynamic analysis.
- Extensive Dataset: Utilizes a large dataset of actors and movie appearances for analysis.

Implementation Details

- Graph-Based Analysis: Uses a graph structure to represent actor connections.
- BFS for Path Finding: Implements BFS to build shortest-path trees.
- Efficient Data Handling: Efficiently processes and maps actor and movie data from large datasets.
  
Commands

u <name>: Set the universe center to <name>.
p <name>: Find the path from <name> to the current center.
c <#>: List top or bottom centers based on average separation.
d <low> <high>: List actors sorted by degree within specified range.
s <low> <high>: List actors by non-infinite separation from the center.
i: List actors with infinite separation from the current center.
q: Quit the game.

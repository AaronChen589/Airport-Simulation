# Airport-Simulation

## Synopsis
Imagine you work for Robert F. Kennedy (RFK) Airport, a new 20 terminal airport built in the middle of
the bustling state of Montana. RFK is still in the preliminary planning stages and the state of Montana
wants to have a better idea of the outgoing air traffic they could expect at RFK. So, RFK’s investors hire
some of the best statisticians to model the outgoing air traffic at RFK.

First, these statisticians model the typical Flight out of RFK. They find that the Zoeing 373 is the most
popular commercial jetliner used with a capacity of 15 Passengers; out of these 15 passengers, 2 are First
Class, 3 are Business Class, 4 are premium economy, and 6 are economy. So, these statisticians decide to
model each flight using the Zoeing 373 as the outgoing plane. Next, they find that each flight typically
has 25 minutes to board followed by 5 minutes to depart. Finally, they talk with RFK planners for more
details on how boarding and departures will operate at RFK. Each flight also has a BoardingQueue
where a maximum of 10 passengers can wait before being let on the flight. At this queue, passengers are
prioritized by flight class with ties in priority broken by passengerID (keep reading for more information
on this ID). When boarding (dequeuing) people from this queue, if no more room exists in their class’s
seats and they’re of a higher class than open seats, they can take a seat from the next lower class
available.

1. For each boarding flight, in ascending order based on how much time is left for boarding, a
passenger is dequeued from a flight’s BoardingQueue and counted against a plane’s capacity.
(P = Given)
2. For each boarding flight, in ascending order based on how much time is left for boarding, a
passenger arrives at a flight’s BoardingQueue (P = Given)
a. This passenger is first class (P = 0.1)
b. This passenger is business class (P = 0.1)
c. This passenger is premium economy (P = 0.3)
d. This passenger is economy (P = 0.4)
e. This passenger has COVID-19, extending all current departures and boarding
by 10 minutes (i.e. if a plane is currently boarding, its allocated departing time
is not extended). (P = 0.1)
3. A new flight begins boarding at RFK (P = Given)
a. This flight is normal (P = 0.95)
b. This flight is Air Force 1, pausing all departures and boarding until its
departure (P = 0.05)

-All events are independent of each other, meaning they can all occur every minute. However, be aware
that each successive sub-event is mutually exclusive, meaning they cannot happen simultaneously (e.x.we won’t have a COVID positive person who is also in first class). -Any passenger who has COVID-19 is not allowed into a flight’s BoardingQueue.
-Every passenger is given a passenger ID equal to their arrival index (i.e. arrival index N implies this person was the Nth person to enter this particular flight’s
BoardingQueue before that flight’s final departure). 
-If all terminals are taken, refuse any new flights. 
-If a plane becomes full, you must start departure. If a BoardingQueue is full, refuse any new passengers until
space becomes available. 



## How to Start Simulation

1) Open up Airport Simulation

2)You will be prompted to enter ...
-a seed for the simulation (optional if you want consistency)
-the probability of passenger arrival time
-the probability that a passenger is dequeued
-the probability that there is a new flight at RFK
- how many minutes this simulation should take

3) Watch the simulation occur through the terminal.
    











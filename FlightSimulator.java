import java.util.Random;
import java.util.Scanner;
public class FlightSimulator {


    private static boolean OccurrenceOfAirForce = false;
    private static int CovidTimer = 0;
    private static int timelapse;

    public static void main(String [] args) throws NoRoomException, NoPassengerException {
        Flight[] flightTerminal  = new Flight [20];
        int flightIndex = 0;
        timelapse = 0;


        Scanner in = new Scanner(System.in);

        System.out.println("Enter a seed for this simulation");
        Long seed = in.nextLong();
        Random r = new Random(seed);

        System.out.println("Enter the probability of passenger arrival time:");
        float probOfArrivalTime = in.nextFloat();

        System.out.println("Enter the probability that a passenger is dequeued:");
        float probOfDequeue = in.nextFloat();

        System.out.println("Enter the probability that there is a new flight at RFK");
        float probOfNewFlight = in.nextFloat();

        System.out.println("Enter how many minutes this simulation should take ");
        int lengthOfSimulation = in.nextInt();


        // *** Start of Simulation ***

        for(int i = 0; i <= lengthOfSimulation; i++ ) {

            System.out.println("Minutes " + i);

            // the probability of all these events aren't mutually exclusive, we need separate
            // probabilities for all of events and their respective sub-events


            float probabilityOfNewFlight = r.nextFloat();
            float probabilityOfTypeOfFlight = r.nextFloat();

            // the chance of a new flight to arrive (we account for the event in which there is max terminal capacity)
            if (flightIndex == 20) {
                if (probabilityOfNewFlight <= probOfNewFlight) {
                    System.out.println("Attempting to start boarding for new flight... failed due to lack of open terminal");
                }
            }
            else {
                if (probabilityOfNewFlight <= probOfNewFlight) {
                    Flight f = NewFlightProbability(probabilityOfTypeOfFlight);

                    // Assuming that there can only be only one Air Force One flight at any given time,
                    // if new flight is air force but there is an air force in terminal, we reject it from landing

                    if(f.getIsA1Flight()){
                        if(OccurrenceOfAirForce){
                            System.out.println("There already exist an Air Force One in the terminal, rerouting flight" +
                                    " somewhere else");
                        }
                        else{
                            flightTerminal[flightIndex] = f;
                            OccurrenceOfAirForce = true;
                            flightIndex++;
                        }
                    }
                    else{
                        flightTerminal[flightIndex] = f;
                        flightIndex++;
                    }
                }
            }

            // the chance for a new passenger to arrive for each existing flights in flight terminal
            // note that the event of an air force one or covid doesn't affect passenger's chance to enqueue
            for (int j = 0; j < flightIndex; j++) {
                float probabilityOfArrivalTime = r.nextFloat();
                float probabilityOfTypeOfArrival = r.nextFloat();
                if (probabilityOfArrivalTime <= probOfArrivalTime) {
                    Passenger pass = PassengerClassProbability(probabilityOfTypeOfArrival);

                    // check if boarding queue has max capacity
                    if(flightTerminal[j].getBoardQueue().getSize() >= 10){
                        System.out.println("A new passenger attempts to board flight to " + flightTerminal[j].getDestination()
                        + "... failed due to lack of space in boarding queue");
                    }
                    else{
                        // Note: the if statement checks if passenger is null, which is the case for when they have COVID
                        // If passenger does have covid, the covid timer increases by 10 through the PassengerClassProbability method
                        if (pass != null) {
                            flightTerminal[j].getBoardQueue().enqueuePassenger(pass);
                            if(pass.getPassPriority() == 4){
                                System.out.println("Event: First class passenger" + "(ID" +  pass.getPassengerID() +")" + " has enqueued "
                                        + "the flight to " + flightTerminal[j].getDestination());
                            }
                            if(pass.getPassPriority() == 3){
                                System.out.println("Event: Business class passenger" + "(ID" +  pass.getPassengerID() +")" + " has enqueued "
                                        + "the flight to " + flightTerminal[j].getDestination());
                            }
                            if(pass.getPassPriority() == 2){
                                System.out.println("Event: Premium economy passenger" + "(ID" +  pass.getPassengerID() +")" + " has enqueued "
                                        + "the flight to " + flightTerminal[j].getDestination());
                            }
                            if(pass.getPassPriority() == 1){
                                System.out.println("Event: Economy passenger" + "(ID" +  pass.getPassengerID() +")" + " has enqueued "
                                        + "the flight to " + flightTerminal[j].getDestination());
                            }
                        }
                        else {
                            System.out.println("COVID positive passenger found attempted to board flight to "
                                    + flightTerminal[j].getDestination() + "! All current departures and boarding extended by 10 minutes");
                        }
                    }
                }
            }

            // if air force one arrives, we must stop all boarding and departing flights. only Air Force 1 can occur
            if(OccurrenceOfAirForce){
                for(int j = 0; j < flightIndex; j++) {
                    float probabilityOfBeingDequeued = r.nextFloat();
                    if(flightTerminal[j].getIsA1Flight()){
                        // if the boarding queue has someone in it, we can see if we can dequeue them
                        if(flightTerminal[j].getBoardQueue().getSize() > 0){
                            if (probabilityOfBeingDequeued <= probOfDequeue) {
                                // checks for whether the A1 flight is boarding
                                if (flightTerminal[j].IsItBoarding()) {
                                    Passenger p1 = flightTerminal[j].getBoardQueue().dequeuePassenger();
                                    flightTerminal[j].addToFlight(p1);
                                    if(p1.getPassPriority() == 4){
                                        System.out.println("Event: First class passenger" + "(ID" +  p1.getPassengerID() +")" + " has boarded "
                                                + "the Air force 1 flight to " + flightTerminal[j].getDestination());
                                    }
                                    if(p1.getPassPriority() == 3){
                                        System.out.println("Event: Business class passenger" + "(ID" +  p1.getPassengerID() +")" + " has boarded "
                                                + "the Air force 1 flight to " + flightTerminal[j].getDestination());
                                    }
                                    if(p1.getPassPriority() == 2){
                                        System.out.println("Event: Premium economy passenger" + "(ID" +  p1.getPassengerID() +")" + " has boarded "
                                                + "the Air force 1 flight to " + flightTerminal[j].getDestination());
                                    }
                                    if(p1.getPassPriority() == 1){
                                        System.out.println("Event: Economy passenger" + "(ID" +  p1.getPassengerID() +")" + " has boarded "
                                                + "the  Air force 1 flight to " + flightTerminal[j].getDestination());
                                    }
                                    //if covid timer is 0, we can resume boarding timer
                                    if (CovidTimer == 0) {
                                        flightTerminal[j].setMinutesLeftBoarding(-1);
                                    }
                                }
                            }
                        }

                        if(flightTerminal[j].getMinutesLeftBoarding() == 0) {
                            flightTerminal[j].setBoarding(false);
                            // if covid timer is 0, we can resume departing timer
                            if (CovidTimer == 0) {
                                flightTerminal[j].setMinutesLeftDeparting(-1);
                            }
                        }
                        // if it's time for departure or if we hit max capacity, the flight must depart
                        if ((flightTerminal[j].getMinutesLeftDeparting() == 0) || (flightTerminal[j].getNumOfPassengers() == 15)) {
                            Flight[] f1 = new Flight[20];
                            Flight temp = flightTerminal[j];
                            System.arraycopy(flightTerminal, 0, f1, 0, j);
                            System.arraycopy(flightTerminal, j + 1, f1, j, 20 - j - 1);
                            flightTerminal = f1;
                            OccurrenceOfAirForce = false;
                            flightIndex--;
                            System.out.println("Air Force 1 flight has departed, Resuming departures and boarding");
                        }
                    }
                }
            }
            else {
                // Determines what happens to all non Air Force One flights in the terminal ******
                for (int j = 0; j < flightIndex; j++) {
                    // checks if there is anyone in the boarding queue
                    if(flightTerminal[j].getBoardQueue().getSize() != 0){
                        float probabilityOfBeingDequeued = r.nextFloat();
                        // if the flight is boarding and there is people, then the boarding queue will dequeue a passenger and put them on to the flight
                        if (probabilityOfBeingDequeued <= probOfDequeue) {
                            // checks for whether the flight is boarding
                            if (flightTerminal[j].IsItBoarding()) {
                                Passenger p1 = flightTerminal[j].getBoardQueue().dequeuePassenger();
                                flightTerminal[j].addToFlight(p1);
                                if(p1.getPassPriority() == 4){
                                    System.out.println("Event: First class passenger" + "(ID" +  p1.getPassengerID() +")" + " has boarded "
                                            + "the flight to " + flightTerminal[j].getDestination());
                                }
                                if(p1.getPassPriority() == 3){
                                    System.out.println("Event: Business class passenger" + "(ID" +  p1.getPassengerID() +")" + " has boarded "
                                            + "the flight to " + flightTerminal[j].getDestination());
                                }
                                if(p1.getPassPriority() == 2){
                                    System.out.println("Event: Premium economy passenger" + "(ID" +  p1.getPassengerID() +")" + " has boarded "
                                            + "the flight to " + flightTerminal[j].getDestination());
                                }
                                if(p1.getPassPriority() == 1){
                                    System.out.println("Event: Economy passenger" + "(ID" +  p1.getPassengerID() +")" + " has boarded "
                                            + "the flight to " + flightTerminal[j].getDestination());
                                }
                                //if covid timer is 0, we can resume boarding timer
                                if (CovidTimer == 0) {
                                    flightTerminal[j].setMinutesLeftBoarding(-1);
                                }
                            }
                        }
                    }

                    // if the flight's boarding has ended or ended already, we need to start departing
                    if (flightTerminal[j].getMinutesLeftBoarding() == 0) {
                        flightTerminal[j].setBoarding(false);
                        // if covid timer is 0, we can resume departing timer
                        if (CovidTimer == 0) {
                            flightTerminal[j].setMinutesLeftDeparting(-1);
                        }
                    }
                    // if it's time for departure or if we hit max capacity, the flight must depart
                    if ((flightTerminal[j].getMinutesLeftDeparting() == 0) || (flightTerminal[j].getNumOfPassengers() == 15)) {
                        Flight[] f1 = new Flight[20];
                        Flight temp = flightTerminal[j];
                        System.arraycopy(flightTerminal, 0, f1, 0, j);
                        System.arraycopy(flightTerminal, j + 1, f1, j, 20 - j - 1);
                        flightTerminal = f1;
                        flightIndex--;
                        System.out.println("***Departing***");
                        System.out.println("Flight to " + temp.getDestination() + " has departed");
                    }
                }
            }


            if(!OccurrenceOfAirForce){
                System.out.println("***Boarding***");
                // prints out all flights that are currently boarding
                for(int y = 0; y < flightIndex; y++){
                    if(flightTerminal[y].IsItBoarding()){
                        System.out.println("Flight to " + flightTerminal[y].getDestination() +
                                " has " + flightTerminal[y].getMinutesLeftBoarding()+ " minutes to board, "+
                                flightTerminal[y].getNumOfPassengers()+ " passenger(s), " + "and " + flightTerminal[y].getBoardQueue().getSize()
                                +  " passenger(s) " + "waiting to board" );
                    }
                }
                System.out.println("***Departing***");
                // prints out all non Air force 1 flights that are currently departing
                for(int y = 0; y < flightIndex; y++){
                    if(flightTerminal[y].getMinutesLeftBoarding() == 0){
                        System.out.println("Flight to " + flightTerminal[y].getDestination() +
                                " has " + flightTerminal[y].getMinutesLeftDeparting()+ " minutes to depart, "+ "and "  +
                                flightTerminal[y].getNumOfPassengers()+ " passenger(s)");
                    }
                }
            }
            else{
                System.out.println("Boarding and Departing are paused till Air Force 1 departs from terminal");
            }

            if (CovidTimer > 0) {
                System.out.println("Due to COVID, boarding and departure times have been paused for a total of" +
                        " "  + CovidTimer + " minutes");
                CovidTimer -= 1;
            }
            timelapse++;
            System.out.println("=======================================================================");
        }

        System.out.println("Simulation terminated. Thank you for choosing RFK!");

    }
    // when the boarding queue is triggered, we trigger this to find a suitable passenger for the queue
    public static Passenger PassengerClassProbability(float probOfEvents){
        Passenger p = null;

        if (probOfEvents <= 0.12){
             p = new Passenger(timelapse, Passenger.TravelClass.firstClass);
        }
        if( 0.12 < probOfEvents && probOfEvents <= 0.24){
             p = new Passenger(timelapse, Passenger.TravelClass.businessClass);
        }
        if( 0.24 < probOfEvents && probOfEvents <= 0.56){
             p = new Passenger(timelapse, Passenger.TravelClass.premiumEconomy);
        }
        if( 0.56 < probOfEvents && probOfEvents <= 0.98){
             p = new Passenger(timelapse, Passenger.TravelClass.Economy);
        }
        if( 0.98 < probOfEvents && probOfEvents <= 1.0){
            CovidTimer += 10;
        }
        return p;
    }

    public static Flight NewFlightProbability(float probOfEvents){
        Flight f = null;
        Scanner in = new Scanner(System.in);

        // 95 % chance of a regular flight and constructor sets timer that will go off when conditions are met
        if(probOfEvents <= 0.95){
            f = new Flight(25, 5);
            f.setIsA1Flight(false);
            System.out.println("Enter the destination of the flight");
            String destination = in.nextLine();
            f.setDestination(destination);
            f.setBoarding(true);
            System.out.println("Event:" + " A new flight to..." + f.getDestination() + "...has begun boarding!");
        }
        if(0.95 < probOfEvents && probOfEvents <= 1.0){
            f = new Flight(25, 5);
            f.setIsA1Flight(true);
            System.out.println("Enter the destination of the flight");
            String destination = in.nextLine();
            f.setDestination(destination);
            f.setBoarding(true);
            System.out.println("Event:" + " A new flight on Air Force 1 to..." + f.getDestination() + "...has begun boarding!");
        }
        return f;
    }
}

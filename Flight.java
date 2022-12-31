public class Flight {

    private String destination;
    private BoardingQueue boardQueue;
    private Passenger [] firstClass = new Passenger[2];
    private Passenger [] businessClass = new Passenger[3];
    private Passenger [] premiumEconomy = new Passenger[4];
    private Passenger [] economy = new Passenger[6];
    private int minutesLeftBoarding;
    private int minutesLeftDeparting;
    private int firstClassInd;
    private int businessClassInd;
    private int premiumClassInd;
    private int economyInd;
    private boolean boarding;
    private int numOfPassengers;
    private boolean isA1Flight;


    public Flight(){
        boardQueue = new BoardingQueue();
        minutesLeftBoarding = -1;
        minutesLeftDeparting = -1;;
        firstClassInd = 0;
        businessClassInd = 0;
        premiumClassInd = 0;
        economyInd = 0;
        boarding = false;
    }

    public Flight(int minutesLeftBoarding, int minutesLeftDeparting){
        boardQueue = new BoardingQueue();
        firstClassInd = 0;
        businessClassInd = 0;
        premiumClassInd = 0;
        economyInd = 0;
        this.minutesLeftDeparting = minutesLeftDeparting;
        this.minutesLeftBoarding = minutesLeftBoarding;
        boarding = false;
    }
    public void setDestination(String destination){
        this.destination = destination;
    }
    public void setBoarding(boolean boarding){
        this.boarding = boarding;
    }
    public void setMinutesLeftBoarding(int diff){
        this.minutesLeftBoarding += diff;
    }
    public void setMinutesLeftDeparting(int diff){
        this.minutesLeftDeparting += diff;
    }
    public void setIsA1Flight(boolean isA1Flight){
        this.isA1Flight = isA1Flight;
    }

    public String getDestination(){
        return this.destination;
    }
    public int getMinutesLeftBoarding(){
        return this.minutesLeftBoarding;
    }
    public int getMinutesLeftDeparting(){
        return this.minutesLeftDeparting;
    }
    public boolean IsItBoarding(){
        return this.boarding;
    }
    public BoardingQueue getBoardQueue(){
        return this.boardQueue;
    }
    public int getNumOfPassengers(){
        return numOfPassengers;
    }
    public boolean getIsA1Flight(){
        return this.isA1Flight;
    }


    public void addToFlight(Passenger boardedPass) throws NoRoomException {

        // search for the target in the boarding queue and dequeue him: We assume the target to be at front of the line
        // since enqueue already sorted the boarding queue based class priority and id, so the person de-queuing will be
        // the one with the highest priority

       /* for(int i = boardQueue.getFront(); i < boardQueue.getPassengerQueue().length; i++){
            if(boardQueue.getPassengerQueue()[i].getPassengerID() == boardedPass.getPassengerID()){
                boardQueue.dequeuePassenger();
            }
        }*/
        if(boardedPass.getPassPriority() == 4){

            // if first seat in first class is empty
            if(firstClassInd == 0){
                firstClass[0] = boardedPass;
                firstClassInd++;
                numOfPassengers++;
            }

            if(firstClassInd < 2){
                firstClass[firstClassInd] = boardedPass;
                firstClassInd++;
                numOfPassengers++;
            }

            // if the two seats are full, to which first class index will point to index 2, we downgrade passenger's seat
            else {
                ClassDownGrade(boardedPass);
            }
        }
        if(boardedPass.getPassPriority() == 3){

            if(businessClassInd == 0){
                businessClass[0] = boardedPass;
                businessClassInd++;
                numOfPassengers++;
            }
            if(businessClassInd < 3){
                businessClass[businessClassInd] = boardedPass;
                businessClassInd++;
                numOfPassengers++;
            }
            else {
                ClassDownGrade(boardedPass);
            }
        }
        if(boardedPass.getPassPriority() == 2){

            if(premiumClassInd == 0){
                premiumEconomy[0] = boardedPass;
                premiumClassInd++;
                numOfPassengers++;
            }
            if(premiumClassInd < 4){
                premiumEconomy[premiumClassInd] = boardedPass;
                premiumClassInd++;
                numOfPassengers++;
            }
            else {
                ClassDownGrade(boardedPass);
            }
        }
        if(boardedPass.getPassPriority() == 1){
            // first seat in economy
            if(economyInd == 0){
                economy[0] = boardedPass;
                economyInd++;
                numOfPassengers++;
            }
            if(economyInd < 6){
                economy[economyInd] = boardedPass;
                economyInd++;
                numOfPassengers++;
            }
            else{
                ClassDownGrade(boardedPass);
            }
        }
    }

    // this method assumes that we know the person need to get his class downgraded (i.e. when
    // seats in his/her class is full) and if it's all full, we send him back to the boarding queue
    public void ClassDownGrade(Passenger pass) throws NoRoomException {

        if(pass.getPassPriority() == 4){
            if(businessClassInd != 3){
                businessClass[businessClassInd] = pass;
                numOfPassengers++;
                return;
            }
            if(premiumClassInd != 4){
                premiumEconomy[premiumClassInd] = pass;
                numOfPassengers++;
                return;
            }
            if(economyInd != 6){
                economy[economyInd] = pass;
                numOfPassengers++;
                return;
            }
            else{
                boardQueue.enqueuePassenger(pass);
                System.out.println("Passenger has been dequeued due to lack of space");
            }
        }
        if(pass.getPassPriority() == 3){
            if(premiumClassInd != 4){
                premiumEconomy[premiumClassInd] = pass;
                numOfPassengers++;
                return;
            }
            if(economyInd != 6){
                economy[economyInd] = pass;
                numOfPassengers++;
                return;
            }
            else{
                boardQueue.enqueuePassenger(pass);
                System.out.println("Passenger has been dequeued due to lack of space");
            }
        }
        if(pass.getPassPriority() == 2){
            if(economyInd != 6){
                economy[economyInd] = pass;
                numOfPassengers++;
                return;
            }
            else{
                boardQueue.enqueuePassenger(pass);
                System.out.println("Passenger has been dequeued due to lack of space");
            }
        }
        if(pass.getPassPriority() == 1){
            boardQueue.enqueuePassenger(pass);
            System.out.println("Passenger has been dequeued due to lack of space");
        }
    }
}

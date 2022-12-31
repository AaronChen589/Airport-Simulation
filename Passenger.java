public class Passenger {

    private int passengerID;
    private int arrivalTime;
    private int passPriority;

    enum TravelClass{
        firstClass, businessClass, premiumEconomy, Economy
    }
    public Passenger(){
        passengerID = -1;
        arrivalTime = -1;
    }
    public Passenger( int arrivalTime, TravelClass travelClass){
        this.arrivalTime = arrivalTime;
        switch(travelClass){
            case firstClass:
                passPriority = 4;
                break;
            case businessClass:
                passPriority = 3;
                break;
            case premiumEconomy:
                passPriority = 2;
                break;
            case Economy:
                passPriority = 1;
                break;
        }
    }

    public void setPassengerID( int passengerID){
        this.passengerID = passengerID;
    }
    public void setArrivalTime( int arrivalTime){
        this.arrivalTime = arrivalTime;
    }
    public int getPassengerID(){
        return this.passengerID;
    }
    public int getArrivalTime(){
        return this.arrivalTime;
    }
    public int getPassPriority(){
       return  this.passPriority;
    }
}

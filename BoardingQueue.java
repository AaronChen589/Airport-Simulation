public class BoardingQueue {

    private Passenger [] PassengerQueue = new Passenger[10];
    private int front;
    private int rear;
    private int size;
    private int arrivalIndex;

    // constructors
    public BoardingQueue(){
        this.front = -1;
        this.rear = -1;
        this.size = 0;
        this.arrivalIndex = 0;
    }
    public BoardingQueue(int front, int rear){
        this.front = front;
        this.rear = rear;
        this.size = 0;
        this.arrivalIndex = 0;
    }

    // setters and getters
    public int getFront() {
        return this.front;
    }
    public int getRear(){
        return this.rear;
    }
    public int getSize(){
        return this.size;
    }
    public Passenger[] getPassengerQueue(){
        return this.PassengerQueue;
    }
    public void setFront(int front){
        this.front = front;
    }
    public void setRear(int rear){
        this.rear = rear;
    }

    public void enqueuePassenger(Passenger newPass) throws NoRoomException {

        // checks if the queue is full and throws exception if it is
        if(size >= 10){
            throw new NoRoomException("Sorry, there is no more room to enqueue");
        }
        // if the queue is empty, we set the front and rear to the 0 index and add the person to the 0th index
        else if(size == 0){
            front = 0;
            rear = 0;
            PassengerQueue[front] = newPass;
            newPass.setPassengerID(arrivalIndex);
            arrivalIndex++;
            size++;
        }
        // otherwise, we add person to the rear index and update the size to show it
        else if(size > 0){
            rear++;
            PassengerQueue[rear] = newPass;
            newPass.setPassengerID(arrivalIndex);
            arrivalIndex++;
            size++;
            SortQueueByPriority();
        }
    }

    public Passenger dequeuePassenger() throws NoPassengerException {

        Passenger dequeuedPassenger = null;

        if(size == 0){
            throw new NoPassengerException("Sorry, there is no passengers to dequeue");
        }

        // we remove the front person by creating array and then copying the current queue without the the front index
        else if(size > 0){
            Passenger [] shiftedQueue = new Passenger[10];
            dequeuedPassenger = PassengerQueue[front];

            System.arraycopy(PassengerQueue,1,shiftedQueue,0,size - 1);
            PassengerQueue = shiftedQueue;
            rear--;
            size--;
        }
        return dequeuedPassenger;
    }


    // this method sorts the Queue so that it accounts for priority based on Class and the earliest arrival times among those within the same Class
    public void SortQueueByPriority(){
        Passenger temp;
        for(int i = 0; i < size - 1; i++){
            for(int j = 1; j < size - i - 1; j++){
                if(PassengerQueue[j].getPassPriority() > PassengerQueue[j+1].getPassPriority() ){
                    temp = PassengerQueue[j];
                    PassengerQueue[j] = PassengerQueue[j+1];
                    PassengerQueue[j+1] = temp;
                }
                if(PassengerQueue[j].getPassPriority() == PassengerQueue[j+1].getPassPriority()){
                    if(PassengerQueue[j].getArrivalTime() < PassengerQueue[j+1].getArrivalTime()){
                        temp = PassengerQueue[j+1];
                        PassengerQueue[j+1] = PassengerQueue[j];
                        PassengerQueue[j] = temp;
                    }
                }
            }
        }
    }
}

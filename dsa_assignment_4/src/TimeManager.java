public class TimeManager implements Runnable {
    private long startTime;
    private long count;

    /*
    This class prints the time since a give time
    but does so on one line. Because it must be done
    in parallel to when algorithms are running, it
    is run on its own thread
    */

    public TimeManager(long startTime) {
        this.startTime = startTime;
        this.count = 0;
    }

    public void run() {

        // While the thread hasn't been set to end
        while (!Thread.currentThread().isInterrupted()){

            if (count > 2) {
                // This print statement removes the line before it and prints the new/extended time
                System.out.print("\r>   Sorting Time: " + (System.currentTimeMillis()-this.startTime) + " ms");
            }

            count ++;
        }

    }
}
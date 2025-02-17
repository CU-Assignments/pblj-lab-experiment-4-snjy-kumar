Experiment 4.3: Ticket Booking System

This program simulates a ticket booking system where multiple users (threads) try to book seats at the same time. The key challenges addressed are:

1) Avoiding Double Booking → Using synchronized methods to ensure no two users book the same seat.
2) Prioritizing VIP Customers → Using thread priorities so VIP users' bookings are processed before regular users.

📌 Core Concepts Used
️1 Synchronized Booking Method
The method bookSeat() is marked as synchronized, ensuring that only one thread can access it at a time.
This prevents race conditions, where two threads might try to book the same seat simultaneously.
  
️2 Thread Priorities for VIP Customers
Threads representing VIP users are assigned Thread.MAX_PRIORITY so they execute first.
Regular users have Thread.NORM_PRIORITY or Thread.MIN_PRIORITY, making them process later.

3 Handling Multiple Users
Each user trying to book a seat is represented by a thread.
Users can select a seat, and if it’s already booked, they receive an error message.


Step-by-Step Execution
1 Initialize the TicketBookingSystem → Allows booking of N seats.
2 Create Multiple Booking Threads → Each user (VIP or Regular) is assigned a thread.
3 Start All Threads → Threads compete for booking, with VIPs processed first.
4 Ensure No Double Booking → synchronized method prevents duplicate seat allocation.
5 Threads Finish Execution & Display Booking Status.


🔹 Why Use Synchronization?
Without synchronized, two threads might book the same seat simultaneously, causing double booking issues. Using synchronized, only one thread at a time can modify the seat booking data.

🔹 Why Use Thread Priorities?
Setting higher priority for VIP users ensures their bookings are processed first, simulating real-world priority-based bookings.
=======================================================================================================================================================================================================
class TicketBookingSystem {
    private static final int TOTAL_SEATS = 5;
    private static boolean[] seats = new boolean[TOTAL_SEATS];

    public synchronized void bookSeat(String user, int seatNumber) {
        if (seatNumber < 1 || seatNumber > TOTAL_SEATS) {
            System.out.println("Invalid seat number!");
            return;
        }

        if (seats[seatNumber - 1]) {
            System.out.println(user + ": Seat " + seatNumber + " is already booked!");
        } else {
            seats[seatNumber - 1] = true;
            System.out.println(user + " booked seat " + seatNumber);
        }
    }

    public void displaySeats() {
        System.out.println("\nBooking Status:");
        for (int i = 0; i < TOTAL_SEATS; i++) {
            if (seats[i]) {
                System.out.println("Seat " + (i + 1) + " is booked.");
            } else {
                System.out.println("Seat " + (i + 1) + " is available.");
            }
        }
    }
}

class BookingThread extends Thread {
    private String user;
    private int seatNumber;
    private TicketBookingSystem system;

    public BookingThread(String user, int seatNumber, TicketBookingSystem system) {
        this.user = user;
        this.seatNumber = seatNumber;
        this.system = system;
    }

    @Override
    public void run() {
        system.bookSeat(user, seatNumber);
    }
}

public class TicketBookingApp {
    public static void main(String[] args) {
        TicketBookingSystem system = new TicketBookingSystem();

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> System.out.println("Uncaught exception in thread " + t.getName() + ": " + e));

        system.displaySeats();

        BookingThread anish = new BookingThread("Anish (VIP)", 1, system);
        anish.setPriority(Thread.MAX_PRIORITY); // VIP user with high priority
        BookingThread bobby = new BookingThread("Bobby (Regular)", 2, system);
        bobby.setPriority(Thread.NORM_PRIORITY); // Regular user with normal priority
        BookingThread charlie = new BookingThread("Charlie (VIP)", 3, system);
        charlie.setPriority(Thread.MAX_PRIORITY); // VIP user with high priority
        BookingThread bobbyLow = new BookingThread("Bobby (Regular)", 4, system);
        bobbyLow.setPriority(Thread.MIN_PRIORITY); // Low priority for regular user

        anish.start();
        bobby.start();
        charlie.start();
        bobbyLow.start();

        try {
            anish.join();
            bobby.join();
            charlie.join();
            bobbyLow.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        system.displaySeats();

        BookingThread bobby2 = new BookingThread("Bobby (Regular)", 1, system);
        bobby2.start();
        
        BookingThread invalidUser = new BookingThread("Invalid User", 6, system);
        invalidUser.start();

        BookingThread newUser = new BookingThread("New User", 3, system);
        newUser.start();
        
        system.displaySeats();
    }
}

  
=======================================================================================================================================================================================================
Test Cases

Test Case 1: No Seats Available Initially
Input:
System starts with 5 seats.
No users attempt to book.
Expected Output:
No bookings yet.

Test Case 2: Successful Booking
Input:
Anish (VIP) books Seat 1.
Bobby (Regular) books Seat 2.
Charlie (VIP) books Seat 3.
Expected Output:
Anish (VIP) booked seat 1
Bobby (Regular) booked seat 2
Charlie (VIP) booked seat 3

Test Case 3: Thread Priorities (VIP First)
Input:
Bobby (Regular) books Seat 4 (low priority).
Anish (VIP) books Seat 4 (high priority).
Expected Output:
Anish (VIP) booked seat 4
Bobby (Regular): Seat 4 is already booked!

Test Case 4: Preventing Double Booking
Input:
Anish (VIP) books Seat 1.
Bobby (Regular) tries to book Seat 1 again.
Expected Output:
Anish (VIP) booked seat 1
Bobby (Regular): Seat 1 is already booked!

Test Case 5: Booking After All Seats Are Taken
Input:
All 5 seats are booked.
A new user (Regular) tries to book Seat 3.
Expected Output:
Error: Seat 3 is already booked!

Test Case 6: Invalid Seat Selection
Input:
User tries to book Seat 0 (out of range).
User tries to book Seat 6 (beyond available seats).
Expected Output:
Invalid seat number!

Test Case 7: Simultaneous Bookings (Concurrency Test)
Input:
10 users try booking at the same time for 5 seats.
Expected Output:
5 users successfully book seats.
5 users receive error messages for already booked seats.

import java.util.concurrent.*;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Presents {

	static int NUM_PRESENT = 500000;
	static int NUM_CARDS = 0;
	static ArrayList<Integer> giftBag = new ArrayList<Integer>();
	static ConcurrentLinkedList gitftChain = new ConcurrentLinkedList();
	static boolean print = false; 


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        
		if(args.length > 0 && args[0].equals("Verbose")) print = true;

		for(int i = 0; i < 500000; i++){
			giftBag.add(i);
		}

		Collections.shuffle(giftBag);

        ExecutorService executor = Executors.newFixedThreadPool(4);

        try{

            Future<?>[] futures = new Future[4];

            for (int y = 0; y < 4; y++) {

                futures[y] = executor.submit(() -> {

					while(!giftBag.isEmpty() || gitftChain.head != null){
						int randomNumber = new Random().nextInt(3) + 1;

						switch(randomNumber){
							case 1: 
								getGiftRandom();
								break;
							case 2: 
								writeThankYou();
								break;
							case 3: 
								int x = new Random().nextInt(500000);
								checkForGift(x);
								break;
						}
					}
                    
                });
            }

            for (Future<?> future : futures)
            {
                future.get();
            }

        }catch(Exception e){
            e.printStackTrace(System.out);
        }
        finally
        {
            executor.shutdown();
        }

		System.out.println("Number of cards sent: " + NUM_CARDS + " Number of presents still in bag: " + NUM_PRESENT);
        
    }

	public static synchronized void  getGiftRandom(){

		if(giftBag.size() == 0) return;

		int x = giftBag.get(giftBag.size()-1);
		giftBag.remove(giftBag.size()-1);

		gitftChain.add(x);

		NUM_PRESENT--;

		if(print)
			System.out.println("Thread " + Thread.currentThread().getName() + " added gift " + x + " to the gift chain");
	}

	public static synchronized void writeThankYou(){
		if(gitftChain.size == 0) return;

		int x = gitftChain.remove();

		if(x == -1) return;
		else{
			NUM_CARDS++;
			if(print)
				System.out.println("Thread " + Thread.currentThread().getName() + " wrote thank you to person " + x + " for gift");

		}

	}

	public static synchronized boolean checkForGift(int x){
		boolean result = gitftChain.search(x);

		if(result && print)
			System.out.println("Thread: " + Thread.currentThread().getName() + " found gift " + x + " in chain");
		else if(!result && print)
			System.out.println("Thread: " + Thread.currentThread().getName() + " didn't find gift " + x + " in chain");

			return result;
	}
}


class Node {
	int data;
	Node next;

	public Node(int data) {
		this.data = data;
		this.next = null;
	}
}


class ConcurrentLinkedList {
    public Node head;
    private final Lock lock;
	public int size = 0;

    public ConcurrentLinkedList() {
        head = null;
        lock = new ReentrantLock();
    }

    public void add(int element) {
        lock.lock();
		if (element < 0) return;
        try {
            Node newNode = new Node(element);
            if (head == null || head.data >= element) {
                newNode.next = head;
                head = newNode;
            } else {
                Node current = head;
                while (current.next != null && current.next.data < element) {
                    current = current.next;
                }
                newNode.next = current.next;
                current.next = newNode;
            }
			size++;
        } finally {
            lock.unlock();
        }
    }

    public int remove() {
        lock.lock();
        try {
            if (head == null) {
                return -1;
            }
            else {
				int x = head.data;
                head = head.next;
				size--;
                return x;
            }
           
        } finally {
            lock.unlock();
        }
    }

    public boolean search(int element) {
        lock.lock();
        try {
            Node current = head;
            while (current != null) {
                if (current.data == element) {
                    return true;
                }
                current = current.next;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    public boolean isEmpty() {
        lock.lock();
        try {
            return head == null;
        } finally {
            lock.unlock();
        }
    }

    public void printList() {
        lock.lock();
        try {
            Node current = head;
            while (current != null) {
                System.out.print(current.data + " ");
                current = current.next;
            }
            System.out.println();
        } finally {
            lock.unlock();
        }
    }
}
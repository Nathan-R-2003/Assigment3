import java.util.concurrent.*;
import java.util.*;

public class Temperature {
	static int[] readings = new int[480];

	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(8);

		try{
			for(int hour = 1; hour <= 24; hour++){
				Future<?>[] futures = new Future[8];

				for(int i = 0; i < 8; i++){
					int I = i;
					futures[i] = executor.submit(() -> {
						for(int x = I*60; x < I*60+60; x++){
							readings[x] = new Random().nextInt(171) - 100;
						}
					});
				}

				for (Future<?> future : futures)
        		{
                	future.get();
            	}

				System.out.println("Report for hour: " + hour);

				int difference = 0;
				int low = 0;
				int high = 9;

				for(int x = 0; x < 8; x++){
					for(int y = x*60; y<x*60+51;y++){
						if(Math.abs(readings[y+9]-readings[y]) >= Math.abs(difference)){
							difference = readings[y+9]-readings[y];
							low = y%60;
							high = (y+9)%60;
						}
					}
				}

				System.out.println("Biggest temp difference of " + difference + "F detected at interval " + low + "-" + high);
				Arrays.sort(readings);
				System.out.println("Smallest five readings were: " + readings[0] + ", " + readings[1] + ", "+ readings[2] + ", "+ readings[3] + ", "+ readings[4]);
				System.out.println("Highest five readings were: " + readings[475] + ", " + readings[476] + ", "+ readings[477] + ", "+ readings[478] + ", "+ readings[479]);
				System.out.println("-------------------------------");
			}

		}catch(Exception e){
			e.printStackTrace(System.out);
		}finally{
			executor.shutdown();
		}
		
	}
}

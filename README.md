Part 1
For this part, I had 4 threads each picking a random task until all 500,000 gifts were processed and the thank you cards were written. To do so, I had an arraylist of 500,000 unsorted integers to represent the unsorted bag of gifts. If a thread picked task 1, they would remove the integer representing the gift from the araylist and add it to the linked list. If a thread picked task two, they remove the gift at the head of the linked list and write a thank you card. For task three, the thread just looks for a specific integer in the linked list. These three task were carried out by synchronized functions to avoid race conditions, and the linked list used a lock for all methods that involved manipulating the list. This solution works because I keep a counter of how many gifts remain in the gift bag and how many cards have been sent and at the end of every test run the cards sent are 500,000 and the gifts remaining are always 0. It is also efficient because it reduced the runtime compared to when I used only one thread for testing. 
To run the code for this part, type javac Presents.java to compile and then java Presents to run. If you want to see the tasks that each thread is performing in the output, type java Presents Verbose when running the code. I wrote the code this way to reduce runtime since printing drastically increases runtime and I didn't want to see the entire process every time.

Part 2
For this part, I had 8 threads picking random values between -100 to 70 for the readings. I had an array of length 480 to store the readings. This array is meant to hold the readings of all threads for one hour, since the threads are taking a reading per minute so 8x60 readings is 480. Once the readings for one hour are done, I write the report by going through the array and finding the largest temp difference for an interval of 10. Then I sort the array and display the largest and smallest 5 readings for that hour. Then I repeat the process 23 more times to simulate 24 hours of readings. The program is correct because It avoids race conditions since the threads are all writing to a specific portion of the array. For example, thread 1 writes to indices 0-59 and thread to to indices 60-119. The code was tested multiple times and each time all readings were found to be within the given temp range and the temp difference is also found to be correct. 
To run the code for this part, type javac Temperature.java to compile and the java Temperature to run. The reports for each hour will be displayed as output.

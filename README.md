# Static Scheduling Algorithm 

1. RMS (Rate Monotonic Scheduling): https://en.wikipedia.org/wiki/Rate-monotonic_scheduling

My implementation uses two lists for task scheduling. One is the list with ready processes to be scheduled, and the other list contains waiting processes, that already have been scheduled.
Processes are sorted in the list in the ascending order with respect to their deadlines (priority).


| Task  	| Capacity 	| Period	|	
| ------------- | ------------- | ------------- |
| 1	  	| 1  		| 4		|	
| 2  		| 2  		| 6		|
| 3		| 3		| 12		|
| 4		| 4		| 24		|

**Result:**
- Scheduling Task 1 at time: 0
- Scheduling Task 2 at time: 1
- Scheduling Task 2 at time: 2
- Scheduling Task 3 at time: 3
- Scheduling Task 1 at time: 4
- Scheduling Task 3 at time: 5
- Scheduling Task 2 at time: 6
- Scheduling Task 2 at time: 7
- Scheduling Task 1 at time: 8
- Scheduling Task 3 at time: 9
- Scheduling Task 4 at time: 10
- Scheduling Task 4 at time: 11
- Scheduling Task 1 at time: 12
- Scheduling Task 2 at time: 13
- Scheduling Task 2 at time: 14
- Scheduling Task 3 at time: 15
- Scheduling Task 1 at time: 16
- Scheduling Task 3 at time: 17
- Scheduling Task 2 at time: 18
- Scheduling Task 2 at time: 19
- Scheduling Task 1 at time: 20
- Scheduling Task 3 at time: 21
- Scheduling Task 4 at time: 22
- Scheduling Task 4 at time: 23

# FinAccelCodeChallange
It contains 3 assignments.

Expectations:-
Use any of these programming languages - Python, GoLang, Java or C/C++
Code should be neat with necessary abstractions, error handling and comments
Write unit test cases to validate the correctness of code

Technical Test Problem Statement #1

Implement an in-memory caching library to store (key, value) objects for faster retrieval. Key requirements of the library are as follows:

The Cache will have fixed capacity specified at initialization time to limit memory usage
Storage and retrieval from Cache should be fast
Cache should support efficient replacement based on configurable eviction policy (eg. LRU - Least Recently Used) when trying to insert in a full cache
Optional Bonus: Support expiry of cache entries based on TTL (time to live in seconds) value specified at time of cache insert.


Technical Test Problem Statement #2

Assume that there is a lake with land. Assume number 0 represents water while positive numbers represent the land. The number represent the height of the land - so number 2 means the land is higher than number 1.

You mission, should you accept it, is to create a *square house* which has the largest area on that lake within the same land height.

Input : Two-dimensional array which represent the lake area.
Output: the biggest number represent the area of the house

Example1:
Input:
0    0    3    3    0 = 6
0    1    3    3    0 = 7
1    3    3    3    0 = 10
0    0    2    1    0 = 3
=1    =4    =11    =10    
should have output : 4

Example2:
Input:
0    3    3    3    0=9
2    3    3    3    3=14
1    3    3    3    2=10
0    0    2    1    0=3
=3    =9    =11    =10    =5
should have output : 9

Technical Test Problem Statement #3

You open a computer, and there are N applications running. Each application has process_id.

Now, let's put those process id to an array where position 0 means currently active application (you are opening that application and click) and last index is the oldest application from last active.

example of the array:
firefox(id=5), chrome(id=1), terminal(id=10), whatsapp(id=3), sublime(id=12) will be represented by [5,1,10,3,12]

if we do ALT+TAB K times before we release it, then the application will rotate according to K.

what it means:
If K=3, then the array [5,1,10,3,12] will now become [3,5,1,10,12]
and then if K = 2, the array will look like [1,3,5,10,12]

Your mission, should you accept it, please find out the last array after we do ALT-TAB

Input:
line 1: array that has process_ids
line 2: sequence of K times

Output:
The final array after all sequence of ALT+TAB done

Example 1:
Input:
[1,2,3,4,5,6]
3 4 2

Output: [1,5,4,2,3,6]

Example 2:
Input:
[1,2,3,4,5,6]
5 1

Output: [1,6,2,3,4,5]

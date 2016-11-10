##Table of Contents

1. [Terminology] (README.md#terminology)
2. [Features Implemented (Required + Additional)] (README.md#features-implemented)
3. [Data Model] (README.md#data-model)
4. [Control Flow] (README.md#control-flow)
5. [HLD] (README.md#hld)
6. [Business Logic] (README.md#business-logic)
7. [LLD] ((README.md#lld))
8. [Implementation Details (Has important note)] (README.md#implementation-details)

###Terminology

1. Friend: A user with whom transaction has been performed previously. Even though the transaction is directional, the relationship is unidirectional.
2. Friend of a Friend: A recursion wherein the user with whom transaction is being performed is the friend of one of your friends, but not your friend.
3. Friend of a Friend of a Friend …. [FoF relationship]
4. Degree: The depth of FoF relationship.


###Features Implemented

####Required Features:

1. Feature #1: Notification on transaction but not when the users involved are friends.
2. Feature #2: Notification on transaction but not when the users involved are within 2nd degree of FoF relationship.
3. Feature #3: Notification on transaction but not when the users involved are within 4th degree of FoF relationship.

Features 1, 2 & 3 will be implemented using the same extensible approach that takes the degree as an input parameter.


####Additional Features:

1. Feature #4: Notification for a user to the system admins wherein the user is on the receiving end of transactions from a large no. of users within a given time period.
2. Feature #5: Notification on a transaction having unusual transaction amount, very different than the ones done previously.


###Data Model

The data will be modelled as a graph where:
1. Nodes: Represent users.
2. Edges: Represent transactions.


###Control Flow

####System Initialization
1. The DB is populated using the historical data stored in either a file or a disk based queue. This is being mocked as a system file for now. In production, this could be:
2. All the historical data loaded from a distributed file system like HDFS.
3. Updates since last bulk load from a disk based queue like kafka.
4. The data is pulled from the disk and interpreted as POJOs. The data could be stored in any form: JSON, CSV etc. (CSV for this case)
5. The POJOs are then processed by application logic to update/initialize the database which stores user graph. This can be a graph database, NoSQL database(not optimal), or a SQL database(not optimal at all due to joins). This is being mocked in memory for now.
6. The requests may be handled concurrently with thread safe updates to nodes. This is not being done given the requirements of the current system in FAQs.


####System Operation
1. Request is made to the system via an exposed API.
2. Request Objects are converted into POJOs. (From CSV in this case).
3. Application business logic processes the request.
4. Reads the current graph (or user subgraph)
5. Applies logic for inference [FoF relationship in this case]
6. Updates the current graph
7. Returns the result to the caller.
8. The requests may be handled concurrently with thread safe updates to nodes. This is not being done given the requirements of the current system in FAQs. Note: special care must be taken to process the requests in timestamp order, not that this can’t be ignored reasonably in a large enough production system.


##HLD

<img src="./images/hld.png" width="800">


##Business Logic:

1. FoF Relationships: Apply BFS starting from user node of any user involved in the transaction upto a maximum depth of the given degree and return the set of friends(or friends of friends...). If the other user is there in the set returned, then transaction is “verified”, else not. An optimization for this can be done as follows:
Maintain an enriched graph of the current user graph which has connections between users up to a given max friendship degree calculated using BFS. This is done only during batch runs (which may happen daily as new transactions are appended to the historical batch data). This runs in O(1) as against O(n) for many cases and acts as a cache(can be done for some specific users). If an edge is found with given distance, then return, otherwise run BFS on the original graph. Note that updates from incoming streaming transactions are happening to the original graph only. This optimization is disabled for my implementation as it could not be loaded into memory but it might work in a distributed environment.
2. Unusually transaction amount: Maintain last K transactions for the sender and calculate mean and standard deviation of the transaction amounts made. Notify the user if there is a significant deviation using z-score method, i.e. more than 3 standard deviations away from the mean. 
3. Maintain last K transactions(receiving end) per user. If the user reaches M transactions and the delta of timestamps is less than a particular value T, flag this user.


##Low Level Design:
###Model:
		1. Transaction POJO
		2. Transaction Status Enum
###Utils:
		1. Graphs - Could’ve been done using JGraphT
		2. Statistics - Could’ve been done using Apache Commons Math
###Data:
		1. User-Transaction Graph
		2. Enriched User-Transaction Graph
		3. Transaction File Reader Queue : Read CSV records from file
		4. User Historic Transaction Database : In memory DB of last K transactions
###Core:
		1. Transaction Parser: Interprets CSV into Transaction POJO
		2. Transaction Validations: various validations for transactions
###Main Program:
		AntiFraud.java: init() + run() + close()


##Implementation Details:
1. Project has been done in Java using Maven dependency management. External libraries used are:
        Guava - for immutable collections
        Apache Lang - for Pair
        Lombok - for boilerplate code [might require plugin installation in editor, available for both IntelliJ and Eclipse]
        JUnit - testing

2. Git ignores output and input files, Intellij files, MacOS files, mvn target and dependency jars etc.
###NOTE: The run_tests.sh script does not copy over pom.xml due to which the test fails without manual intervention. Regardless of that, run.sh works.
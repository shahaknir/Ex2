Background:
This next file will explain the purpose of this assignment.
This assignment is a Pokémon game that is implements by a graph and agents chasing Pokémon.
The assignment has 2 parts: 
1.	Implements the method and object that are needed to run the game.
2.	Implements our best solution of the computer winning as many points in each level
Issued by Ariel University, OOP course 2020.

First part of the assignment:
************************************************************
node_data implement by NodeData:
The interface node_data is designed to have all the qualities that are needed per one node in an directed weighted graph, if you’re wondering what they are:
int key – the node’s unique name.
int tag – the node’s temporal data which can be used when working with graphs (aka color: e, g, white, gray, black, etc.).
String info – the node’s meta data.
Double weight – the node’s weight.
Geo_location – the node’s location in 3D dimension.
Static final int keyGenerator = 0 – makes sure each node gets a unique id and ,make sure we avoid overriding data in the HashMap.
1.	NodeData(): this method is made to initialize the node once creating it.
2.	NodeData(node_data ND): this method is made to initialize a node once according to another node.
3.	NodeData(int id): this method is made to initialize the node with a specific id.
4.	getKey(): each node gets a unique key that will be her “name” – that is the most basic way to find a specific node in our collection, neighbors, and graph in general. This method allows us to know what the node “name” is.
5.	getLocation(): this method returns the node’s location in a 3D dimension.
6.	setLocation(geo_location p): this method sets the node’s location to the geometrical location that was given (=p).
7.	getWeight(): this method returns the node’s weight.
8.	setWeight(double t): this method allows setting the weight of the node.
9.	getInfo(): this method returns the node’s meta data that associated with her.
10.	setInfo(String s): this method changes the meta data associated with the node to s.
11.	getTag(): this method returns the node’s temporal data which can be used in different graphs.
12.	setTag(int t): this method allows setting the temporal marking of the node.
************************************************************
edge_data implement by EdgeData:
The interface edge_data is designed to have all the qualities that are needed per one edge in a directed weighted graph, if you’re wondering what they are:
int source – the source node id of the edge.
Int destination – the destination node id of the edge.
Double weight – the edge weight, effects the speed that one agent can run.
String info – the node’s meta data.
int tag – the edge temporal data which can be used when working with graphs.
1.	EdgeData(int source, int destination, double weight, String info, int tag ): this method is made to initialize the edge once creating it by the source, destination, weight, info and tag that are given.
2.	EdgeData(int source, int destination, double weight): this method is made to initialize the edge once creating it by the source, destination and weight that are given.
3.	EdgeData(): this method is made to initialize the edge once creating it by the default data.
4.	getSrc(): this method return the source node id of this edge.
5.	getDest(): this method return the destination node id of this edge.
6.	getWeight(): this method return the weight of this edge.
7.	getInfo(): this method return info of this edge.
8.	setInfo(String s): this method changes the meta data associated with the edge to s.
9.	getTag(): this method return tag of this edge.
10.	setTag(int t): this method allows setting the temporal marking of the edge.
Geo_location implement by GeoLocation:
The interface geo_location is designed to have all the qualities that are needed per a node in a directed weighted graph, if you’re wondering what they are:
Double x – the node’s value on the x axis.
Double y – the node’s value on the y axis.
Double z – the node’s value on the z axis.

1.	x(): this method return the x axis value.
2.	y(): this method return the y axis value.
3.	z(): this method return the z axis value.
4.	Distance(geo_location g): this method return the distance between the given node and g.
5.	setX(double x): this method changes the x axis value.
6.	setY(double y): this method changes the x axis value.
7.	setZ(double Z): this method changes the x axis value.
***********************************************************************

directed_weighted_graph implement by DWGraph_DS:
The interface directed_weighted_graph is designed to help the user control his graph by these methods, if you’re wondering what they are:

Int edgeCount – counts the number of edges in our graph.
Int mc – counts any change in the inner state of the graph such as removing or adding an edge, connecting two nodes, adding a node to the graph etc.
HashMap nodes– a list containing all our graph’s nodes.
HashMap edges – maps from each node that represent a source to one edge at least, to a map of node to edge.
HashMap reverseEdges– maps from each node that represent a destination to one edge at least, to a map of node to edge.
1.	DWGraph_DS(): this method initiates all the parameters above, there for initiating a directed weighted graph.
2.	getNode(int key): this method searching our graph for a node by the “name” key. By using the data structure HashMap we guaranty that searching for this node will take O(1).
3.	getEdge(int node1, int node2): this method checks whether node1 and node2 are connected or not. If the nodes are connected – they are neighbors and has an edge between them, this method will return the weight of the edge. By using the data structure HashMap we guaranty that searching this node will take O(1).
4.	addNode(node_data): this method adds a new node to our graph. By using the data structure HashMap we guaranty that adding this node will take O(1).
5.	Connect(int node1, int node2, double w): this method connects two nodes together. The nodes “names” are node1 and node2. The method will create a new edge by the parameters above and add it to our hashMaps. By using HashMap at the Neighbors list in NodeData we guaranty that adding these nodes will take O(1).
6.	getV(): this method returns a shallow copy of nodes which represents all the graph nodes. By using the data structure HashMap we guaranty that a shallow copy of this collection will take O(1) (because the method will use a HashMap default method.
7.	getE(int node_id): this method returns a collection containing all of node_id neighbors. By using the data structure HashMap we guaranty that getting this collection will take O(k) while k being the node_id dgree.
8.	removeNode(int key): this method removes a node by the “name” of key from our graph completely. The method goes in the node we wish to remove and finds its neighbors, afterwards it disconnects the edges between the node and its neighbors and finally removing the node from the graphNodes HashMap. By using the data structure HashMap we guaranty that removing this node from all of its neighbors will take O(n) while |V| = n.
9.	removeEdge(int node1, int node2): this method deletes the edge connecting the two nodes by the “names” node1 and node2 and therefore disconnecting the nodes. By using HashMap at the Neighbors list in NodeData we guaranty that finding and deleting  these nodes will take O(1). 
10.	nodeSize(): this method returns the number of nodes and so returns: the size of the nodes HashMap. The fact that we are returning an int from the class is in fact O(1).
11.	 edgeSize(): this method returns the number of edges and so returns: the edgeCount defined in the beginning of the class. The fact that we are returning an int from the class is in fact O(1).
12.	getMC(): this method returns the number of changes that happened in the graph.
13.	setMC(int mc): this method sets the number of changes that happened in the graph to the given value.
14.	Equals(Object obj):  this method over ride the default method of JAVA and check if a directed weighted graph is equal to another.
***********************************************************

3. weighted_graph_algorithms implement by DWGraph_Algo:
The interface weighted_graph_algorithms is designed to help the user making more complicated actions such as initiating a  directed weighted graph, copying a directed weighted graph, making sure that all the graph nodes are connected, etc., if you’re wondering what they are:
Directed_weighted_graph graph – representing our graph.
HashMap parent – remembering how we got to this node.
HashMap distance – represent the distance (by weight of edge) to “travel” from a node that was used in Dijkstra algo.
1.	Init(directes_weighted_graph g): this method initiates the graph and set the algorithms operates on.
2.	getGraph(): this method returns the graph.
3.	Copy(): this method computes a deep copy of this graph.
4.	isConnected(): this method returns true only if there is a valid path from every node to any node in our directed graph, uses BFS algorithm.
5.	shortestPathDist(int src, int dest): this method runs from node src to all its neighbors and find if src and dest are connected and if so, it returns the value of the shortest path between them by weight  of edges of course. If src and dest are not connected – this method returns the value -1. This method uses Dijkstra algorithm.
6.	shortestPath(int src, int dest): this method returns the nodes in the shortest path between src and dest by weight  of edges. This method uses Dijkstra algorithm.
7.	Save(String file): this method saves this directed weighted graph to the given file name. this method will return true if and only if the file was successfully saved.
8.	Load(String file): this method will load a graph to DWGraph_Algo if the file was uploaded then graph will be changed to the graph in the file and if so, the method will return true. 
9.	Dijkstra (node_data src): this method implement the Dijkstra algorithm.
10.	BFS(directed_weighted_graph g, node_data src): this method implements the Brath First Search.

Second part
Ex2 represent the server playing our wining algorithm.
This class goal is to will the biggest grade in each level of the game, these methods are meant to help wining, if you’re wondering what they are:

MyFrame _win – will be our game window.
Arena _ar – the arena of the level provided by the server.
Dw_graph_algorithm algo – the algo of the level game that we can use our methods of the first part.
Directed_weighted_graph gg – the level graph.
HashMap agent_movement – maps from each agent and his path to the Pokémon chosen for him to chase.
Long id – the user ID.
Int level – the level that was chosen to be played.
1.	Main: this static method makes sure that the user can open the game from the terminal by entering an id and level.
2.	LoginAndLevelSelction(): getting the user ID using a window and then getting his choice of level.
3.	Run(): represent the game rune. Calls the “init” method to set the agents on the graph.
4.	moveAgents(): moves the agent according to the “best chosen path” towards the best Pokémon target from the agent current location.
5.	chooseBestPAth(CL_Agent agent, List<CL_Pokemon> pokemons): this method find the best path and pokemon to set the agent towards by using Djikstra.
6.	Load_graph(String g):Converts a JSON to directed weighted graph.
7.	Init(game_srvice): this method adds the agent at the first initiating the game.






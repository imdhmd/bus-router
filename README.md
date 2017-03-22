### About

This is a solution for the problem specified at: https://github.com/goeuro/challenges/tree/master/bus_route_challenge

### Assumptions

This solution assumes that 'direct bus route' exists between station A and B, if the data file has a route specified with both A and B in it, regardless of their positional order.

Ex:

3  
0 0 1 2 3 4  
1 3 1 6 5  
2 0 6 4  

If `dep_sid == 5` and `arr_sid == 6`, `direct_bus_route` will be `true`.


### External libraries used

- Sparkjava: This is a light weight restful API framework. http://sparkjava.com/
- Gson: A json parser and formatter
- Jersey http client: This client has been used only on AppTest to invoke the rest endpoint

### Design decisions

- Data file (loaded at application start-up) is mapped to following map data model

	{depStation, arrStation} -> [routeId1, routeId2 ..]

  Having such a map minimizes effort needed to ask the question 'Does a direct bus route exist between depStation and arrStation?'


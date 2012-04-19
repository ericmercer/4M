#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <string.h>
#include <time.h>
#include "gem.h"
#include "mcapi.h"

#define SIZE 10
#define PORTNUM 1000
#define NDIST 1.0
#define COLINDEX(a,b) (a==b)


typedef struct{
	int src;
	double dest[SIZE];
}DistVect;

typedef struct{
	int cancomm;
	mcapi_endpoint_t endp;
}NodeComm;


double networkmap[SIZE][SIZE];


int getNextNode(int i){
	if(i + 1 >= SIZE){
		return 0;
	}
	return i + 1;
	
}

int getPrevNode(int i){
	if(i - 1 < 0){
		return SIZE - 1;
	}
	return i - 1;
	
}

void nodeBody(void * arg){
	int nodenum = (int)arg;
	int nextNode = getNextNode(nodenum);
	int prevNode = getPrevNode(nodenum);
	//
	double distTable[SIZE][SIZE];
	DistVect out;
	double distVector[SIZE];
	int i;
	int j;
	//set up our distance table (destination = rows, via = columns, cost is value)
	for(i = 0; i < SIZE; i++){
		for(j = 0; j < SIZE; j++){

			distTable[i][j] = INFINITY;
		}
		out.dest[i] = INFINITY;	
	}
	//fill in our distance vector to other nodes and also set up communication endpoints
	
	out.src = nodenum;
	int updated = 1;
	size_t size;
	DistVect in;
	NodeComm neighbors[SIZE];
	//this is how you set up a thread
	mcapi_status_t status;
	mcapi_version_t version;
	mcapi_endpoint_t thisNodeEP;
	mcapi_initialize(nodenum, &version, &status);
	thisNodeEP = mcapi_create_endpoint(PORTNUM, &status);
	//set up nodecomm objects and endpoint with filling in distance vector
	for(i = 0; i < SIZE; i++){
		if(i != nodenum && networkmap[nodenum][i] != INFINITY){
			neighbors[i].cancomm = 1;
			neighbors[i].endp = mcapi_get_endpoint(i, PORTNUM, &status);
			out.dest[i] = networkmap[nodenum][i];
		}else{
			if(i != nodenum){
				out.dest[i] = INFINITY;
			}else{
				out.dest[i] = 0;
			}
			neighbors[i].cancomm = 0;
		}

	}

	/*out.dest[nodenum] = 0;
	out.dest[nextNode] = 1;
	out.dest[prevNode] = 1;
	//int count  = 0;

	mcapi_endpoint_t nextNodeEP;
	mcapi_endpoint_t prevNodeEP;
	
	
	
	prevNodeEP = mcapi_get_endpoint(prevNode, PORTNUM, &status);
	nextNodeEP = mcapi_get_endpoint(nextNode, PORTNUM, &status);*/

	//if this gets to SIZE * SIZE
	int rcvcount  = 0;
	//this object is used to report the status of a non-blocking receive

	mcapi_request_t req;

	while(rcvcount < SIZE * SIZE){
		
		if(updated){
			for(i = 0; i < SIZE; i++){
				if(neighbors[i].cancomm){
					mcapi_msg_send(thisNodeEP, neighbors[i].endp, &out, sizeof(out), 0, &status);
				}
			}
			
			
			updated = 0;

			printf("Node %d updated\n", nodenum);
		}
		//receive
		mcapi_msg_recv_i(thisNodeEP, &in, sizeof(in), &req, &status);
		if(mcapi_wait(&req, &size, &status, 1)){
			//if we receive a message, modify our distance vector
			//memcpy(&distTable[COLINDEX(in.src, nextNode)], &in.dest, sizeof(in.dest));
			double val;
			for(i = 0; i < SIZE; i++){
				val = in.dest[i] + networkmap[nodenum][i];
				distTable[i][in.src] = val;
				//update the distance vector
				if(i != nodenum  && val < out.dest[i]){
					out.dest[i] = val;
					updated = 1;
					rcvcount = 0;
				}
			}

		}
		
		//col = COLINDEX(in.src, nextNode);

		

		rcvcount++;
	}
	
	
}


int main(int argc, char * argv){
	int i;
	int j;

	//propagate network TODO
	srand ( time(NULL) );
	for(i = 0; i < SIZE; i++){
		for(j = i; j < SIZE; j++){
			if(j == i){
				networkmap[i][j] = 0;
			}else{
				networkmap[i][j] = rand() % 10 + 1;
				networkmap[j][i] = networkmap[i][j];
				if(rand() % 4 == 0){
					networkmap[i][j] = INFINITY;
					networkmap[j][i] = INFINITY;
				}
			}
		}

	}
	
	
	for(i = 0; i < SIZE; i++){
		//create the threads
		create_gem_thread(nodeBody, (void *)i);
	}
	wait_gem_threads();
	
}

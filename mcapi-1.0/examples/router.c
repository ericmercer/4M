#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <string.h>

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
    int count = 0;
	int nodenum = (int)arg;
	int nextNode = getNextNode(nodenum);
	int prevNode = getPrevNode(nodenum);
	//
	double distTable[2][SIZE];
	DistVect out;
	double distVector[SIZE];
	int i;
	for(i = 0; i < SIZE; i++){
		distTable[0][i] = INFINITY;
		distTable[1][i] = INFINITY;
		out.dest[i] = INFINITY;	
	}
	//distTable[1][nodenum] = 0;
	out.src = nodenum;
	out.dest[nodenum] = 0;
	out.dest[nextNode] = 1;
	out.dest[prevNode] = 1;
	//int count  = 0;
	mcapi_status_t status;
	mcapi_version_t version;
	mcapi_endpoint_t nextNodeEP;
	mcapi_endpoint_t prevNodeEP;
	mcapi_endpoint_t thisNodeEP;
	mcapi_initialize(nodenum, &version, &status);
	thisNodeEP = mcapi_create_endpoint(PORTNUM, &status);
	prevNodeEP = mcapi_get_endpoint(prevNode, PORTNUM, &status);
	nextNodeEP = mcapi_get_endpoint(nextNode, PORTNUM, &status);
	int updated = 1;
	size_t size;
	DistVect in;
	

	while(1){
		if(updated){
			mcapi_msg_send(thisNodeEP, prevNodeEP, &out, sizeof(out), 0, &status);
			mcapi_msg_send(thisNodeEP, nextNodeEP, &out, sizeof(out), 0, &status);
			updated = 0;

			printf("Node %d updated\n", nodenum);
		}
		mcapi_msg_recv(thisNodeEP, &in, sizeof(in), &size, &status);
		memcpy(&distTable[COLINDEX(in.src, nextNode)], &in.dest, sizeof(in.dest));
		//col = COLINDEX(in.src, nextNode);
		for(i = 0; i < SIZE; i++){
			if(i != nodenum){
				int col = (distTable[0][i] < distTable[1][i]) ? 0 : 1;
				if(out.dest[i] != distTable[col][i] + NDIST){
					out.dest[i] = distTable[col][i] + NDIST;
					updated = 1;
				}
				
			}
		}
		

		///count++
	}
	
	
}


int main(int argc, char * argv){
	int i;
	for(i = 0; i < SIZE; i++){
		create_gem_thread(nodeBody, (void *)i);
	}
	wait_gem_threads();
	
}

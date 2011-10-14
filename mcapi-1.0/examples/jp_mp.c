/******************************************************************************
*    Jurassic Park Simulator (message passing version)
*******************************************************************************
*    Author: Mark O'Neill (mto@byu.edu)
*    Version: 0.82
*    Created: August 20, 2010
*    Last Modified: Feb 4, 2011
*
*    Compile using gcc -o jp jp_mp.c -lpthread -lmcapi -lmrapi
*
*    Usage: jp <visitors>
*            Visitors must be a positive multiple of three
*
*    Note: visitor count can only be up to 9 when using the sample
*        MCAPI implemention without modification. The sample
*        implementation's max nodes is 32 by default (MAX_NODES).
*        This needs to be set higher for more visitors.
*
*    Description: This mimicks a project for an operating systems
*        class at BYU, except that it uses message passing
*        rather than semaphores.  The description of the project
*        can be found at:
*        https://facwiki.cs.byu.edu/cs345/index.php/Jurassic_Park
*****************************************************************************/

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <time.h>
#include <assert.h>
#include "gem.h"
#include "mcapi.h"

/**
TODO LIST

1. Use message passing to update park structure
   to eliminate all shared memory necessity
2. Eliminate memory leaks.  After #1, there will
   only be one malloc call anyway.  That'll make
   it very easy.

*/

#define NUM_CARS                       4
#define NUM_DRIVERS                    4
#define NUM_SEATS                       3
#define NUM_VISITORS                (NUM_SEATS*15)
#define MAX_IN_PARK                    20
#define MAX_TICKETS                      (NUM_CARS*NUM_SEATS)
#define MAX_IN_MUSEUM               5
#define MAX_IN_GIFTSHOP             2

#define WAIT_MIN                1000000
#define WAIT_MAX                5000000
#define DISPLAY_PERIOD                1000000

#define SHUTDOWN_MSG                0xFFFFFFFF

#define PARKDEBUG                 0

// MCAPI Symbolic Constants
#define DEFAULT_PRIORITY            0

#define DEFAULT_PORT                          0
#define REQUEST_PORT                      1
#define RESOURCE_PORT                   2
#define MOVE_CARS_PORT                3

#define MASTER_NODE_ID                0
#define INTERFACE_NODE_ID            1
#define DISPLAY_NODE_ID                2
#define DRIVER_MANAGER_NODE_ID          3
#define DISPLAY_MANAGER_NODE_ID         4
#define PARK_ALLOWANCE_MANAGER_NODE_ID        5
#define TICKET_ALLOWANCE_MANAGER_NODE_ID    6
#define MUSEUM_ALLOWANCE_MANAGER_NODE_ID    7
#define PASSENGER_MANAGER_NODE_ID        8
#define GIFTSHOP_ALLOWANCE_MANAGER_NODE_ID    9

#define DRIVER_BASE_NODE_ID            10
#define CAR_BASE_NODE_ID            (DRIVER_BASE_NODE_ID+NUM_DRIVERS)
#define VISITOR_BASE_NODE_ID            (CAR_BASE_NODE_ID+NUM_CARS)

/**
 *  struct CAR
 *  @location: the location of the car in the park
 *  @passengers: the number of passengers in the car
 */
typedef struct car
{
        int location;
        int passengers;
} CAR;

/**
 *  struct JPARK
 *  @numOutsidePark: the number outside of the park
 *  @numInPark: the number inside the park (P)
 *  @numTicketsAvailable: the tickets available for sale (T)
 *  @numRidesTaken: the number of passengers that have taken rides
 *  @numExitedPark: the number of visitors that have exited
 *  @numInTicketLine: the number of visitors waiting for tickets
 *  @numInMuseumLine: the number of visitors waiting in museum line
 *  @numInMuseum: the number of visitors in the museum
 *  @numInCarLine: the number of visitors waiting in the car line
 *  @numInCars: the number of visitors riding in cars
 *  @numInGiftLine: the number of visitors waiting in the gift shop line
 *  @numInGiftShop: the number of visitors in the gift shop
 *  @drivers: the array of drivers' states (-1=T, 0=z, 1=A, 2=B, etc.)
 *  @cars: the array of cars in the park
 */
typedef struct
{
        int numOutsidePark;
        int numInPark;
        int numTicketsAvailable;
        int numRidesTaken;
        int numExitedPark;
        int numInTicketLine;
        int numInMuseumLine;
        int numInMuseum;
        int numInCarLine;
        int numInCars;
        int numInGiftLine;
        int numInGiftShop;
    int loadingCar;
    int rider;
    int visitors;
        int drivers[NUM_DRIVERS];
        CAR cars[NUM_CARS];
} JPARK;

typedef struct {
    JPARK* park;
    int number;
} PARAM;

void f(const char *str) {
    fprintf(stderr, "%s\n", str);
}


/*
 * Routine prototypes
 */
 
// Main routines and tasks
void driverTask(void* args);
char* myTime(char* svtime);
void drawPark(JPARK *park);
void jurassicDisplayTask(void *args);
int makeMove(int car, JPARK* myPark);
void parkInterfaceTask(void *args);
void shutdownPark(mcapi_endpoint_t masterEp);
void initializePark(JPARK* myPark, int num_visitors);
void initializeResources(mcapi_endpoint_t masterEp);
void visitorTask(void *args);
void carTask(void *args);

// Manager Threads
void interfaceManagerTask(void*);
void driverManagerTask(void*);
void parkAllowanceManagerTask(void*);
void ticketAllowanceManagerTask(void*);
void museumAllowanceManagerTask(void*);
void giftshopAllowanceManagerTask(void*);
void passengerManagerTask(void*);

// Helper routines
void createDriverCarVisitorTasks(PARAM* params);
void createNode(int nodeID, mcapi_version_t* version, mcapi_status_t* status);
void finalizeNode(mcapi_status_t* status);
mcapi_endpoint_t createEndpoint(int port, mcapi_status_t* status);
void sendMessage(mcapi_endpoint_t src, mcapi_endpoint_t dest, char* msg, size_t msgSize, mcapi_status_t* status);
void recvMessage(mcapi_endpoint_t src, char* msgBuf, size_t bufSize, size_t* recvSize, mcapi_status_t* status);
mcapi_endpoint_t getEndpoint(int nodeID, int port, mcapi_status_t* status);
void randomWait();

void mainTask(void * args) {
    PARAM* params = (PARAM *)args;
    JPARK* myPark = params->park;
    int visitors = params->number;

    // MCAPI structures for message passing
    mcapi_endpoint_t masterEp;
    mcapi_status_t status;
    mcapi_version_t version;

    // Create main MCAPI node
    createNode(MASTER_NODE_ID, &version, &status);
    masterEp = createEndpoint(DEFAULT_PORT, &status);

    initializeResources(masterEp);

    while (myPark->numExitedPark < visitors) gem_sleep();

    gem_sleep();
    shutdownPark(masterEp);

    // Finialize MCAPI node
    finalizeNode(&status);
}

int main(int argc, char* argv[]) {
    int visitors;
    long randomSeedVal;

    // get number of visitors
    if (argc >= 2) {
        if ((atoi(argv[1]) % 3) == 0 && atoi(argv[1]) > 0) {
            visitors = atoi(argv[1]);
        }
        else {
            printf("Visitors must be a positive multiple of three\n");
            return -1;
        }
    }
    else {
        visitors = NUM_VISITORS;
    }

    if (argc >= 3) randomSeedVal = atoi(argv[2]);
    else randomSeedVal = time(NULL);
    srand(randomSeedVal);    // seed for random wait times

    // Create park structure
    JPARK* myPark = malloc(sizeof(JPARK));
    initializePark(myPark,visitors);

    PARAM* params = malloc(sizeof(PARAM));
    params->park = myPark;
    params->number = visitors;

    create_gem_thread(parkAllowanceManagerTask, (void*)NULL);
    create_gem_thread(museumAllowanceManagerTask, (void*)NULL);
    create_gem_thread(giftshopAllowanceManagerTask, (void*)NULL);
    create_gem_thread(ticketAllowanceManagerTask, (void*)NULL);
    create_gem_thread(interfaceManagerTask, (void*)NULL);
    create_gem_thread(driverManagerTask, (void*)NULL);
    create_gem_thread(passengerManagerTask, (void*)NULL);

    create_gem_thread(parkInterfaceTask, ((PARAM *)params));
    create_gem_thread(jurassicDisplayTask, ((PARAM *)params));


    createDriverCarVisitorTasks(params);

    create_gem_thread(mainTask, (void*)params);

    wait_gem_threads();

    return 0;
}

void initializeResources(mcapi_endpoint_t masterEp) {
    int i;
    mcapi_status_t status;
    unsigned int sendBuf = 0;
    unsigned int recvBuf = 0;
    size_t bufSize = sizeof(unsigned int);
    size_t recvSize;
    mcapi_endpoint_t ticketResourceEp;
    mcapi_endpoint_t museumResourceEp;
    mcapi_endpoint_t parkResourceEp;
    mcapi_endpoint_t giftshopResourceEp;
    mcapi_endpoint_t displayEp;
    
    sendBuf = MASTER_NODE_ID;
    
    
    for (i = 0; i < 7; i++) { // wait for manager threads to check in (7 manager threads)
        recvMessage(masterEp, (void*)&recvBuf, bufSize, &recvSize, &status);
    }
    
    ticketResourceEp = getEndpoint(TICKET_ALLOWANCE_MANAGER_NODE_ID,RESOURCE_PORT,&status);
    museumResourceEp = getEndpoint(MUSEUM_ALLOWANCE_MANAGER_NODE_ID,RESOURCE_PORT,&status);
    parkResourceEp = getEndpoint(PARK_ALLOWANCE_MANAGER_NODE_ID,RESOURCE_PORT,&status);
    giftshopResourceEp = getEndpoint(GIFTSHOP_ALLOWANCE_MANAGER_NODE_ID,RESOURCE_PORT,&status);
    displayEp = getEndpoint(DISPLAY_MANAGER_NODE_ID,RESOURCE_PORT,&status);

    // Initialize messages for some manager threads (others dont need initial messages)
    for (i = 0; i < MAX_TICKETS; i++) {
        sendMessage(masterEp, ticketResourceEp, (void*)&sendBuf, bufSize, &status);
    }

    for (i = 0; i < MAX_IN_MUSEUM; i++) {
        sendMessage(masterEp, museumResourceEp, (void*)&sendBuf, bufSize, &status);
    }

    for (i = 0; i < MAX_IN_PARK; i++) {
        sendMessage(masterEp, parkResourceEp, (void*)&sendBuf, bufSize, &status);
    }

    for (i = 0; i < MAX_IN_GIFTSHOP; i++) {
        sendMessage(masterEp, giftshopResourceEp, (void*)&sendBuf, bufSize, &status);
    }
    
    // start park
    sendMessage(masterEp, displayEp, (void*)&sendBuf, bufSize, &status);
    return;
}

void createDriverCarVisitorTasks(PARAM* args) {
    PARAM* params = (PARAM *)args;
    JPARK* myPark = params->park;
    int visitors = params->number;
        int i;

    PARAM* driverParams = malloc(sizeof(PARAM) * NUM_DRIVERS);
    PARAM* carParams = malloc(sizeof(PARAM) * NUM_CARS);
    PARAM* visitorParams = malloc(sizeof(PARAM) * visitors);

        for (i = 0; i < NUM_DRIVERS; i++) {
            driverParams[i].park = myPark;
            driverParams[i].number = i;
            create_gem_thread(driverTask, (PARAM *)&driverParams[i]);
        }

        for (i = 0; i < NUM_CARS; i++) {
            carParams[i].park = myPark;
            carParams[i].number = i;
            create_gem_thread(carTask, (PARAM *)&carParams[i]);
        }

    for (i = 0; i < visitors; i++) {
            visitorParams[i].park = myPark;
            visitorParams[i].number = i;
            create_gem_thread(visitorTask, (PARAM *)&visitorParams[i]);
        }

        return;
}


void shutdownPark(mcapi_endpoint_t masterEp) {
    int i;
    mcapi_status_t status;
    unsigned int sendBuf = SHUTDOWN_MSG;
    unsigned int recvBuf = 0;
    size_t bufSize = sizeof(unsigned int);
    size_t recvSize;

    mcapi_endpoint_t driverEps[NUM_DRIVERS];
    mcapi_endpoint_t carEps[NUM_CARS];
    
    // Shutdown parkInterfaceTask and jurassicDisplayTask
    mcapi_endpoint_t parkInterfaceEp = getEndpoint(INTERFACE_NODE_ID,MOVE_CARS_PORT,&status);
    mcapi_endpoint_t parkInterfaceEp2 = getEndpoint(INTERFACE_NODE_ID,DEFAULT_PORT,&status);
    mcapi_endpoint_t jurassicDisplayEp = getEndpoint(DISPLAY_NODE_ID,DEFAULT_PORT,&status);    
    sendMessage(masterEp, jurassicDisplayEp, (void*)&sendBuf, bufSize, &status);
    recvMessage(masterEp, (void*)&recvBuf, bufSize, &recvSize, &status);
    sendMessage(masterEp, parkInterfaceEp, (void*)&sendBuf, bufSize, &status);
    sendMessage(masterEp, parkInterfaceEp2, (void*)&sendBuf, bufSize, &status);
    recvMessage(masterEp, (void*)&recvBuf, bufSize, &recvSize, &status);

    // Shutdown managers
    mcapi_endpoint_t requestDriverEp = getEndpoint(DRIVER_MANAGER_NODE_ID,REQUEST_PORT,&status);
    mcapi_endpoint_t requestDisplayEp = getEndpoint(DISPLAY_MANAGER_NODE_ID,REQUEST_PORT,&status);
    mcapi_endpoint_t requestParkEp = getEndpoint(PARK_ALLOWANCE_MANAGER_NODE_ID,REQUEST_PORT,&status);
    mcapi_endpoint_t requestTicketEp = getEndpoint(TICKET_ALLOWANCE_MANAGER_NODE_ID,REQUEST_PORT,&status);
    mcapi_endpoint_t requestMuseumEp = getEndpoint(MUSEUM_ALLOWANCE_MANAGER_NODE_ID,REQUEST_PORT,&status);
    mcapi_endpoint_t requestPassengerEp = getEndpoint(PASSENGER_MANAGER_NODE_ID,REQUEST_PORT,&status);
    mcapi_endpoint_t requestGiftshopEp = getEndpoint(GIFTSHOP_ALLOWANCE_MANAGER_NODE_ID,REQUEST_PORT,&status);
    
    sendMessage(masterEp, requestDriverEp, (void*)&sendBuf, bufSize, &status);
    sendMessage(masterEp, requestDisplayEp, (void*)&sendBuf, bufSize, &status);
    sendMessage(masterEp, requestParkEp, (void*)&sendBuf, bufSize, &status);
    sendMessage(masterEp, requestTicketEp, (void*)&sendBuf, bufSize, &status);
    sendMessage(masterEp, requestMuseumEp, (void*)&sendBuf, bufSize, &status);
    sendMessage(masterEp, requestPassengerEp, (void*)&sendBuf, bufSize, &status);
    sendMessage(masterEp, requestGiftshopEp, (void*)&sendBuf, bufSize, &status);

    for (i = 0; i < 7; i++) { // wait for manager threads to check out
        recvMessage(masterEp, (void*)&recvBuf, bufSize, &recvSize, &status);
    }
    
    // Shutdown cars
    for (i = 0; i < NUM_CARS; i++) {
        carEps[i] = getEndpoint(CAR_BASE_NODE_ID+i,DEFAULT_PORT,&status);
        sendMessage(masterEp, carEps[i], (void*)&sendBuf, bufSize, &status);
        recvMessage(masterEp, (void*)&recvBuf, bufSize, &recvSize, &status);
    }

    // Shutdown drivers
    for (i = 0; i < NUM_DRIVERS; i++) {
        driverEps[i] = getEndpoint(DRIVER_BASE_NODE_ID+i,DEFAULT_PORT,&status);
        sendMessage(masterEp, driverEps[i], (void*)&sendBuf, bufSize, &status);
        recvMessage(masterEp, (void*)&recvBuf, bufSize, &recvSize, &status);
    }

    printf("\nJurassic Park is shutting down for the evening!\n");
    return;
}

void giftshopAllowanceManagerTask(void * args) {
    unsigned int sendBuf = 0;
    unsigned int recvBuf = 0;
    size_t bufSize = sizeof(unsigned int);
    size_t recvSize;
    unsigned int requesterPort = DEFAULT_PORT;
    unsigned int requesterNodeID;
    mcapi_status_t status;
    mcapi_version_t version;
    mcapi_endpoint_t giftshopReadyEp;
    mcapi_endpoint_t giftshopWantedEp;
    mcapi_endpoint_t requesterEp;
    mcapi_endpoint_t masterEp;

    createNode(GIFTSHOP_ALLOWANCE_MANAGER_NODE_ID, &version, &status);
    giftshopWantedEp = createEndpoint(REQUEST_PORT,&status);
    giftshopReadyEp = createEndpoint(RESOURCE_PORT,&status);
    masterEp = getEndpoint(MASTER_NODE_ID,DEFAULT_PORT,&status);
    
    // Tell master node that you're set up
    sendBuf = GIFTSHOP_ALLOWANCE_MANAGER_NODE_ID;
    sendMessage(giftshopReadyEp, masterEp, (void*)&sendBuf, bufSize, &status);
    
    while (1) {
        // get message from visitor requesting giftshop access
        recvMessage(giftshopWantedEp, (void*)&recvBuf, bufSize, &recvSize, &status);
        if (recvBuf == SHUTDOWN_MSG) {
            break;
        }
        requesterNodeID = recvBuf; // store requester's node ID
        requesterEp = getEndpoint(requesterNodeID,requesterPort,&status);

        // wait for giftshop to be ready (empty enough for another visitor)
        recvMessage(giftshopReadyEp, (void*)&recvBuf, bufSize, &recvSize, &status);

        // giftshop ready - notify visitor
        sendMessage(giftshopReadyEp, requesterEp, (void*)&sendBuf, bufSize, &status);
    }
    
    // Inform master node that you're done
    sendMessage(giftshopReadyEp, masterEp, (void*)&sendBuf, bufSize, &status);

    if (PARKDEBUG) printf("giftshopAllowanceManagerTask done\n");
    finalizeNode(&status);
}

void ticketAllowanceManagerTask(void * args) {
    unsigned int sendBuf = 0;
    unsigned int recvBuf = 0;
    size_t bufSize = sizeof(unsigned int);
    size_t recvSize;
    unsigned int requesterPort = DEFAULT_PORT;
    unsigned int requesterNodeID;
    mcapi_status_t status;
    mcapi_version_t version;
    mcapi_endpoint_t ticketReadyEp;
    mcapi_endpoint_t ticketWantedEp;
    mcapi_endpoint_t requesterEp;
    mcapi_endpoint_t masterEp;

    createNode(TICKET_ALLOWANCE_MANAGER_NODE_ID, &version, &status);
    ticketWantedEp = createEndpoint(REQUEST_PORT,&status);
    ticketReadyEp = createEndpoint(RESOURCE_PORT,&status);
    masterEp = getEndpoint(MASTER_NODE_ID,DEFAULT_PORT,&status);
    
    // Tell master node that you're set up
    sendBuf = TICKET_ALLOWANCE_MANAGER_NODE_ID;
    sendMessage(ticketReadyEp, masterEp, (void*)&sendBuf, bufSize, &status);
    
    while (1) {
        // get message from visitor requesting ticket
        recvMessage(ticketWantedEp, (void*)&recvBuf, bufSize, &recvSize, &status);
        if (recvBuf == SHUTDOWN_MSG) {
            break;
        }
        requesterNodeID = recvBuf; // store requester's node ID
        requesterEp = getEndpoint(requesterNodeID,requesterPort,&status);

        // wait for ticket to be ready
        recvMessage(ticketReadyEp, (void*)&recvBuf, bufSize, &recvSize, &status);

        // ticket ready - notify visitor
        sendMessage(ticketReadyEp, requesterEp, (void*)&sendBuf, bufSize, &status);
    }

    // Inform master node that you're done
    sendMessage(ticketReadyEp, masterEp, (void*)&sendBuf, bufSize, &status);
    
    if (PARKDEBUG) printf("ticketAllowanceManagerTask done\n");
    finalizeNode(&status);
}

void museumAllowanceManagerTask(void * args) {
    unsigned int sendBuf = 0;
    unsigned int recvBuf = 0;
    size_t bufSize = sizeof(unsigned int);
    size_t recvSize;
    unsigned int requesterPort = DEFAULT_PORT;
    unsigned int requesterNodeID;
    mcapi_status_t status;
    mcapi_version_t version;
    mcapi_endpoint_t museumReadyEp;
    mcapi_endpoint_t museumWantedEp;
    mcapi_endpoint_t requesterEp;
    mcapi_endpoint_t masterEp;

    createNode(MUSEUM_ALLOWANCE_MANAGER_NODE_ID, &version, &status);
    museumWantedEp = createEndpoint(REQUEST_PORT,&status);
    museumReadyEp = createEndpoint(RESOURCE_PORT,&status);
    masterEp = getEndpoint(MASTER_NODE_ID,DEFAULT_PORT,&status);
    
    // Tell master node that you're set up
    sendBuf = MUSEUM_ALLOWANCE_MANAGER_NODE_ID;
    sendMessage(museumReadyEp, masterEp, (void*)&sendBuf, bufSize, &status);
    
    while (1) {
            // get message from visitor requesting museum access
            recvMessage(museumWantedEp, (void*)&recvBuf, bufSize, &recvSize, &status);
        if (recvBuf == SHUTDOWN_MSG) {
            break;
        }
        requesterNodeID = recvBuf; // store requester's node ID
        requesterEp = getEndpoint(requesterNodeID,requesterPort,&status);

        // wait for museum to be ready (empty enough for another visitor)
        recvMessage(museumReadyEp, (void*)&recvBuf, bufSize, &recvSize, &status);

        // museum ready - notify visitor
        sendMessage(museumReadyEp, requesterEp, (void*)&sendBuf, bufSize, &status);
    }

    // Inform master node that you're done
    sendMessage(museumReadyEp, masterEp, (void*)&sendBuf, bufSize, &status);
    
    if (PARKDEBUG) printf("museumAllowanceManagerTask done\n");
    finalizeNode(&status);
}

void parkAllowanceManagerTask(void * args) {
    unsigned int sendBuf = 0;
    unsigned int recvBuf = 0;
    size_t bufSize = sizeof(unsigned int);
    size_t recvSize;
    unsigned int requesterPort = DEFAULT_PORT;
    unsigned int requesterNodeID;
    mcapi_status_t status;
    mcapi_version_t version;
    mcapi_endpoint_t parkReadyEp;
    mcapi_endpoint_t parkWantedEp;
    mcapi_endpoint_t requesterEp;
    mcapi_endpoint_t masterEp;

    createNode(PARK_ALLOWANCE_MANAGER_NODE_ID, &version, &status);
    parkWantedEp = createEndpoint(REQUEST_PORT,&status);
    parkReadyEp = createEndpoint(RESOURCE_PORT,&status);
    masterEp = getEndpoint(MASTER_NODE_ID,DEFAULT_PORT,&status);
    
    // Tell master node that you're set up
    sendBuf = PARK_ALLOWANCE_MANAGER_NODE_ID;
    sendMessage(parkReadyEp, masterEp, (void*)&sendBuf, bufSize, &status);
    
    while (1) {
        // get message from visitor requesting park access
        recvMessage(parkWantedEp, (void*)&recvBuf, bufSize, &recvSize, &status);
        if (recvBuf == SHUTDOWN_MSG) {
            break;
        }
        requesterNodeID = recvBuf; // store requester's node ID
        requesterEp = getEndpoint(requesterNodeID,requesterPort,&status);

        // wait for park to be ready to accept more visitors
        recvMessage(parkReadyEp, (void*)&recvBuf, bufSize, &recvSize, &status);

        // park ready - notify visitor
        sendMessage(parkReadyEp, requesterEp, (void*)&sendBuf, bufSize, &status);
    }

    // Inform master node that you're done
    sendMessage(parkReadyEp, masterEp, (void*)&sendBuf, bufSize, &status);
    
    if (PARKDEBUG) printf("parkAllowanceManagerTask done\n");
    finalizeNode(&status);
}

void driverManagerTask(void * args) {
    unsigned int sendBuf = 0;
    unsigned int recvBuf = 0;
    size_t bufSize = sizeof(unsigned int);
    size_t recvSize;
    unsigned int requesterPort = DEFAULT_PORT;
    unsigned int requesterNodeID;
    unsigned int driverNodeID;
    mcapi_status_t status;
    mcapi_version_t version;
    mcapi_endpoint_t driverReadyEp;
    mcapi_endpoint_t driverWantedEp;
    mcapi_endpoint_t requesterEp;
    mcapi_endpoint_t masterEp;

    createNode(DRIVER_MANAGER_NODE_ID, &version, &status);
    driverWantedEp = createEndpoint(REQUEST_PORT,&status);
    driverReadyEp = createEndpoint(RESOURCE_PORT,&status);
    masterEp = getEndpoint(MASTER_NODE_ID,DEFAULT_PORT,&status);
    
    // Tell master node that you're set up
    sendBuf = DRIVER_MANAGER_NODE_ID;
    sendMessage(driverReadyEp, masterEp, (void*)&sendBuf, bufSize, &status);

    while (1) {
        // get message from node requesting a driver
        recvMessage(driverWantedEp, (void*)&recvBuf, bufSize, &recvSize, &status);
        if (recvBuf == SHUTDOWN_MSG) {
            break;
        }
        requesterNodeID = recvBuf; // store requester's node ID
        requesterEp = getEndpoint(requesterNodeID,requesterPort,&status);

        // wait for any driver to be ready
        recvMessage(driverReadyEp, (void*)&recvBuf, bufSize, &recvSize, &status);
            driverNodeID = recvBuf; // store driver's node ID
            sendBuf = driverNodeID;

        // driver available, signal requester (and tell him what driver ID is ready)
        sendMessage(driverReadyEp, requesterEp, (void*)&sendBuf, bufSize, &status);
    }

    // Inform master node that you're done
    sendBuf = DRIVER_MANAGER_NODE_ID;
    sendMessage(driverReadyEp, masterEp, (void*)&sendBuf, bufSize, &status);
    
    if (PARKDEBUG) printf("driverManagerTask done\n");
    finalizeNode(&status);
}

void driverTask(void *args) {
    PARAM* params = (PARAM *)args;
    JPARK* myPark = params->park;
    int driverID = params->number;
    int myNodeID = driverID + DRIVER_BASE_NODE_ID;

    // MCAPI-specific variables
    unsigned int sendBuf;
    unsigned int recvBuf;
    unsigned int requesterNodeID;
    size_t bufSize = sizeof(unsigned int);
    size_t recvSize;
    mcapi_status_t status;
    mcapi_version_t version;
    mcapi_endpoint_t thisDriverEp;
    mcapi_endpoint_t parkResourceEp;
    mcapi_endpoint_t parkRequestEp;
    mcapi_endpoint_t requesterEp;
    mcapi_endpoint_t driverIdleEp;
    mcapi_endpoint_t masterEp;

    // Initialize this MCAPI node
    createNode(myNodeID, &version, &status);
    thisDriverEp = createEndpoint(DEFAULT_PORT, &status);

    // Put endpoint node ID into sendBuffer;
    sendBuf = myNodeID;

    // Get references to all needed external endpoints
    driverIdleEp = getEndpoint(DRIVER_MANAGER_NODE_ID, RESOURCE_PORT, &status);
    parkRequestEp = getEndpoint(DISPLAY_MANAGER_NODE_ID,REQUEST_PORT,&status);
    parkResourceEp = getEndpoint(DISPLAY_MANAGER_NODE_ID,RESOURCE_PORT,&status);
    masterEp = getEndpoint(MASTER_NODE_ID,DEFAULT_PORT,&status);

    // Inform park that you're idle
    sendMessage(thisDriverEp, driverIdleEp, (void*)&sendBuf, bufSize, &status);

    // Repeat until park is closed
    while (1) {

        // Wait for request to sell tickets or drive a car
        recvMessage(thisDriverEp, (void*)&recvBuf, bufSize, &recvSize, &status);
        if (recvBuf == SHUTDOWN_MSG) {
            break;
        }
        requesterNodeID = recvBuf;

        if (requesterNodeID >= VISITOR_BASE_NODE_ID) { // requester is a visitor, sell ticket
            // Move the driver to the ticket stand
            sendMessage(thisDriverEp, parkRequestEp, (void*)&sendBuf, bufSize, &status); // request lock on park
            recvMessage(thisDriverEp, (void*)&recvBuf, bufSize, &recvSize, &status); // get lock on park
            myPark->drivers[driverID] = -1; // change to ticket stand
            sendMessage(thisDriverEp, parkResourceEp, (void*)&sendBuf, bufSize, &status); // release lock on park

            // get access to the visitor's endpoint
            requesterEp = getEndpoint(requesterNodeID, DEFAULT_PORT, &status);

            // Signal the visitor that you have his ticket ready
            sendMessage(thisDriverEp, requesterEp, (void*)&sendBuf, bufSize, &status);

            // Wait until the visitor buys the ticket
            recvMessage(thisDriverEp, (void*)&recvBuf, bufSize, &recvSize, &status);

        }
        else if (requesterNodeID >= CAR_BASE_NODE_ID && requesterNodeID < VISITOR_BASE_NODE_ID) { // requester is a car, drive it
            // Move the driver to the loading car
            sendMessage(thisDriverEp, parkRequestEp, (void*)&sendBuf, bufSize, &status); // request lock on park
            recvMessage(thisDriverEp, (void*)&recvBuf, bufSize, &recvSize, &status); // get lock on park
            myPark->drivers[driverID] = myPark->loadingCar + 1; // change driver to be loading car
            fprintf(stderr, "DRIVER %d DRIVING CAR %c\n", driverID+1, 'A'+myPark->loadingCar);
            fflush(stderr);
            sendMessage(thisDriverEp, parkResourceEp, (void*)&sendBuf, bufSize, &status); // release lock on park

            // get access to the car's endpoint
            requesterEp = getEndpoint(requesterNodeID, DEFAULT_PORT, &status);
            
            // Signal the car that you're ready to drive
            sendMessage(thisDriverEp, requesterEp, (void*)&sendBuf, bufSize, &status);

            // Wait until the ride is over and all passengers have left
            recvMessage(thisDriverEp, (void*)&recvBuf, bufSize, &recvSize, &status);
        }
        else {
            printf("Unknown request for driver\n");
            abort();
        }

        // Put the driver back to sleep
        sendMessage(thisDriverEp, parkRequestEp, (void*)&sendBuf, bufSize, &status); // request lock on park
        recvMessage(thisDriverEp, (void*)&recvBuf, bufSize, &recvSize, &status); // get lock on park
        myPark->drivers[driverID] = 0; // change park
        sendMessage(thisDriverEp, parkResourceEp, (void*)&sendBuf, bufSize, &status); // release lock on park

        // Inform park that you're idle
        sendMessage(thisDriverEp, driverIdleEp, (void*)&sendBuf, bufSize, &status);
    }

    // Tell master node you're finished
    sendMessage(thisDriverEp, masterEp, (void*)&sendBuf, bufSize, &status);
    if (PARKDEBUG) printf("driver %d done\n",driverID);
    finalizeNode(&status);

}

void passengerManagerTask(void * args) {
    unsigned int sendBuf = 0;
    unsigned int recvBuf = 0;
    size_t bufSize = sizeof(unsigned int);
    size_t recvSize;
    unsigned int requesterPort = DEFAULT_PORT;
    unsigned int requesterNodeID;
    unsigned int visitorNodeID;
    mcapi_status_t status;
    mcapi_version_t version;
    mcapi_endpoint_t passengerReadyEp;
    mcapi_endpoint_t passengerWantedEp;
    mcapi_endpoint_t requesterEp;
    mcapi_endpoint_t masterEp;

    createNode(PASSENGER_MANAGER_NODE_ID, &version, &status);
    passengerWantedEp = createEndpoint(REQUEST_PORT,&status);
    passengerReadyEp = createEndpoint(RESOURCE_PORT,&status);
    masterEp = getEndpoint(MASTER_NODE_ID,DEFAULT_PORT,&status);
    
    // Tell master node that you're set up
    sendBuf = PASSENGER_MANAGER_NODE_ID;
    sendMessage(passengerReadyEp, masterEp, (void*)&sendBuf, bufSize, &status);

    while (1) {
        // get message from node requesting a passenger
        recvMessage(passengerWantedEp, (void*)&recvBuf, bufSize, &recvSize, &status);
        if (recvBuf == SHUTDOWN_MSG) {
            break;
        }
        requesterNodeID = recvBuf; // store requester's node ID
        requesterEp = getEndpoint(requesterNodeID,requesterPort,&status);

        // wait for any visitor to be ready
        recvMessage(passengerReadyEp, (void*)&recvBuf, bufSize, &recvSize, &status);
        visitorNodeID = recvBuf; // store visitors's node ID
        sendBuf = visitorNodeID;

        // passenger available, signal requester (and tell him what vistitor ID is ready)
        sendMessage(passengerReadyEp, requesterEp, (void*)&sendBuf, bufSize, &status);
    }
    
    // Inform master node that you're done
    sendBuf = PASSENGER_MANAGER_NODE_ID;
    sendMessage(passengerReadyEp, masterEp, (void*)&sendBuf, bufSize, &status);
    
    if (PARKDEBUG) printf("passengerManagerTask done\n");
    finalizeNode(&status);
}

void carTask(void* args) {
    PARAM* params = (PARAM *)args;
    JPARK* myPark = params->park;
    int carID = params->number;
    int myNodeID = carID + CAR_BASE_NODE_ID;
    unsigned int myPassengerIDs[4];
    int myDriverID = 0;
    int passengers = 0;
    int i = 0;

    // MCAPI-specific variables
    unsigned int sendBuf;
    unsigned int recvBuf;
    size_t bufSize = sizeof(unsigned int);
    size_t recvSize;
    mcapi_status_t status;
    mcapi_version_t version;
    mcapi_endpoint_t thisCarEp;
    mcapi_endpoint_t interfaceEp;
    mcapi_endpoint_t driverRequestEp;
    mcapi_endpoint_t myDriverEp;
    mcapi_endpoint_t myPassengerEps[NUM_SEATS];
    mcapi_endpoint_t masterEp;

    // Initialize this MCAPI node
    createNode(myNodeID, &version, &status);
    thisCarEp = createEndpoint(DEFAULT_PORT, &status);
    interfaceEp = getEndpoint(INTERFACE_NODE_ID,DEFAULT_PORT,&status);
    driverRequestEp = getEndpoint(DRIVER_MANAGER_NODE_ID,REQUEST_PORT,&status);
    masterEp = getEndpoint(MASTER_NODE_ID,DEFAULT_PORT,&status);

    // Send your node id as message content
    sendBuf = myNodeID;
    
    while (1) {

        // Wait for parkInterfaceTask to signal that you have a passenger
        recvMessage(thisCarEp, (void*)&recvBuf, bufSize, &recvSize, &status);
        if (recvBuf == SHUTDOWN_MSG) {
            break;
        }

        // store the passenger's node ID
        myPassengerIDs[passengers] = recvBuf;
        myPassengerEps[passengers] = getEndpoint(myPassengerIDs[passengers],DEFAULT_PORT,&status);

        // Let the world know you are loading
        myPark->loadingCar = carID;
        fprintf(stderr, "LOADING CAR %c\n", 'A'+myPark->loadingCar);
        fflush(stderr);

        // Signal your visitor/passenger to sit down
        sendMessage(thisCarEp, myPassengerEps[passengers], (void*)&sendBuf, bufSize, &status);
        
        // Wait for passenger to say he's seated
        recvMessage(thisCarEp, (void*)&recvBuf, bufSize, &recvSize, &status);
        assert(recvBuf == myPassengerIDs[passengers]);
        
        // Increase number of passengers on this car
        passengers++;

        // Check to see if the car isn't full
        if (passengers < NUM_SEATS) {
            // Tell parkInterfaceTask that you have loaded the passenger
            sendMessage(thisCarEp, interfaceEp, (void*)&sendBuf, bufSize, &status);
        }
        // When the car is full
        else {
        
            // Request a driver and wait until one is ready (driver knows whether or not you're a car or visitor
            // based on your node id
            sendMessage(thisCarEp, driverRequestEp, (void*)&sendBuf, bufSize, &status);
            recvMessage(thisCarEp, (void*)&recvBuf, bufSize, &recvSize, &status);
            myDriverID = recvBuf;  // save node ID of driver
            myDriverEp = getEndpoint(myDriverID, DEFAULT_PORT, &status);
            printf("DRIVER ID: %u\n", myDriverID);
            
            // signal to driver that you need him to drive a car
            sendMessage(thisCarEp, myDriverEp, (void*)&sendBuf, bufSize, &status);
                
            // Wait for driver to tell you he's ready
            recvMessage(thisCarEp, (void*)&recvBuf, bufSize, &recvSize, &status);

            // Tell parkInterfaceTask that you have loaded the passenger
            sendMessage(thisCarEp, interfaceEp, (void*)&sendBuf, bufSize, &status);

            // Wait until ride is over
            recvMessage(thisCarEp, (void*)&recvBuf, bufSize, &recvSize, &status);
            assert(recvBuf == INTERFACE_NODE_ID);
            
            // Tell passengers they're done
            for (i = 0; i < NUM_SEATS; i++) {
                sendMessage(thisCarEp, myPassengerEps[i], (void*)&sendBuf, bufSize, &status);
            }
            // Tell driver he's done
            sendMessage(thisCarEp, myDriverEp, (void*)&sendBuf, bufSize, &status);

            // Empty out your car
            passengers = 0;
        }
    }

    // Tell master node you're finished
    sendBuf = myNodeID;
    sendMessage(thisCarEp, masterEp, (void*)&sendBuf, bufSize, &status);
    
    if (PARKDEBUG) printf("car %d done\n",carID);
    finalizeNode(&status);
}

void visitorTask(void* args) {
    PARAM* params = (PARAM *)args;
    JPARK* myPark = params->park;
    int visitorID = params->number;
    int myNodeID = visitorID + VISITOR_BASE_NODE_ID;
    unsigned int myDriverNodeID;
    unsigned int myCarNodeID;

    // MCAPI-specific variables
    unsigned int sendBuf;
    unsigned int recvBuf;
    size_t bufSize = sizeof(unsigned int);
    size_t recvSize;
    mcapi_status_t status;
    mcapi_version_t version;
    mcapi_endpoint_t thisVisitorEp;
    mcapi_endpoint_t parkAllowanceResourceEp;
    mcapi_endpoint_t parkAllowanceRequestEp;
    mcapi_endpoint_t ticketAllowanceResourceEp;
    mcapi_endpoint_t ticketAllowanceRequestEp;
    mcapi_endpoint_t museumAllowanceResourceEp;
    mcapi_endpoint_t museumAllowanceRequestEp;
    mcapi_endpoint_t giftshopAllowanceResourceEp;
    mcapi_endpoint_t giftshopAllowanceRequestEp;
    mcapi_endpoint_t passengerResourceEp;
    mcapi_endpoint_t parkResourceEp;
    mcapi_endpoint_t parkRequestEp;
    mcapi_endpoint_t driverRequestEp;
    mcapi_endpoint_t myDriverEp;
    mcapi_endpoint_t myCarEp;

    // Initialize this MCAPI node
    createNode(myNodeID, &version, &status);
    thisVisitorEp = createEndpoint(DEFAULT_PORT, &status);
    
    // Put endpoint node ID into sendBuffer;
    sendBuf = myNodeID;
    
    // Get references to all needed external endpoints
    parkRequestEp = getEndpoint(DISPLAY_MANAGER_NODE_ID,REQUEST_PORT,&status);
    parkResourceEp = getEndpoint(DISPLAY_MANAGER_NODE_ID,RESOURCE_PORT,&status);
    parkAllowanceRequestEp = getEndpoint(PARK_ALLOWANCE_MANAGER_NODE_ID,REQUEST_PORT,&status);
    parkAllowanceResourceEp = getEndpoint(PARK_ALLOWANCE_MANAGER_NODE_ID,RESOURCE_PORT,&status);
    ticketAllowanceRequestEp = getEndpoint(TICKET_ALLOWANCE_MANAGER_NODE_ID,REQUEST_PORT,&status);
    ticketAllowanceResourceEp = getEndpoint(TICKET_ALLOWANCE_MANAGER_NODE_ID,RESOURCE_PORT,&status);
    driverRequestEp = getEndpoint(DRIVER_MANAGER_NODE_ID,REQUEST_PORT,&status);
    museumAllowanceRequestEp = getEndpoint(MUSEUM_ALLOWANCE_MANAGER_NODE_ID,REQUEST_PORT,&status);
    museumAllowanceResourceEp = getEndpoint(MUSEUM_ALLOWANCE_MANAGER_NODE_ID,RESOURCE_PORT,&status);
    passengerResourceEp = getEndpoint(PASSENGER_MANAGER_NODE_ID,RESOURCE_PORT,&status);
    giftshopAllowanceRequestEp = getEndpoint(GIFTSHOP_ALLOWANCE_MANAGER_NODE_ID,REQUEST_PORT,&status);
    giftshopAllowanceResourceEp = getEndpoint(GIFTSHOP_ALLOWANCE_MANAGER_NODE_ID,RESOURCE_PORT,&status);

    // Wait to enter park
    randomWait();

    // Move visitor to outside park
    sendMessage(thisVisitorEp, parkRequestEp, (void*)&sendBuf, bufSize, &status); // request lock on park
    recvMessage(thisVisitorEp, (void*)&recvBuf, bufSize, &recvSize, &status); // get lock on park
    myPark->numOutsidePark++; // update park
    sendMessage(thisVisitorEp, parkResourceEp, (void*)&sendBuf, bufSize, &status); // release lock on park

    // Wait to get to park
    randomWait();

    // Wait to see if you can enter the park
    sendMessage(thisVisitorEp, parkAllowanceRequestEp, (void*)&sendBuf, bufSize, &status);
    recvMessage(thisVisitorEp, (void*)&recvBuf, bufSize, &recvSize, &status);
    
    // Move visitor to inside park (and into ticket line)
    sendMessage(thisVisitorEp, parkRequestEp, (void*)&sendBuf, bufSize, &status); // request lock on park
    recvMessage(thisVisitorEp, (void*)&recvBuf, bufSize, &recvSize, &status); // get lock on park
    myPark->numOutsidePark--;
    myPark->numInPark++;
    myPark->numInTicketLine++;
    sendMessage(thisVisitorEp, parkResourceEp, (void*)&sendBuf, bufSize, &status); // release lock on park

    // Wait in ticket line
    randomWait();

    // Wait until there is an available ticket before you request a driver
    sendMessage(thisVisitorEp, ticketAllowanceRequestEp, (void*)&sendBuf, bufSize, &status);
    recvMessage(thisVisitorEp, (void*)&recvBuf, bufSize, &recvSize, &status);

    // Change the number of available tickets
    sendMessage(thisVisitorEp, parkRequestEp, (void*)&sendBuf, bufSize, &status); // request lock on park
    recvMessage(thisVisitorEp, (void*)&recvBuf, bufSize, &recvSize, &status); // get lock on park
    myPark->numTicketsAvailable--;
    sendMessage(thisVisitorEp, parkResourceEp, (void*)&sendBuf, bufSize, &status); // release lock on park

    // Wait until you can get a driver
    sendMessage(thisVisitorEp, driverRequestEp, (void*)&sendBuf, bufSize, &status);
    recvMessage(thisVisitorEp, (void*)&recvBuf, bufSize, &recvSize, &status);
    myDriverNodeID = recvBuf;     // store node ID of ready driver
    
    // Get the driver's endpoint
    myDriverEp = getEndpoint(myDriverNodeID,DEFAULT_PORT,&status);
    {
        // Signal to driver that you want to purchase a ticket
        sendMessage(thisVisitorEp, myDriverEp, (void*)&sendBuf, bufSize, &status);
        {
            // Wait for your ticket to be ready
            recvMessage(thisVisitorEp, (void*)&recvBuf, bufSize, &recvSize, &status);

            // buy ticket, let the driver know you got it so it can go on
            sendMessage(thisVisitorEp, myDriverEp, (void*)&sendBuf, bufSize, &status);
        }

    }

    // Leave the ticket line and go to museum
    sendMessage(thisVisitorEp, parkRequestEp, (void*)&sendBuf, bufSize, &status); // request lock on park
    recvMessage(thisVisitorEp, (void*)&recvBuf, bufSize, &recvSize, &status); // get lock on park
    myPark->numInTicketLine--;
    myPark->numInMuseumLine++;
    sendMessage(thisVisitorEp, parkResourceEp, (void*)&sendBuf, bufSize, &status); // release lock on park

    // Wait in museum line for some time
    randomWait();

    // Wait until you can enter the museum
    sendMessage(thisVisitorEp, museumAllowanceRequestEp, (void*)&sendBuf, bufSize, &status);
    recvMessage(thisVisitorEp, (void*)&recvBuf, bufSize, &recvSize, &status);
    
    // Get inside museum, update display appropriately
    sendMessage(thisVisitorEp, parkRequestEp, (void*)&sendBuf, bufSize, &status); // request lock on park
    recvMessage(thisVisitorEp, (void*)&recvBuf, bufSize, &recvSize, &status); // get lock on park
    myPark->numInMuseumLine--;
    myPark->numInMuseum++;
    sendMessage(thisVisitorEp, parkResourceEp, (void*)&sendBuf, bufSize, &status); // release lock on park

    // Wait in museum for a certain amount of time
    randomWait();

    // Leave museum, go to tour car line, and allow someone else into the museum
    sendMessage(thisVisitorEp, parkRequestEp, (void*)&sendBuf, bufSize, &status); // request lock on park
    recvMessage(thisVisitorEp, (void*)&recvBuf, bufSize, &recvSize, &status); // get lock on park
    myPark->numInMuseum--;
    myPark->numInCarLine++;
    sendMessage(thisVisitorEp, parkResourceEp, (void*)&sendBuf, bufSize, &status); // release lock on park
    sendMessage(thisVisitorEp, museumAllowanceResourceEp, (void*)&sendBuf, bufSize, &status);

    // Signal to passenger manager that you are ready to be seated, wait for car to tell you that you can
    sendMessage(thisVisitorEp, passengerResourceEp, (void*)&sendBuf, bufSize, &status);
    
    recvMessage(thisVisitorEp, (void*)&recvBuf, bufSize, &recvSize, &status); // response from car
    
    myCarNodeID = recvBuf; // store node ID of car you'll be entering
    myCarEp = getEndpoint(myCarNodeID,DEFAULT_PORT,&status); // get car's endpoint
    
    // Wait until you have been seated then remove yourself from the line and put yourself in the car
    sendMessage(thisVisitorEp, parkRequestEp, (void*)&sendBuf, bufSize, &status); // request lock on park
    recvMessage(thisVisitorEp, (void*)&recvBuf, bufSize, &recvSize, &status); // get lock on park
    myPark->numInCarLine--;
    myPark->numInCars++;
    myPark->numTicketsAvailable++;
    sendMessage(thisVisitorEp, parkResourceEp, (void*)&sendBuf, bufSize, &status); // release lock on park
    fprintf(stderr, "VISITOR %d ON RIDE\n", visitorID);
    
    // Tell car you've chosen a seat and sat down
    sendMessage(thisVisitorEp, myCarEp, (void*)&sendBuf, bufSize, &status);
        
    // Return your park ticket
    sendMessage(thisVisitorEp, ticketAllowanceResourceEp, (void*)&sendBuf, bufSize, &status);
    
    // Wait for car to tell you the ride is over
    recvMessage(thisVisitorEp, (void*)&recvBuf, bufSize, &recvSize, &status); // car tells you ride is done
    assert(myCarNodeID = recvBuf);

    fprintf(stderr, "VISITOR %d OFF RIDE\n", visitorID);
    // leave the car and go to gift shop line
    sendMessage(thisVisitorEp, parkRequestEp, (void*)&sendBuf, bufSize, &status); // request lock on park
    recvMessage(thisVisitorEp, (void*)&recvBuf, bufSize, &recvSize, &status); // get lock on park    
    myPark->numInCars--;
    myPark->numInGiftLine++;
    sendMessage(thisVisitorEp, parkResourceEp, (void*)&sendBuf, bufSize, &status); // release lock on park

    fprintf(stderr, "VISITOR %d AT GIFT SHOP\n", visitorID);
    // Wait in the gift shop line
    randomWait();

    // Wait until you can enter the gift shop
    sendMessage(thisVisitorEp, giftshopAllowanceRequestEp, (void*)&sendBuf, bufSize, &status);
    recvMessage(thisVisitorEp, (void*)&recvBuf, bufSize, &recvSize, &status);
    
    // Enter giftshop
    sendMessage(thisVisitorEp, parkRequestEp, (void*)&sendBuf, bufSize, &status); // request lock on park
    recvMessage(thisVisitorEp, (void*)&recvBuf, bufSize, &recvSize, &status); // get lock on park    
    myPark->numInGiftLine--;
    myPark->numInGiftShop++;
    sendMessage(thisVisitorEp, parkResourceEp, (void*)&sendBuf, bufSize, &status); // release lock on park

    // Spend some time in the gift shop
    randomWait();

    // Leave the gift shop and exit the park
    sendMessage(thisVisitorEp, parkRequestEp, (void*)&sendBuf, bufSize, &status); // request lock on park
    recvMessage(thisVisitorEp, (void*)&recvBuf, bufSize, &recvSize, &status); // get lock on park    
    myPark->numInGiftShop--;
    myPark->numInPark--;
    myPark->numExitedPark++;
    sendMessage(thisVisitorEp, parkResourceEp, (void*)&sendBuf, bufSize, &status); // release lock on park
    
    // Allow more people into giftshop and park (release resources)
    sendMessage(thisVisitorEp, giftshopAllowanceResourceEp, (void*)&sendBuf, bufSize, &status);
    sendMessage(thisVisitorEp, parkAllowanceResourceEp, (void*)&sendBuf, bufSize, &status);

    if (PARKDEBUG) printf("visitor %d done\n",visitorID);
    finalizeNode(&status);
}

void initializePark(JPARK* myPark, int num_visitors) {
    int i;

    // Start park
    printf("Start Jurassic Park...\n");

    // initial car locations
    for (i=0; i < NUM_CARS; i++) {
        (myPark->cars[i]).location = 33 - i;
        (myPark->cars[i]).passengers = 0;
    }

    // drivers are all asleep
    for (i=0; i < NUM_DRIVERS; i++) {
        myPark->drivers[i] = 0;
    }

    // initialize other park variables
    myPark->visitors = num_visitors;
    myPark->numOutsidePark = 0;                                /* # trying to enter park */
    myPark->numTicketsAvailable = MAX_TICKETS;                /* T=# */
    myPark->numInPark = 0;                                    /* P=# */
    myPark->numRidesTaken = 0;                                /* S=# */

    myPark->numInTicketLine = 0;                                /* Ticket line */
    myPark->numInMuseumLine = 0;                                /* Museum line */
    myPark->numInMuseum = 0;                                    /* # in museum */
    myPark->numInCarLine = 0;                                /* Ride line */
    myPark->numInCars = 0;                                    /* # in cars */
    myPark->numInGiftLine = 0;                                /* Gift shop line */
    myPark->numInGiftShop = 0;                                /* # in gift shop */
    myPark->numExitedPark = 0;                                /* # exited park */
    return;
}


void parkInterfaceTask(void *args) {
    PARAM* params = (PARAM *)args;
    JPARK* myPark = params->park;
    unsigned int passengerNodeID;
    //int visitors = params->number;
    int i, j, k;
    int exit;
    unsigned int c[NUM_CARS];
    
    unsigned int sendBuf;
    unsigned int recvBuf;
    size_t bufSize = sizeof(unsigned int);
    size_t recvSize;
    mcapi_status_t status;
    mcapi_version_t version;
    mcapi_endpoint_t interfaceEp;
    mcapi_endpoint_t moveCarsEp;
    mcapi_endpoint_t passengerRequestEp;
    mcapi_endpoint_t masterEp;
    mcapi_endpoint_t carEp[NUM_CARS];

    createNode(INTERFACE_NODE_ID, &version, &status);
    interfaceEp = createEndpoint(DEFAULT_PORT, &status);
    moveCarsEp = createEndpoint(MOVE_CARS_PORT, &status);
    
    sendBuf = INTERFACE_NODE_ID;

    passengerRequestEp = getEndpoint(PASSENGER_MANAGER_NODE_ID,REQUEST_PORT,&status);
    masterEp = getEndpoint(MASTER_NODE_ID,DEFAULT_PORT,&status);
    for (i = 0; i < NUM_CARS; i++) {
        carEp[i] = getEndpoint(CAR_BASE_NODE_ID+i, DEFAULT_PORT, &status);
    }

    // wait to move cars
    do {
        // wait for signal to move cars
        recvMessage(moveCarsEp, (void*)&recvBuf, bufSize, &recvSize, &status);
        if (recvBuf == SHUTDOWN_MSG) {
            break;
        }

        // order car locations
        for (i=0; i<NUM_CARS; i++) {
            c[i] = i;
        }

        // sort car positions
        for (i=0; i<(NUM_CARS-1); i++) {
            for (j=i+1; j<NUM_CARS; j++) {
                if (myPark->cars[c[i]].location < myPark->cars[c[j]].location) {
                    k = c[i];
                    c[i] = c[j];
                    c[j] = k;
                }
            }
        }

        // move each car if possible (or load or unload)
        for (i=0; i < NUM_CARS; i++) {
            // move car (-1=location occupied, 0=moved, 1=no move)
            if (makeMove(c[i], myPark)) {
                // check if car is loading
                if ((myPark->cars[c[i]].location == 33) &&
                    (myPark->cars[c[i]].passengers < NUM_SEATS) &&
                    (myPark->numInCarLine)) {
                    // Request a passenger and wait until you get a visitor ready to seat
                    sendBuf = INTERFACE_NODE_ID;
                    sendMessage(interfaceEp, passengerRequestEp, (void*)&sendBuf, bufSize, &status);
                    recvMessage(interfaceEp, (void*)&recvBuf, bufSize, &recvSize, &status);
                    if (recvBuf == SHUTDOWN_MSG) {
                        exit = 1;
                        break;
                    }
                    
                    passengerNodeID = recvBuf; // store node ID of ready visitor
                    sendBuf = passengerNodeID; // get ready to pass visitor ID on to corresponding car
                    // notify car that a visitor is ready to board (and pass along visitor node ID)
                    sendMessage(interfaceEp, carEp[c[i]], (void*)&sendBuf, bufSize, &status);

                    // Wait for car to tell you that it has successfully loaded a passenger
                    recvMessage(interfaceEp, (void*)&recvBuf, bufSize, &recvSize, &status);
                    assert(recvBuf == CAR_BASE_NODE_ID+c[i]);
                    myPark->cars[c[i]].passengers++;
                }

                // check if car is unloading
                if ((myPark->cars[c[i]].location == 30) &&
                    (myPark->cars[c[i]].passengers > 0)) {
                    // empty each seat until car is empty
                    myPark->numRidesTaken++;

                    // if car empty, signal ride over
                    if (--myPark->cars[c[i]].passengers == 0) {
                        sendBuf = INTERFACE_NODE_ID;
                        sendMessage(interfaceEp, carEp[c[i]], (void*)&sendBuf, bufSize, &status);
                    }
                }

                // sanity check
                if (    (myPark->cars[0].location == myPark->cars[1].location) ||
                        (myPark->cars[0].location == myPark->cars[2].location) ||
                        (myPark->cars[0].location == myPark->cars[3].location) ||
                        (myPark->cars[1].location == myPark->cars[2].location) ||
                        (myPark->cars[1].location == myPark->cars[3].location) ||
                        (myPark->cars[2].location == myPark->cars[3].location) ) {
                    printf("\nProblem:");
                    for (k=0; k<NUM_CARS; k++) printf(" %d", myPark->cars[k].location);
                    k=getchar();
                }
            }
        }
        if (exit) {
            break;
        }
    //} while (myPark->numExitedPark < visitors);
    } while(1);
    
    sendBuf = INTERFACE_NODE_ID;
    sendMessage(interfaceEp, masterEp, (void*)&sendBuf, bufSize, &status);
    if (PARKDEBUG) printf("parkInterfaceTask done\n");
    finalizeNode(&status);
}


int makeMove(int car, JPARK* myPark) {
    int i, j;
    int moved;

    switch (j = myPark->cars[car].location) {
            // at cross roads 1
        case 7:
        {
            j = (rand()%2) ? 24 : 8;
            break;
        }

            // at cross roads 2
        case 20:
        {
            // take shorter route if no one in line
            if (myPark->numInCarLine == 0)
            {
                j = 28;
            }
            else
            {
                j = (rand()%3) ? 28 : 21;
            }
            break;
        }

            // bridge loop
        case 23:
        {
            j = 1;
            break;
        }

            // bridge short route
        case 27:
        {
            j = 20;
            break;
        }

            // exit car
        case 30:
        {
            if (myPark->cars[car].passengers == 0)
            {
                j++;
            }
            break;
        }

            // loading car
        case 33:
        {
            // if there is someone in car and noone is in line, proceed
            //if ((myPark.cars[car].passengers == NUM_SEATS) ||
            //     ((myPark.numInCarLine == 0) && myPark.cars[car].passengers))

            // if car is full, proceed into park
            if (myPark->cars[car].passengers == NUM_SEATS)
            {
                j = 0;
            }
            break;
        }

            // all other moves
        default:
        {
            j++;
            break;
        }
    }
    // check to see if position is taken
    for (i=0; i<NUM_CARS; i++) {
        if (i != car) {
            if (myPark->cars[i].location == j) {
                return -1;
            }
        }
    }

    // return 0 if car moved
    moved = (myPark->cars[car].location == j);

    // make move
    myPark->cars[car].location = j;
    return moved;
}


void interfaceManagerTask(void * args) {
    unsigned int sendBuf = 0;
    unsigned int recvBuf = 0;
    size_t bufSize = sizeof(unsigned int);
    size_t recvSize;
    unsigned int requesterPort = DEFAULT_PORT;
    unsigned int requesterNodeID;
    mcapi_status_t status;
    mcapi_version_t version;
    mcapi_endpoint_t parkEp;
    mcapi_endpoint_t parkMutexEp;
    mcapi_endpoint_t requesterEp;
    mcapi_endpoint_t masterEp;

    createNode(DISPLAY_MANAGER_NODE_ID, &version, &status);
    parkEp = createEndpoint(REQUEST_PORT,&status);
    parkMutexEp = createEndpoint(RESOURCE_PORT,&status);
    masterEp = getEndpoint(MASTER_NODE_ID,DEFAULT_PORT,&status);
    
    sendBuf = DISPLAY_MANAGER_NODE_ID;
    
    sendMessage(parkEp, masterEp, (void*)&sendBuf, bufSize, &status);

    while (1) {
        // get message from node requesting access to interface
        recvMessage(parkEp, (void*)&recvBuf, bufSize, &recvSize, &status);
        if (recvBuf == SHUTDOWN_MSG) {
            break;
        }
        requesterNodeID = recvBuf;
        requesterEp = getEndpoint(requesterNodeID,requesterPort,&status);

        // wait for interface to be available
        recvMessage(parkMutexEp, (void*)&recvBuf, bufSize, &recvSize, &status);

        // park interface available, signal requester that he can go ahead
        sendMessage(parkEp, requesterEp, (void*)&sendBuf, bufSize, &status);
    }

    // Inform master node that you're done
    sendMessage(parkEp, masterEp, (void*)&sendBuf, bufSize, &status);
    
    if (PARKDEBUG) printf("interfaceManagerTask done\n");
    finalizeNode(&status);
}

void jurassicDisplayTask(void *args) {
    PARAM* params = (PARAM *)args;
    JPARK* myPark = params->park;
    JPARK currentPark;

    unsigned int sendBuf;
    unsigned int recvBuf;
    size_t bufSize = sizeof(unsigned int);
    size_t recvSize;
    mcapi_status_t status;
    mcapi_version_t version;
    mcapi_endpoint_t thisTaskEp;
    mcapi_endpoint_t parkResourceEp;
    mcapi_endpoint_t parkRequestEp;
    mcapi_endpoint_t moveCarsEp;
    mcapi_endpoint_t masterEp;

    // Initialize this MCAPI node
    createNode(DISPLAY_NODE_ID, &version, &status);
    thisTaskEp = createEndpoint(DEFAULT_PORT, &status);
    
    // Put endpoint node ID into sendBuffer;
    sendBuf = DISPLAY_NODE_ID;
    
    // Get references to all needed external endpoints
    parkRequestEp = getEndpoint(DISPLAY_MANAGER_NODE_ID,REQUEST_PORT,&status);
    parkResourceEp = getEndpoint(DISPLAY_MANAGER_NODE_ID,RESOURCE_PORT,&status);
    moveCarsEp = getEndpoint(INTERFACE_NODE_ID,MOVE_CARS_PORT,&status);
    masterEp = getEndpoint(MASTER_NODE_ID,DEFAULT_PORT,&status);

    // display park every second
    do {

        gem_sleep();

        // take snapshot of park
        sendMessage(thisTaskEp, parkRequestEp, (void*)&sendBuf, bufSize, &status); // request lock on park
        recvMessage(thisTaskEp, (void*)&recvBuf, bufSize, &recvSize, &status); // get lock on park
        if (recvBuf == SHUTDOWN_MSG) {
            break;
        }
        currentPark = *myPark;
        sendMessage(thisTaskEp, parkResourceEp, (void*)&sendBuf, bufSize, &status); // release lock on park

        // draw current park
        if (!PARKDEBUG) drawPark(&currentPark);

        // signal for cars to move
        sendMessage(thisTaskEp, moveCarsEp, (void*)&sendBuf, bufSize, &status);
    } while (1);

    printf("\nThank you for visiting Jurassic Park!!");
    sendMessage(thisTaskEp, masterEp, (void*)&sendBuf, bufSize, &status);
    if (PARKDEBUG) printf("jurassicDisplayTask done\n");
    finalizeNode(&status);
}


// Draw Jurassic Park
//          1         2         3         4         5         6         7
//01234567890123456789012345678901234567890123456789012345678901234567890
//                _______________________________________________________    0
//   Entrance    /            ++++++++++++++++++++++++++++++++++++++++++|    1
//              -             +o0o-o1o-o2o-o3o-o4o-o5o-o6o             +|    2
//          ## -             /+   /                       \            +|    3
//            /             o +  o     ************        o           +|    4
//   |********         ##>>33 + 23     *Cntrl Room*        7           +|    5
//   |*Ticket*              o +  o     * A=# D1=# *        o           +|    6
//   |*Booth *             32 +  |     * B=# D2=# *       / \          +|    7
//   |* T=#  * ##           o +  o     * C=# D3=# *      o   o8o-o9o   +|    8
//   |* P=## *             31 + 22     * D=# D4=# *    24           \  +|    9
//   |* S=#  *              o +  o     ************    o             o +|    10
//   |********         ##<<30 +   \                   /             10 +|    11
//   |                      o +    o21-o20-o27-o26-o25               o +|    12
//   |                       \+       /   \                          | +|    13
//   |                        +o29-o28     o                         o +|    14
//   |                        +++++++++++ 19                        11 +|    15
//   |                                  +  o                         o +|    16
//   |              ##     ##           +   \                       /  +|    17
//   |        ******\ /****\ /********  +    o  O\                 o   +|    18
//   |        *         *            *  +   18    \/|||\___       12   +|    19
//    \       *  Gifts  *   Museum   *  +    o     x   x           o   +|    20
//     -      *    #    *     ##     *  +     \                   /    +|    21
//   ## -     *         *            *  +      o17-o16-o15-o14-o13     +|    22
//       \    ************************  ++++++++++++++++++++++++++++++++|    23
//

#define D1Upper    13
#define D1Left    45
#define D1Lower    18
#define D1Right    53

#define D2Upper    4
#define D2Left    53
#define D2Lower    13
#define D2Right    57

void drawPark(JPARK *park) {
    static int direction1 = D1Left;
    static int dy1 = D1Lower;

    static int direction2 = D2Left;
    static int dy2 = D1Lower-9;

    int i, j;
    char svtime[64];                        // ascii current time
    char driver[] = {'T', 'z', 'A', 'B', 'C', 'D' };
    char buf[32];
    char pk[25][80];
    char cp[34][3] = {    {2, 29, 0}, {2, 33, 0}, {2, 37, 0}, {2, 41, 0},        /* 0-6 */
        {2, 45, 0}, {2, 49, 0}, {2, 53, 0},
        {4, 57, 1},                                                        /* 7 */
        {8, 59, 0}, {8, 63, 0},                                        /* 8-9 */
        {10, 67, 1}, {14, 67, 1}, {18, 65, 1},                    /* 10-12 */
        {22, 61, 0}, {22, 57, 0}, {22, 53, 0},                    /* 13-17 */
        {22, 49, 0}, {22, 45, 0},
        {18, 43, 1}, {14, 41, 1},                                    /* 18-19 */
        {12, 37, 0}, {12, 33, 0}, {8, 31, 1}, {4, 31, 1},    /* 20-23 */
        {8, 55, 3},                                                        /* 24 */
        {12, 49, 0}, {12, 45, 0}, {12, 41, 0},                    /* 25-27 */
        {14, 33, 0}, {14, 29, 0},                                    /* 28-29 */
        {10, 26, 1}, {8, 26, 1}, {6, 26, 1}, {4, 26, 1},    /* 30-33 */
    };

    strcpy(&pk[0][0],  "                ___Jurassic Park_______________________________________");
    strcpy(&pk[1][0],  "   Entrance    /            ++++++++++++++++++++++++++++++++++++++++++|");
    strcpy(&pk[2][0],  "              -             +---------------------------             +|");
    strcpy(&pk[3][0],  "           # -             /+   /                       \\            +|");
    strcpy(&pk[4][0],  "            /             | +  |     ************        |           +|");
    strcpy(&pk[5][0],  "   |********          # >>| +  |     *Cntrl Room*        |           +|");
    strcpy(&pk[6][0],  "   |*Ticket*              | +  |     * A=# D1=# *        |           +|");
    strcpy(&pk[7][0],  "   |*Booth *              | +  |     * B=# D2=# *       / \\          +|");
    strcpy(&pk[8][0],  "   |* T=#  * #            | +  |     * C=# D3=# *      /   -------   +|");
    strcpy(&pk[9][0],  "   |* P=#  *              | +  |     * D=# D4=# *     /           \\  +|");
    strcpy(&pk[10][0], "   |* S=#  *              | +  |     ************    /             | +|");
    strcpy(&pk[11][0], "   |********            <<| +   \\                   /              | +|");
    strcpy(&pk[12][0], "   |                      | +    -------------------               | +|");
    strcpy(&pk[13][0], "   |                       \\+       /   \\                          | +|");
    strcpy(&pk[14][0], "   |                        +-------     |                         | +|");
    strcpy(&pk[15][0], "   |                        +++++++++++  |                         | +|");
    strcpy(&pk[16][0], "   |                                  +  |                         | +|");
    strcpy(&pk[17][0], "   |                #      #          +   \\                       /  +|");
    strcpy(&pk[18][0], "   |        ******\\ /****\\ /********  +    |                     |   +|");
    strcpy(&pk[19][0], "   |        *         *            *  +    |                     |   +|");
    strcpy(&pk[20][0], "    \\       *  Gifts  *   Museum   *  +    |                     |   +|");
    strcpy(&pk[21][0], "     -      *         *            *  +     \\                   /    +|");
    strcpy(&pk[22][0], "    # -     *         *            *  +      -------------------     +|");
    strcpy(&pk[23][0], "       \\    ************************  ++++++++++++++++++++++++++++++++|");
    strcpy(&pk[24][0], "   Exit \\_____________________________________________________________|");
    //@ENABLE_SWAPS

    // output time
    sprintf(buf, "%s", myTime(svtime));
    memcpy(&pk[0][strlen(pk[0]) - strlen(buf)], buf, strlen(buf));

    // out number waiting to get into park
    sprintf(buf, "%d", park->numOutsidePark);
    memcpy(&pk[3][12 - strlen(buf)], buf, strlen(buf));

    // T=#, out number of tickets available
    sprintf(buf, "%d ", park->numTicketsAvailable);
    memcpy(&pk[8][8], buf, strlen(buf));

    // P=#, out number in park (not to exceed OSHA requirements)
    sprintf(buf, "%d ", park->numInPark);
    memcpy(&pk[9][8], buf, strlen(buf));

    // S=#, output guests completing ride
    sprintf(buf, "%d", park->numRidesTaken);
    memcpy(&pk[10][8], buf, strlen(buf));

    // out number in ticket line
    sprintf(buf, "%d ", park->numInTicketLine);
    memcpy(&pk[8][13], buf, strlen(buf));

    // out number in gift shop line
    sprintf(buf, "%d", park->numInGiftLine);
    memcpy(&pk[17][21 - strlen(buf)], buf, strlen(buf));

    // out number in museum line
    sprintf(buf, "%d", park->numInMuseumLine);
    memcpy(&pk[17][28 - strlen(buf)], buf, strlen(buf));

    // out number in car line
    sprintf(buf, "%d", park->numInCarLine);
    memcpy(&pk[5][23 - strlen(buf)], buf, strlen(buf));

    // out number in gift shop
    sprintf(buf, "%d ", park->numInGiftShop);
    memcpy(&pk[21][17], buf, strlen(buf));

    // out number in museum
    sprintf(buf, "%d ", park->numInMuseum);
    memcpy(&pk[21][29], buf, strlen(buf));

    // out number exited park
    sprintf(buf, "%d", park->numExitedPark);
    memcpy(&pk[22][5 - strlen(buf)], buf, strlen(buf));

    // cars
    for (i=0; i<NUM_CARS; i++)
    {
        sprintf(buf, "%d", park->cars[i].passengers);
        memcpy(&pk[6+i][42 - strlen(buf)], buf, strlen(buf));
    }

    // drivers
    for (i=0; i<NUM_DRIVERS; i++)
    {
        pk[6+i][46] = driver[park->drivers[i] + 1];
    }

    // output cars
    for (i=0; i<NUM_CARS; i++)
    {
        // draw car
        j = park->cars[i].location;
        switch (cp[j][2])
        {
                // horizontal
            case 0:
            {
                pk[(int)cp[j][0]][(int)cp[j][1]+0] = 'o';
                pk[(int)cp[j][0]][(int)cp[j][1]+1] = 'A'+i;
                pk[(int)cp[j][0]][(int)cp[j][1]+2] = 'o';
                break;
            }
                // vertical
            case 1:
            {
                pk[(int)cp[j][0]+0][(int)cp[j][1]] = 'o';

                //pk[cp[j][0]+1][cp[j][1]] = 'A'+i;
                //if ((park->cars[i].passengers > 0) && (park->cars[i].passengers < NUM_SEATS))
                if ((park->cars[i].passengers > 0) &&
                    ((j == 30) || (j == 33)))
                {
                    pk[(int)cp[j][0]+1][(int)cp[j][1]] = '0'+park->cars[i].passengers;
                }
                else pk[(int)cp[j][0]+1][(int)cp[j][1]] = 'A'+i;

                pk[(int)cp[j][0]+2][(int)cp[j][1]] = 'o';
                break;
            }
            case 2:
            {
                pk[cp[j][0]+0][cp[j][1]+0] = 'o';
                pk[cp[j][0]+1][cp[j][1]+1] = 'A'+i;
                pk[cp[j][0]+2][cp[j][1]+2] = 'o';
                break;
            }
            case 3:
            {
                pk[cp[j][0]+0][cp[j][1]-0] = 'o';
                pk[cp[j][0]+1][cp[j][1]-1] = 'A'+i;
                pk[cp[j][0]+2][cp[j][1]-2] = 'o';
                break;
            }
        }
    }

    // move dinosaur #1
    dy1 = dy1 + (rand()%3) - 1;
    if (dy1 < D1Upper) dy1 = D1Upper;
    if (dy1 > D1Lower) dy1 = D1Lower;

    if (direction1 > 0)
    {
        memcpy(&pk[dy1+0][direction1+4], "...  /O", 7);
        memcpy(&pk[dy1+1][direction1+0], "___/|||\\/", 9);
        memcpy(&pk[dy1+2][direction1+3], "x   x", 5);
        if (++direction1 > D1Right) direction1 = -direction1;
    }
    else
    {
        if ((rand()%3) == 1)
        {
            memcpy(&pk[dy1+0][-direction1+4], "...", 3);
            memcpy(&pk[dy1+1][-direction1+1], "__/|||\\___", 10);
            memcpy(&pk[dy1+2][-direction1+0], "O  x   x", 8);
        }
        else
        {
            memcpy(&pk[dy1+0][-direction1+0], "O\\  ...", 7);
            memcpy(&pk[dy1+1][-direction1+2], "\\/|||\\___", 9);
            memcpy(&pk[dy1+2][-direction1+3], "x   x", 5);
        }
        if (++direction1 > -D1Left) direction1 = -direction1;
    }

    // move dinosaur #2

    dy2 = dy2 + (rand()%3) - 1;
    if (dy2 < D2Upper) dy2 = D2Upper;
    if (dy2 > D2Lower) dy2 = D2Lower;
    dy2 = (dy2+9) >= dy1 ? dy1-9 : dy2;

    if (direction2 > 0)
    {
        memcpy(&pk[dy2+0][direction2+7], "_", 1);
        memcpy(&pk[dy2+1][direction2+6], "/o\\", 3);
        memcpy(&pk[dy2+2][direction2+4], "</ _<", 5);
        memcpy(&pk[dy2+3][direction2+3], "</ /", 4);
        memcpy(&pk[dy2+4][direction2+2], "</ ==x", 6);
        memcpy(&pk[dy2+5][direction2+3], "/  \\", 4);
        memcpy(&pk[dy2+6][direction2+2], "//)__)", 6);
        memcpy(&pk[dy2+7][direction2+0], "<<< \\_ \\_", 9);
        if (++direction2 > D2Right) direction2 = -direction2;
    }
    else
    {
        memcpy(&pk[dy2+0][-direction2+1], "_", 1);
        memcpy(&pk[dy2+1][-direction2+0], "/o\\", 3);
        memcpy(&pk[dy2+2][-direction2+0], ">_ \\>", 5);
        memcpy(&pk[dy2+3][-direction2+2], "\\ \\>", 4);
        memcpy(&pk[dy2+4][-direction2+1], "x== \\>", 6);
        memcpy(&pk[dy2+5][-direction2+2], "/  \\", 4);
        memcpy(&pk[dy2+6][-direction2+1], "(__(\\\\", 6);
        memcpy(&pk[dy2+7][-direction2+0], "_/ _/ >>>", 9);
        if (++direction2 > -D2Left) direction2 = -direction2;
    }

    // draw park
    //system("cls");
    printf("\n");
    for (i=0; i<25; i++) printf("\n%s", &pk[i][0]);
    printf("\n");

    // driver in only one place at a time
    for (i=0; i<(NUM_DRIVERS-1); i++)
    {
        if (park->drivers[i] > 0)
        {
            for (j=i+1; j<NUM_DRIVERS; j++)
            {
                printf("%d at %d and %d at %d\n", i, park->drivers[i], j, park->drivers[j]);
                assert("Driver Error" && (park->drivers[i] != park->drivers[j]));
            }
        }
    }
    fflush(stdout);

    return;
} // end drawPark

char* myTime(char* svtime) {
    time_t cTime;                            /* current time */
    time(&cTime);                            /* read current time */
    strcpy(svtime, asctime(localtime(&cTime)));
    svtime[strlen(svtime)-1] = 0;        /* eliminate nl at end */
    return svtime;
}

void randomWait(void * args) {
    //int waitTime = rand() % 5000000 + 1000000;
    gem_sleep();
}

void createNode(int nodeID, mcapi_version_t* version, mcapi_status_t* status) {
    mcapi_initialize(nodeID, version, status);
    if (*status != MCAPI_SUCCESS) {
        printf("Could not initialize node.  Error: ");
        switch(*status) {
            case MCAPI_ENO_INIT:
                printf("MCAPI_ENO_INIT");
                break;
            case MCAPI_INITIALIZED:
                printf("MCAPI_INITIALIZED");
                break;
            case MCAPI_ENODE_NOTVALID:
                printf("MCAPI_ENODE_NOTVALID");
                break;
            case MCAPI_EPARAM:
                printf("MCAPI_EPARAM");
                break;
            default:
                printf("Unknown error");
        }
        printf("\n");
        abort();
    }
    return;
}

void finalizeNode(mcapi_status_t* status) {
    mcapi_finalize(status);
    if (*status != MCAPI_SUCCESS) {
        printf("Could not finalize node.  Error: ");
        switch(*status) {
            case MCAPI_ENO_FINAL:
                printf("MCAPI_ENO_FINAL");
                break;
            case MCAPI_EPARAM:
                printf("MCAPI_EPARAM");
                break;
            default:
                printf("Unknown error");
        }
        printf("\n");
        abort();
    }
    return;
}

mcapi_endpoint_t createEndpoint(int port, mcapi_status_t* status) {
    mcapi_endpoint_t endpoint = mcapi_create_endpoint(port, status);
    if (*status != MCAPI_SUCCESS) {
        printf("Could not create endpoint (port: %d). Error: ",port);
        switch(*status) {
            case MCAPI_EPORT_NOTVALID:
                printf("MCAPI_EPORT_NOTVALID");
                break;
            case MCAPI_EENDP_ISCREATED:
                printf("MCAPI_EENDP_ISCREATED");
                break;
            case MCAPI_ENODE_NOTINIT:
                printf("MCAPI_ENODE_NOTINIT");
                break;
            case MCAPI_EENDP_LIMIT:
                printf("MCAPI_EENDP_LIMIT");
                break;
            case MCAPI_EEP_NOTALLOWED:
                printf("MCAPI_EEP_NOTALLOWED");
                break;
            case MCAPI_EPARAM:
                printf("MCAPI_EPARAM");
                break;
            default:
                printf("Unknown error");
        }
        printf("\n");
        abort();
    }
    return endpoint;
}

mcapi_endpoint_t getEndpoint(int nodeID, int port, mcapi_status_t* status) {
    mcapi_endpoint_t endpoint = mcapi_get_endpoint(nodeID, port, status);
    if (*status != MCAPI_SUCCESS) {
        printf("Could not get endpoint (node: %d, port: %d). Error: ",nodeID,port);
        switch(*status) {
            case MCAPI_EPORT_NOTVALID:
                printf("MCAPI_EPORT_NOTVALID");
                break;
            case MCAPI_ENODE_NOTVALID:
                printf("MCAPI_ENODE_NOTVALID");
                break;
            case MCAPI_EPARAM:
                printf("MCAPI_EPARAM");
                break;
            default:
                printf("Unknown error");
        }
        printf("\n");
        abort();
    }
    return endpoint;
}

void sendMessage(mcapi_endpoint_t src, mcapi_endpoint_t dest, char* msg, size_t msgSize, mcapi_status_t* status) {
    mcapi_msg_send(src,dest,msg,msgSize,DEFAULT_PRIORITY,status);
    if (*status != MCAPI_SUCCESS) {
        printf("Could not send message. Error: ");
        switch(*status) {
            case MCAPI_ENOT_ENDP:
                printf("MCAPI_ENOT_ENDP");
                break;
            case MCAPI_EMESS_LIMIT:
                printf("MCAPI_EMESS_LIMIT");
                break;
            case MCAPI_ENO_BUFFER:
                printf("MCAPI_ENO_BUFFER");
                break;
            case MCAPI_EPRIO:
                printf("MCAPI_EPRIO");
                break;
            case MCAPI_EPARAM:
                printf("MCAPI_EPARAM");
                break;
            default:
                printf("Unknown error");
        }
        printf("\n");
        abort();
    }
    return;
}

void recvMessage(mcapi_endpoint_t src, char* msgBuf, size_t bufSize, size_t* recvSize, mcapi_status_t* status) {
    mcapi_msg_recv(src,msgBuf,bufSize,recvSize,status);
    if (*status != MCAPI_SUCCESS) {
        printf("Could not recieve message. Error: ");
        switch(*status) {
            case MCAPI_ENOT_ENDP:
                printf("MCAPI_ENOT_ENDP");
                break;
            case MCAPI_ETRUNCATED:
                printf("MCAPI_ETRUNCATED");
                break;
            case MCAPI_EPARAM:
                printf("MCAPI_EPARAM");
                break;
            default:
                printf("Unknown error");
        }
        printf("\n");
        abort();
    }
    return;
}


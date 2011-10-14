/**
*	FSpec data type defintions and transition processing core.
*	
*	The data types represent each of the FSpec types, all inheriting from FSpecValue to allow the
*	use of polymorphism to cover for when the definite type is not obvious (or when the computation
*	was defined in a polymorphic manner).  The FSpec processing class has factory methods to for
*	each data type.  It may also keep cached copies of empty/identity values since they are used
*	for comparison frequently.
*	
*	The processing core implements a monitor locking system on top of PThreads.  There is one global
*	lock for the monitor and one global condition variable to release the lock when an active
*	transition (one that has been called with input) is not enabled (none of the guard conditions
*	are true).  When releasing the lock, notify all is called to allow active transitions to check
*	if they have become enabled.
*	
*	The FSpec class is a singleton.  The instance never needs to be accessed directly, only static
*	methods need to be used.  The instance just holds the lock and condition variable, as well as
*	references to threads started for always-active (background / daemon) transitions.
*	
*	@author Everett morse
*/
#ifndef _FSPEC_H_
#define _FSPEC_H_


/////////////////////// DATA TYPES ////////////////

class FSpecValue {
	
};

class FSpecBoolean : public FSpecValue {
	
};

class FSpecNumber : public FSpecValue {
	
};

class FSpecString : public FSpecValue {
	
};

class FSpecTuple : public FSpecValue {
	
};

class FSpecSet : public FSpecValue {
	
};

//////////////////// PROCESSING CORE //////////////////

class FSpec {
	private:
	//lock
	//cond
	//threads[]
	static FSpec *instance;
	
	FSpec() {
		//Init lock
		//Init cond
		//init daemon transition threads
	}
	
	~Fspec() {
		//Free lock, cond
		//End and free threads
	}
	
	static FSpec & getInstance() {
		if( instance == NULL )
			instance = new FSpec();
		return instance;
	}
	
	public:
	
	/* Data Type Methods */
	static FSpecBoolean Boolean(bool value);
	static FSpecNumber Number(double value);
	static FSpecString String(char *value);
	static FSpecTuple Tuple();
	static FSpecSet Set();
	
	/* Processing Methods */
	static void initialize();
	static void finalize();
	static void lock();
	static void wait();
	static void unlock();
	static void isRunning();
	
};


/////////////// MACROS ////////////////

#define FSPEC_FORALL(pattern, test)   FSpec::ForAll("pattern", lambda(q, test))
#define FSPEC_EXISTS(pattern, test)   FSpec::Exists("pattern", lambda(q, test))


#endif

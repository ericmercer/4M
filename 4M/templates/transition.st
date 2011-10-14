/**
 * Transition for $methodName$ in $fspecName$.
 */
void $fspecName$::$methodName$($parameters; separator=", "$) {
	bool ERROR = false;
	bool enabled = false;
	
	FSpec::lock();		//enter the monitor
	do {
		$temporaries$
		//Errors
		$errors; separator="\n"$
		
		//Rules
		$rules; separator="\n"$
		
		if( !enabled )
			FSpec::wait(); //release monitor, wait for other transitions to be processed
	} while(!enabled && FSpec::isRunning());
	FSpec::unlock();	//exit the monitor and notify all
}


/**
* Main header file for $fspecName$ API
*/
#ifndef __FSPEC_$fspecName$_H__
#define __FSPEC_$fspecName$_H__

#include "FSpec.h"


/**
 * Class encapsulating the direct translations of methods for the $fspecName$ API.
 */
class $fspecName$ {
	public:
	
	private:
	/* API State */
	$stateDecls; separator="\n"$
	
	
	public:
	/**
	 * Constructor.
	 * Initializes state variables to values given in fspec file.
	 */
	$fspecName$() {
		$stateInits; separator="\n"$
	}
	
	/* Transitions */
	$methodDecls; separator="\n"$
	
};

/* Wrappers */

$methodWrapperDecls; separator="\n"$

#endif



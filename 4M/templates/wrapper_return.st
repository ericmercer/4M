/** 
 * This one has a ResultAddr param, so translate that to a return value
 */
FSpecValue $fspecName$_$methodName$($parameters; separator=", "$) {
	FSpecValue result;
	$fspecName$::$methodName$($parameterNames; separator=", "$$comma$&result);
	return result;
}


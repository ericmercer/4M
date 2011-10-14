#include "gem_config.h"

#include <windows.h> 
#include <tchar.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <process.h>

/* Declared in API-specific code */
extern char* specPath;
extern char* serverPath;

static PROCESS_INFORMATION piProcInfo;
static HANDLE g_hChildStd_IN_Rd;
static HANDLE g_hChildStd_IN_Wr;
static HANDLE g_hChildStd_OUT_Rd;
static HANDLE g_hChildStd_OUT_Wr;

static void ErrorExit (LPSTR lpszMessage) { 
    fprintf(stderr, "ERROR: %s\n", lpszMessage); 
    exit(0); 
}

FILE ** start_server_process(void) {
    char rktPath[] = "racket";
    FILE ** serverIO = malloc(sizeof(FILE*)*2);
    int pipeIn, pipeOut;
    char * sFlag = GEM_SINGLE_MODE ? "-s" : "";
    char * dFlag = gemDebugEnabled() ? "-d" : "";
    // Start the racket process
	TCHAR szCmdline[512];
	sprintf(
        szCmdline, "%s %s %s %s %s %s",
        rktPath, serverPath, "-p", sFlag, dFlag, specPath
    );
	STARTUPINFO siStartInfo;
	BOOL bSuccess = FALSE; 
    SECURITY_ATTRIBUTES saAttr; 
	saAttr.nLength = sizeof(SECURITY_ATTRIBUTES); 
	saAttr.bInheritHandle = TRUE; 
	saAttr.lpSecurityDescriptor = NULL; 
	// Create a pipe for the child process's STDOUT. 
	if ( ! CreatePipe(&g_hChildStd_OUT_Rd, &g_hChildStd_OUT_Wr, &saAttr, 0) ) 
	  ErrorExit(TEXT("StdoutRd CreatePipe")); 
	// Ensure the read handle to the pipe for STDOUT is not inherited.
	if ( ! SetHandleInformation(g_hChildStd_OUT_Rd, HANDLE_FLAG_INHERIT, 0) )
	  ErrorExit(TEXT("Stdout SetHandleInformation")); 
	// Create a pipe for the child process's STDIN. 
	if (! CreatePipe(&g_hChildStd_IN_Rd, &g_hChildStd_IN_Wr, &saAttr, 0)) 
	  ErrorExit(TEXT("Stdin CreatePipe")); 
	// Ensure the write handle to the pipe for STDIN is not inherited. 
	if ( ! SetHandleInformation(g_hChildStd_IN_Wr, HANDLE_FLAG_INHERIT, 0) )
	  ErrorExit(TEXT("Stdin SetHandleInformation"));  
	// Create the child process.    
	// Set up members of the STARTUPINFO structure. 
	// This structure specifies the STDIN and STDOUT handles for redirection.
	ZeroMemory( &siStartInfo, sizeof(STARTUPINFO) );
	siStartInfo.cb = sizeof(STARTUPINFO); 
    siStartInfo.hStdError = GetStdHandle(STD_ERROR_HANDLE);
	siStartInfo.hStdOutput = g_hChildStd_OUT_Wr;
	siStartInfo.hStdInput = g_hChildStd_IN_Rd;
	siStartInfo.dwFlags |= STARTF_USESTDHANDLES;
	// Create the child process. 
	bSuccess = CreateProcess(NULL, 
	  szCmdline,     // command line 
	  NULL,          // process security attributes 
	  NULL,          // primary thread security attributes 
	  TRUE,          // handles are inherited 
	  0,             // creation flags 
	  NULL,          // use parent's environment 
	  NULL,          // use parent's current directory 
	  &siStartInfo,  // STARTUPINFO pointer 
	  &piProcInfo);  // receives PROCESS_INFORMATION 

	// If an error occurs, exit the application. 
	if ( ! bSuccess ) 
	  ErrorExit(TEXT("CreateProcess"));
	else 
	{
	  // Close handles to the child process and its primary thread.
	  // Some applications might keep these handles to monitor the status
	  // of the child process, for example. 
	  CloseHandle(piProcInfo.hProcess);
	  CloseHandle(piProcInfo.hThread);
	}
	// Return FILE * for standard in and out
	pipeIn = _open_osfhandle((intptr_t)g_hChildStd_IN_Wr, 0);
	pipeOut = _open_osfhandle((intptr_t)g_hChildStd_OUT_Rd, _O_RDONLY);
    serverIO[0] = _fdopen(pipeIn, "w");
    serverIO[1] = _fdopen(pipeOut, "r");
    return serverIO;
}

void wait_server_process() {
	WaitForSingleObject(piProcInfo.hProcess, INFINITE); // Wait for the server to turn off
}

double current_inexact_milliseconds() {
    struct timeval tv;
    if(gettimeofday(&tv,0)) {
        printf("*** ERROR in gettimeofday()\n");
    }
    return tv.tv_sec*1000 + tv.tv_usec/1000.;
}

/*
150117509-Mustafa Abdullah Hakkoz	
150116034-Enes Garip

Project2- MYSHELL
*/



#include <stdio.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <stdlib.h>
#include <sys/wait.h>
#include <signal.h>
#include <fcntl.h>
 


#define HIST_NUMBER 10
#define MAX_LINE 80 /* 80 chars per line, per command, should be enough. */
#define MAX_BG 100
#define CREATE_FLAGS (O_WRONLY | O_CREAT | O_APPEND)
#define CREATE_MODE (S_IRUSR | S_IWUSR | S_IRGRP | S_IROTH)

// A struct that contains information about path variables
typedef struct {
    char** pathArray;
    int size;
} pathStruct;


// current foreground PID
int currentFGPID;
int isProcessFG;



///////////////////////////////////////////////////////////////////////////////
//function that reads linux path and returns it as struct (array + arraysize)//
///////////////////////////////////////////////////////////////////////////////
pathStruct* readPath(){
	char* envPath = getenv("PATH");

	//copy PATH 2 times for securing it
	int len = strlen(envPath)+1;
	char *tempPath = malloc(len*sizeof(char));
	memcpy(tempPath, envPath, len);
	char *tempPath2 = malloc(len*sizeof(char));
	memcpy(tempPath2, envPath, len);

	//tokenize tempPath
	char *token=strtok(tempPath,":");
	if(token==NULL){
		perror("Error - can't read PATH environment variable.\n");
	}

	//count number of tokens in PATH. we will use this for malloc
	int numTokens = 0;
	while (token != NULL){
		numTokens++;
		token=strtok(NULL,":");
	}

	//Build array of paths
	char **pathArray = malloc(numTokens * MAX_LINE*sizeof(char*));

	//tokenize again by using tempPath2. this time copy content to array
	char *token2=strtok(tempPath2,":");
	int i = 0;
	while (token2 != NULL){
		pathArray[i] = token2;
		i++;
		token2 = strtok(NULL, ":");
	}

	//build a struct to return both array and arraysize
    pathStruct* result = (pathStruct*)malloc(sizeof(pathStruct));
    result->pathArray = pathArray;
    result->size = numTokens;

    // Then return it
    return result;
}



//////////////////////////////////////////
// search command file in array of paths//
//////////////////////////////////////////
char* searchInPaths(char* file, pathStruct* paths) {
    int i;
    for(i=0; i<paths->size; i++){
		//build executable path
        char *tempPath = malloc(1000*sizeof(char));
        snprintf(tempPath, strlen(paths->pathArray[i])+2+strlen(file), "%s/%s", paths->pathArray[i],file);

        //check if exist and cop
        if(access(tempPath, F_OK)==0){
			char *validPath = malloc(1000*sizeof(char));
			snprintf(validPath,strlen(tempPath)+1,"%s",tempPath);
			return validPath;
			}
    }
    return NULL;
}



////////////////////////////////////
//function for redirecion case ">"//
///////////////////////////////////
int redirect1(char* fileName) {
	int fd;
	fd = open(fileName, O_WRONLY | O_CREAT, CREATE_MODE);
	if (fd == -1) {
		fprintf(stderr,"Failed to open %s",fileName);
		return 1;
	}
	if (dup2(fd, STDOUT_FILENO) == -1) {
		perror("Failed to redirect standard output");
		return 1;
	}
	if (close(fd) == -1) {
		perror("Failed to close the file");
		return 1;
	}
	fprintf(stderr,"Output will be seen in %s\n",fileName);
	return 0;
}


////////////////////////////////////
//function for redirecion case ">>"//
///////////////////////////////////
int redirect2(char* fileName) {
	int fd;
	fd = open(fileName, O_APPEND | O_CREAT, CREATE_MODE);
	if (fd == -1) {
		fprintf(stderr,"Failed to open %s",fileName);
		return 1;
	}
	if (dup2(fd, STDOUT_FILENO) == -1) {
		perror("Failed to redirect standard output");
		return 1;
	}
	if (close(fd) == -1) {
		perror("Failed to close the file");
		return 1;
	}
	fprintf(stderr,"Output will be seen in %s\n",fileName);
	return 0;
}

////////////////////////////////////
//function for redirecion case "<"//
///////////////////////////////////
int redirect3(char* fileName) {
	int fd;
	fd = open(fileName, O_RDONLY, CREATE_MODE);
	if (fd == -1) {
		fprintf(stderr,"Failed to open %s",fileName);
		return 1;
	}
	if (dup2(fd, STDIN_FILENO) == -1) {
		perror("Failed to redirect standard input");
		return 1;
	}
	if (close(fd) == -1) {
		perror("Failed to close the file");
		return 1;
	}
	fprintf(stderr,"Input is from %s\n",fileName);
	return 0;
}


////////////////////////////////////
//function for redirecion case "2>"//
///////////////////////////////////
int redirect4(char* fileName) {
	int fd;
	fd = open(fileName, O_WRONLY | O_CREAT, CREATE_MODE);
	if (fd == -1) {
		fprintf(stderr,"Failed to open %s",fileName);
		return 1;
	}
	if (dup2(fd, STDERR_FILENO) == -1) {
		perror("Failed to redirect standard output");
		return 1;
	}
	if (close(fd) == -1) {
		perror("Failed to close the file");
		return 1;
	}
	
	fprintf(stderr,"Errors will be seen in %s\n",fileName);
	return 0;
}

////////////////////////////////////
//function for redirecion case "<" + ">"//
///////////////////////////////////
int redirect5(char* inputFileName,char* outputFileName) {
	int fdin;
	fdin = open(inputFileName, O_RDONLY, CREATE_MODE);
	int fdout;
	fdout = open(outputFileName, O_WRONLY | O_CREAT, CREATE_MODE);
	if (fdin == -1) {
		fprintf(stderr,"Failed to open %s",inputFileName);
		return 1;
	}
	if (fdout == -1) {
		fprintf(stderr,"Failed to open %s",outputFileName);
		return 1;
	}
	if (dup2(fdin, STDIN_FILENO) == -1) {
		perror("Failed to redirect standard input");
		return 1;
	}
	if (dup2(fdout, STDOUT_FILENO) == -1) {
		perror("Failed to redirect standard output");
		return 1;
	}
	if (close(fdin) == -1) {
		fprintf(stderr,"Failed to close %s",inputFileName);
		return 1;
	}
	if (close(fdout) == -1) {
		fprintf(stderr,"Failed to close %s",outputFileName);
		return 1;
	}
	fprintf(stderr,"Input is from %s\n",inputFileName);
	return 0;
	fprintf(stderr,"Output will be seen in %s\n",outputFileName);
	return 0;
}


//////////////////////////////
//function that Runs command//
//////////////////////////////
int runCommand(char** args, char* path, int* background, int* currentFGPID, pid_t bgArray[], int *currentIndex, int doneflags[],int *redirectionMode,char* inputFileName,char* outputFileName) {
    pid_t child_pid;
    pid_t wpid;
	int status;
	//doneflag=0;
	
    child_pid = fork();

    //Error
    if(child_pid == -1) {
        fprintf(stderr,"Error - Fork is interrupted.\n");
        return 1;
    }

    //if child
    if(child_pid == 0) {
		if(*redirectionMode==1)
			redirect1(outputFileName);
		if(*redirectionMode==2)
			redirect2(outputFileName);
		if(*redirectionMode==3)
			redirect3(inputFileName);
		if(*redirectionMode==4)
			redirect4(outputFileName);
		if(*redirectionMode==5)
			redirect5(inputFileName,outputFileName);
			
		execv(path, args);
		perror("Error - Execv returned unexpected result.\n");
    }

    //if parent
    //wait for child process if it runs on foreground
    if (*background == 0){
		*currentFGPID=child_pid;
		do {
			wpid = waitpid(child_pid, NULL, 0);
			} 
		while (!WIFEXITED(status) && !WIFSIGNALED(status));
		if(wpid==-1)
			fprintf(stderr,"Error - Parent failed to wait.\n");
	
	}

	if (*background == 1){
		bgArray[*currentIndex] = child_pid;
		*currentIndex = ((*currentIndex) + 1) % MAX_BG;
		 
		while (doneflags[*currentIndex]){
			wpid = waitpid(child_pid, NULL, 0);
		};
	}

	return 0;
}





/////////////////////////////////
//Given function that sets args//
/////////////////////////////////
/* The setup function below will not return any value, but it will just: read
in the next command line; separate it into distinct arguments (using blanks as
delimiters), and set the args array entries to point to the beginning of what
will become null-terminated, C-style strings. */

void setup(char inputBuffer[], char *args[],int *background)
{
    int length, /* # of characters in the command line */
        i,      /* loop index for accessing inputBuffer array */
        start,  /* index where beginning of next command parameter is */
        ct;     /* index of where to place the next parameter into args[] */

    ct = 0;

    /* read what the user enters on the command line */
    length = read(STDIN_FILENO,inputBuffer,MAX_LINE);
  
    /* 0 is the system predefined file descriptor for stdin (standard input),
       which is the user's screen in this case. inputBuffer by itself is the
       same as &inputBuffer[0], i.e. the starting address of where to store
       the command that is read, and length holds the number of characters
       read in. inputBuffer is not a null terminated C-string. */

    start = -1;
    if (length == 0)
        exit(0);            /* ^d was entered, end of user command stream */

/* the signal interrupted the read system call */
/* if the process is in the read() system call, read returns -1
  However, if this occurs, errno is set to EINTR. We can check this  value
  and disregard the -1 value */
    if ( (length < 0) && (errno != EINTR) ) {
        printf("error reading the command");
		exit(-1);           /* terminate with error code of -1 */
    }

	printf(">>%s<<",inputBuffer);
    for (i=0;i<length;i++){ /* examine every character in the inputBuffer */

        switch (inputBuffer[i]){
	    case ' ':
	    case '\t' :               /* argument separators */
			if(start != -1){
	            args[ct] = &inputBuffer[start];    /* set up pointer */
			    ct++;
			}
	        inputBuffer[i] = '\0'; /* add a null char; make a C string */
			start = -1;
			break;

        case '\n':                 /* should be the final char examined */
			if (start != -1){
	            args[ct] = &inputBuffer[start];
			    ct++;
			}
	        inputBuffer[i] = '\0';
	        args[ct] = NULL; /* no more arguments to this command */
			break;

	    default :             /* some other character */
			if (start == -1)
		    	start = i;
            if (inputBuffer[i] == '&'){
		    	*background  = 1;
                inputBuffer[i-1] = '\0';
			}
		} /* end of switch */
    }    /* end of for */
    args[ct] = NULL; /* just in case the input line was > 80 */

	for (i = 0; i <= ct; i++)
		printf("args %d = %s\n",i,args[i]);
} /* end of setup routine */



///////////////////////////////////////////////
//Modified version of readPath for tokenizing//
///////////////////////////////////////////////

char ** tokenize(char inputBuffer[])
{
    //copy buffer 2 times for securing it
	int len = strlen(inputBuffer)+1;
	char *tempPath = malloc(len*sizeof(char));
	memcpy(tempPath, inputBuffer, len);
	char *tempPath2= malloc(len*sizeof(char));
	memcpy(tempPath2, inputBuffer, len);

	//tokenize tempPath
	char *token=strtok(tempPath," ");
	if(token==NULL){
		perror("Error - can't read buffer.\n");
	}

	//count number of tokens in PATH. we will use this for malloc
	int numTokens = 0;
	while (token != NULL){
		numTokens++;
		token=strtok(NULL," ");
	}

	//Build array of paths
	char **args = malloc(numTokens * MAX_LINE*sizeof(char*));

	//tokenize again by using tempPath2. this time copy content to array
	char *token2=strtok(tempPath2," ");
	int i = 0;
	while (token2 != NULL){
		args[i] = token2;
		i++;
		token2 = strtok(NULL, " ");
	}
	return args;
} 



/////////////////////////////////////////////
/////function that runs for command "path"///
/////////////////////////////////////////////
int runPath(int argCount,char** args, int* background, pathStruct* paths){


	//without arguments
	if (argCount==1){
		int i;
		for(i=0;i<paths->size;i++){
			if(i==(paths->size)-1)
				printf("%s",paths->pathArray[i]);
			else
				printf("%s:", paths->pathArray[i]);
		}
		return 0;
	}
	else if(argCount==3){
		//with argument "+"
		if(strcmp(args[1],"+")==0){
			//Build array of paths
			char **tempArray = malloc(((paths->size)+1) * MAX_LINE*sizeof(char*));
			int i;
			for (i=0;i<paths->size;i++){
				tempArray[i] = paths->pathArray[i];
			}
			tempArray[paths->size]=args[2];
			tempArray[paths->size+1]=NULL;
			//Build a temp struct then assign to argument "paths"
			pathStruct* result = (pathStruct*)malloc(sizeof(pathStruct));
			result->pathArray = tempArray;
			result->size = (paths->size)+1;
			*paths=*result;
			return 0;
		}
		//with argument "-"
		else if(strcmp(args[1],"-")==0){
			//Build array of paths
			char **tempArray = malloc((paths->size) * MAX_LINE*sizeof(char*));
			int i,j=0;
			for (i=0;i<paths->size;i++){
				if(strcmp(paths->pathArray[i],args[2])==0){
					j++;
				}
				else{
					tempArray[i-j] = paths->pathArray[i];
				}
			}
			tempArray[paths->size-j]=NULL;
			//Build a temp struct then assign to argument "paths"
			pathStruct* result = (pathStruct*)malloc(sizeof(pathStruct));
			result->pathArray = tempArray;
			result->size = paths->size-j;
			*paths=*result;
			return 0;
		}
		else {
			fprintf(stderr,"Error - supported options for command <path> are <+> or <->");
			return -1;
		}
	}
	else{
		fprintf(stderr,"Error - Valid number of arguments for command <path> is 0 or 2");
		return -1;
}
}


//////////////////////////////
//function that Runs history//
//////////////////////////////
int history(char *hist[], int current,int argCount,char** args,int* background,pathStruct* paths, pid_t bgArray[], int *currentIndex, int doneflags[],int *redirectionMode,char* inputFileName,char* outputFileName)
{
	if (argCount==1){
        int i = current;
        int hist_num = 0;
        do {
                if (hist[i]) {
                        printf("%5d  %s\n", hist_num, hist[i]);
                        hist_num++;
                }
                i = (i + 1) % HIST_NUMBER;
        } while (i != current);
        return 0;
	}
	else if(argCount==3){
		//with argument "-i"
		if(strcmp(args[1],"-i")==0){
			int index;
			if(atoi(args[2])<10 && atoi(args[2])>=0)
				index=atoi(args[2]);
			else{
				fprintf(stderr,"Error - Use integer between 0-10 after option <-i>");
				return -1;
			}
			
			//choose a command from hist array
			char wholeCommand[MAX_LINE];
			snprintf(wholeCommand, strlen(hist[index])+1, "%s", hist[index]);
			//tokenize command
			char **tempArgs;
			tempArgs=tokenize(wholeCommand);

			//find path of command
			char* pathOfCommand;
			pathOfCommand = searchInPaths(tempArgs[0],paths);
			//execute command
			if (pathOfCommand!=NULL) {
				//int success;
				runCommand(tempArgs, pathOfCommand, background, &currentFGPID, bgArray, currentIndex, doneflags,redirectionMode,inputFileName,outputFileName);
				return 0; 
			}
			else{
				fprintf(stderr,"%s: Command is not found in system PATH variable.\n", tempArgs[0]);		
				return -1;
			}
		}
		else{
			fprintf(stderr,"Error - Only supported option for command <history> is <-i>");
			return -1;
		}
	}
	else{
		fprintf(stderr,"Error - Valid number of arguments for command <path> is 0 or 2");
		return -1;
	}
}


////////////////////////
/////signal handler/////
////////////////////////
static void catchCtrlZ(int signo) {
	if(isProcessFG) {
        kill(currentFGPID, SIGKILL);
	}
}




/////////////////////////////////////////////
/////function that runs for command "fg"///
/////////////////////////////////////////////
int runFg(int argCount,char** args, int* background, pid_t bgArray[],int *currentIndex,int doneflags[]){
	if (argCount==2){
		pid_t pid=atoi(args[1]);
		int i;
		for(i=0;i<MAX_BG;i++){
			if(bgArray[i]==pid){
				doneflags[*currentIndex]=1;
				printf("process is moved to FG");
				*background=0;
				*currentIndex = ((*currentIndex) - 1) % MAX_BG;
				isProcessFG=1;
				waitpid(atoi(args[1]), NULL, 0);
				
			}
		}		
		return 0;
	}
	else{
		fprintf(stderr,"Error - Valid number of argument for command <fg> is 1. Syntax: <fg> <pid> ");
		return -1;
	}
}


/////////////////////////////////////////////
/////function that runs for command "exit"///
/////////////////////////////////////////////
int runExit(int *currentIndex){
	if(*currentIndex==0){
		printf("shell is closing...");
        exit(0);
	}
	else{
		fprintf(stderr, "Error - Can't exist. There are some background processes. Type <ps> to see their pid, then use <fg> <pid> and ctr+z to close them.\n");
		// return to get new commands
		return 0;
	}
}


///////////////////////
//////main function////
///////////////////////
int main(void)
{
	int i,current=0, currentIndex=0, index1, index2, index3, index4, redirectionMode;
	char inputBuffer[MAX_LINE]; /*buffer to hold command entered */
	int background; /* equals 1 if a command is followed by '&' */
	char *args[MAX_LINE/2 + 1]; /*command line arguments */
	int numArgs;
	char inputFileName[MAX_LINE];
	inputFileName[0]='\0';
	char outputFileName[MAX_LINE];
	outputFileName[0]='\0';
	char buffer[MAX_LINE];
	char *hist[HIST_NUMBER];
	for (i = 0; i < HIST_NUMBER; i++)
			hist[i] = NULL;
	pid_t bgArray[MAX_BG];
	for (i = 0; i < MAX_BG; i++)
			bgArray[i] = 0;
			
	int doneflags[MAX_BG];
	for (i = 0; i < MAX_BG; i++) 
		doneflags[i] = 0;

	//Read PATH environment variable
	pathStruct* paths = (pathStruct*)malloc(sizeof(pathStruct));
	paths=readPath();

	// set up signal handler
	struct sigaction act;
	act.sa_handler = catchCtrlZ;	
	act.sa_flags = 0;
	if ((sigemptyset(&act.sa_mask) == -1) || (sigaction(SIGTSTP, &act, NULL) == -1)) {
		perror("Failed to set SIGTSTP handler");
		return 1;
	}
	
	
	while (1){
		numArgs = 0;
		background = 0;
		isProcessFG=0;
		currentFGPID=0;
		index1=0;
		index2=0;
		index3=0;
		index4=0;
		redirectionMode=0;
		printf("\nmyshell: ");
		/*setup() calls exit() when Control-D is entered */
		setup(inputBuffer, args, &background);
		/** the steps are:
		(1) fork a child process using fork()
		(2) the child process will invoke execv()
		(3) if background == 0, the parent will wait,
		otherwise it will invoke the setup() function again. */
		
		
		
		
		//continue if user hit enter
		if(args[0]==NULL)
			continue;

		//count of args
		int i=0;
		while(args[i] != NULL) {
			numArgs++;
			i++;
		}
		
		//build a buffer that holds current command
		strcpy(buffer, args[0]);
		int a;
		for(a=1;a<numArgs;a++){
			strcat(buffer, " ");
			strcat(buffer, args[a]);
		}
		buffer[strlen(buffer)] = '\0';

		//Check if args contains "&"
		int j;
		for(j=0;j<numArgs;j++){
			if(strcmp(args[j],"&")==0){
				background=1;
				isProcessFG=0;
				args[j]=NULL;
				numArgs--;
			}
			else
				isProcessFG=1;
		}
		
		//Check if args contains ">", ">>", "<" or "2>"
		int k;
		for(k=0;k<numArgs;k++){
			if(strcmp(args[k],">")==0)
				index1=k;	
			if(strcmp(args[k],">>")==0)
				index2=k;
			if(strcmp(args[k],"<")==0)
				index3=k;
			if(strcmp(args[k],"2>")==0)
				index4=k;
		}
		
		//execute redirects	
		if(index1>0 && index3==0){ //case ">"
			strcpy(outputFileName,args[index1+1]); 
			outputFileName[strlen(outputFileName)] = '\0';
			args[index1]=NULL;
			numArgs=numArgs-index1;
			redirectionMode=1;
		}
		
		if(index2>0){ //case ">>"
			strcpy(outputFileName,args[index2+1]); 
			outputFileName[strlen(outputFileName)] = '\0';
			args[index2]=NULL;
			numArgs=numArgs-index2;
			redirectionMode=2;
		}
		
		if(index3>0 && index1==0){ //case "<"
			strcpy(inputFileName,args[index3+1]); 
			inputFileName[strlen(inputFileName)] = '\0';
			args[index3]=NULL;
			numArgs=numArgs-index3;
			redirectionMode=3;
		}
		
		if(index4>0){ //case "2>"
			strcpy(outputFileName,args[index4+1]); 
			outputFileName[strlen(outputFileName)] = '\0';
			args[index4]=NULL;
			numArgs=numArgs-index4;
			redirectionMode=4;
		}
		
		if(index3>0 && index1>0){ //case "<" + ">"
			strcpy(inputFileName,args[index3+1]); 
			inputFileName[strlen(inputFileName)] = '\0';
			strcpy(outputFileName,args[index1+1]); 
			outputFileName[strlen(outputFileName)] = '\0';
			args[index3]=NULL;
			numArgs=numArgs-index3;
			redirectionMode=5;
		}
		
		
		
		
		//check for command "history"
		free(hist[current]);
		if (strcmp(args[0], "history") != 0){
			hist[current] = strdup(buffer);
			current = (current + 1) % HIST_NUMBER;
		}
		else{
			history(hist, current,numArgs,args,&background,paths,bgArray, &currentIndex, doneflags,&redirectionMode,inputFileName,outputFileName);
			continue;
		}
		
		//check for command "path"
		if(strcmp(args[0],"path")==0){
			int result=runPath(numArgs,args, &background, paths);
			if(result==0){}
			else{
				fprintf(stderr,"\nError - can't run command: ");
				int i;
				for(i=0;i<numArgs;i++)
					fprintf(stderr,"%s ",args[i]);
			}
			continue;
		}

		//check for command "fg"
		if(strcmp(args[0],"fg")==0){
			int result=runFg(numArgs,args, &background,bgArray, &currentIndex,doneflags);
			if(result==0){}
			else{
				fprintf(stderr,"\nError - can't run command: ");
				int i;
				for(i=0;i<numArgs;i++)
					fprintf(stderr,"%s ",args[i]);
			}
			continue;
		}

		//check for command "exit"
		if(strcmp(args[0],"exit")==0){
			int result=runExit(&currentIndex);
			if(result==0){}
			else{
				fprintf(stderr,"\nError - can't run command: ");
				int i;
				for(i=0;i<numArgs;i++)
					fprintf(stderr,"%s ",args[i]);
			}
			continue;
		}

		// search other commands in PATH
		char* pathOfCommand;
		pathOfCommand = searchInPaths(args[0],paths);

		//execute command
		if (pathOfCommand!=NULL) {		
			runCommand(args, pathOfCommand, &background, &currentFGPID, bgArray, &currentIndex, doneflags,&redirectionMode,inputFileName,outputFileName);
		}
		else
			fprintf(stderr,"%s: Command is not found in system PATH variable.\n", args[0]);

					

		}
}

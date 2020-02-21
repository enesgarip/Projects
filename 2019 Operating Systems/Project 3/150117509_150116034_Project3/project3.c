/*
150117509-Mustafa Abdullah Hakkoz	
150116034-Enes Garip

Project3
*/

//compile: gcc -o project3 project3.c -lpthread
//run:  ./project3 -d test.txt -n 5 4 3 2



#include <stdio.h>
#include <pthread.h> 
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <semaphore.h>

#define MAXLINE 100
#define MAXCHAR 200
#define MAXNAME 100



typedef struct
{
   char* content;
   int index;
   int read;
   int upper;
   int replace;
   int write;
} LINEINFO; 	//struct that holds info about a single line
	

//GLOBAL VARIABLES
int currentReadIndex=0;
LINEINFO lines[MAXLINE];
char filename[MAXNAME];
int readcount=0;


//MUTEX AND SEMAPHORES
pthread_mutex_t readLock = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t upperLock = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t replaceLock = PTHREAD_MUTEX_INITIALIZER;
pthread_mutex_t writeLock = PTHREAD_MUTEX_INITIALIZER;


//HANDLER FOR READ THREADS
void *readLine(void *arg){  	
	int id;
	id = *((int*)arg);
	
	char* tempStr;
	tempStr=malloc(MAXCHAR*sizeof(char));
	
	FILE *fp;
	fp =fopen(filename,"r");
	int i=0;
	while (1) {		
			if (fgets(tempStr,MAXCHAR,fp) == NULL) break;		
			if(lines[i].read==0){
				pthread_mutex_trylock (&readLock);	// lock mutex
				strcpy(lines[i].content,tempStr);
				lines[i].read=1;
				readcount++;
				sleep(1);
				printf("Read_%d \t Read_%d read the line %d which is “%s”\n", id, id, i+1, lines[i].content);
				pthread_mutex_unlock (&readLock);// unlock mutex
			}
			i++;
	}
	fclose(fp);
	free(tempStr);
    pthread_exit(0); /* exit thread */
}

//convert string to uppercase
char* toUpper(char Str[]){
	int i;
	for (i = 0;Str[i]!='\0';i++){
  		if(Str[i]>='a' && Str[i]<='z'){
  			Str[i] = Str[i] -32;
		}
  	}
	return Str;
}


//HANDLER FOR UPPER THREADS
void *upperCase(void *arg){
	int id;
	id = *((int*)arg);
	
	char tempStr[MAXCHAR];
	
	while(readcount){
		int i=0;
		while (i<MAXLINE) {
			if(lines[i].read==1){ 
				strcpy(tempStr,toUpper(lines[i].content));
				if(lines[i].upper==0){
					pthread_mutex_trylock (&upperLock);	// lock mutex
					printf("Upper_%d \t Upper_%d read index %d and converted from %s to “%s”\n", id, id, i+1, lines[i].content, tempStr);
					strcpy(lines[i].content,tempStr);
					lines[i].upper=1;
					pthread_mutex_unlock (&upperLock);// unlock mutex
				}
			}
			i++;
		}
	}
	pthread_exit(0); /* exit thread */
}


//convert spaces to underscores
char* spaceToUnderscore(char Str[]){
	int i;
	for (i = 0;Str[i]!='\0';i++){
  		if(Str[i]==' '){
  			Str[i] = '_';
		}
  	}
	return Str;
}

//HANDLER FOR REPLACE THREADS
void *replace(void *arg){
	int id;
	id = *((int*)arg);
	
	char tempStr[MAXCHAR];
	
	while(readcount){
		int i=0;
		while (i<MAXLINE) {
			if(lines[i].read==1){ 
				strcpy(tempStr,spaceToUnderscore(lines[i].content));
				if(lines[i].replace==0){
					pthread_mutex_trylock (&replaceLock);	// lock mutex
					printf("Replace_%d \t Replace_%d read index %d and converted from %s to “%s”\n", id, id, i+1, lines[i].content, tempStr);
					strcpy(lines[i].content,tempStr);
					lines[i].replace=1;
					pthread_mutex_unlock (&replaceLock);// unlock mutex
				}
			}
			i++;
		}
	}
	pthread_exit(0); /* exit thread */
}

//finds a specific line index and modify it
void* writeLine(int index, char* str){
	FILE *fp;
	fp =fopen(filename,"r+");
	
	char tempStr[MAXCHAR];
	long cursor;
	
	int i;
	for(i=0;i<index;i++){
		fgets(tempStr,MAXCHAR,fp);
		cursor = ftell(fp);                      
	}
	cursor-=strlen(tempStr)-1;
	fseek(fp,cursor,SEEK_CUR);
	fputs(str,fp);            
	fclose(fp);
}



//HANDLER FOR WRITER THREADS
void* writeToFile(void *arg){
	int id;
	id = *((int*)arg);
	
	while(1){
		int i=0;
		while (i<MAXLINE) {
			if(lines[i].replace && lines[i].upper){ 
				if(lines[i].write==0){
					pthread_mutex_trylock (&writeLock);	// lock mutex
					printf("Writer_%d \t Writer_%d write line %d back which is %s”\n", id, id, i+1, lines[i].content);
					writeLine(i,lines[i].content);
					lines[i].write=1;
					pthread_mutex_unlock (&writeLock);// unlock mutex
				}
			}
			i++;
		}
	}
	pthread_exit(0); /* exit thread */
}

int main(int argc, char **argv){
	// error cases:
	//invalid count of arguments
	if (argc != 8){  
      fprintf (stderr, "invalid number of arguments. Usage: ./project3 -d <filename> -n <#readthreads> <#upperthreads> <#replacethreads> <#writethreads>\n");
      return 1;
	}
	
	//invalid first arg 
	if(strcmp(argv[1],"-d")==0){
		strcpy(filename, argv[2]);
		FILE *fp;
		fp = fopen(filename, "r");
		if (fp == NULL){
			fprintf(stderr, "Could not open file %s\n",argv[2]);
			return 1;
		}
	}
	else{
		fprintf(stderr, "first argument should be <-d>. Usage: ./project3 -d <filename> -n <#readthreads> <#upperthreads> <#replacethreads> <#writethreads>\n");
		return 1;
	}
	
	//invalid third arg
	int numOfReadThreads;
	int numOfUpperThreads;
	int numOfReplaceThreads;
	int numOfWriteThreads;		
    if(strcmp(argv[3],"-n")==0){
		if(atoi(argv[4])>0)
			numOfReadThreads = atoi(argv[4]);
		else if(atoi(argv[5])>0)
			numOfUpperThreads = atoi(argv[5]);
		else if(atoi(argv[6])>0)
			numOfReplaceThreads = atoi(argv[6]);
		else if(atoi(argv[7])>0)
			numOfWriteThreads = atoi(argv[7]);
		else{
			fprintf(stderr, "number of threads should be greater than zero.");
			return 1;
		}
	}
	else{
		fprintf(stderr, "third argument should be <-n>. Usage: ./project3 -d <filename> -n <#readthreads> <#upperthreads> <#replacethreads> <#writethreads>\n");
		return 1;
	}
	
	//memory allocations for array of struct
	int x;
	for (x = 0; x < MAXLINE; x++){
        lines[x].content=(char*)malloc(MAXCHAR*sizeof(char));
        lines[x].index=x;
        lines[x].read=0;
        lines[x].upper=0;
        lines[x].replace=0;
        lines[x].write=0;      
	}
	
	
	//initialize read threads
	pthread_t readThreads[numOfReadThreads];
	int idr[numOfReadThreads];
	int i;
	for(i=0;i<numOfReadThreads;i++)
		idr[i]=i;
	for(i=0;i<numOfReadThreads;i++)
      pthread_create (&readThreads[i],NULL,&readLine,(void *)&idr[i]); //create threads
      
      
      //initialize upper threads
	pthread_t upperThreads[numOfUpperThreads];
	int idu[numOfUpperThreads];
	for(i=0;i<numOfUpperThreads;i++)
		idu[i]=i;
	for(i=0;i<numOfUpperThreads;i++)
      pthread_create (&upperThreads[i],NULL,&upperCase,(void *)&idu[i]); //create threads
      
        //initialize replace threads
	pthread_t replaceThreads[numOfReplaceThreads];
	int idrep[numOfReplaceThreads];
	for(i=0;i<numOfReplaceThreads;i++)
		idrep[i]=i;
	for(i=0;i<numOfReplaceThreads;i++)
      pthread_create (&replaceThreads[i],NULL,&replace,(void *)&idrep[i]); //create threads
      
      //initialize replace threads
	pthread_t writeThreads[numOfWriteThreads];
	int idw[numOfWriteThreads];
	for(i=0;i<numOfWriteThreads;i++)
		idw[i]=i;
	for(i=0;i<numOfWriteThreads;i++)
      pthread_create (&writeThreads[i],NULL,&writeToFile,(void *)&idw[i]); //create threads
      
      
	//join all threads
	for(i=0;i<numOfWriteThreads;i++)
	  pthread_join(writeThreads[i],NULL); //wait for the termination of the threads
	  
	for(i=0;i<numOfReplaceThreads;i++)
	  pthread_join(replaceThreads[i],NULL); //wait for the termination of the threads
	  
	for(i=0;i<numOfUpperThreads;i++)
	  pthread_join(upperThreads[i],NULL); //wait for the termination of the threads
	  
	for(i=0;i<numOfReadThreads;i++)
	  pthread_join(readThreads[i],NULL); //wait for the termination of the threads
	  
	
	return 0;
}




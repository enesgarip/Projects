#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <locale.h>
#include <math.h>

#define MAXCHAR 1000

struct listNode {
    int econ;
    int health;
    int magazin;
    int check;
    char term[MAXCHAR];
    struct listNode *nextPtr;
};

typedef struct listNode ListNode;
typedef ListNode *ListNodePtr;
void insert(ListNodePtr *sPtr, char *value,char *cat);
void printList(ListNodePtr currentPtr);
int isEmpty(ListNodePtr sPtr);
int isDuplicate(ListNodePtr sPtr,char *str,char *cat);
void part_b (ListNodePtr startPtr);
void part_a(ListNodePtr startPtr);


int main() {
    setlocale(LC_ALL, "Turkish");

    FILE *fp;

    ListNodePtr startPtr=NULL;

    int i=1;

    char str[MAXCHAR];
    char str2[15];
    char e[]="econ";
    char h[]="health";
    char m[]="magazin";



        while(1){

            sprintf(str2,"smalldataset\\%s\\%d.txt",e,i);
            fp = fopen(str2, "r");
            i++;

            while (fscanf(fp, "%s" ,str) != EOF){
                if(isDuplicate(startPtr,str,e))
                    insert(&startPtr,str,e);
            }
            fclose(fp);
            if(fp==NULL) break;
        }

        i=1;

        while(1){
            sprintf(str2,"smalldataset\\%s\\%d.txt",h,i);
            fp = fopen(str2, "r");
            i++;

            while (fscanf(fp, "%s" ,str) != EOF){
                if(isDuplicate(startPtr,str,h))
                    insert(&startPtr,str,h);

            }
            fclose(fp);
            if(fp==NULL) break;
        }

        i=1;

        while(1){
            sprintf(str2,"smalldataset\\%s\\%d.txt",m,i);
            fp = fopen(str2, "r");
            i++;

            while (fscanf(fp, "%s" ,str) != EOF){
                if(isDuplicate(startPtr,str,m))
                    insert(&startPtr,str,m);

            }
            fclose(fp);
            if(fp==NULL) break;
        }
            part_b(startPtr);

            printf("\n\n ----------------------- \n\n");

            printf("\n\n\n");

            printList(startPtr);

            printf("\n\n\n ----------------------- \n\n");

            printf("\n\n\n The Big O of the program is O(n^2)\n\n\n\n\n");

            part_a(startPtr);



}

void insert(ListNodePtr *sPtr, char *value,char *cat){

            ListNodePtr newPtr=malloc(sizeof(ListNode));


            strcpy(newPtr->term,value);
            newPtr->econ=0;
            newPtr->health=0;
            newPtr->magazin=0;
            if (strcmp("econ",cat)==0)
                newPtr->econ=1;
            if (strcmp("health",cat)==0)
                newPtr->health=1;
            if (strcmp("magazin",cat)==0)
                newPtr->magazin=1;
            newPtr->check = 0;

            newPtr->nextPtr=NULL;


            ListNodePtr previousPtr = NULL;
            ListNodePtr currentPtr= *sPtr;


            while(currentPtr !=NULL ){

                previousPtr=currentPtr;

                currentPtr=currentPtr->nextPtr;

            }



            if(previousPtr==NULL){

            newPtr->nextPtr=*sPtr;

            *sPtr=newPtr;

            }

            else{

            previousPtr->nextPtr=newPtr;

            newPtr->nextPtr=currentPtr;

            }

}





void printList(ListNodePtr currentPtr){
    int k=0;

        if(isEmpty(currentPtr)){

            puts("List is empty.\n");

        }

        else{

            puts("The linked list is: ");

            while(currentPtr != NULL) {

                k++;

                printf("%d.%s(%d%d%d) --> ",k,currentPtr->term,currentPtr->econ,currentPtr->health,currentPtr->magazin);

                currentPtr=currentPtr->nextPtr;

            }

        puts("NULL\n");

        }

}

int isEmpty(ListNodePtr sPtr){

        return sPtr=NULL;

}



int isDuplicate(ListNodePtr sPtr,char *str, char *cat){

        while(sPtr!=NULL){
            if(strcmp(sPtr->term, str) == 0) {
                if (strcmp("econ",cat)== 0) {
                    sPtr->econ++;
                }
                else if (strcmp("health",cat)== 0) {
                    sPtr->health++;
                }
                else if (strcmp("magazin,",cat)== 0) {
                    sPtr->magazin++;
                }
                return 0;
            }
            sPtr=sPtr->nextPtr;
        }

    return 1;

}

void part_b (ListNodePtr startPtr){
        ListNodePtr tempPtr;
        ListNodePtr iterPtr;
        printf("MOST FREQUENTS OF ECON\n\n");
        int i;
        for(i = 0; i < 5; i++) {
            iterPtr = startPtr;
            tempPtr = startPtr;
            while(1){
                if(iterPtr->econ != 0) {
                    if(iterPtr->econ >= tempPtr->econ && iterPtr->check == 0) {
                        tempPtr = iterPtr;

                    }
                }
                iterPtr = iterPtr -> nextPtr;
                if(iterPtr == NULL)
                    break;
            }
            tempPtr->check++;
         //   part_c(tempPtr);
            printf("%s - %d\n", tempPtr->term, tempPtr->econ);
        }
        printf("\n\n");
        printf("MOST FREQUENTS OF HEALTH\n\n");
        for(i = 0; i < 5; i++) {
            iterPtr = startPtr;
            tempPtr = startPtr;
            while(1){
                if(iterPtr->health != 0) {
                    if(iterPtr->health >= tempPtr->health && iterPtr->check <= 1) {
                        tempPtr = iterPtr;
                    }
                }
                iterPtr = iterPtr -> nextPtr;
                if(iterPtr == NULL)
                    break;
            }
            if(tempPtr->check == 0)
                tempPtr->check+=2;
            else if(tempPtr->check == 1)
                tempPtr->check++;
            printf("%s - %d\n", tempPtr->term, tempPtr->health);
        }
        printf("\n\n");
        printf("MOST FREQUENTS OF MAGAZINE\n\n");
        for(i = 0; i < 5; i++) {
            iterPtr = startPtr;
            tempPtr = startPtr;
            while(1){
                if(iterPtr->magazin != 0) {
                    if(iterPtr->magazin >= tempPtr->magazin && iterPtr->check <= 2) {
                        tempPtr = iterPtr;
                    }
                }
                iterPtr = iterPtr -> nextPtr;
                if(iterPtr == NULL)
                    break;
            }
            if(tempPtr->check == 0)
                tempPtr->check += 3;
            else if(tempPtr->check == 1)
                tempPtr->check += 2;
            else if(tempPtr->check == 2)
                tempPtr->check++;
            printf("%s - %d\n", tempPtr->term, tempPtr->magazin);
        }

}

void part_a(ListNodePtr startPtr){
        ListNodePtr tempPtr=startPtr;
        ListNodePtr iterPtr=startPtr;


            printf("FIRST ORDER\n");
            printf("-----------\n");
            while(iterPtr->nextPtr!=NULL){
                    tempPtr=startPtr;
                    tempPtr=iterPtr;
                while(tempPtr->econ>0){
                    if(iterPtr->econ>=1&&(strcmp(iterPtr->term,tempPtr->term)!=0)){
                            printf("<%s.%s>\t",iterPtr->term,tempPtr->term);



                    }
                    tempPtr=tempPtr->nextPtr;
                }

                tempPtr=startPtr;
                tempPtr=iterPtr;
                while(tempPtr->health>0){
                    if(iterPtr->health>=1&&(strcmp(iterPtr->term,tempPtr->term)!=0)){
                            printf("<%s.%s>\t",iterPtr->term,tempPtr->term);



                    }
                    tempPtr=tempPtr->nextPtr;
                }
                iterPtr=iterPtr->nextPtr;
                }
                tempPtr=startPtr;
                tempPtr=iterPtr;
                while(iterPtr->nextPtr!=NULL){
                    tempPtr=startPtr;
                    tempPtr=iterPtr;
                    while(tempPtr->magazin>0){
                    if(iterPtr->magazin>=1&&(strcmp(iterPtr->term,tempPtr->term)!=0)){
                            printf("<%s.%s>\t",iterPtr->term,tempPtr->term);
                    }
                    tempPtr=tempPtr->nextPtr;

                }
                iterPtr=iterPtr->nextPtr;
                }
            printf("\n");
            printf("\nSECOND ORDER\n");
            printf("\n--------------\n");
            tempPtr=startPtr;
            iterPtr=startPtr;
            while(iterPtr->nextPtr!=NULL){
                    tempPtr=startPtr;
                    tempPtr=iterPtr;
                    while(tempPtr->magazin<=0){
                        if((iterPtr->econ>=1&&tempPtr->health>=1)&&(strcmp(iterPtr->term,tempPtr->term)!=0)){
                            printf("<%s.%s>\t",iterPtr->term,tempPtr->term);

                        }
                    tempPtr=tempPtr->nextPtr;
                    }
                iterPtr=iterPtr->nextPtr;
                }
           //     tempPtr=startPtr;
        //   iterPtr=startPtr;

            printf("\n");
            printf("\nTHIRD ORDER\n");
            printf("\n--------------\n");

            tempPtr=startPtr;
            iterPtr=startPtr;
            while(iterPtr->nextPtr!=NULL){
                    tempPtr=startPtr;

                    while(iterPtr->health<=0){
                        if((iterPtr->econ>=0&&tempPtr->magazin>=0)||(tempPtr->econ>=0&&iterPtr->magazin>=0)){
                            printf("<%s.%s>\t",iterPtr->term,tempPtr->term);

                        }
                    tempPtr=tempPtr->nextPtr;
                    }
                iterPtr=iterPtr->nextPtr;
                }


}

int part_c(ListNodePtr currentPtr){
        float idf=0;
        idf=log(currentPtr->econ/currentPtr->econ);
        printf("IDF Values---%d",currentPtr->econ);
        printf("----------");
        printf("%s,%d",currentPtr->term,idf);
}
                /*  tempPtr=startPtr;
                    tempPtper=iterPtr;
                    while(tempPtr->econ<=0){
                        if(!(iterPtr->magazin>=1||iterPtr->health>=1)&&(strcmp(iterPtr->term,tempPtr->term))){
                            printf("*****\t");
                        }
                    tempPtr=tempPtr->nextPtr;
                    }
*/

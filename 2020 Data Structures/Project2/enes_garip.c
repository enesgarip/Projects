// Priority queue implementation with Binomial Heap
// Enes Garip - 150116034

#include <stdio.h>
#include <dirent.h>
#include <string.h>
#include <stdlib.h>

#define MAX_FILE_NAME_LENGTH 100
#define MAX_WORD_LENGTH 150


// This list holds the names of the files and number of occurence in the file.
struct fileNode {
    int fileNumber;
    char fileName[MAX_FILE_NAME_LENGTH];
    struct fileNode *nextFileNode;
    int countOfWord;
};
typedef struct fileNode fileNode;

// This list holds the contents of the files in the directory.
struct wordNode {
    char fileName[MAX_FILE_NAME_LENGTH];
    char word[MAX_WORD_LENGTH];
    struct wordNode *nextWordNode;
};
typedef struct wordNode wordNode;

// Binomial Heap nodes for creating a binomial heap.
struct heapNode {
    char fileNameData[MAX_FILE_NAME_LENGTH];
    int numberOfWordsData;
    int degreeData;
    struct heapNode* parentNode;
    struct heapNode* childNode;
    struct heapNode* siblingNode;
};
typedef struct heapNode heapNode;

// Function Prototypes
wordNode * createWordNode();
wordNode *addWordNode(wordNode*,char*,char*);
fileNode * createFileNode();
fileNode *addFileNode(fileNode *, int,char *);
heapNode* createBinHeap();
heapNode* createBinHeapNode(int,char*);
heapNode* binHeapUnionEnqueue(heapNode*,heapNode*);
heapNode* binHeapUnionDequeue(heapNode*,heapNode*);
heapNode* binHeapMergeEnqueue(heapNode*,heapNode*);
heapNode* binHeapMergeDequeue(heapNode*,heapNode*);
heapNode* dequeue(heapNode*);
void heapLinking(heapNode*,heapNode*);
void binHeapRootListLinking(heapNode*);
void printBinHeap(heapNode*);
void enqueue(int,char*);
void printWordList(wordNode *);
void printFileList(fileNode *);

// Global variables for binomial heap.
heapNode *rootHeap = NULL;
heapNode *helperHeap = NULL;

// That function creates a binomial heap and returns the address of that binomial heap.
heapNode* createBinHeap() {
    heapNode *heapNode=NULL;
    return heapNode;
}

// In this function, for example the first binomial heap node is already added, second one's connections are made in
// this function. Because of the connection, the degree of parent node is increased..
void heapLinking(heapNode *a,heapNode *b) {
    a->parentNode = b;
    a->siblingNode = b->childNode;
    b->childNode = a;
    b->degreeData = b->degreeData + 1;
}

// In this function, create a binomial heap node with arguments. The number of occurence and filename is the arguments.
heapNode *createBinHeapNode(int number,char *filename) {
    heapNode *temp;
    temp = (heapNode*) malloc(sizeof(heapNode));
    temp->numberOfWordsData = number;
    strcpy(temp->fileNameData,filename);
    return temp;
}

// Union function for enqueue operation.
heapNode *binHeapUnionEnqueue(heapNode *H1,heapNode *H2) {
    heapNode *prevX=NULL,*x,*nextX; // variable initializations
    heapNode *tempHeap = createBinHeap();   // Temporary heap. function returns that heap to the caller.
    tempHeap = binHeapMergeEnqueue(H1, H2); // First of all, the arguments of the functions merged.
    if (tempHeap == NULL) {
        return tempHeap;
    }
    x = tempHeap;   // x is the head of the root list of the heap.
    nextX = x->siblingNode; // next x is the sibling node of the x.
    while (nextX != NULL) {
        // iteration in the root list.
        if ((x->degreeData != nextX->degreeData) || ((nextX->siblingNode != NULL) && (nextX->siblingNode)->degreeData == x->degreeData)) {
            prevX = x;
            x = nextX;
        } else { // That else statement actually sorts the root list.
            if (x->numberOfWordsData >= nextX->numberOfWordsData) {
                x->siblingNode = nextX->siblingNode;
                heapLinking(nextX, x);
            } else {
                if (prevX == NULL) {
                    tempHeap = nextX;
                }else {
                    prevX->siblingNode = nextX;
                }
                heapLinking(x, nextX);
                x = nextX;
            }
        }
        nextX = x->siblingNode;
    }
    return tempHeap;
}

// The same logic in the enqueue part but because of the some different operations these function is seperated.
heapNode *binHeapUnionDequeue(heapNode *H1,heapNode *H2) {
    heapNode *prevX=NULL,*x,*nextX;
    heapNode *tempHeap =createBinHeap();
    tempHeap = binHeapMergeDequeue(H1, H2);
    if (tempHeap == NULL){
        return tempHeap;
    }
    x = tempHeap;
    nextX = x->siblingNode;
    while (nextX != NULL) {
        if ((x->degreeData != nextX->degreeData) || ((nextX->siblingNode != NULL) && (nextX->siblingNode)->degreeData == x->degreeData)) {
            prevX = x;
            x = nextX;
        } else {
            if (x->numberOfWordsData >= nextX->numberOfWordsData) {
                x->siblingNode = nextX->siblingNode;
                heapLinking(nextX, x);
            } else {
                if (prevX == NULL){
                    tempHeap = nextX;
                } else {
                    prevX->siblingNode = nextX;
                }
                heapLinking(x, nextX);
                x = nextX;
            }
        }
        nextX = x->siblingNode;
    }
    return tempHeap;
}

// Merging function for enqueue operation. In that part, after the insertion of a new node, this function called and returns a binomial heap
// that some of the neccessary things are done such as connection for the siblings.
heapNode *binHeapMergeEnqueue(heapNode *H1,heapNode *H2) {
    heapNode *temp = createBinHeap(),*heap1=H1,*heap2=H2;
    if (heap1 != NULL) {
        if ((heap2 != NULL && heap1->degreeData <= heap2->degreeData) || heap2==NULL ){
            temp = heap1;
        }
        else if (heap2 != NULL && heap1->degreeData > heap2->degreeData){
            temp = heap2;
        }
    } else{
        temp= heap2;
    }
    heapNode *a,*b;
    while (heap1 != NULL && heap2 != NULL) {
        if (heap1->degreeData < heap2->degreeData) {
            heap1 = heap1->siblingNode;
        } else if (heap1->degreeData == heap2->degreeData) {
            a = heap1->siblingNode;
            heap1->siblingNode = heap2;
            heap1 = a;
        } else {
            b = heap2->siblingNode;
            heap2->siblingNode = heap1;
            heap2 = b;
        }
    }
    return temp;
}

// The same logic in the enqueue merge orperation. But in addition, for correctness, there iş a break statement in the while loop.
heapNode* binHeapMergeDequeue(heapNode *H1,heapNode *H2) {
    heapNode *temp = createBinHeap(),*heap1=H1,*heap2=H2;
    if (heap1 != NULL) {
        if ((heap2 != NULL && heap1->degreeData <= heap2->degreeData) || heap2==NULL ){
            temp = heap1;
        }
        else if (heap2 != NULL && heap1->degreeData > heap2->degreeData){
            temp = heap2;
        }
    } else{
        temp = heap2;
    }
    heapNode *a,*b;
    while (heap1 != NULL && heap2 != NULL) {
        if (heap1->degreeData < heap2->degreeData) {
            heap1 = heap1->siblingNode;
        } else if (heap1->degreeData == heap2->degreeData) {
            a = heap1->siblingNode;
            heap1->siblingNode = heap2;
            heap1 = a;
            break;
        } else {
            b = heap2->siblingNode;
            heap2->siblingNode = heap1;
            heap2 = b;
            heap1->siblingNode=heap2;
            break;
        }
    }
    return temp;
}

// Dequeue function. In that function, the node which stores the bigger number of words is extracted to the binomial heap and returns the
// removed node so we know which node is removed.
heapNode* dequeue(heapNode *H1) {
    int max;
    heapNode *temp = NULL;
    heapNode *removedNode = H1;
    heapNode *iter;
    helperHeap = NULL;
    if (removedNode == NULL) {
        printf("\nDequeue Error! No nodes to extract!!");
        return removedNode;
    }
    iter = removedNode;
    max=iter->numberOfWordsData;
    while (iter->siblingNode != NULL) {
        if ((iter->siblingNode)->numberOfWordsData > max) {
            max = (iter->siblingNode)->numberOfWordsData;
            temp = iter;
            removedNode= iter->siblingNode;
        }
        iter = iter->siblingNode;
    }
    if (temp == NULL && removedNode->siblingNode == NULL){
        H1 = NULL;
    }
    else if (temp == NULL) {
        H1 = removedNode->siblingNode;
    }
    else if (temp->siblingNode == NULL) {
        temp = NULL;
    }
    else {
        temp->siblingNode = removedNode->siblingNode;
    }
    if (removedNode->childNode != NULL) {
        binHeapRootListLinking(removedNode->childNode);
        (removedNode->childNode)->siblingNode = NULL;
    }
    rootHeap = binHeapUnionDequeue(H1, helperHeap);
    return removedNode;
}

// This recursive function used in dequeue function.
void binHeapRootListLinking(heapNode *H1) {
    if (H1->siblingNode != NULL) {
        binHeapRootListLinking(H1->siblingNode);
        (H1->siblingNode)->siblingNode = H1;
    } else {
        helperHeap = H1;
    }
}

// It prints the root list of the binomial heap.
void printBinHeap(heapNode *H) {
    heapNode *temp;
    if (H == NULL) {
        printf("\nError! Heap is Empty!!!");
    }
    printf("\nBinomial Heap Root Nodes List:\n");
    temp = H;
    while (temp != NULL) {
        printf("%s(%d)",temp->fileNameData, temp->numberOfWordsData);
        if (temp->siblingNode != NULL) {
            printf("->");
        }
        temp = temp->siblingNode;
    }
    printf("\n");
}

// The enqueue method needs 2 arguments. One of them is the number of words and the other one is the filename that contains this words.
// it creates a temporary binomial heap and binomial heap node and unify the heap with global heap.
void enqueue(int number,char *filename) {
    heapNode *tempNode = createBinHeapNode(number,filename);
    heapNode *tempHeap = createBinHeap();
    tempNode->parentNode = NULL;
    tempNode->childNode = NULL;
    tempNode->siblingNode = NULL;
    tempNode->degreeData = 0;
    tempHeap = tempNode;
    rootHeap = binHeapUnionEnqueue(rootHeap, tempHeap);
}

// That function is for creation of a word node that holds all the words in files.
wordNode *createWordNode(){
    wordNode *temp;
    temp = malloc(sizeof(wordNode));
    temp->nextWordNode = NULL;
    temp->fileName[0]='\0';
    temp->word[0]='\0';
    return temp;//return the new node
}

// Addition function for wordList.
wordNode *addWordNode(wordNode *head, char *word,char *fileName){
    wordNode *temp,*p;
    temp = createWordNode();
    strcpy(temp->fileName,fileName);
    strcpy(temp->word,word);
    if(head == NULL){
        head = temp;
    }
    else{
        p  = head;
        while(p->nextWordNode != NULL){
            p =  p->nextWordNode;
        }
        p->nextWordNode = temp;
    }
    return head;
}

// Creation function for fileList.
fileNode *createFileNode(){
    fileNode *temp;
    temp = malloc(sizeof(fileNode));
    temp->nextFileNode = NULL;
    temp->fileNumber=0;
    temp->fileName[0]='\0';
    temp->countOfWord=0;
    return temp;
}

// Addition function for fileList. These nodes holds the file names and occurence of a word in a specific file.
fileNode *addFileNode(fileNode *head, int index,char *fileName){
    fileNode *temp,*p;
    temp = createFileNode();
    temp->fileNumber = index;
    strcpy(temp->fileName,fileName);
    if(head == NULL){
        head = temp;
    }
    else{
        p  = head;
        while(p->nextFileNode != NULL){
            p = (fileNode *) p->nextFileNode;
        }
        p->nextFileNode = (fileNode *) temp;
    }
    return head;
}

// Printer for word list.
void printWordList(wordNode *currentPtr){
    printf("\nThe word list is: ");
    while(currentPtr != NULL) {
        printf("(%s: %s )--> ",currentPtr->fileName,currentPtr->word);
        currentPtr=currentPtr->nextWordNode;
    }
}

// Printer for file list.
void printFileList(fileNode *currentPtr){
    printf("\nThe file list is: ");
    while(currentPtr != NULL) {
        printf("(%d. %s )--> ",currentPtr->fileNumber,currentPtr->fileName);
        currentPtr=currentPtr->nextFileNode;
    }
}

int main() {
    fileNode *root=createFileNode();        // File list creation.
    wordNode *root2=createWordNode();       // Word list creation.
    DIR *folder;                        // directory pointer.
    struct dirent *entry;
    int files = 0;                  // number of files.
    FILE *filePointer;              // file pointer
    char ch;                        //holds each character in the files in that variable.
    char word[MAX_WORD_LENGTH];             // holds a word that comes from ch variable.
    word[0]='\0';
    folder = opendir("files");       // opening of a directory.
    char keyword[MAX_WORD_LENGTH];              // The word which user want to search.
    printf("Reading Process Started...");
    if(folder == NULL){                         // if folder will not be found.
        perror("Unable to read directory");
        return -1;
    }
    while( (entry=readdir(folder)) ){
        if(!strcmp(entry->d_name, ".") || !strcmp(entry->d_name, "..") ){
            //
        }
        else {
            char fileName[MAX_FILE_NAME_LENGTH]="\0";           // holds the file name.
            strcpy(fileName, "files/");                  // for reading.
            strcat(fileName, entry->d_name);                // the filename holds the way of the file.
            files++;                                // file counter.
            filePointer = fopen(fileName, "r");     // opening of a file
            addFileNode(root,files,entry->d_name);          // file added to the file list.
            if (filePointer == NULL){                       // if file is not found.
                printf("File is not available!!\n");
            }
            else{
                while ((ch = fgetc(filePointer)) != EOF)        // reads file char by char.
                {
                    if((ch >= 'a' && ch <= 'z')||(ch >= 'A' && ch <= 'Z')||ch=='\''||ch==';'||(ch>='0' && ch<='9')){
                        sprintf(word, "%s%c", word, ch);        // the word variable holds the words.
                    } else{
                        if (word[0]!='\0'){
                            addWordNode(root2,word,entry->d_name);      // the word is added to the word list. The root of the word list is root2.
                            word[0]='\0';
                        }
                    }
                }
            }
            fclose(filePointer);            // Closing of a file.
        }
    }
    closedir(folder);                   // Closing of a folder.
    printf("\nReading complete successfully..");
    printf("\nEnter the keyword you want to search:");
    scanf("%s",keyword);            // User input.
    wordNode *q=root2,*replacement=root2;           // temporary pointers for iteration over the lists.
    fileNode *temp=root;
    int k=0;                               // temporary variable for counting the occurence of the words.
    char fileName2[MAX_FILE_NAME_LENGTH];
    while (root2!=NULL){                        // the iteration over all of the words in the word list.
        if (strcmp(root2->word,keyword)==0){                // if user input and the word is same..
            strcpy(fileName2,root2->fileName);              // filename copied to the filename variable..
            k++;                                            // and the occurence is increased 1.
            if (strcmp(root2->fileName,fileName2)==0){
                temp=root;                                 // file list iterator
                while (temp!=NULL){
                    if (strcmp(temp->fileName,fileName2)==0) {          // if the file which contains that keyword is found in file list..
                        temp->countOfWord+=1;                      // then the pointer in the file list increased 1 so the occurrence of..
                    }                                   // the word is stored successfully.
                    temp=temp->nextFileNode;
                }

            }
        } else{
            k=0;        // Resetting the k value.
        }
        root2=root2->nextWordNode;
    }
    int l=0;
    fileNode *t=root;           // temporary file list variable
    while (t!=NULL){                        // in that loop all the files inserted into the binomial heap.
        if (t->countOfWord!=0){
            enqueue(t->countOfWord,t->fileName);
            l++;
        }
        t=t->nextFileNode;
    }
    printf("\nThe number of relevant documents: %d\n",l);
    printBinHeap(rootHeap);         // prints the  root list of the binomial heap
    heapNode* p;
    int i;
    for (i = 0; i <5 ; ++i) {       // for loop for extracting 5 relevant documents.
        p = dequeue(rootHeap);      // dequeue function. p is the extracted node.
        if (p != NULL) {
            printf("\n\n\tThe number of words in %s is %d\n", p->fileNameData, p->numberOfWordsData);
            printf("\t%s -> ",p->fileNameData);
            while (q!=NULL){
                if (strcmp(q->fileName,p->fileNameData)==0){
                    printf("%s ",q->word);
                }
                q=q->nextWordNode;
            }
            q=replacement;
        }
    }
    if (rootHeap!=NULL){
        printBinHeap(rootHeap);
    }
    printf("\n\n\nThe program ended.");
    return 0;
}

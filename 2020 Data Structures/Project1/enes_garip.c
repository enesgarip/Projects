// Enes Garip - 150116034

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <locale.h>
#define BUFFER 100000
// Global Variables for holding the data of Access Time.
int depth=1;
int totalForA=0;
int totalForB=0;
// Struct for Tree
typedef struct node {
    char *wordData;
    int frequencyData;
    int numberData;
    int depthNum;
    struct node *left,*right;
}node;
// Node creation function
node *createNode(char* word){
    int wordLength = (int )(strlen(word)+1);
    node* temp;
    temp = (node*)malloc(sizeof(node));
    temp->wordData = (char*)malloc(sizeof(char*) * wordLength);
    strcpy(temp->wordData, word);
    temp->left = NULL;
    temp->right = NULL;
    return temp;
}
// Insert function for insertion of a node to the Binary Search Tree. In addition, depth levels of nodes are added
// to the nodes.
// The function is only for first tree(Binary Search Tree). In Binary Tree, the levelOrderInsert function will be used.
node *insert(node *root, node* element){
    if(root == NULL){
        return element;
    }
    else{
        if(strcmp(element->wordData, root->wordData) < 0){
            if (root->left != NULL) {
                depth++;
                root->left = insert(root->left, element);
            }
            else{
                root->left = element;
                root->left->depthNum=depth;
            }
        }
        else{
            if(root->right != NULL){
                depth++;
                root->right = insert(root->right, element);
            }
            else{
                root->right = element;
                root->right->depthNum=depth;
            }
        }
        depth=1;
        return root;
    }
}
// Inorder tree traversal function. Also calculates the total access time of the Binary Search tree.
void printInorderForFirstTree(node* node){
    if (node != NULL) {
        totalForA=totalForA + ((node->depthNum+1)*node->frequencyData);
        printInorderForFirstTree(node->left);
        printf("%d,%s,%d -> Depth:%d\n",node->numberData,node->wordData,node->frequencyData,node->depthNum+1);
        printInorderForFirstTree(node->right);
    }
}
// LowerCase function to get all the words in lowercase.
void toLowerCase(char *str){
    int i;
    for(i=0;i<=strlen(str);i++){
        if(str[i]>=65&&str[i]<=90)
            str[i]=str[i]+32;
    }
}
/* Level order insert function for binary tree. The array holds the nodes of a first tree.
With recursion, the nodes inserted to the binary tree with a level order manner.*/
node* levelOrderInsert(node* root, node** arr, int start, int size){
    if(start<size) {
        int left = 2 * start + 1;
        int right = 2 * start + 2;
        node *temp = arr[start];
        root = temp;
        root->left= levelOrderInsert(root->left, arr, left, size);
        root->right= levelOrderInsert(root->right, arr, right, size);
    }
    return root;
}
// Inorder traversal of binary tree. Also calculates the total access time of the Binary tree.
void printInorderForSecondTree(node* node){
    if (node != NULL) {
        totalForB = totalForB + (node->depthNum + 1) * node->frequencyData;
        printInorderForSecondTree(node->left);
        printf("%d,%s,%d -> Depth:%d\n", node->numberData, node->wordData, node->frequencyData, node->depthNum + 1);
        printInorderForSecondTree(node->right);
    }
}
// For the Binary Tree, the depthLevelOfNode function traverse the tree recursively for the calculation of depth number.
void depthLevelOfNode(node* root, int currentLevel, int num) {
    if(root == NULL){
        return;
    }
    if(root->numberData == num){
        root->depthNum=currentLevel;
    }
    depthLevelOfNode(root->left, currentLevel + 1, num);
    depthLevelOfNode(root->right, currentLevel + 1, num);
}
int main() {
    setlocale(LC_ALL,"");
    FILE *fp;
    char line[100], *number = "",*word,*frequency;        // for holding the data of the file.
    char *token;
    node* arrayOfNodes[BUFFER];                 // The array that holds the address of each node at each index.
    int index=0;
    node* root=NULL;                    // Root of the Binary Search Tree.
    node* tempNode=NULL;
    fp=fopen("input.txt","r");
    if(fp==NULL){
        printf("File does not exist!!");        // file existence control.
    }
    else{
        while (fgets(line,1000,fp)){
            token=strtok(line,",");
            number=token;                   // token for numbers.
            token=strtok(NULL,",");
            word=token;                     // token for words.
            token=strtok(NULL,",");
            frequency=token;                // token for frequency numbers.
            toLowerCase(word);              // lowercase words.
            tempNode=createNode(word);             // Creation of the root.
            tempNode->frequencyData=atoi(frequency);   // assigning the frequency data to node's frequencyData field.
            tempNode->numberData=atoi(number);         // assigning the number data to node's numberData field.
            root=insert(root,tempNode);
            arrayOfNodes[index]= tempNode;              // assigning the nodes to the arrayOfNodes.
            index++;
        }
    }
    printf("\n\t-Inorder of the Binary Search Tree-\n");
    printf("{Number,Word,Frequency}\n");
    printInorderForFirstTree(root);
    printf("\nTotal Access Time of the Binary Search Tree: %d\n",totalForA);
    int k,j;
    long tmp;           // temporary variable for sorting the arrayOfNodes.
    for(k=0; k<atoi(number); k++){
        for(j=k+1; j<atoi(number); j++){
            if(arrayOfNodes[k]->frequencyData < arrayOfNodes[j]->frequencyData){
                tmp = (long) arrayOfNodes[k];
                arrayOfNodes[k] = arrayOfNodes[j];
                arrayOfNodes[j] = (node*)tmp;
            }
        }
    }// arrayOfNodes sorted. // frequencyData // Descending order
    int i;
    for(i=0; i<atoi(number); i++){
        arrayOfNodes[i]->depthNum=0;
        arrayOfNodes[i]->left=NULL;
        arrayOfNodes[i]->right=NULL;
    }/* The arrayOfNodes just holds the addresses of the nodes so when creating the Binary tree the data fields
     depth number, pointer of left and right node must be cleared. If not, the data from Binary Search Tree
     causes conflicts.*/
    node* root2=NULL;
    root2=levelOrderInsert(root2, arrayOfNodes, 0, atoi(number));/* After the data fields cleared
    the levelOrderInsert function inserts each node one by one with the help of addresses of nodes in the arrayOfNodes*/
    for (i = 1; i <= atoi(number); ++i){
        depthLevelOfNode(root2, 0, i);
    }// After the Binary Tree created, depth level of each node calculated in the for loop.
    printf("\n\t-Inorder of the Binary Tree-\n");
    printf("{Number,Word,Frequency}\n");
    printInorderForSecondTree(root2);
    printf("\nTotal Access Time of the Binary Tree: %d\n",totalForB);
    return 0;
}

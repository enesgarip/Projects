/*
    CSE225 DATA STRUCTURES PROJECT #2
    Enes Garip-150116034
*/
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAXSIZE 1000                     // Define an array size of MAXSIZE

typedef struct tree{                        // Struct of the tree

    int key;                                // Key variable contains the numbers
    struct tree *leftN, *rightN;            // Right Node and Left Node of the nodes

}tree;

typedef struct tempTree{

    int tempKey;
    struct tempTree *tempLeft, *tempRight;

}tempTree;

tempTree *tempRoot=NULL;
tree *root=NULL;


// Function Constructiors
void inorder(tree *root);
tree *newNode(int item);
tree *insert(tree* node,int number);
void swap (int v[], int i, int j);
void perm (int v[], int n, int i);
int identicalTrees(tree* a, tree* b);



int main(){
    int i=0,k=0;


    char input[MAXSIZE];
    int *array[MAXSIZE];
    char *p;                                // string token variable
    int b;                                  // int variable for atoi function
    printf("Enter values:\n");
    gets(input);
    p=strtok(input," ");                    // Tokenizing the input
    k++;
        while(p!=NULL){

            b=atoi(p);
            p=strtok(NULL," ");
            array[i++]=b;
                if(root!=NULL){
                insert(root,b);            // Calling the insert function if the tree is already created

                }
                else{
                    root=insert(root,b);      // Creating and inserting the root
                }
            k++;
        }

    printf("\n\tInorder Traversal of the Tree");
    printf("\n\t-----------------------------\n");
    inorder(root);                             // Traversing the tree
    printf("\n\n\n");
    perm(array,k-1,1);
    system("pause");
    return 0;
}

tree *newNode(int item){                            // Creating new node for each input number
    tree *temp=(tree*)malloc(sizeof(tree));
    temp->key=item;
    temp->leftN=temp->rightN=NULL;
    return temp;
}

void inorder(tree *root){                           // Recursive inorder traversal Function

    if(root!=NULL){
        inorder(root->leftN);
        printf("\t\t%d\n",root->key);
        inorder(root->rightN);
    }
}

tree *insert(tree* node,int number){                //Insertion function for putting each input to the tree
    if(node == NULL){
        return newNode(number);
    }
    if(number < node->key){
        node->leftN = insert(node->leftN,number);
    }
    else if(number > node->key){
        node->rightN=insert(node->rightN,number);
    }

    return node;
}

unsigned long long counter=1;               // Counter variable for valid outputs

void perm (int v[], int n, int i) {         // Generating permutations of an array from index i to index n-1

    int	j;                                  // int variable for traversing the array

    tempRoot=insert(tempRoot,v[0]);         // inserting root of the temporary tree (first index of user's input)

	if (i == n) {                           /* if we reach at the end of array we find a permutation and the rest of the code
                                               we print all valid permutations */
		for (j=0; j<n; j++){

            insert(tempRoot,v[j]);          // insert the permutations to the temporary tree

        }
        if(identicalTrees(root,tempRoot)){  // if original tree and temporary tree is identical so the permutation is valid and shows the permutation
                printf(" %d)\t",counter);
                counter++;
            for (j=0; j<n; j++){

                printf("%d ",v[j]);

            }
        printf("\n");
        }


        tempRoot = NULL;                    // For every permutation we need to clean temporary tree



	} else{


		for (j=i; j<n; j++) {



			swap (v, i, j);                 // swapping for getting permutations

			perm (v, n, i+1);               // recursively permute so all the permutations shown with the help of swap function

			swap (v, i, j);                 // swapping back for all other inputs

		}

    }


}

void swap (int v[], int i, int j) {         // swap function for permutation function
	int	t;

	t = v[i];
	v[i] = v[j];
	v[j] = t;

}

int identicalTrees(tree* a, tree* b) {
    if( ! (a || b ) )                       //if both of them are empty return 1
        return 1;

    if( !( a && b ) )                       // if one of them is empty return 0
        return 0;

    return ( a->key == b->key )             // traverse the trees for determining the trees are identical.
        && identicalTrees( a->leftN, b->leftN )
        && identicalTrees( a->rightN, b->rightN );
}





#include <stdlib.h>
#include <stdio.h>
#define true 1

/* Enes Garip-150116034
-Insert function inserts numbers from the file.
-Max and min functions finds max value or min value in tree.
-Deletion function deletes a specific node.
-Search function searches for a specific node.
*/
FILE *fptr;

struct ts_tree {

int key;
struct ts_tree *leftn,*rightn,*middlen;
};

typedef struct ts_tree node;


void insert(node ** tree, int val)
{
    node *temp = NULL;
    if(!(*tree))
    {
        temp = (node *)malloc(sizeof(node));
        temp->leftn = temp->rightn = temp->middlen= NULL;
        temp->key = val;
        *tree = temp;
        return;
    }

    if(val < (*tree)->key)
    {
        insert(&(*tree)->leftn, val);
    }
    else if(val > (*tree)->key && val>( (*tree)->key * (*tree)->key ) )
    {
        insert(&(*tree)->rightn, val);
    }
    else if(val > (*tree)->key && val<=( (*tree)->key * (*tree)->key ) )
    {
        insert(&(*tree)->middlen, val);
    }

}

int max(node *tree){
    int x,y;
    while(tree->rightn !=NULL){
        tree=tree->rightn;
    }
    x=tree->key;
    while(tree->middlen!=NULL){
        tree=tree->middlen;
    }
    y=tree->key;
    if(x>y)  {
            printf("%d\n",x);
            return x;
    }

    else {
    printf("%d\n",y);
    return y;
    }
}

int min(node *tree){
    while(tree->leftn!=NULL){
        tree=tree->leftn;
    }
    printf("%d\n",tree->key);
    return tree->key;

}
node * deletion(node *tree,int x){

        if(tree==NULL){
            return NULL;
        }
        if(tree->key==x){
            if(tree->leftn == NULL && tree->middlen == NULL && tree->rightn == NULL){
                printf("Key deleted.");
               return NULL;


            }
           else if(tree->rightn == NULL && tree->leftn != NULL && tree->middlen != NULL){
                tree->key=tree->middlen->key;
                tree->middlen=deletion(tree->middlen,tree->middlen->key);
                return tree;

            }
           else if(tree->leftn == NULL && tree->rightn == NULL && tree->middlen !=NULL){
                tree->key=tree->middlen->key;
                tree->middlen=deletion(tree->middlen,tree->middlen->key);
                return tree;


           }
           else if(tree->leftn != NULL && tree->rightn == NULL && tree->middlen ==NULL){
                tree->key=tree->leftn->key;
                tree->leftn=deletion(tree->leftn,tree->key);
                return tree;

           }
           else if(tree->leftn != NULL && tree->rightn != NULL && tree->middlen !=NULL){
                tree->key=tree->middlen->key;
                tree->middlen=deletion(tree->middlen,tree->key);
                tree->rightn=deletion(tree->rightn,tree->key);
                return tree;

           }

        }
        else if(tree->key>x){

           tree->leftn=deletion(tree->leftn,x);
           return tree;
        }
        else if(tree->key<x && x<=(tree->key * tree->key)){
                 tree->middlen=deletion(tree->middlen,x);
                 return tree;
                }
        else if(tree->key<x && x>(tree->key * tree->key)){
                tree->rightn=deletion(tree->rightn,x);
                return tree;
        }
    return tree;
}

void print_postorder(node * tree)
{
    if (tree)
    {
        print_postorder(tree->leftn);
        print_postorder(tree->rightn);
        print_postorder(tree->middlen);
        printf("\n\t%d",tree->key);
    }
}




void print_inorder(node * tree)
{

    if (tree)
    {

        print_inorder(tree->leftn);
        printf("\t%d\n",tree->key);
        print_inorder(tree->middlen);

        print_inorder(tree->rightn);
    }
}
void print_preorder(node * tree)
{
    if (tree)
    {
        printf("\t%d\n",tree->key);
        print_preorder(tree->leftn);
        print_preorder(tree->middlen);
        print_preorder(tree->rightn);
    }

}
node* search(node ** tree, int val)
{
   if(!(*tree))
    {
        return NULL;
    }
    else if((*tree)->key != val && (*tree)->key!=NULL){




    if(val < (*tree)->key)
    {
        printf("%d",(*tree)->key);
        printf("---->");
      return search(&((*tree)->leftn), val);
    }
    else if(val > (*tree)->key && val>( (*tree)->key * (*tree)->key ))
    {
        printf("%d",(*tree)->key);
        printf("---->");
       return search(&((*tree)->rightn), val);

    }
     else if(val > (*tree)->key && val<=( (*tree)->key * (*tree)->key ) )
    {
        printf("%d",(*tree)->key);
        printf("---->");
       return search(&(*tree)->middlen, val);
    }

    }
    if(val == (*tree)->key)
    {
        printf("%d",(*tree)->key);
      //  return *tree;
    }

    return *tree;
}


int main(){
    node *root;

    int num,choice,rem,srchnum;
    root=NULL;
    fptr = fopen("input.txt","r");
   if (fptr == NULL){
       printf("Error! opening file");
       exit(1);
   }
    else{
   while(!feof(fptr)){

   fscanf(fptr,"%d",&num);


   insert(&root, num);



   }
    }
    fclose(fptr);
    do{
        printf("\n\tMenu\n");
        printf("\n1.Display list in Pre order");
        printf("\n2.Display list in In order");
        printf("\n3.Display list in Post order");
        printf("\n4.Remove a key from list");
        printf("\n5.Search a key");
        printf("\n0.Zero to exit\n");
        scanf("%d",&choice);
        switch(choice){
            case 1:
                printf("Pre Order Display\n");
                print_preorder(root);
       //         max(root);
       //         min(root);
                break;
            case 2:
                 printf("In Order Display\n");
                 print_inorder(root);

                 break;
            case 3:
                printf("Post order Display");
                print_postorder(root);
                break;

            case 4:
                printf("\nEnter the key: ");
                scanf("%d",&rem);
                root=deletion(root,rem);

                break;
            case 5:
                printf("\nEnter the key: ");
                scanf("%d",&srchnum);
                printf("(If arrow shows a number the key is found in the tree.)");
                printf("\n(If arrow goes to space at the end means that the key does not found.)\n");
                search(&root,srchnum);
                break;


        }

    }while(choice != 0);



   return 0;
}

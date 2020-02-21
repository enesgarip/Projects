#include <stdio.h>
#include <stdlib.h>
#include <locale.h>
int count=0;
int k=0;
struct node
{
    int vertex;
    struct node* next;
};
struct node* createNode(int);

struct Graph
{
    int vertices;
    struct node** adj;
    int **adjMatrix;
}G;

struct Graph* createGraph(int vertices);
void addEdge(struct Graph* graph, int src, int dest);
void printGraph(struct Graph* graph);
void degreeCentrality(int a,int b);
// struct graph *graph_create(int nodes);

int main()
{
   setlocale(LC_ALL,"");
    struct Graph* graph = createGraph(10);
    printf("\n\t\tNode\t\tStandardized Score\n");
     addEdge(graph, 0, 1);   addEdge(graph, 0, 4);   addEdge(graph, 0, 6);                                                                            /*      printf("\nVertex 0 has %d edges\n",k);  */    degreeCentrality(k,0);
k=0; addEdge(graph, 1, 0);   addEdge(graph, 1, 2);   addEdge(graph, 1, 4);   addEdge(graph, 1, 6);                                                   /*       printf("\nVertex 1 has %d edges\n",k);    */  degreeCentrality(k,1);
k=0; addEdge(graph, 2, 1);   addEdge(graph, 2, 3);   addEdge(graph, 2, 4);                                                                            /*      printf("\nVertex 2 has %d edges\n",k);    */  degreeCentrality(k,2);
k=0; addEdge(graph, 3, 2);   addEdge(graph, 3, 4);   addEdge(graph, 3, 5);                                                                             /*     printf("\nVertex 3 has %d edges\n",k);*/      degreeCentrality(k,3);
k=0; addEdge(graph, 4, 0);   addEdge(graph, 4, 1);   addEdge(graph, 4, 2);   addEdge(graph, 4, 3);    addEdge(graph, 4, 5);   addEdge(graph, 4, 6);    /*    printf("\nVertex 4 has %d edges\n",k);*/       degreeCentrality(k,4);
k=0; addEdge(graph, 5, 3);   addEdge(graph, 5, 4);   addEdge(graph, 5, 6);   addEdge(graph, 5, 7);                                                      /*    printf("\nVertex 5 has %d edges\n",k);*/      degreeCentrality(k,5);
k=0; addEdge(graph, 6, 0);   addEdge(graph, 6, 1);   addEdge(graph, 6, 4);   addEdge(graph, 6, 5);    addEdge(graph, 6, 7);                             /*    printf("\nVertex 6 has %d edges\n",k);*/      degreeCentrality(k,6);
k=0; addEdge(graph, 7, 5);   addEdge(graph, 7, 6);   addEdge(graph, 7, 8);                                                                              /*    printf("\nVertex 7 has %d edges\n",k);*/      degreeCentrality(k,7);
k=0; addEdge(graph, 8, 7);   addEdge(graph, 8, 9);                                                                                                       /*   printf("\nVertex 8 has %d edges\n",k);*/      degreeCentrality(k,8);
k=0; addEdge(graph, 9, 8);                                                                                                                               /*   printf("\nVertex 9 has %d edges\n",k);*/      degreeCentrality(k,9);

    printf("\nTotal number of edges=%d\n",count/2);

    printGraph(graph);
  //  struct graph * d = graph_create(10);
    printf("\n");
    return 0;
}



struct node* createNode(int v)
{
    struct node* newNode = malloc(sizeof(struct node));
    newNode->vertex = v;
    newNode->next = NULL;
    return newNode;

}


struct Graph* createGraph(int vertices)
{
    struct Graph* graph = malloc(sizeof(struct Graph));
    graph->vertices = vertices;

    graph->adj = malloc(vertices * sizeof(struct node*));

    int i;
    for (i = 0; i < vertices; i++){
        graph->adj[i] = NULL;

    }

    return graph;
}

void addEdge(struct Graph* graph, int src, int dest)
{

    k++;
    count++;
    // Add edge from src to dest
    struct node* newNode = createNode(dest);
    newNode->next = graph->adj[src];
    graph->adj[src] = newNode;




}

void printGraph(struct Graph* graph)
{
    printf("\n0=Cem, 1=Ayþe, 2=Belma, 3=Edip, 4=Dundar\n5=Gamze, 6=Ferit, 7=Halit, 8=Ilke, 9=Jale\n");
    int v;
    for (v = 0; v < graph->vertices; v++)
    {
        struct node* temp = graph->adj[v];
        printf("\nAdjacency list of vertex %d\n ", v);
        while (temp)
        {
            printf("%d  ", temp->vertex);
            temp = temp->next;
        }
        printf("\n");

}

}


void degreeCentrality(int a,int b){
  //  int x=sizeof(graph->adj);
  // printf("\t\t%d/%d",a,x);
    printf("Vertex %d",b);
    printf("\t%d",a);
    printf("\t\t%d/9\n",a);
}

/*struct graph *graph_create(int nodes) {

    struct Graph * tmp = &G;
    int r = nodes, c = nodes, i, j, x, y;

    //arr[r][c]
    G.adj = (int **)malloc(r * sizeof(int *));

    for (i=0; i<r; i++){
         *(G.adj + i) = (int *)malloc(c * sizeof(int));
         //arr[i] = (int *)malloc(c * sizeof(int));
    }


    x= 0;
    y=1;
    for (i = 0; i <  r; i++){//3
      for (j = 0; j < c; j++){//4
         G.adjMatrix[i][j] = x;
        if(i==0 && (j==1 || j==4 || j==6)){
            G.adjMatrix[i][j]=y;
        }
        if(i==1 && (j==0 || j==2 || j==4 || j==6)){
            G.adjMatrix[i][j]=y;
        }
        if(i==2 && (j==1 || j==3 || j==4)){
            G.adjMatrix[i][j]=y;
        }
        if(i==3 && (j==2 || j==4 || j==5)){
            G.adjMatrix[i][j]=y;
        }
        if(i==4 && (j==0 || j==1 || j==2 || j==3 || j==5 || j==6)){
            G.adjMatrix[i][j]=y;
        }
        if(i==5 && (j==3 || j==4 || j==6 || j==7)){
            G.adjMatrix[i][j]=y;
        }
        if(i==6 && (j==0 || j==1 || j==4 || j==5 || j==7)){
            G.adjMatrix[i][j]=y;
        }
        if(i==7 && (j==5 || j==6 || j==8)){
            G.adjMatrix[i][j]=y;
        }
        if(i==8 && (j==7 || j==9)){
            G.adjMatrix[i][j]=y;
        }
        if(i==9 && (j==8)){
            G.adjMatrix[i][j]=y;
        }

      }
    }


    for (i = 0; i <  r; i++){
        printf("\n");
        for (j = 0; j < c; j++){
            printf("      %d", G.adjMatrix[i][j]);
        }
    }

    return tmp;

}
*/

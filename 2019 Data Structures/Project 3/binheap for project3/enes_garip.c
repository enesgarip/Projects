 /* Enes Garip
    150116034
    The program does not work properly.

 */
       #include "enes_garip.h"
       #include "enes_garip_2.h"

/* START: fig6_52.txt */
        typedef struct BinNode *Position;




        struct BinNode
        {
		    ElementType Item;
            //model_node_type mn;
            Position    LeftChild;
            Position    NextSibling;
        };

        struct Collection
        {
            int CurrentSize;
            BinTree TheTrees[ MaxTrees ];
        };

        BinQueue
        Initialize( void )
        {
            BinQueue H;
            int i;

            H = malloc( sizeof( struct Collection ) );
            if( H == NULL )
                FatalError( "Out of space!!!" );
            H->CurrentSize = 0;
            for( i = 0; i < MaxTrees; i++ )
                H->TheTrees[ i ] = NULL;
            return H;
        }

        static void
        DestroyTree( BinTree T )
        {
            if( T != NULL )
            {
                DestroyTree( T->LeftChild );
                DestroyTree( T->NextSibling );
                free( T );
            }
        }

        void
        Destroy( BinQueue H )
        {
            int i;

            for( i = 0; i < MaxTrees; i++ )
                DestroyTree( H->TheTrees[ i ] );
        }

        BinQueue
        MakeEmpty( BinQueue H )
        {
            int i;

            Destroy( H );
            for( i = 0; i < MaxTrees; i++ )
                H->TheTrees[ i ] = NULL;
            H->CurrentSize = 0;

            return H;
        }

        /* Not optimized for O(1) amortized performance */
        BinQueue
        Insert( ElementType Item, /*model_node_type m,*/ BinQueue H )
        {
            BinTree NewNode;
            BinQueue OneItem;
			int i;

            NewNode = malloc( sizeof( struct BinNode ) );
            if( NewNode == NULL )
                FatalError( "Out of space!!!" );
            NewNode->LeftChild = NewNode->NextSibling = NULL;
			NewNode->Item = Item;
            /*NewNode->mn.p = m.p;
			NewNode->mn.sid.scalar = m.sid.scalar;
			NewNode->mn.aid.d3act = m.aid.d3act;
			NewNode->mn.nexts.scalar = m.nexts.scalar;
			NewNode->mn.reward = m.reward;
			NewNode->mn.last=m.last;
			for (i=0; i < max_act_const; i++) {
			  NewNode->mn.from[i].s=m.from[i].s;
			  NewNode->mn.from[i].a=m.from[i].a;
			}*/

            OneItem = Initialize( );
            OneItem->CurrentSize = 1;
            OneItem->TheTrees[ 0 ] = NewNode;

            return Merge( H, OneItem );
        }

/* START: fig6_56.txt */
        ElementType
        DeleteMin( BinQueue H )
        {
            int i, j;
            int MinTree;   /* The tree with the minimum item */
            BinQueue DeletedQueue;
            Position DeletedTree, OldRoot;
            ElementType MinItem;
			//model_node_type MinItem;

            if( IsEmpty( H ) )
            {
                Error( "Empty binomial queue" );
				MinItem=-Infinity;

			//  for (i=0; i < max_act_const; i++) {
			//    MinItem.from[i].s=-1;
			//    MinItem->mn.from[i].a=m.from[i].a;
			//  }
                return MinItem;
            }

            MinItem = Infinity;
            for( i = 0; i < MaxTrees; i++ )
            {
                if( H->TheTrees[ i ] &&
                    H->TheTrees[ i ]->Item < MinItem )
                {
                    /* Update minimum */
                    MinItem = H->TheTrees[ i ]->Item;

                    MinTree = i;
                }
            }

            DeletedTree = H->TheTrees[ MinTree ];
            OldRoot = DeletedTree;
            DeletedTree = DeletedTree->LeftChild;
            free( OldRoot );

            DeletedQueue = Initialize( );
            DeletedQueue->CurrentSize = ( 1 << MinTree ) - 1;
            for( j = MinTree - 1; j >= 0; j-- )
            {
                DeletedQueue->TheTrees[ j ] = DeletedTree;
                DeletedTree = DeletedTree->NextSibling;
                DeletedQueue->TheTrees[ j ]->NextSibling = NULL;
            }

            H->TheTrees[ MinTree ] = NULL;
            H->CurrentSize -= DeletedQueue->CurrentSize + 1;

            Merge( H, DeletedQueue );
            return MinItem;
        }
/* END */

        ElementType
        FindMin( BinQueue H )
        {
            int i;
            ElementType MinItem;

            if( IsEmpty( H ) )
            {
                Error( "Empty binomial queue" );
				MinItem=0;
                return MinItem;
            }

            MinItem = Infinity;

            for( i = 0; i < MaxTrees; i++ )
            {
                if( H->TheTrees[ i ] &&
                            H->TheTrees[ i ]->Item < MinItem ) {
                    MinItem = H->TheTrees[ i ]->Item;
				}
            }

            return MinItem;
        }

        int
        IsEmpty( BinQueue H )
        {
            return H->CurrentSize == 0;
        }

        int IsFull( BinQueue H )
        {
            return H->CurrentSize == Capacity;
        }

/* START: fig6_54.txt */
        /* Return the result of merging equal-sized T1 and T2 */
        BinTree
        CombineTrees( BinTree T1, BinTree T2 )
        {
            if( T1->Item > T2->Item )
                return CombineTrees( T2, T1 );
            T2->NextSibling = T1->LeftChild;
            T1->LeftChild = T2;
            return T1;
        }
/* END */

/* START: fig6_55.txt */
        /* Merge two binomial queues */
        /* Not optimized for early termination */
        /* H1 contains merged result */

        BinQueue
        Merge( BinQueue H1, BinQueue H2 )
        {
            BinTree T1, T2, Carry = NULL;
            int i, j;

            if( H1->CurrentSize + H2->CurrentSize > Capacity )
                Error( "Merge would exceed capacity" );

            H1->CurrentSize += H2->CurrentSize;
            for( i = 0, j = 1; j <= H1->CurrentSize; i++, j *= 2 )
            {
                T1 = H1->TheTrees[ i ]; T2 = H2->TheTrees[ i ];

                switch( !!T1 + 2 * !!T2 + 4 * !!Carry )
                {
                    case 0: /* No trees */
                    case 1: /* Only H1 */
                        break;
                    case 2: /* Only H2 */
                        H1->TheTrees[ i ] = T2;
                        H2->TheTrees[ i ] = NULL;
                        break;
                    case 4: /* Only Carry */
                        H1->TheTrees[ i ] = Carry;
                        Carry = NULL;
                        break;
                    case 3: /* H1 and H2 */
                        Carry = CombineTrees( T1, T2 );
                        H1->TheTrees[ i ] = H2->TheTrees[ i ] = NULL;
                        break;
                    case 5: /* H1 and Carry */
                        Carry = CombineTrees( T1, Carry );
                        H1->TheTrees[ i ] = NULL;
                        break;
                    case 6: /* H2 and Carry */
                        Carry = CombineTrees( T2, Carry );
                        H2->TheTrees[ i ] = NULL;
                        break;
                    case 7: /* All three */
                        H1->TheTrees[ i ] = Carry;
                        Carry = CombineTrees( T1, T2 );
                        H2->TheTrees[ i ] = NULL;
                        break;
                }
            }
            return H1;
        }


		BinTree printTree(BinTree p, BinTree *r, int i)
		{
		  BinTree t[20]={NULL}, q; int j;
		  for ( j=0; j<i; j++ ) t[j]= r[j];
		  i=0;
		  if (p!=NULL) {
		    printf("& %2.1lf ",p->Item);
			q=p->NextSibling;
			j=0;
			do {
			  while (q!=NULL) {
			    printf("%2.1lf ",q->Item);
			    if (q->LeftChild != NULL) { r[i]=q->LeftChild; i++; }
			    q=q->NextSibling;
			  }
			  q=t[j++];
			} while (q!=NULL);
		  }
		  else return NULL;
		  //for (j=0; j<i; j++) t[j]=NULL;
		  printf("\n");
		  printTree(p->LeftChild, r, i);
		}


		//	/*
		main()
		{
		    /************************************
		    *  In Main, i try to                *
		    *  add the input file to            *
		    *  the binomial heap but            *
		    *  it also adds P1,P2 and           *
		    *  the rest of process id's         *
		    *  as zero.                         *
		    *                                   *
		    *                                   *
		    *                                   */
          char *k; double ret; char *ptr; int arr[100];int x=0,y=1,eMax=0,largest;
          char str[10000];
		  BinQueue H1, H2;
		  BinTree p, r[20]={NULL};
		  ElementType Item;
		  char ch;
		  int i;
		  H1 = Initialize( );
            FILE *fp;
            fp=fopen("input.txt","r");
             while(fscanf(fp,"%s",str)!=EOF){
                k=strtok(str," ");
                ret=strtod(k,&ptr);
                Item=ret;
                Insert(Item,H1);
            //    arr[x]=Item;
              //  printf("%d.%d\n",x,arr[x]);
            //    x++;


             //   Insert(Item,H1);
             }
            /*    largest=arr[0];
                for(i=0;i<60;i=i+3){printf("\n%d\n",largest);
                    if(largest<arr[i]){
                        largest=arr[i];

                    }
                }
            */
            for (i=0; i<MaxTrees; i++) {
			              p=H1->TheTrees[i];
						  printTree(p, r, 0); printf("/\n");

						}
						system("pause");
/*		  do {
		    printf("choice: \n"); scanf("%c",&ch);
		    switch (ch) {
			  case 'i': Item=(0.0001)*(rand()%10000);
			  Insert(Item, H1); printf("Item=%5.1lf",Item); if (IsEmpty( H1 )) printf("empty!");
			            break;
			  case 'd': if (!IsEmpty( H1 )) Item=DeleteMin( H1 );
                        break;
			  case 'p': for (i=0; i<MaxTrees; i++) {
			              p=H1->TheTrees[i];
						  printTree(p, r, 0); printf("/\n");
						}
						break;
			  case 'x': break;
			}
		  }	while ( ch!='x');
		  */
		}                                          // */
/* END */

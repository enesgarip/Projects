typedef double ElementType;
        #define Infinity (6000000000L)

        #ifndef _BinHeap_H
		
		#define MaxTrees (12)   /* Stores 2^9 -1 items */
        #define Capacity (4095)

    //  #define MaxTrees (14)   /* Stores 2^14 -1 items */
    //  #define Capacity (16383)
		
	//	#define MaxTrees (30)   /* Stores 2^30 -1 items */
    //  #define Capacity (1073741823)

        struct BinNode;
        typedef struct BinNode *BinTree;
        struct Collection;
        typedef struct Collection *BinQueue;

        BinQueue Initialize( void );
        void Destroy( BinQueue H );
        BinQueue MakeEmpty( BinQueue H );
        BinQueue Insert(ElementType Item, BinQueue H );
        ElementType DeleteMin( BinQueue H );
        BinQueue Merge( BinQueue H1, BinQueue H2 );
        ElementType FindMin( BinQueue H );
        int IsEmpty( BinQueue H );
        int IsFull( BinQueue H );
        #endif
/* END */
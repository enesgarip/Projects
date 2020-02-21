// Enes Garip-150116034

#include <stdio.h>
#include <stdlib.h>
#include <stdarg.h>
#include <string.h>
#include <math.h>
#include <locale.h>


FILE *inpf;
struct node {                           //node for Master LL
    char *word;
    int occurence;
	struct node *next;
	struct subnode *nextSub;
  };


struct subnode{                         //node for each node of Master LL points.
char *docID;
int occurenceInDoc;
int lengthOfDoc;
char *categoryName;
};
typedef struct node node;
typedef struct subnode subnode;
void insert(node**, char*);



int main(){
    setlocale(LC_ALL,"");
    node *hdr, *p, *q, *r;

    char buf[100];
    char str[2000];

    hdr=NULL;
inpf=fopen("dataset\\dataset\\econ\\1.txt","r");
    while (!feof(inpf))
{
fscanf(inpf,"%s\n",buf);
    sprintf(&str,buf);
    insert(&hdr,str);

    printf("%s\n",str);



}

fclose(inpf);

inpf=fopen("dataset\\dataset\\econ\\2.txt","r");
    while (!feof(inpf))
{
fscanf(inpf,"%s\n",buf);
    sprintf(&str,buf);
    insert(&hdr,str);

    printf("%s\n",str);
}
fclose(inpf);
inpf=fopen("dataset\\dataset\\econ\\3.txt","r");
    while (!feof(inpf))
{
fscanf(inpf,"%s\n",buf);
    sprintf(&str,buf);
    insert(&hdr,str);

    printf("%s\n",str);
}
fclose(inpf);
inpf=fopen("dataset\\dataset\\econ\\4.txt","r");
    while (!feof(inpf))
{
fscanf(inpf,"%s\n",buf);
    sprintf(&str,buf);
    insert(&hdr,str);

    printf("%s\n",str);
}
fclose(inpf);
inpf=fopen("dataset\\dataset\\econ\\5.txt","r");
    while (!feof(inpf))
{
fscanf(inpf,"%s\n",buf);
    sprintf(&str,buf);
    insert(&hdr,str);

    printf("%s\n",str);
}
fclose(inpf);
inpf=fopen("dataset\\dataset\\econ\\6.txt","r");
    while (!feof(inpf))
{
fscanf(inpf,"%s\n",buf);
    sprintf(&str,buf);
    insert(&hdr,str);

    printf("%s\n",str);
}
fclose(inpf);
inpf=fopen("dataset\\dataset\\health\\1.txt","r");
    while (!feof(inpf))
{
fscanf(inpf,"%s\n",buf);
    sprintf(&str,buf);
    insert(&hdr,str);

    printf("%s\n",str);
}
fclose(inpf);
inpf=fopen("dataset\\dataset\\health\\2.txt","r");
    while (!feof(inpf))
{
fscanf(inpf,"%s\n",buf);
    sprintf(&str,buf);
    insert(&hdr,str);

    printf("%s\n",str);
}
fclose(inpf);
inpf=fopen("dataset\\dataset\\health\\3.txt","r");
    while (!feof(inpf))
{
fscanf(inpf,"%s\n",buf);
    sprintf(&str,buf);
    insert(&hdr,str);

    printf("%s\n",str);
}
fclose(inpf);
inpf=fopen("dataset\\dataset\\health\\4.txt","r");
    while (!feof(inpf))
{
fscanf(inpf,"%s\n",buf);
    sprintf(&str,buf);
    insert(&hdr,str);

    printf("%s\n",str);
}
fclose(inpf);
inpf=fopen("dataset\\dataset\\health\\5.txt","r");
    while (!feof(inpf))
{
fscanf(inpf,"%s\n",buf);
    sprintf(&str,buf);
    insert(&hdr,str);

    printf("%s\n",str);
}
fclose(inpf);
 inpf=fopen("dataset\\dataset\\health\\6.txt","r");
    while (!feof(inpf))
{
fscanf(inpf,"%s\n",buf);
    sprintf(&str,buf);
    insert(&hdr,str);

    printf("%s\n",str);
}
fclose(inpf);
inpf=fopen("dataset\\dataset\\health\\7.txt","r");
    while (!feof(inpf))
{
fscanf(inpf,"%s\n",buf);
    sprintf(&str,buf);
    insert(&hdr,str);

    printf("%s\n",str);
}
fclose(inpf);
inpf=fopen("dataset\\dataset\\magazin\\1.txt","r");
    while (!feof(inpf))
{
fscanf(inpf,"%s\n",buf);
    sprintf(&str,buf);
    insert(&hdr,str);

    printf("%s\n",str);
}
fclose(inpf);
inpf=fopen("dataset\\dataset\\magazin\\2.txt","r");
    while (!feof(inpf))
{
fscanf(inpf,"%s\n",buf);
    sprintf(&str,buf);
    insert(&hdr,str);

    printf("%s\n",str);
}
fclose(inpf);
inpf=fopen("dataset\\dataset\\magazin\\3.txt","r");
    while (!feof(inpf))
{
fscanf(inpf,"%s\n",buf);
    sprintf(&str,buf);
    insert(&hdr,str);

    printf("%s\n",str);
}
fclose(inpf);
inpf=fopen("dataset\\dataset\\magazin\\4.txt","r");
    while (!feof(inpf))
{
fscanf(inpf,"%s\n",buf);
    sprintf(&str,buf);
    insert(&hdr,str);

    printf("%s\n",str);
}
fclose(inpf);
inpf=fopen("dataset\\dataset\\magazin\\5.txt","r");
    while (!feof(inpf))
{
fscanf(inpf,"%s\n",buf);
    sprintf(&str,buf);
    insert(&hdr,str);

    printf("%s\n",str);
}
fclose(inpf);
 p=hdr;
  while (p != NULL) {
    printf("%s\n",p->word);
	p=p->next;
  }



}

void insert(node ** header, char* word)
{
   node *p,*q,*r;



   p=malloc(sizeof(node));
   p->word=word;
   p->next=NULL;


   if (*header == NULL) *header=p;

     else {
     q=*header;
     while (q != NULL && strcmp(q->word,p->word)<0) {
       r=q;
	   q=q->next;
	 }
    if (q!=NULL && strcmp(q->word,p->word)==0) {printf(" "); return 0;}


	 if (q!=NULL) p->next=q;


     if (q==*header) *header=p;


	 else r->next=p;

   }


}

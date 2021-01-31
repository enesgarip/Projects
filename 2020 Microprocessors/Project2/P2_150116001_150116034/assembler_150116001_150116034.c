// to compile, gcc assembler.c -o assembler
// No error check is provided.
// Variable names cannot start with 0-9.
// hexadecimals are twos complement.
// first address of the code section is zero, data section follows the code section.
//fout tables are formed: jump table, ldi table, label table and variable table.
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

//Converts a hexadecimal string to integer.
int hex2int( char* hex)
{
    int result=0;

    while ((*hex)!='\0')
    {
        if (('0'<=(*hex))&&((*hex)<='9'))
            result = result*16 + (*hex) -'0';
        else if (('a'<=(*hex))&&((*hex)<='f'))
            result = result*16 + (*hex) -'a'+10;
        else if (('A'<=(*hex))&&((*hex)<='F'))
            result = result*16 + (*hex) -'A'+10;
        hex++;
    }
    return(result);
}


//Decimal to binary converter as the name suggests
char* dec2bin(int decNum){
	int i=0;
	int j, dividend;
	char* binNum=(char*)malloc(3);
	

	
	j = 2;
    while(!(j< 0)){
        dividend = decNum >> j;

        if (dividend & 1)
            *(binNum+i) = 1 + '0';
        else
            *(binNum+i) = 0 + '0';
        i++;
        j--;
    }
    *(binNum+i) = '\0';
    return binNum;
}
//binary to decimal converter as the name suggests
int bin2dec(int binNum){
	int converted=0;
	int i=0, remaining=0;
	while(binNum>0){
		remaining=binNum%10;
		binNum=binNum/10;
		converted=converted+remaining*pow(2,i);
		i++;
	}
	return converted;
}

void main()
{
    setbuf(stdout, NULL);
    FILE *fp;
    char line[100];
    char *token = NULL;
    char *op1, *op2, *op3, *label;
    char ch;
    int  chch;
    int ctr2=0;
	char * R0=bin2dec(0); //register 0 holds 0 value
    int program[1000];
    int counter=0;  //holds the address of the machine code instruction


    struct label
    {
        int location;
        char *label;
    };

    struct jumpinstruction
    {
        int location;
        char *label;
    };

    struct variable
    {
        int location;
        char *name;
    };

    struct ldiinstruction
    {
        int location;
        char *name;
    };
    
    struct ldiinstruction lditable[100];
    int noofldis=0;

	struct label labeltable[50]; 
    int nooflabels = 0; 
    
	struct jumpinstruction jumptable[100]; 
    int noofjumps=0; 
    
    struct variable variabletable[50]; 
    int noofvariables = 0;

    fp = fopen("calisankod.txt","r");

    if (fp != NULL)
    {
        while(fgets(line,sizeof line,fp)!= NULL)  //skip till .code section
        {
            token=strtok(line,"\n\t\r ");
            if (strcmp(token,".code")==0 )
                break;
        }
        while(fgets(line,sizeof line,fp)!= NULL)
        {
            token=strtok(line,"\n\t\r ");  //get the instruction mnemonic or label

//========================================   FIRST PASS  ======================================================
            while (token)
            {
                if (strcmp(token,"ldi")==0)        //---------------LDI INSTRUCTION--------------------
                {
                	ctr2=1;
                	printf("%d\n",ctr2);
                    op1 = strtok(NULL,"\n\t\r ");                                //get the 1st operand of ldi, which is the register that ldi loads
                    op2 = strtok(NULL,"\n\t\r ");                                //get the 2nd operand of ldi, which is the data that is to be loaded
                    program[counter]=0x1000+hex2int(op1);                        //generate the first 16-bit of the ldi instruction
                    counter++;                                                   //move to the second 16-bit of the ldi instruction
                    if ((op2[0]=='0')&&(op2[1]=='x'))                            //if the 2nd operand is twos complement hexadecimal
                        program[counter]=hex2int(op2+2)&0xffff;              //convert it to integer and form the second 16-bit
                    else if ((  (op2[0])=='-') || ((op2[0]>='0')&&(op2[0]<='9')))       //if the 2nd operand is decimal
                        program[counter]=atoi(op2)&0xffff;                         //convert it to integer and form the second 16-bit
                    else                                                           //if the second operand is not decimal or hexadecimal, it is a laber or a variable.
                    {                                                               //in this case, the 2nd 16-bits of the ldi instruction cannot be generated.
                        lditable[noofldis].location = counter;                 //record the location of this 2nd 16-bit
                        op1=(char*)malloc(sizeof(op2));                         //and the name of the label/variable that it must contain
                        strcpy(op1,op2);                                        //in the lditable array.
                        lditable[noofldis].name = op1;
                        noofldis++;
                    }
                    counter++;                                                     //skip to the next memory location
                }

                else if (strcmp(token,"ld")==0)      //------------LD INSTRUCTION---------------------
                {
                	ctr2=2;
                	printf("%d\n",ctr2);
                    op1 = strtok(NULL,"\n\t\r ");                //get the 1st operand of ld, which is the destination register
                    op2 = strtok(NULL,"\n\t\r ");                //get the 2nd operand of ld, which is the source register
                    
					ch = (op1[0]-48)| ((op2[0]-48)<<3);        //form bits 11-0 of machine code. 48 is ASCII value of '0'
                    program[counter]=0x2000+((ch)&0x003f);       //form the instruction and write it to memory
                    
					counter++;                                   //skip to the next empty location in memory
                }
                else if (strcmp(token,"st")==0) //-------------ST INSTRUCTION--------------------
                {
                	ctr2=3;
                	printf("%d\n",ctr2);
                    op1 = strtok(NULL,"\n\t\r ");                //get the 1st operand of st, which is the source  register
                    op2 = strtok(NULL,"\n\t\r ");				 //get the 2nd operand of st, which is the destination register
                    
                    chch = ((op1[0]-48)<<3)| ((op2[0]-48)<<6);
                    program[counter]=0x3000+((chch)&0x01ff);       //form the instruction and write it to memory
                    
					counter++;
                }
                else if (strcmp(token,"jz")==0) //------------- CONDITIONAL JUMP ------------------
                {
                	ctr2=4;
                	printf("%d\n",ctr2);
                    op1 = strtok(NULL,"\n\t\r ");			//read the label
                    
					jumptable[noofjumps].location = counter;	//write the jz instruction's location into the jumptable
                    
					op2=(char*)malloc(sizeof(op1)); 		//allocate space for the label
                    strcpy(op2,op1);				//copy the label into the allocated space
                    jumptable[noofjumps].label=op2;			//point to the label from the jumptable
                    noofjumps++;					//skip to the next empty location in jumptable
                    program[counter]=0x4000;			//write the incomplete instruction (just opcode) to memory

                    counter++;					//skip to the next empty location in memory.
                }
                else if (strcmp(token,"jmp")==0)  //-------------- JUMP -----------------------------
                {
                	ctr2=5;
                	printf("%d\n",ctr2);
                    op1 = strtok(NULL,"\n\t\r ");			//read the label
                    
					jumptable[noofjumps].location = counter;	//write the jz instruction's location into the jumptable
                    
					op2=(char*)malloc(sizeof(op1)); 		//allocate space for the label
                    strcpy(op2,op1);				//copy the label into the allocated space
                    jumptable[noofjumps].label=op2;			//point to the label from the jumptable
                    noofjumps++;					//skip to the next empty location in jumptable
                    program[counter]=0x5000;			//write the incomplete instruction (just opcode) to memory
                    counter++;					//skip to the next empty location in memory.
                }
                else if (strcmp(token,"add")==0) //----------------- ADD -------------------------------
                {
                	ctr2=6;
                	printf("%d\n",ctr2);
                	
                    op1 = strtok(NULL,"\n\t\r ");
                    op2 = strtok(NULL,"\n\t\r ");
                    op3 = strtok(NULL,"\n\t\r ");
                    chch = (op1[0]-48)| ((op2[0]-48)<<6)|((op3[0]-48)<<3);
                    program[counter]=0x7000+((chch)&0x0fff);
                    counter++;
                }
                else if (strcmp(token,"sub")==0)
                {
                	ctr2=7;
                	printf("%d\n",ctr2);
                	
                    op1 = strtok(NULL,"\n\t\r ");
                    op2 = strtok(NULL,"\n\t\r ");
                    op3 = strtok(NULL,"\n\t\r ");
                    chch = (op1[0]-48)| ((op2[0]-48)<<6)|((op3[0]-48)<<3);
                    program[counter]=0x7200+((chch)&0x01ff);
                    counter++;
                }
                else if (strcmp(token,"and")==0)
                {
                	ctr2=8;
                	printf("%d\n",ctr2);
                	
                    op1 = strtok(NULL,"\n\t\r ");
                    op2 = strtok(NULL,"\n\t\r ");
                    op3 = strtok(NULL,"\n\t\r ");
                    
                    chch = (op1[0]-48)| ((op2[0]-48)<<6)|((op3[0]-48)<<3);
                    program[counter]=0x7400+((chch)&0x01ff);
                    counter++;
                }
                else if (strcmp(token,"or")==0)
                {
                	ctr2=9;
                	printf("%d\n",ctr2);
                	
                    op1 = strtok(NULL,"\n\t\r ");
                    op2 = strtok(NULL,"\n\t\r ");
                    op3 = strtok(NULL,"\n\t\r ");
                    
                    chch = (op1[0]-48)| ((op2[0]-48)<<6)|((op3[0]-48)<<3);
                    program[counter]=0x7600+((chch)&0x01ff);
                    counter++;
                }
                else if (strcmp(token,"xor")==0)
                {
                	ctr2=10;
                	printf("%d\n",ctr2);
                	
                    op1 = strtok(NULL,"\n\t\r ");
                    op2 = strtok(NULL,"\n\t\r ");
                    op3 = strtok(NULL,"\n\t\r ");
                    
                    chch = (op1[0]-48)| ((op2[0]-48)<<6)|((op3[0]-48)<<3);
                    program[counter]=0x7800+((chch)&0x01ff);
                    counter++;
                }
                else if (strcmp(token,"not")==0)
                {
                	ctr2=11;
                	printf("%d\n",ctr2);
                	
                    op1 = strtok(NULL,"\n\t\r ");
                    op2 = strtok(NULL,"\n\t\r ");
                    
                    ch = (op1[0]-48)| ((op2[0]-48)<<3);
                    program[counter]=0x7e40+((ch)&0x003f);
                    counter++;
                }
                else if (strcmp(token,"mov")==0)
                {
                	ctr2=12;
                	printf("%d\n",ctr2);
                	
                    op1 = strtok(NULL,"\n\t\r ");
                    op2 = strtok(NULL,"\n\t\r ");
                    
                    ch = (op1[0]-48)| ((op2[0]-48)<<3);
                    program[counter]=0x7e40+((ch)&0x003f);
                    counter++;
                }
                else if (strcmp(token,"inc")==0)
                {
                	ctr2=13;
                	printf("%d\n",ctr2);
                	
                    op1 = strtok(NULL,"\n\t\r ");
                    ch = (op1[0]-48)| ((op1[0]-48)<<3);
                    
                    program[counter]=0x7e80+((ch)&0x003f);
                    counter++;
                }
                else if (strcmp(token,"dec")==0)
                {
                	ctr2=14;
                	printf("%d\n",ctr2);
                	
                    op1 = strtok(NULL,"\n\t\r ");
                    ch = (op1[0]-48)| ((op1[0]-48)<<3);
                    
                    program[counter]=0x7ec0+((ch)&0x003f);
                    counter++;
                }
                else if (strcmp(token,"push")==0)
                {
                	ctr2=15;
                	printf("%d\n",ctr2);
                	
                    op1 = strtok(NULL,"\n\t\r ");
                    ch = ((op1[0]-48)<<6);
                    program[counter]=0x8038+((ch)&0x00c0);
                    counter++;
                }
                else if (strcmp(token,"pop")==0)
				{
					ctr2=16;
                	printf("%d\n",ctr2);
                	
                    op1 = strtok(NULL,"\n\t\r ");
                    ch = (op1[0]-48);
                    program[counter]=0x9038+((ch)&0x0007);
                    counter++;
                }
                else if (strcmp(token,"call")==0)
                {
                	ctr2=17;
                	printf("%d\n",ctr2);
                	
                    op1 = strtok(NULL,"\n\t\r ");
                    
                    jumptable[noofjumps].location = counter;
                    
                    op2=(char*)malloc(sizeof(op1));
                    strcpy(op2,op1);
                    jumptable[noofjumps].label=op2;
                    noofjumps++;
                    program[counter]=0xa000;
                    counter++;
                }
                else if (strcmp(token,"ret")==0)
                {
                	ctr2=18;
                	printf("%d\n",ctr2);
                	
                    program[counter]=0xb000;			//write opcode to memory
                    counter++;					//go to the next empty location in memory
                }


                else
                {
                	ctr2=-1;
                	printf("%d\n",ctr2);
                    labeltable[nooflabels].location = counter;    //read the label and update labeltable.
                    op1=(char*)malloc(sizeof(token));
                    strcpy(op1,token);
                    labeltable[nooflabels].label=op1;
                    nooflabels++;
                }
                token = strtok(NULL,",\n\t\r ");
            }
        }


//================================= SECOND PASS ==============================


        int i,j;
        for (i=0; i<noofjumps;i++)        //for all jump/jz instructions
        {
            j=0;
            while ( strcmp(jumptable[i].label , labeltable[j].label) != 0 ) {            
                printf(">>>>%s\n",jumptable[i].label);
		    	printf("<<<<%s\n",labeltable[j].label);
            	j++;    
				printf("j: %d\n", j-1);                                                               
	    	}
			
			program[jumptable[i].location] +=(labeltable[j].location-jumptable[i].location-1)&0x0fff;    
        }




        // search for the start of the .data segment
        rewind(fp);
        while(fgets(line,sizeof line,fp)!= NULL)  //skip till .data, if no .data, also ok.
        {
            token=strtok(line,"\n\t\r ");
            if (strcmp(token,".data")==0 )
                break;

        }


        
        int dataarea=0;
        while(fgets(line,sizeof line,fp)!= NULL)
        {
            token=strtok(line,"\n\t\r ");
            if (strcmp(token,".code")==0 )  //go till the .code segment
                break;
            else if (token[strlen(token)-1]==':')
            {
            	printf("*");
                token[strlen(token)-1]='\0';  
                variabletable[noofvariables].location=counter+dataarea;
                op1=(char*)malloc(sizeof(token));
                strcpy(op1,token);
                variabletable[noofvariables].name=op1;
                token = strtok(NULL,",\n\t\r ");
                if (token==NULL)
                    program[counter+dataarea]=0;
                else if (strcmp(token, ".space")==0)
                {
                    token=strtok(NULL,"\n\t\r ");
                    dataarea+=atoi(token);
                }
                else if((token[0]=='0')&&(token[1]=='x'))
                    program[counter+dataarea]=hex2int(token+2)&0xffff;
                else if ((  (token[0])=='-') || ('0'<=(token[0])&&(token[0]<='9'))  )
                    program[counter+dataarea]=atoi(token)&0xffff;
                noofvariables++;
                dataarea++;
            }
        }

// supply the address fields for the ldi instructions from the variable table
        for( i=0; i<noofldis;i++)
        {
            j=0;
            while ((j<noofvariables)&&( strcmp( lditable[i].name , variabletable[j].name)!=0 ))
                j++;
            if (j<noofvariables)
                program[lditable[i].location] = variabletable[j].location;
        }

// supply the address fields for the ldi instructions from the label table
        for( i=0; i<noofldis;i++)
        {
            j=0;
            while ((j<nooflabels)&&( strcmp( lditable[i].name , labeltable[j].label)!=0 ))
                j++;
            if (j<nooflabels){
                program[lditable[i].location] = (labeltable[j].location)&0x0fff;
                printf("%d %d %d\n", i, j, (labeltable[j].location));
            }
        }

//display the resulting tables
        printf("LABEL TABLE\n");
        for (i=0;i<nooflabels;i++)
            printf("%d %s\n", labeltable[i].location, labeltable[i].label);
        printf("\n");
        printf("JUMP TABLE\n");
        for (i=0;i<noofjumps;i++)
            printf("%d %s\n", jumptable[i].location, jumptable[i].label);
        printf("\n");
        printf("VARIABLE TABLE\n");
        for (i=0;i<noofvariables;i++)
            printf("%d %s\n", variabletable[i].location, variabletable[i].name);
        printf("\n");
        printf("LDI INSTRUCTIONS\n");
        for (i=0;i<noofldis;i++)
            printf("%d %s\n", lditable[i].location, lditable[i].name);
        printf("\n");
        fclose(fp);
        fp = fopen("C:\\intelFPGA_lite\\18.0\\FPGACodes\\pocket_calculator\\ram.dat","w");
        //fprintf(fp,"v2.0 raw\n");
        for (i=0;i<counter+dataarea;i++){
            fprintf(fp,"%04x\n",program[i]);
            printf("%04x\n",program[i]);
        }

    }

}

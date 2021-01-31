// to compile, gcc assembler.c -o assembler
// No error check is provided.
// Variable names cannot start with numeric characters, ie, with 0-9.
// hexadecimals are twos complement.
// first address of the code section is zero, and the data section follows the code section in memory.
// four tables are formed: jump table, ldi table, label table and variable table.
 
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>


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

int bin2hex(int binNum)
{
    int i = 1,hexNum= 0, rem;

    while (binNum != 0)
    {
        hexNum = hexNum + (binNum % 10)* i;
        i = i * 2;
        binNum = binNum / 10;
    }

    return hexNum;
}

main()
{     
    FILE *fp;
    char line[100];
    char *token = NULL; 
    char *op1, *op2, *op3, *label;
    char ch;
    int  chch;
    int program[1000];
    int counter=0;  //holds the address of the machine code instruction
    

	
	
 	
 
    struct label  
    {
        int location;
        char *name;
    };
    struct variable  
    {
        int location;
        char *name;
    };
    struct jumpStr  
    {
        int location;
        char *name;
    };
    struct ldiStr  
    {
        int location;
        char *name;
    };
 
// A label is a symbol which mark a location within the code section. In the example 
// program above, the strings "lpp", "loop" and "lp1" are labels. 
// In reptile, labels are used by jump, jz and ldi instructions.  
    struct label labeltable[50]; //there can be 50 labels at most in our programs
    int nooflabels = 0;          //number of labels encountered during assembly.
 
 // A variable is a symbol which mark a location within the data section. In the example 
// program above, the strings "", "" and "" are variables.
// In reptile, variables are used by ldi instructions.
    struct variable variabletable[50]; // The list of variables in .data section and their locations.
    int noofvariables = 0;    //number of jumps encountered during assembly. 
 
// Jump instructions cannot be assembled readily because we may not know the value of 
// the label when we encountered a jump instruction. This happens if the label used by
// that jump instruction appear below that jump instruction. This is the situation 
// with the label "loop" in the example program above. Hence, the location of jump 
// instructions must be stored.
    struct jumpStr jumptable[100]; //There can be at most 100 jumps
    int noofjumps=0;               //number of jump instructions encountered during assembly.    
 
 
//Variables and labels are used by ldi instructions.
//The memory for the variables are traditionally allocated at the end of the code section.
//Hence their addresses are not known when we assemble a ldi instruction. Also, the value of 
//a label may not be known when we encounter a ldi instruction which uses that label.
//Hence, the location of the ldi instructions must be kept, and these instructions must be 
//modified when we discover the address of the label or variable that it uses.
    struct ldiStr lditable[100];
    int noofldis=0;
 
 
 
    fp = fopen("deneme.txt","r");
 
    if (fp != NULL)
    {
    	counter=0;
        while(fgets(line,sizeof line,fp)!= NULL)  //skip till .code section
        {
        	
            token=strtok(line,"\n\t\r ");
            if (strcmp(token,".code")==0 )
                break;
        } 
        printf("[][]%s\n",token);
        while(fgets(line,sizeof line,fp)!= NULL)
        {
            token=strtok(line,"\n\t\r ");  //get the instruction mnemonic or label
 			printf("[]%s\n",token);
			
			
			
			if (token[strlen(token) - 1] == ':')
            {
                char *ptr = token;
                ptr[strlen(token)-1] = 0;
                
                labeltable[nooflabels].location = counter;
                op1=(char*)malloc(sizeof(token));
                strcpy(op1,token);
                labeltable[nooflabels].name=op1;
				
                nooflabels++;

                counter--;
            }

            if (strcmp(token,"ldi")==0)
            {
                counter+=2;

            }
            else{
            	counter+=1;
			}
                
		}
	}
	
	fp = fopen("deneme.txt","r");
	counter=0;
	
	while(fgets(line,sizeof line,fp)!= NULL)
    {
        token=strtok(line,"\n\t\r ");
        if (strcmp(token,".code")==0)
        {
            break;
        }
    }
	
	while(fgets(line,sizeof line,fp)!= NULL)  //skip till .code section
    {
    	token=strtok(line,"\n\t\r ");
//========================================   FIRST PASS  ====================================================== 
        while (token)
        {
			printf("^^^%s", token);
			
            if (strcmp(token,"ldi")==0)        //---------------LDI INSTRUCTION--------------------
            {
            	printf("%0");
                op1 = strtok(NULL,"\n\t\r ");                                //get the 1st operand of ldi, which is the register that ldi loads
                op2 = strtok(NULL,"\n\t\r ");                                //get the 2nd operand of ldi, which is the data that is to be loaded
                program[counter]=0x1000+(int)strtol(op1, NULL, 0);           //generate the first 16-bit of the ldi instruction
                counter++;                                                   //move to the second 16-bit of the ldi instruction
                if ((op2[0]=='0')&&(op2[1]=='x')){                            //if the 2nd operand is twos complement hexadecimal
                    printf("%1");
					program[counter]=hex2int(op2+2)&0xffff;              //convert it to integer and form the second 16-bit 
            	
				}
				else if ((  (op2[0])=='-') || ((op2[0]>='0')&&(op2[0]<='9'))){       //if the 2nd operand is decimal 
                    printf("%2");
					program[counter]=atoi(op2)&0xffff;                         //convert it to integer and form the second 16-bit 
            	}
				else                                                           //if the second operand is not decimal or hexadecimal, it is a laber or a variable.
                {           
					printf("%3");
					lditable[noofldis].location = counter;                 //record the location of this 2nd 16-bit  
                    op1=(char*)malloc(sizeof(op2));                         //and the name of the label/variable that it must contain
                    strcpy(op1,op2);                                        //in the lditable array.
                    lditable[noofldis].name = op1;
                    printf("???%d", lditable[noofldis].location);                                                    //in this case, the 2nd 16-bits of the ldi instruction cannot be generated.
                    printf("???%s", lditable[noofldis].name);
					noofldis++;    
					                                         
                }        
                counter++;                                                     //skip to the next memory location 
				
            }                                       

            else if (strcmp(token,"ld")==0)      //------------LD INSTRUCTION---------------------         
            {
                op1 = strtok(NULL,"\n\t\r ");                //get the 1st operand of ld, which is the destination register
                op2 = strtok(NULL,"\n\t\r ");                //get the 2nd operand of ld, which is the source register
                char * regs=dec2bin(0);
				ch = (op1[0]-48)| ((op2[0]-48) << 3);        //form bits 11-0 of machine code. 48 is ASCII value of '0'

                
                char inst_str[9];
				strcpy(inst_str,"");
                strcat(inst_str, regs);
                strcat(inst_str, regs);
                strcat(inst_str, dec2bin((int)(op2[0]-48)));
                strcat(inst_str, dec2bin((int)(op1[0]-48)));
                
				program[counter]=0x2000+(bin2hex(atoi(inst_str)&0xffff));       //form the instruction and write it to memory
                
				counter++;                                   //skip to the next empty location in memory
            
			}
            else if (strcmp(token,"st")==0) //-------------ST INSTRUCTION--------------------
            {
                op1 = strtok(NULL,"\n\t\r ");                //get the 1st operand of ld, which is the destination register
                op2 = strtok(NULL,"\n\t\r ");                //get the 2nd operand of ld, which is the source register
                char * regs=dec2bin(0);
				ch = (op1[0]-48)| ((op2[0]-48) << 3);        //form bits 11-0 of machine code. 48 is ASCII value of '0'

                
                char inst_str[20];
                strcat(inst_str, regs);
                strcat(inst_str, dec2bin((int)(op1[0]-48)));
                strcat(inst_str, dec2bin((int)(op2[0]-48)));
                strcat(inst_str, regs);
                
				program[counter]=0x3000+((strtol(inst_str, NULL,2))&0x01ff);       //form the instruction and write it to memory
                
				counter++;                                   //skip to the next empty location in memory
            
            }
            else if (strcmp(token,"jz")==0) //------------- CONDITIONAL JUMP ------------------
            {
                op1=strtok(NULL,"\n\t\r ");	
                printf("\n\t%s\t%s\n",strupr(token),op1);
                jumptable[noofjumps].location = counter;	
                op2=(char*)malloc(sizeof(op1)); 		
                strcpy(op2,op1);				
                jumptable[noofjumps].name=op2;			
                noofjumps++;					
                program[counter]=0x4000;
                counter++;
				
            }
                            
            else if (strcmp(token,"add")==0) //----------------- ADD -------------------------------
            {
                op1 = strtok(NULL,"\n\t\r ");    
                op2 = strtok(NULL,"\n\t\r ");
                op3 = strtok(NULL,"\n\t\r ");
                
				chch = (op1[0]-48)| ((op3[0]-48)<<3)|((op2[0]-48)<<6) | (0<<9);  
                
                program[counter]=0x7000+((chch)&0xffff); 
                
				counter++; 

            }
            else if (strcmp(token,"sub")==0)
            {
                op1 = strtok(NULL,"\n\t\r ");    
                op2 = strtok(NULL,"\n\t\r ");
                op3 = strtok(NULL,"\n\t\r ");
                
				chch = (op1[0]-48)| ((op3[0]-48)<<3)|((op2[0]-48)<<6) | (1<<9);  
                
                program[counter]=0x7000+((chch)&0xffff); 
                
				counter++; 
            }
            else if (strcmp(token,"and")==0)
            {
				op1 = strtok(NULL,"\n\t\r "); 
                op2 = strtok(NULL,"\n\t\r "); 
                op3 = strtok(NULL,"\n\t\r "); 
                
                char * regs=dec2bin(2);
                
				char inst_str[20];
                strcat(inst_str, regs);
                strcat(inst_str, dec2bin((int)(op2[0]-48)));
                strcat(inst_str, dec2bin((int)(op3[0]-48)));
                strcat(inst_str, dec2bin((int)(op1[0]-48)));
				
				program[counter]=0x7000+((strtol(inst_str, NULL,2))&0xffff);
				
				counter++;

            }
            else if (strcmp(token,"or")==0)
            {
                op1 = strtok(NULL,"\n\t\r ");
                op2 = strtok(NULL,"\n\t\r ");
                op3 = strtok(NULL,"\n\t\r ");
                
                char * regs=dec2bin(3);
                
				char inst_str[20];
				
				strcpy(inst_str, regs);
                strcat(inst_str, dec2bin((int)(op2[0]-48)));
                strcat(inst_str, dec2bin((int)(op3[0]-48)));
                strcat(inst_str, dec2bin((int)(op1[0]-48)));
                
				program[counter]=0x7000+((strtol(inst_str, NULL,2))&0xffff);
				
				counter++;
            }
            else if (strcmp(token,"xor")==0)
            {
                op1 = strtok(NULL,"\n\t\r ");
                op2 = strtok(NULL,"\n\t\r ");
                op3 = strtok(NULL,"\n\t\r ");
                
                char * regs=dec2bin(4);
                
				char inst_str[20];
				
				strcpy(inst_str, regs);
                strcat(inst_str, dec2bin((int)(op2[0]-48)));
                strcat(inst_str, dec2bin((int)(op3[0]-48)));
                strcat(inst_str, dec2bin((int)(op1[0]-48)));
                
				program[counter]=0x7000+((strtol(inst_str, NULL,2))&0xffff);
				
				counter++;
            }  
			else if (strcmp(token,"inc")==0)
            {
                op1 = strtok(NULL,"\n\t\r ");
                ch = (op1[0]-48)| ((op1[0]-48)<<3);
                program[counter]=0x7e80+((ch)&0xffff);  
                counter++;
				
            }
            else if (strcmp(token,"dec")==0)
            {
				op1 = strtok(NULL,"\n\t\r ");
                ch = ((int)(op1[0]-48))| (((int)(op1[0]-48))<<3);
                program[counter]=0x7ec0+((ch)&0xffff);  
                counter++;
				
            }                      
            else if (strcmp(token,"not")==0)
            {
                op1 = strtok(NULL,"\n\t\r ");
                op2 = strtok(NULL,"\n\t\r ");
                ch = (op1[0]-48) | ((op2[0]-48)<<3 | 0<<6 | 7 <<9);
                program[counter]=0x7e00+((ch)&0xffff);  
                counter++;
            }
            else if (strcmp(token,"mov")==0)
            {
                op1 = strtok(NULL,"\n\t\r ");
                op2 = strtok(NULL,"\n\t\r ");
                ch = (op1[0]-48) | ((op2[0]-48)<<3) | 1<<6  ;
                program[counter]=0x7e00+((ch)&0xffff); 
                counter++;
            }
            else if (strcmp(token,"jmp")==0) 
            {
                op1 = strtok(NULL,"\n\t\r ");            
                jumptable[noofjumps].location = counter;    
                op2=(char*)malloc(sizeof(op1));                         
                strcpy(op2,op1);               
                jumptable[noofjumps].name=op2;           
                noofjumps++;                    
                program[counter]=0x5000;       
				     
                counter++;   
			
            }
            else if (strcmp(token,"push")==0)
            {
        		op1 = strtok(NULL,"\n\t\r ");

			
				
				
				
				char inst_str[30];
				char* s=dec2bin((int)(op1[0]-48));
                strcpy(inst_str, dec2bin(0));
                strcat(inst_str, s );
                strcat(inst_str, dec2bin(7)); 
                strcat(inst_str, dec2bin(0));

                program[counter]=0x8000+((strtol(inst_str, NULL, 2))&0x01ff);

                counter++;
			}
			else if (strcmp(token,"pop")==0)
            {
            	op1 = strtok(NULL,"\n\t\r ");

				
				
				
				char inst_str[30];
				char* s=dec2bin((int)(op1[0]-48));
                strcpy(inst_str, dec2bin(0));
                strcat(inst_str, dec2bin(0));
                strcat(inst_str, dec2bin(7)); 
				strcat(inst_str, s);
                program[counter]=0x9000+((strtol(inst_str, NULL, 2))&0xffff);

                counter++;
        	}
			else if (strcmp(token,"call")==0)
            {
            	op1 = strtok(NULL,"\n\t\r ");			
                
                jumptable[noofjumps].location =counter;	
                op2=(char*)malloc(sizeof(op1)); 	
                strcpy(op2,op1);				
                jumptable[noofjumps].name=op2;	
                noofjumps++;					

                program[counter]=0xa000;
                counter++;
        	}
        	else if (strcmp(token,"ret")==0)
			{
        		program[counter]=0xb000; 
                counter++;
			}
            
            token = strtok(NULL,",\n\t\r ");  // if what is read before is an instruction, this will be NULL
                                              //if what is read before is an label, this will be an opcode.
        }
    
 }
 
 
//================================= SECOND PASS ==============================

//supply the address fields of the jump and jz instructions by matching jumptable and labeltable
        int i, j;         
        for (i=0; i<noofjumps;i++)   //for all jump/jz instructions encountered
        {
            j=0;
			
			printf("i: %d\n", i);
            while (( strcmp(jumptable[i].name , labeltable[j].name ) != 0 )){
				printf(">>>>%s\n",jumptable[i].name);
				printf("<<<<%s\n",labeltable[j].name);
				j++;    
				printf("j: %d\n", j);
			} 
			/*  
			int a=labeltable[j].location;
			printf("a: %d\n", a);
			int b=jumptable[i].location;
			printf("b: %d\n", b);
		   */
		   program[jumptable[i].location] +=(labeltable[j].location-jumptable[i].location-1)&0x0fff;       //copy the jump address into memory.
			
		}                                                     

 
//search for the start of the .data segment

        rewind(fp);  
		
        while(fgets(line,sizeof line,fp)!= NULL)  //skip till .data, if no .data, also ok.
        {
			
            token=strtok(line,"\n\t\r ");
            if (strcmp(token,".data")==0 )
                break;
 
        }

// process the .data segment and generate the variabletable[] array.
        
		int dataarea=0;
        while(fgets(line,sizeof line,fp)!= NULL)
        {
			
            token=strtok(line,"\n\t\r ");
            if (strcmp(token,".code")==0 ){  //go till the .code segment
				break;
			}
            else if (token[strlen(token)-1]==':')
            {                
                token[strlen(token)-1]='\0';  //will not cause memory leak, as we do not do malloc
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
                    program[counter+dataarea]=(int)strtol(token, NULL, 0)&0xffff;
                else if ((  (token[0])=='-') || ('0'<=(token[0])&&(token[0]<='9'))  )
                    program[counter+dataarea]=atoi(token)&0xffff;  
                noofvariables++;
                dataarea++;
            }
        }



	printf("LABEL TABLE\n");
    for (i=0; i<nooflabels; i++)
        printf("%d %s\n", labeltable[i].location, labeltable[i].name);
        
    fclose(fp);

    


	 printf("counter: %d\n",counter);
    for (i=0; i<counter; i++)
    {
    	//printf("i: %d\n",i);
        printf("%04x\n",program[i]);
    }		

    
    fp = fopen("output_yeni.hex","w"); 
    fprintf(fp,"v2.0 raw\n");  
    for (i=0; i<counter+dataarea; i++){
		
        fprintf(fp,"%04x\n",program[i]);
	}
    fclose(fp);

    fp = fopen("output_yeni.txt","w");
    for (i=0; i<counter; i++) 
        fprintf(fp,"%04x\n",program[i]);

    for (i=0; i<512-counter; i++){
        
		fprintf(fp,"%04x\n",0000);
	}
    fclose(fp);
}







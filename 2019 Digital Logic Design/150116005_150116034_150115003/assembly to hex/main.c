#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

char * convertHex(char*);
char * decimalToHex(char*,int);
char * binaryToHex(char*,int,int);

FILE *fp, *fp1;
char line[100];

int main()
{
    if((fp = fopen("input.txt", "r")) != NULL)
    {
        fp1 = fopen("output.hex", "w");
        fprintf(fp1,"v2.0 raw\n");
        while(fgets(line, sizeof(line), fp))
        {
            char *temp, *temp1, *temp2;
            temp = strtok(line, " ,\n");

            if(strcmp(temp, "ADD") == 0)
            {
                temp = strtok(NULL, " ,R\n");
                temp1 = strtok(NULL, " ,R\n");
                temp2 = strtok(NULL, " ,R\n");
                fprintf(fp1, "0%s%s%s0 ", convertHex(temp), convertHex(temp1), convertHex(temp2));
            }
            else if(strcmp(temp, "AND") == 0)
            {
                temp = strtok(NULL, " ,R\n");
                temp1 = strtok(NULL, " ,R\n");
                temp2 = strtok(NULL, " ,R\n");
                fprintf(fp1, "0%s%s%s1 ", convertHex(temp), convertHex(temp1), convertHex(temp2));
            }
            else if(strcmp(temp, "OR") == 0)
            {
                temp = strtok(NULL, " ,R\n");
                temp1 = strtok(NULL, " ,R\n");
                temp2 = strtok(NULL, " ,R\n");
                fprintf(fp1, "0%s%s%s2 ", convertHex(temp), convertHex(temp1), convertHex(temp2));
            }
            else if(strcmp(temp, "XOR") == 0)
            {
                temp = strtok(NULL, " ,R\n");
                temp1 = strtok(NULL, " ,R\n");
                temp2 = strtok(NULL, " ,R\n");
                fprintf(fp1, "0%s%s%s3 ", convertHex(temp), convertHex(temp1), convertHex(temp2));
            }
            else if(strcmp(temp, "ADDI") == 0)
            {
                temp = strtok(NULL, " ,R\n");
                temp1 = strtok(NULL, " ,R\n");
                temp2 = strtok(NULL, " ,R\n");
                fprintf(fp1, "%s%s%s4 ", decimalToHex(temp2,6), convertHex(temp), convertHex(temp1));
            }
            else if(strcmp(temp, "ANDI") == 0)
            {
                temp = strtok(NULL, " ,R\n");
                temp1 = strtok(NULL, " ,R\n");
                temp2 = strtok(NULL, " ,R\n");
                fprintf(fp1, "%s%s%s5 ", decimalToHex(temp2,6), convertHex(temp), convertHex(temp1));
            }
            else if(strcmp(temp, "ORI") == 0)
            {
                temp = strtok(NULL, " ,R\n");
                temp1 = strtok(NULL, " ,R\n");
                temp2 = strtok(NULL, " ,R\n");
                fprintf(fp1, "%s%s%s6 ", decimalToHex(temp2,6), convertHex(temp), convertHex(temp1));
            }
            else if(strcmp(temp, "XORI") == 0)
            {
                temp = strtok(NULL, " ,R\n");
                temp1 = strtok(NULL, " ,R\n");
                temp2 = strtok(NULL, " ,R\n");
                fprintf(fp1, "%s%s%s7 ", decimalToHex(temp2,6), convertHex(temp), convertHex(temp1));
            }
            else if(strcmp(temp, "JUMP") == 0)
            {
                temp = strtok(NULL, " ,R\n");
                fprintf(fp1, "%s8 ", decimalToHex(temp,14));
            }
            else if(strcmp(temp, "BGT") == 0)
            {
                temp = strtok(NULL, " ,R\n");
                temp1 = strtok(NULL, " ,R\n");
                temp2 = strtok(NULL, " ,R\n");
                fprintf(fp1, "%s%s%s9 ", decimalToHex(temp2,6), convertHex(temp), convertHex(temp1));
            }
            else if(strcmp(temp, "BEQ") == 0)
            {
                temp = strtok(NULL, " ,R\n");
                temp1 = strtok(NULL, " ,R\n");
                temp2 = strtok(NULL, " ,R\n");
                fprintf(fp1, "%s%s%sA ", decimalToHex(temp2,6), convertHex(temp), convertHex(temp1));
            }
            else if(strcmp(temp, "BGE") == 0)
            {
                temp = strtok(NULL, " ,R\n");
                temp1 = strtok(NULL, " ,R\n");
                temp2 = strtok(NULL, " ,R\n");
                fprintf(fp1, "%s%s%sB ", decimalToHex(temp2,6), convertHex(temp), convertHex(temp1));
            }
            else if(strcmp(temp, "BLT") == 0)
            {
                temp = strtok(NULL, " ,R\n");
                temp1 = strtok(NULL, " ,R\n");
                temp2 = strtok(NULL, " ,R\n");
                fprintf(fp1, "%s%s%sC ", decimalToHex(temp2,6), convertHex(temp), convertHex(temp1));
            }
            else if(strcmp(temp, "LD") == 0)
            {
                temp = strtok(NULL, " ,R\n");
                temp1 = strtok(NULL, " ,R\n");
                fprintf(fp1, "%s%sD ", decimalToHex(temp1, 10), convertHex(temp));
            }
            else if(strcmp(temp, "BLE") == 0)
            {
                temp = strtok(NULL, " ,R\n");
                temp1 = strtok(NULL, " ,R\n");
                temp2 = strtok(NULL, " ,R\n");
                fprintf(fp1, "%s%s%sE ", decimalToHex(temp2,6), convertHex(temp), convertHex(temp1));
            }
            else if(strcmp(temp, "ST") == 0)
            {
                temp = strtok(NULL, " ,R\n");
                temp1 = strtok(NULL, " ,R\n");
                fprintf(fp1, "%s%sF ", decimalToHex(temp1, 10), convertHex(temp));
            }
        }
        fprintf(fp1, "\n\n");
        fclose(fp);
        fclose(fp1);
        printf("The output file has written\n");
    }
    return 0;
}

char * convertHex(char *x)
{
    if(strcmp(x, "10") == 0)
        return "A";
    else if(strcmp(x, "11") == 0)
        return "B";
    else if(strcmp(x, "12") == 0)
        return "C";
    else if(strcmp(x, "13") == 0)
        return "D";
    else if(strcmp(x, "14") == 0)
        return "E";
    else if(strcmp(x, "15") == 0)
        return "F";
    else
        return x;
}

char * decimalToHex(char *x,int ins)
{
    int n = atoi(x), k = 0;

    if(n < 0)
    {
        k = -1;
        n *= -1;
        n -= 1;
    }

    int i;
    char result[14];
    char *result1 = (char*)malloc(14);

    for(i=0; n>0; i++)
    {
        result[i] = 48 + (n%2);
        n = n/2;
    }
    result[i] = '0';
    result[i+1] = '\0';
    strcpy(result1, result);
    strrev(result1);

    if(k == 0)
    {
        char temp[16] = "000000000000000";
        for(i = 14; i > 14 - strlen(result1); i--)
        {
            temp[i] = result1[i - 14 + strlen(result1)];
        }
        char *result2 = (char*)malloc(14);
        strcpy(result2, temp);
        return binaryToHex(result2,k,ins);
    }
    else
    {
        for(i = 0; i < strlen(result1); i++ )
        {
            if(result1[i] == '0')
                result1[i] = '1';
            else
                result1[i] = '0';
        }
        char temp[16] = "111111111111111";
        for(i = 14; i > 14 - strlen(result1); i--)
        {
            temp[i] = result1[i - 14 + strlen(result1)];
        }
        char *result2 = (char*)malloc(14);
        strcpy(result2, temp);
        return binaryToHex(result2,k,ins);
    }
}

char * binaryToHex(char *x,int k,int ins)
{

    char result4[5];
    char t1[3], t2[3], t3[3], t4[3];
    char *temp = (char*)malloc(5);
    int i, j, sum = 0;
    for(i = 0; i < 15;)
    {
        if(i<2)
        {
            for(j = 1; j >= 0; j--)
            {
                sum += (x[i]- 48)*(int)pow(2,j);
                i++;
            }
            if(i == 2)
            {
                sprintf(t1, "%d", sum);

                sum = 0;
            }
        }
        for(j = 3; j >= 0; j--)
        {
            sum += (x[i]- 48)*(int)pow(2,j);
            i++;
        }
        if(i == 6)
        {
            sprintf(t2, "%d", sum);
            sum = 0;
        }
        else if(i == 10)
        {
            sprintf(t3, "%d", sum);
            sum = 0;
        }
        else if(i == 14)
        {
            sprintf(t4, "%d", sum);
            sum = 0;
        }
        else if(i == 14)
        {
            sprintf(t4,"%d",sum);
        }
    }

    if(ins==14)
    {
        sprintf(result4, "%s%s%s%s", convertHex(t1), convertHex(t2), convertHex(t3), convertHex(t4));
    }
    else if(ins == 6 && k == 0)
    {
        sprintf(result4, "%s%s",  convertHex(t3), convertHex(t4));

    }
    else if(ins == 6 && k == -1)
    {
        if(strcmp(t1,"3") == 0 && strcmp(t4,"0") == 0)
            strcpy(t1,"2");

        sprintf(result4, "%s%s",  convertHex(t1), convertHex(t4));
    }
    else if(ins == 10 && k == 0)
    {
        sprintf(result4, "%s%s%s",  convertHex(t2),convertHex(t3), convertHex(t4));

    }
    else if(ins == 10 && k == -1)
    {
        if(strcmp(t1,"3") == 0 && strcmp(t3,"0") == 0 && strcmp(t4,"0") == 0)
        {
            strcpy(t1,"2");
        }
        sprintf(result4, "%s%s%s",  convertHex(t1), convertHex(t3), convertHex(t4));
    }
    strcpy(temp, result4);
    return temp;
}

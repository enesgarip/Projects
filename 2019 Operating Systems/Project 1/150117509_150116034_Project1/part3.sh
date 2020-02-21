#!/bin/bash

# Project-1 
# Mustafa Abdullah Hakkoz - 150117509
# Enes Garip - 150116034

# Part-3 DRAW HOLLOWED SQUARE
# A script to draw an hollowed square with stars based on 2 numbers given.
# It works with 2 integers as argument. First argument must be greater than the second one. Also their difference must be even 
# number. First int will define outer square and second int will define inner square.
# 
# 
# Usage: ./part3.sh integer integer

LIMIT=99
E_NO_ARG=1
E_ONE_ARG=2
E_EXCESS_ARG=3
E_FIRST_NO_INT=4
E_FIRST_NO_LIMIT=5
E_SEC_NO_INT=6
E_SEC_NO_LIMIT=7
E_ARG1_LESS_ARG2=8
E_ARGS_NO_EVEN=9

# error when there's no input argument
if [ $# -eq 0 ]
then
	echo "Error - No input arguments."
    	echo "Syntax should be: $0 integer integer"
	exit $E_NO_ARG
fi

# error when there's no second input argument
if [ $# -eq 1 ]
then
	echo "Error - Second input is missing!"
    	echo "Syntax should be: $0 integer integer"
	exit $E_ONE_ARG
fi

# error when there's more than 2 input argument
if [ $# -gt 2 ]
then
	echo "Error - Too many arguments."
	echo "Syntax should be: $0 integer integer"
	exit $E_EXCESS_ARG
fi

# error when first argument is not integer
if ! [[ $1 =~ ^[0-9]+$ ]]
then
	echo "Error - First argument is not integer."
	echo "Syntax should be: $0 integer integer"
	exit $E_FIRST_NO_INT

# error when first argument is not in limits (<1 or >$LIMIT)
elif [ $1 -lt 1 ] || [ $1 -gt $LIMIT ]
then
	echo "Error - First argument is not in limits (0 > int1 >= $LIMIT)."
	echo "Syntax should be: $0 int1 int2"
	exit $E_FIRST_NO_LIMIT
fi

# error when second argument is not integer
if ! [[ $2 =~ ^[0-9]+$ ]]
then
	echo "Error - Second argument is not integer."
	echo "Syntax should be: $0 integer integer"
	exit $E_SEC_NO_INT

# error when second argument is not in limits (<0 or >$LIMIT)
elif [ $2 -lt 0 ] || [ $2 -gt $LIMIT ]
then
	echo "Error - Second argument is not in limits (0 >= int1 >= $LIMIT)."
	echo "Syntax should be: $0 int1 int2"
	exit $E_SEC_NO_LIMIT
fi

# error when first argument not greater than second one
if ! [ $1 -gt $2 ]
then
	echo "Error - First argument must be greater than second one."
	echo "Syntax should be: $0 int1 int2"
	exit $E_ARG1_LESS_ARG2

# error when their difference not an even number
else
	difference=`expr $1 - $2`
	if (( $difference % 2 ))
	then
		echo "Error - Difference of integers must be an even number."
		echo "Syntax should be: $0 int1 int2"
		exit $E_ARGS_NO_EVEN
	fi
fi

#############
# MAIN CODE #	
#############

#assign arguments
first=$1
second=$2	

#calculate borders
let "x=((first+second)/2)"
let "y=((first-second)/2)"

#print spaces first, then print stars
for ((i=1;i<=first;i++))
do
	for ((j=1;j<=first;j++))
	do
		if [ $i -gt $y ] && [ $i -le $x ] && [ $j -gt $y ] && [ $j -le $x ]
		then 
			echo -n " "
		else
			echo -n "*"
		fi
	done
	echo
done

		
	 
exit 0



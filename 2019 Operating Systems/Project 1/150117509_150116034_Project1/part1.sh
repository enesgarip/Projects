#!/bin/bash

# Project-1 
# Mustafa Abdullah Hakkoz - 150117509
# Enes Garip - 150116034

# Part-1 CHECK FOR POLINDROMES
# A script to check if a given string is a polindrome or not.
# It works with single words w/wo quotes as input.
# But when it comes to sentences with many words it requires to use it with quotes to prevent multi arguments.
#  
# Usage: ./part1.sh "A string with many words"

E_NO_ARG=1
E_EXCESS_ARG=2
E_EMPTY_ARG=3
E_NO_ALPHA_ARG=4
E_1LETTER_ARG=5


# error when there's no input argument
if [ $# -eq 0 ]
then
    echo "Error - No input arguments."
    echo "Syntax should be: $0 \"A string with many words\""
    
exit $E_NO_ARG
fi

# error when there's more than 1 input argument
if [ $# -gt 1 ]
then
    echo "Error - Too many arguments or missing quotes if you are trying to give a sentence as an input."
    echo "Syntax should be: $0 \"A string with many words\""
    
exit $E_EXCESS_ARG
fi

# convert all chars to lowercase (only works for English letters like I-->i. Not for Turkish letters like İ-->i)
str=$1
strLowerCase=${str,,}

# clear spaces
strNoSpace=${strLowerCase// /}

# error when input argument is empty string
if [ ${#strNoSpace} -eq 0 ]
then
    echo "Error - Input argument is empty or all spaces. Pls provide some string that only consists of a-z/A-Z letters or spaces."
    echo "Syntax should be: $0 \"A string with many words\""
    
exit $E_EMPTY_ARG
fi

# check if input argument is string with a-z letters
if ! [[ $1 =~ ^[a-zA-Z[:space:]]+$ ]]
then
    echo "Error - Input argument includes invalid characters. It should only consist of a-z/A-Z letters or spaces."
    echo "Syntax should be: $0 \"A string with many words\""
    
exit $E_NO_ALPHA_ARG
fi


# check if input argument has more than 1 letter

if [ ${#strNoSpace} -lt 2 ]
then
    echo "Error - All words in input argument must have got more than 1 letter."
    echo "Syntax  should be: $0 \"A string with many words\""
    
exit $E_1LETTER_ARG
fi


#############
# MAIN CODE #	
#############

revStr=""

#take reverse of strNoSpace
for ((i=${#strNoSpace}; i>=0; i--))
do 
	temp=${strNoSpace:$i:1}
	revStr="$revStr"$temp 
done

#calculate middle position of revStr
temp=`expr ${#revStr} + 1`
halfLen=`expr $temp / 2`

#check for polindrome
for ((i=0; i<=halflen; i++))
do
	if [ ${revStr[i]} != ${strNoSpace[-i]} ]
	then
		echo "$str is not a palindrome"
	else
		echo "$str is a palindrome"
	fi
done

exit 0




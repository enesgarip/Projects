#!/bin/bash

# Project-1 
# Mustafa Abdullah Hakkoz - 150117509
# Enes Garip - 150116034

# Part-4 UPPERCASE CONVERSION
# A script to convert matching characters to uppercase in all text files in a given directory.
# It works with a wild card and an optianal pathname as arguments.
#  
# Usage: ./part4.sh "wildcard" path(optional)


E_NO_ARG=1
E_EXCESS_ARG=2
E_EMPTY_ARG1=3
E_PATH_NO_EXIST=4
E_TXT_NO_EXIST=5

# error when there's no input argument
if [ $# -eq 0 ]
then
    echo "Error - No input arguments."
    echo "Syntax should be: $0 \"wildcard\" path(optional)"
    
exit $E_NO_ARG
fi

# error when there's more than 2 input argument
if [ $# -gt 2 ]
then
    echo "Error - Too many arguments."
    echo "Syntax should be: $0 \"wildcard\" path(optional)"
    
exit $E_EXCESS_ARG
fi

# clear spaces
arg1=$1
arg1NoSpace=${arg1// /}

# error when first input argument is empty string
if [[ $arg1NoSpace = "" ]]
then
    echo "Error - First Input argument is an empty string or consists just spaces. Pls provide a string that denotes a standard wildcard format."
    echo "Syntax should be: $0 \"wildcard\" path(optional)"
    exit $E_EMPTY_ARG1
fi

# error when path doesn't exist
if ! [ -d $2 ] 
then
    echo "Error - Directory \"$2\" does not exists."
    echo "Syntax should be: $0 \"wildcard\" path(optional)"
    exit $E_PATH_NO_EXIST
fi

# assign first argument to a variable
wildcard=$arg1NoSpace

# assign second argument to a variable. if it isn't provided, assign current directory to the variable.
if [ $# -eq 2 ]
then directory=$2
else directory=$PWD
fi

cd "$directory"

#error when there's no .txt file in the directory
for file in *.txt
do    
	if ! [ -e "$file" ]
	then 
		echo "Error - There's no text file to operate on in the directory $directory"
		exit $E_TXT_NO_EXIST
	fi
done


#############
# MAIN CODE #	
#############
file_count=0

# search in all text files at $directory
for file in *.txt
do
	# OR part is added for not reading last line if line ends directly with EOF instead of "/n"
	while IFS= read -r line || [ -n "$line" ] 
	do
		for word in $line
		do
			#clean word from ponctuation characters by using POSIX standard
			cleanWord=${word/[[:punct:]]/}		
			#if word matches with wildcard convert it to uppercase
			if [[ $cleanWord = $wildcard ]]
			then	
				printf "%s " ${word^^}
				printf "The word \"%s\" inside %s/%s is substituted with \"%s\".\n\n" "$word" "$directory" "$file" \
					"${word^^}" >> .output     #write output of screen to a different file named ".output"
			else	printf "%s " $word
			fi
		done
	printf "\n"
	done < "$file" > .file_content		#write output of loop to a different file named ".file_content" 
	mv .file_content "$file"			#replace .file_content with original file
	file_count=`expr $file_count + 1`
done

	if [ -e .output ]
	then 
		cat .output			#show screen output by reading from a file
		rm .output			#delete the file
	else echo "No word found for wildcard $wildcard in $file_count text files in the directory: $directory" 
	fi
	
exit 0

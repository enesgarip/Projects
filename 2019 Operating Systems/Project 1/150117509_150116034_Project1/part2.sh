#!/bin/bash

# Project-1 
# Mustafa Abdullah Hakkoz - 150117509
# Enes Garip - 150116034

# Part-2 MOVE .c FILES
# A script to find all the files whose extension is ".c" in a given directory and move them into newly created "cprogs" directory.
# It works with an optianal pathname as argument.
# If user doesn't provide a pathname, program will run under current working directory.
# 
# Usage: ./part2.sh path(optional)

E_MLTPL_ARG=1
E_PATH_NO_EXIST=2
E_C_NO_EXIST=3

#error when there's more than 1 input argument
if [ $# -gt 1 ]
then
	echo "Error - Too many arguments."
    	echo "Syntax should be: $0 path(optional)"
exit $E_MLTPL_ARG
fi

# assign input argument to a variable. if it isn't provided, assign current directory to the variable.
if [ $# -eq 1 ]
then directory=$1
else directory=$PWD
fi

# error when path doesn't exist
if ! [ -d "$directory" ] 
then
    echo "Error - Directory \"$directory\" does not exists."
    echo "Syntax should be: $0 path(optional)"
    exit $E_PATH_NO_EXIST
fi

#error when there's no .c file in the directory
cd "$directory"
for file in *.c
do    
	if ! [ -e "$file" ]
	then 
		echo "Error - There's no .c file to operate in the directory: $directory"
		exit $E_C_NO_EXIST
	fi
done

#############
# MAIN CODE #	
#############

#if optional path doesn't provided by user, it will work under current working directory. Else it will use the given one.
if [ $# -eq 0 ]
then
	mkdir -p cprogs
	echo "cprogs directory is created under the current directory."
	mv *.c cprogs
	echo "C files succesfully moved into cprogs."
elif [ $# -eq 1 ]
then
	c_dir=$1
	cd "$c_dir"
	mkdir -p cprogs
	echo "cprogs directory is created under the given directory $c_dir"
	mv *.c cprogs
	echo "C files succesfully moved into cprogs."
fi
		

exit 0



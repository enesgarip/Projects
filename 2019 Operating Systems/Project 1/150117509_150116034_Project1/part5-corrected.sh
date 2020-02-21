#!/bin/bash

# Project-1 
# Mustafa Abdullah Hakkoz - 150117509
# Enes Garip - 150116034

# Part-5 DELETE FILES
# A script to find all the files whose size is zero under a given directory and ask the user to delete them one by one.
# It works with an optianal pathname as argument and -R option which yields recursive search for sub directories.
# If user doesn't provide a pathname, program will run under current working directory.
# 
# Usage: ./part5.sh -R(option) path(optional argument)


E_EXCESS_ARG=1
E_SWAP_ARGS=2
E_INVALID_OPT=3
E_PATH_NO_EXIST=4
E_ZERO_NO_EXIST=5


# error when there's more than 2 input argument
if [ $# -gt 2 ]
then
    echo "Error - Too many arguments."
    echo "Syntax should be: $0 -R(option) path(optional argument)"
    exit $E_EXCESS_ARG
fi


# error cases when 2 input arguments are given 
if [ $# -eq 2 ]
then
    	#The case of first argument is valid path
	if [ -d "$1" ]
	then
		if [[ $2 = "-R" ]]	# if second argument is valid option "-R" program will warn user to swap arguments.
		then 
			echo "Error - Swap arguments. First must be option \"-R\" and second must be \"path\"."
	    		echo "True syntax is: $0 -R(option) path(optional argument)"
			exit $E_SWAP_ARGS
		else			# if second argument is other than "-R" program will give excess error.
			echo "Error - Too many arguments."
	    		echo "Syntax should be: $0 -R(option) path(optional argument)"
			exit $E_EXCESS_ARG
		fi
	
	#The case of first argument is NOT valid path
	else
		if ! [[ $1 = "-R" ]]	#first argument invalid option
		then
			echo "Error - Invalid option."
	    		echo "Syntax should be: $0 -R(option) path(optional argument)"
			exit $E_INVALID_OPT
		else				#first argument is valid option and second argument is invalid path
			if ! [ -d "$2" ]
			then
				echo "Error - Directory \"$2\" does not exists."
		    		echo "Syntax should be: $0 -R(option) path(optional argument)"
				exit $E_PATH_NO_EXIST
			fi	
		fi
	fi	
fi


# error cases when 1 input argument is given 
if [ $# -eq 1 ]
then
	#The case of the argument is invalid option
	if [ ${#1} -eq 2 ] && [[ $1 =~ -[^R] ]]
	then
		echo "Error - Invalid option."
    		echo "Syntax should be: $0 -R(option) path(optional argument)"
		exit $E_INVALID_OPT

	#The case of the argument is invalid path
	elif ! [ -d "$1" ]
	then
		if ! [[ $1 =~ "-R" ]]
		then
			echo "Error - Directory \"$1\" does not exists."
				echo "Syntax should be: $0 -R(option) path(optional argument)"
			exit $E_PATH_NO_EXIST
		fi
	fi
fi


#############
# MAIN CODE #	
#############

#assign arguments when there's no input argument
if [ $# -eq 0 ]
then
	path=$PWD
	option=0
fi

#assign arguments when there's 1 input argument
if [ $# -eq 1 ]
then
	if [[ $1 = "-R" ]]
	then
		path=$PWD
		option=1
	elif [ -d "$1" ]
	then
		path=$1
		option=0
	fi
fi

#assign arguments when there are 2 input arguments
if [ $# -eq 2 ]
then
	if [[ $1 = "-R" ]]
	then
		if [ -d "$2" ]
		then
			path=$2
			option=1
		fi
	fi
fi

cd "$path"

# build "files" array if -R option isn't provided
if [ $option -eq 0 ]
then
	files=$(find ./ -maxdepth 1 -type f -size 0 )

	# error when there's no file with size 0
	if [ ${#files} -eq 0 ] 
	then 
		echo "Error - There's no files with size zero under current directory. You can try again with option -R"
		echo "Syntax should be: $0 -R(option) path(optional argument)"
		exit $E_ZERO_NO_EXIST
	fi
fi

# build "files" array if -R option is provided
if [ $option -eq 1 ]
then
	files=$(find ./ -type f -size 0)

	# error when there's no file with size 0
	if [ ${#files} -eq 0 ] 
	then 
		echo "Error - There's no files with size zero under all sub directories."
		exit $E_ZERO_NO_EXIST
	fi
fi


#execute removing process
IFS=$'\n'
for file in $files
do
	printf "\nDo you want to delete `dirname "$file"`/`basename "$file"`? (y/n):"
	read yourch
	case $yourch in
		"y") rm "$file"; echo "1 file deleted." ;;
		"n") continue ;;
		*) echo "Opps!!! Please enter \"y\" or \"n\":" ; read ;;
	esac	
done


exit 0

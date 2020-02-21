#!/bin/bash

# Project-1 
# Mustafa Abdullah Hakkoz - 150117509
# Enes Garip - 150116034
#
# Bonus Part MENU
# Script to create simple menu and take action according to that selected.
# 


while :
do
	clear
	echo "-------------------------------------"
	echo " Main Menu "
	echo "-------------------------------------"
	echo "1. Check for palindromes"
	echo "2. Move .c files"
	echo "3. Draw hollowed square"
	echo "4. Uppercase conversion"
	echo "5. Delete files"
	echo "6. Exit"
	echo "======================="
	echo -n "Enter your menu choice [1-6]: "
	read yourch
	case $yourch in
		1) printf "\n##### CHECK FOR PALINDROMES #####\n";
		   echo "Enter a string to check if it's a polindrome or not:";	
		   read ch1;
		   ./part1.sh $ch1; 
		   printf "\nPress a key to return menu. . ." ; read;;

		2) printf "\n##### MOVE .C FILES #####\n";
		   echo "You can optionally enter a path (or hit enter to pass it empty):";	
		   read ch2;
		   ./part2.sh $ch2; 
		   printf "\nPress a key to return menu. . ." ; read;;

		3) printf "\n##### DRAW HOLLOWED SQUARE #####\n";
		   echo "Enter first integer for drawing:";	
		   read ch3 ;
		   echo "Enter second integer for drawing:";	
		   read ch4 ;
		   ./part3.sh $ch3 $ch4; 
		   printf "\nPress a key to return menu. . ." ; read;;

		4) printf "\n##### UPPERCASE CONVERSION #####\n";
		   echo "Enter a wildcard to search in files:";	
		   read ch5;
		   echo "You can optionally enter a path (or hit enter to pass it empty):";	
		   read ch6;
		   ./part4.sh $ch5 $ch6; 
		   printf "\nPress a key to return menu. . ." ; read;;

		5) printf "\n##### DELETE FILES #####\n";
		   echo "You can enter -R option with and/or an optional path (or hit enter to pass it empty):";
		   echo "For example: -R an/optional/path"	
		   echo "Or just: -R"
  		   echo "Or just: an/optional/path"
		   read ch7;
		   ./part5.sh $ch7; 
		   printf "\nPress a key to return menu. . ." ; read;;

		6) printf "\nBye Bye. . .\n" ;
		   exit 0 ;;

		*) echo "Opps!!! Please select choice 1,2,3,4,5, or 6";
		   echo "Press a key. . ." ; read ;;
	esac
done

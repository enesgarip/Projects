
# Muhammed F. EROĞLU 	- 	150116022
# Enes GARİP 		 	- 	150116034

.data
	first_line : .asciiz "Enter the first matrix: "
	second_line : .asciiz "Enter the second matrix: "
	third_line : .asciiz "Enter the first dimension of first matrix: "
	fourth_line : .asciiz "Enter the second dimension of the first matrix: " 
	first_string : .space 200
	second_string : .space 200
	first_matrix: .space 200
	second_matrix: .space 200
	solution_matrix: .space 200
	welcome_msg : .asciiz "Welcome to our MIPS Project!\n"
	q1_a: .asciiz"a : "
	q1_b: .asciiz"b : "
	row: .word 0
	column : .word 0
	row2 : .word 0
	column2: .word 0	
	arrayofa: .space 100
	arrayofb:	.space 100 
    nline:		.asciiz "\n"
	choice : 	.asciiz "Main Menu:\n1.Square Root Approximate\n2.Matrix Multiplication\n3.Palindrome\n4.Exit\nPlease select an option : "
	question_one:    .asciiz "Enter the number of iteration for the series : "		
	string: .space 200
	question: .asciiz "Enter an input string : " 
	count : .word 0
	success_str : .asciiz "is palindrome."
	failure_str: .asciiz"is not palindrome."	
	exit_str : .asciiz "Program ends. Bye :) "
	print_matrix_msg: .asciiz "Matrix Multiplication :\n"
	error_msg: .asciiz "An error has occured. Please try again ..."
		
	.text
	.globl main
      
main:
	li $v0, 4                						# Print the header of the program
	la $a0, welcome_msg          
	syscall

		
	menu:
	
		move $t0,$zero
		move $t1,$zero
		move $t2,$zero
		move $t3,$zero
		move $t4,$zero
		move $t5,$zero								# Set all registers 0 in order to use them properly.
		move $t6,$zero
		move $t7,$zero
		move $t8,$zero
		move $t9,$zero
		move $s1,$zero
		move $s2,$zero
		move $s3,$zero
		move $s4,$zero
		
		li $v0, 4               
        la $a0, choice          
        syscall
		
		li $v0, 5	
		syscall           
		move $t5, $v0
		beq $t5,1,part_one
		beq $t5,2,part_two							# Jump each part for different selection
		beq $t5,3,part_three
		beq $t5,4,exit
		
		j menu
		

part_one:

	li $v0, 4               
    la $a0, question_one        
    syscall		

    li $v0, 5	
    syscall                  
	move $t5, $v0
	
	addi 	$t8,$t8,1
	addi  	$t7,$zero,0
	sw      $t8,arrayofa($t7)
	sw      $t8,arrayofb($t7)
	la 		$s7, arrayofa
	la      $s3, arrayofa
	la      $s4, arrayofb
	
loop1:
	bge		$t0, $t5, makezero
	
	lw      $s1, 0($s3) 							# a's value
	lw      $s2, 0($s4) 							# b's value
    addi    $s3, $s3, 4 							# a's next adress
	addi    $s4, $s4, 4 							# b's next adress
	
	add    $s1, $s1, $s2							# a = a + b
	sw     $s1, 0($s4) 								# write the addition into the next element of arrayb
	add    $s1, $s1, $s2 							# a = a + b
	sw     $s1, 0($s3) 								# write the addition into the next element of arrayb
	
	addi $t0,$t0,1
	
	j	loop1
	
makezero: sub    $t0, $t0, $t5
	
	li      $v0, 4     
    la    $a0,q1_a 
    syscall
	
la      $t1, arrayofa	

print_loop_a:										# This loop is for printing the array of a that is calculated before
    bge     $t0, $t5, initialize

    lw      $t2, 0($t1)
    addi    $t1, $t1, 4

    li      $v0, 1      
    move    $a0, $t2
    syscall

    li      $a0, 32
    li      $v0, 11  
    syscall

    addi    $t0, $t0, 1
    j      print_loop_a
	
initialize: sub    $t0, $t0, $t5				


	li      $a0, 10
    li      $v0, 11  
    syscall
	
	li      $v0, 4     
    la    $a0,q1_b 
    syscall
			
la      $t1, arrayofb

print_loop_b:										# This loop is for printing the array of b that is calculated before
    bge     $t0, $t5, end_of_part_one

    lw      $t2, 0($t1)
    addi    $t1, $t1, 4

    li      $v0, 1      
    move    $a0, $t2
    syscall

    li      $a0, 32
    li      $v0, 11  
    syscall

    addi    $t0, $t0, 1
    j      print_loop_b

end_of_part_one:
	
	li $v0, 4                
    la $a0, nline           
    syscall
	
	li $v0, 4                
    la $a0, nline           
    syscall
	
	j menu											# Jump menu after printing the values of a and b




#-------------------------------------------------------------------------------------------------------------------------------------------------------------


part_two:

	li $v0, 4             
    la $a0, first_line  							# Print first line      
    syscall

	
	li $v0,8	
    la $a0, first_string 							# Take first matrix's string   
    li $a1, 150 
    move $t0,$a0 
	syscall
	
	
	li $v0, 4             
    la $a0,second_line   							# Print second line       
    syscall
	
	li $v0,8 
    la $a0, second_string 							# Take second matrix's string 
    li $a1, 150 
    move $t0,$a0 
	syscall
	
	
	li $v0, 4             
    la $a0, third_line     							# Print third line    
    syscall
	
	li $v0, 5	
	syscall    										# Take first matrix's row	
	sw $v0, row
	
	
	li $v0, 4             
    la $a0, fourth_line       						# Print fourth line    
    syscall	
	
	
	li $v0, 5	
	syscall     									# Take first matrix's column	
	sw $v0, column
	sw $v0, row2
	
	
	la    $t4, first_string	
	la	  $t2, first_matrix
	move  $t5,$zero
	
	
	fill_firstmatrix:								# filling first matrix array with the help of first string with t4 and t2.
													# saves number in byte to first_matrix array
		lb $t3,($t4) 
		beq $t3,32,instr							# if char is " " skip the char
		subu $t3,$t3,48								# find the integer value of char
		bltz $t3,error								# Break the loop if the car is not a number
		subu $t3,$t3,10
		bgtz $t3,error
		addi $t3,$t3,10
		sw  $t3,($t2)								# store the integer value into word array
		addu $t2,$t2,4								# increase adress of word array	
		
		addu $t4,$t4,1								# check the next char of the string break
		lb $t3,($t4)							
		beq $t3,32,fill_firstmatrix					# if char is " " skip the char
		beq $t3,10,secondmatrix						# if char is "\n" break the loop
		
		subu $t2,$t2,4								# load the last stored int 
		lw $t3,($t2)								# and multiply it by 10
		move $t6,$t3								# add it with new int value 
		add $t6,$t6,$t6								# store it into the old place
		sll $t3,$t3,3
		add $t3,$t3,$t6		
		lb $t7,($t4)
		subu $t7,$t7,48
		add $t3,$t3,$t7		
		sw  $t3,($t2)
		addu $t2,$t2,4
		instr:	addu $t4,$t4,1
		j fill_firstmatrix
	
	
secondmatrix:
	beq   $t5,1,continue
	la    $t4, second_string	
	la	  $t2, second_matrix
	addi  $t5,$t5,1
	j fill_firstmatrix


continue:
	
	move $t3,$t2
	la $t2, second_matrix							# Calculate the int numbers by subtract addresses and divide them with 4
	sub $t3,$t3,$t2									# calculate row 2 and column 2
	srl $t3,$t3,2									# and store them into row2 and column2
	lw $t5,row2
	divu $t3,$t5
	mflo $t3
	sw $t3,column2
	move $t7,$zero									# make $t7 zero in order to use it again

	la $t1, first_matrix		
	la $t2, second_matrix
	
	lw $s3, row
	lw $t3, column2
	multu $s3,$t3									# store the solution matrix's element number in $s3
	mflo $s3
	
	move $t3,$zero
	
outer_loop:											# this for loop stands by each element of the solution matrix
	move $s1,$zero
	lw $t5,column2
	divu $t7,$t5									# $t7 stores the number of iteration for solution matrix
	mfhi $t4
	beq $t4,0,change_reference

inner_loop:

	beq $t7,$s3,print_solution  					# breaks the transaction loop if the calculated elements is equal to the solution matrix's element number 
	
	lw $t9,column2
	divu $t6,$t9									# define which column of the second matrix should be in transaction
	mfhi $t6
	
	sll $t6,$t6,2	
	add $t2,$t2,$t6
	
	
	
	move $t8,$zero
	lw $t5,  column
	
	
	transaction:
		beq $t8,$t5,last_part_of_outer_loop
		
		move $t4,$t1
		sll $t8,$t8,2	
		add $t4,$t4,$t8								# load the number from first matrix
		srl $t8,$t8,2								# Same row next column
		lw $t3,($t4) 
		
		
		sll $t9,$t9,2
		mult $t9,$t8
		mflo $t0									# Load the number from second matrix
		move $t4,$t2								# same column next row
		add $t4,$t4,$t0
		lw $s4,($t4)
		
	
		srl $t9,$t9,2		
		mult $t3,$s4
		mflo $t3									# multipy loaded two numbers and add them into the solution matrix's element
		add $s1,$s1,$t3
		
		addi $t8,$t8,1
		
		j transaction
	
	
	
last_part_of_outer_loop:
	
	sub $t2,$t2,$t6
	srl $t6,$t6,2
	
	la $t8,solution_matrix
	sll $t7,$t7,2
	add $t8,$t8,$t7
	srl $t7,$t7,2
	sw $s1,($t8)

	addi $t6,$t6,1
	addi $t7,$t7,1
	j outer_loop
	
change_reference:									# This part defines which row of the first matrix should be selected
	mflo $t8
	beq $t8,0,inner_loop
	lw $t8,column
	sll $t8,$t8,2
	add $t1,$t1,$t8	
	j inner_loop
	
print_solution:	
	
	li $v0, 4                
    la $a0, nline           
    syscall
	
	li $v0, 4                
    la $a0, print_matrix_msg           
    syscall
	
	la $t2,solution_matrix
	lw $t3, row
	lw $t4, column2
	multu $t3,$t4
	mflo $t3
	
	move $t5,$zero
	lw $t4,column2
	

print_solution_loop:								# this loop prints the soluton matrix
	div $t5,$t4
	mfhi $t7

	beq $t5,$t3,end_of_part_two
	beq $t7,0,add_new_line
	

inner:	
	lw $t6,($t2)		
	move  $t0,$t6	
	
	li      $v0, 1      	
    move    $a0, $t0
	li 		$a1, 3 
    syscall
	
	addi $t6,$t6,-10
	bltz $t6,add_space_check1
check1:												# These checks is for formatting output
	addi $t6,$t6,-90
	bltz $t6,add_space_check2
check2:	
	
	
	li      $a0, 32
	li      $v0, 11 								# print \n in console  
	syscall
	
	
	addi $t2,$t2,4
	addi $t5,$t5,1
	
	j print_solution_loop
add_space_check1:
	li      $a0, 32
	li      $v0, 11 # print \n in console  
	syscall
j check1

add_space_check2:
	li      $a0, 32
	li      $v0, 11 # print \n in console  
	syscall
j check2


add_new_line:
	
	beq 	$t5,0,inner
	li      $a0, 10
	li      $v0, 11 # print \n in console  
	syscall
	
	j inner

end_of_part_two:
	
	li $v0, 4                
    la $a0, nline           
    syscall
	
	li $v0, 4                
    la $a0, nline           
    syscall
	
	j menu

#-------------------------------------------------------------------------------------------------------------------------------------------------------------

part_three:
	li $v0, 4             
    la $a0, question  # Print question     
    syscall
	
	li $v0,8	
    la $a0, string	 # Take the string to be checked if palindrome   
    li $a1, 150 
    move $t0,$a0 
	syscall	
	
	li $v0, 4                
    la $a0, nline           
    syscall


	
	la $t2,string
	
	count_char:										# This for loop counts char in the string
		lb $t4,($t2)
		
		beq $t4,10,end_of_count_loop
		


		addi $t3,$t3,1
		addi $t2,$t2,1
		
		j count_char

	end_of_count_loop:

		sb $zero,($t2)
		li $t4,1
		subu $t3,$t3,$t4
		sw $t3,count

		

		move $t5,$zero  							# $ t5 is for palindrome loop iterator
		
	palindrome:
		move $t3,$zero  
		move $t4,$zero	
		
		lw $t3,count
		srl $t3,$t3,1								# If the loop is in the middle of string the rest is unnecessary to check
		subu $t4,$t5,$t3							# break the loop and jump the success state
		bgez $t4,success
		
		move $t3,$zero 
		move $t4,$zero	
		
		la $t2,string
		
		add $t3,$t3,$t5
		lw $t4,count
		subu $t4,$t4,$t5
		add $t3,$t3,$t2
		lb $t6,($t3)								# Load one char from start and load one char from end
		la $t2,string
		add $t4,$t4,$t2
		lb $t7,($t4)
		
		beq  $t7,$t6,last_part_palindrome
		subu $t7,$t7,$t6
		beq  $t7,32,last_part_palindrome			# Check if loaded two chars is equal (case insensitive)
		add	 $t7,$t7,$t6
		subu $t6,$t6,$t7
		beq  $t6,32,last_part_palindrome
		j failure
		

	last_part_palindrome:
		addi $t5,$t5,1
		j palindrome

	success:
		
		li $v0, 4             
		la $a0, string  							# Print the string      
		syscall
		
		li      $a0, 32
		li      $v0, 11								# print " "
		syscall
		
		li $v0, 4             
		la $a0, success_str  						# Print " is palindrome."     
		syscall	
		
		li $v0, 4                
		la $a0, nline           					# print "\n"
		syscall
		
	j end_of_part_three

	failure:
		
		li $v0, 4             
		la $a0, string  							# Print the string      
		syscall
		
		li      $a0, 32
		li      $v0, 11								# print " "
		syscall
		
		li $v0, 4             
		la $a0,	failure_str  						# Print " is not palindrome."     
		syscall
		
		li $v0, 4                					
		la $a0, nline      							# print "\n"    
		syscall
	
	j end_of_part_three


end_of_part_three:

	li $v0, 4                
    la $a0, nline           
    syscall
	j menu
	

#-----------------------------------------------------------------------------------------------------------------------------


error:
	li $v0, 4                
    la $a0, nline           
    syscall
		
	li $v0, 4                
    la $a0, error_msg           
    syscall

	li $v0, 4                
    la $a0, nline           
    syscall
	
	li $v0, 4                
    la $a0, nline           
    syscall
	j menu
	
	


exit:
	
	li $v0, 4                
	la $a0, exit_str          # Print the exit message 
	syscall

	jr $ra
module main (  output wire [3:0] rowwrite,
						input [3:0] colread,
						input clk,
						output wire [3:0] data,
						
						output wire [3:0] grounds,
						output wire [6:0] display
					);

reg [15:0] data_all, temp, temp2, matchsticks;
wire [3:0] keyout;
reg [4:0] enteredNumber;
reg [25:0] clk1;
reg [1:0] ready_buffer;
reg ack;
wire ready;

reg player;

reg [1:0] state;
localparam STATE1 = 2'b00, STATE2 = 2'b01, STATE3 = 2'b10;

assign data=keyout;
	
sevensegment ss1 (.datain(data_all), .grounds(grounds), .display(display), .clk(clk));

keypad_ex  kp1(.rowwrite(rowwrite),.colread(colread),.clk(clk),.keyout(keyout),.ready(ready),.ack(ack));


function integer hexatodecimal;
 input [15:0] a; 
 integer b;
 begin
 b=a[3:0]+a[7:4]*10+a[11:8]*100+a[15:12]*1000;
 hexatodecimal=b;
 end
endfunction



always @(posedge clk)
	ready_buffer<= {ready_buffer[0],ready}; 
	
	

always @(posedge clk)
begin
		case(state)
		STATE1:
			begin
				if(ready_buffer==STATE2)
				begin
					if((0<=keyout)&&(keyout<=9))
					begin
						data_all<=(data_all<<4)+keyout;
						state<=STATE1;
					end
					else if(keyout ==4'he)
					begin
						
						
						temp2=(data_all[11:8] * 100) +(data_all[7:4] * 10) + data_all[3:0];
						matchsticks=temp2;
						//if((9<temp2[3:0]) || (9<temp2[7:4]) || (9<temp2[11:8]))
						begin
						
							temp[3:0]  = temp2 % 4'd10;            
							temp[7:4]  = ((temp2 / 4'd10));// mod 10 olacak mı     
							//temp[11:8] = (temp2 / 4'd100) ; // bu varken 10'a 210 verdi
						
						
						end
						
						
						data_all=temp;
						
						/*
							temp[3:0] = temp2 % 4'd10;            
							temp[7:4] = ((temp2 / 4'd10));     
							temp[11:8] = (temp2 / 4'd100) ;
						
						
						data_all[3:0]<=temp[3:0];
						data_all[7:4]<=temp[7:4];
						data_all[11:8]<=temp[11:8]*16*16;
						*/
						state<=STATE2;
					end
					
					ack<=1;
				end
				else
					ack<=0;
			end
		STATE2:
			begin
				if(ready_buffer==STATE3)
				begin
					if((1<=keyout)&&(keyout<=3))
					begin
						enteredNumber<=keyout;
					end
					else if(keyout==4'hf)
					begin
						if((matchsticks-enteredNumber >= 1) && (1<=enteredNumber)&&(enteredNumber<=3))
						begin
							matchsticks=matchsticks-enteredNumber;//sequential
							
							begin
						
								temp[3:0]  = matchsticks % 4'd10;            
								temp[7:4]  = ((matchsticks / 4'd10));// mod 10 olacak mı     
								//temp[11:8] = (temp2 / 4'd100) ; // bu varken 10'a 210 verdi
						
							end				
						
							data_all=temp;
							
							
							
							player=~player;
							enteredNumber=0;
	
						end
					end
					ack<=1;
					
					if(matchsticks==1)
							begin
								
								state<=STATE3;
							
							end
					else
							state<=STATE2;
				end
			end
		STATE3:
			begin
				
				if(player==0)
					data_all<=16'h00a2;
				else if(player==1)
					data_all<=16'h00a1;
					
				state<=2'b11;
			end
	endcase
end

initial 	
	begin
		data_all=0;
		ack=0;
		player=0;
		temp=16'b0;

		state=2'b00;
		enteredNumber=4'b0000;
	end

endmodule

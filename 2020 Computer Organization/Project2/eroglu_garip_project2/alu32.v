module alu32(sum,a,b,bgezsig,status,gin);//ALU operation according to the ALU control line values
output [31:0] sum;
input [31:0] a,b; 
input [2:0] gin;//ALU control line
input bgezsig;
wire bgezsig;
reg [31:0] sum;
reg [31:0] less;
output status;
reg[1:0] status;
integer intt;
always @(a or b or gin)
begin
	intt = b[10:4];
	case(gin)
	3'b010: sum=a+b; 		//ALU control line=010, ADD
	3'b110: sum=a+1+(~b);	//ALU control line=110, SUB
	3'b111: begin less=a+1+(~b);	//ALU control line=111, set on less than
			if (less[31]) sum=1;	
			else sum=0;
		  end
	3'b000: sum=a & b;	//ALU control line=000, AND
	3'b001: sum=a|b;		//ALU control line=001, OR
	3'b011: sum=a<<intt;
	3'b100: sum=a;
	3'b101: sum =a^b;	// XORI - bitwise 
	default: sum=31'bx;	
	endcase
if(~(|sum))status[1] = 1;
else status[1] = 0;
if(sum[31]) status[0] = 1;
else status[0] = 0;
end
endmodule

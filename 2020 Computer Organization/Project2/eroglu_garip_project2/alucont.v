module alucont(aluop1,aluop0,f3,f2,f1,f0,gout,jmaddctrl,jrctrl,xori);//Figure 4.12 
input aluop1,aluop0,f3,f2,f1,f0,xori;
output [2:0] gout;
output jmaddctrl,jrctrl;
reg jmaddctrl,jrctrl;
reg [2:0] gout;
always @(aluop1 or aluop0 or f3 or f2 or f1 or f0)
begin
assign jmaddctrl = 0;
assign jrctrl = 0;
if(~(aluop1|aluop0))
	begin
	if(xori)gout=3'b101;		// xori signal,ALU control=101 (xori)
	else gout=3'b100;		// for bgez
	end
if(aluop0&~(aluop1))gout=3'b110;
if(aluop1&~(aluop0))//R-type
begin
	if (~(f3|f2|f1|f0))gout=3'b010; 	//function code=0000,ALU control=010 (add)
	if (f3&~(f2)&f1&~(f0))gout=3'b111;			//function code=1010,ALU control=111 (set on less than)
	if (~(f3)&~(f2)&f1&~(f0))gout=3'b110;		//function code=0010,ALU control=110 (sub)
	if (~(f3)&f2&~(f1)&f0)gout=3'b001;			//function code=0101,ALU control=001 (or)
	if (~(f3)&f2&~(f1)&~(f0))gout=3'b000;		//function code=0100,ALU control=000 (and)
	if (~(f3)&~(f2)&f1&f0)gout=3'b011;		//function code=0011,ALU control=011 (sll)
	if (~(f3)&~(f2)&~(f1)&f0)
	begin
	gout=3'b010;		//function code=0001,ALU control=010 (jmadd)
	assign jmaddctrl=1;
	end
	if (~(f3)&f2&f1&~(f0))		
	begin
	gout=3'b100;		//function code=0110,ALU control=100 (jr)
	assign jrctrl=1;
	end
	
end

end
endmodule

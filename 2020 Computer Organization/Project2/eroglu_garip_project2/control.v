module control(in,regdest,alusrc,memtoreg,regwrite,memread,memwrite,branch,aluop1,aluop2,xorisig,bgezsig,balzsig);
input [5:0] in;
output regdest,alusrc,memtoreg,regwrite,memread,memwrite,branch,aluop1,aluop2,xorisig,bgezsig,balzsig;
wire rformat,lw,sw,beq,bgez,balz;
wire [1:0] branch;

assign rformat=~|in;												 // SLL burda r -type olduğu için sadece function code a bakacağız
assign lw=in[5]& (~in[4])&(~in[3])&(~in[2])&in[1]&in[0];			  // 100011
assign sw=in[5]& (~in[4])&in[3]&(~in[2])&in[1]&in[0];				  // 101011
assign beq=~in[5]& (~in[4])&(~in[3])&in[2]&(~in[1])&(~in[0]); 		  // 000100
assign xori=~in[5]& (~in[4])&in[3]&in[2]&in[1]&(~in[0]);	  		  // 001110
assign bgez=in[5]& (~in[4])&(~in[3])&in[2]&in[1]&in[0];	  	  		  // 100111
assign balz=(~in[5])& in[4]&in[3]&(~in[2])&in[1]&(~in[0]);	 		  // 011010
assign regdest=rformat;
assign alusrc=lw|sw|xori;
assign memtoreg=lw;
assign regwrite=rformat|lw|xori;
assign memread=lw;
assign memwrite=sw;
assign aluop1=rformat;
assign aluop2=branch[0];
assign xorisig=xori;
assign bgezsig = bgez;
assign branch[1] = beq|bgez;
assign branch[0] = beq|balz;
assign balzsig= balz;
endmodule

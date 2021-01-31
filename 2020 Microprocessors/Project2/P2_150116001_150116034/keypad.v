module keypad (
						output reg [3:0] rowwrite,
						input [3:0] colread,
						input clk,
						input ack,
						input statusordata,
						output reg [15:0] keyout
						);
wire keypressed;		
reg [25:0] clk1;
reg ready;
reg [3:0] keyread, data;
reg [3:0] rowpressed;
reg [3:0] pressedcol [0:3];
reg [11:0] rowpressed_buffer0, rowpressed_buffer1, rowpressed_buffer2, rowpressed_buffer3;
reg [3:0] rowpressed_debounced;

always @(posedge clk)
	clk1<=clk1+1;

always @(posedge clk1[15])
	rowwrite<={rowwrite[2:0], rowwrite[3]};

always @(posedge clk1[15])
	if (rowwrite== 4'b1110)
		begin
			rowpressed[0]<= ~(&colread); //colread=1111--> none of them pressed, colread=1110 --> 1, colread=1101-->2, 1011-->3, 0111->A
			pressedcol[0]<=colread;
		end
	else if (rowwrite==4'b1101)
		begin
			rowpressed[1]<= ~(&colread); //colread=1111--> none of them pressed, colread=1110 --> 4, colread=1101-->5, 1011-->6, 0111->B
			pressedcol[1]<=colread;
		end
	else if (rowwrite==4'b1011)
		begin
			rowpressed[2]<= ~(&colread); //colread=1111--> none of them pressed, colread=1110 --> 7, colread=1101-->8, 1011-->9, 0111->C
			pressedcol[2]<=colread;
		end
	else if (rowwrite==4'b0111)
		begin
			rowpressed[3]<= ~(&colread); //colread=1111--> none of them pressed, colread=1110 --> *(E), colread=1101-->0, 1011-->#(F), 0111->D
			pressedcol[3]<=colread;
		end
		
wire transition0_10;
wire transition0_01;

assign transition0_10=~|rowpressed_buffer0;
assign transition0_01=&rowpressed_buffer0;

wire transition1_10;
wire transition1_01;

assign transition1_10=~|rowpressed_buffer1;
assign transition1_01=&rowpressed_buffer1;

wire transition2_10;
wire transition2_01;

assign transition2_10=~|rowpressed_buffer2;
assign transition2_01=&rowpressed_buffer2;

wire transition3_10;
wire transition3_01;

assign transition3_10=~|rowpressed_buffer3; //kpd=1-->0
assign transition3_01=&rowpressed_buffer3;  //kpd=0-->1


always @(posedge clk1[15])
	begin
		rowpressed_buffer0<= {rowpressed_buffer0[10:0],rowpressed[0]};
		if (rowpressed_debounced[0]==0 && transition0_01)
			rowpressed_debounced[0]<=1;
		if (rowpressed_debounced[0]==1 && transition0_10)
			rowpressed_debounced[0]<=0;
	
	rowpressed_buffer1<= {rowpressed_buffer1[10:0],rowpressed[1]};
		if (rowpressed_debounced[1]==0 && transition1_01)
			rowpressed_debounced[1]<=1;
		if (rowpressed_debounced[1]==1 && transition1_10)
			rowpressed_debounced[1]<=0;
			
	rowpressed_buffer2<= {rowpressed_buffer2[10:0],rowpressed[2]};
		if (rowpressed_debounced[2]==0 && transition2_01)
			rowpressed_debounced[2]<=1;
		if (rowpressed_debounced[2]==1 && transition2_10)
			rowpressed_debounced[2]<=0;
	
	rowpressed_buffer3<= {rowpressed_buffer3[10:0],rowpressed[3]};
		if (rowpressed_debounced[3]==0 && transition3_01)
			rowpressed_debounced[3]<=1;
		if (rowpressed_debounced[3]==1 && transition3_10)
			rowpressed_debounced[3]<=0;
	end 

always @*
	begin
		if (rowpressed_debounced[0]==1)
			begin
				if (pressedcol[0]==4'b1110)
					keyread=4'h1;
				else if (pressedcol[0]==4'b1101)
					keyread=4'h2;
				else if (pressedcol[0]==4'b1011)
					keyread=4'h3;
				else if (pressedcol[0]==4'b0111)
					keyread=4'hA;
				else keyread=4'b0000;
			end
		else if (rowpressed_debounced[1]==1)
			begin
				if (pressedcol[1]==4'b1110)
					keyread=4'h4;
				else if (pressedcol[1]==4'b1101)
					keyread=4'h5;
				else if (pressedcol[1]==4'b1011)
					keyread=4'h6;
				else if (pressedcol[1]==4'b0111)
					keyread=4'hB;
				else keyread=4'b0000;
			end
		else if (rowpressed_debounced[2]==1)
			begin
				if (pressedcol[2]==4'b1110)
					keyread=4'h7;
				else if (pressedcol[2]==4'b1101)
					keyread=4'h8;
				else if (pressedcol[2]==4'b1011)
					keyread=4'h9;
				else if (pressedcol[2]==4'b0111)
					keyread=4'hC;
				else keyread=4'b0000;
			end
		else if (rowpressed_debounced[3]==1)
			begin
				if (pressedcol[3]==4'b1110)
					keyread=4'hE;
				else if (pressedcol[3]==4'b1101)
					keyread=4'h0;
				else if (pressedcol[3]==4'b1011)
					keyread=4'hF;
				else if (pressedcol[3]==4'b0111)
					keyread=4'hD;
				else keyread=4'b0000;
			end
		else keyread=4'b0000;
	end //always


assign keypressed= rowpressed_debounced[0]||rowpressed_debounced[1]||rowpressed_debounced[2]||rowpressed_debounced[3];

reg [1:0] keypressed_buffer; //yeni karakter için parmağı çekip tekrar basmamız için gerekli

always @(posedge clk)
	keypressed_buffer<={keypressed_buffer[0],keypressed};

always @(posedge clk)
	if ((keypressed_buffer==2'b01)&&(ready==0))
		begin
			data<=keyread;
			ready<=1;
		end
	else if ((ack==1)&&(ready==1))
		ready<=0;
	
always @(*)
	if (statusordata==1)
		keyout={15'b0,ready};
	else
		keyout={12'b0,data};
	
initial 
	begin
		rowwrite=4'b1110;		
		ready=0;
	end
endmodule
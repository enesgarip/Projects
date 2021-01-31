module main_module (
			output wire [3:0] rowwrite,
			input [3:0] colread,
			input clk,
			output wire [3:0] grounds,
			output wire [6:0] display,
			output reg [3:0] debug_leds_r,
			output reg [3:0] debug_leds_l,
			output reg [7:0] debug_leds,
			input pushbutton //may be used as clock
			);
 
reg [15:0] data_all;
wire [3:0] keyout;
reg [25:0] clk1;
reg [1:0] ready_buffer;
reg ack;
reg statusordata;




wire [15:0] regzero;
 
//memory map is defined here
localparam	BEGINMEM=12'h000,
		ENDMEM=12'h1ff,
		KEYPAD=12'h900,
		SEVENSEG=12'hb00;
//  memory chip
reg [15:0] memory [0:51]; 
 
// cpu's input-output pins
wire [15:0] data_out, keypad_out;
reg [15:0] data_in;
wire [11:0] address;
wire memwt;

 
sevensegment ss1 (.datain(data_all), .grounds(grounds), .display(display), .clk(clk));
 
keypad  kp1(.rowwrite(rowwrite), .colread(colread), .clk(clk), .ack(ack), .statusordata(statusordata), .keyout(keypad_out));
 
bird br1 (.clk(clk), .data_in(data_in), .data_out(data_out), .address(address), .memwt(memwt), .zero_result(regzero));
 
 
//multiplexer for cpu input
always @*
	if ( (BEGINMEM<=address) && (address<=ENDMEM) )
		begin
			data_in=memory[address];
			ack=0;
			statusordata=0;
			debug_leds_r<=4'b0100;
		end
	else if (address==KEYPAD+1)
		begin	
			statusordata=1;
			data_in=keypad_out;
			ack=0;
			debug_leds_r<=4'b0010;
		end
	else if (address==KEYPAD)
		begin
			statusordata=0;
			data_in<={12'b0,keypad_out/*keyout*/};
			debug_leds_r<=4'b0001;
			ack=1;
			//to be added 
		end
	else
		begin
			data_in=16'hf345; //any number
			ack=0;
			statusordata=0;
		end
 
//multiplexer for cpu output 
 
always @(posedge clk) //data output port of the cpu
	if (memwt)
		if ( (BEGINMEM<=address) && (address<=ENDMEM) )
		begin
			memory[address]<=data_out;
			debug_leds_l<=4'b0010;
		end
		else if ( SEVENSEG==address)
		begin
			data_all<=data_out/*regzero*/;
			debug_leds_l<=4'b0001;
		end
always @(posedge clk)
   clk1 = clk1+1;
 
initial 
	begin
		debug_leds_r=4'b1111;
		debug_leds_l=4'b1111;
		clk1=0;
		data_all=0;
		ack=0;
		statusordata=0;
		$readmemh("ram.dat", memory);
	end
 
endmodule
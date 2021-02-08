module shift_26(shout,shin);
output [31:0] shout;
input [25:0] shin;
assign shout=shin<<2;
endmodule

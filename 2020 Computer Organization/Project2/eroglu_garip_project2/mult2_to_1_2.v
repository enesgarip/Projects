module mult2_to_1_2(out, i0,i1,s0);
output [1:0] out;
input [1:0]i0,i1;
input s0;
assign out = s0 ? i1:i0;
endmodule
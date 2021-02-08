module mult4_to_1_32(out, i0,i1,i2,i3,s0);
output [31:0] out;
input [31:0]i0,i1,i2,i3;
input [1:0] s0;

assign out = s0[1] ? (s0[0] ? i3 : i2) : (s0[0] ? i1 : i0);
//if( ~(s0[1]) & ~(s0[0]) ) assign out = i0;
//else if( (~s0[1]) & s0[0] ) assign out = i1;
//else if( s0[1] & (~s0[0]) ) assign out = i2;
//else assign out = i3;
endmodule

module processor;
reg [31:0] pc; //32-bit prograom counter
reg clk; //clock
reg [7:0] datmem[0:31],mem[0:31]; //32-size data and instruction memory (8 bit(1 byte) for each location)
reg[1:0] status;
wire [31:0] 
dataa,	//Read data 1 output of Register File
datab,	//Read data 2 output of Register File
out2,		//Output of mux with ALUSrc control-mult2
out3,		//Output of mux with MemToReg control-mult3
out4,		//Output of mux with (Branch&ALUZero) control-mult4
out5,
out7,
out8,
sum,		//ALU result
extad,	//Output of sign-extend unit
adder1out,	//Output of adder which adds PC and 4-add1
adder2out,	//Output of adder which adds PC+4 and 2 shifted sign-extend result-add2
sextad,	//Output of shift left 2 unit
jumpline,
empty;

wire [5:0] inst31_26;	//31-26 bits of instruction
wire [4:0] 
inst25_21,	//25-21 bits of instruction
inst20_16,	//20-16 bits of instruction
inst15_11,	//15-11 bits of instruction
out1;		//Write data input of Register File

wire[25:0] inst25_0;

wire [15:0] inst15_0;	//15-0 bits of instruction

wire [31:0] instruc,	//current instruction
dpack;	//Read data output of memory (data read from memory)

wire [2:0] gout;	//Output of ALU control unit

wire jmaddctrl; // Control signal for jmadd output of alucontrol.
wire jrctrl,balz,xori,bgezsig; //Control signal for jr output of alucontrol.
wire [1:0] out_j_control,mux_status;

wire zout,	//Zero output of ALU
pcsrc,	//Output of AND gate with Branch and ZeroOut inputs
//Control signals
regdest,alusrc,memtoreg,regwrite,memread,memwrite,aluop1,aluop0,
modifiedmemRead, modifiedmemtoReg, modifiedregWrite;
wire [0:1] branch,status_in_wire,status_out_wire;

//32-size register file (32 bit(1 word) for each register)
reg [31:0] registerfile[0:31];

integer i;

// datamemory connections

always @(posedge clk)
//write data to memory
if (memwrite)
begin 
//sum stores address,datab stores the value to be written
datmem[sum[4:0]+3]=datab[7:0];
datmem[sum[4:0]+2]=datab[15:8];
datmem[sum[4:0]+1]=datab[23:16];
datmem[sum[4:0]]=datab[31:24];
end

//instruction memory
//4-byte instruction
 assign instruc={mem[pc[4:0]],mem[pc[4:0]+1],mem[pc[4:0]+2],mem[pc[4:0]+3]};
 assign inst31_26=instruc[31:26];
 assign inst25_21=instruc[25:21];
 assign inst20_16=instruc[20:16];
 assign inst15_11=instruc[15:11];
 assign inst15_0=instruc[15:0];
 assign inst25_0=instruc[25:0];
 
always @(posedge clk)
 assign status = mux_status;
 assign status_out_wire = status;

// registers

assign dataa=registerfile[inst25_21];//Read register 1
assign datab=registerfile[inst20_16];//Read register 2

always @(posedge clk)
 registerfile[out1]= modifiedregWrite ? out7:registerfile[out1];//Write data to register

//read data from memory, sum stores address
assign dpack={datmem[sum[5:0]],datmem[sum[5:0]+1],datmem[sum[5:0]+2],datmem[sum[5:0]+3]};

//multiplexers
//mux with RegDst control
mult2_to_1_5  mult1(out1, instruc[20:16],instruc[15:11],regdest);

//mux with ALUSrc control
mult2_to_1_32 mult2(out2, datab,extad,alusrc);

//mux with MemToReg control
mult2_to_1_32 mult3(out3, sum,dpack,modifiedmemtoReg);

// BR-J signal controls
mult4_to_1_32 mult4(out4, adder1out,empty,jumpline,adder2out,out_j_control);

// output , 0.input, 1.input, selectbit
//mux with jmadd and ordinar path
mult2_to_1_32 mult5(out5,out4, out3,jmaddctrl);

// mux with jmadd and ordinar data ( the addres will be written into $31)
mult2_to_1_32 mult6(out7,out3,adder1out,jmaddctrl);

//mux with jmadd and ordinar path
mult2_to_1_32 mult7(out8,out5, out3,jrctrl);

//mux with jmadd and ordinar path
mult2_to_1_2 mult8(mux_status,status_in_wire, status_out_wire,balz);

// load pc
always @(negedge clk)
if (i == 31)
begin
pc=32'b100;
i = i+1;
end
else
pc = out8;
// alu, adder and control logic connections

br_j_control brj(branch,status,out_j_control);

//ALU unit
alu32 alu1(sum,dataa,out2,bgezsig,status_out_wire,gout);

//adder which adds PC and 4
adder add1(pc,32'h4,adder1out);

//adder which adds PC+4 and 2 shifted sign-extend result
adder add2(adder1out,sextad,adder2out);

//Control unit
control cont(instruc[31:26],regdest,alusrc,memtoreg,regwrite,memread,memwrite,branch,
aluop1,aluop0,xori,bgezsig,balz);

//Sign extend unit
signext sext(instruc[15:0],extad);

//ALU control unit
alucont acont(aluop1,aluop0,instruc[3],instruc[2], instruc[1], instruc[0] ,gout,jmaddctrl,jrctrl,xori);

//Shift-left 2 unit
shift shift2(sextad,extad);
shift_26 shift1(jumpline,inst25_0);

//AND gate
// Kaldirilacak ve yerine jumpcontrol gelecek.
//assign pcsrc=branch && status[1];
assign modifiedregWrite = instruc[3] ?(instruc[2]?(instruc[1]?(instruc[0]?(regwrite):(regwrite)):(instruc[0]?(regwrite):(regwrite))):(instruc[1]?(instruc[0]?(regwrite):(regwrite)):(instruc[0]?(regwrite):(regwrite)))):(instruc[2]?(instruc[1]?(instruc[0]?(regwrite):(0)):(instruc[0]?(regwrite):(regwrite))):(instruc[1]?(instruc[0]?(regwrite):(regwrite)):(instruc[0]?(regwrite):(regwrite))));

// OR gate
assign modifiedmemRead = memread | jmaddctrl;
assign modifiedmemtoReg = memtoreg |jmaddctrl;

//initialize datamemory,instruction memory and registers
//read initial data from files given in hex
initial
begin
$readmemh("initDm.dat",datmem); //read Data Memory
$readmemh("initIM.dat",mem);//read Instruction Memory
$readmemh("initReg.dat",registerfile);//read Register File

	for(i=0; i<31; i=i+1)
	$display("Instruction Memory[%0d]= %h  ",i,mem[i],"Data Memory[%0d]= %h   ",i,datmem[i],
	"Register[%0d]= %h",i,registerfile[i],"deger : ",datmem[16]);
end

initial
begin
pc=0;
#400 $finish;
	
end
initial
begin
clk=0;
//40 time unit for each cycle
forever #20  clk=~clk;
end
initial 
begin
  $monitor($time,"PC %h",pc,"  SUM %h",sum,"   INST %h",instruc[31:0],
"   REGISTER %h %h %h %h ",registerfile[4],registerfile[5], registerfile[6],registerfile[1] );
end
endmodule


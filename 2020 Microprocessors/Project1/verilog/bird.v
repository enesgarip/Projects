module bird(grounds, display, clk, pushbutton); 
 
    input   pushbutton; 
    output  [3:0] grounds;
    output  [6:0] display;
    input   clk; 
 
 
    //memory map is defined here
    localparam     BEGINMEM=12'h000,
                   ENDMEM=12'h6ff,
                   KEYPAD=12'h900,
                   SEVENSEG=12'hb00;
 
//.dış(iç)
 
    //  memory chip
    reg     [15:0] memory [0:511]; 
 
    // cpu's input-output pins
    wire    [15:0] pc;
    reg     [15:0] data_out, data_in;
    reg     [11:0] address;
    reg     memwt;
 
    // input-output devices's pins
    reg     [15:0] keypadregs [1:0];
    reg     [15:0] ss7;
 
 
    //instantiation of cpu
    cpu birdCpu(.clk(pushbutton), .data_out(data_out), data_in(data_in), 
                                        .address(address), .memwt(memwt));
    //instantiation of sevensegment
    sevensegment monitor1( .grounds(grounds), .display(display), .clk(clk),  .info(memory[0]));
    //instantiation of keypad
    keypad_ex keypad1(.rowwrite(rowwrite), .colread(colread), .dataout(keypaddataout),
                                        .readyclr(readyclr),.a0(a0), .clk(clk))
 
    //multiplexer for cpu input
    always @*  
        if ( (BEGINMEM<=address) && (address<=ENDMEM) )
            data_in=memory[address];
        else if ( (KEYPAD<=address) && (address<=KEYPAD+1) )
            data_in=keypaddataout;
        else
            data_in=16'hf345;    
 
 
    //multiplexer for cpu output            
    always @(posedge clk) //data output port of the cpu
        if (memwt)
            if ( (BEGINMEM<=address) && (address<=ENDMEM) )
                memory[address]<=data_out;
            else if ( SEVENSEG1==address) 
                 ss7<=data_out;
 
 
    initial begin
            memory[0]=16'h7e80;
            memory[1]=16'h5ffe;
            for (i=2;i<512;i=i+1)
            begin
                memory[i]=16'h0;  //eğer doğru hatırlıyorsam 128 lokasyonun tümünü (sıfır olsalar bile) 
            end                       // initialize etmek lazım..
            $readmemh("output.txt", memory);
          end
 
endmodule
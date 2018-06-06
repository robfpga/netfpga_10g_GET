//-----------------------------------------------------------------------------
// nf10_axis_converter_0_wrapper.v
//-----------------------------------------------------------------------------

module nf10_axis_converter_0_wrapper
  (
    axi_aclk,
    axi_resetn,
    m_axis_tdata,
    m_axis_tstrb,
    m_axis_tuser,
    m_axis_tvalid,
    m_axis_tready,
    m_axis_tlast,
    s_axis_tdata,
    s_axis_tstrb,
    s_axis_tuser,
    s_axis_tvalid,
    s_axis_tready,
    s_axis_tlast
  );
  input axi_aclk;
  input axi_resetn;
  output [255:0] m_axis_tdata;
  output [31:0] m_axis_tstrb;
  output [127:0] m_axis_tuser;
  output m_axis_tvalid;
  input m_axis_tready;
  output m_axis_tlast;
  input [63:0] s_axis_tdata;
  input [7:0] s_axis_tstrb;
  input [127:0] s_axis_tuser;
  input s_axis_tvalid;
  output s_axis_tready;
  input s_axis_tlast;

  nf10_axis_converter
    #(
      .C_M_AXIS_DATA_WIDTH ( 256 ),
      .C_S_AXIS_DATA_WIDTH ( 64 ),
      .C_M_AXIS_TUSER_WIDTH ( 128 ),
      .C_S_AXIS_TUSER_WIDTH ( 128 ),
      .C_LEN_WIDTH ( 16 ),
      .C_SPT_WIDTH ( 8 ),
      .C_DPT_WIDTH ( 8 ),
      .C_DEFAULT_VALUE_ENABLE ( 0 ),
      .C_DEFAULT_SRC_PORT ( 8'h00 ),
      .C_DEFAULT_DST_PORT ( 8'h00 )
    )
    nf10_axis_converter_0 (
      .axi_aclk ( axi_aclk ),
      .axi_resetn ( axi_resetn ),
      .m_axis_tdata ( m_axis_tdata ),
      .m_axis_tstrb ( m_axis_tstrb ),
      .m_axis_tuser ( m_axis_tuser ),
      .m_axis_tvalid ( m_axis_tvalid ),
      .m_axis_tready ( m_axis_tready ),
      .m_axis_tlast ( m_axis_tlast ),
      .s_axis_tdata ( s_axis_tdata ),
      .s_axis_tstrb ( s_axis_tstrb ),
      .s_axis_tuser ( s_axis_tuser ),
      .s_axis_tvalid ( s_axis_tvalid ),
      .s_axis_tready ( s_axis_tready ),
      .s_axis_tlast ( s_axis_tlast )
    );

endmodule

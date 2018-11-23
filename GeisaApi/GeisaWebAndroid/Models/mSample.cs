using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mSample
    {
        public String SampleId { get; set; }
        public String CallPlanId { get; set; }
        public int CustId { get; set; }
        public String SampleFor { get; set; }
        public String SampleDate { get; set; }
        public int SampleStatusId { get; set; }
        public String SampleStatus { get; set; }
        public String CreatedDate { get; set; }
        public String CreatedBy { get; set; }
        //untuk realisasi sample
        public String SampleReceivedDate { get; set; }
        public String CustPic { get; set; }
        public String CustPicJabatan { get; set; }
        public String CustPicHp { get; set; }
        public String Note { get; set; }
        public String ModifiedDate { get; set; }
        public String ModifiedBy { get; set; }
        public int StatusSend { get; set; }
        // untuk followup sample
        public String SampleResponseDate { get; set; }
        public String SampleResponseNote { get; set; }
        //gobal
        public List<mProductSample> ProductOfRequest { get; set; }
        public List<mProductSample> ProductOfRealisasi { get; set; }

        //public class mProductSample
        //{
        //    public String SampleProdukId { get; set; }
        //    public String SampleId { get; set; }
        //    public int ProductId { get; set; }
        //    public String Kemasan { get; set; }
        //    public double Qty { get; set; }
        //    public String Note { get; set; }
        //    public String TypeRequest { get; set; }
        //    public bool Selected { get; set; }
        //    public String ProductName { get; set; }
        //    public String ProductCode { get; set; }
        //    public String CreatedDate { get; set; }
        //}
    }
}
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace GeisaWebAndroid.Models
{
    public class mCustomerClasification
    {

        public int RecId { get; set; }
        public int CustId { get; set; }
        public int ChannelWayId { get; set; }
        public int ChannelGradeId { get; set; }
        public int ChannelAppId { get; set; }
        public int ChannelStagingId { get; set; }
        public String PeriodeStart { get; set; }
        public String PeriodeEnd { get; set; }
        public int Active { get; set; }
  
        public String CreatedDate { get; set; }

        public String ChannelCode { get; set; }
        public String ChannelWayName { get; set; }
        public int ChannelGroupId { get; set; }
        public String Description { get; set; }
        public int Status { get; set; }

        public int ChannelId { get; set; }
        public String ChannelName { get; set; }
        public String Email { get; set; }
        public String Signature { get; set; }
        public String Pic { get; set; }
        public int StatusId { get; set; }

        public String ChannelGradeCode { get; set; }
        public String ChannelGradeName { get; set; }
        public String ChannelGradeDescription { get; set; }

        public String ChannelAppCode { get; set; }
        public String ChannelAppName { get; set; }

        public String ChannelStagingCode { get; set; }
        public String ChannelStagingName { get; set; }
        public String ChannelStagingDescription { get; set; }
        public String ChannelStagingShareWalet { get; set; }

        public List<ChannelStagingApproach> StagingApproach { get; set; }
        public List<ChannelProduk> Produk { get; set; }

        public class ChannelProduk
        {
            public int ChannelAppProdukRelasiId { get; set; }
            public int ChannelAppId { get; set; }
            public int ProdukId { get; set; }
            public int Status { get; set; }
            public String ProductName { get; set; }
            public String ProductSimpleDescription { get; set; }
        }
        public  class ChannelStagingApproach
        {
            public int RecId { get; set; }
            public int ChannelStagingItemId { get; set; }
            public int ChannelStagingId { get; set; }
            public String ChannelStagingFor { get; set; }
            public int ChannelStagingApproachStatus { get; set; }

            public String ChannelStagingCode { get; set; }
            public String ChannelStagingName { get; set; }
            public String ChannelStagingDescription { get; set; }
            public String ChannelStagingShareWalet { get; set; }

            public List<StagingItem> Item { get; set; }
            public class StagingItem
            {
                public int ChannelStagingItemId { get; set; }
                public String ChannelStagingItemCode { get; set; }
                public String ChannelStagingItemName { get; set; }
                public String ChannelStagingItemDesc { get; set; }
                public int ChannelStagingItemStatus { get; set; }
            }
            
        }
    }
    
}